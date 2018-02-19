package com.challenge.starwarsapi.swapi.planet.model;

import java.util.List;

public class Planet {
	private String name;
	private List<Film> films;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilms() {
		return films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}
}
