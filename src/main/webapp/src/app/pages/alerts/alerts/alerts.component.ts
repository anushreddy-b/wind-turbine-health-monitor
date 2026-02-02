import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TelemetryService, AlertItem } from '../../../core/services/telemetry.service';

@Component({
  selector: 'app-alerts',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alerts.component.html',
  styleUrl: './alerts.component.scss'
})
export class AlertsComponent implements OnInit {
  alerts: AlertItem[] = [];

  constructor(private telemetryService: TelemetryService) {}

  ngOnInit(): void {
    this.telemetryService.getAlerts(50).subscribe(alerts => {
      this.alerts = alerts || [];
    });
  }
}
