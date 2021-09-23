import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TipoProducto } from '../model/tipoProducto';

@Injectable({
  providedIn: 'root'
})
export class TipoProductoService {

  url = `${environment.host}/api/tipo-producto`;

  lectura = false;

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private http: HttpClient) { }

  getTipos(): Observable<TipoProducto[]> {
    return this.http.get<TipoProducto[]>(this.url);
  }

  getTipo(id: number): Observable<TipoProducto> {
    const idUrl = `${this.url}/${id}`;
    
    return this.http.get<TipoProducto>(idUrl);
  }

  crearTipo(tipo: TipoProducto): Observable<TipoProducto> {
    return this.http.post<TipoProducto>(this.url, tipo, this.options);
  }

  modificarTipo(tipo: TipoProducto): Observable<any> {
    return this.http.put<any>(this.url, tipo, this.options);
  }

  activarTipo(id: number) {
    const idUrl = `${this.url}/activar/${id}`;

    return this.http.post(idUrl, this.options);
  }

  desactivarTipo(id: number) {
    const idUrl = `${this.url}/desactivar/${id}`;

    return this.http.post(idUrl, this.options);
  }

  activarSoloLectura(): void {
    this.lectura = true;
  }

  desactivarSoloLectura(): void {
    this.lectura = false;
  }
}
