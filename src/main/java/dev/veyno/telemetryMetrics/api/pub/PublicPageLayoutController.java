package dev.veyno.telemetryMetrics.api.pub;

import dev.veyno.telemetryMetrics.data.dto.api.pub.PublicPageDtos;
import dev.veyno.telemetryMetrics.data.entity.statuspage.*;
import dev.veyno.telemetryMetrics.data.repository.statuspage.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/pages")
public class PublicPageLayoutController {

    private final TelemetryStatusPageRepository statusPageRepo;
    private final TelemetryStatusPageLayoutRepository layoutRepo;
    private final TelemetryStatusPageWidgetRepository widgetRepo;
    private final TelemetryStatusPageWidgetTooltipRepository tooltipRepo;
    private final TelemetryStatusPageGraphRepository graphRepo;

    public PublicPageLayoutController(
            TelemetryStatusPageRepository statusPageRepo,
            TelemetryStatusPageLayoutRepository layoutRepo,
            TelemetryStatusPageWidgetRepository widgetRepo,
            TelemetryStatusPageWidgetTooltipRepository tooltipRepo,
            TelemetryStatusPageGraphRepository graphRepo
    ) {
        this.statusPageRepo = statusPageRepo;
        this.layoutRepo = layoutRepo;
        this.widgetRepo = widgetRepo;
        this.tooltipRepo = tooltipRepo;
        this.graphRepo = graphRepo;
    }

    @GetMapping("/{statusPageId}/layout")
    public PublicPageDtos.PageLayoutResponse getLayout(@PathVariable UUID statusPageId) {

        TelemetryStatusPage page = statusPageRepo.findById(statusPageId).orElseThrow();

        List<TelemetryStatusPageLayout> layouts = layoutRepo.findByStatusPageId(statusPageId);

        Set<UUID> widgetIds = layouts.stream()
                .map(TelemetryStatusPageLayout::getWidgetId)
                .collect(Collectors.toSet());

        List<TelemetryStatusPageWidget> widgets = widgetIds.isEmpty()
                ? List.of()
                : widgetRepo.findAllById(widgetIds);

        Set<UUID> graphIds = widgets.stream()
                .map(TelemetryStatusPageWidget::getGraphId)
                .collect(Collectors.toSet());

        List<TelemetryStatusPageWidgetTooltip> tooltips = widgetIds.isEmpty()
                ? List.of()
                : tooltipRepo.findByWidgetIdIn(new ArrayList<>(widgetIds));

        tooltips.forEach(t -> graphIds.add(t.getGraphId()));

        List<TelemetryStatusPageGraph> graphs = graphIds.isEmpty()
                ? List.of()
                : graphRepo.findAllById(graphIds);

        Map<UUID, List<UUID>> tooltipGraphIdsByWidget = tooltips.stream()
                .collect(Collectors.groupingBy(
                        TelemetryStatusPageWidgetTooltip::getWidgetId,
                        Collectors.mapping(TelemetryStatusPageWidgetTooltip::getGraphId, Collectors.toList())
                ));

        List<PublicPageDtos.LayoutItem> layoutItems = layouts.stream()
                .map(l -> new PublicPageDtos.LayoutItem(
                        l.getId(),
                        l.getWidgetId(),
                        l.getStatusPageId(),
                        l.getPosX(),
                        l.getPosY(),
                        l.getScaleX(),
                        l.getScaleY(),
                        l.getDuration(),
                        l.getGraphStyle()
                ))
                .toList();

        List<PublicPageDtos.WidgetItem> widgetItems = widgets.stream()
                .map(w -> new PublicPageDtos.WidgetItem(
                        w.getId(),
                        w.getGraphId(),
                        tooltipGraphIdsByWidget.getOrDefault(w.getId(), List.of())
                ))
                .toList();

        List<PublicPageDtos.GraphItem> graphItems = graphs.stream()
                .map(g -> new PublicPageDtos.GraphItem(
                        g.getId(),
                        g.getDatasourceId(),
                        g.getName()
                ))
                .toList();

        return new PublicPageDtos.PageLayoutResponse(
                new PublicPageDtos.PageSummary(page.getId(), page.getName()),
                layoutItems,
                widgetItems,
                graphItems
        );
    }
}