import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MasterService {

  constructor(private http: HttpClient) { }

  /**
   * Get ToDo list for loggedin user
   * @returns Observable<any>
   */
  getToDoList(): Observable<any> {
    return this.http.get('/getToDoList');
  }

  /**
   * Save ToDo list against loggedin user
   * @param data 
   * @returns Observable<any>
   */
  saveToDoList(data: Array<any>): Observable<any> {
    return this.http.post('/saveToDoList', data);
  }
}
