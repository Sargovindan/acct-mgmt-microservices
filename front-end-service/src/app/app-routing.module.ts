import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AddCustomerComponent } from './components/add-customer/add-customer.component';
import { AddAccountComponent } from './components/add-account/add-account.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';


const routes: Routes = [
  { path: 'add-customer', component: AddCustomerComponent },
  { path: 'add-account', component: AddAccountComponent },
  { path: 'account-details', component: AccountDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
