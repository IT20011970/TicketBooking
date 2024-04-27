import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {NavbarService} from "./navbar.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userType

  constructor(private router: Router, private navBarS: NavbarService) {
    this.navBarS.navBar.subscribe(() => {
      this.userType = JSON.parse(localStorage.getItem('user')) !== null ? JSON.parse(localStorage.getItem('user')).role : null
    })
  }

  ngOnInit(): void {
    this.userType = JSON.parse(localStorage.getItem('user')) !== null ? JSON.parse(localStorage.getItem('user')).role : null
  }

  signOut() {
    localStorage.clear()
    this.router.navigate(['/login'])
  }
}
