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

import es.cic.bootcamp.grupo03final.conversor.TipoProductoConversor;
import es.cic.bootcamp.grupo03final.dto.TipoProductoDto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.TipoProductoRepositorio;
import es.cic.bootcamp.grupo03final.servicio.TipoProductoServicio;

@SpringBootTest
public class TipoProductoServicioTest {

    @Autowired
    private TipoProductoServicio tipoProductoServicio;

    @MockBean
    private TipoProductoConversor tipoProductoConversor;

    @MockBean
    private TipoProductoRepositorio tipoProductoRepositorio;
 
    @BeforeEach
	void setUp() throws Exception {
	}

    @Test
    void testRead() {
        TipoProducto tipo = generarTipoProducto();
        TipoProductoDto dto = generarTipoProductoDto();

        Optional<TipoProducto> optional = Optional.of(tipo);

        when(tipoProductoRepositorio.findById(tipo.getId())).thenReturn(optional);
        when(tipoProductoConversor.entityToDto(tipo)).thenReturn(dto);

        TipoProductoDto resultado = tipoProductoServicio.leer(tipo.getId());

        assertThat(dto)
		.usingRecursiveComparison()
		.isEqualTo(resultado);
		verify(tipoProductoRepositorio, times(1)).findById(tipo.getId());
		verify(tipoProductoConversor, times(1)).entityToDto(tipo);
    }

    private TipoProducto generarTipoProducto() {
		TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setId(1L);
		tipoProducto.setNombre("Lavadora");
		tipoProducto.setDescripcion("Electrodomestico para lavar la ropa.");
		return tipoProducto;
	}

    private TipoProductoDto generarTipoProductoDto() {
		TipoProductoDto dto = new TipoProductoDto();
        dto.setId(1L);
		dto.setNombre("Lavadora");
		dto.setDescripcion("Electrodomestico para lavar la ropa.");
		return dto;
	}
}
