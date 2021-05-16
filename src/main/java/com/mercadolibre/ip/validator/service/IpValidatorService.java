package com.mercadolibre.ip.validator.service;

import com.mercadolibre.ip.validator.dto.IpValidatorResponse;

public interface IpValidatorService {
	
	public IpValidatorResponse getIPInformation(String ip);
	
	public void addIPToBlackList(String ip);
	
}
