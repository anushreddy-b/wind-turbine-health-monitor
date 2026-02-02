package com.windfarm.wind_turbine_health_monitor.repository;


import com.windfarm.wind_turbine_health_monitor.model.TelemetryRaw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TelemetryRawRepository extends JpaRepository<TelemetryRaw, Long> {

    List<TelemetryRaw> findByTurbineIdAndTimestampBetween(
            Integer turbineId,
            Timestamp start,
            Timestamp end
    );

    TelemetryRaw findTopByTurbineIdOrderByTimestampDesc(Integer turbineId);
}
