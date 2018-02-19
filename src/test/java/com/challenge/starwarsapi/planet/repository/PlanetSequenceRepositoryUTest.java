package com.challenge.starwarsapi.planet.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.starwarsapi.planet.repository.PlanetSequenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetSequenceRepositoryUTest {
	
	@Autowired
	private PlanetSequenceRepository planetSequenceRepository;
	
	@Test
	public void getSequences() {
		assertThat(planetSequenceRepository.getNextSequence(), equalTo(1L));
		assertThat(planetSequenceRepository.getNextSequence(), equalTo(2L));
		assertThat(planetSequenceRepository.getNextSequence(), equalTo(3L));
	}
}
