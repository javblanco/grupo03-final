import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { TipoProductoService } from '../service/tipo-producto.service';

import { TipoProductoMaestroComponent } from './tipo-producto-maestro.component';

describe('TipoProductoMaestroComponent', () => {
  let component: TipoProductoMaestroComponent;
  let fixture: ComponentFixture<TipoProductoMaestroComponent>;
  let spyTipoService: jasmine.SpyObj<TipoProductoService>;

  beforeEach( () => {
     TestBed.configureTestingModule({
      declarations: [ TipoProductoMaestroComponent ],
      imports: [RouterTestingModule,
      FormsModule],
      providers: [
        { provide: ComponentFixtureAutoDetect, useValue: true },
        {
          provide: TipoProductoService,
            useValue: jasmine.createSpyObj('TipoProductoService', ['getTiposActivos'])
        }  
      ]
    }).compileComponents();

    let spyService = TestBed.get(TipoProductoService);

    spyService.getTiposActivos.and.returnValue(of([{id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true},
    {id:2, nombre: 'Bolígrafo', descripcion: 'Sirve para escribir', activo: true},
    {id:3, nombre: 'Goma', descripcion: 'Sirve para borrar algo que se ha apuntado', activo: true},
    {id:4, nombre: 'Libreta', descripcion: 'Se usa para realizar anotaciones', activo: true}]
    ));

    fixture = TestBed.createComponent(TipoProductoMaestroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Debería haber 4 registros', () => {
    fixture.detectChanges();
    
      let compile = fixture.nativeElement as HTMLElement;
      expect(compile.querySelectorAll('table#tabla-tipo-producto tbody tr').length).toBe(4);

  })
});
