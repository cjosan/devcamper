package com.cjson.devcamper.service;

import com.cjson.devcamper.model.Bootcamp;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BootcampService {

	List<Bootcamp> getAllBootcamps(Predicate predicate, Pageable pageable, String select);
	Optional<Bootcamp> getSingleBootcampById(Long id) throws IllegalStateException;
	Bootcamp createBootcamp(Bootcamp bootcamp);
	Optional<Bootcamp> updateBootcamp(Long id, Bootcamp bootcamp) throws IllegalStateException;
	Boolean deleteBootcamp(Long id);
	List<Bootcamp> getBootcampsInRadius(String zipcode, Double radius, String units);

}
