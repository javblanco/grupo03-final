package es.cic.bootcamp.grupo03final.repositorio;


import org.springframework.data.repository.CrudRepository;

import es.cic.bootcamp.grupo03final.modelo.Producto;

public interface  ProductoRepositorio  extends CrudRepository<Producto, Long> {

}
