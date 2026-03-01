package dev.veyno.telemetryMetrics.data.entity.statuspage;

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

    private int scaleX;
    private int scaleY;

    private int duration;

    private int graphStyle;

    protected TelemetryStatusPageLayout() {
    }

    public TelemetryStatusPageLayout(
            UUID widgetId,
            UUID statusPageId,
            int posX,
            int posY,
            int scaleX,
            int scaleY,
            int duration,
            int graphStyle
    ) {
        this.widgetId = widgetId;
        this.statusPageId = statusPageId;
        this.posX = posX;
        this.posY = posY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.duration = duration;
        this.graphStyle = graphStyle;
    }

    public UUID getId() { return id; }
    public UUID getWidgetId() { return widgetId; }
    public UUID getStatusPageId() { return statusPageId; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getScaleX() { return scaleX; }
    public int getScaleY() { return scaleY; }
    public int getDuration() { return duration; }
    public int getGraphStyle() { return graphStyle; }

    public void setWidgetId(UUID widgetId) { this.widgetId = widgetId; }
    public void setStatusPageId(UUID statusPageId) { this.statusPageId = statusPageId; }
    public void setPosX(int posX) { this.posX = posX; }
    public void setPosY(int posY) { this.posY = posY; }
    public void setScaleX(int scaleX) { this.scaleX = scaleX; }
    public void setScaleY(int scaleY) { this.scaleY = scaleY; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setGraphStyle(int graphStyle) { this.graphStyle = graphStyle; }
}