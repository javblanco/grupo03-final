package es.cic.bootcamp.grupo03final.conversor;

import javax.validation.Valid;

import es.cic.bootcamp.grupo03.dto.ProductoDto;
import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;

public class ProductoConversor {

	
	public Producto dtoToEntity(@Valid ProductoDto dto, TipoProducto tipoProducto) {
		
		Producto producto = new Producto();
		
		producto.setId(dto.getId());
		producto.setNombre(dto.getNombre());
		producto.setMarca(dto.getMarca());
		producto.setModelo(dto.getModelo());
		producto.setDescripción(dto.getDescripción());
		
		
		if(tipoProducto !=null) {
			producto.setTipoProducto(tipoProducto);
		}
		
		return producto;

	}

	public ProductoDto entityToDto(Producto p) {
		
		ProductoDto dto = new ProductoDto();
		
		dto.setCantidadUnidadesAlmacen(p.getCantidadUnidadesAlmacen());
		dto.setCantidadUnidadesTienda(p.getCantidadUnidadesTienda());
		dto.setDescripción(p.getDescripción());
		dto.setId(p.getId());
		dto.setMarca(p.getMarca());
		dto.setModelo(p.getModelo());
		dto.setNombre(p.getNombre());
				
		if(p.getTipoProducto() !=null) {
			dto.setIdGrupo(p.getTipoProducto().//TODO());
			dto.setGrupo(p.getTipoProducto().//TODO);
		}
		
		return dto;
	}

	public void dtoToEntity(Producto producto, @Valid ProductoDto dto, TipoProducto tipoProducto) {

		if (dto.getId() != null) {
			producto.setId(dto.getId());
		}
		if (dto.getNombre() != null) {
			producto.setNombre(dto.getNombre());
		}
		if (dto.getModelo() != null) {
			producto.setModelo(dto.getModelo());
		}
		if (dto.getDescripción() != null) {
			producto.setDescripción(dto.getDescripción());
		}
		if (dto.getMarca() != null) {
			producto.setNombre(dto.getNombre());
		}
		if (dto.getTipoProducto() != null) {
			producto.setTipoProducto(dto.getTipoProducto());
		}
		
		producto.setCantidadUnidadesAlmacen(dto.getCantidadUnidadesAlmacen());

		producto.setCantidadUnidadesTienda(dto.getCantidadUnidadesTienda());

	}

}
