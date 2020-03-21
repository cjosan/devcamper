package com.cjson.devcamper.dto.geocode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Location {

	private String street;
	private String adminArea6;
	private String adminArea6Type;
	private String adminArea5;
	private String adminArea5Type;
	private String adminArea4;
	private String adminArea4Type;
	private String adminArea3;
	private String adminArea3Type;
	private String adminArea1;
	private String adminArea1Type;
	private String postalCode;
	private String geocodeQualityCode;
	private String geocodeQuality;
	private Boolean dragPoint;
	private String sideOfStreet;
	private String linkId;
	private String unknownInput;
	private String type;
	private LatLng latLng;
	private DisplayLatLng displayLatLng;
	private String mapUrl;

}
