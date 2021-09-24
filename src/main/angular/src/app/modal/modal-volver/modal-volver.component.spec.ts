import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ModalVolverComponent } from './modal-volver.component';

describe('ModalVolverComponent', () => {
  let component: ModalVolverComponent;
  let fixture: ComponentFixture<ModalVolverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalVolverComponent, ],
      providers: [
        {provide: NgbActiveModal}
      ]
          
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalVolverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
