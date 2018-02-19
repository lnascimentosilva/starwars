package com.challenge.starwarsapi.commontests.planet;

import static com.challenge.starwarsapi.commontests.planet.PlanetForTestsRepository.compare;
import static org.mockito.Matchers.argThat;

import org.mockito.ArgumentMatcher;

import com.challenge.starwarsapi.planet.model.Planet;

public class PlanetArgumentMatcher extends ArgumentMatcher<Planet> {
	
	private Planet expected;

	public static Planet planetEq(Planet expected) {
		return argThat(new PlanetArgumentMatcher(expected));
	}

	public PlanetArgumentMatcher(Planet expected) {
		this.expected = expected;
	}

	@Override
	public boolean matches(Object actualObj) {
		Planet actual = (Planet) actualObj;		
		
		compare(actual, expected);
//		assertThat(actual.getId(), is(equalTo(expected.getId())));
//		assertThat(actual.getName(), is(equalTo(expected.getName())));
//		assertThat(actual.getClimate(), is(equalTo(expected.getClimate())));
//		assertThat(actual.getTerrain(), is(equalTo(expected.getTerrain())));
//		
		return true;
	}
}