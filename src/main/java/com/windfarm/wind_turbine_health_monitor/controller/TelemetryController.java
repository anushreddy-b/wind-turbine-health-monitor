package com.windfarm.wind_turbine_health_monitor.controller;

import com.windfarm.wind_turbine_health_monitor.model.TelemetryRaw;
import com.windfarm.wind_turbine_health_monitor.service.TelemetryService;
import org.springframework.web.bind.annotation.*;

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
}
