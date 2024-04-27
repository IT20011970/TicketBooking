import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from "../../../_service/user.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {


  customer;


  constructor(private customerS: UserService, private router: Router, private userS: UserService,private notifierService:NotifierService) {
    this.customer = this.customerS.newCustomer() //Create user object
  }
  ngOnInit(): void {

  }
  addCustomer() {
    this.customer.role = 'traveller';
    this.customer.IsApprove=true;
    this.customer.Status="active";
    this.customerS.addUser(this.customer).subscribe( //Create traveller
      customer => {
        console.log(customer);
        this.ngOnInit();
        this.notifierService.notify("success","success");
        this.ngOnInit();
      },
        error => {
          console.error("An error occurred1:", error.error);
          this.notifierService.notify("error", error.error);
          this.ngOnInit();
          // Handle the error here (e.g., show a user-friendly message)
        },

    );
  }

}
