import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TurbineService } from '../../../../core/services/turbine.service';

@Component({
  selector: 'app-turbine-list',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './turbine-list.component.html',
  styleUrls: ['./turbine-list.component.scss']
})
export class TurbineListComponent implements OnInit {

  turbines: any[] = [];

  constructor(private turbineService: TurbineService) {}

  ngOnInit(): void {
    this.loadTurbines();
  }

  loadTurbines(): void {
    this.turbineService.getAll().subscribe({
      next: (data: any[]) => {
        this.turbines = data;
      },
      error: (err: any) => {
        console.error('Error loading turbines:', err);
      }
    });
  }
}
