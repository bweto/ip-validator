package com.mercadolibre.ip.validator.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IpValidatorRequestTest {

	@Test
	void test() {
		var ipValidatorRequest = new IpValidatorRequest();
		assertNotNull(ipValidatorRequest.toString());
	}

}
