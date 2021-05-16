package com.mercadolibre.ip.validator.db.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.mercadolibre.ip.validator.db.entity.IPBlackListEntity;

@DataJpaTest
class BlackListRepositoryTest {
	
	@Autowired
	private BlackListRepository blackListRepository;
	
	@Test
	void itShouldInsertAndExistByIpNumber() {
		var ip = "192.168.104.255";
		var  ipBlackListEntity = new IPBlackListEntity(ip);
		
		blackListRepository.save(ipBlackListEntity);
		
		assertTrue(blackListRepository.existsByIpNumber(ip));
	}
	
	@Test
	void itShouldNotFoundIpNumber() {
		var ip = "192.168.104.12";
		assertFalse(blackListRepository.existsByIpNumber(ip));
	}
	
	@Test
	void itShouldNotInsertByIpNumberInvalidSize() {
		var ip = "192.168.104.255.365.123";
		var ipBlackListEntity = new IPBlackListEntity(ip);
	
		assertThatThrownBy(() -> blackListRepository.save(ipBlackListEntity))
			.hasMessageContaining("could not execute statement;")
			.isInstanceOf(DataIntegrityViolationException.class);
	}
	
	@Test
	void itShouldNotInsertIfIpNumberIsNull() {
		var  ipBlackListEntity = new IPBlackListEntity(null);
		assertThatThrownBy(() -> blackListRepository.save(ipBlackListEntity))
			.hasMessageContaining("not-null property references a null or transient value")
			.isInstanceOf(DataIntegrityViolationException.class);
	}
}
