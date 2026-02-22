package xyz.herweg.telemetryMetrics.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Service {


    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private UUID systemId;

    @Column
    private UUID categoryId;

    @Column
    private String name;

    @Column
    private Instant createdAt;


    public Service() {
    }

    public Service(UUID id, UUID systemId, UUID categoryId, String name, Instant createdAt) {
        this.id = id;
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
