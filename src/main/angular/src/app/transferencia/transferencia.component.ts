import { Component, OnInit } from '@angular/core';
import { Producto } from '../model/producto';
import { ProductoService } from '../service/producto.service';
import { TransferenciaService } from '../service/transferencia.service';

@Component({
  selector: 'app-transferencia',
  templateUrl: './transferencia.component.html',
  styleUrls: ['./transferencia.component.css']
})
export class TransferenciaComponent implements OnInit {

  accion = 1;

  productos: Producto[] = [];

  producto = <Producto>{};

  constructor(private service: TransferenciaService,
    private productoService: ProductoService) { }

  ngOnInit(): void {
    this.accion = this.service.accion;
    this.getListadoProducto();
  }

  getListadoProducto(): void {
    this.productoService.getProductos()
    .subscribe(productos => this.productos = productos);
  }
}
