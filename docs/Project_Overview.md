# Wind Turbine Health Monitoring System

## 1. Overview
This project provides a real-time health monitoring platform for large wind farms. It ingests
high-frequency telemetry, aggregates it hourly, detects anomalies, and surfaces the results
in a modern operations dashboard for engineers, supervisors, and analysts.

## 2. Business Purpose
Wind turbines generate telemetry every 10 seconds. Monitoring thousands of turbines manually
is impractical. This system automates:
- Health tracking and anomaly detection
- Fleet visibility and regional analytics
- Predictive maintenance indicators

## 3. Key Features
- Real-time turbine health dashboard
- High-frequency telemetry ingestion
- Hourly aggregation with anomaly scoring
- Region/farm filtering and fleet status
- REST API architecture
- Container-friendly deployment model

## 4. User Roles & Stories
- Operations Engineer: live status and alerts
- Plant Supervisor: region/farm filtering
- Analyst: hourly trends and historical patterns
- Maintenance Planner: daily generation metrics
- Backend Engineer: hourly aggregation jobs
- Platform Engineer: parallel processing and scalability

## 5. System Architecture
The platform uses a layered architecture:
1. **Frontend**: Angular single-page app
2. **REST API**: Spring Boot controllers
3. **Service Layer**: business logic and aggregation
4. **Data Layer**: MySQL persistence
5. **Schedulers**: hourly aggregation jobs

## 6. Database Design
Tables:
- `turbines`: turbine metadata (name, region, farm, capacity)
- `telemetry_raw`: 10‑second telemetry records
- `telemetry_hourly`: hourly aggregates with anomaly scores

## 7. API Surface (Current)
Turbines:
- `GET /api/turbines`
- `GET /api/turbines/{id}`
- `POST /api/turbines`
- `PUT /api/turbines/{id}`
- `DELETE /api/turbines/{id}`
- `GET /api/turbines/regions`
- `GET /api/turbines/farms`
- `GET /api/turbines/filter?region=&farm=`

Telemetry:
- `GET /api/telemetry/latest/{id}`
- `POST /api/telemetry/raw`
- `GET /api/telemetry/status`
- `GET /api/telemetry/overview`
- `GET /api/telemetry/hourly/{id}?limit=`
- `GET /api/telemetry/alerts?limit=&threshold=`

Aggregation:
- `POST /api/process/aggregate/{turbineId}`

## 8. Telemetry Data Flow
1. Raw telemetry arrives every 10 seconds into `telemetry_raw`
2. Latest values are served immediately to the UI
3. Scheduler aggregates hourly data
4. Anomaly detection is applied
5. Aggregated records stored in `telemetry_hourly`

## 9. Anomaly Detection
Rule-based scoring:
- If `avgPower < 30` → anomaly = 1
- Else → anomaly = 0

## 10. Scheduler
Hourly aggregation job (Spring Scheduler):
- CRON: `0 0 * * * *`
- Processes turbines in parallel for scalability

## 11. Technology Stack
Backend:
- Java 21
- Spring Boot 3+
- JPA/Hibernate

Frontend:
- Angular 18
- Chart.js for trend visualization

Database:
- MySQL

DevOps:
- Docker / Docker Compose

## 12. How to Run
Backend:
```
mvn spring-boot:run
```

Frontend:
```
cd src/main/webapp
npm install
npm start
```

## 13. UI Highlights
- Live dashboard with KPIs
- Turbine fleet cards with health status
- Alerts center for anomalies
- Telemetry ingestion page
- Login screen (static user)

## 14. Future Enhancements
- ML-based anomaly prediction
- Role-based access control
- Historical trend analytics dashboards
- Export and reporting
