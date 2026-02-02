package com.windfarm.wind_turbine_health_monitor.dto;

import lombok.Data;

@Data
public class TelemetryOverviewDTO {
    private long totalTurbines;
    private long reportingTurbines;
    private long anomalyCount;
    private Double avgPower;
    private Double avgWindSpeed;
}
