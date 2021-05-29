package com.mercadolibre.ip.validator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanRestemplateConfiguration {
	
	
	@Bean
	public RestTemplate conectionThirdPerson() {
		return new RestTemplate();
	}
	
}
