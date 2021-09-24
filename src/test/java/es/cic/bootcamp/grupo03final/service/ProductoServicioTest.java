package es.cic.bootcamp.grupo03final.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.cic.bootcamp.grupo03final.conversor.ProductoConversor;
import es.cic.bootcamp.grupo03final.dto.ProductoDto;
import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.repositorio.ProductoRepositorio;
import es.cic.bootcamp.grupo03final.servicio.ProductoServicio;

@SpringBootTest
public class ProductoServicioTest {
    
    @Autowired
    private ProductoServicio productoServicio;

    @MockBean
    private ProductoRepositorio productoRepositorio;

    @MockBean
    private ProductoConversor productoConversor;


    @BeforeEach
	void setUp() throws Exception {
	}

    @Test
    void testRead() {
        Producto producto = generarProducto();
        ProductoDto dto = generarProductoDto();


        Optional<Producto> optionalProducto = Optional.of(producto);

        when(productoRepositorio.findById(producto.getId())).thenReturn(optionalProducto);
        when(productoConversor.entityToDto(producto)).thenReturn(dto);

        ProductoDto resultado = productoServicio.read(producto.getId());

        assertThat(dto)
		.usingRecursiveComparison()
		.isEqualTo(resultado);
		verify(productoRepositorio, times(1)).findById(producto.getId());
		verify(productoConversor, times(1)).entityToDto(producto);

    }


    private Producto generarProducto() {
		Producto p = new Producto();
        p.setId(1L);
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
        dto.setId(1L);
		dto.setNombre("balon");
		dto.setMarca("puma");
		dto.setModelo("xr22");
		dto.setDescripcion("Balon de futbol 11 ideal para adultos.");
		
		dto.setCantidadUnidadesAlmacen(0);
		dto.setCantidadUnidadesTienda(0);
		
		return dto;
	}

}
