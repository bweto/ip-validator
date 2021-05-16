package com.mercadolibre.ip.validator.dto;

import java.io.Serializable;
import java.util.List;

public class RestCountriesResponse implements Serializable {

	private static final long serialVersionUID = 8413098560542168233L;

	private List<CurrencyDTO> currencies;

	public List<CurrencyDTO> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<CurrencyDTO> currencies) {
		this.currencies = currencies;
	}

	@Override
	public String toString() {
		return "RestCountriesResponse [currencies=" + currencies + "]";
	}

}
