import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent },

  {
    path: 'turbines',
    loadComponent: () =>
      import('./pages/turbines/list/turbine-list/turbine-list.component')
        .then(m => m.TurbineListComponent)
  },

  {
    path: 'alerts',
    loadComponent: () =>
      import('./pages/alerts/alerts/alerts.component')
        .then(m => m.AlertsComponent)
  }
];

