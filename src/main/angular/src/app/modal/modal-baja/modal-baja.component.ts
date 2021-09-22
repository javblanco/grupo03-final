import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-baja',
  templateUrl: './modal-baja.component.html',
  styleUrls: ['./modal-baja.component.css']
})
export class ModalBajaComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
