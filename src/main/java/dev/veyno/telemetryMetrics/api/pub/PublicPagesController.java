package dev.veyno.telemetryMetrics.api.pub;


import dev.veyno.telemetryMetrics.data.dto.api.pub.PublicPageDtos;
import dev.veyno.telemetryMetrics.data.repository.statuspage.TelemetryStatusPageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/pages")
public class PublicPagesController {

    private final TelemetryStatusPageRepository statusPageRepo;

    public PublicPagesController(TelemetryStatusPageRepository statusPageRepo) {
        this.statusPageRepo = statusPageRepo;
    }

    @GetMapping
    public List<PublicPageDtos.PageSummary> listPages() {
        return statusPageRepo.findAll().stream()
                .map(p -> new PublicPageDtos.PageSummary(p.getId(), p.getName()))
                .toList();
    }
}