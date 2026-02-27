package xyz.herweg.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.herweg.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageWidget;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageWidgetRepository extends JpaRepository<TelemetryStatusPageWidget, UUID> {

    List<TelemetryStatusPageWidget> findByGraphId(UUID graphId);
}