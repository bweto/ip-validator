package com.mercadolibre.ip.validator.db.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class IPBlackListEntityTest {
	
	@Test
	void testBlackListEntityGettersAndSetters(){
		var ipBlackListEntity = new IPBlackListEntity();
		ipBlackListEntity.setId(1L);
		ipBlackListEntity.setIpNumber("192.168.10.2");
		var time = LocalDateTime.now();
		ipBlackListEntity.setCreateAt(time);
	
		assertEquals("192.168.10.2", ipBlackListEntity.getIpNumber());
		assertEquals(1L, ipBlackListEntity.getId());
		assertEquals(time.toString(), ipBlackListEntity.getCreateAt().toString());
	}
	
	@Test
	void testBlackListEntityConstructor() {
		var ipBlackListEntity = new IPBlackListEntity( "192.168.25.36");
		assertEquals("192.168.25.36", ipBlackListEntity.getIpNumber());
		assertNull(ipBlackListEntity.getId());
		assertNotNull(ipBlackListEntity.getCreateAt());
	}
	
}
