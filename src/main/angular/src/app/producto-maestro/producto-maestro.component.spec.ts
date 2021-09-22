import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { ProductoService } from '../service/producto.service';

import { ProductoMaestroComponent } from './producto-maestro.component';

describe('ProductoMaestroComponent', () => {
  let component: ProductoMaestroComponent;
  let fixture: ComponentFixture<ProductoMaestroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductoMaestroComponent ],
      imports: [RouterTestingModule],
      providers: [
        { provide: ComponentFixtureAutoDetect, useValue: true },
        {
          provide: ProductoService,
            useValue: jasmine.createSpyObj('ProductoService', ['getProductos'])
        }  
      ]
    })
    .compileComponents();

    let spyService = TestBed.get(ProductoService);

    spyService.getProductos.and.returnValue(
      of([
        {id:1, nombre: 'Noespresso', marca: 'Cafeteras SA', modelo: 'Noespresso 3000', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idProductoProducto: 1, nombreTipoProducto: 'Cafetera'},
      {id:2, nombre: 'Boligrafo negro', marca: 'Material Boc', modelo: 'Serie clásica', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idProductoProducto: 2, nombreTipoProducto: 'Bolígrafo'},
      {id:3, nombre: 'Goma de vainilla', marca: 'Turín', modelo: 'Turín 400', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idProductoProducto: 1, nombreTipoProducto: 'Goma'},
      {id:4, nombre: 'Libreta de anillas', marca: 'Cambrige', modelo: 'Clásico verde', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idProductoProducto: 1, nombreTipoProducto: 'Libreta'},
      ])
    )
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductoMaestroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Debería haber 4 elementos', () => {
    fixture.detectChanges();
    
    let compile = fixture.nativeElement as HTMLElement;
    expect(compile.querySelectorAll('table#tabla-producto tbody tr').length).toBe(4);

  })
});
