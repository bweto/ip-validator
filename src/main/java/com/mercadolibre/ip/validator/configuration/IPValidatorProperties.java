package com.mercadolibre.ip.validator.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IPValidatorProperties {

	@Value("${connections-with-third-parties.ip2country.url}")
	private String ip2countryUrl;

	@Value("${connections-with-third-parties.restcountries.url}")
	private String restcountriesUrl;

	@Value("${connections-with-third-parties.fixerio.url}")
	private String fixerioUrl;

	@Value("${connections-with-third-parties.fixerio.key}")
	private String fixerioKey;

	@Value("${connections-with-third-parties.fixerio.options}")
	private String fixerioOptions;

	public String getIp2countryUrl() {
		return ip2countryUrl;
	}

	public String getRestcountriesUrl() {
		return restcountriesUrl;
	}

	public String getFixerioUrl() {
		return fixerioUrl;
	}

	public String getFixerioKey() {
		return fixerioKey;
	}

	public String getFixerioOptions() {
		return fixerioOptions;
	}

}
