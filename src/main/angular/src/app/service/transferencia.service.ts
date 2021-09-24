import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TransferenciaService {
  url = `${environment.host}/api/producto`;

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private http: HttpClient) { }

  transferir(id:number, cantidad: number): Observable<any> {
    let peticion = `${this.url}/transferir/${id}`;
    return this.http.post(peticion, cantidad, this.options);
  }

  devolver(id:number, cantidad: number): Observable<any> {
    let peticion = `${this.url}/devolver/${id}`;
    return this.http.post(peticion, cantidad, this.options);
  }

  reponer(id:number, cantidad: number): Observable<any> {
    let peticion = `${this.url}/pedir/${id}`;
    return this.http.post(peticion, cantidad, this.options);
  }
  
}
