package dev.veyno.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageWidgetTooltip;

import java.util.List;
import java.util.UUID;

public interface TelemetryStatusPageWidgetTooltipRepository extends JpaRepository<TelemetryStatusPageWidgetTooltip, UUID> {

    List<TelemetryStatusPageWidgetTooltip> findByWidgetId(UUID widgetId);

    List<TelemetryStatusPageWidgetTooltip> findByGraphId(UUID graphId);

    List<TelemetryStatusPageWidgetTooltip> findByWidgetIdIn(List<UUID> widgetIds);
}