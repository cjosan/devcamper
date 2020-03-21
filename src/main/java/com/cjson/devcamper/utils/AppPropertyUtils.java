package com.cjson.devcamper.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppPropertyUtils {

	@Value("${mapquest.address.url}")
	private String mapquestUrl;

	public String getMapquestUrl() {
		return mapquestUrl;
	}

	public void setMapquestUrl(String mapquestUrl) {
		this.mapquestUrl = mapquestUrl;
	}
}
