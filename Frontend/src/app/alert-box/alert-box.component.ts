import {Component, Input, OnInit} from '@angular/core';
import {AlertBoxService} from "./alert-box.service";

@Component({
  selector: 'app-alert-box',
  templateUrl: './alert-box.component.html',
  styleUrls: ['./alert-box.component.css']
})
export class AlertBoxComponent implements OnInit {

  @Input() alertMsg;

  constructor(private alertService: AlertBoxService) {
  }

  ngOnInit() {
  }

  isTrueOrFalse(reply) {
    this.alertService.reply.next(reply)
  }

}
