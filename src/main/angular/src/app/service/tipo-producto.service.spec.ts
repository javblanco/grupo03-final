import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { TipoProducto } from '../model/tipoProducto';

import { TipoProductoService } from './tipo-producto.service';

describe('TipoProductoService', () => {
  let service: TipoProductoService;

  let httpClientSpy: { get: jasmine.Spy };

  beforeEach(() => {
    TestBed.configureTestingModule({});
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);

    const tipos: TipoProducto[] = [
      {id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true},
      {id:2, nombre: 'Bolígrafo', descripcion: 'Sirve para escribir', activo: true},
      {id:3, nombre: 'Goma', descripcion: 'Sirve para borrar algo que se ha apuntado', activo: true},
      {id:4, nombre: 'Libreta', descripcion: 'Se usa para realizar anotaciones', activo: true}
    ];

    httpClientSpy.get.and.returnValue(of(tipos));
    
    service = new TipoProductoService(httpClientSpy as any);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('El método listar debería devolver 4 valores', () => {
    service.getTipos().subscribe(
      tipos => expect(tipos.length).toBe(4)
    );
  });
});

