package com.cjson.devcamper.dto.geocode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Options {

	private Integer maxResults;
	private Boolean thumbMaps;
	private Boolean ignoreLatLngInput;

}
