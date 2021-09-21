import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { TipoProducto } from '../model/tipoProducto';
import { Producto } from '../model/producto';

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

    const producto = [
      {id:1, nombre: 'Noespresso', marca: 'Cafeteras SA', modelo: 'Noespresso 3000', cantidadAlmacen: 10, cantidadTienda: 2, cantidadTotal: 12, idTipoProducto: 1, nombreTipoProducto: 'Cafetera'},
      {id:2, nombre: 'Boligrafo negro', marca: 'Material Boc', modelo: 'Serie clásica', cantidadAlmacen: 10, cantidadTienda: 2, cantidadTotal: 12, idTipoProducto: 2, nombreTipoProducto: 'Bolígrafo'},
      {id:3, nombre: 'Goma de vainilla', marca: 'Turín', modelo: 'Turín 400', cantidadAlmacen: 10, cantidadTienda: 2, cantidadTotal: 12, idTipoProducto: 3, nombreTipoProducto: 'Goma'},
      {id:4, nombre: 'Libreta de anillas', marca: 'Cambrige', modelo: 'Clásico verde', cantidadAlmacen: 10, cantidadTienda: 2, cantidadTotal: 12, idTipoProducto: 4, nombreTipoProducto: 'Libreta'},

    ]
    return {tipoProducto, producto}
  }; 

  genId<T extends TipoProducto | Producto>(tabla: T[]): number {
    return tabla.length > 0 ? Math.max(... tabla.map(t => t.id))+ 1 : 1;
  }
}
