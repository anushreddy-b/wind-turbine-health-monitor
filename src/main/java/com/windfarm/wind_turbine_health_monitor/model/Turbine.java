package com.windfarm.wind_turbine_health_monitor.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "turbines")
public class Turbine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer turbineId;

    private String name;
    private String farm;
    private String region;
    private Double capacityMw;

    public Integer getTurbineId() {
        return turbineId;
    }

    public void setTurbineId(Integer turbineId) {
        this.turbineId = turbineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getCapacityMw() {
        return capacityMw;
    }

    public void setCapacityMw(Double capacityMw) {
        this.capacityMw = capacityMw;
    }
}