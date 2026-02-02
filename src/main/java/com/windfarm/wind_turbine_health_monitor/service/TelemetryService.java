package com.windfarm.wind_turbine_health_monitor.service;

import com.windfarm.wind_turbine_health_monitor.dto.AlertDTO;
import com.windfarm.wind_turbine_health_monitor.dto.TelemetryOverviewDTO;
import com.windfarm.wind_turbine_health_monitor.dto.TurbineStatusDTO;
import com.windfarm.wind_turbine_health_monitor.model.TelemetryHourly;
import com.windfarm.wind_turbine_health_monitor.model.TelemetryRaw;
import com.windfarm.wind_turbine_health_monitor.model.Turbine;
import com.windfarm.wind_turbine_health_monitor.repository.TelemetryHourlyRepository;
import com.windfarm.wind_turbine_health_monitor.repository.TelemetryRawRepository;
import com.windfarm.wind_turbine_health_monitor.repository.TurbineRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class TelemetryService {

    private final TelemetryRawRepository repo;
    private final TelemetryHourlyRepository hourlyRepo;
    private final TurbineRepository turbineRepo;

    public TelemetryService(
            TelemetryRawRepository repo,
            TelemetryHourlyRepository hourlyRepo,
            TurbineRepository turbineRepo
    ) {
        this.repo = repo;
        this.hourlyRepo = hourlyRepo;
        this.turbineRepo = turbineRepo;
    }

    public TelemetryRaw getLatest(Integer turbineId) {
        return repo.findTopByTurbineIdOrderByTimestampDesc(turbineId);
    }

    public TelemetryRaw ingest(TelemetryRaw telemetry) {
        return repo.save(telemetry);
    }

    public List<TurbineStatusDTO> getStatusList() {
        List<Turbine> turbines = turbineRepo.findAll();
        List<TurbineStatusDTO> result = new ArrayList<>();

        for (Turbine turbine : turbines) {
            TelemetryRaw latest = repo.findTopByTurbineIdOrderByTimestampDesc(turbine.getTurbineId());
            TelemetryHourly latestHourly = hourlyRepo.findTopByTurbineIdOrderByHourDesc(turbine.getTurbineId());

            TurbineStatusDTO dto = new TurbineStatusDTO();
            dto.setTurbineId(turbine.getTurbineId());
            dto.setName(turbine.getName());
            dto.setFarm(turbine.getFarm());
            dto.setRegion(turbine.getRegion());
            dto.setCapacityMw(turbine.getCapacityMw());

            if (latest != null) {
                dto.setTimestamp(latest.getTimestamp());
                dto.setWindSpeed(latest.getWindSpeed());
                dto.setRotorSpeed(latest.getRotorSpeed());
                dto.setPowerOutput(latest.getPowerOutput());
                dto.setTemperature(latest.getTemperature());
                dto.setVibration(latest.getVibration());
            }

            Double anomalyScore = latestHourly != null ? latestHourly.getAnomalyScore() : null;
            if (anomalyScore == null && latest != null && latest.getPowerOutput() != null) {
                anomalyScore = latest.getPowerOutput() < 30 ? 1d : 0d;
            }
            dto.setAnomalyScore(anomalyScore);
            dto.setHealth(resolveHealth(latest, anomalyScore));

            result.add(dto);
        }

        result.sort(Comparator.comparing(TurbineStatusDTO::getTurbineId));
        return result;
    }

    public TelemetryOverviewDTO getOverview() {
        List<TurbineStatusDTO> statuses = getStatusList();
        TelemetryOverviewDTO overview = new TelemetryOverviewDTO();
        overview.setTotalTurbines(turbineRepo.count());
        overview.setReportingTurbines(statuses.stream().filter(s -> s.getTimestamp() != null).count());
        overview.setAnomalyCount(statuses.stream()
                .filter(s -> s.getAnomalyScore() != null && s.getAnomalyScore() >= 1)
                .count());

        Double avgPower = statuses.stream()
                .map(TurbineStatusDTO::getPowerOutput)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0d);
        overview.setAvgPower(avgPower);

        Double avgWind = statuses.stream()
                .map(TurbineStatusDTO::getWindSpeed)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0d);
        overview.setAvgWindSpeed(avgWind);

        return overview;
    }

    public List<TelemetryHourly> getHourlyHistory(Integer turbineId, int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 168));
        return hourlyRepo.findByTurbineIdOrderByHourDesc(turbineId, PageRequest.of(0, safeLimit)).getContent();
    }

    public List<AlertDTO> getAlerts(double threshold, int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 200));
        List<TelemetryHourly> alerts = hourlyRepo
                .findByAnomalyScoreGreaterThanEqualOrderByHourDesc(threshold, PageRequest.of(0, safeLimit))
                .getContent();

        List<AlertDTO> result = new ArrayList<>();
        for (TelemetryHourly hourly : alerts) {
            Turbine turbine = turbineRepo.findById(hourly.getTurbineId()).orElse(null);
            AlertDTO dto = new AlertDTO();
            dto.setTurbineId(hourly.getTurbineId());
            if (turbine != null) {
                dto.setName(turbine.getName());
                dto.setFarm(turbine.getFarm());
                dto.setRegion(turbine.getRegion());
            }
            dto.setHour(hourly.getHour());
            dto.setAvgPower(hourly.getAvgPower());
            dto.setAnomalyScore(hourly.getAnomalyScore());
            result.add(dto);
        }

        return result;
    }

    private String resolveHealth(TelemetryRaw latest, Double anomalyScore) {
        if (latest == null) {
            return "No Data";
        }
        if (anomalyScore != null && anomalyScore >= 1) {
            return "Anomaly";
        }
        if (latest.getPowerOutput() != null && latest.getPowerOutput() <= 0) {
            return "Stopped";
        }
        return "Normal";
    }
}
