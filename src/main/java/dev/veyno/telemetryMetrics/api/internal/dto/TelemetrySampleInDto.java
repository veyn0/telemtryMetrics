package dev.veyno.telemetryMetrics.api.internal.dto;

import java.time.Instant;

public record TelemetrySampleInDto (Instant timestamp, double value) {}