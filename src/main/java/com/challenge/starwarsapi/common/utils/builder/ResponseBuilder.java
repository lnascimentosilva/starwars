package com.challenge.starwarsapi.common.utils.builder;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.challenge.starwarsapi.common.exception.FieldNotValidException;

public final class ResponseBuilder {

	private ResponseBuilder() {
	}

	public static ResponseEntity<?> getCreated(Long id) {
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(location).build();
	}

	public static ResponseEntity<?> getInvalidField(String resource, FieldNotValidException e) {
		String entity = String.format(
				"{ \"errorIdentification\": \"%s.invalidField.%s\", \"errorDescription\": \"%s\" }", resource,
				e.getFieldName(), e.getMessage());
		return new ResponseEntity<String>(entity, HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<?> getNotFound(String resource) {
		String entity = String.format(
				"{ \"errorIdentification\": \"%s.notFound\", \"errorDescription\": \"%s not found\" }", resource,
				resource);
		return new ResponseEntity<String>(entity, HttpStatus.NOT_FOUND);
	}
}
