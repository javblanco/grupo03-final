export interface Producto {
    id: number;
    nombre: string;
    idTipoProducto: number;
    nombreTipoProducto: string;
    marca: string;
    modelo: string;
    cantidadAlmacen: number;
    cantidadTienda: number;
    cantidadTotal: number;
}