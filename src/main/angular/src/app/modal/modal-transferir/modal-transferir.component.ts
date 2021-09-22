import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-transferir',
  templateUrl: './modal-transferir.component.html',
  styleUrls: ['./modal-transferir.component.css']
})
export class ModalTransferirComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  @Input() cantidad?: number;

  @Input() nombre?: string;

}
