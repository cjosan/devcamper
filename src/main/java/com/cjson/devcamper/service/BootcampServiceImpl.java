package com.cjson.devcamper.service;

import com.cjson.devcamper.dto.geocode.GeocodeResponse;
import com.cjson.devcamper.model.Bootcamp;
import com.cjson.devcamper.model.Location;
import com.cjson.devcamper.repository.BootcampRepository;
import com.cjson.devcamper.utils.GeocoderUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BootcampServiceImpl implements BootcampService {

	private final BootcampRepository bootcampRepo;
	private GeocoderUtils geocoderUtils;

	public BootcampServiceImpl(BootcampRepository bootcampRepo, GeocoderUtils geocoderUtils) {
		this.bootcampRepo = bootcampRepo;
		this.geocoderUtils = geocoderUtils;
	}

	@Override
	public List<Bootcamp> getAllBootcamps(Predicate predicate, Pageable pageable) {
		return StreamSupport.stream(bootcampRepo.findAll(predicate, pageable).spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Optional<Bootcamp> getSingleBootcampById(Long id) {
		return bootcampRepo.findById(id);
	}

	@Override
	public Bootcamp createBootcamp(Bootcamp bootcamp) {
		bootcamp.setLocation(getLocationInfo(bootcamp.getAddress()));
		return bootcampRepo.save(bootcamp);
	}

	@Override
	public Optional<Bootcamp> updateBootcamp(Long id, Bootcamp bootcamp) throws IllegalStateException {
		Optional<Bootcamp> bootcampFromDb = bootcampRepo.findById(id);

		if (!bootcampFromDb.isPresent()) {
			return bootcampFromDb;
		}

		bootcamp.setId(id);

		return Optional.of(bootcampRepo.save(bootcamp));
	}

	@Override
	public Boolean deleteBootcamp(Long id) {
		Optional<Bootcamp> bootcampFromDb = bootcampRepo.findById(id);

		if (!bootcampFromDb.isPresent()) {
			return false;
		}

		bootcampRepo.deleteById(id);
		return true;
	}

	private Location getLocationInfo(String location) {
		GeocodeResponse geocodeResponse = geocoderUtils.getAddress(location);
		com.cjson.devcamper.dto.geocode.Location responseLocation = geocodeResponse.getResults().get(0).getLocations().get(0);
		String formattedAddress = String.join(", ",
				responseLocation.getStreet(),
				responseLocation.getAdminArea5(),
				responseLocation.getAdminArea3() + " " + responseLocation.getPostalCode(),
				responseLocation.getAdminArea1());

		return Location.builder()
				.id(null)
				.coordinates(Arrays.asList(responseLocation.getLatLng().getLng(), responseLocation.getLatLng().getLat()))
				.formattedAddress(formattedAddress)
				.street(responseLocation.getStreet())
				.city(responseLocation.getAdminArea5())
				.state(responseLocation.getAdminArea3())
				.zipcode(responseLocation.getPostalCode())
				.country(responseLocation.getAdminArea1())
				.build();
	}
}
