import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-reponer',
  templateUrl: './modal-reponer.component.html',
  styleUrls: ['./modal-reponer.component.css']
})
export class ModalReponerComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  @Input() cantidad?: number;

  @Input() nombre?: string;

}
