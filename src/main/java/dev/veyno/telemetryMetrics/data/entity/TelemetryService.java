package dev.veyno.telemetryMetrics.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public class TelemetryService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID systemId;
    private UUID categoryId;

    private String name;
    private Instant createdAt;

    protected TelemetryService() {
    }

    public TelemetryService(UUID systemId, UUID categoryId, String name, Instant createdAt) {
        this.systemId = systemId;
        this.categoryId = categoryId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSystemId() {
        return systemId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}