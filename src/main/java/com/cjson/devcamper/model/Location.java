package com.cjson.devcamper.model;

import com.cjson.devcamper.utils.ListToStringConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Location {

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Point locationPoint;

	@Convert(converter = ListToStringConverter.class)
	private List<Double> coordinates;
	private String formattedAddress;
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private String country;

}
