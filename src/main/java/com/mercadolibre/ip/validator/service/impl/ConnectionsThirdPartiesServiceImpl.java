package com.mercadolibre.ip.validator.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mercadolibre.ip.validator.configuration.IPValidatorProperties;
import com.mercadolibre.ip.validator.dto.CurrencyDTO;
import com.mercadolibre.ip.validator.dto.FixerIoApiResponse;
import com.mercadolibre.ip.validator.dto.IpCountryResponse;
import com.mercadolibre.ip.validator.dto.RestCountriesResponse;
import com.mercadolibre.ip.validator.exception.ConnectionsThirdPartiesException;
import com.mercadolibre.ip.validator.service.ConnectionsThirdPartiesService;
import com.mercadolibre.ip.validator.utils.ErrorMessages;

@Service
public class ConnectionsThirdPartiesServiceImpl implements ConnectionsThirdPartiesService {

	@Autowired
	private IPValidatorProperties ipValidatorProperties;

	@Override
	public IpCountryResponse ipCountry(String ip) {

		IpCountryResponse response = new RestTemplate()
				.getForObject(ipValidatorProperties.getIp2countryUrl().concat(ip), IpCountryResponse.class);

		if (response == null || response.getCountryCode3() == null || response.getCountryCode3().isBlank()) {
			throw new ConnectionsThirdPartiesException(ErrorMessages.IP_WITHOUT_COUNTRY.message());
		}
		
		return response;
	}

	@Override
	public String restCountries(String iso) {

		RestCountriesResponse response = new RestTemplate()
				.getForObject(ipValidatorProperties.getRestcountriesUrl().concat(iso), RestCountriesResponse.class);

		return validRestCountriesResponse(response);

	}

	public String validRestCountriesResponse(RestCountriesResponse response) {
		if (response == null) {
			throw new ConnectionsThirdPartiesException(ErrorMessages.NOT_FOUND_CURRENCY.message());
		}

		if (response.getCurrencies() == null || response.getCurrencies().isEmpty()) {
			throw new ConnectionsThirdPartiesException(ErrorMessages.NOT_FOUND_CURRENCY.message());
		}

		Optional<CurrencyDTO> currency = response.getCurrencies().stream().findFirst();

		if (!currency.isPresent() || currency.get().getCode() == null || currency.get().getCode().isBlank()) {
			throw new ConnectionsThirdPartiesException(ErrorMessages.NOT_FOUND_CURRENCY.message());
		}

		return currency.get().getCode();
	}

	@Override
	public FixerIoApiResponse fixerioApi() {
		FixerIoApiResponse response = new RestTemplate().getForObject(
				ipValidatorProperties.getFixerioUrl().concat(
						ipValidatorProperties.getFixerioKey().concat(ipValidatorProperties.getFixerioOptions())),
				FixerIoApiResponse.class);

		validFixerIoApi(response);

		return response;
	}

	public void validFixerIoApi(FixerIoApiResponse response) {
		if (response == null || response.getSuccess() == null || !response.getSuccess().booleanValue()) {
			throw new ConnectionsThirdPartiesException(ErrorMessages.BAD_RESPONSE_FIXER_IO.message());
		}
	}

}
