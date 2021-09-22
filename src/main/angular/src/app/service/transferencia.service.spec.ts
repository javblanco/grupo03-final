import { TestBed } from '@angular/core/testing';
import { Producto } from '../model/producto';

import { TransferenciaService } from './transferencia.service';

describe('TransferenciaService', () => {
  let service: TransferenciaService;
  let httpClientSpy: { get: jasmine.Spy };

  const productos: Producto[] = [
    {id:1, nombre: 'Noespresso', marca: 'Cafeteras SA', modelo: 'Noespresso 3000', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idTipoProducto: 1, nombreTipoProducto: 'Cafetera'},
    {id:2, nombre: 'Boligrafo negro', marca: 'Material Boc', modelo: 'Serie clásica', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idTipoProducto: 2, nombreTipoProducto: 'Bolígrafo'},
    {id:3, nombre: 'Goma de vainilla', marca: 'Turín', modelo: 'Turín 400', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idTipoProducto: 3, nombreTipoProducto: 'Goma'},
    {id:4, nombre: 'Libreta de anillas', marca: 'Cambrige', modelo: 'Clásico verde', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idTipoProducto: 4, nombreTipoProducto: 'Libreta'},

  ]

  beforeEach(() => {
    TestBed.configureTestingModule({});
    
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    
    service = new TransferenciaService(httpClientSpy as any);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
