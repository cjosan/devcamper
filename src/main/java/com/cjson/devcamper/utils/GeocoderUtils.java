package com.cjson.devcamper.utils;

import com.cjson.devcamper.dto.geocode.GeocodeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GeocoderUtils {

	private final AppPropertyUtils propertyUtils;

	public GeocoderUtils(AppPropertyUtils propertyUtils) {
		this.propertyUtils = propertyUtils;
	}

	public GeocodeResponse getAddress(String location) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(propertyUtils.getMapquestUrl(), GeocodeResponse.class, location);
	}

}
