package com.cjson.devcamper.dto.geocode;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Result {

	private ProvidedLocation providedLocation;
	private List<Location> locations;

}
