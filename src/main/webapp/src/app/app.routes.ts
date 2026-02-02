import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/auth/login/login.component')
        .then(m => m.LoginComponent)
  },
  { path: '', component: DashboardComponent, canActivate: [authGuard] },

  {
    path: 'turbines',
    loadComponent: () =>
      import('./pages/turbines/list/turbine-list/turbine-list.component')
        .then(m => m.TurbineListComponent),
    canActivate: [authGuard]
  },

  {
    path: 'alerts',
    loadComponent: () =>
      import('./pages/alerts/alerts/alerts.component')
        .then(m => m.AlertsComponent),
    canActivate: [authGuard]
  },

  {
    path: 'ingest',
    loadComponent: () =>
      import('./pages/telemetry/ingest/telemetry-ingest.component')
        .then(m => m.TelemetryIngestComponent),
    canActivate: [authGuard]
  }
];

