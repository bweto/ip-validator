package com.mercadolibre.ip.validator.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class IpValidatorResponse implements Serializable {

	private static final long serialVersionUID = 3309987988956587741L;

	private String countryName;

	private String countryISO;

	private String currency;

	private String base;

	private BigDecimal price;

	public IpValidatorResponse(String countryName, String countryISO, String currency, String base, BigDecimal price) {
		this.countryName = countryName;
		this.countryISO = countryISO;
		this.currency = currency;
		this.base = base;
		this.price = price;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryISO() {
		return countryISO;
	}

	public void setCountryISO(String countryISO) {
		this.countryISO = countryISO;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "IpValidatorResponse [countryName=" + countryName + ", countryISO=" + countryISO + ", currency="
				+ currency + ", base=" + base + ", price=" + price + "]";
	}

}
