package com.challenge.starwarsapi.planet.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Planet {

	@Id
	private Long id;
	@Indexed
	@NotNull
	private String name;
	@NotNull
	private String climate;
	@NotNull
	private String terrain;
	private int movieAppearances;

	public Planet() {
	}

	public Planet(String name, String climate, String terrain) {
		super();
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public int getMovieAppearances() {
		return movieAppearances;
	}

	public void setMovieAppearances(int movieAppearances) {
		this.movieAppearances = movieAppearances;
	}

	@Override
	public String toString() {
		return "Planet [id=" + id + ", name=" + name + ", climate=" + climate + ", terrain=" + terrain + "]";
	}

}