package com.mercadolibre.ip.validator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.ip.validator.dto.GeneralResponse;
import com.mercadolibre.ip.validator.dto.IpValidatorRequest;
import com.mercadolibre.ip.validator.dto.IpValidatorResponse;
import com.mercadolibre.ip.validator.exception.ConnectionsThirdPartiesException;
import com.mercadolibre.ip.validator.exception.IpValidatorException;
import com.mercadolibre.ip.validator.service.IpValidatorService;
import com.mercadolibre.ip.validator.utils.ErrorMessages;

@WebMvcTest(IpValidatorController.class)
class IpValidatorControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IpValidatorService ipValidatorService;

	private ObjectMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new ObjectMapper();
	}

	@Test
	void testGetIpInformation200() throws Exception {
		var ip = "192.168.2.3";
		var ipValidatorResponse = new IpValidatorResponse("Colombia", "COL", "COP", "EUR", new BigDecimal(4476.383588));
		Mockito.when(ipValidatorService.getIPInformation(ip)).thenReturn(ipValidatorResponse);
		var response = mvc
				.perform(get("/ip/information/{ip}", "192.168.2.3").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertThat(response).isEqualToIgnoringWhitespace(mapper.writeValueAsString(ipValidatorResponse));
	}

	@Test
	void testGetIpInformation400ConnectionsThirdPartiesException() throws Exception {
		var ip = "1.2.3";
		var generalResponse = new GeneralResponse(ErrorMessages.IP_WITHOUT_COUNTRY.message());
		Mockito.when(ipValidatorService.getIPInformation(ip))
				.thenThrow(new ConnectionsThirdPartiesException(ErrorMessages.IP_WITHOUT_COUNTRY.message()));

		var response = mvc.perform(get("/ip/information/{ip}", ip).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		assertThat(response).isEqualToIgnoringWhitespace(mapper.writeValueAsString(generalResponse));
	}

	@Test
	void testGetIpInformation400IpValidatorException() throws Exception {
		var ip = "1.2.3";
		var generalResponse = new GeneralResponse(ErrorMessages.IS_BAN_LIST.message());
		Mockito.when(ipValidatorService.getIPInformation(ip))
				.thenThrow(new IpValidatorException(ErrorMessages.IS_BAN_LIST.message()));

		var response = mvc.perform(get("/ip/information/{ip}", ip).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

		assertThat(response).isEqualToIgnoringWhitespace(mapper.writeValueAsString(generalResponse));
	}

	@Test
	void testGetIpInformation500HttpClientErrorException() throws Exception {
		var ip = "1.2.3";
		var generalResponse = new GeneralResponse(ErrorMessages.INTERNAL_SERVER_ERROR.message());
		Mockito.when(ipValidatorService.getIPInformation(ip)).thenThrow(new HttpClientErrorException(
				HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR.message()));

		var response = mvc.perform(get("/ip/information/{ip}", ip).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isInternalServerError()).andReturn().getResponse().getContentAsString();

		assertThat(response).isEqualToIgnoringWhitespace(mapper.writeValueAsString(generalResponse));
	}

	@Test
	void testAddIPToBanList201() throws JsonProcessingException, Exception {
		IpValidatorRequest ipValidatorRequest = new IpValidatorRequest();
		var message = "The IP was successfully added to the ban list.";
		String ip = "192.168.2.3";
		;
		ipValidatorRequest.setIpNumber(ip);
		var generalResponse = new GeneralResponse(message);
		ipValidatorService.addIPToBlackList(ip);
		var response = mvc
				.perform(post("/ip/banned")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapper.writeValueAsString(ipValidatorRequest))
						)
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		assertThat(response).isEqualToIgnoringWhitespace(mapper.writeValueAsString(generalResponse));
	}

}
