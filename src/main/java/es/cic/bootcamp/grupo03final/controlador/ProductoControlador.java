package es.cic.bootcamp.grupo03final.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.cic.bootcamp.grupo03final.dto.ProductoDto;
import es.cic.bootcamp.grupo03final.servicio.ProductoServicio;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/producto")
public class ProductoControlador {

	@Autowired
	private ProductoServicio productoServicio;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Long create(@Valid @RequestBody ProductoDto dto) {
		
		return productoServicio.create(dto);
		
	}
	
	@GetMapping
	public List<ProductoDto> list() {
		
		return productoServicio.list();
		
	}
	
	@GetMapping("/{id}")
	public ProductoDto read(@PathVariable(required = true, name = "id") Long id) {
		return productoServicio.read(id);
	}
	
	@PutMapping
	public void update(@Valid @RequestBody ProductoDto dto) {
		productoServicio.update(dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true, name = "id") Long id) {
		productoServicio.delete(id);
	}

	
	@PostMapping("/transferir/{id}")
	public void transferirTienda(@PathVariable(required = true, name = "id") Long id, @RequestBody int cantidad) {

				
		productoServicio.transferirCantidadesAlmacenATienda(id, cantidad);
		
	}
	
	@PostMapping("/devolver/{id}")
	public void devolverAlmacen(@PathVariable(required = true, name = "id") Long id, @RequestBody int cantidad) {
		
		productoServicio.devolverCantidadesTiendaAAlmacen(id, cantidad);
		
		
	}
	
	@PostMapping("/pedir/{id}")
	public void pedirAlmacen(@PathVariable(required = true, name = "id") Long id, @RequestBody int cantidad) {
		
		productoServicio.pedirNuevoStockAlmacen(id, cantidad);
		
	}
	
	@GetMapping("/tipo-activo")
	public List<ProductoDto> getProductosTipoActivo() {
		
		return productoServicio.getProductosTipoActivo();
		
	}
	
	@GetMapping("/stock")
	public List<ProductoDto> getProductosEnStock() {
		
		return productoServicio.getProductosEnStock();
	}
	
	@GetMapping("/almacen")
	public List<ProductoDto> getProductosEnStockAlmacen() {
		
		return productoServicio.getProductosEnStockAlmacen();
	}

	@GetMapping("/tienda")
	public List<ProductoDto> getProductosEnStockTienda() {
		
		return productoServicio.getProductosEnStockTienda();
	}
	
}
