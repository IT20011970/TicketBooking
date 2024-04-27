import {Component, OnInit} from '@angular/core';
import {LoginService} from '../_service/login.service';
import {Router} from '@angular/router';
import {NavbarService} from '../navbar/navbar.service';
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user;

  constructor(private loginS: LoginService, private router: Router, private navBarS: NavbarService,private notifierService:NotifierService) {
    this.user = this.newUser();//User Object
  }

  ngOnInit(): void {
  }

  login() {
    this.loginS.login(this.user).subscribe(user => { // Sent request to login
      if (user !== null) {
        this.notifierService.notify("success","success");
        localStorage.setItem('user', JSON.stringify(user));
        if (user.role === 'travelleragent') {
          this.router.navigate(['/my_profile']); //Navigate to travel agent
          this.navBarS.navBar.next();
        } else if (user.role === 'backendOfficer') {
          this.router.navigate(['/manage-status']); //Navigate backend
          this.navBarS.navBar.next();
        }
        else {
          this.router.navigate(['/']); //Navigate backend
          this.navBarS.navBar.next();
        }
      }

    } ,error => {
      console.error("An error occurred1:", error.error); // Error
      this.notifierService.notify("error", error.error);
      this.ngOnInit();
      // Handle the error here (e.g., show a user-friendly message)
    });
  }

  newUser() { // Reset user
    return {
      password: ''
    };
  }
}
