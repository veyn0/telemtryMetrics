package dev.veyno.telemetryMetrics.api.internal.dto;

import java.util.List;
import java.util.UUID;

public record IngestTelemetrySamplesRequest (UUID serviceId, List<TelemetrySampleInDto> samples) {}