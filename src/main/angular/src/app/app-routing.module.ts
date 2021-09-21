import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductoMaestroComponent } from './producto-maestro/producto-maestro.component';
import { TipoProductoDetalleComponent } from './tipo-producto-detalle/tipo-producto-detalle.component';
import { TipoProductoMaestroComponent } from './tipo-producto-maestro/tipo-producto-maestro.component';

const routes: Routes = [
  {path: 'tipo-producto/listar', component: TipoProductoMaestroComponent},
  {path: 'tipo-producto', component: TipoProductoDetalleComponent},
  {path: 'tipo-producto/:id', component: TipoProductoDetalleComponent},
  {path: 'producto/listar', component: ProductoMaestroComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
