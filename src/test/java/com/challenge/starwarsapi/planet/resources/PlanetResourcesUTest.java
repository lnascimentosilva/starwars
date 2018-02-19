package com.challenge.starwarsapi.planet.resources;

import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.alderaan;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.allPlanets;
import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.planetWithId;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.starwarsapi.common.exception.FieldNotValidException;
import com.challenge.starwarsapi.common.utils.urlreader.URLContentReader;
import com.challenge.starwarsapi.planet.exception.PlanetNotFoundException;
import com.challenge.starwarsapi.planet.model.Planet;
import com.challenge.starwarsapi.planet.services.PlanetServices;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PlanetResources.class, secure = false)
public class PlanetResourcesUTest {

	private static final String MAY_NOT_BE_NULL = "may not be null";

	@Autowired
	private MockMvc mockMvc;

	@Value("${input.planet.aldeeran}")
	private URL aldeeran;

	@Value("${output.planet.find-all-more-than-one-planet}")
	private URL findAllMoreThanOnePlanet;

	@Value("${output.planet.find-all-one-planet}")
	private URL findAllOnePlanet;

	@Value("${output.planet.find-all-no-planet}")
	private URL findAllNoPlanet;

	@Value("${output.planet.find-one-planet}")
	private URL findOnePlanet;

	@Value("${output.planet.find-planet-not-found}")
	private URL findPlanetNotFound;	
	
	@Value("${output.planet.error-invalid-name}")
	private URL errorInvalidName;
	
	@Value("${output.planet.error-invalid-climate}")
	private URL errorInvalidClimate;
	
	@Value("${output.planet.error-invalid-terrain}")
	private URL errorInvalidTerrain;
	


	@MockBean
	private PlanetServices planetServices;

	@Test
	public void addValidPlanet() throws Exception {
		when(planetServices.add(any(Planet.class))).thenReturn(planetWithId(alderaan(), 1L));

		String content = URLContentReader.getContent(aldeeran);
		mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print())
				.andExpect(status().isCreated()).andExpect(header().string("Location", containsString("/planets/1")));
	}

	@Test
	public void addPlanetWithNullName() throws Exception {
		when(planetServices.add(any(Planet.class))).thenThrow(new FieldNotValidException("name", MAY_NOT_BE_NULL));
		
		String content = URLContentReader.getContent(aldeeran);
		String jsonResponse = URLContentReader.getContent(errorInvalidName);
		mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print()).andExpect(status().isBadRequest()).andExpect(content().json(jsonResponse));
	}

	@Test
	public void addPlanetWithNullClimate() throws Exception  {
		when(planetServices.add(any(Planet.class))).thenThrow(new FieldNotValidException("climate", MAY_NOT_BE_NULL));
		
		String content = URLContentReader.getContent(aldeeran);
		String jsonResponse = URLContentReader.getContent(errorInvalidClimate);
		mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print()).andExpect(status().isBadRequest()).andExpect(content().json(jsonResponse));
	}

	@Test
	public void addPlanetWithNullTerrain() throws Exception  {
		when(planetServices.add(any(Planet.class))).thenThrow(new FieldNotValidException("terrain", MAY_NOT_BE_NULL));
		
		String content = URLContentReader.getContent(aldeeran);
		String jsonResponse = URLContentReader.getContent(errorInvalidTerrain);
		mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print()).andExpect(status().isBadRequest()).andExpect(content().json(jsonResponse));
	}

	@Test
	public void addPlanetInexistent() throws Exception {
		when(planetServices.add(any(Planet.class))).thenThrow(new PlanetNotFoundException());
		
		String content = URLContentReader.getContent(aldeeran);
		String jsonResponse = URLContentReader.getContent(findPlanetNotFound);
		mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print()).andExpect(status().isNotFound()).andExpect(content().json(jsonResponse));
	}

	@Test
	public void findPlanetById() throws Exception {
		when(planetServices.findById(1L)).thenReturn(planetWithId(alderaan(), 1L));

		String jsonResponse = URLContentReader.getContent(findOnePlanet);
		mockMvc.perform(get("/planets/{id}", 1L)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findPlanetByIdNotFound() throws Exception {
		when(planetServices.findById(999L)).thenThrow(new PlanetNotFoundException());

		String jsonResponse = URLContentReader.getContent(findPlanetNotFound);
		mockMvc.perform(get("/planets/{id}", 999L)).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findPlanetByName() throws Exception {
		when(planetServices.findByName(alderaan().getName())).thenReturn(planetWithId(alderaan(), 1L));

		String jsonResponse = URLContentReader.getContent(findOnePlanet);
		mockMvc.perform(get("/planets/name/{name}", alderaan().getName())).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findPlanetByNameNotFound() throws Exception {
		when(planetServices.findByName("NameNotFound")).thenThrow(new PlanetNotFoundException());

		String jsonResponse = URLContentReader.getContent(findPlanetNotFound);
		mockMvc.perform(get("/planets/name/{name}", "NameNotFound")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findAllPlanetsMoreThanOnePlanet() throws Exception {
		when(planetServices.findAll()).thenReturn(allPlanets());

		String jsonResponse = URLContentReader.getContent(findAllMoreThanOnePlanet);
		mockMvc.perform(get("/planets")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findAllPlanetsOnePlanet() throws Exception {
		when(planetServices.findAll()).thenReturn(Arrays.asList(planetWithId(alderaan(), 1L)));

		String jsonResponse = URLContentReader.getContent(findAllOnePlanet);
		mockMvc.perform(get("/planets")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void findAllPlanetsNoPlanet() throws Exception {
		when(planetServices.findAll()).thenReturn(new ArrayList<Planet>());

		String jsonResponse = URLContentReader.getContent(findAllNoPlanet);
		mockMvc.perform(get("/planets")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}

	@Test
	public void removePlanet() throws Exception {
		mockMvc.perform(delete("/planets/{id}", 1L)).andDo(print()).andExpect(status().isNoContent());
		verify(planetServices).delete(1L);
	}

	@Test
	public void removePlanetByIdNotFound() throws Exception {
		doThrow(new PlanetNotFoundException()).when(planetServices).delete(999L);
		String jsonResponse = URLContentReader.getContent(findPlanetNotFound);
		mockMvc.perform(delete("/planets/{id}", 999L)).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().json(jsonResponse));
	}
}
