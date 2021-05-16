package com.mercadolibre.ip.validator.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixerIoApiResponseTest {

	@Test
	void fixerIoApiResponse() {
		var fixerIoApiResponse = new FixerIoApiResponse();
		assertNull(fixerIoApiResponse.getBase());
		assertNull(fixerIoApiResponse.getRates());
		assertNull(fixerIoApiResponse.getSuccess());
		assertNotNull(fixerIoApiResponse.toString());
	}

}
