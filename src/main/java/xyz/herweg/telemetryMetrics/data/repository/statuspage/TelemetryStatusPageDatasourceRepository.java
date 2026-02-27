package xyz.herweg.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.herweg.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageDatasource;

import java.util.UUID;

public interface TelemetryStatusPageDatasourceRepository extends JpaRepository<TelemetryStatusPageDatasource, UUID> {
}