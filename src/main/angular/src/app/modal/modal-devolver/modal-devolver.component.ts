import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-devolver',
  templateUrl: './modal-devolver.component.html',
  styleUrls: ['./modal-devolver.component.css']
})
export class ModalDevolverComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  @Input() cantidad?: number;

  @Input() nombre?: string;
}
