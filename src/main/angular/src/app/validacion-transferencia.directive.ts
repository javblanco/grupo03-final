import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn } from '@angular/forms';

export const cantidadValidador: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const cantidad = control.get('cantidad_traspasar');
  const cantidadAlmacen = control.get('cantidad_almacen');
  const cantidadTienda = control.get('cantidad_tienda');
  const accion = control.get('accion');

  if (cantidad?.value == null || cantidad?.value == 0) {
    return true ? {errorCantidad: true} : null;
  }

  if(accion?.value == 1) {
    return cantidad?.value > cantidadAlmacen?.value ? {errorTransferencia: true} : null ;
  }

  if(accion?.value == 2) {
    return cantidad?.value > cantidadTienda?.value ? {errorDevolucion: true} : null ;
  }

  return null;

}

@Directive({
  selector: '[appValidacionTransferencia]',
  providers: [{ provide: NG_VALIDATORS, useExisting: ValidacionTransferenciaDirective, multi: true }]

})
export class ValidacionTransferenciaDirective implements Validator{

  constructor() { }

  validate(control: AbstractControl): ValidationErrors | null {
    return cantidadValidador(control);
  }

}
