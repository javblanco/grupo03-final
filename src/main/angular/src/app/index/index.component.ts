import { Component, OnInit } from '@angular/core';
import { TransferenciaService } from '../service/transferencia.service';

@Component({
  selector: 'app-index2',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  constructor(private service: TransferenciaService) { }

  ngOnInit(): void {
  }

  transferir() {
    this.service.accion = 1;
  }

  devolver() {
    this.service.accion = 2;
  }

  reponer() {
    this.service.accion = 3;
  }
}
