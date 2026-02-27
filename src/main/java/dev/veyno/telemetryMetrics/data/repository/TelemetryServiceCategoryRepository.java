package dev.veyno.telemetryMetrics.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.TelemetryServiceCategory;

import java.util.UUID;

public interface TelemetryServiceCategoryRepository extends JpaRepository<TelemetryServiceCategory, UUID> {
}