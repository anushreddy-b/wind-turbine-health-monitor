package com.windfarm.wind_turbine_health_monitor.service;

import com.windfarm.wind_turbine_health_monitor.model.TelemetryRaw;
import com.windfarm.wind_turbine_health_monitor.repository.TelemetryRawRepository;
import org.springframework.stereotype.Service;

@Service
public class TelemetryService {

    private final TelemetryRawRepository repo;

    public TelemetryService(TelemetryRawRepository repo) {
        this.repo = repo;
    }

    public TelemetryRaw getLatest(Integer turbineId) {
        return repo.findTopByTurbineIdOrderByTimestampDesc(turbineId);
    }
}
