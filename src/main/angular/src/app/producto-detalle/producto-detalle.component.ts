import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalModificarComponent } from '../modal/modal-modificar/modal-modificar.component';
import { ModalVolverComponent } from '../modal/modal-volver/modal-volver.component';
import { Producto } from '../model/producto';
import { TipoProducto } from '../model/tipoProducto';
import { ProductoService } from '../service/producto.service';
import { TipoProductoService } from '../service/tipo-producto.service';

@Component({
  selector: 'app-producto-detalle',
  templateUrl: './producto-detalle.component.html',
  styleUrls: ['./producto-detalle.component.css']
})
export class ProductoDetalleComponent implements OnInit {

  producto = <Producto>{};

  listaTipos: TipoProducto[] = [];

  mensaje?: string;

  lectura = false;

  constructor(
    private productoService: ProductoService,
    private tipoService: TipoProductoService,
    private router: ActivatedRoute,
    private location: Location,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.getListaTipos();
    this.getProducto();
  }

  getListaTipos(): void {
    this.tipoService.getTipos()
    .subscribe(tipos => this.listaTipos = tipos);
  }

  getProducto(): void {
    let id = Number(this.router.snapshot.paramMap.get('id'));

    if(id) {
      this.productoService.getProducto(id)
      .subscribe(
        producto => this.producto = producto
      );

      this.lectura = this.productoService.lectura;
    } 
  }

  guardar(): void {
    if(this.producto.id) {
      this.modalService.open(ModalModificarComponent)
      .result.then(
        () => this.modificar()
      );
    } else {
      this.crear();
    }
  }

  volver(): void {
    if(this.lectura) {
      this.location.back();
    } else {
      this.modalService.open(ModalVolverComponent)
      .result.then(
        () => this.location.back()
      );
    }
    
  }

  crear(): void {
    this.productoService.crearProducto(this.producto)
    .subscribe(
      producto => {
        this.producto.id = producto;
        this.mensaje='Se ha creado el producto';
      }
    );
    
  }

  modificar() :void {
    this.productoService.modificarProducto(this.producto)
    .subscribe(() => this.mensaje='Se ha modificado el producto');
    
  }
}
