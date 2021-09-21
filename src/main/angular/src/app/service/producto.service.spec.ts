import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { Producto } from '../model/producto';

import { ProductoService } from './producto.service';

describe('ProductoService', () => {
  let service: ProductoService;
  let httpClientSpy: { get: jasmine.Spy };

  const productos: Producto[] = [
    {id:1, nombre: 'Noespresso', marca: 'Cafeteras SA', modelo: 'Noespresso 3000', cantidadAlmacen: 10, cantidadTienda: 2, cantidadTotal: 12, idTipoProducto: 1, nombreTipoProducto: 'Cafetera'},
    {id:2, nombre: 'Boligrafo negro', marca: 'Material Boc', modelo: 'Serie clásica', cantidadAlmacen: 10, cantidadTienda: 2, cantidadTotal: 12, idTipoProducto: 2, nombreTipoProducto: 'Bolígrafo'},
    {id:3, nombre: 'Goma de vainilla', marca: 'Turín', modelo: 'Turín 400', cantidadAlmacen: 10, cantidadTienda: 2, cantidadTotal: 12, idTipoProducto: 3, nombreTipoProducto: 'Goma'},
    {id:4, nombre: 'Libreta de anillas', marca: 'Cambrige', modelo: 'Clásico verde', cantidadAlmacen: 10, cantidadTienda: 2, cantidadTotal: 12, idTipoProducto: 4, nombreTipoProducto: 'Libreta'},

  ]

  beforeEach(() => {
    TestBed.configureTestingModule({});

    
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    
    service = new ProductoService(httpClientSpy as any);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('El método getProductos debería devolver 4 registros', () => {
    httpClientSpy.get.and.returnValue(of(productos));

    service.getProductos().subscribe(
      resultado => expect(resultado.length).toBe(4)
    );
  });

  it('El método getProducto de id=1 debería devolver un producto de nombre "Noespresso"', () => {
    httpClientSpy.get.and.returnValue(of(productos[0]));

    service.getProducto(1).subscribe(
      resultado => expect(resultado.nombre).toBe('Noespresso')
    );
  });
});
