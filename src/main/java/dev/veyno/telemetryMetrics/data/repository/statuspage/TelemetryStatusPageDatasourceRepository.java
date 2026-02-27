package dev.veyno.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageDatasource;

import java.util.UUID;

public interface TelemetryStatusPageDatasourceRepository extends JpaRepository<TelemetryStatusPageDatasource, UUID> {
}