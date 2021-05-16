package com.mercadolibre.ip.validator.configuration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SwaggerConfigurationTest {

	@Test
	void test() {
		var swaggerConfigurationTest = new SwaggerConfiguration();
		assertNotNull(swaggerConfigurationTest.api());
	}

}
