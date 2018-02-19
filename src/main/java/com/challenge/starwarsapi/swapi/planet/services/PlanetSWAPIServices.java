package com.challenge.starwarsapi.swapi.planet.services;

import com.challenge.starwarsapi.planet.exception.PlanetNotFoundException;

public interface PlanetSWAPIServices {
	
	public int getMovieAppearances(String planetName) throws PlanetNotFoundException;
}
