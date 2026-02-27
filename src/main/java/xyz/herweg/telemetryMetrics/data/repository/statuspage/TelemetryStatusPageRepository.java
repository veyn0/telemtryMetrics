package xyz.herweg.telemetryMetrics.data.repository.statuspage;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.herweg.telemetryMetrics.data.entity.statuspage.TelemetryStatusPage;

import java.util.UUID;

public interface TelemetryStatusPageRepository extends JpaRepository<TelemetryStatusPage, UUID> {
}