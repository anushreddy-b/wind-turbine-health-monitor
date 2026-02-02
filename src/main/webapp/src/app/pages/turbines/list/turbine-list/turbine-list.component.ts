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
