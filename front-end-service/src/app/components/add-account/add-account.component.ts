import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {

  account = {
    customerId: '',
    initialCredit: 0
  };
  submitted = false;

  customerDetails: any;

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
  }

  addAccount(): void {
    const data = {
      customerId: this.account.customerId,
      initialCredit: this.account.initialCredit
    };

    this.accountService.createAccount(data)
      .subscribe(
        response => {
          this.customerDetails = response;
          console.log(response);
          this.submitted = true;
        },
        error => {
          console.log(error);
        });
  }

  newAccount(): void {
    this.submitted = false;
    this.account = {
      customerId: '',
      initialCredit: 0
    };
  }

}
