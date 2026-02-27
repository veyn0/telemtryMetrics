package dev.veyno.telemetryMetrics.data.entity.statuspage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class TelemetryStatusPageDatasourceComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID datasourceId;
    private UUID serviceId;

    protected TelemetryStatusPageDatasourceComposition() {
    }

    public TelemetryStatusPageDatasourceComposition(UUID datasourceId, UUID serviceId) {
        this.datasourceId = datasourceId;
        this.serviceId = serviceId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDatasourceId() {
        return datasourceId;
    }

    public UUID getServiceId() {
        return serviceId;
    }
}