import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { TelemetryService } from '../../core/services/telemetry.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  latest: any;
  turbineId = 1;

  constructor(private telemetryService: TelemetryService) {}

  ngOnInit(): void {
    this.loadLatest();

    setInterval(() => {
      this.loadLatest();
    }, 5000);
  }

  loadLatest() {
    this.telemetryService.getLatest(this.turbineId)
      .subscribe(data => this.latest = data);
  }
}
