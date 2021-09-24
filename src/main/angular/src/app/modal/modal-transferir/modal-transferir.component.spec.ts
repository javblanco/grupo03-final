import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ModalTransferirComponent } from './modal-transferir.component';

describe('ModalTransferirComponent', () => {
  let component: ModalTransferirComponent;
  let fixture: ComponentFixture<ModalTransferirComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalTransferirComponent ],
     
      providers: [
        {provide: NgbActiveModal}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalTransferirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
