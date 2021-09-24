package es.cic.bootcamp.grupo03final.controlador;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import es.cic.bootcamp.grupo03final.conversor.TipoProductoConversor;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.TipoProductoRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
class TipoProductoControladorIntegracionTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TipoProductoRepositorio tipoProductoRepositorio;
	
	@Autowired
	private TipoProductoConversor tipoProductoConversor;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void testLeer() throws Exception {
		TipoProducto tipo = tipoProductoRepositorio.save(new TipoProducto("producto1", "descripcion1"));
				MockHttpServletRequestBuilder request = get("/api/tipo-producto/{id}", tipo.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	
	@Test
	void testCrear() throws Exception {
		TipoProducto tipoProducto = generarTipoProducto();
		String body = mapper.writeValueAsString(tipoProductoConversor.entityToDto(tipoProducto));
		
		MockHttpServletRequestBuilder request = post("/api/tipo-producto")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body);
		
		MvcResult result = mvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andReturn();
		
		String resultado = result.getResponse().getContentAsString();
		
		JSONObject obj = new JSONObject(resultado);

		String respuesta = obj.getString("id");
		
		
		Long idResultado = Long.parseLong(respuesta);
		
		TipoProducto tipoProductoResultado = tipoProductoRepositorio.findById(idResultado).get();
		
		assertThat(tipoProducto)
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(tipoProductoResultado);
	}
	
	@Test
	void testListar() throws Exception {
		TipoProducto tipoProducto1 = generarTipoProducto();
		TipoProducto tipoProducto2 = generarTipoProducto2();

		tipoProductoRepositorio.saveAll(List.of(tipoProducto1, tipoProducto2));
		
		MockHttpServletRequestBuilder request = get("/api/tipo-producto")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		String respuesta = mapper.writeValueAsString(tipoProductoConversor.entityListToDtoList(List.of(tipoProducto1, tipoProducto2)));
		
		respuesta = respuesta.substring(1, respuesta.length()-1);
		
		MvcResult result = mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
		.andReturn();
		
		String resultado = result.getResponse().getContentAsString();
		

		assertTrue(resultado.contains(respuesta), "El registro leido no es el esperado.");
	}

	@Test
	void testModificar() throws Exception {
		TipoProducto tipoProducto = generarTipoProducto();
		
		tipoProductoRepositorio.save(tipoProducto);
		
		TipoProducto tipoProductoAModificar = generarTipoProducto();
		tipoProductoAModificar.setId(tipoProducto.getId());
		tipoProductoAModificar.setNombre("Secadora");

		String body = mapper.writeValueAsString(tipoProductoConversor.entityToDto(tipoProducto));
		
		MockHttpServletRequestBuilder request = put("/api/tipo-producto")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		TipoProducto resultado = tipoProductoRepositorio.findById(tipoProducto.getId()).get();
		
		assertEquals(tipoProductoAModificar, resultado, "El registro no se ha modificado correctamente.");

	}
	
	@Test
	void testDarDeBaja() throws Exception {
		TipoProducto tipoProducto = generarTipoProducto();
		
		tipoProductoRepositorio.save(tipoProducto);
		
		TipoProducto tipoProductoADarDeBaja = generarTipoProducto();
		tipoProductoADarDeBaja.setId(tipoProducto.getId());
		tipoProductoADarDeBaja.setActivo(false);;

		String body = mapper.writeValueAsString(tipoProductoConversor.entityToDto(tipoProducto));
		

		MockHttpServletRequestBuilder request = post("/api/tipo-producto/desactivar/{id}",tipoProducto.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		TipoProducto resultado = tipoProductoRepositorio.findById(tipoProducto.getId()).get();
		
		assertEquals(tipoProductoADarDeBaja, resultado, "El registro no se ha dado de baja correctamente");

	}
	
	@Test
	void testDarDeAlta() throws Exception {
		TipoProducto tipoProducto = generarTipoProducto2();
		
		tipoProductoRepositorio.save(tipoProducto);
		tipoProducto.setActivo(false);
		tipoProductoRepositorio.save(tipoProducto);
		
		TipoProducto tipoProductoADarDeAlta = generarTipoProducto2();
		tipoProductoADarDeAlta.setId(tipoProducto.getId());
		tipoProductoADarDeAlta.setActivo(true);
		

		MockHttpServletRequestBuilder request = post("/api/tipo-producto/activar/{id}",tipoProducto.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
		
		TipoProducto resultado = tipoProductoRepositorio.findById(tipoProducto.getId()).get();
		
		assertEquals(tipoProductoADarDeAlta, resultado, "El registro no se ha modificado correctamente.");

	}
	
	@Test
	void testListarActivos() throws Exception {
		TipoProducto producto = generarTipoProducto();
		TipoProducto producto1 = generarTipoProducto();
		TipoProducto producto2 = generarTipoProducto();
		
		producto2.setActivo(false);
	
		tipoProductoRepositorio.saveAll(List.of(producto, producto1, producto2));
		
		MockHttpServletRequestBuilder request = get("/api/tipo-producto/activo")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		String respuesta = mapper.writeValueAsString(tipoProductoConversor.entityListToDtoList(List.of(producto, producto1)));
		
		respuesta = respuesta.substring(1, respuesta.length()-1);
		
		MvcResult result = mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
		.andReturn();
		
		String resultado = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertTrue(resultado.contains(respuesta), "Error en el listado de productos, no son los esperados");
	}


	private TipoProducto generarTipoProducto() {
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setNombre("Lavadora");
		tipoProducto.setDescripcion("Electrodomestico para lavar la ropa.");
		return tipoProducto;
	}
	private TipoProducto generarTipoProducto2() {
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setActivo(false);
		tipoProducto.setNombre("Lavadora");
		tipoProducto.setDescripcion("Electrodomestico para lavar la ropa.");
		return tipoProducto;
	}
}
