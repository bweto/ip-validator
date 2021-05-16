package com.mercadolibre.ip.validator.db.repository.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mercadolibre.ip.validator.db.entity.IPBlackListEntity;
import com.mercadolibre.ip.validator.db.repository.BlackListRepository;

@SpringBootTest
class BlackListServiceImplTest {
		
	@Autowired
	private BlackListService blackListService;
	
    @MockBean
    private BlackListRepository blackListRepository;
	
    private final String ip = "192.168.104.255";
    
	@Test
	void itShouldInsertIpToBlackList() {
		var id = 1L;
		var iPBlackListMock = new IPBlackListEntity(ip);
		iPBlackListMock.setId(id);
		var  ipBlackListEntity = new IPBlackListEntity(ip);
		
		Mockito.when(blackListRepository.save(ipBlackListEntity)).thenReturn(iPBlackListMock);
		var testIpBlackListEntity = blackListService.save(ipBlackListEntity);
		
		assertEquals(id, testIpBlackListEntity.getId());
		assertEquals(ip, testIpBlackListEntity.getIpNumber());
		assertNotNull(testIpBlackListEntity.getCreateAt());

	}
	
	@Test
	void isShouldTruByExistIp() {
		Mockito.when(blackListRepository.existsByIpNumber(ip)).thenReturn(true);
		assertTrue(blackListService.existsByIpNumber(ip));
	}
	
	@Test
	void isShouldFalseByNotExistIp() {
		var ipFalse = "125.32.65.32";
		Mockito.when(blackListRepository.existsByIpNumber(ipFalse)).thenReturn(false);
		assertFalse(blackListService.existsByIpNumber(ipFalse));
	}

}
