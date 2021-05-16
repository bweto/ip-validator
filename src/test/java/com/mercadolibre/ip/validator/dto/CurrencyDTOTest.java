package com.mercadolibre.ip.validator.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CurrencyDTOTest {

	@Test
	void currencyDTOTest() {
			var currencyDTO = new CurrencyDTO();
			currencyDTO.setCode("COP");
			assertNotNull(currencyDTO.getCode());
			assertNotNull(currencyDTO.toString());
				
	}

}
