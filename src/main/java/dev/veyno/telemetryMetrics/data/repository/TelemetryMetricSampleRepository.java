package dev.veyno.telemetryMetrics.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.veyno.telemetryMetrics.data.entity.TelemetryMetricSample;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TelemetryMetricSampleRepository extends JpaRepository<TelemetryMetricSample, UUID> {

    List<TelemetryMetricSample> findAllByServiceId(UUID serviceId);

    List<TelemetryMetricSample> findAllByServiceIdAndTimestampBetween(UUID serviceId, Instant from, Instant to);
}