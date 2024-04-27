import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from "../../_service/user.service";


@Component({
  selector: 'app-register-customer',
  templateUrl: './register-customer.component.html',
  styleUrls: ['./register-customer.component.css']
})
export class RegisterCustomerComponent implements OnInit {

  customer;
  otp = 0;
  otpEntered;
  otpReceived = false;
  districts = [];
  places
  districtPlaces = []
  vehicleTypes = []

  constructor(private customerS: UserService, private router: Router, private userS: UserService) {
    this.customer = this.customerS.newCustomer()
  }

  ngOnInit(): void {

  }

  addCustomer() {
    this.customer.role = 'travelleragent';
    this.customer.IsApprove=true;
    this.customer.Status="active";
    this.customerS.addUser(this.customer).subscribe(customer => {
      this.router.navigate(['/login']);
    });
  }

}
