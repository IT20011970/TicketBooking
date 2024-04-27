import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ConfirmBoxService} from "../../../_service/confirm-box.service";
import {NotifierService} from "angular-notifier"
import {BackendService} from "../../../_service/BackendService";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  user = []; //user obj
  userActive = []; //active user
  userprofile = [];//user profile
  userDB //user DB
  activatebtn=0 //activate user
  diactivatebtn=0 //diactivate user
  constructor(private router: Router,private backendService:BackendService,  private confirmBox: ConfirmBoxService,private notifierService:NotifierService) {
    this.userDB= backendService.newUser(); // assign to object
  }

  ngOnInit(): void {
    this.getInActiveTraveller() //get inactive user
    this.getActiveTraveller()//get active user
    this.getTravellerProfile()//get user
  }

  getInActiveTraveller(){
    this.backendService.getTraveller().subscribe((d)=>{this.user=d})
  }
  getActiveTraveller(){
    this.backendService.getActiveTraveller().subscribe((d)=>{this.userActive=d})
  }

  getTravellerProfile(){
    this.backendService.getTravellerProfile().subscribe((d)=>{this.userprofile=d})
  }
  diactivate(){ //incative function
    this.userDB.status="inactive"
    this.userDB.isApprove=true

    this.backendService.approveTraveller(this.userDB.nic,this.userDB).subscribe(
      (d)=> {
        this.ngOnInit()
        console.log(d)
      }
    )
  }

  activate(){ //active function
    this.userDB.status="active"
    this.userDB.isApprove=true

    this.backendService.approveTraveller(this.userDB.nic,this.userDB).subscribe(
      (d)=> {
        this.ngOnInit()
        console.log(d)
      }
    )
  }

  getDataorder(data){ //get clicked data
    this.activatebtn=1
    this.diactivatebtn=0
    this.userDB=data
  }

  getDataorder2(data){ //get clicked data2
    this.activatebtn=0
    this.diactivatebtn=1
    this.userDB=data
  }

}
