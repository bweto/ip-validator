package com.mercadolibre.ip.validator.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class IpValidatorResponseTest {

	@Test
	void test() {
		var ipValidatorResponse = new IpValidatorResponse(
				"Colombia", "COL", "COP", "EUR", new BigDecimal(4476.383588)
				);
		ipValidatorResponse.setCountryISO("COL");
		assertNotNull(ipValidatorResponse.getCountryISO());
		ipValidatorResponse.setCountryName("Colombia");
		assertNotNull(ipValidatorResponse.getCountryName());
		ipValidatorResponse.setCurrency("COP");
		assertNotNull(ipValidatorResponse.getCurrency());
		ipValidatorResponse.setPrice(new BigDecimal(10));
		assertNotNull(ipValidatorResponse.getPrice());
		ipValidatorResponse.setBase("EUR");
		assertNotNull(ipValidatorResponse.getBase());
		assertNotNull(ipValidatorResponse.toString());
	}

}
