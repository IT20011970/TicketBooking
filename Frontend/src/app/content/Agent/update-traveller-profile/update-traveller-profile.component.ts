import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ConfirmBoxService} from "../../../_service/confirm-box.service";
import {BackendService} from "../../../_service/BackendService";
import {UserService} from "../../../_service/user.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-update-traveller-profile',
  templateUrl: './update-traveller-profile.component.html',
  styleUrls: ['./update-traveller-profile.component.css']
})
export class UpdateTravellerProfileComponent implements OnInit {

  user

  constructor(private backendService:BackendService,private userService:UserService,private router: Router, private notifierService: NotifierService, private confirmBox: ConfirmBoxService) {
    this.user=backendService.user
  }

  ngOnInit(): void {
  }

  updateUser() {
    this.userService.updateUser(this.user.nic,this.user).subscribe(
        data => {
          console.log(data);
          this.notifierService.notify("success","success");
          this.router.navigate(['/manage_user']);
        },
          error => {
            console.error("An error occurred1:", error.error);
            this.notifierService.notify("error", error.error);
            this.ngOnInit();
            // Handle the error here (e.g., show a user-friendly message)
          }


    )
  }

}
