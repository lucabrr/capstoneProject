import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { DashboardGuard } from './guards/dashboard.guard';
import { ClientPageComponent } from './pages/client-page/client-page.component';
import { FindClientComponent } from './pages/find-client/find-client.component';


const routes: Routes = [
  {
  path: '',
  component: LoginPageComponent,
  pathMatch: 'full',
  },
  {
  path:"dashboard",
  component: DashboardPageComponent,
  canActivate: [DashboardGuard]
  },

  {
    path:"client",
    component: ClientPageComponent,
    canActivate: [DashboardGuard]

    },
    {
      path:"client/:id",
      component: ClientPageComponent,
      canActivate: [DashboardGuard]

      },
    {
      path:"findClient",
      component: FindClientComponent,
      canActivate: [DashboardGuard]

      },
      {
        path:"findClient/:path",
        component: FindClientComponent,
        canActivate: [DashboardGuard]

        }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
