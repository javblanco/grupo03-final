import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Producto } from '../model/producto';
import { ProductoService } from '../service/producto.service';

@Component({
  selector: 'app-producto-maestro',
  templateUrl: './producto-maestro.component.html',
  styleUrls: ['./producto-maestro.component.css']
})
export class ProductoMaestroComponent implements OnInit {

  productos: Producto[] = [];

  constructor(private service: ProductoService,
    private location: Location) { }

  ngOnInit(): void {
    this.getProductos();
    this.service.desactivarSoloLectura();
  }

  getProductos(): void {
    this.service.getProductos()
    .subscribe(
      productos => this.productos = productos
    )
  }

  volver(): void {
    this.location.back();
  }

  ver(): void {
    this.service.activarSoloLectura();
  }
}
