package com.mercadolibre.ip.validator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.ip.validator.db.entity.IPBlackListEntity;
import com.mercadolibre.ip.validator.db.repository.service.BlackListService;
import com.mercadolibre.ip.validator.dto.IpValidatorResponse;
import com.mercadolibre.ip.validator.exception.IpValidatorException;
import com.mercadolibre.ip.validator.service.ConnectionsThirdPartiesService;
import com.mercadolibre.ip.validator.service.IpValidatorService;
import com.mercadolibre.ip.validator.utils.ErrorMessages;
import com.mercadolibre.ip.validator.utils.IpValidator;

@Service
public class IpValidatorServiceImpl implements IpValidatorService {
	
	@Autowired
	private IpValidator ipValidator;
	
	@Autowired
	private ConnectionsThirdPartiesService connectionsThirdPartiesService;
	
	@Autowired
	private BlackListService blackListService;
	
	@Override
	public IpValidatorResponse getIPInformation(String ip) {
		
		if(!ipValidator.test(ip)) throw new IpValidatorException(ErrorMessages.INVALID_IP.message());
		
		if(blackListService.existsByIpNumber(ip)) throw new IpValidatorException(ErrorMessages.IS_BAN_LIST.message());

		var ipCountryResponse = connectionsThirdPartiesService.ipCountry(ip);
		var currency = connectionsThirdPartiesService.restCountries(ipCountryResponse.getCountryCode3());
		var fixerIoApiResponse = connectionsThirdPartiesService.fixerioApi();
		var price = fixerIoApiResponse.getRates().get(currency.toUpperCase());

		return new IpValidatorResponse(
					ipCountryResponse.getCountryName(),
					ipCountryResponse.getCountryCode3(),
					currency,
					fixerIoApiResponse.getBase(),
					price
				);
	}

	@Override
	public void addIPToBlackList(String ip) {
		
		if(!ipValidator.test(ip)) throw new IpValidatorException(ErrorMessages.INVALID_IP.message());
		
		if(blackListService.existsByIpNumber(ip)) throw new IpValidatorException(ErrorMessages.EXIST_IP.message());
		
		blackListService.save(new IPBlackListEntity(ip));
	}

}
