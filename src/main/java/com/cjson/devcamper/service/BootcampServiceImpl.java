package com.cjson.devcamper.service;

import com.cjson.devcamper.dto.geocode.GeocodeResponse;
import com.cjson.devcamper.model.Bootcamp;
import com.cjson.devcamper.model.Location;
import com.cjson.devcamper.repository.BootcampRepository;
import com.cjson.devcamper.utils.GeocoderUtils;
import com.querydsl.core.types.Predicate;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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
	public List<Bootcamp> getAllBootcamps(Predicate predicate, Pageable pageable, String select) {
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

	@Override
	public List<Bootcamp> getBootcampsInRadius(String zipcode, Double radius, String units) {
		Location location = getLocationInfo(zipcode);

		double meters = 1609.34;
		if (units != null) {
			meters = units.equals("km") ? 1000 : 1609.34;
		}

		return bootcampRepo.getBootcampsInRadius(location.getCoordinates().get(1), location.getCoordinates().get(0), radius, meters);
	}

	private Location getLocationInfo(String location) {
		GeocodeResponse geocodeResponse = geocoderUtils.getAddress(location);
		com.cjson.devcamper.dto.geocode.Location responseLocation = geocodeResponse.getResults().get(0).getLocations().get(0);
		String formattedAddress = String.join(", ",
				responseLocation.getStreet(),
				responseLocation.getAdminArea5(),
				responseLocation.getAdminArea3() + " " + responseLocation.getPostalCode(),
				responseLocation.getAdminArea1());

		GeometryFactory geometryFactory = new GeometryFactory();
		Point point = geometryFactory.createPoint(new Coordinate(responseLocation.getLatLng().getLng(), responseLocation.getLatLng().getLat()));

		return Location.builder()
				.locationPoint(point)
				.coordinates(Arrays.asList(responseLocation.getLatLng().getLat(), responseLocation.getLatLng().getLng()))
				.formattedAddress(formattedAddress)
				.street(responseLocation.getStreet())
				.city(responseLocation.getAdminArea5())
				.state(responseLocation.getAdminArea3())
				.zipcode(responseLocation.getPostalCode())
				.country(responseLocation.getAdminArea1())
				.build();
	}
}
