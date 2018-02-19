package com.challenge.starwarsapi.planet.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.challenge.starwarsapi.common.repository.NextSequenceRepository;
import com.challenge.starwarsapi.planet.model.Planet;

@Service
public class PlanetSequenceRepository extends NextSequenceRepository<Planet>{

	@Autowired
	private MongoOperations mongo;
	
	@Override
	protected Class<Planet> getPersistentClass() {
		return Planet.class;
	}

	@Override
	protected MongoOperations getMongoOperations() {
		return mongo;
	}
	
}
