package es.cic.bootcamp.grupo03final.controlador;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.bootcamp.grupo03final.dto.TipoProductoDto;
import es.cic.bootcamp.grupo03final.servicio.TipoProductoServicio;

	@RestController
	@RequestMapping("/api/tipo-producto")
	@CrossOrigin(origins = "http://localhost:4200")
	
	public class TipoProductoControlador {
		
		private static final Logger LOGGER = Logger.getLogger(TipoProductoControlador.class.getName());
		
		@Autowired
		private TipoProductoServicio tipoProductoServicio;
		

		@GetMapping("/{id}")
		public TipoProductoDto leer(@PathVariable(required = true, name = "id") Long id) {
			LOGGER.info("lee un registro de tipo de producto");
			return tipoProductoServicio.leer(id);
		}
		
		@PostMapping
		public TipoProductoDto crear(@Valid @RequestBody TipoProductoDto tipoProducto) {
			LOGGER.info("crea un registro de tipo de producto");
			return tipoProductoServicio.crear(tipoProducto);
		}


		@GetMapping
		public List<TipoProductoDto> listar() {
			LOGGER.info("lista todos los productos");
			return tipoProductoServicio.listar();
		}
		
		@PutMapping
		public void modificar(@Valid @RequestBody TipoProductoDto tipoProducto) {
			LOGGER.info("modifica un registro de tipo de producto");
			tipoProductoServicio.modificar(tipoProducto);
		}
		
		
		@PostMapping("/desactivar/{id}")
		public void darDeBaja(@PathVariable(required = true, name="id") Long id) {
			LOGGER.info("desactiva un tipo de producto");
			tipoProductoServicio.darDeBaja(id);
		}
		
		@PostMapping("/activar/{id}")
		public void darDeAlta(@PathVariable(required = true, name="id") Long id) {
			LOGGER.info("activo un tipo de producto");
			tipoProductoServicio.darDeAlta(id);
		}
		
		@GetMapping("/activo")
		public List<TipoProductoDto> getProductosTipoActivo() {
			LOGGER.info("listo los tipos de producto activos");
			return tipoProductoServicio.listarActivos();
		}

	}
