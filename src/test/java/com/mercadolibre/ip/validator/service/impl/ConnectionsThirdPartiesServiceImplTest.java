package com.mercadolibre.ip.validator.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;

import com.mercadolibre.ip.validator.configuration.IPValidatorProperties;
import com.mercadolibre.ip.validator.dto.CurrencyDTO;
import com.mercadolibre.ip.validator.dto.FixerIoApiResponse;
import com.mercadolibre.ip.validator.dto.RestCountriesResponse;
import com.mercadolibre.ip.validator.exception.ConnectionsThirdPartiesException;
import com.mercadolibre.ip.validator.service.ConnectionsThirdPartiesService;
import com.mercadolibre.ip.validator.utils.ErrorMessages;

@SpringBootTest
class ConnectionsThirdPartiesServiceImplTest {
	
	@Autowired
	private ConnectionsThirdPartiesService connectionsThirdPartiesService;
	
	@MockBean
	private IPValidatorProperties ipValidatorProperties;
	
	
	@Test
	void itShuldGetInformationOfIp2Country() {
		Mockito.when(ipValidatorProperties.getIp2countryUrl()).thenReturn("https://api.ip2country.info/ip?");
		var ipCountryResponse = connectionsThirdPartiesService.ipCountry("186.84.22.170");
		assertNotNull(ipCountryResponse);
		assertNotNull(ipCountryResponse.toString());
		assertNotNull(ipCountryResponse.getCountryName());
	}
	
	@Test
	void itShuldThrowIPWithoutCountryOfIp2CountryException() {
		Mockito.when(ipValidatorProperties.getIp2countryUrl()).thenReturn("https://api.ip2country.info/ip?");
		
		assertThatThrownBy(() ->connectionsThirdPartiesService.ipCountry("255.255.255.255"))
		.hasMessageContaining(ErrorMessages.IP_WITHOUT_COUNTRY.message())
		.isInstanceOf(ConnectionsThirdPartiesException.class);
		
	}
	
	@Test
	void itShuldTBadRequestOfIp2Country() {
		Mockito.when(ipValidatorProperties.getIp2countryUrl()).thenReturn("https://api.ip2country.info/ip?");
		assertThatThrownBy(() ->connectionsThirdPartiesService.ipCountry("255.255.255.2350.36"))
		.isInstanceOf(HttpClientErrorException.class);
	}
	
	@Test
	void itShuldGetInformationOfRestCountries() {
		Mockito.when(ipValidatorProperties.getRestcountriesUrl()).thenReturn("https://restcountries.eu/rest/v2/alpha/");
		String currency = connectionsThirdPartiesService.restCountries("col");
		assertNotNull(currency);
	}
	
	@Test 
	void itShuldFailByBadRequestOfRestCountries(){
		Mockito.when(ipValidatorProperties.getRestcountriesUrl()).thenReturn("https://restcountries.eu/rest/v2/alpha/");
		assertThatThrownBy(() -> connectionsThirdPartiesService.restCountries("xd"))
		.isInstanceOf(HttpClientErrorException.class);
	}
	
	@Test
	void itShuldFailValidationsOfRestCountries() {
		var connectionsTest = new ConnectionsThirdPartiesServiceImpl();
		assertThatThrownBy(() -> connectionsTest.validRestCountriesResponse(null))
		.isInstanceOf(ConnectionsThirdPartiesException.class);
		
		var tesNullList = new RestCountriesResponse();
		assertThatThrownBy(() -> connectionsTest.validRestCountriesResponse(tesNullList))
		.isInstanceOf(ConnectionsThirdPartiesException.class);
		
		var tesNullCurrency = new RestCountriesResponse();
		tesNullCurrency.setCurrencies(Collections.singletonList(new CurrencyDTO()));
		assertThatThrownBy(() -> connectionsTest.validRestCountriesResponse(tesNullCurrency))
		.isInstanceOf(ConnectionsThirdPartiesException.class);

	}
	
	@Test
	void itShuldGetInformationOfFixerIo() {
		Mockito.when(ipValidatorProperties.getFixerioUrl()).thenReturn("http://data.fixer.io/api/latest?access_key=");
		Mockito.when(ipValidatorProperties.getFixerioKey()).thenReturn("0cb10c7349dd577d1a592c24daa4ffea");
		Mockito.when(ipValidatorProperties.getFixerioOptions()).thenReturn("&format=1");
		var fixerIoApiResponse = connectionsThirdPartiesService.fixerioApi();
		assertNotNull(fixerIoApiResponse);
	}
	
	@Test
	void itShuldFailValidationsOfFixerIo() {
		var connectionsTest = new ConnectionsThirdPartiesServiceImpl();
		assertThatThrownBy(() -> connectionsTest.validFixerIoApi(null))
		.hasMessageContaining(ErrorMessages.BAD_RESPONSE_FIXER_IO.message())
		.isInstanceOf(ConnectionsThirdPartiesException.class);
	
		var fixerIoApiResponse = new FixerIoApiResponse();
		assertThatThrownBy(() -> connectionsTest.validFixerIoApi(fixerIoApiResponse))
		.hasMessageContaining(ErrorMessages.BAD_RESPONSE_FIXER_IO.message())
		.isInstanceOf(ConnectionsThirdPartiesException.class);
		
		fixerIoApiResponse.setSuccess(Boolean.FALSE);
		assertThatThrownBy(() -> connectionsTest.validFixerIoApi(fixerIoApiResponse))
		.hasMessageContaining(ErrorMessages.BAD_RESPONSE_FIXER_IO.message())
		.isInstanceOf(ConnectionsThirdPartiesException.class);
	}
	
}
