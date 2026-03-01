package dev.veyno.telemetryMetrics.data.dto.api.pub;

import java.util.List;
import java.util.UUID;

public final class PublicDataDtos {

    public record Point(long t, Double v) {}

    public record DataResponse(
            UUID datasourceId,
            long from,
            long to,
            int duration,
            List<Point> points
    ) {}
}