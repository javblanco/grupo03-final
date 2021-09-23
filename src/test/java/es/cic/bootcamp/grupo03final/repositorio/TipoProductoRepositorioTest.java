package es.cic.bootcamp.grupo03final.repositorio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import es.cic.bootcamp.grupo03final.modelo.TipoProducto;

@DataJpaTest
class TipoProductoRepositorioTest{
	
	@Autowired
	private TipoProductoRepositorio tipoProductoRepositorio;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testCrear() {
		
	TipoProducto tipoProducto = generarTipoProducto();
	
	TipoProducto resultado = tipoProductoRepositorio.save(tipoProducto);
	entityManager.flush();

	assertNotNull(resultado.getId(), "La clave primaria no deberia ser nula");
	TipoProducto tipoProductoEnBBDD = entityManager.find(TipoProducto.class, resultado.getId());
	assertEquals(resultado, tipoProductoEnBBDD, "No se ha guardado el tipo de producto");
	}
	
	@Test
	void testLeer() {
		TipoProducto tipoProductoALeer = entityManager.persist(generarTipoProducto());
		
		Optional<TipoProducto> optional = tipoProductoRepositorio.findById(tipoProductoALeer.getId());
	
		if (optional.isPresent()) {
			TipoProducto tipoProductoLeido = optional.get();
			
			assertEquals(tipoProductoALeer,tipoProductoLeido,"No se ha conseguido leer el tipo de producto");
		}else {
			assertNull(optional,"La busqueda del tipo de producto ha sido nula"	);
			}
	}
	
	@Test
	void testListar() {
		TipoProducto tipoProductoALeer = tipoProductoRepositorio.save(generarTipoProducto());
		
		TipoProducto tipoProductoALeer2 =  tipoProductoRepositorio.save(generarTipoProducto());
		
		List<TipoProducto> listaTipoProducto = List.of(tipoProductoALeer,tipoProductoALeer2);
		
		List<TipoProducto> listaEnBBDD = new ArrayList<TipoProducto>();
		
		tipoProductoRepositorio.findAll().forEach(listaEnBBDD::add);
		
		assertThat(listaEnBBDD.size())
		.isGreaterThanOrEqualTo(listaTipoProducto.size());
		
	}
	
	@Test
	void testModificar() {
		TipoProducto tipoProductoALeer = entityManager.persist(generarTipoProducto());
		entityManager.flush();
		TipoProducto tipoProductoEnBBDD = tipoProductoRepositorio.findById(tipoProductoALeer.getId()).get();
		
		tipoProductoEnBBDD.setDescripcion("Electrodoméstico para lavar");
		entityManager.flush();
		
		TipoProducto tipoProductoTrasActualizar = entityManager.find(TipoProducto.class , tipoProductoALeer.getId());
		
		assertEquals("Electrodoméstico para lavar",tipoProductoTrasActualizar.getDescripcion(),"No se ha actualizado");
		
	}
	
	@Test
	void testDarDeBaja() {
		TipoProducto tipoProductoALeer = entityManager.persist(generarTipoProducto());
		entityManager.flush();
		TipoProducto tipoProductoEnBBDD = tipoProductoRepositorio.findById(tipoProductoALeer.getId()).get();
		
		tipoProductoEnBBDD.setActivo(false);
		entityManager.flush();
		
		TipoProducto tipoProductoTrasActualizar = entityManager.find(TipoProducto.class , tipoProductoALeer.getId());
		
		assertEquals(false,tipoProductoTrasActualizar.isActivo(),"No se ha dado de baja");
		
	}
	
	@Test
	void testDarDeAlta() {
		TipoProducto tipoProductoALeer = entityManager.persist(generarTipoProducto2());
		entityManager.flush();
		TipoProducto tipoProductoEnBBDD = tipoProductoRepositorio.findById(tipoProductoALeer.getId()).get();
		
		tipoProductoEnBBDD.setActivo(true);
		entityManager.flush();
		
		TipoProducto tipoProductoTrasActualizar = entityManager.find(TipoProducto.class , tipoProductoALeer.getId());
		
		assertEquals(true,tipoProductoTrasActualizar.isActivo(),"No se ha dado de baja");
		
	}
	@Test
	void testBorrar() {
		//Este test es si es necesario borrar un producto en el futuro, por ahora solo se desactivan
		TipoProducto tipoProductoABorrar = entityManager.persist(generarTipoProducto());
		entityManager.flush();
		
		tipoProductoRepositorio.deleteById(tipoProductoABorrar.getId());
		entityManager.flush();
		
		TipoProducto tipoProductoTrasBorrar = entityManager.find(TipoProducto.class ,tipoProductoABorrar.getId());
		assertNull(tipoProductoTrasBorrar,"No se ha eliminado el Producto");
		
	}
	
	
	private TipoProducto generarTipoProducto() {
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setNombre("Lavadora");
		tipoProducto.setDescripcion("Electrodoméstico para lavar la ropa.");
		return tipoProducto;
	}
	private TipoProducto generarTipoProducto2() {
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setActivo(false);
		tipoProducto.setNombre("Lavadora");
		tipoProducto.setDescripcion("Electrodoméstico para lavar la ropa.");
		return tipoProducto;
	}
}
