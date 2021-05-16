package com.mercadolibre.ip.validator.dto;

import java.io.Serializable;

public class IpCountryResponse implements Serializable {

	private static final long serialVersionUID = 1132983171402140404L;

	private String countryCode3;
	private String countryName;

	public String getCountryCode3() {
		return countryCode3;
	}

	public void setCountryCode3(String countryCode3) {
		this.countryCode3 = countryCode3;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public String toString() {
		return "IpCountryResponse [countryCode3=" + countryCode3 + ", countryName=" + countryName + "]";
	}

}
