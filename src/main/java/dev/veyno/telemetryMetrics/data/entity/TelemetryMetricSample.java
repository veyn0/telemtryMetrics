package dev.veyno.telemetryMetrics.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public class TelemetryMetricSample {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID serviceId;

    private Instant timestamp;

    private double value;

    protected TelemetryMetricSample() {
    }

    public TelemetryMetricSample(UUID serviceId, Instant timestamp, double value) {
        this.serviceId = serviceId;
        this.timestamp = timestamp;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }
}