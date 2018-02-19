package com.challenge.starwarsapi.planet.services;

import java.util.List;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.starwarsapi.common.utils.validation.ValidationUtils;
import com.challenge.starwarsapi.planet.exception.PlanetNotFoundException;
import com.challenge.starwarsapi.planet.model.Planet;
import com.challenge.starwarsapi.planet.repository.PlanetRepository;
import com.challenge.starwarsapi.planet.repository.PlanetSequenceRepository;
import com.challenge.starwarsapi.swapi.planet.services.PlanetSWAPIServicesImpl;

@Service
public class PlanetServicesImpl implements PlanetServices {

	@Autowired
	private PlanetRepository planetRepository;
	
	@Autowired
	private PlanetSequenceRepository planetSequenceRepository;
	
	@Autowired
	private PlanetSWAPIServicesImpl planetSWAPIService;
	
	@Autowired
	private Validator validator;

	@Override
	public Planet add(Planet planet) {
		validate(planet);
		planet.setMovieAppearances(planetSWAPIService.getMovieAppearances(planet.getName()));
		planet.setId(planetSequenceRepository.getNextSequence());
		return planetRepository.insert(planet);
	}
	
	private void validate(Planet planet) {
		ValidationUtils.validateEntityFields(validator, planet);
	}

	@Override
	public Planet findById(Long id) {
		Planet planet = planetRepository.findOne(id);
		if (planet == null) {
			throw new PlanetNotFoundException();
		}
		return planet;
	}

	@Override
	public Planet findByName(String name) {
		Planet planet = planetRepository.findByName(name);
		if (planet == null) {
			throw new PlanetNotFoundException();
		}
		return planet;
	}

	@Override
	public List<Planet> findAll() {
		return planetRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		Planet planet = planetRepository.findOne(id);
		if (planet == null) {
			throw new PlanetNotFoundException();
		}
		planetRepository.delete(id);
	}

}