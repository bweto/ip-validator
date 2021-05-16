package com.mercadolibre.ip.validator.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mercadolibre.ip.validator.db.entity.IPBlackListEntity;
import com.mercadolibre.ip.validator.db.repository.service.BlackListService;
import com.mercadolibre.ip.validator.dto.FixerIoApiResponse;
import com.mercadolibre.ip.validator.dto.IpCountryResponse;
import com.mercadolibre.ip.validator.exception.IpValidatorException;
import com.mercadolibre.ip.validator.service.ConnectionsThirdPartiesService;
import com.mercadolibre.ip.validator.service.IpValidatorService;
import com.mercadolibre.ip.validator.utils.ErrorMessages;
import com.mercadolibre.ip.validator.utils.IpValidator;

@SpringBootTest
class IpValidatorServiceImplTest {
	
	@Autowired
	private IpValidatorService ipValidatorService; 
	
	@MockBean
	private IpValidator ipValidator;
	
	@MockBean
	private ConnectionsThirdPartiesService connectionsThirdPartiesService;
	
	@MockBean
	private BlackListService blackListService;
	
	@Test
	void getIPInformationOK() {
		var ip = "192.168.52.3";
		Mockito.when(ipValidator.test(ip)).thenReturn(true);
		Mockito.when(blackListService.existsByIpNumber(ip)).thenReturn(false);
		var ipCountryResponse = new IpCountryResponse();
		ipCountryResponse.setCountryCode3("COL");
		ipCountryResponse.setCountryName("Colombia");
		Mockito.when(connectionsThirdPartiesService.ipCountry(ip)).thenReturn(ipCountryResponse);
		Mockito.when(connectionsThirdPartiesService.restCountries(ipCountryResponse.getCountryCode3())).thenReturn("COP");
		var fixerIoApiResponse = new FixerIoApiResponse();
		fixerIoApiResponse.setBase("EUR");
		fixerIoApiResponse.setSuccess(Boolean.TRUE);
		fixerIoApiResponse.setRates(Collections.singletonMap("COP", new BigDecimal(4476.383588)));
		Mockito.when(connectionsThirdPartiesService.fixerioApi()).thenReturn(fixerIoApiResponse);
		
		assertNotNull(ipValidatorService.getIPInformation(ip));
	}
	
	@Test
	void getInformationInvalidIP() {
		var ip = "1.2.3";
		Mockito.when(ipValidator.test(ip)).thenReturn(false);
		
		assertThatThrownBy(() -> ipValidatorService.getIPInformation(ip))
		.hasMessageContaining(ErrorMessages.INVALID_IP.message())
		.isInstanceOf(IpValidatorException.class);
	}
	
	@Test
	void getInformationIPInBanList() {
		var ip = "125.222.3.5";
		Mockito.when(ipValidator.test(ip)).thenReturn(true);
		Mockito.when(blackListService.existsByIpNumber(ip)).thenReturn(true);
		assertThatThrownBy(() -> ipValidatorService.getIPInformation(ip))
		.hasMessageContaining(ErrorMessages.IS_BAN_LIST.message())
		.isInstanceOf(IpValidatorException.class);
	}
	
	@Test
	void addIPToBlackListOk() {
		var ip = "192.168.52.3";
		Mockito.when(ipValidator.test(ip)).thenReturn(true);
		Mockito.when(blackListService.existsByIpNumber(ip)).thenReturn(false);
		var ipPBlackListEntity = new IPBlackListEntity(ip);
		ipPBlackListEntity.setId(1L);
		Mockito.when(blackListService.save(new IPBlackListEntity(ip))).thenReturn(ipPBlackListEntity);
		ipValidatorService.addIPToBlackList(ip);
	}
	
	@Test
	void addIPToBlackInvalidIP() {
		var ip = "1.12.3";
		Mockito.when(ipValidator.test(ip)).thenReturn(false);
		assertThatThrownBy(() -> ipValidatorService.addIPToBlackList(ip))
		.hasMessageContaining(ErrorMessages.INVALID_IP.message())
		.isInstanceOf(IpValidatorException.class);
	}
	
	@Test
	void addIPToBlackListWhenExistsInBanList() {
		var ip = "1.12.3";
		Mockito.when(ipValidator.test(ip)).thenReturn(true);
		Mockito.when(blackListService.existsByIpNumber(ip)).thenReturn(true);
		assertThatThrownBy(() -> ipValidatorService.addIPToBlackList(ip))
		.hasMessageContaining(ErrorMessages.EXIST_IP.message())
		.isInstanceOf(IpValidatorException.class);
	}

}
