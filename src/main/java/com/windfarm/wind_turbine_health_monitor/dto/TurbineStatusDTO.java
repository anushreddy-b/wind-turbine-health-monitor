package com.windfarm.wind_turbine_health_monitor.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TurbineStatusDTO {
    private Integer turbineId;
    private String name;
    private String farm;
    private String region;
    private Double capacityMw;
    private Timestamp timestamp;
    private Double windSpeed;
    private Double rotorSpeed;
    private Double powerOutput;
    private Double temperature;
    private Double vibration;
    private Double anomalyScore;
    private String health;
}
