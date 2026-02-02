package com.windfarm.wind_turbine_health_monitor.controller;

import com.windfarm.wind_turbine_health_monitor.model.Turbine;
import com.windfarm.wind_turbine_health_monitor.service.TurbineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turbines")
@CrossOrigin
public class TurbineController {

    private final TurbineService service;

    public TurbineController(TurbineService service) {
        this.service = service;
    }

    @GetMapping
    public List<Turbine> getAll() {
        return service.getAll();
    }
}
