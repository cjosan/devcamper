package com.cjson.devcamper.utils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListToStringConverter implements AttributeConverter<List<Double>, String> {

	@Override
	public String convertToDatabaseColumn(List<Double> doubles) {
		return doubles.stream()
				.map(Object::toString)
				.collect(Collectors.joining(","));
	}

	@Override
	public List<Double> convertToEntityAttribute(String s) {
		return Arrays.stream(s.split(","))
				.map(Double::parseDouble)
				.collect(Collectors.toList());
	}
}
