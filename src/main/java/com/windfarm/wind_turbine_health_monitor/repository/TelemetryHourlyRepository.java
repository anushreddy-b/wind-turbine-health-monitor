package com.windfarm.wind_turbine_health_monitor.repository;

import com.windfarm.wind_turbine_health_monitor.model.TelemetryHourly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryHourlyRepository extends JpaRepository<TelemetryHourly, Long> {
    TelemetryHourly findTopByTurbineIdOrderByHourDesc(Integer turbineId);

    Page<TelemetryHourly> findByTurbineIdOrderByHourDesc(Integer turbineId, Pageable pageable);

    Page<TelemetryHourly> findByAnomalyScoreGreaterThanEqualOrderByHourDesc(Double threshold, Pageable pageable);
}
