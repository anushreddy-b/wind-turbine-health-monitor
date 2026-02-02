package com.windfarm.wind_turbine_health_monitor.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AlertDTO {
    private Integer turbineId;
    private String name;
    private String farm;
    private String region;
    private Timestamp hour;
    private Double avgPower;
    private Double anomalyScore;
}
