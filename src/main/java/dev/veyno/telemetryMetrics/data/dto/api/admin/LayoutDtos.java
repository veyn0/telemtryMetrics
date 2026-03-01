package dev.veyno.telemetryMetrics.data.dto.api.admin;

import java.time.Instant;
import java.util.UUID;

public final class LayoutDtos {

    public record CreateLayoutRequest(
            UUID widgetId,
            UUID statusPageId,
            int posX,
            int posY,
            int scaleX,
            int scaleY,
            int duration,
            int graphStyle
    ) {}

    public record UpdateLayoutRequest(
            UUID widgetId,
            UUID statusPageId,
            Integer posX,
            Integer posY,
            Integer scaleX,
            Integer scaleY,
            Integer duration,
            Integer graphStyle
    ) {}

    public record LayoutResponse(
            UUID id,
            UUID widgetId,
            UUID statusPageId,
            int posX,
            int posY,
            int scaleX,
            int scaleY,
            int duration,
            int graphStyle
    ) {}
}