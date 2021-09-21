package es.cic.bootcamp.grupo03final.repositorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.TipoProductoRepositorio;

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
	void testSave() {
		
	TipoProducto tipoProducto = generarTipoProducto();
	
	TipoProducto resultado = tipoProductoRepositorio.save(tipoProducto);
	entityManager.flush();

	assertNotNull(resultado.getId(), "La clave primaria no deberia ser nula");
	TipoProducto tipoProductoEnBBDD = entityManager.find(TipoProducto.class, resultado.getId());
	assertEquals(resultado, tipoProductoEnBBDD, "No se ha guardado el vehiculo");
	}
	
	private TipoProducto generarTipoProducto() {
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setNombre("Lavadora");
		tipoProducto.setDescripcion("Electrodoméstico para lavar la ropa.");
		return tipoProducto;
	}
}
