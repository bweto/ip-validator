package com.mercadolibre.ip.validator.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class FixerIoApiResponse implements Serializable {

	private static final long serialVersionUID = 3691226822291752907L;

	private Boolean success;

	private String base;

	private Map<String, BigDecimal> rates;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Map<String, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(Map<String, BigDecimal> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "FixerIoApiResponse [success=" + success + ", base=" + base + ", rates=" + rates + "]";
	}

}
