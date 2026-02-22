package xyz.herweg.telemetryMetrics.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.herweg.telemetryMetrics.data.entity.MetricSample;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface MetricSampleRepository extends JpaRepository<MetricSample, UUID> {

    List<MetricSample> findAllByServiceId(UUID serviceId);

    List<MetricSample> findAllByServiceIdAndTimestampBetween(UUID serviceId, Instant fromTs, Instant toTs);
}