package es.cic.bootcamp.grupo03final.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class TipoProductoDto {

	private Long id;
	
	@Length(max=70, min=2)
	@NotNull(message = "{!}-->DEBE RELLENAR EL CAMPO DE NOMBRE.")
	private String nombre;
	
	@Length(max=256)
	private String descripcion;
	
	private boolean activo = true;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
