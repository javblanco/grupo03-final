package es.cic.bootcamp.grupo03final;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import es.cic.bootcamp.grupo03final.modelo.Producto;
import es.cic.bootcamp.grupo03final.modelo.TipoProducto;
import es.cic.bootcamp.grupo03final.repositorio.ProductoRepositorio;
import es.cic.bootcamp.grupo03final.repositorio.TipoProductoRepositorio;

@SpringBootApplication
public class Grupo03FinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(Grupo03FinalApplication.class, args);
	}

	@Bean
  	public CommandLineRunner demo(TipoProductoRepositorio tipoRepositorio, ProductoRepositorio productoRepositorio) {
		  return(args) -> {
			  TipoProducto tipo1 = tipoRepositorio.save(new TipoProducto("Cafetera", "Hace café"));
			  TipoProducto tipo2 = tipoRepositorio.save(new TipoProducto("Taza", "Permite tomar el café"));

			  Producto producto1 = new Producto();
			  producto1.setTipoProducto(tipo1);
			  producto1.setNombre("Noespresso");
			  producto1.setMarca("Cafeteras No S.A.");
			  producto1.setModelo("Turbo 2000");
			  producto1.setCantidadUnidadesAlmacen(60);
			  producto1.setCantidadUnidadesTienda(20);

			  Producto producto2 = new Producto();
			  producto2.setTipoProducto(tipo2);
			  producto2.setNombre("Taza con mensaje motivador");
			  producto2.setMarca("Señor feliz");
			  producto2.setModelo("Taza de las sonrisas");
			  producto2.setCantidadUnidadesAlmacen(40);
			  producto2.setCantidadUnidadesTienda(10);

			  productoRepositorio.saveAll(List.of(producto1, producto2));
		  };
	}

}
