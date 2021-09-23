package es.cic.bootcamp.grupo03final.modelo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private TipoProducto tipoProducto;
	
	private String nombre;
	private String marca;
	private String modelo;
	private String descripción;
	
	private int cantidadUnidadesTienda;
	private int cantidadUnidadesAlmacen;
	
	public Producto() {
		super();
	}
	
	public Producto(Long id, TipoProducto tipoProducto, String nombre, String marca, String modelo, String descripción,
			int cantidadUnidadesTienda, int cantidadUnidadesAlmacen) {
		super();
		this.id = id;
		this.tipoProducto = tipoProducto;
		this.nombre = nombre;
		this.marca = marca;
		this.modelo = modelo;
		this.descripción = descripción;
		this.cantidadUnidadesTienda = cantidadUnidadesTienda;
		this.cantidadUnidadesAlmacen = cantidadUnidadesAlmacen;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		return "Producto [id=" + id + ", tipoProducto=" + tipoProducto + ", nombre=" + nombre + ", marca=" + marca
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
        Producto other = (Producto) obj;
        return Objects.equals(id, other.id) ;
    }
    
}
