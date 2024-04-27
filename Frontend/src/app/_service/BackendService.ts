import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BackendService {
  user: any;

  constructor(private http: HttpClient) {
  }

  addTrain(train): Observable<any> {
    console.log("sssss",train)
    return this.http.post<any>(environment.backend_url + "/Train", train);
  }

  updateTrain(id,train): Observable<any> {
    console.log("sssss",train)
    return this.http.put<any>(environment.backend_url + "/Train?id="+id, train);
  }
  deleteTrain(id,train): Observable<any> {
    console.log("sssss",train)
    return this.http.put<any>(environment.backend_url + "/CancelTrain?id="+id, train);
  }
  getTrain(): Observable<any> {
    return this.http.get<any>(environment.backend_url + '/Train');
  }

  approveTraveller(id,data): Observable<any> {
    return this.http.put<any>(environment.backend_url + '?id='+id, data);
  }

  getTraveller(): Observable<any> {
    return this.http.get<any>(environment.backend_url + '/traveller');
  }
  getHistory(): Observable<any> {
    return this.http.get<any>(environment.backend_url + '/History');
  }

  getActiveTraveller(): Observable<any> {
    return this.http.get<any>(environment.backend_url + '/activetraveller');
  }
  getTravellerProfile(): Observable<any> {
    return this.http.get<any>(environment.backend_url + '/travellerprofile');
  }

  newTrain() {
    return {
      number: "",
      depatre_Station: "",
      arrival_Station: "",
      depatre_Time: "",
      arrival_Time:'',
      status: ""
    };
  }

  newUser() {
    return {
      nic: "",
      name: "",
      address: "",
      role: "",
      contactNumber: "",
      password: "",
      status: "",
      isApprove: ""
    };
  }




}
