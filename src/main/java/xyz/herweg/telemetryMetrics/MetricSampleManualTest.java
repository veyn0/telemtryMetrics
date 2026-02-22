package xyz.herweg.telemetryMetrics;


import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.herweg.telemetryMetrics.data.entity.MetricSample;
import xyz.herweg.telemetryMetrics.data.repository.MetricSampleRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class MetricSampleManualTest {

    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(TelemetryMetricsApplication.class);

        MetricSampleRepository repo = context.getBean(MetricSampleRepository.class);

        UUID id = UUID.randomUUID();
        UUID serviceId = UUID.randomUUID();

        MetricSample sample = new MetricSample(
                id,
                serviceId,
                Instant.now(),
                123L
        );

        repo.save(sample);

        System.out.println("Saved sample: " + id);

        var loaded = repo.findById(id);
        System.out.println("Loaded by PK: " + loaded.orElse(null));

        List<MetricSample> byService = repo.findAllByServiceId(serviceId);
        System.out.println("Loaded by serviceId count: " + byService.size());

        long now = System.currentTimeMillis();
        List<MetricSample> range = repo
                .findAllByServiceIdAndTimestampBetween(serviceId, Instant.now().minusMillis(10000), Instant.now().plusMillis(10000));

        System.out.println("Loaded in range count: " + range.size());

        context.close();
    }
}