package dev.veyno.telemetryMetrics.api.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import dev.veyno.telemetryMetrics.api.internal.dto.IngestTelemetrySamplesRequest;
import dev.veyno.telemetryMetrics.api.internal.dto.IngestTelemetrySamplesResponse;
import dev.veyno.telemetryMetrics.data.entity.TelemetryMetricSample;
import dev.veyno.telemetryMetrics.data.repository.TelemetryMetricSampleRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/internal")
public class InternalTelemetrySamplesController {

    private final TelemetryMetricSampleRepository sampleRepo;

    @Value("${telemetry.internal.api-key}")
    private String internalApiKey;

    public InternalTelemetrySamplesController(TelemetryMetricSampleRepository sampleRepo) {
        this.sampleRepo = sampleRepo;
    }

    @PostMapping("/samples")
    public IngestTelemetrySamplesResponse ingestSamples(
            @RequestHeader(value = "X-Api-Key", required = false) String apiKey,
            @RequestBody IngestTelemetrySamplesRequest request
    ) {
        if (apiKey == null || !apiKey.equals(internalApiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API key");
        }

        if (request == null || request.serviceId() == null || request.samples() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing serviceId or samples");
        }

        if (request.samples().isEmpty()) {
            return new IngestTelemetrySamplesResponse(0);
        }

        Instant now = Instant.now();

        List<TelemetryMetricSample> entities = new ArrayList<>(request.samples().size());
        for (var s : request.samples()) {
            if (s == null || s.timestamp() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sample timestamp missing");
            }
//            if (s.timestamp().isAfter(now.plusSeconds(300))) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sample timestamp is in the future");
//            }

            entities.add(new TelemetryMetricSample(
                    request.serviceId(),
                    s.timestamp(),
                    s.value()
            ));
        }

        sampleRepo.saveAll(entities);

        return new IngestTelemetrySamplesResponse(entities.size());
    }
}