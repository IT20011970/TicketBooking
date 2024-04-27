import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AlertBoxService {

  reply = new Subject<object>();

  constructor() { }
}
