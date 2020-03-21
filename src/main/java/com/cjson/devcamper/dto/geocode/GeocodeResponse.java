package com.cjson.devcamper.dto.geocode;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GeocodeResponse {

	private Info info;
	private Options options;
	private List<Result> results;

}