import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TipoProducto } from '../model/tipoProducto';
import { TipoProductoService } from '../service/tipo-producto.service';

import { TipoProductoDetalleComponent } from './tipo-producto-detalle.component';

describe('TipoProductoDetalleComponent', () => {
  let component: TipoProductoDetalleComponent;
  let fixture: ComponentFixture<TipoProductoDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TipoProductoDetalleComponent ],
      imports: [RouterTestingModule,
      FormsModule,
      ],
      providers: [
        { provide: ComponentFixtureAutoDetect, useValue: true },
        {provide: NgbActiveModal},
        {
          provide: TipoProductoService,
            useValue: jasmine.createSpyObj('TipoProductoService', ['getTipo'])
        }  
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TipoProductoDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('El input de nombre debería estar vacio', async () => {
    component.tipo = <TipoProducto>{};

    let compile = fixture.nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(async () => {
      await expect(compile.querySelector('input#tipo-nombre')?.value).toBe('');
 
     });
  });

  it('El textarea de descripcion debería estar vacio', async () => {
    component.tipo = <TipoProducto>{};

    let compile = fixture.nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(async () => {
      await expect(compile.querySelector('textarea#tipo-descripcion')?.value).toBe(''); 
     });
  });

  it('El input de nombre debería estar contener "Cafetera"', async () => {
    component.tipo = {id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true};

    let compile = fixture.nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(async () => {
      await expect(compile.querySelector('input#tipo-nombre')?.value).toBe('Cafetera');
 
     });
  });

  it('El textarea de descripcion debería ser "Máquina que hace café"', async () => {
    component.tipo = {id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true};

    let compile = fixture.nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(async () => {
      await expect(compile.querySelector('textarea#tipo-descripcion')?.value).toBe('Máquina que hace café');
 
     });
  });

});
