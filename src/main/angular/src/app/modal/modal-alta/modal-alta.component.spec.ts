import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ModalAltaComponent } from './modal-alta.component';

describe('ModalAltaComponent', () => {
  let component: ModalAltaComponent;
  let fixture: ComponentFixture<ModalAltaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalAltaComponent ],
      providers: [
        {provide: NgbActiveModal}
      ]
          
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalAltaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
