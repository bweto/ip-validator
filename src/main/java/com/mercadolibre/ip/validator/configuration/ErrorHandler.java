package com.mercadolibre.ip.validator.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.mercadolibre.ip.validator.dto.GeneralResponse;
import com.mercadolibre.ip.validator.exception.ConnectionsThirdPartiesException;
import com.mercadolibre.ip.validator.exception.IpValidatorException;
import com.mercadolibre.ip.validator.utils.ErrorMessages;

@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(value =  ConnectionsThirdPartiesException.class)
	public ResponseEntity<Object> connectionsThirdPartiesException(ConnectionsThirdPartiesException connectionsThirdPartiesException){
		return new ResponseEntity<>(new GeneralResponse(connectionsThirdPartiesException.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value =  HttpClientErrorException.class)
	public ResponseEntity<Object> httpClientErrorException(HttpClientErrorException httpClientErrorException){
		return httpClientErrorException.getStatusCode().is4xxClientError()? 
				new ResponseEntity<>(new GeneralResponse(httpClientErrorException.getMessage()), HttpStatus.BAD_REQUEST):
					new ResponseEntity<>(new GeneralResponse(ErrorMessages.INTERNAL_SERVER_ERROR.message()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value =  IpValidatorException.class)
	public ResponseEntity<Object> ipValidatorException(IpValidatorException ipValidatorException){
		return new ResponseEntity<>(new GeneralResponse(ipValidatorException.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
}
