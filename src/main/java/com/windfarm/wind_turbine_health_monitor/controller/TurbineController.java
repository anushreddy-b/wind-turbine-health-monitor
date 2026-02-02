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

    @GetMapping("/{id}")
    public Turbine getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Turbine create(@RequestBody Turbine turbine) {
        return service.create(turbine);
    }

    @PutMapping("/{id}")
    public Turbine update(@PathVariable Integer id, @RequestBody Turbine turbine) {
        return service.update(id, turbine);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/filter")
    public List<Turbine> filter(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String farm
    ) {
        return service.getByRegionFarm(region, farm);
    }

    @GetMapping("/regions")
    public List<String> getRegions() {
        return service.getRegions();
    }

    @GetMapping("/farms")
    public List<String> getFarms() {
        return service.getFarms();
    }
}
