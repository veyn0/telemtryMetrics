package xyz.herweg.telemetryMetrics.data.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "metric_samples",
        indexes = {
                @Index(name = "idx_metric_samples_service_ts", columnList = "service_id,timestamp")
        }
)
public class MetricSample {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "value", nullable = false)
    private double value;

    protected MetricSample() {}

    public MetricSample(UUID id, UUID serviceId, Instant timestamp, double value) {
        this.id = id;
        this.serviceId = serviceId;
        this.timestamp = timestamp;
        this.value = value;
    }

    public UUID getId() { return id; }
    public UUID getServiceId() { return serviceId; }
    public Instant getTimestamp() { return timestamp; }
    public double getValue() { return value; }
}
