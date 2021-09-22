package es.cic.bootcamp.grupo03final.excepcion;

import org.springframework.http.HttpStatus;
	import org.springframework.web.bind.annotation.ResponseStatus;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public class TipoProductoExcepcion extends RuntimeException {

		public TipoProductoExcepcion(String message, Throwable cause) {
			super(message, cause);
		}

		public TipoProductoExcepcion(String message) {
			super(message);
		}

		
		
	}

