import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalTransferirComponent } from '../modal/modal-transferir/modal-transferir.component';
import { ModalVolverComponent } from '../modal/modal-volver/modal-volver.component';
import { Producto } from '../model/producto';
import { ProductoService } from '../service/producto.service';
import { TransferenciaService } from '../service/transferencia.service';

@Component({
  selector: 'app-transferencia',
  templateUrl: './transferencia.component.html',
  styleUrls: ['./transferencia.component.css']
})
export class TransferenciaComponent implements OnInit {

  accion: number;
  productos: Producto[] = [];

  cantidad: number;

  productoT = <Producto>{};

  productoSeleccionado = <Producto>{};

  constructor(private productoService: ProductoService,
    private transferenciaService: TransferenciaService,
    private modalService: NgbModal,
    private location: Location ) { 
    this.accion = this.transferenciaService.accion;
    this.cantidad = 0;
  }

  ngOnInit(): void {
    this.listarProductos();
  }

  listarProductos(): void {
    this.productoService.getProductos()
    .subscribe(productos => this.productos = productos)
  }

  volver() : void {
    this.modalService.open(ModalVolverComponent)
    .result.then(
      () => this.location.back()
    );
  }

  cargarProducto(producto: Producto) {
    this.productoT = producto;
  }

  transferir() : void {
    if(this.accion==1) {
      let modalRef = this.modalService.open(ModalTransferirComponent);
      modalRef.componentInstance.cantidad = this.cantidad;
      modalRef.componentInstance.nombre = this.productoT.nombre;

      modalRef.result.then(
        () => this.transferenciaService.transferir(this.productoT.id, this.cantidad)
        .subscribe(() => this.seleccionarProducto())
      );
      
    } else if(this.accion ==2) {
      let modalRef = this.modalService.open(ModalTransferirComponent);
      modalRef.componentInstance.cantidad = this.cantidad;
      modalRef.componentInstance.nombre = this.productoT.nombre;

      modalRef.result.then(
        () => this.transferenciaService.devolver(this.productoT.id, this.cantidad)
        .subscribe(() => this.seleccionarProducto())
      );

    } else if(this.accion == 3) {
      let modalRef = this.modalService.open(ModalTransferirComponent);
      modalRef.componentInstance.cantidad = this.cantidad;
      modalRef.componentInstance.nombre = this.productoT.nombre;

      modalRef.result.then(
        () => this.transferenciaService.reponer(this.productoT.id, this.cantidad)
        .subscribe(() => this.seleccionarProducto())
      );
    }
  }

  seleccionarProducto(): void {
    this.productoService.getProducto(this.productoT.id)
    .subscribe(producto =>{ 
      this.listarProductos();
      this.productoT = producto;
      this.cantidad = 0;
    });
  }
}
