import { Component, OnInit } from '@angular/core';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.css']
})
export class AddCustomerComponent implements OnInit {
  customer = {
    firstname: '',
    surname: ''
  };
  submitted = false;

  customerDetails: any;

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
  }

  saveCustomer(): void {
    const data = {
      firstname: this.customer.firstname,
      surname: this.customer.surname
    };

    this.customerService.createCustomer(data)
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

  newCustomer(): void {
    this.submitted = false;
    this.customer = {
      firstname: '',
      surname: ''
    };
  }

}
