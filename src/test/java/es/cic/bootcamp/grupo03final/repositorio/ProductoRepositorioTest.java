package es.cic.bootcamp.grupo03final.repositorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;

@DataJpaTest
class ProductoRepositorioTest {

	@Autowired
	private ProductoRepositorio productoRepositorio;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@BeforeEach
	void setUp() throws Exception {
		
	}
	
	@Test
	void testCrear() {
	Producto producto = generarProducto();
	TipoProducto tipoProducto = generarTipoProducto();
	entityManager.persistAndFlush(tipoProducto);
	
	producto.setTipoProducto(tipoProducto);
	Producto resultado = productoRepositorio.save(producto);
	entityManager.flush();
	
	assertNotNull(resultado.getId(),"La clave primaria no deberia ser nula" );
	Producto productoEnBBDD = entityManager.find(Producto.class, resultado.getId());
	assertEquals(resultado,productoEnBBDD,"No se ha guardado el producto");
	}
	
	@Test
	void testLeer() {
		Producto producto = generarProducto();
		TipoProducto tipoProducto = generarTipoProducto();
		entityManager.persistAndFlush(tipoProducto);
		producto.setTipoProducto(tipoProducto);
		
		Producto productoALeer = entityManager.persist(producto);
		
		Optional<Producto> optional = productoRepositorio.findById(productoALeer.getId());
	
		if (optional.isPresent()) {
			Producto productoLeido = optional.get();
			
			assertEquals(productoALeer,productoLeido,"No se ha conseguido leer el producto");
		}else {
			assertNull(optional,"La busqueda del producto ha sido nula"	);
			}
	}
	
	@Test
	void testListar() {
		Producto producto = generarProducto();
		TipoProducto tipoProducto = generarTipoProducto();
		entityManager.persistAndFlush(tipoProducto);
		producto.setTipoProducto(tipoProducto);
		
		Producto productoALeer = productoRepositorio.save(producto);
		
		Producto producto2 = generarProducto();
		TipoProducto tipoProducto2 = generarTipoProducto();
		entityManager.persistAndFlush(tipoProducto2);
		producto.setTipoProducto(tipoProducto2);
		Producto productoALeer2 =  productoRepositorio.save(producto2);
		
		List<Producto> listaProducto = List.of(productoALeer,productoALeer2);
		
		List<Producto> listaEnBBDD = new ArrayList<Producto>();
		
		productoRepositorio.findAll().forEach(listaEnBBDD::add);
		
		assertThat(listaEnBBDD.size())
		.isGreaterThanOrEqualTo(listaProducto.size());
		
	}
	
	@Test
	void testModificar() {
		Producto producto = generarProducto();
		TipoProducto tipoProducto = generarTipoProducto();
		entityManager.persistAndFlush(tipoProducto);
		producto.setTipoProducto(tipoProducto);
		
		Producto productoALeer = entityManager.persist(producto);
		entityManager.flush();
		Producto productoEnBBDD = productoRepositorio.findById(productoALeer.getId()).get();
		
		productoEnBBDD.setNombre("Electrodoméstico para lavar");
		entityManager.flush();
		
		Producto productoTrasActualizar = entityManager.find(Producto.class , productoALeer.getId());
		
		assertEquals("Electrodoméstico para lavar",productoTrasActualizar.getNombre(),"No se ha actualizado el nombre del producto");
		
	}
	
	
	
	private Producto generarProducto() {
		Producto producto = new Producto();
		producto.setCantidadUnidadesAlmacen(5);
		producto.setCantidadUnidadesTienda(7);
		producto.setDescripcion("Chocolate con sabor a oreo");
		producto.setMarca("Milka");
		producto.setModelo("Milka con Oreo");
		producto.setNombre("MilkaOreo");
		return producto;
		
	}

	private TipoProducto generarTipoProducto() {
			TipoProducto tipoProducto = new TipoProducto();
			tipoProducto.setNombre("Chocolate");
			tipoProducto.setDescripcion("Alimento compuesto por cacao y leche");
			return tipoProducto;
	}

}
