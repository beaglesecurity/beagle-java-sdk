/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.client;

import org.apache.http.HttpStatus;

import com.beaglesecurity.execptions.ForbiddenException;
import com.beaglesecurity.execptions.GeneralAPIException;
import com.beaglesecurity.execptions.InternalServerErrorException;
import com.beaglesecurity.execptions.UnAuthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BeagleSecurityClientBase {
	protected static final String baseUrl = "https://api.beaglesecurity.com/rest/v2/";	
	protected String token;	
	/**
     * <p>
     * Convert a json string to object given as type
     * </p>
     * @param json string to convert as object
     * @param type the type to which the json string needs to convert
     * @param <T> passing class type
     * @return Converted json object
     */
	protected <T> T convertJsonToObject(String json, Class<T> type) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, type);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
     * <p>
     * Generate common exceptions based or REST API return code
     * </p>
     * 
     * @param errorCode is the code for generating custom exception
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
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
