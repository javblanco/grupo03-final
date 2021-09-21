import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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


  


  constructor(private tipoService: TipoProductoService,
    private location: Location,
    private routes: ActivatedRoute) { }

  ngOnInit(): void {
    this.getTipo();
  }


  getTipo(): void {
    let id = Number(this.routes.snapshot.paramMap.get('id'));

    if(id) {
      this.tipoService.getTipo(id)
      .subscribe(tipo => this.tipo = tipo);
      this.lectura = this.tipoService.lectura;
    }
  }

  volver(): void {
    this.location.back();
  }

  guardar(): void {
    if(this.tipo.id) {
      this.modificar();
    } else {
      this.crear();
    }
  }

  crear(): void {
    this.tipoService.crearTipo(this.tipo)
    .subscribe(id => this.tipo.id = id);
    this.mensaje = 'Se ha creado el registro';
  }

  modificar(): void {
    this.tipoService.modificarTipo(this.tipo)
    .subscribe();
    this.mensaje = 'Se ha modificado el registro';
  }

  cambiarActivo(): void {
    if(this.tipo.id) {
      if(this.tipo.activo) {
        this.tipo.activo = false;
        this.tipoService.modificarTipo(this.tipo)
        .subscribe();
        this.mensaje = 'Se ha dado el registro de baja';
      } else {
        this.tipo.activo = true;
        this.tipoService.modificarTipo(this.tipo)
        .subscribe();
        this.mensaje = 'Se ha dado el registro de alta';
      }
    }
  }

 
}
