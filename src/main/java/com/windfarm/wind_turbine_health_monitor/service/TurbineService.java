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

    public Turbine getById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Turbine not found"));
    }

    public Turbine create(Turbine turbine) {
        turbine.setTurbineId(null);
        return repo.save(turbine);
    }

    public Turbine update(Integer id, Turbine turbine) {
        Turbine existing = getById(id);
        existing.setName(turbine.getName());
        existing.setFarm(turbine.getFarm());
        existing.setRegion(turbine.getRegion());
        existing.setCapacityMw(turbine.getCapacityMw());
        return repo.save(existing);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<Turbine> getByRegionFarm(String region, String farm) {
        boolean hasRegion = region != null && !region.isBlank();
        boolean hasFarm = farm != null && !farm.isBlank();

        if (hasRegion && hasFarm) {
            return repo.findByRegionIgnoreCaseAndFarmIgnoreCase(region, farm);
        }
        if (hasRegion) {
            return repo.findByRegionIgnoreCase(region);
        }
        if (hasFarm) {
            return repo.findByFarmIgnoreCase(farm);
        }
        return repo.findAll();
    }

    public List<String> getRegions() {
        return repo.findDistinctRegions();
    }

    public List<String> getFarms() {
        return repo.findDistinctFarms();
    }
}
