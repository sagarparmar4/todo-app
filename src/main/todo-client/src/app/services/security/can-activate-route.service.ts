import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SecurityService } from './security.service';

@Injectable({
  providedIn: 'root'
})
export class CanActivateRouteService implements CanActivate {
  
  constructor(
    private securityService: SecurityService, 
    private toastrService: ToastrService,
    private router: Router
  ) { }
  
  /**
   * Validate if user can access angular routes
   * 
   * @returns boolean
   */
  canActivate(): boolean {
    if(this.securityService.isLoggedIn) {
      return true;
    }
    this.toastrService.error("Please sign in to continue");
    this.router.navigate(['/home']);
    return false;
  }
  
}
