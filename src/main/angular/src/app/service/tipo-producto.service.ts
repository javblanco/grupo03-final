import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TipoProducto } from '../model/tipoProducto';

@Injectable({
  providedIn: 'root'
})
export class TipoProductoService {

  url = 'api/tipoProducto';

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private http: HttpClient) { }

  getTipos(): Observable<TipoProducto[]> {
    return this.http.get<TipoProducto[]>(this.url);
  }
}
