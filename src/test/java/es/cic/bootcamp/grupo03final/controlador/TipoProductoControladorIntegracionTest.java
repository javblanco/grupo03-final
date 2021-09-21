package es.cic.bootcamp.grupo03final.controlador;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.TipoProductoRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
public class TipoProductoControladorIntegracionTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TipoProductoRepositorio tipoProductoRepositorio;
	
	@Test
	void leerTest() throws Exception {
		TipoProducto tipo = tipoProductoRepositorio.save(new TipoProducto("producto1", "descp"));
				MockHttpServletRequestBuilder request = get("/api/tipo-producto/{id}", tipo.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform(request)
		.andDo(print())
		.andExpect(status().isOk());
	}
}
