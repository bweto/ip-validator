package com.mercadolibre.ip.validator.db.repository.service;

import com.mercadolibre.ip.validator.db.entity.IPBlackListEntity;

public interface BlackListService {
	
	public IPBlackListEntity save( IPBlackListEntity ipBlackListEntity);
	
	public boolean existsByIpNumber(String ipNumber);
	
}
