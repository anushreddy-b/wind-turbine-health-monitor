package com.windfarm.wind_turbine_health_monitor.controller;

import com.windfarm.wind_turbine_health_monitor.dto.ResponseMessage;
import com.windfarm.wind_turbine_health_monitor.service.AggregationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/process")
@CrossOrigin
public class AggregationController {

    private final AggregationService service;

    public AggregationController(AggregationService service) {
        this.service = service;
    }

    @PostMapping("/aggregate/{turbineId}")
    public ResponseMessage aggregate(@PathVariable Integer turbineId) {
        service.aggregateHour(turbineId);
        return new ResponseMessage("Hourly aggregation completed.");
    }
}