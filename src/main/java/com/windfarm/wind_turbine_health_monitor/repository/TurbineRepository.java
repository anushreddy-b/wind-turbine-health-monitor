package com.windfarm.wind_turbine_health_monitor.repository;

import com.windfarm.wind_turbine_health_monitor.model.Turbine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurbineRepository extends JpaRepository<Turbine, Integer> {
}
