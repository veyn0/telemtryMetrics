package dev.veyno.telemetryMetrics.data.dto.api.pub;

import java.util.List;
import java.util.UUID;

public final class PublicPageDtos {

    public record PageSummary(UUID id, String name) {}

    public record PageLayoutResponse(
            PageSummary page,
            List<LayoutItem> layouts,
            List<WidgetItem> widgets,
            List<GraphItem> graphs
    ) {}

    public record LayoutItem(
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

    public record WidgetItem(
            UUID id,
            UUID graphId,
            List<UUID> tooltipGraphIds
    ) {}

    public record GraphItem(
            UUID id,
            UUID datasourceId,
            String name
    ) {}
}