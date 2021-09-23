import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
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

  accion: string;
  productos: Producto[] = [];

  cantidad: number;

  productoT = <Producto>{};

  productoSeleccionado = <Producto>{};

  invalido = false;

  constructor(private productoService: ProductoService,
    private transferenciaService: TransferenciaService,
    private modalService: NgbModal,
    private location: Location,
    private routes: ActivatedRoute ) { 
    this.accion = this.routes.snapshot.url[0].toString();
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

  transferir(invalido: boolean | null) : void {
    if(invalido) {
      this.invalido = true;
    } else {
      this.ejecutarAccion();
    }
    
  }

  ejecutarAccion(): void {
    if(this.accion === 'transferir') {
      this.crearModal().result.then(
        () => this.transferenciaService.transferir(this.productoT.id, this.cantidad)
        .subscribe(() =>  {
          this.seleccionarProducto();
          this.productoT= <Producto>{};
          this.productoSeleccionado= <Producto>{};
        })
      );
      
    } else if(this.accion === 'devolver') {
      this.crearModal().result.then(
        () => this.transferenciaService.devolver(this.productoT.id, this.cantidad)
        .subscribe(() => {
          this.seleccionarProducto();
          this.productoT= <Producto>{};
          this.productoSeleccionado= <Producto>{};
        })
      );

    } else if(this.accion === 'reponer') {
      this.crearModal().result.then(
        () => this.transferenciaService.reponer(this.productoT.id, this.cantidad)
        .subscribe(() =>  {
          this.seleccionarProducto();
          this.productoT= <Producto>{};
          this.productoSeleccionado= <Producto>{};
        })
      );
    }
  }

crearModal(): NgbModalRef {
  let modalRef = this.modalService.open(ModalTransferirComponent);
  modalRef.componentInstance.cantidad = this.cantidad;
  modalRef.componentInstance.nombre = this.productoT.nombre;

  return modalRef;
}
  seleccionarProducto(): void {
    this.productoService.getProducto(this.productoT.id)
    .subscribe(producto =>{ 
      this.listarProductos();
      this.productoT = producto;
      this.productoSeleccionado = producto;
      this.cantidad = 0;
    });

    this.invalido = false;
  }
}
