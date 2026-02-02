# Wind Turbine Health Monitor

Real-time Wind Turbine Health Monitoring System built with Spring Boot and Angular. It ingests
high-frequency telemetry, runs hourly aggregation with anomaly scoring, and presents a modern
operations dashboard for fleet visibility.

## Features
- Live dashboard with KPIs and charts
- Turbine fleet management (create/edit/delete)
- High-frequency telemetry ingestion
- Hourly aggregation with anomaly detection
- Alerts center with anomaly list
- Region/farm filtering and fleet status
- Static login screen (demo user)

## Tech Stack
- Backend: Java 21, Spring Boot 3, JPA/Hibernate
- Frontend: Angular 18, Chart.js
- Database: MySQL

## Run Locally

### Backend
```
cd /home/anush/Documents/wind-turbine-health-monitor
mvn spring-boot:run
```
API: `http://localhost:8000`

### Frontend
```
cd src/main/webapp
npm install
npm start
```
UI: `http://localhost:4200`

## Demo Login
- Username: `root`
- Password: `root`

## API Endpoints

### Turbines
- `GET /api/turbines`
- `GET /api/turbines/{id}`
- `POST /api/turbines`
- `PUT /api/turbines/{id}`
- `DELETE /api/turbines/{id}`
- `GET /api/turbines/regions`
- `GET /api/turbines/farms`
- `GET /api/turbines/filter?region=&farm=`

### Telemetry
- `GET /api/telemetry/latest/{id}`
- `POST /api/telemetry/raw`
- `GET /api/telemetry/status`
- `GET /api/telemetry/overview`
- `GET /api/telemetry/hourly/{id}?limit=`
- `GET /api/telemetry/alerts?limit=&threshold=`

### Aggregation
- `POST /api/process/aggregate/{turbineId}`

## UI Routes
- `/` Dashboard
- `/turbines` Fleet management
- `/alerts` Alerts center
- `/ingest` Telemetry ingestion
- `/login` Login

## Documentation
- Project overview: `docs/Project_Overview.md`

## Backend Package Structure
`src/main/java/com/windfarm/wind_turbine_health_monitor`
```
├── WindTurbineHealthMonitorApplication.java
├── config
│   └── SchedulerConfig.java
├── controller
│   ├── TurbineController.java
│   ├── TelemetryController.java
│   └── AggregationController.java
├── dto
│   ├── LatestTelemetryDTO.java
│   ├── TelemetryOverviewDTO.java
│   ├── TurbineStatusDTO.java
│   ├── AlertDTO.java
│   └── ResponseMessage.java
├── exception
│   └── ResourceNotFoundException.java
├── model
│   ├── Turbine.java
│   ├── TelemetryRaw.java
│   └── TelemetryHourly.java
├── repository
│   ├── TurbineRepository.java
│   ├── TelemetryRawRepository.java
│   └── TelemetryHourlyRepository.java
├── scheduler
│   └── HourlyAggregationJob.java
└── service
    ├── TurbineService.java
    ├── TelemetryService.java
    └── AggregationService.java
```
