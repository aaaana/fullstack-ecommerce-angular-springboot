import { FormControl, ValidationErrors } from '@angular/forms';

export class AnnaShopValidators {
  // whitespace vilidation

  static notOnlyWhiteSpace(control: FormControl): ValidationErrors | null {
    // check if the string contains whitespace
    if (control.value != null && control.value.trim().length === 0) {
      // invalid, return error object

      return { notOnlyWhiteSpace: true };
    } else {
      return null;
    }
  }
}
