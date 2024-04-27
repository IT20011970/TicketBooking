import { Directive } from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from "@angular/forms";

@Directive({
  selector: '[appVehicleNumberValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: VehicleNumberValidatorDirective, multi: true}]
})
export class VehicleNumberValidatorDirective implements Validator {

  // @HostListener('document:keyup', ['$event'])
  // KeyUpEvent(event: KeyboardEvent) {
  //   if (event.keyCode == KeyCodes.LEFT)
  //     this.counter--;
  //   if (event.keyCode == KeyCodes.RIGHT)
  //     this.counter++;
  // }
  preVal;

  validate(control: AbstractControl): { [key: string]: any } | null {
    let VEHICLE_REGEX1 = /^[0-9][-][0-9]{4}$|^[0-9]{2}[-][0-9]{4}$|^[0-9]{3}[-][0-9]{4}$/; // Regular Expression 1
    let VEHICLE_REGEX3 = /^[A-Z]{2}[-][0-9]{4}$|^[A-Z]{3}[ ][0-9]{4}$/; // Regular Expression 3

    if (control.value != undefined) {
      if (control.value.length == 0 || VEHICLE_REGEX1.test(control.value)) {
        return null;
      } else if (control.value.length == 0 || VEHICLE_REGEX3.test(control.value)) {
        return null;
      }

      if (this.preVal != control.value) {
        this.preVal = control.value.toUpperCase();
        control.setValue(control.value.toUpperCase());
      }
    } else {
      return null;
    }

    return {'vehicleNumberInvalid': true};
  }

  constructor() {
  }

}
