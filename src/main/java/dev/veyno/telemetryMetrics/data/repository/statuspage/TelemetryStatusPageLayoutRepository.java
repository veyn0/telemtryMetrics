package dev.veyno.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageLayout;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageLayoutRepository extends JpaRepository<TelemetryStatusPageLayout, UUID> {

    List<TelemetryStatusPageLayout> findByStatusPageId(UUID statusPageId);

    List<TelemetryStatusPageLayout> findByWidgetId(UUID widgetId);
}