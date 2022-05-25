import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private authToken: string = null;
  isLoggedIn: boolean = false;
  loggedinUser: any;

  constructor(
    private http: HttpClient, 
    private toastrService: ToastrService, 
    private router: Router
  ) { }

  /**
   * Validate user and fetch user context
   */
  validateUser() {
    this.http.get('/getLoggedinUser').subscribe((res) => {
      this.isLoggedIn = true;
      this.loggedinUser = res;
      this.router.navigate(['/dashboard']);
    }, () => {
      this.router.navigate(['/home']);
    });
  }

  /**
   * Logout user from application and reset necessary variables
   */
  logout(): void {
    this.http.get('/logout').subscribe((res) => {
      this.isLoggedIn = false;
      this.authToken = null;
      this.router.navigate(['home']);
    }, err => this.toastrService.error(err.message ? err.message : err));
  }

  /**
   * Helper method to create authentication token
   * @param username
   * @param password 
   */
  updateCredentials(username: string = '', password: string = '') {
    this.authToken = btoa(username + ':' + password);
  }

  /**
   * Get authentication token
   * 
   * @returns string
   */
  getAuthToken(): string {
    return this.authToken;
  }

  /**
   * Reset authentication token
   */
  resetAuthToken(): void {
    this.authToken = null;
  }

}
