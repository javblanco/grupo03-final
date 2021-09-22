package es.cic.bootcamp.grupo03final.controlador;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.bootcamp.grupo03final.conversor.ProductoConversor;
import es.cic.bootcamp.grupo03final.dto.ProductoDto;
import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.ProductoRepositorio;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductocontroladorIntegracionTest {

	@Autowired
	private MockMvc mvc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired 
	private ProductoRepositorio productoRepositorio;
	
	@Autowired
	private ProductoConversor productoConversor;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	@BeforeEach
	void setUp() throws Exception {
	}
	

	private Producto generarProducto() {
		Producto p = new Producto();
		p.setNombre("balon");
		p.setMarca("puma");
		p.setModelo("xr22");
		p.setDescripción("Balon de futbol 11 ideal para adultos.");
		p.setCantidadUnidadesAlmacen(0);
		p.setCantidadUnidadesTienda(0);
		
		return p;
	}
	
	private ProductoDto generarProductoDto() {
		ProductoDto dto = new ProductoDto();
		dto.setNombre("balon");
		dto.setMarca("puma");
		dto.setModelo("xr22");
		dto.setDescripción("Balon de futbol 11 ideal para adultos.");
		
		dto.setCantidadUnidadesAlmacen(0);
		dto.setCantidadUnidadesTienda(0);
		
		return dto;
	}


	
	@Test
	void testCreateValidacionesCorrectas() throws Exception {
		Producto productoResultado = generarProducto();

		TipoProducto tipoP = new TipoProducto();
		tipoP.setNombre("Balon");
		
		entityManager.persist(tipoP);
		entityManager.flush();
		
		productoResultado.setTipoProducto(tipoP);
		
		ProductoDto dto = generarProductoDto();
		dto.setIdTipoProducto(tipoP.getId());
		
		MockHttpServletRequestBuilder request =
				post("/api/producto")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto));
		
		
		MvcResult result = mvc.perform(request)
						   .andDo(print())
						   .andExpect(status().isCreated())
						   .andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		Long idProductoResultado = Long.parseLong(content);
		
		Producto productoEnBBDD = entityManager.find(Producto.class, idProductoResultado);
		
		assertNotNull(productoEnBBDD.getTipoProducto(), "El tipo no se ha asociado con el producto");
		
		
		assertThat(productoEnBBDD)
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(productoResultado);
				
	}

	@Test
	void testListar() throws Exception {
		Producto producto = generarProducto();
		Producto producto1 = generarProducto();
		
		productoRepositorio.saveAll(List.of(producto, producto1));
		
		MockHttpServletRequestBuilder request = get("/api/producto")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		String respuesta = mapper.writeValueAsString(productoConversor.entityListToDtoList(List.of(producto, producto1)));
		
		respuesta = respuesta.substring(1, respuesta.length()-1);
		
		MvcResult result = mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
		.andReturn();
		
		String resultado = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertTrue(resultado.contains(respuesta), "Error en el listado de productos, no son los esperados");
	}
	
	@Test
	void testModificar() throws Exception {
		Producto producto = generarProducto();
		
		productoRepositorio.save(producto);
		
		Producto productoModificar = generarProducto();
		productoModificar.setId(producto.getId());
		productoModificar.setNombre("Pelota");
		
		String body = mapper.writeValueAsString(productoConversor.entityToDto(producto));
		
		MockHttpServletRequestBuilder request = put("/api/producto")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body);
		
		MvcResult result = mvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		Producto resultado = productoRepositorio.findById(producto.getId()).get();
		
		assertEquals(productoModificar, resultado, "El registro no se ha modificado correctamente.");

	}



	
	

}
