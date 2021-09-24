import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ModalReponerComponent } from './modal-reponer.component';

describe('ModalReponerComponent', () => {
  let component: ModalReponerComponent;
  let fixture: ComponentFixture<ModalReponerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalReponerComponent ],
     
      providers: [
        {provide: NgbActiveModal}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalReponerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
