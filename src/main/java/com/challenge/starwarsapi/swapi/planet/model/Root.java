package com.challenge.starwarsapi.swapi.planet.model;

import java.util.List;

public class Root {

	private int count;
	private List<Planet> results;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Planet> getResults() {
		return results;
	}

	public void setResults(List<Planet> results) {
		this.results = results;
	}
}
