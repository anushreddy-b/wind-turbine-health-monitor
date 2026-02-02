package com.windfarm.wind_turbine_health_monitor.dto;

import lombok.Data;

@Data
public class LatestTelemetryDTO {
    private Double windSpeed;
    private Double rotorSpeed;
    private Double powerOutput;
    private Double temperature;
    private Double vibration;
}
