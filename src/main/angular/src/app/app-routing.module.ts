import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TipoProductoMaestroComponent } from './tipo-producto-maestro/tipo-producto-maestro.component';

const routes: Routes = [
  {path: 'tipo-producto/listar', component: TipoProductoMaestroComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
