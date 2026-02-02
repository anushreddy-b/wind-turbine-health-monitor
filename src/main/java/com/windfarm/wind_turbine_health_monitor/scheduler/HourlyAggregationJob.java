package com.windfarm.wind_turbine_health_monitor.scheduler;

import com.windfarm.wind_turbine_health_monitor.repository.TurbineRepository;
import com.windfarm.wind_turbine_health_monitor.service.AggregationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HourlyAggregationJob {

    private final AggregationService aggregationService;
    private final TurbineRepository turbineRepository;

    public HourlyAggregationJob(AggregationService aggregationService, TurbineRepository turbineRepository) {
        this.aggregationService = aggregationService;
        this.turbineRepository = turbineRepository;
    }

    // Runs every hour
    @Scheduled(cron = "0 0 * * * *")
    public void processHourlyAggregation() {
        turbineRepository.findAll()
                .parallelStream()
                .forEach(turbine ->
                        aggregationService.aggregateHour(turbine.getTurbineId())
                );
    }
}
