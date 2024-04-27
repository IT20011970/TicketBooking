import { Directive } from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from "@angular/forms";

@Directive({
  selector: '[appLoginValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: LoginValidatorDirective, multi: true}]
})
export class LoginValidatorDirective implements Validator {

  validate(control: AbstractControl): { [key: string]: any } | null {
    let EMAIL_REGEX = /^[a-z.]+[0-9a-z]+[@][0-9a-z]+[.][0-9a-z]+$/; // Regular Expression 1
    let TELEPHONE_REGEX = /^[0-9]{3}[-][0-9]{7}$/; // Regular Expression 11

    if (control.value != undefined) {
      if (control.value.length == 0 || EMAIL_REGEX.test(control.value)) {
        return null;
      } else if (control.value.length == 0 || TELEPHONE_REGEX.test(control.value)) {
        return null;
      }

      // if (this.preVal != control.value) {
      //   this.preVal = control.value.toUpperCase();
      //   control.setValue(control.value.toUpperCase());
      // }
    } else {
      return null;
    }

    return {'loginInvalid': true};
  }

  constructor() {
  }

}
