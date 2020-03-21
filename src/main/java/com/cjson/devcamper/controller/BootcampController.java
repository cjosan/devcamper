package com.cjson.devcamper.controller;

import com.cjson.devcamper.model.Bootcamp;
import com.cjson.devcamper.repository.BootcampRepository;
import com.cjson.devcamper.service.BootcampService;
import com.cjson.devcamper.utils.GeocoderUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bootcamps")
public class BootcampController {

	private final BootcampService bootcampService;

	public BootcampController(BootcampService bootcampService, GeocoderUtils geocoderUtils) {
		this.bootcampService = bootcampService;
	}

	@GetMapping
	public ResponseEntity<List<Bootcamp>> getAllBootcamps(@QuerydslPredicate(root = Bootcamp.class, bindings = BootcampRepository.class) Predicate predicate,
	                                                      Pageable pageable) {
		return new ResponseEntity<>(bootcampService.getAllBootcamps(predicate, pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Bootcamp> getSingleBootcampById(@PathVariable Long id) {
		return new ResponseEntity<>(
				bootcampService
						.getSingleBootcampById(id)
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bootcamp not found with id of " + id)),
				HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Bootcamp> createBootcamp(@RequestBody Bootcamp bootcamp) {
		return new ResponseEntity<>(bootcampService.createBootcamp(bootcamp), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Bootcamp> updateBootcamp(@PathVariable Long id, @RequestBody Bootcamp bootcamp) {
		return new ResponseEntity<>(
				bootcampService
						.updateBootcamp(id, bootcamp)
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bootcamp not found with id of " + id)),
				HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void deleteBootcamp(@PathVariable Long id) {
		if (!bootcampService.deleteBootcamp(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bootcamp not found with id of " + id);
		} else {
			ResponseEntity.ok();
		}
	}
}
