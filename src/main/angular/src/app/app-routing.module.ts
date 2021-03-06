import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './index/index.component';
import { ProductoDetalleComponent } from './producto-detalle/producto-detalle.component';
import { ProductoMaestroComponent } from './producto-maestro/producto-maestro.component';
import { TipoProductoDetalleComponent } from './tipo-producto-detalle/tipo-producto-detalle.component';
import { TipoProductoMaestroComponent } from './tipo-producto-maestro/tipo-producto-maestro.component';
import { TransferenciaComponent } from './transferencia/transferencia.component';

const routes: Routes = [
  {path: 'index', component: IndexComponent},
  {path: '', redirectTo: 'index', pathMatch: 'full'},
  {path: 'tipo-producto/listar', component: TipoProductoMaestroComponent},
  {path: 'tipo-producto', component: TipoProductoDetalleComponent},
  {path: 'tipo-producto/:id', component: TipoProductoDetalleComponent},
  {path: 'producto/listar', component: ProductoMaestroComponent},
  {path: 'producto', component: ProductoDetalleComponent},
  {path: 'producto/:id', component: ProductoDetalleComponent},
  {path: 'transferir', component: TransferenciaComponent},
  {path: 'devolver', component: TransferenciaComponent},
  {path: 'reponer', component: TransferenciaComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
