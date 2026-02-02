import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TelemetryService {

  private base = 'http://localhost:8000/api/telemetry';

  constructor(private http: HttpClient) {}

  getLatest(id: number): Observable<any> {
    return this.http.get<any>(`${this.base}/latest/${id}`);
  }
}
