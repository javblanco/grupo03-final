import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { ProductoService } from '../service/producto.service';
import { TransferenciaService } from '../service/transferencia.service';

import { TransferenciaComponent } from './transferencia.component';

describe('TransferenciaComponent', () => {
  let component: TransferenciaComponent;
  let fixture: ComponentFixture<TransferenciaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferenciaComponent],
      imports: [
        RouterTestingModule,
        FormsModule ],
      providers: [
        { provide: ComponentFixtureAutoDetect, useValue: true },
        {
          provide: ProductoService,
            useValue: jasmine.createSpyObj('ProductoService', ['getProducto', 'getProductos'])
        },
        {
          provide: TransferenciaService,
            useValue: jasmine.createSpyObj('TransferenciaService', ['transferir', 'devolver', 'reponer'])
        },
        {
            provide: ActivatedRoute,
            useValue: {snapshot: {url:'transferir'}}
          
        }
      ]
    })
    .compileComponents();

    let productoSpyService = TestBed.get(ProductoService);

    productoSpyService.getProducto.and.returnValue(of([
      {id:1, nombre: 'Noespresso', marca: 'Cafeteras SA', modelo: 'Noespresso 3000', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idTipoProducto: 1, nombreTipoProducto: 'Cafetera'},

    ]));

    productoSpyService.getProductos.and.returnValue(of([
      {id:1, nombre: 'Noespresso', marca: 'Cafeteras SA', modelo: 'Noespresso 3000', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idProductoProducto: 1, nombreTipoProducto: 'Cafetera'},
      {id:2, nombre: 'Boligrafo negro', marca: 'Material Boc', modelo: 'Serie clásica', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idProductoProducto: 2, nombreTipoProducto: 'Bolígrafo'},
      {id:3, nombre: 'Goma de vainilla', marca: 'Turín', modelo: 'Turín 400', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idProductoProducto: 1, nombreTipoProducto: 'Goma'},
      {id:4, nombre: 'Libreta de anillas', marca: 'Cambrige', modelo: 'Clásico verde', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idProductoProducto: 1, nombreTipoProducto: 'Libreta'},
    ]));

    let spyService = TestBed.get(TransferenciaService);

    spyService.transferir.and.returnValue(of({id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true}));
    spyService.devolver.and.returnValue(of({id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true}));
    spyService.reponer.and.returnValue(of({id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true}));

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferenciaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
