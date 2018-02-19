package com.challenge.starwarsapi.common.utils.connection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.starwarsapi.common.exception.IntegrationException;

public class Connection {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Connection.class); 

	/**
	 * Obtém um URLConnection
	 */
	public static URLConnection getConnection(URL url) {		
		
		try {
			// abrindo conexão
			return url.openConnection();
			
		} catch (IOException e) {
			String msg = String.format("Não foi possível conectar a URL: %s", url);
			LOGGER.error(msg);
			throw new IntegrationException(msg, e);
		}
		
	}
	
}
