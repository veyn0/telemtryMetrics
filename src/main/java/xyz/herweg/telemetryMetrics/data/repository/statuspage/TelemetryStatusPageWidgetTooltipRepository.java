package xyz.herweg.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.herweg.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageWidgetTooltip;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageWidgetTooltipRepository extends JpaRepository<TelemetryStatusPageWidgetTooltip, UUID> {

    List<TelemetryStatusPageWidgetTooltip> findByWidgetId(UUID widgetId);

    List<TelemetryStatusPageWidgetTooltip> findByGraphId(UUID graphId);
}