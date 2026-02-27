package dev.veyno.telemetryMetrics.data.entity.statuspage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class TelemetryStatusPageWidgetTooltip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID widgetId;
    private UUID graphId;

    protected TelemetryStatusPageWidgetTooltip() {
    }

    public TelemetryStatusPageWidgetTooltip(UUID widgetId, UUID graphId) {
        this.widgetId = widgetId;
        this.graphId = graphId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getWidgetId() {
        return widgetId;
    }

    public UUID getGraphId() {
        return graphId;
    }
}