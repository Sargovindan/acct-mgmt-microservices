import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

const baseUrl = environment.CUSTOMER_API_URL;

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  createCustomer(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }
}
