package dev.veyno.telemetryMetrics.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.TelemetrySystem;

import java.util.UUID;

public interface TelemetrySystemRepository extends JpaRepository<TelemetrySystem, UUID> {
}