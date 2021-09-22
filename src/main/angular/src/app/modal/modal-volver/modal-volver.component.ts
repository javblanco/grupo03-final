import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-volver',
  templateUrl: './modal-volver.component.html',
  styleUrls: ['./modal-volver.component.css']
})
export class ModalVolverComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
