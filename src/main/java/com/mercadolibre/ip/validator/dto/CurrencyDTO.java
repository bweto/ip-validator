package com.mercadolibre.ip.validator.dto;

import java.io.Serializable;

public class CurrencyDTO implements Serializable {
	private static final long serialVersionUID = -148081751840322944L;

	private String code;

	public String getCode() {
		return code;
	}
	
	
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Currency [code=" + code + "]";
	}

}
