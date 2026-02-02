package com.windfarm.wind_turbine_health_monitor.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "telemetry_raw")
public class TelemetryRaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer turbineId;

    private Timestamp timestamp;

    private Double windSpeed;
    private Double rotorSpeed;
    private Double powerOutput;

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getRotorSpeed() {
        return rotorSpeed;
    }

    public void setRotorSpeed(Double rotorSpeed) {
        this.rotorSpeed = rotorSpeed;
    }

    public Double getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(Double powerOutput) {
        this.powerOutput = powerOutput;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getVibration() {
        return vibration;
    }

    public void setVibration(Double vibration) {
        this.vibration = vibration;
    }

    private Double temperature;
    private Double vibration;
}
