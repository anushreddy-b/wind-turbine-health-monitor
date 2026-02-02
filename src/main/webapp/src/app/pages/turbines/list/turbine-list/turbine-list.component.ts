import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TurbineService } from '../../../../core/services/turbine.service';
import { TelemetryService, TurbineStatus } from '../../../../core/services/telemetry.service';

@Component({
  selector: 'app-turbine-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './turbine-list.component.html',
  styleUrls: ['./turbine-list.component.scss']
})
export class TurbineListComponent implements OnInit {

  statusList: TurbineStatus[] = [];
  filteredList: TurbineStatus[] = [];
  regions: string[] = [];
  farms: string[] = [];
  selectedRegion = '';
  selectedFarm = '';
  formState = {
    turbineId: null as number | null,
    name: '',
    farm: '',
    region: '',
    capacityMw: null as number | null
  };
  formMessage = '';

  constructor(
    private turbineService: TurbineService,
    private telemetryService: TelemetryService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.turbineService.getRegions().subscribe(regions => {
      this.regions = regions || [];
    });
    this.turbineService.getFarms().subscribe(farms => {
      this.farms = farms || [];
    });
    this.telemetryService.getStatusList().subscribe(list => {
      this.statusList = list || [];
      this.applyFilters();
    });
  }

  applyFilters(): void {
    this.filteredList = this.statusList.filter(item => {
      const regionOk = this.selectedRegion ? item.region === this.selectedRegion : true;
      const farmOk = this.selectedFarm ? item.farm === this.selectedFarm : true;
      return regionOk && farmOk;
    });
  }

  clearFilters(): void {
    this.selectedRegion = '';
    this.selectedFarm = '';
    this.applyFilters();
  }

  startCreate(): void {
    this.formState = {
      turbineId: null,
      name: '',
      farm: '',
      region: '',
      capacityMw: null
    };
    this.formMessage = '';
  }

  startEdit(turbine: TurbineStatus): void {
    this.formState = {
      turbineId: turbine.turbineId,
      name: turbine.name || '',
      farm: turbine.farm || '',
      region: turbine.region || '',
      capacityMw: turbine.capacityMw || null
    };
    this.formMessage = '';
  }

  saveTurbine(): void {
    const payload = {
      name: this.formState.name,
      farm: this.formState.farm,
      region: this.formState.region,
      capacityMw: this.formState.capacityMw
    };

    const request = this.formState.turbineId
      ? this.turbineService.update(this.formState.turbineId, payload)
      : this.turbineService.create(payload);

    request.subscribe({
      next: () => {
        this.formMessage = this.formState.turbineId
          ? 'Turbine updated successfully.'
          : 'Turbine created successfully.';
        this.startCreate();
        this.loadData();
      },
      error: () => {
        this.formMessage = 'Unable to save turbine. Please try again.';
      }
    });
  }

  deleteTurbine(turbineId: number): void {
    this.turbineService.delete(turbineId).subscribe({
      next: () => {
        this.formMessage = 'Turbine deleted successfully.';
        this.loadData();
      },
      error: () => {
        this.formMessage = 'Unable to delete turbine.';
      }
    });
  }

  getHealthClass(health?: string): string {
    if (health === 'Anomaly') {
      return 'health-danger';
    }
    if (health === 'Stopped') {
      return 'health-warning';
    }
    if (health === 'No Data') {
      return 'health-muted';
    }
    return 'health-ok';
  }
}
