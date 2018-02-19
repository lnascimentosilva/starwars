package com.challenge.starwarsapi.planet.repository;

import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.alderaan;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.yavinIV;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.planetWithId;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.allPlanets;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.compare;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.starwarsapi.planet.model.Planet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetRepositoryUTest {

	@Autowired
	private PlanetRepository planetRepository;

	@After
	public void setDownTestCase() {
		planetRepository.deleteAll();
	}

	@Test
	public void addPlanetAndFindItById() {
		Planet planetAdded = planetRepository.insert(planetWithId(alderaan(), 1L));
		assertThat(planetAdded.getId(), notNullValue());

		Planet planetFound = planetRepository.findOne(planetAdded.getId());
		compare(planetFound, planetWithId(alderaan(), 1L));

	}

	@Test
	public void addPlanetAndFindItByName() {
		Planet planetAdded = planetRepository.insert(planetWithId(alderaan(), 1L));
		assertThat(planetAdded.getId(), notNullValue());

		Planet planetFound = planetRepository.findByName(alderaan().getName());
		compare(planetFound, planetWithId(alderaan(), 1L));

	}

	@Test(expected = DuplicateKeyException.class)
	public void addPlanetWithDuplicatedId() {
		Planet planetAdded = planetRepository.insert(planetWithId(alderaan(), 1L));
		assertThat(planetAdded.getId(), notNullValue());

		planetRepository.insert(planetWithId(alderaan(), 1L));
	}

	@Test
	public void findPlanetByIdNotFound() {
		Planet planetNotFound = planetRepository.findOne(999L);
		assertThat(planetNotFound, nullValue());
	}

	@Test
	public void findPlanetByNameNotFound() {
		Planet planetNotFound = planetRepository.findByName("Terra");
		assertThat(planetNotFound, nullValue());
	}

	@Test
	public void findAllPlanetsNoItem() {
		List<Planet> planets = planetRepository.findAll();
		assertThat(planets.size(), equalTo(0));
	}

	@Test
	public void findAllPlanetsOneItem() {
		planetRepository.insert(planetWithId(alderaan(), 1L));
		List<Planet> planets = planetRepository.findAll();

		assertThat(planets.size(), equalTo(1));
		compare(planets.get(0), planetWithId(alderaan(), 1L));
	}

	@Test
	public void findAllPlanetsList() {
		planetRepository.insert(allPlanets());
		List<Planet> planets = planetRepository.findAll();

		assertThat(planets.size(), equalTo(2));
		compare(planets.get(0), planetWithId(alderaan(), 1L));
		compare(planets.get(1), planetWithId(yavinIV(), 2L));
	}

	@Test
	public void addPlanetAndRemoveIt() {
		Planet planetAdded = planetRepository.insert(planetWithId(alderaan(), 1L));
		assertThat(planetAdded.getId(), notNullValue());

		Planet planetFound = planetRepository.findOne(planetAdded.getId());
		compare(planetFound, planetWithId(alderaan(), 1L));

		planetRepository.delete(planetAdded.getId());

		Planet planetNotFound = planetRepository.findOne(planetAdded.getId());
		assertThat(planetNotFound, nullValue());
	}

	@Test
	public void removePlanetByIdNotFound() {
		Planet planetNotFound = planetRepository.findOne(999L);
		assertThat(planetNotFound, nullValue());

		planetRepository.delete(999L);
	}

}
