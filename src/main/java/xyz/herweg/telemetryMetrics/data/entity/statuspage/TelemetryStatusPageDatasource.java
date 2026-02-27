package xyz.herweg.telemetryMetrics.data.entity.statuspage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class TelemetryStatusPageDatasource {

    public enum Type {
        RAW,
        COMPOSED
    }

    public enum CompositionType {
        ADD,
        AVERAGE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Type type;

    private UUID serviceId;

    private CompositionType compositionType;

    protected TelemetryStatusPageDatasource() {
    }

    public TelemetryStatusPageDatasource(Type type, UUID serviceId, CompositionType compositionType) {
        this.type = type;
        this.serviceId = serviceId;
        this.compositionType = compositionType;
    }

    public UUID getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public CompositionType getCompositionType() {
        return compositionType;
    }
}