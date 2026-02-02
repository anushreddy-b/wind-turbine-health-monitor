import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TurbineStatus {
  turbineId: number;
  name: string;
  farm: string;
  region: string;
  capacityMw: number;
  timestamp: string;
  windSpeed: number;
  rotorSpeed: number;
  powerOutput: number;
  temperature: number;
  vibration: number;
  anomalyScore: number;
  health: string;
}

export interface TelemetryOverview {
  totalTurbines: number;
  reportingTurbines: number;
  anomalyCount: number;
  avgPower: number;
  avgWindSpeed: number;
}

export interface AlertItem {
  turbineId: number;
  name: string;
  farm: string;
  region: string;
  hour: string;
  avgPower: number;
  anomalyScore: number;
}

@Injectable({ providedIn: 'root' })
export class TelemetryService {

  private base = 'http://localhost:8000/api/telemetry';

  constructor(private http: HttpClient) {}

  getLatest(id: number): Observable<any> {
    return this.http.get<any>(`${this.base}/latest/${id}`);
  }

  getOverview(): Observable<TelemetryOverview> {
    return this.http.get<TelemetryOverview>(`${this.base}/overview`);
  }

  getStatusList(): Observable<TurbineStatus[]> {
    return this.http.get<TurbineStatus[]>(`${this.base}/status`);
  }

  getAlerts(limit = 20, threshold = 1): Observable<AlertItem[]> {
    return this.http.get<AlertItem[]>(
      `${this.base}/alerts?limit=${limit}&threshold=${threshold}`
    );
  }

  getHourly(turbineId: number, limit = 24): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.base}/hourly/${turbineId}?limit=${limit}`
    );
  }
}
