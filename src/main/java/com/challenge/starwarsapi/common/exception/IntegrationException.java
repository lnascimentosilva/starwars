package com.challenge.starwarsapi.common.exception;

public class IntegrationException extends RuntimeException{

	private static final long serialVersionUID = 6296561343616861589L;
	
	public IntegrationException(final String message, final Exception e) {
		super(message, e);
	}

}
