package es.cic.bootcamp.grupo03final.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductoExcepcion extends RuntimeException {

	public ProductoExcepcion(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductoExcepcion(String message) {
		super(message);
	}

}