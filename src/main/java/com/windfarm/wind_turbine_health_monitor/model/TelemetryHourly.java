package com.windfarm.wind_turbine_health_monitor.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "telemetry_hourly")
public class TelemetryHourly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer turbineId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTurbineId() {
        return turbineId;
    }

    public void setTurbineId(Integer turbineId) {
        this.turbineId = turbineId;
    }

    public Timestamp getHour() {
        return hour;
    }

    public void setHour(Timestamp hour) {
        this.hour = hour;
    }

    public Double getAvgWindSpeed() {
        return avgWindSpeed;
    }

    public void setAvgWindSpeed(Double avgWindSpeed) {
        this.avgWindSpeed = avgWindSpeed;
    }

    public Double getAvgRotorSpeed() {
        return avgRotorSpeed;
    }

    public void setAvgRotorSpeed(Double avgRotorSpeed) {
        this.avgRotorSpeed = avgRotorSpeed;
    }

    public Double getAvgPower() {
        return avgPower;
    }

    public void setAvgPower(Double avgPower) {
        this.avgPower = avgPower;
    }

    public Double getAnomalyScore() {
        return anomalyScore;
    }

    public void setAnomalyScore(Double anomalyScore) {
        this.anomalyScore = anomalyScore;
    }

    private Timestamp hour;

    private Double avgWindSpeed;
    private Double avgRotorSpeed;
    private Double avgPower;

    private Double anomalyScore;
}