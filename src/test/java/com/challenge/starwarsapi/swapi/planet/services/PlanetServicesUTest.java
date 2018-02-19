package com.challenge.starwarsapi.swapi.planet.services;

import static com.challenge.starwarsapi.commontests.planet.PlanetArgumentMatcher.planetEq;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.alderaan;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.allPlanets;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.compare;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.planetWithId;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.yavinIV;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.starwarsapi.common.exception.FieldNotValidException;
import com.challenge.starwarsapi.planet.exception.PlanetNotFoundException;
import com.challenge.starwarsapi.planet.model.Planet;
import com.challenge.starwarsapi.planet.repository.PlanetRepository;
import com.challenge.starwarsapi.planet.repository.PlanetSequenceRepository;
import com.challenge.starwarsapi.planet.services.PlanetServices;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetServicesUTest {

	@Autowired
	private PlanetServices planetServices;

	@MockBean
	private PlanetRepository planetRepository;
	
	@MockBean
	private PlanetSequenceRepository planetSequenceRepository;

	@Test
	public void addValidPlanet() {
		when(planetRepository.insert(planetEq(planetWithId(alderaan(), 1L)))).thenReturn(planetWithId(alderaan(), 1L));
		when(planetSequenceRepository.getNextSequence()).thenReturn(1L);
		try {
			Planet planetAdded = planetServices.add(alderaan());
			assertThat(planetAdded.getId(), equalTo(1L));
		} catch (FieldNotValidException e) {
			fail("No error should have been thrown");
		}
	}

	@Test
	public void addPlanetWithNullName() {
		Planet planet = alderaan();
		planet.setName(null);
		addPlanetWithInvalidField(planet, "name");
	}

	@Test
	public void addPlanetWithNullClimate() {
		Planet planet = alderaan();
		planet.setClimate(null);
		addPlanetWithInvalidField(planet, "climate");
	}

	@Test
	public void addPlanetWithNullTerrain() {
		Planet planet = alderaan();
		planet.setTerrain(null);
		addPlanetWithInvalidField(planet, "terrain");
	}
	
	@Test(expected = PlanetNotFoundException.class)
	public void addPlanetInexistent() {
		Planet planet = alderaan();
		planet.setName("PlanetNotFound");
		planetServices.add(planet);
	}
	
	@Test
	public void findPlanetById() throws PlanetNotFoundException {
		when(planetRepository.findOne(1L)).thenReturn(planetWithId(alderaan(), 1L));

		Planet planet = planetServices.findById(1L);
		compare(planet, planetWithId(alderaan(), 1L));
	}

	@Test(expected = PlanetNotFoundException.class)
	public void findPlanetByIdNotFound() throws PlanetNotFoundException {
		when(planetRepository.findOne(999L)).thenReturn(null);

		planetServices.findById(999L);
	}

	@Test
	public void findPlanetByName() throws PlanetNotFoundException {
		when(planetRepository.findByName(alderaan().getName())).thenReturn(planetWithId(alderaan(), 1L));

		Planet planet = planetServices.findByName(alderaan().getName());
		compare(planet, planetWithId(alderaan(), 1L));
	}
	
	@Test(expected = PlanetNotFoundException.class)
	public void findPlanetByNameNotFound() throws PlanetNotFoundException {
		when(planetRepository.findByName("NameNotFound")).thenReturn(null);

		planetServices.findByName("NameNotFound");
	}
	
	@Test
	public void findAllPlanetsNoItem() {
		when(planetRepository.findAll()).thenReturn(new ArrayList<Planet>());
		List<Planet> planets = planetServices.findAll();
		assertThat(planets.size(), equalTo(0));
	}

	@Test
	public void findAllPlanetsOneItem() {
		when(planetRepository.findAll()).thenReturn(Arrays.asList(planetWithId(alderaan(), 1L)));
		List<Planet> planets = planetServices.findAll();

		assertThat(planets.size(), equalTo(1));
		compare(planets.get(0), planetWithId(alderaan(), 1L));
	}

	@Test
	public void findAllPlanetsList() {
		when(planetRepository.findAll()).thenReturn(allPlanets());
		List<Planet> planets = planetServices.findAll();

		assertThat(planets.size(), equalTo(2));
		compare(planets.get(0), planetWithId(alderaan(), 1L));
		compare(planets.get(1), planetWithId(yavinIV(), 2L));
	}
	
	@Test
	public void removePlanet() {
		when(planetRepository.findOne(1L)).thenReturn(planetWithId(alderaan(), 1L));
		planetServices.delete(1L);
		verify(planetRepository).delete(1L);
	}

	@Test(expected = PlanetNotFoundException.class)
	public void removePlanetByIdNotFound() {
		when(planetRepository.findOne(999L)).thenReturn(null);
		planetServices.delete(999L);
	}

	private void addPlanetWithInvalidField(Planet planet, String expectedInvalidFieldName) {
		try {
			planetServices.add(planet);
			fail("An error should have been thrown");
		} catch (final FieldNotValidException e) {
			assertThat(e.getFieldName(), equalTo(expectedInvalidFieldName));
		}
	}

}