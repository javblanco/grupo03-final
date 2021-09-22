import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ModalBajaComponent } from './modal-baja.component';

describe('ModalBajaComponent', () => {
  let component: ModalBajaComponent;
  let fixture: ComponentFixture<ModalBajaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalBajaComponent ],
     
      providers: [
        {provide: NgbActiveModal}
      ]
          
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalBajaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
