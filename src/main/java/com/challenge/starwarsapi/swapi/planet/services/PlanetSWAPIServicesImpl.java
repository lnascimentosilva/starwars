package com.challenge.starwarsapi.swapi.planet.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.challenge.starwarsapi.planet.exception.PlanetNotFoundException;
import com.challenge.starwarsapi.swapi.planet.model.Planet;
import com.challenge.starwarsapi.swapi.planet.model.Root;

@Service
public class PlanetSWAPIServicesImpl implements PlanetSWAPIServices {

	@Value("${swapi.planet.name.url}")
	private String url;

	@Override
	public int getMovieAppearances(String planetName) throws PlanetNotFoundException {
		return this.getQuantity(this.getJson(planetName), planetName);
	}

	private Root getJson(String planetName) {
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        //To avoid 403 error
        headers.add("user-agent", "required");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<Root> response = restTemplate.exchange(url, HttpMethod.GET, entity, Root.class, planetName);
		
		return response.getBody();
	}

	private int getQuantity(Root root, String planetName) {
		if (root.getCount() > 0) {
			for (Planet planet : root.getResults()) {
				if (planet.getName().equalsIgnoreCase(planetName)) {
					return planet.getFilms().size();
				}
			}
		}
		
		throw new PlanetNotFoundException();
	}
}