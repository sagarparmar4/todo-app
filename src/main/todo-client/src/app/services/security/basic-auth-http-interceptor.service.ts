import { HttpRequest, HttpHandler, HttpInterceptor, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { SecurityService } from './security.service';

@Injectable()
export class BasicAuthHttpInterceptorService implements HttpInterceptor {
    
  constructor(
    private router: Router, 
    private securityService: SecurityService,
    private toastrService: ToastrService
  ) { }
  
  /**
   * Intercept all outgoing requests to add HTTP headers
   * 
   * @param request 
   * @param next 
   * @returns Observable<HttpEvent<any>>
   */
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authToken = this.securityService.getAuthToken();
    request = request.clone({
      setHeaders: {
        'Authorization': 'Basic ' + (authToken ? authToken : ''),
        'X-Requested-With': 'XMLHttpRequest'
      }
    });  
    
    // Handle errors. Redirect user to sign in page in case of HTTP 401 error (Unauthorized requests)
    return next.handle(request).pipe(catchError(err => {
      if (err.status === 401) {
        this.securityService.resetAuthToken();
        this.toastrService.error('Invalid credentials. Please login again.');
        this.router.navigate(['/home']);
      }
      return throwError(err);
    }));
  }
}
