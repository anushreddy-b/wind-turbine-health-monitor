package com.windfarm.wind_turbine_health_monitor.service;

import com.windfarm.wind_turbine_health_monitor.model.TelemetryHourly;
import com.windfarm.wind_turbine_health_monitor.model.TelemetryRaw;
import com.windfarm.wind_turbine_health_monitor.repository.TelemetryHourlyRepository;
import com.windfarm.wind_turbine_health_monitor.repository.TelemetryRawRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class AggregationService {

    private final TelemetryRawRepository rawRepo;
    private final TelemetryHourlyRepository hourlyRepo;

    public AggregationService(TelemetryRawRepository rawRepo, TelemetryHourlyRepository hourlyRepo) {
        this.rawRepo = rawRepo;
        this.hourlyRepo = hourlyRepo;
    }

    public void aggregateHour(Integer turbineId) {

        Timestamp start = Timestamp.valueOf(LocalDateTime.now().minusHours(1));
        Timestamp end = Timestamp.valueOf(LocalDateTime.now());

        List<TelemetryRaw> list =
                rawRepo.findByTurbineIdAndTimestampBetween(turbineId, start, end);

        double avgPower = list.stream().mapToDouble(TelemetryRaw::getPowerOutput).average().orElse(0);
        double avgWind = list.stream().mapToDouble(TelemetryRaw::getWindSpeed).average().orElse(0);
        double avgRotor = list.stream().mapToDouble(TelemetryRaw::getRotorSpeed).average().orElse(0);

        // Basic rule-based anomaly detection
        double anomalyScore = avgPower < 30 ? 1 : 0;

        TelemetryHourly hourly = new TelemetryHourly();
        hourly.setTurbineId(turbineId);
        hourly.setHour(start);
        hourly.setAvgWindSpeed(avgWind);
        hourly.setAvgRotorSpeed(avgRotor);
        hourly.setAvgPower(avgPower);
        hourly.setAnomalyScore(anomalyScore);

        hourlyRepo.save(hourly);
    }
}

