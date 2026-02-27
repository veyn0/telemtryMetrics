package xyz.herweg.telemetryMetrics.data.entity.statuspage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public class TelemetryStatusPageLayout {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID widgetId;
    private UUID statusPageId;

    private int posX;
    private int posY;

    private Instant fromTime;
    private Instant toTime;

    private int graphStyle;

    protected TelemetryStatusPageLayout() {
    }

    public TelemetryStatusPageLayout(
            UUID widgetId,
            UUID statusPageId,
            int posX,
            int posY,
            Instant fromTime,
            Instant toTime,
            int graphStyle
    ) {
        this.widgetId = widgetId;
        this.statusPageId = statusPageId;
        this.posX = posX;
        this.posY = posY;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.graphStyle = graphStyle;
    }

    public UUID getId() {
        return id;
    }

    public UUID getWidgetId() {
        return widgetId;
    }

    public UUID getStatusPageId() {
        return statusPageId;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public Instant getToTime() {
        return toTime;
    }

    public int getGraphStyle() {
        return graphStyle;
    }
}