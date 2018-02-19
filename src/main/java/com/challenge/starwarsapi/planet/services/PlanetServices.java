package com.challenge.starwarsapi.planet.services;

import java.util.List;

import com.challenge.starwarsapi.common.exception.FieldNotValidException;
import com.challenge.starwarsapi.planet.exception.PlanetNotFoundException;
import com.challenge.starwarsapi.planet.model.Planet;

public interface PlanetServices {
	
	public Planet add(Planet planet) throws PlanetNotFoundException, FieldNotValidException;
	public Planet findById(Long id) throws PlanetNotFoundException;
	public Planet findByName(String name) throws PlanetNotFoundException;
	public List<Planet> findAll();
	public void delete(Long id) throws PlanetNotFoundException;

}
