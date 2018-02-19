package com.challenge.starwarsapi.planet.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.alderaan;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.yavinIV;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.starwarsapi.planet.exception.PlanetNotFoundException;
import com.challenge.starwarsapi.swapi.planet.services.PlanetSWAPIServices;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetSWAPIServicesUTest {

	@Autowired
	private PlanetSWAPIServices planetSWAPIServices;

	@Test
	public void findValidPlanets() {
		assertThat(planetSWAPIServices.getMovieAppearances(alderaan().getName()), equalTo(2));
		assertThat(planetSWAPIServices.getMovieAppearances(yavinIV().getName()), equalTo(1));
	}
	
	@Test(expected = PlanetNotFoundException.class)
	public void findInValidPlanets() {
		planetSWAPIServices.getMovieAppearances("PlanetNotFound");
	}

}