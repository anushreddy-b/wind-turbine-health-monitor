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
}