package com.mercadolibre.ip.validator.dto;

import java.io.Serializable;

public class GeneralResponse implements Serializable {

	private static final long serialVersionUID = -2226167693599732186L;
	
	private String message;

	public GeneralResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
