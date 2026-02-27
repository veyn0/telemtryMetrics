package dev.veyno.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageGraph;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageGraphRepository extends JpaRepository<TelemetryStatusPageGraph, UUID> {

    List<TelemetryStatusPageGraph> findByDatasourceId(UUID datasourceId);
}