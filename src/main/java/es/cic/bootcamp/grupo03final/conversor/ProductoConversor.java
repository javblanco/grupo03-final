package es.cic.bootcamp.grupo03final.conversor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import org.springframework.stereotype.Component;

import es.cic.bootcamp.grupo03final.dto.ProductoDto;
import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;

@Component
public class ProductoConversor {

	public Producto dtoToEntity(@Valid ProductoDto dto, TipoProducto tipoProducto) {

		Producto producto = new Producto();

		producto.setId(dto.getId());
		producto.setNombre(dto.getNombre());
		producto.setMarca(dto.getMarca());
		producto.setModelo(dto.getModelo());
		producto.setDescripcion(dto.getDescripcion());

		if (tipoProducto != null) {
			producto.setTipoProducto(tipoProducto);
		}

		producto.setCantidadUnidadesAlmacen(dto.getCantidadUnidadesAlmacen());
		producto.setCantidadUnidadesTienda(dto.getCantidadUnidadesTienda());

		return producto;

	}

	public ProductoDto entityToDto(Producto p) {

		ProductoDto dto = new ProductoDto();

		dto.setCantidadUnidadesAlmacen(p.getCantidadUnidadesAlmacen());
		dto.setCantidadUnidadesTienda(p.getCantidadUnidadesTienda());
		dto.setDescripcion(p.getDescripcion());
		dto.setId(p.getId());
		dto.setMarca(p.getMarca());
		dto.setModelo(p.getModelo());
		dto.setNombre(p.getNombre());

		if (p.getTipoProducto() != null) {
			dto.setIdTipoProducto(p.getTipoProducto().getId());
			dto.setNombreTipoProducto(p.getTipoProducto().getNombre());
			dto.setTipoProducto(p.getTipoProducto());
		}

		return dto;
	}

	public void dtoToEntity(Producto producto, @Valid ProductoDto dto) {

		if (dto.getId() != null) {
			producto.setId(dto.getId());
		}
		if (dto.getNombre() != null) {
			producto.setNombre(dto.getNombre());
		}
		if (dto.getModelo() != null) {
			producto.setModelo(dto.getModelo());
		}
		if (dto.getDescripcion() != null) {
			producto.setDescripcion(dto.getDescripcion());
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

	public Object entityListToDtoList(List<Producto> listaProducto) {
		
		List<ProductoDto> lista = new ArrayList<>();

		listaProducto.forEach(p -> lista.add(this.entityToDto(p)));

		return lista;

	}

}
