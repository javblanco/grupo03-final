export interface Producto {
    id: number;
    nombre: string;
    idTipoProducto: number;
    nombreTipoProducto: string;
    marca: string;
    modelo: string;
    cantidadUnidadesTienda: number;
    cantidadUnidadesAlmacen: number;
    cantidadTotal: number;
}