package com.mercadolibre.ip.validator.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class GeneralResponseTest {

	@Test
	void test() {
		var messagge = new GeneralResponse("test");
		assertNotNull(messagge);
		messagge.setMessage("dif");
		assertNotNull(messagge.getMessage());
	}

}
