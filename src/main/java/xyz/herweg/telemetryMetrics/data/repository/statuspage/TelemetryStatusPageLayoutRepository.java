package xyz.herweg.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.herweg.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageLayout;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageLayoutRepository extends JpaRepository<TelemetryStatusPageLayout, UUID> {

    List<TelemetryStatusPageLayout> findByStatusPageId(UUID statusPageId);

    List<TelemetryStatusPageLayout> findByWidgetId(UUID widgetId);
}