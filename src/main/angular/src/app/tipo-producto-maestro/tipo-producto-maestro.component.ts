import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TipoProducto } from '../model/tipoProducto';
import { TipoProductoService } from '../service/tipo-producto.service';

@Component({
  selector: 'app-tipo-producto-maestro',
  templateUrl: './tipo-producto-maestro.component.html',
  styleUrls: ['./tipo-producto-maestro.component.css']
})
export class TipoProductoMaestroComponent implements OnInit {

  tipos: TipoProducto[] = [];

  visible = false;

  constructor(private tipoService: TipoProductoService,
    private location: Location) { }

  ngOnInit(): void {
    this.getTipos();
    this.tipoService.desactivarSoloLectura();
  }

  getTipos(): void {
    this.tipoService.getTiposActivos()
    .subscribe(tipos => this.tipos = tipos);
  }

  volver(): void {
    this.location.back();
  }

  ver(): void {
    this.tipoService.activarSoloLectura();
  }
}
