package es.cic.bootcamp.grupo03final.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.grupo03final.conversor.ProductoConversor;
import es.cic.bootcamp.grupo03final.dto.ProductoDto;
import es.cic.bootcamp.grupo03final.excepcion.CreateProductoExcepcion;
import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.ProductoRepositorio;
import es.cic.bootcamp.grupo03final.repositorio.TipoProductoRepositorio;

@Service
@Transactional
public class ProductoServicio {

	@Autowired
	private ProductoRepositorio productoRepositorio;

	@Autowired
	private ProductoConversor productoConversor;

	@Autowired
	private TipoProductoRepositorio tipoProductoRepository;

	public Long create(@Valid ProductoDto dto) {

		if (dto.getId() != null) {
			throw new CreateProductoExcepcion("No se puede realizar una operación de modificación");
		}

		Optional<TipoProducto> optional = tipoProductoRepository.findById(dto.getIdTipoProducto());

		TipoProducto tipoProducto = null;

		if (optional.isPresent()) {
			tipoProducto = optional.get();
		}

		Producto resultado = productoRepositorio.save(productoConversor.dtoToEntity(dto, tipoProducto));

		return resultado.getId();

	}

	public List<ProductoDto> list() {

		List<Producto> lista = new ArrayList<>();

		productoRepositorio.findAll().forEach(lista::add);

		List<ProductoDto> listaDto = new ArrayList<>();

		lista.forEach(p -> listaDto.add(productoConversor.entityToDto(p)));

		return listaDto;

	}

	public ProductoDto read(Long id) {

		Optional<Producto> optional = productoRepositorio.findById(id);

		if (optional.isPresent()) {
			Producto producto = optional.get();
			return productoConversor.entityToDto(producto);
		} else {
			return null;
		}

	}

	public void update(@Valid ProductoDto dto) {

		Optional<Producto> optional = productoRepositorio.findById(dto.getId());

		if (optional.isPresent()) {
			Producto producto = optional.get();

			productoConversor.dtoToEntity(producto, dto, producto.getTipoProducto());
			productoRepositorio.save(producto);
		}

	}

	public void delete(Long id) {
		productoRepositorio.deleteById(id);
	}

	public void devolverCantidadesTiendaAAlmacen(Long id, int cantidad) {

		Producto productoSeleccionado = comprobacionDeRegistroDeProducto(id);

		if (productoSeleccionado == null) {
			throw new CreateProductoExcepcion(
					"No se puede realizar una operación de devolución puesto que el id introducido es nulo");
		} else {

			if (cantidad <= productoSeleccionado.getCantidadUnidadesTienda() && cantidad > 0) {

				// Realiza la operación correctamente

				productoSeleccionado
						.setCantidadUnidadesAlmacen(productoSeleccionado.getCantidadUnidadesAlmacen() + cantidad);
				productoSeleccionado
						.setCantidadUnidadesTienda(productoSeleccionado.getCantidadUnidadesTienda() - cantidad);

			} else {

				// Si el numero introducido es mayor que las cantidades en tienda ERROR
				throw new CreateProductoExcepcion(
						"No se puede realizar una operación de devolución puesto que la cantidad indicada es superior a la almacenada en la tienda.");

			}
		}
	}

	public void transferirCantidadesAlmacenATienda(Long id, int cantidad) {

		Producto productoSeleccionado = comprobacionDeRegistroDeProducto(id);

		if (productoSeleccionado == null) {
			throw new CreateProductoExcepcion(
					"No se puede realizar una operación de devolución puesto que el id introducido es nulo");
		} else {

			if (cantidad <= productoSeleccionado.getCantidadUnidadesAlmacen() && cantidad > 0) {

				// Realiza la operación correctamente

				productoSeleccionado
						.setCantidadUnidadesAlmacen(productoSeleccionado.getCantidadUnidadesAlmacen() - cantidad);
				productoSeleccionado
						.setCantidadUnidadesTienda(productoSeleccionado.getCantidadUnidadesTienda() + cantidad);

			} else {

				// Si el numero introducido es mayor que las cantidades en tienda ERROR
				throw new CreateProductoExcepcion(
						"No se puede realizar una operación de devolución puesto que la cantidad indicada es superior a la almacenada en la tienda.");

			}
		}
	}

	public void pedirNuevoStockAlmacen(Long id, int cantidad) {
		
		Producto productoSeleccionado = comprobacionDeRegistroDeProducto(id);

		if (productoSeleccionado == null) {
			throw new CreateProductoExcepcion(
					"No se puede realizar una operación de devolución puesto que el id introducido es nulo");
		} else {

			if (cantidad > 0) {

				// Realiza la operación correctamente

				productoSeleccionado
						.setCantidadUnidadesAlmacen(productoSeleccionado.getCantidadUnidadesAlmacen() + cantidad);

			} else {

				// Si el numero introducido es mayor que las cantidades en tienda ERROR
				throw new CreateProductoExcepcion(
						"No se puede realizar una operación de añadir stock al almacen, porque la cantidad introducida es negativa.");

			}
		}
		
	}
	
	
	private Producto comprobacionDeRegistroDeProducto(Long id) {

		Optional<Producto> productoExiste = productoRepositorio.findById(id);

		if (productoExiste.isPresent() == false) {

			return null;

		} else {

			Producto productoSeleccionado = productoExiste.get();
			return productoSeleccionado;
		}

	}

}
