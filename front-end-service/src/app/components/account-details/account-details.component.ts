import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {

  accountTransactions = null;

  submitted = false;

  customerId = '';

  customerDetails: any;

  accountDetails: any;

  currentAccountTransactions: any;
  currentIndex: any;
  

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
  }

  getAccountTransactions(): void {
    this.accountService.getAccountDetails(this.customerId)
      .subscribe(
        accountTransactions => {
          this.customerDetails = accountTransactions.data.customer;
          this.accountDetails = accountTransactions.data.account;
          this.accountTransactions = accountTransactions.data.transactions;
          console.log(accountTransactions);
        },
        error => {
          console.log(error);
        });
  }

  setActiveTutorial(transaction: any, index: number): void {
    this.currentAccountTransactions = transaction;
    this.currentIndex = index;
  }

  newAccountDetailsRequest(): void {
    this.customerId = '';
    this.submitted = false;
    this.accountTransactions = null;
  }

}
