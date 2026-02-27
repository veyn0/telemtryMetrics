package xyz.herweg.telemetryMetrics.data.entity.statuspage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class TelemetryStatusPageGraph {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID datasourceId;

    private String name;

    protected TelemetryStatusPageGraph() {
    }

    public TelemetryStatusPageGraph(UUID datasourceId, String name) {
        this.datasourceId = datasourceId;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDatasourceId() {
        return datasourceId;
    }

    public String getName() {
        return name;
    }
}