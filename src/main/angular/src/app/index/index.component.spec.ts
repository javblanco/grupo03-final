import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { TransferenciaService } from '../service/transferencia.service';

import { IndexComponent } from './index.component';

describe('IndexComponent', () => {
  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IndexComponent ],
      imports:[RouterTestingModule],
      providers: [
        { provide: ComponentFixtureAutoDetect, useValue: true },
        {
          provide: TransferenciaService,
            useValue: jasmine.createSpyObj('TransferenciaService', ['transferir', 'devolver', 'reponer'])
        }
      ]
    })
    .compileComponents();

    let spyService = TestBed.get(TransferenciaService);

    spyService.transferir.and.returnValue(of({id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true}));
    spyService.devolver.and.returnValue(of({id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true}));
    spyService.reponer.and.returnValue(of({id:1, nombre: 'Cafetera', descripcion: 'Máquina que hace café', activo: true}));

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
