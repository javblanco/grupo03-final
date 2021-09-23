package es.cic.bootcamp.grupo03final.dto;

import java.util.Objects;

import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;

public class ProductoDto {
	
	private Long id;
	
	private Long idTipoProducto;
	private String nombreTipoProducto;
	private TipoProducto tipoProducto;
	
	// Campos de texto libre
	private String nombre;
	private String marca;
	private String modelo;
	private String descripción;
	
	private int cantidadUnidadesTienda;
	private int cantidadUnidadesAlmacen;
	
	public ProductoDto() {
		super();
	}

	public ProductoDto(Long id, Long idTipoProducto, String nombreTipoProducto, TipoProducto tipoProducto,
			String nombre, String marca, String modelo, String descripción, int cantidadUnidadesTienda,
			int cantidadUnidadesAlmacen) {
		super();
		this.id = id;
		this.idTipoProducto = idTipoProducto;
		this.nombreTipoProducto = nombreTipoProducto;
		this.tipoProducto = tipoProducto;
		this.nombre = nombre;
		this.marca = marca;
		this.modelo = modelo;
		this.descripción = descripción;
		this.cantidadUnidadesTienda = cantidadUnidadesTienda;
		this.cantidadUnidadesAlmacen = cantidadUnidadesAlmacen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTipoProducto() {
		return idTipoProducto;
	}

	public void setIdTipoProducto(Long idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
	
	public String getNombreTipoProducto() {
		return nombreTipoProducto;
	}

	public void setNombreTipoProducto(String nombreTipoProducto) {
		this.nombreTipoProducto = nombreTipoProducto;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescripción() {
		return descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
	}

	public int getCantidadUnidadesTienda() {
		return cantidadUnidadesTienda;
	}

	public void setCantidadUnidadesTienda(int cantidadUnidadesTienda) {
		this.cantidadUnidadesTienda = cantidadUnidadesTienda;
	}

	public int getCantidadUnidadesAlmacen() {
		return cantidadUnidadesAlmacen;
	}

	public void setCantidadUnidadesAlmacen(int cantidadUnidadesAlmacen) {
		this.cantidadUnidadesAlmacen = cantidadUnidadesAlmacen;
	}

	@Override
	public String toString() {
		return "ProductoDto [id=" + id + ", nombre=" + nombre + ", idTipoProducto=" + idTipoProducto
				+ ", nombreTipoProducto=" + nombreTipoProducto + ", tipoProducto=" + tipoProducto + ", marca=" + marca
				+ ", modelo=" + modelo + ", descripción=" + descripción + ", cantidadUnidadesTienda="
				+ cantidadUnidadesTienda + ", cantidadUnidadesAlmacen=" + cantidadUnidadesAlmacen + "]";
	}

	@Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductoDto other = (ProductoDto) obj;
        return Objects.equals(id, other.id) ;
    }

}
