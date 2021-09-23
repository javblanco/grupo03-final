import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Producto } from '../model/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  url = `${environment.host}/api/producto`;
  
  lectura = false;

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private http: HttpClient) { }

  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.url);
  }

  getProducto(id: number): Observable<Producto> {
    let idUrl = `${this.url}/${id}`;

    return this.http.get<Producto>(idUrl);
  }

  crearProducto(producto: Producto): Observable<number> {
    return this.http.post<number>(this.url, producto, this.options);
  }

  modificarProducto(producto: Producto): Observable<any> {
    return this.http.put(this.url, producto, this.options);
  }

  getProductosTipoActivo(): Observable<Producto[]> {
    const urlActivo = `${this.url}/tipo-activo`;
    
    return this.http.get<Producto[]>(urlActivo);
  }
  activarSoloLectura(): void {
    this.lectura = true;
  }

  desactivarSoloLectura(): void {
    this.lectura = false;
  }
}
