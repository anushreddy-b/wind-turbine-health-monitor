package com.windfarm.wind_turbine_health_monitor.repository;

import com.windfarm.wind_turbine_health_monitor.model.Turbine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TurbineRepository extends JpaRepository<Turbine, Integer> {
    List<Turbine> findByRegionIgnoreCase(String region);

    List<Turbine> findByFarmIgnoreCase(String farm);

    List<Turbine> findByRegionIgnoreCaseAndFarmIgnoreCase(String region, String farm);

    @Query("select distinct t.region from Turbine t where t.region is not null order by t.region")
    List<String> findDistinctRegions();

    @Query("select distinct t.farm from Turbine t where t.farm is not null order by t.farm")
    List<String> findDistinctFarms();
}
