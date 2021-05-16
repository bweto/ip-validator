package com.mercadolibre.ip.validator.configuration;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class IPValidatorPropertiesTest {

	@Test
	void iPValidatorPropertiesTest() {
		var iPValidatorProperties = new IPValidatorProperties();
		assertNull(iPValidatorProperties.getFixerioKey());
		assertNull(iPValidatorProperties.getFixerioOptions());
		assertNull(iPValidatorProperties.getFixerioUrl());
		assertNull(iPValidatorProperties.getIp2countryUrl());
		assertNull(iPValidatorProperties.getRestcountriesUrl());
	}

}
