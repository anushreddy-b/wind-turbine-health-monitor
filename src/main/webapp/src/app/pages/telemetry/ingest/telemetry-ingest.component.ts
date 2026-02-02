import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TelemetryService } from '../../../core/services/telemetry.service';

@Component({
  selector: 'app-telemetry-ingest',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './telemetry-ingest.component.html',
  styleUrls: ['./telemetry-ingest.component.scss']
})
export class TelemetryIngestComponent {
  payload = {
    turbineId: null as number | null,
    timestamp: '',
    windSpeed: null as number | null,
    rotorSpeed: null as number | null,
    powerOutput: null as number | null,
    temperature: null as number | null,
    vibration: null as number | null
  };
  message = '';

  constructor(private telemetryService: TelemetryService) {}

  submit(): void {
    if (!this.payload.turbineId) {
      this.message = 'Turbine ID is required.';
      return;
    }

    const body = {
      ...this.payload,
      timestamp: this.payload.timestamp ? new Date(this.payload.timestamp).toISOString() : null
    };

    this.telemetryService.ingestRaw(body).subscribe({
      next: () => {
        this.message = 'Telemetry ingested successfully.';
        this.payload = {
          turbineId: null,
          timestamp: '',
          windSpeed: null,
          rotorSpeed: null,
          powerOutput: null,
          temperature: null,
          vibration: null
        };
      },
      error: () => {
        this.message = 'Failed to ingest telemetry.';
      }
    });
  }
}
