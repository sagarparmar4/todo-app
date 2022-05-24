import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginPageComponent } from './components/login-page/login-page.component';


const routes: Routes = [
  { path: 'home', component: LoginPageComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { 'useHash': true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
