import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ModalDevolverComponent } from './modal-devolver.component';

describe('ModalDevolverComponent', () => {
  let component: ModalDevolverComponent;
  let fixture: ComponentFixture<ModalDevolverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalDevolverComponent ],
     
      providers: [
        {provide: NgbActiveModal}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalDevolverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
