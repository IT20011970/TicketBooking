import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {BackendService} from "../../../_service/BackendService";
import {ConfirmBoxService} from "../../../_service/confirm-box.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  History = [];

  constructor(private router: Router,private backendService:BackendService,  private confirmBox: ConfirmBoxService,private notifierService:NotifierService) {

  }
  getHistory(){
    this.backendService.getHistory().subscribe((d)=>{this.History=d})
  }

  ngOnInit(): void {
    this.getHistory()
  }


}
