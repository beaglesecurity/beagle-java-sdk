package com.beaglesecurity.client;

import org.apache.http.HttpStatus;

import com.beaglesecurity.execptions.ForbiddenException;
import com.beaglesecurity.execptions.GeneralAPIException;
import com.beaglesecurity.execptions.InternalServerErrorException;
import com.beaglesecurity.execptions.UnAuthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BeagleSecurityClientBase {
	protected static final String baseUrl = "https://api.dev.beaglesecure.com/rest/v2/";
	protected String token;
	protected String formatCreateApplication = "{\"name\" : \"%s\",\"url\" : \"%s\",\"projectKey\" : \"%s\",\"type\" : \"%s\"}";
	protected <T> T convertJsonToObject(String json, Class<T> type) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, type);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	protected void handleCommonExceptions(int errorCode) {
		if (errorCode == HttpStatus.SC_FORBIDDEN) {
			throw new ForbiddenException("Forbidden.");
		} else if (errorCode == HttpStatus.SC_UNAUTHORIZED) {
			throw new UnAuthorizedException("Un authorized.");
		} else if (errorCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
			throw new InternalServerErrorException("Internal server error.");
		} else {
			throw new GeneralAPIException("Some error has occured.");
		}
	}
}
