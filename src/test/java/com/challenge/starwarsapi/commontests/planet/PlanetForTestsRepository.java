package com.challenge.starwarsapi.commontests.planet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;

import com.challenge.starwarsapi.planet.model.Planet;

@Ignore
public class PlanetForTestsRepository {
	
	public static Planet alderaan() {
		Planet alderaan = new Planet("Alderaan", "temperate", "grasslands, mountains");
		alderaan.setMovieAppearances(2);
		return alderaan;
	}

	public static Planet yavinIV() {
		Planet yavinIV = new Planet("Yavin IV", "temperate, tropical", "jungle, rainforests");
		yavinIV.setMovieAppearances(1);
		return yavinIV;
	}

	public static List<Planet> allPlanets() {
		return Arrays.asList(planetWithId(alderaan(),1L), planetWithId(yavinIV(),2L));
	}

	public static Planet planetWithId(final Planet planet, final Long id) {
		planet.setId(id);
		return planet;
	}
	
	public static void compare(Planet planet, Planet otherPlanet) {
		assertThat(planet, notNullValue());
		assertThat(otherPlanet, notNullValue());
		assertThat(planet.getId(), equalTo(otherPlanet.getId()));
		assertThat(planet.getName(), equalTo(otherPlanet.getName()));
		assertThat(planet.getClimate(), equalTo(otherPlanet.getClimate()));
		assertThat(planet.getTerrain(), equalTo(otherPlanet.getTerrain()));
		assertThat(planet.getMovieAppearances(), equalTo(otherPlanet.getMovieAppearances()));
	}
}
