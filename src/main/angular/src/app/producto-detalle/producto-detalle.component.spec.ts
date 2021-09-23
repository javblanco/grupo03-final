import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { Producto } from '../model/producto';
import { ProductoService } from '../service/producto.service';
import { TipoProductoService } from '../service/tipo-producto.service';

import { ProductoDetalleComponent } from './producto-detalle.component';

describe('ProductoDetalleComponent', () => {
  let component: ProductoDetalleComponent;
  let fixture: ComponentFixture<ProductoDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductoDetalleComponent ],
      imports:[RouterTestingModule,
      FormsModule],
      providers: [
        { provide: ComponentFixtureAutoDetect, useValue: true },
        {provide: NgbActiveModal},
        {
          provide: ProductoService,
            useValue: jasmine.createSpyObj('ProductoService', ['getProducto'])
        }  ,
        {
          provide: TipoProductoService,
            useValue: jasmine.createSpyObj('TipoProductoService', ['getTipos'])
        }
      ]
    })
    .compileComponents();

    let tipoSpyService = TestBed.get(TipoProductoService);

    tipoSpyService.getTipos.and.returnValue(of([{id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true},
    {id:2, nombre: 'Bolígrafo', descripcion: 'Sirve para escribir', activo: true},
    {id:3, nombre: 'Goma', descripcion: 'Sirve para borrar algo que se ha apuntado', activo: true},
    {id:4, nombre: 'Libreta', descripcion: 'Se usa para realizar anotaciones', activo: true}]
    ));

    let productoSpyService = TestBed.get(ProductoService);

    productoSpyService.getProducto.and.returnValue(of([
      {id:1, nombre: 'Noespresso', marca: 'Cafeteras SA', modelo: 'Noespresso 3000', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idTipoProducto: 1, nombreTipoProducto: 'Cafetera'},

    ]))

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductoDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Los inputs deberían estar vacios', async () => {
    component.producto = <Producto>{};
    let compile = fixture.nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(async () => {
      await expect(compile.querySelector('input#producto-nombre')?.value).toBe('');
 
     });
  });

  it('El input de nombre debería ser "Noespresso"', async () => {
    component.producto =       {id:1, nombre: 'Noespresso', marca: 'Cafeteras SA', modelo: 'Noespresso 3000', cantidadUnidadesAlmacen: 10, cantidadUnidadesTienda: 2, cantidadTotal: 12, idTipoProducto: 1, nombreTipoProducto: 'Cafetera'};
    let compile = fixture.nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(async () => {
      await expect(compile.querySelector('input#producto-nombre')?.value).toBe('Noespresso');
 
     });
  });
});
