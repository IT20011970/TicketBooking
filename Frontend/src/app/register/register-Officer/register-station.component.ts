import {Component, OnInit} from '@angular/core';
import {Route, Router} from "@angular/router";
import {LoginService} from "../../_service/login.service";
import {UserService} from "../../_service/user.service";



@Component({
  selector: 'app-register-station',
  templateUrl: './register-station.component.html',
  styleUrls: ['./register-station.component.css']
})
export class RegisterStationComponent implements OnInit {


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
    this.customer.role = 'backendOfficer';
    this.customer.IsApprove=true;
    this.customer.Status="active";
    this.customerS.addUser(this.customer).subscribe(customer => {
      this.router.navigate(['/login']);
    });
  }




}
