package com.challenge.starwarsapi.planet.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.starwarsapi.common.exception.FieldNotValidException;
import com.challenge.starwarsapi.common.utils.builder.ResponseBuilder;
import com.challenge.starwarsapi.planet.exception.PlanetNotFoundException;
import com.challenge.starwarsapi.planet.model.Planet;
import com.challenge.starwarsapi.planet.services.PlanetServices;

@RestController
@RequestMapping("/planets")
public class PlanetResources {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String RESOURCE = "planet";

	@Autowired
	private PlanetServices planetServices;

	@GetMapping()
	public List<Planet> findAll() {
		return planetServices.findAll();
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Planet planet) {

		try {
			planet = planetServices.add(planet);
			return ResponseBuilder.getCreated(planet.getId());
		} catch (FieldNotValidException e) {
			logger.error("The field \"{}\" of the planet is not valid", e.getFieldName(), e);
			return ResponseBuilder.getInvalidField(RESOURCE, e);
		} catch (PlanetNotFoundException e) {
			logger.error("No planet found for the given name \"{}\"", planet.getName(), e);
			return ResponseBuilder.getNotFound(RESOURCE);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		try {
			Planet planet = planetServices.findById(id);
			return ResponseEntity.ok(planet);
		} catch (PlanetNotFoundException e) {
			logger.error("No planet found for the given id \"{}\"", id, e);
			return ResponseBuilder.getNotFound(RESOURCE);
		}
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<?> findById(@PathVariable("name") String name) {
		try {
			Planet planet = planetServices.findByName(name);
			return ResponseEntity.ok(planet);
		} catch (PlanetNotFoundException e) {
			logger.error("No planet found for the given name \"{}\"", name, e);
			return ResponseBuilder.getNotFound(RESOURCE);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		try {
			planetServices.delete(id);
			return ResponseEntity.noContent().build();
		} catch (PlanetNotFoundException e) {
			logger.error("No planet found for the given id \"{}\"", id, e);
			return ResponseBuilder.getNotFound(RESOURCE);
		}
	}

}
