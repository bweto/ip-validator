package com.mercadolibre.ip.validator.utils;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class IpValidator implements Predicate<String> {
	
	private static final String PATTERN = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	
	@Override
	public boolean test(String ip) {	
		return ip == null || !Pattern.matches(PATTERN,ip) ? false : true;
	}

}
