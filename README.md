# wind-turbine-health-monitor
Real-time Wind Turbine Health Monitoring System built with Spring Boot, enabling telemetry ingestion, anomaly detection, hourly aggregation processing, and operational visibility for large-scale wind farms.
src/main/java/com/windfarm/wind_turbine_health_monitor
│
├── WindTurbineHealthMonitorApplication.java
│
├── config
│   └── SchedulerConfig.java
│
├── controller
│   ├── TurbineController.java
│   ├── TelemetryController.java
│   └── AggregationController.java
│
├── dto
│   ├── LatestTelemetryDTO.java
│   └── ResponseMessage.java
│
├── exception
│   └── ResourceNotFoundException.java
│
├── model
│   ├── Turbine.java
│   ├── TelemetryRaw.java
│   └── TelemetryHourly.java
│
├── repository
│   ├── TurbineRepository.java
│   ├── TelemetryRawRepository.java
│   └── TelemetryHourlyRepository.java
│
├── scheduler
│   └── HourlyAggregationJob.java
│
└── service
    ├── TurbineService.java
    ├── TelemetryService.java
    └── AggregationService.java
