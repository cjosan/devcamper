package com.cjson.devcamper.model;

import com.cjson.devcamper.utils.ListToStringConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	Long id;

	@Convert(converter = ListToStringConverter.class)
	private List<Double> coordinates;
	private String formattedAddress;
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private String country;

}
