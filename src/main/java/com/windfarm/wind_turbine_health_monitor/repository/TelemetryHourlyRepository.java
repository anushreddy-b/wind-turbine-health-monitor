package com.windfarm.wind_turbine_health_monitor.repository;

import com.windfarm.wind_turbine_health_monitor.model.TelemetryHourly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryHourlyRepository extends JpaRepository<TelemetryHourly, Long> {
}
