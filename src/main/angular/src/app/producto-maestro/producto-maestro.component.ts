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

  filtro = 1;

  filtrado = 1;

  constructor(private service: ProductoService,
    private location: Location) { }

  ngOnInit(): void {
    this.getProductos();
    this.service.desactivarSoloLectura();
  }

  getProductos(): void {
    this.service.getProductos()
    .subscribe(
      productos => {
      this.productos = productos;
      this.filtrado = 1;
      }
    )
  }

  volver(): void {
    this.location.back();
  }

  ver(): void {
    this.service.activarSoloLectura();
  }

  filtrar(): void {
    if(this.filtro === 1) {
      this.getProductos();
    } else if(this.filtro === 2) {
      this.service.getProductosAlmacen()
      .subscribe(productos => {
        this.productos = productos;
        this.filtrado = 2;
      });
    } else if(this.filtro === 3) {
      this.service.getProductosTienda()
      .subscribe(productos => {
        this.productos = productos;
        this.filtrado = 3;
      });
    }
  }
}
