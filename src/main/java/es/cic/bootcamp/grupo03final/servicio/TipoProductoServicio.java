package es.cic.bootcamp.grupo03final.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.grupo03final.conversor.TipoProductoConversor;
import es.cic.bootcamp.grupo03final.dto.TipoProductoDto;
import es.cic.bootcamp.grupo03final.excepcion.TipoProductoExcepcion;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.TipoProductoRepositorio;

@Service
@Transactional
public class TipoProductoServicio {

	private static final String MENSAJE_EXCEPCION = "No se realiza la operación";

	@Autowired
	private TipoProductoRepositorio tipoProductoRepositorio;

	@Autowired
	private TipoProductoConversor tipoProductoConversor;

	public TipoProductoDto crear(TipoProductoDto dto) {
		if (dto.getId() != null) {
			throw new TipoProductoExcepcion(MENSAJE_EXCEPCION);
		}

		TipoProducto tipoProducto = tipoProductoConversor.dtoToEntity(dto);

		return tipoProductoConversor.entityToDto(tipoProductoRepositorio.save(tipoProducto));
	}

	public TipoProductoDto leer(Long id) {
		Optional<TipoProducto> optional = tipoProductoRepositorio.findById(id);

		if (optional.isPresent()) {
			return tipoProductoConversor.entityToDto(optional.get());
		} else {
			return null;
		}
	}

	public List<TipoProductoDto> listar() {
		List<TipoProducto> lista = new ArrayList<>();

		tipoProductoRepositorio.findAll().forEach(lista::add);

		return tipoProductoConversor.entityListToDtoList(lista);
	}

	public void modificar(TipoProductoDto dto) {

		if (dto.getId() == null) {
			throw new TipoProductoExcepcion(MENSAJE_EXCEPCION);
		}

		TipoProducto tipoProducto = tipoProductoConversor.dtoToEntity(dto);

		tipoProductoConversor.entityToDto(tipoProductoRepositorio.save(tipoProducto));
	}

//public void borrar(Long id) {
	// tipoProductoRepositorio.deleteById(id);
//}

	public void darDeBaja(Long id) {
		Optional<TipoProducto> optional = tipoProductoRepositorio.findById(id);
		TipoProducto tipoProducto = null;

		if (optional.isPresent()) {
			tipoProducto = optional.get();
		} else {
			throw new TipoProductoExcepcion(MENSAJE_EXCEPCION);
		}
		if (tipoProducto.isActivo() == true) {
			tipoProducto.setActivo(false);
			} else {
				throw new TipoProductoExcepcion("Ya estaba dado de baja el producto");
			}
		}


	public void darDeAlta(Long id) {
		Optional<TipoProducto> optional = tipoProductoRepositorio.findById(id);
		TipoProducto tipoProducto = new TipoProducto();

		if (optional.isPresent()) {
			tipoProducto = optional.get();
		} else {
			throw new TipoProductoExcepcion(MENSAJE_EXCEPCION);
		}
		if (tipoProducto.isActivo() == false) {
			tipoProducto.setActivo(true);
			} else {
				throw new TipoProductoExcepcion("Ya estaba dado de alta el producto");
			}
		}
	
	public List<TipoProductoDto> listarActivos() {
		
		List<TipoProducto> listaCompleta = new ArrayList<>();
		List<TipoProducto> listaActivos = new ArrayList<>();
		List<TipoProductoDto> listaDto = new ArrayList<>();

		tipoProductoRepositorio.findAll().forEach(listaCompleta::add);
		
		for (int i=0; i< listaCompleta.size(); i++) {
			
			TipoProducto p = listaCompleta.get(i);
			
			if ( p.isActivo() == true) {
				
				listaActivos.add(p);
			}
		}
		
		listaActivos.forEach(p -> listaDto.add(tipoProductoConversor.entityToDto(p)));

		return listaDto;
		
	}

	

}
