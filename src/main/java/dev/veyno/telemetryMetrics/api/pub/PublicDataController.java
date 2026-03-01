package dev.veyno.telemetryMetrics.api.pub;


import dev.veyno.telemetryMetrics.data.dto.api.pub.PublicDataDtos;
import dev.veyno.telemetryMetrics.data.entity.TelemetryMetricSample;
import dev.veyno.telemetryMetrics.data.entity.statuspage.*;
import dev.veyno.telemetryMetrics.data.repository.TelemetryMetricSampleRepository;
import dev.veyno.telemetryMetrics.data.repository.statuspage.*;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/data")
public class PublicDataController {

    private static final int MAX_POINTS = 500;
    private static final int DEFAULT_DURATION_MINUTES = 60;

    private final TelemetryStatusPageDatasourceRepository datasourceRepo;
    private final TelemetryStatusPageDatasourceCompositionRepository compositionRepo;
    private final TelemetryMetricSampleRepository sampleRepo;

    private final TelemetryStatusPageGraphRepository graphRepo;
    private final TelemetryStatusPageWidgetRepository widgetRepo;
    private final TelemetryStatusPageLayoutRepository layoutRepo;

    public PublicDataController(
            TelemetryStatusPageDatasourceRepository datasourceRepo,
            TelemetryStatusPageDatasourceCompositionRepository compositionRepo,
            TelemetryMetricSampleRepository sampleRepo,
            TelemetryStatusPageGraphRepository graphRepo,
            TelemetryStatusPageWidgetRepository widgetRepo,
            TelemetryStatusPageLayoutRepository layoutRepo
    ) {
        this.datasourceRepo = datasourceRepo;
        this.compositionRepo = compositionRepo;
        this.sampleRepo = sampleRepo;
        this.graphRepo = graphRepo;
        this.widgetRepo = widgetRepo;
        this.layoutRepo = layoutRepo;
    }

    @GetMapping("/{datasourceId}")
    public PublicDataDtos.DataResponse getDatasourceData(
            @PathVariable UUID datasourceId,
            @RequestParam(required = false) Integer duration
    ) {
        TelemetryStatusPageDatasource ds = datasourceRepo.findById(datasourceId).orElseThrow();

        int effectiveDuration = (duration != null)
                ? clampInt(duration, 1, 60 * 24 * 7)
                : findDefaultDurationMinutes(datasourceId);

        Instant to = Instant.now();
        Instant from = to.minus(Duration.ofMinutes(effectiveDuration));

        List<PublicDataDtos.Point> points = switch (ds.getType()) {
            case RAW -> computeRaw(ds, from, to);
            case COMPOSED -> computeComposed(ds, from, to);
        };

        return new PublicDataDtos.DataResponse(
                datasourceId,
                from.toEpochMilli(),
                to.toEpochMilli(),
                effectiveDuration,
                points
        );
    }

    private int findDefaultDurationMinutes(UUID datasourceId) {
        List<TelemetryStatusPageGraph> graphs = graphRepo.findByDatasourceId(datasourceId);
        if (graphs.isEmpty()) return DEFAULT_DURATION_MINUTES;

        List<UUID> graphIds = graphs.stream().map(TelemetryStatusPageGraph::getId).toList();

        List<TelemetryStatusPageWidget> widgets = new ArrayList<>();
        for (UUID graphId : graphIds) {
            widgets.addAll(widgetRepo.findByGraphId(graphId));
        }
        if (widgets.isEmpty()) return DEFAULT_DURATION_MINUTES;

        for (TelemetryStatusPageWidget w : widgets) {
            List<TelemetryStatusPageLayout> layouts = layoutRepo.findByWidgetId(w.getId());
            if (!layouts.isEmpty()) {
                int d = layouts.get(0).getDuration();
                if (d > 0) return d;
            }
        }

        return DEFAULT_DURATION_MINUTES;
    }

    private List<PublicDataDtos.Point> computeRaw(TelemetryStatusPageDatasource ds, Instant from, Instant to) {
        UUID serviceId = ds.getServiceId();
        if (serviceId == null) return List.of();

        List<TelemetryMetricSample> samples = sampleRepo.findAllByServiceIdAndTimestampBetween(serviceId, from, to);
        samples.sort(Comparator.comparing(TelemetryMetricSample::getTimestamp));

        if (samples.isEmpty()) return List.of();

        if (samples.size() <= MAX_POINTS) {
            return samples.stream()
                    .map(s -> new PublicDataDtos.Point(s.getTimestamp().toEpochMilli(), s.getValue()))
                    .toList();
        }

        List<Long> timeline = uniformTimeline(from, to, MAX_POINTS);
        return timeline.stream()
                .map(t -> new PublicDataDtos.Point(t, interpolateAt(samples, t)))
                .toList();
    }

