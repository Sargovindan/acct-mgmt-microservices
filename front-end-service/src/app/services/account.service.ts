import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

const baseUrl = environment.ACCOUNT_API_URL;

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }

  createAccount(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  getAccountDetails(customerId: any): Observable<any> {
    return this.http.get(`${baseUrl}/${customerId}`);
  }
}
