package dev.veyno.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.statuspage.TelemetryStatusPage;

import java.util.UUID;

public interface TelemetryStatusPageRepository extends JpaRepository<TelemetryStatusPage, UUID> {
}