package com.mercadolibre.ip.validator.db.repository.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercadolibre.ip.validator.db.entity.IPBlackListEntity;
import com.mercadolibre.ip.validator.db.repository.BlackListRepository;

@Service
@Transactional
public class BlackListServiceImpl implements BlackListService{
	
	@Autowired
	private  BlackListRepository blackListRepository;
		
	public IPBlackListEntity save( IPBlackListEntity ipBlackListEntity) {
		return blackListRepository.save(ipBlackListEntity);
	}
	
	@Transactional(readOnly = true)
	public boolean existsByIpNumber(String ipNumber) {
		return blackListRepository.existsByIpNumber(ipNumber);
	}

}
