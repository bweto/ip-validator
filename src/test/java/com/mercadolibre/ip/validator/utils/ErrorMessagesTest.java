package com.mercadolibre.ip.validator.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ErrorMessagesTest {

	@Test
	void test() {
		assertNotNull(ErrorMessages.IP_WITHOUT_COUNTRY.message());
	}

}
