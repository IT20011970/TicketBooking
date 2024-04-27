import {Component, OnInit} from '@angular/core';
import {NotifierService} from "angular-notifier";
import {Router} from "@angular/router";
import {BackendService} from "../../../_service/BackendService";
import {ConfirmBoxService} from "../../../_service/confirm-box.service";

@Component({
  selector: 'app-fuel-consumption-customer',
  templateUrl: './fuel-consumption-customer.component.html',
  styleUrls: ['./fuel-consumption-customer.component.css']
})
export class FuelConsumptionCustomerComponent implements OnInit {

  user = [];
  id
  userprofile = [];
  userDB
  activatebtn=0
  diactivatebtn=0
  constructor(private router: Router,private backendService:BackendService,  private confirmBox: ConfirmBoxService,private notifierService:NotifierService) {
    this.userDB= backendService.newUser();
  }
  getTravellerProfile(){
    this.backendService.getTravellerProfile().subscribe((d)=>{this.userprofile=d})
  }

  delete(){

    this.userDB.isApprove=false
    this.backendService.approveTraveller(this.userDB.nic,this.userDB).subscribe(
        d => {
          console.log(d);
          this.ngOnInit();
          this.notifierService.notify("success","success");
          this.ngOnInit();
        },
          error => {
            console.error("An error occurred1:", error.error);
            this.notifierService.notify("error", error.error);
            this.ngOnInit();
            // Handle the error here (e.g., show a user-friendly message)
          }
    )
  }

  getDataorder2(data){
    this.activatebtn=0
    this.diactivatebtn=1
    this.userDB=data
    this.backendService.user=data
  }
  getDataEdit(data){
    this.backendService.user=data
    this.router.navigate(['/update_traveller']);
    console.log(data.nic)
  }


  ngOnInit(): void {
    this.getTravellerProfile()
  }



}
