import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TurbineService {

  private api = 'http://localhost:8000/api/turbines';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.api}/${id}`);
  }

  create(payload: any): Observable<any> {
    return this.http.post<any>(this.api, payload);
  }

  update(id: number, payload: any): Observable<any> {
    return this.http.put<any>(`${this.api}/${id}`, payload);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  getRegions(): Observable<string[]> {
    return this.http.get<string[]>(`${this.api}/regions`);
  }

  getFarms(): Observable<string[]> {
    return this.http.get<string[]>(`${this.api}/farms`);
  }

  filter(region?: string, farm?: string): Observable<any[]> {
    const params = new URLSearchParams();
    if (region) {
      params.set('region', region);
    }
    if (farm) {
      params.set('farm', farm);
    }
    const query = params.toString();
    return this.http.get<any[]>(
      query ? `${this.api}/filter?${query}` : `${this.api}/filter`
    );
  }
}
