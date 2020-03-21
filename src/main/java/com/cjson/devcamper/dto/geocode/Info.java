package com.cjson.devcamper.dto.geocode;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Info {
	private Integer statuscode;
	private Copyright copyright;
	private List<Object> messages;
}
