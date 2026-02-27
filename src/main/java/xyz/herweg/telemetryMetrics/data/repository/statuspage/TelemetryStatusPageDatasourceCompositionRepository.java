package xyz.herweg.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.herweg.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageDatasourceComposition;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageDatasourceCompositionRepository
        extends JpaRepository<TelemetryStatusPageDatasourceComposition, UUID> {

    List<TelemetryStatusPageDatasourceComposition> findByDatasourceId(UUID datasourceId);
}