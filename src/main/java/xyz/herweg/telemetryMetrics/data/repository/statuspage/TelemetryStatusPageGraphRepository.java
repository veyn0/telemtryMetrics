package xyz.herweg.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.herweg.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageGraph;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageGraphRepository extends JpaRepository<TelemetryStatusPageGraph, UUID> {

    List<TelemetryStatusPageGraph> findByDatasourceId(UUID datasourceId);
}