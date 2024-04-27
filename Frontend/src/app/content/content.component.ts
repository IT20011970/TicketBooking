import {Component, OnInit} from '@angular/core';
import {ConfirmBoxService} from "../_service/confirm-box.service";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  isModalTableDetails = {
    text: '',
    openTable: false,
    foundLetter: ''
  };

  reply = {
    msg: ''
  };

  constructor(private confirmBox: ConfirmBoxService) {
    this.confirmBox.confirmBox.subscribe((reply: any) => {
      this.reply = reply
      this.isTrueOrFalseDetails(true)
    })
  }

  ngOnInit(): void {
  }

  isTrueOrFalseDetails(reply) {
    this.isModalTableDetails.openTable = reply;
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'))
  }
}
