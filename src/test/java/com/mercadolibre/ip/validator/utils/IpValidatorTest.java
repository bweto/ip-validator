package com.mercadolibre.ip.validator.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IpValidatorTest {

	private IpValidator ipValidatorTest;
	
	@BeforeEach
	void setUp() {
		ipValidatorTest = new IpValidator();
	}
	
	@ParameterizedTest
	@CsvSource({
		"192.168.125.255, true",
		"192.290.0.125, false",
		"290.1.3.56, false",
		"295.36.15, false",
		"1.1.1.1, true",
		"1.1.1, false",
		"null, false",
		"120.120.125.132., false"
	})
	void ipValidatorTest(String ip, boolean expected) {
		var isValid = ipValidatorTest.test(ip);
		assertThat(isValid).isEqualTo(expected);
	}

}
