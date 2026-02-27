package dev.veyno.telemetryMetrics.data.entity.statuspage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class TelemetryStatusPageWidget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID graphId;

    protected TelemetryStatusPageWidget() {
    }

    public TelemetryStatusPageWidget(UUID graphId) {
        this.graphId = graphId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getGraphId() {
        return graphId;
    }
}