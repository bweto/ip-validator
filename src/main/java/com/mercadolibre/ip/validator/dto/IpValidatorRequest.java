package com.mercadolibre.ip.validator.dto;

import java.io.Serializable;



public class IpValidatorRequest implements Serializable {

	private static final long serialVersionUID = -7382199164409513814L;
	
	private String ipNumber;

	public String getIpNumber() {
		return ipNumber;
	}

	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}

	@Override
	public String toString() {
		return "IpValidatorRequest [ipNumber=" + ipNumber + "]";
	}

}
