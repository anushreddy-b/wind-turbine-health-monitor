import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Chart } from 'chart.js/auto';
import {
  AlertItem,
  TelemetryOverview,
  TelemetryService,
  TurbineStatus
} from '../../core/services/telemetry.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('trendChart') trendChart?: ElementRef<HTMLCanvasElement>;

  overview: TelemetryOverview | null = null;
  statusList: TurbineStatus[] = [];
  alerts: AlertItem[] = [];
  hourlyData: any[] = [];
  selectedTurbineId: number | null = null;
  refreshHandle: number | null = null;
  trendChartInstance: Chart | null = null;

  constructor(private telemetryService: TelemetryService) {}

  ngOnInit(): void {
    this.refreshDashboard();
    this.refreshHandle = window.setInterval(() => {
      this.refreshDashboard();
    }, 8000);
  }

  ngAfterViewInit(): void {
    if (this.hourlyData.length > 0) {
      this.renderTrendChart();
    }
  }

  ngOnDestroy(): void {
    if (this.refreshHandle !== null) {
      window.clearInterval(this.refreshHandle);
    }
  }

  refreshDashboard(): void {
    forkJoin({
      overview: this.telemetryService.getOverview(),
      status: this.telemetryService.getStatusList(),
      alerts: this.telemetryService.getAlerts(6)
    }).subscribe(({ overview, status, alerts }) => {
      this.overview = overview;
      this.statusList = status || [];
      this.alerts = alerts || [];

      if (!this.selectedTurbineId && this.statusList.length > 0) {
        this.selectedTurbineId = this.statusList[0].turbineId;
        this.loadHourly(this.selectedTurbineId);
      } else if (this.selectedTurbineId) {
        this.loadHourly(this.selectedTurbineId);
      }
    });
  }

  get selectedStatus(): TurbineStatus | undefined {
    return this.statusList.find(item => item.turbineId === this.selectedTurbineId);
  }

  trackById(_: number, item: TurbineStatus): number {
    return item.turbineId;
  }

  onTurbineChange(turbineId: number | null): void {
    if (turbineId) {
      this.loadHourly(turbineId);
    }
  }

  getHealthClass(status?: TurbineStatus): string {
    if (!status) {
      return 'health-muted';
    }
    if (status.health === 'Anomaly') {
      return 'health-danger';
    }
    if (status.health === 'Stopped') {
      return 'health-warning';
    }
    if (status.health === 'No Data') {
      return 'health-muted';
    }
    return 'health-ok';
  }

  private loadHourly(turbineId: number): void {
    this.telemetryService.getHourly(turbineId, 24).subscribe(data => {
      this.hourlyData = (data || []).reverse();
      this.renderTrendChart();
    });
  }

  private renderTrendChart(): void {
    if (!this.trendChart?.nativeElement) {
      return;
    }
    if (this.trendChartInstance) {
      this.trendChartInstance.destroy();
    }

    const labels = this.hourlyData.map(item => {
      const date = item.hour ? new Date(item.hour) : null;
      return date ? date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) : '';
    });

    this.trendChartInstance = new Chart(this.trendChart.nativeElement, {
      type: 'line',
      data: {
        labels,
        datasets: [
          {
            label: 'Avg Power (kW)',
            data: this.hourlyData.map(item => item.avgPower ?? 0),
            borderColor: '#2563eb',
            backgroundColor: 'rgba(37, 99, 235, 0.15)',
            tension: 0.3,
            fill: true
          },
          {
            label: 'Avg Wind (m/s)',
            data: this.hourlyData.map(item => item.avgWindSpeed ?? 0),
            borderColor: '#0ea5e9',
            backgroundColor: 'rgba(14, 165, 233, 0.1)',
            tension: 0.3,
            fill: false
          },
          {
            label: 'Avg Rotor (rpm)',
            data: this.hourlyData.map(item => item.avgRotorSpeed ?? 0),
            borderColor: '#14b8a6',
            backgroundColor: 'rgba(20, 184, 166, 0.1)',
            tension: 0.3,
            fill: false
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'top'
          }
        },
        scales: {
          y: {
            grid: {
              color: 'rgba(148, 163, 184, 0.2)'
            }
          },
          x: {
            grid: {
              display: false
            }
          }
        }
      }
    });
  }
}
