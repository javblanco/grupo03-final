package es.cic.bootcamp.grupo03final.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.grupo03.dto.TipoProductoDto;
import es.cic.bootcamp.grupo03final.conversor.TipoProductoConversor;
import es.cic.bootcamp.grupo03final.excepcion.TipoProductoExcepcion;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootstrap.grupo03final.repositorio.TipoProductoRepositorio;

@Service
@Transactional
public class TipoProductoServicio {
	
	private static final String MENSAJE_EXCEPCION = "No se realiza la operación";

@Autowired
private TipoProductoRepositorio tipoProductoRepositorio;

@Autowired
private TipoProductoConversor tipoProductoConversor;

public TipoProductoDto crear(TipoProductoDto dto) {
	if(dto.getId() != null) {
		throw new TipoProductoExcepcion(MENSAJE_EXCEPCION);
	}
	
	TipoProducto tipoProducto = tipoProductoConversor.dtoToEntity(dto);
	
	return tipoProductoConversor.entityToDto(tipoProductoRepositorio.save(tipoProducto));
}

public TipoProductoDto leer(Long id) {
	Optional<TipoProducto> optional = tipoProductoRepositorio.findById(id);
	
	if(optional.isPresent()) {
		return tipoProductoConversor.entityToDto(optional.get());
	}else{
		return null;
	}
}

public List<TipoProductoDto> listar() {
	List<TipoProducto> lista = new ArrayList<>();
	
	tipoProductoRepositorio.findAll().forEach(lista::add);
	
	return tipoProductoConversor.entityListToDtoList(lista);
}

public TipoProductoDto modificar(TipoProductoDto dto) {
	
	if(dto.getId() == null) {
		throw new TipoProductoExcepcion(MENSAJE_EXCEPCION);
	}
	
	TipoProducto tipoProducto = tipoProductoConversor.dtoToEntity(dto);
	
	return tipoProductoConversor.entityToDto(tipoProductoRepositorio.save(tipoProducto));
}

//public void borrar(Long id) {
	//tipoProductoRepositorio.deleteById(id);
//}

public void darDeBaja(Long id) {
	Optional<TipoProducto> optional = tipoProductoRepositorio.findById(id);
	TipoProducto tipoProducto = new TipoProducto();
	
	if(optional.isPresent()) {
		tipoProducto = optional.get();
		tipoProducto.setActivo(false);
	}else{
		throw new TipoProductoExcepcion(MENSAJE_EXCEPCION);
	}
}

public void darDeAlta(Long id) {
	Optional<TipoProducto> optional = tipoProductoRepositorio.findById(id);
	TipoProducto tipoProducto = new TipoProducto();
	
	if(optional.isPresent()) {
		tipoProducto = optional.get();
		tipoProducto.setActivo(true);
	}else{
		throw new TipoProductoExcepcion(MENSAJE_EXCEPCION);
	}
}
	
}



