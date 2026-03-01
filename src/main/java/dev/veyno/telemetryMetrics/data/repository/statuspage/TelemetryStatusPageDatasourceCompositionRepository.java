package dev.veyno.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageDatasourceComposition;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageDatasourceCompositionRepository
        extends JpaRepository<TelemetryStatusPageDatasourceComposition, UUID> {

    List<TelemetryStatusPageDatasourceComposition> findByDatasourceId(UUID datasourceId);

}