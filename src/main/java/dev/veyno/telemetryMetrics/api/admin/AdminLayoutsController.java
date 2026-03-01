package dev.veyno.telemetryMetrics.api.admin;

import dev.veyno.telemetryMetrics.data.dto.api.admin.LayoutDtos;
import dev.veyno.telemetryMetrics.data.entity.statuspage.TelemetryStatusPageLayout;
import dev.veyno.telemetryMetrics.data.repository.statuspage.TelemetryStatusPageLayoutRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/layouts")
public class AdminLayoutsController {

    private final TelemetryStatusPageLayoutRepository repo;

    public AdminLayoutsController(TelemetryStatusPageLayoutRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<LayoutDtos.LayoutResponse> list(
            @RequestParam(required = false) UUID statusPageId,
            @RequestParam(required = false) UUID widgetId
    ) {
        List<TelemetryStatusPageLayout> rows;
        if (statusPageId != null) rows = repo.findByStatusPageId(statusPageId);
        else if (widgetId != null) rows = repo.findByWidgetId(widgetId);
        else rows = repo.findAll();

        return rows.stream()
                .map(l -> new LayoutDtos.LayoutResponse(
                        l.getId(),
                        l.getWidgetId(),
                        l.getStatusPageId(),
                        l.getPosX(),
                        l.getPosY(),
                        l.getScaleX(),
                        l.getScaleY(),
                        l.getDuration(),
                        l.getGraphStyle()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public LayoutDtos.LayoutResponse get(@PathVariable UUID id) {
        TelemetryStatusPageLayout l = repo.findById(id).orElseThrow();
        return new LayoutDtos.LayoutResponse(
                l.getId(), l.getWidgetId(), l.getStatusPageId(),
                l.getPosX(), l.getPosY(), l.getScaleX(), l.getScaleY(), l.getDuration(), l.getGraphStyle()
        );
    }

    @PostMapping
    public LayoutDtos.LayoutResponse create(@RequestBody LayoutDtos.CreateLayoutRequest req) {
        TelemetryStatusPageLayout l = repo.save(new TelemetryStatusPageLayout(
                req.widgetId(),
                req.statusPageId(),
                req.posX(),
                req.posY(),
                req.scaleX(),
                req.scaleY(),
                req.duration(),
                req.graphStyle()
        ));
        return new LayoutDtos.LayoutResponse(
                l.getId(), l.getWidgetId(), l.getStatusPageId(),
                l.getPosX(), l.getPosY(),
                l.getScaleX(), l.getScaleY(),
                l.getDuration(), l.getGraphStyle()
        );
    }

    @PatchMapping("/{id}")
    public LayoutDtos.LayoutResponse update(@PathVariable UUID id, @RequestBody LayoutDtos.UpdateLayoutRequest req) {
        TelemetryStatusPageLayout l = repo.findById(id).orElseThrow();

        if (req.widgetId() != null) l.setWidgetId(req.widgetId());
        if (req.statusPageId() != null) l.setStatusPageId(req.statusPageId());
        if (req.posX() != null) l.setPosX(req.posX());
        if (req.posY() != null) l.setPosY(req.posY());
        if (req.scaleX() != null) l.setScaleX(req.scaleX());
        if (req.scaleY() != null) l.setScaleY(req.scaleY());
        if (req.duration() != null) l.setDuration(req.duration());
        if (req.graphStyle() != null) l.setGraphStyle(req.graphStyle());

        return new LayoutDtos.LayoutResponse(
                l.getId(), l.getWidgetId(), l.getStatusPageId(),
                l.getPosX(), l.getPosY(),
                l.getScaleX(), l.getScaleY(),
                l.getDuration(), l.getGraphStyle()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repo.deleteById(id);
    }
}