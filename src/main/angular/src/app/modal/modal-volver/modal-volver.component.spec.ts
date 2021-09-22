import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalVolverComponent } from './modal-volver.component';

describe('ModalVolverComponent', () => {
  let component: ModalVolverComponent;
  let fixture: ComponentFixture<ModalVolverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalVolverComponent ]
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
