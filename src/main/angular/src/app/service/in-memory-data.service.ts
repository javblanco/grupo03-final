import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { TipoProducto } from '../model/tipoProducto';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService{

  constructor() { }

  createDb() {
    const tipoProducto = [
      {id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true},
      {id:2, nombre: 'Bolígrafo', descripcion: 'Sirve para escribir', activo: true},
      {id:3, nombre: 'Goma', descripcion: 'Sirve para borrar algo que se ha apuntado', activo: true},
      {id:4, nombre: 'Libreta', descripcion: 'Se usa para realizar anotaciones', activo: true},
    ];

    return {tipoProducto}
  }; 

  genId<T extends TipoProducto>(tabla: T[]): number {
    return tabla.length > 0 ? Math.max(... tabla.map(t => t.id))+ 1 : 1;
  }
}
