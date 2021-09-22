import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ModalModificarComponent } from './modal-modificar.component';

describe('ModalModificarComponent', () => {
  let component: ModalModificarComponent;
  let fixture: ComponentFixture<ModalModificarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalModificarComponent ],
      providers: [
        {provide: NgbActiveModal}
      ]
          
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalModificarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