    private List<PublicDataDtos.Point> computeComposed(TelemetryStatusPageDatasource ds, Instant from, Instant to) {
        List<TelemetryStatusPageDatasourceComposition> rows = compositionRepo.findByDatasourceId(ds.getId());
        if (rows.isEmpty()) return List.of();

        List<UUID> serviceIds = rows.stream().map(TelemetryStatusPageDatasourceComposition::getServiceId).toList();

        Map<UUID, List<TelemetryMetricSample>> byService = new HashMap<>();
        for (UUID serviceId : serviceIds) {
            List<TelemetryMetricSample> samples = sampleRepo.findAllByServiceIdAndTimestampBetween(serviceId, from, to);
            samples.sort(Comparator.comparing(TelemetryMetricSample::getTimestamp));
            byService.put(serviceId, samples);
        }

        boolean allUnderLimit = byService.values().stream().allMatch(s -> s.size() <= MAX_POINTS);

        List<Long> timeline;
        if (allUnderLimit) {
            List<TelemetryMetricSample> base = byService.values().stream()
                    .max(Comparator.comparingInt(List::size))
                    .orElse(List.of());

            if (base.isEmpty()) return List.of();

            timeline = base.stream()
                    .map(s -> s.getTimestamp().toEpochMilli())
                    .distinct()
                    .limit(MAX_POINTS)
                    .toList();
        } else {
            timeline = uniformTimeline(from, to, MAX_POINTS);
        }

        TelemetryStatusPageDatasource.CompositionType compType = ds.getCompositionType();
        if (compType == null) return List.of();

        List<PublicDataDtos.Point> out = new ArrayList<>(timeline.size());

        for (long t : timeline) {
            List<Double> values = new ArrayList<>(serviceIds.size());

            for (UUID serviceId : serviceIds) {
                List<TelemetryMetricSample> s = byService.get(serviceId);
                if (s == null || s.isEmpty()) {
                    values.add(null);
                    continue;
                }
                values.add(interpolateAt(s, t));
            }

            Double result = switch (compType) {
                case ADD -> combineAdd(values);
                case AVERAGE -> combineAverage(values);
            };

            out.add(new PublicDataDtos.Point(t, result));
        }

        return out;
    }

    private static Double combineAdd(List<Double> values) {
        if (values.stream().anyMatch(Objects::isNull)) return null;
        double sum = 0.0;
        for (Double v : values) sum += v;
        return sum;
    }

    private static Double combineAverage(List<Double> values) {
        if (values.stream().anyMatch(Objects::isNull)) return null;
        double sum = 0.0;
        for (Double v : values) sum += v;
        return sum / values.size();
    }

    private static List<Long> uniformTimeline(Instant from, Instant to, int points) {
        long fromMs = from.toEpochMilli();
        long toMs = to.toEpochMilli();
        if (toMs <= fromMs || points <= 0) return List.of();

        if (points == 1) return List.of(fromMs);

        long span = toMs - fromMs;
        long step = Math.max(1, span / (points - 1));

        List<Long> timeline = new ArrayList<>(points);
        long t = fromMs;
        for (int i = 0; i < points; i++) {
            timeline.add(t);
            t += step;
            if (t > toMs) t = toMs;
        }
        return timeline;
    }

    private static Double interpolateAt(List<TelemetryMetricSample> samples, long tMs) {
        int n = samples.size();
        if (n == 0) return null;

        long firstT = samples.get(0).getTimestamp().toEpochMilli();
        long lastT = samples.get(n - 1).getTimestamp().toEpochMilli();
        if (tMs < firstT || tMs > lastT) return null;

        int lo = 0;
        int hi = n - 1;

        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            long mt = samples.get(mid).getTimestamp().toEpochMilli();
            if (mt == tMs) return samples.get(mid).getValue();
            if (mt < tMs) lo = mid + 1;
            else hi = mid - 1;
        }

        int right = lo;
        int left = right - 1;

        if (left < 0 || right >= n) return null;

        TelemetryMetricSample a = samples.get(left);
        TelemetryMetricSample b = samples.get(right);

        long ta = a.getTimestamp().toEpochMilli();
        long tb = b.getTimestamp().toEpochMilli();
        if (tb == ta) return a.getValue();

        double va = a.getValue();
        double vb = b.getValue();

        double alpha = (double) (tMs - ta) / (double) (tb - ta);
        return va + alpha * (vb - va);
    }

    private static int clampInt(int v, int min, int max) {
        if (v < min) return min;
        if (v > max) return max;
        return v;
    }
}