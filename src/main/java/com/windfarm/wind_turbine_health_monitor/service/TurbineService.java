package com.windfarm.wind_turbine_health_monitor.service;

import com.windfarm.wind_turbine_health_monitor.model.Turbine;
import com.windfarm.wind_turbine_health_monitor.repository.TurbineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurbineService {

    private final TurbineRepository repo;

    public TurbineService(TurbineRepository repo) {
        this.repo = repo;
    }

    public List<Turbine> getAll() {
        return repo.findAll();
    }
}
