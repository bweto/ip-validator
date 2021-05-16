package com.mercadolibre.ip.validator.service;

import com.mercadolibre.ip.validator.dto.FixerIoApiResponse;
import com.mercadolibre.ip.validator.dto.IpCountryResponse;


public interface ConnectionsThirdPartiesService {
	
	public IpCountryResponse ipCountry(String ip);
	
	public String restCountries(String iso);
	
	public FixerIoApiResponse fixerioApi();
	
}
