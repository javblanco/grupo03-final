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
import es.cic.bootcamp.grupo03final.excepcion.ProductoExcepcion;
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
			throw new ProductoExcepcion("No se puede realizar una operación de modificación");
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

			productoConversor.dtoToEntity(producto, dto);
			productoRepositorio.save(producto);
		}

	}

	public void delete(Long id) {
		productoRepositorio.deleteById(id);
	}

	public void devolverCantidadesTiendaAAlmacen(Long id, int cantidad) {

		Producto productoSeleccionado = comprobacionDeRegistroDeProducto(id);

		if (productoSeleccionado == null) {
			throw new ProductoExcepcion(
					"No se puede realizar una operación de devolución puesto que el id introducido es nulo");
		} else {

			if (cantidad <= productoSeleccionado.getCantidadUnidadesTienda() && cantidad > 0) {

				productoSeleccionado
						.setCantidadUnidadesAlmacen(productoSeleccionado.getCantidadUnidadesAlmacen() + cantidad);
				productoSeleccionado
						.setCantidadUnidadesTienda(productoSeleccionado.getCantidadUnidadesTienda() - cantidad);

				productoRepositorio.save(productoSeleccionado);

			} else {
				throw new ProductoExcepcion(
						"No se puede realizar una operación de devolución puesto que la cantidad indicada es superior a la almacenada en la tienda.");

			}
		}
	}

	public void transferirCantidadesAlmacenATienda(Long id, int cantidad) {

		Producto productoSeleccionado = comprobacionDeRegistroDeProducto(id);

		if (productoSeleccionado == null) {
			throw new ProductoExcepcion(
					"No se puede realizar una operación de traspaso puesto que el id introducido es nulo");
		} else {

			if (cantidad <= productoSeleccionado.getCantidadUnidadesAlmacen() && cantidad > 0) {

				productoSeleccionado
						.setCantidadUnidadesAlmacen(productoSeleccionado.getCantidadUnidadesAlmacen() - cantidad);
				productoSeleccionado
						.setCantidadUnidadesTienda(productoSeleccionado.getCantidadUnidadesTienda() + cantidad);
				productoRepositorio.save(productoSeleccionado);

				productoRepositorio.save(productoSeleccionado);
			} else if (cantidad == 0) {
				throw new ProductoExcepcion(
						"No se pueden traspasar 0 cantidades de un producto, no se ha seleccionado una cantidad a transferir.");

			} else {

				throw new ProductoExcepcion(
						"No se puede realizar una operación de traspaso puesto que la cantidad indicada es superior a la almacenada en la tienda.");

			}
		}
	}

	public void pedirNuevoStockAlmacen(Long id, int cantidad) {

		Producto productoSeleccionado = comprobacionDeRegistroDeProducto(id);

		if (productoSeleccionado == null) {
			throw new ProductoExcepcion(
					"No se puede realizar una operación de reposición puesto que el id introducido es nulo");
		} else {

			if (cantidad > 0) {

				productoSeleccionado
						.setCantidadUnidadesAlmacen(productoSeleccionado.getCantidadUnidadesAlmacen() + cantidad);
				productoRepositorio.save(productoSeleccionado);

			} else if (cantidad == 0) {
				throw new ProductoExcepcion(
						"No se pueden traspasar 0 cantidades de un producto, no se ha seleccionado una cantidad a transferir.");
			} else {

				throw new ProductoExcepcion(
						"No se puede realizar una operación de añadir stock al almacen, porque la cantidad introducida es negativa.");

			}
		}

	}

	private Producto comprobacionDeRegistroDeProducto(Long id) {

		Optional<Producto> productoExiste = productoRepositorio.findById(id);

		if (!productoExiste.isPresent()) {

			return null;

		} else {

			return productoExiste.get();
		}

	}

	public List<ProductoDto> getProductosTipoActivo() {

		List<Producto> listaCompleta = new ArrayList<>();
		List<Producto> listaActivos = new ArrayList<>();
		List<ProductoDto> listaDto = new ArrayList<>();

		productoRepositorio.findAll().forEach(listaCompleta::add);

		for (int i = 0; i < listaCompleta.size(); i++) {

			Producto p = listaCompleta.get(i);

			if (p.getTipoProducto().isActivo()) {

				listaActivos.add(p);

			}
		}

		listaActivos.forEach(p -> listaDto.add(productoConversor.entityToDto(p)));

		return listaDto;

	}

	public List<ProductoDto> getProductosEnStock() {
		List<Producto> listaCompleta = new ArrayList<>();
		List<Producto> listaEnStock = new ArrayList<>();
		List<ProductoDto> listaDto = new ArrayList<>();

		productoRepositorio.findAll().forEach(listaCompleta::add);

		for (int i = 0; i < listaCompleta.size(); i++) {

			Producto p = listaCompleta.get(i);
			int stockDisponible = p.getCantidadUnidadesAlmacen() + p.getCantidadUnidadesTienda();
			if (stockDisponible > 0) {
				listaEnStock.add(p);
			}
		}

		listaEnStock.forEach(p -> listaDto.add(productoConversor.entityToDto(p)));

		return listaDto;

	}

	public List<ProductoDto> getProductosEnStockAlmacen() {
		List<Producto> listaCompleta = new ArrayList<>();
		List<Producto> listaEnStock = new ArrayList<>();
		List<ProductoDto> listaDto = new ArrayList<>();

		productoRepositorio.findAll().forEach(listaCompleta::add);

		for (int i = 0; i < listaCompleta.size(); i++) {

			Producto p = listaCompleta.get(i);
			int stockDisponibleAlmacen = p.getCantidadUnidadesAlmacen();

			if (stockDisponibleAlmacen > 0) {
				listaEnStock.add(p);
			}
		}

		listaEnStock.forEach(p -> listaDto.add(productoConversor.entityToDto(p)));

		return listaDto;

	}

	public List<ProductoDto> getProductosEnStockTienda() {
		List<Producto> listaCompleta = new ArrayList<>();
		List<Producto> listaEnStock = new ArrayList<>();
		List<ProductoDto> listaDto = new ArrayList<>();

		productoRepositorio.findAll().forEach(listaCompleta::add);

		for (int i = 0; i < listaCompleta.size(); i++) {

			Producto p = listaCompleta.get(i);
			int stockDisponibleTienda = p.getCantidadUnidadesTienda();

			if (stockDisponibleTienda > 0) {
				listaEnStock.add(p);
			}
		}

		listaEnStock.forEach(p -> listaDto.add(productoConversor.entityToDto(p)));

		return listaDto;

	}
}
