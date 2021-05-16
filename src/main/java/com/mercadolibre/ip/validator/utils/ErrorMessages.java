package com.mercadolibre.ip.validator.utils;

public enum ErrorMessages {

	IP_WITHOUT_COUNTRY("IP without country"),
	NOT_FOUND_CURRENCY("Not found currency"),
	BAD_RESPONSE_FIXER_IO("Bad response fixer io API"),
	INVALID_IP("Invalid IP"),
	IS_BAN_LIST("The ip is on the ban list"),
	EXIST_IP("This IP is already on the ban list"),
	INTERNAL_SERVER_ERROR("Something unexpected happened");

	private String message;

	private ErrorMessages(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

}
