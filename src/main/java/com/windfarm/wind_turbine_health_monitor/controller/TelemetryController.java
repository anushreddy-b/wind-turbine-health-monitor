package com.windfarm.wind_turbine_health_monitor.controller;

import com.windfarm.wind_turbine_health_monitor.dto.AlertDTO;
import com.windfarm.wind_turbine_health_monitor.dto.TelemetryOverviewDTO;
import com.windfarm.wind_turbine_health_monitor.dto.TurbineStatusDTO;
import com.windfarm.wind_turbine_health_monitor.model.TelemetryHourly;
import com.windfarm.wind_turbine_health_monitor.model.TelemetryRaw;
import com.windfarm.wind_turbine_health_monitor.service.TelemetryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
@CrossOrigin
public class TelemetryController {

    private final TelemetryService service;

    public TelemetryController(TelemetryService service) {
        this.service = service;
    }

    @GetMapping("/latest/{id}")
    public TelemetryRaw getLatest(@PathVariable Integer id) {
        return service.getLatest(id);
    }

    @PostMapping("/raw")
    public TelemetryRaw ingest(@RequestBody TelemetryRaw telemetry) {
        return service.ingest(telemetry);
    }

    @GetMapping("/status")
    public List<TurbineStatusDTO> getStatus() {
        return service.getStatusList();
    }

    @GetMapping("/overview")
    public TelemetryOverviewDTO getOverview() {
        return service.getOverview();
    }

    @GetMapping("/hourly/{id}")
    public List<TelemetryHourly> getHourly(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "24") int limit
    ) {
        return service.getHourlyHistory(id, limit);
    }

    @GetMapping("/alerts")
    public List<AlertDTO> getAlerts(
            @RequestParam(defaultValue = "1") double threshold,
            @RequestParam(defaultValue = "20") int limit
    ) {
        return service.getAlerts(threshold, limit);
    }
}
