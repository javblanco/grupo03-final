import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalAltaComponent } from '../modal/modal-alta/modal-alta.component';
import { ModalBajaComponent } from '../modal/modal-baja/modal-baja.component';
import { ModalModificarComponent } from '../modal/modal-modificar/modal-modificar.component';
import { ModalVolverComponent } from '../modal/modal-volver/modal-volver.component';
import { TipoProducto } from '../model/tipoProducto';
import { TipoProductoService } from '../service/tipo-producto.service';
import { TipoProductoMaestroComponent } from '../tipo-producto-maestro/tipo-producto-maestro.component';

@Component({
  selector: 'app-tipo-producto-detalle',
  templateUrl: './tipo-producto-detalle.component.html',
  styleUrls: ['./tipo-producto-detalle.component.css']
})
export class TipoProductoDetalleComponent implements OnInit {

  tipo = <TipoProducto>{};

  mensaje?: string;

  lectura = false;

  creacion = true;
  


  constructor(private tipoService: TipoProductoService,
    private location: Location,
    private routes: ActivatedRoute,
    private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getTipo();
  }


  getTipo(): void {
    let id = Number(this.routes.snapshot.paramMap.get('id'));

    if(id) {
      this.tipoService.getTipo(id)
      .subscribe(tipo => this.tipo = tipo);
      this.lectura = this.tipoService.lectura;
      this.creacion = false;
    }
  }

  volver(): void {
    if(this.lectura) {
      this.location.back();
    } else {
      this.modalService.open(ModalVolverComponent).result.then(() => this.location.back());
    }
  }

  guardar(): void {
    if(this.tipo.id) {
      this.modalService.open(ModalModificarComponent).result.then(() => this.modificar());
    } else {
      this.crear();
    }
  }

  crear(): void {
    this.tipoService.crearTipo(this.tipo)
    .subscribe(tipo => {
      this.tipo.id = tipo.id;
      this.mensaje = 'Se ha creado el registro';
      this.tipo.activo = true;
    });
    
  }

  modificar(): void {
    this.tipoService.modificarTipo(this.tipo)
    .subscribe(() => {
      this.mensaje = 'Se ha modificado el tipo'
    });
    ;
  }

  cambiarActivo(): void {
    if(this.tipo.id) {
      if(this.tipo.activo) {
        this.modalService.open(ModalBajaComponent).result.then(
          () => this.darBaja()
        );
      } else {
        this.modalService.open(ModalAltaComponent).result.then(
          () => this.darAlta()
        )       
      }
    }
  }

  darBaja(): void {
      this.tipoService.desactivarTipo(this.tipo.id)
      .subscribe(() => {
        this.tipo.activo = false;
        this.mensaje = 'Se ha dado de baja el tipo.'
      }) ;    
  }

  darAlta(): void {
    this.tipoService.activarTipo(this.tipo.id)
    .subscribe(() => {
      this.tipo.activo = true;
      this.mensaje = 'Se ha dado el tipo de alta'
    });
    
  }
 
}
