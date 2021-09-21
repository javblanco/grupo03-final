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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
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

import es.cic.bootcamp.grupo03final.dto.ProductoDto;
import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductocontroladorIntegracionTest {

	@Autowired
	private MockMvc mvc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Autowired
	private ObjectMapper mapper;
	
	
	@BeforeEach
	void setUp() throws Exception {
	}
	

	private Producto generarProductoConDto() {
		Producto p = new Producto();
		p.setNombre("balón");
		p.setMarca("puma");
		p.setModelo("xr22");
		p.setDescripción("Balón de futbol 11 ideal para niños y adultos.");
		p.setCantidadUnidadesAlmacen(0);
		p.setCantidadUnidadesTienda(0);
		
		return p;
	}
	
	private ProductoDto generarDto() {
		ProductoDto dto = new ProductoDto();
		dto.setNombre("balón");
		dto.setMarca("puma");
		dto.setModelo("xr22");
		dto.setDescripción("Balón de futbol 11 ideal para niños y adultos.");
		
		dto.setCantidadUnidadesAlmacen(0);
		dto.setCantidadUnidadesTienda(0);
		
		return dto;
	}


	
	@Test
	void testCreateValidacionesCorrectas() throws Exception {
		Producto productoResultado = generarProductoConDto();

		TipoProducto tipoP = new TipoProducto();
		tipoP.setNombre("Balon");
		
		entityManager.persist(tipoP);
		entityManager.flush();
		
		productoResultado.setTipoProducto(tipoP);
		
		ProductoDto dto = generarDto();
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

	
	

}
