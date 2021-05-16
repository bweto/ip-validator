package com.mercadolibre.ip.validator.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.mercadolibre.ip.validator.exception.ConnectionsThirdPartiesException;

class ErrorHandlerTest {

	@Test
	void ErrorHandlerException() {
		var errorHandler = new ErrorHandler();
		assertNotNull(errorHandler.connectionsThirdPartiesException(new ConnectionsThirdPartiesException("test")));
		assertNotNull(errorHandler.httpClientErrorException(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "")));
	}

}
