package com.mercadolibre.ip.validator.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class RestCountriesResponseTest {

	@Test
	void test() {
		var restCountriesResponse = new RestCountriesResponse();
		assertNotNull(restCountriesResponse.toString());
	}

}
