package es.cic.bootcamp.grupo03final.controlador;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import es.cic.bootcamp.grupo03final.conversor.ProductoConversor;
import es.cic.bootcamp.grupo03final.dto.ProductoDto;
import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.ProductoRepositorio;
import es.cic.bootcamp.grupo03final.repositorio.TipoProductoRepositorio;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductoControladorIntegracionTest {

	@Autowired
	private MockMvc mvc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired 
	private ProductoRepositorio productoRepositorio;
	
	@Autowired
	private TipoProductoRepositorio tipoProductoRepositorio;
	
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
		p.setDescripcion("Balon de futbol 11 ideal para adultos.");
		p.setCantidadUnidadesAlmacen(0);
		p.setCantidadUnidadesTienda(0);
		
		return p;
	}
	
	private ProductoDto generarProductoDto() {
		ProductoDto dto = new ProductoDto();
		dto.setNombre("balon");
		dto.setMarca("puma");
		dto.setModelo("xr22");
		dto.setDescripcion("Balon de futbol 11 ideal para adultos.");
		
		dto.setCantidadUnidadesAlmacen(0);
		dto.setCantidadUnidadesTienda(0);
		
		return dto;
	}

	private TipoProducto generarTipoProducto() {
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setNombre("Lavadora");
		tipoProducto.setDescripcion("Electrodomestico para lavar la ropa.");
		return tipoProducto;
	}

	
	@Test
	void testCrear() throws Exception {
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
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		Producto resultado = productoRepositorio.findById(producto.getId()).get();
		
		assertEquals(productoModificar, resultado, "El registro no se ha modificado correctamente.");

	}
	
	@Test
	void testBorrar() throws Exception {
		Producto p = generarProducto();
		
		Producto productoABorrar = productoRepositorio.save(p);
		
		MockHttpServletRequestBuilder request = delete("/api/producto/{id}", productoABorrar.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		
		Optional<Producto> alquilerEnBBDD = productoRepositorio.findById(productoABorrar.getId());
		
		assertTrue(alquilerEnBBDD.isEmpty(), "No se ha borrado el registro");
	}

	@Test
	void testLeer() throws Exception {
		Producto p = generarProducto();

		productoRepositorio.save(p);

		MockHttpServletRequestBuilder request = get("/api/producto/{id}", p.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(request).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testDevolver() throws Exception {
		
		Producto p = generarProducto();
		p.setCantidadUnidadesAlmacen(10);
		p.setCantidadUnidadesTienda(6);
		
		productoRepositorio.save(p);
		
		Long id = p.getId();
		int cantidad = 4;
		
		MockHttpServletRequestBuilder request = post("/api/producto/devolver/{id}", id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cantidad));
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		
		int cantidadTiendaNueva = productoRepositorio.findById(p.getId()).get().getCantidadUnidadesTienda();
		int cantidadAlmacenNueva = productoRepositorio.findById(p.getId()).get().getCantidadUnidadesAlmacen();
		
		assertEquals(2,cantidadTiendaNueva, "No se ha restado la cantidad correctamente de la tienda.");
		assertEquals(14,cantidadAlmacenNueva,  "No se ha sumado la cantidad correctamente del almacen.");
	}
	
	@Test
	void testTransferir() throws Exception {
		
		Producto p = generarProducto();
		p.setCantidadUnidadesAlmacen(10);
		p.setCantidadUnidadesTienda(6);
		
		productoRepositorio.save(p);
		
		Long id = p.getId();
		int cantidad = 4;
		
		MockHttpServletRequestBuilder request = post("/api/producto/transferir/{id}", id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cantidad));
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		
		int cantidadTiendaNueva = productoRepositorio.findById(p.getId()).get().getCantidadUnidadesTienda();
		int cantidadAlmacenNueva = productoRepositorio.findById(p.getId()).get().getCantidadUnidadesAlmacen();
		
		assertEquals(6, cantidadAlmacenNueva, "No se ha restado la cantidad correctamente del almacen.");
		assertEquals(10, cantidadTiendaNueva ,  "No se ha sumado la cantidad correctamente de la tienda.");
		
	}
	
	@Test
	void testPedir() throws Exception {
		
		Producto p = generarProducto();
		p.setCantidadUnidadesAlmacen(10);
		
		productoRepositorio.save(p);
		
		Long id = p.getId();
		Integer cantidad = 20;
		
		MockHttpServletRequestBuilder request = post("/api/producto/pedir/{id}", id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cantidad));
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		

		int cantidadAlmacenNueva = productoRepositorio.findById(p.getId()).get().getCantidadUnidadesAlmacen();
		
		assertEquals( 30, cantidadAlmacenNueva, "No se ha sumado la cantidad correctamente del almacen.");
		
	}


	@Test
	void testListarActivos() throws Exception {
		Producto producto = generarProducto();
		Producto producto1 = generarProducto();
		Producto producto2 = generarProducto();
		
		TipoProducto tipo = generarTipoProducto(); //Se autogeneran como activos
		TipoProducto tipo1 = generarTipoProducto();
		TipoProducto tipo2 = generarTipoProducto();
		
		tipo2.setActivo(false);

		tipoProductoRepositorio.saveAll(List.of(tipo, tipo1, tipo2));
		
		producto.setTipoProducto(tipo);
		producto1.setTipoProducto(tipo1);
		producto2.setTipoProducto(tipo2);
		
		
		productoRepositorio.saveAll(List.of(producto, producto1, producto2));
		
		MockHttpServletRequestBuilder request = get("/api/producto/tipo-activo")
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
	void testListarProductosEnStock() throws Exception {
		Producto producto = generarProducto();
		Producto producto1 = generarProducto();
		Producto producto2 = generarProducto();
		
		TipoProducto tipo = generarTipoProducto(); 
		TipoProducto tipo1 = generarTipoProducto();
		TipoProducto tipo2 = generarTipoProducto();
		
		producto.setCantidadUnidadesAlmacen(1);
		producto.setCantidadUnidadesTienda(2);
		producto1.setCantidadUnidadesAlmacen(0);
		producto1.setCantidadUnidadesTienda(2);
		producto2.setCantidadUnidadesAlmacen(0);
		producto2.setCantidadUnidadesTienda(0);

		tipoProductoRepositorio.saveAll(List.of(tipo, tipo1, tipo2));
		
		producto.setTipoProducto(tipo);
		producto1.setTipoProducto(tipo1);
		producto2.setTipoProducto(tipo2);
		
		
		productoRepositorio.saveAll(List.of(producto, producto1, producto2));
		
		MockHttpServletRequestBuilder request = get("/api/producto/stock")
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
	void testListarProductosEnStockAlmacen() throws Exception {
		Producto producto = generarProducto();
		Producto producto1 = generarProducto();
		Producto producto2 = generarProducto();
		
		TipoProducto tipo = generarTipoProducto(); 
		TipoProducto tipo1 = generarTipoProducto();
		TipoProducto tipo2 = generarTipoProducto();
		
		producto.setCantidadUnidadesAlmacen(1);
		producto1.setCantidadUnidadesAlmacen(2);
		producto2.setCantidadUnidadesAlmacen(0);

		tipoProductoRepositorio.saveAll(List.of(tipo, tipo1, tipo2));
		
		producto.setTipoProducto(tipo);
		producto1.setTipoProducto(tipo1);
		producto2.setTipoProducto(tipo2);
		
		
		productoRepositorio.saveAll(List.of(producto, producto1, producto2));
		
		MockHttpServletRequestBuilder request = get("/api/producto/almacen")
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
	void testListarProductosEnStockTienda() throws Exception {
		Producto producto = generarProducto();
		Producto producto1 = generarProducto();
		Producto producto2 = generarProducto();
		
		TipoProducto tipo = generarTipoProducto(); 
		TipoProducto tipo1 = generarTipoProducto();
		TipoProducto tipo2 = generarTipoProducto();
		
		producto.setCantidadUnidadesTienda(2);
		producto1.setCantidadUnidadesTienda(2);
		producto2.setCantidadUnidadesTienda(0);

		tipoProductoRepositorio.saveAll(List.of(tipo, tipo1, tipo2));
		
		producto.setTipoProducto(tipo);
		producto1.setTipoProducto(tipo1);
		producto2.setTipoProducto(tipo2);
		
		
		productoRepositorio.saveAll(List.of(producto, producto1, producto2));
		
		MockHttpServletRequestBuilder request = get("/api/producto/tienda")
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
	
	

}
