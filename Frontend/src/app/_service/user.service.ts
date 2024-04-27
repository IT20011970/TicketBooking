import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../environments/environment";
import {LoginService} from "./login.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  placesFound = new Subject<any>()

  constructor(private http: HttpClient,private loginService:LoginService) {

  }

  login(user): Observable<any> {
    return this.http.post<any>(environment.backend_url + "/login", user);
  }


  addUser(customer): Observable<any> {
    return this.http.post<any>(environment.backend_url + '/', customer);
  }

  updateUser(id,user): Observable<any> {
    console.log("sssss",user)
    return this.http.put<any>(environment.backend_url + "?id="+id, user);
  }


  newCustomer() {
    return {
      nic: '',
      name: '',
      address: '',
      role: '',
      contactNumber: '',
      password: '',
      Status:'',
      IsApprove:''
    };
  }

}
