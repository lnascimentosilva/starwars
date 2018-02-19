package com.challenge.starwarsapi.planet.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.challenge.starwarsapi.planet.model.Planet;

public interface PlanetRepository extends MongoRepository<Planet, Long> {
	
    public Planet findByName(String name);
    
 }