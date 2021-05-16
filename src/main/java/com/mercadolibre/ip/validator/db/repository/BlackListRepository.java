package com.mercadolibre.ip.validator.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.mercadolibre.ip.validator.db.entity.IPBlackListEntity;

public interface BlackListRepository extends CrudRepository<IPBlackListEntity, Long> {
	
	public boolean existsByIpNumber(String ipNumber);
	
	public IPBlackListEntity findByIpNumber(String ipNumber);
	
}
