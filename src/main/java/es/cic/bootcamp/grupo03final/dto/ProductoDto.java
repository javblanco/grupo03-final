package es.cic.bootcamp.grupo03final.dto;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;

public class ProductoDto {
	
	private Long id;
	
	private Long idTipoProducto;
	private String nombreTipoProducto;
	private TipoProducto tipoProducto;
	
	@NotBlank
	private String nombre;
	private String marca;
	private String modelo;
	private String descripcion;
	
	@Min(value = 0)
	private int cantidadUnidadesTienda;

	@Min(value = 0)
	private int cantidadUnidadesAlmacen;
	
	public ProductoDto() {
		super();
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
				+ ", modelo=" + modelo + ", descripcion=" + descripcion + ", cantidadUnidadesTienda="
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
