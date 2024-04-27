import { Directive } from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from "@angular/forms";

@Directive({
  selector: '[appTelephoneValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: TelephoneValidatorDirective, multi: true}]
})
export class TelephoneValidatorDirective implements Validator {

  setLetter = true;

  validate(control: AbstractControl): { [key: string]: any } | null {
    let TELEPHONE_REGEX = /^[0-9]{3}[-][0-9]{7}$/; // Regular Expression 1
    if (control.value != undefined) {
      if (control.value.length == 0 || TELEPHONE_REGEX.test(control.value)) {
        return null;
      }
      if (control.value.length === 2) {
        this.setLetter = true;
      } else if (control.value.length === 3 && this.setLetter) {
        control.setValue(control.value + '-')
      } else if (control.value.length === 4) {
        this.setLetter = false;
      }
    }

    return {'telephoneInvalid': true};
  }

  constructor() {
  }

}
