package es.cic.bootcamp.grupo03final.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.cic.bootcamp.grupo03.dto.TipoProductoDto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;

@Component
public class TipoProductoConversor {

	public TipoProductoDto entityToDto(TipoProducto tipoProducto) {
		TipoProductoDto dto= new TipoProductoDto();
		
		dto.setId(tipoProducto.getId());
		
		dto.setNombre(tipoProducto.getNombre());
		dto.setDescripcion(tipoProducto.getDescripcion());
		dto.setActivo(tipoProducto.isActivo());
		
		return dto;
		}

public List<TipoProductoDto> entityListToDtoList(List<TipoProducto> listaTipoProducto) {
	List<TipoProductoDto> lista = new ArrayList<>();
	
	listaTipoProducto.forEach(
			tipoProducto -> lista.add(this.entityToDto(tipoProducto))
			);
	return lista;
}

public TipoProducto dtoToEntity(TipoProductoDto dto) {
	TipoProducto tipoProducto = new TipoProducto();
	
	tipoProducto.setId(dto.getId());
	tipoProducto.setNombre(dto.getNombre());
	tipoProducto.setDescripcion(dto.getDescripcion());
	tipoProducto.setActivo(dto.isActivo());
	
	return tipoProducto;
}
}
