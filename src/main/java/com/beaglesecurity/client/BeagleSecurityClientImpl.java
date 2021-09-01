/*
 * Copyright 2021-2025 beaglesecurity.com. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * 
 * You may not use this file except in compliance with the License.
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 */

package com.beaglesecurity.client;

import java.util.List;

import org.apache.http.HttpStatus;

import com.beaglesecurity.api.results.APIResult;
import com.beaglesecurity.api.results.ProjectsResult;
import com.beaglesecurity.api.results.ProjectsWithApplicationResult;
import com.beaglesecurity.client.helper.HttpReturn;
import com.beaglesecurity.client.helper.HttpUtil;
import com.beaglesecurity.entities.Project;
import com.beaglesecurity.entities.ProjectWithApplication;
import com.beaglesecurity.execptions.ForbiddenException;
import com.beaglesecurity.execptions.GeneralAPIException;
import com.beaglesecurity.execptions.InternalServerErrorException;
import com.beaglesecurity.execptions.InvalidSessionException;
import com.beaglesecurity.execptions.PlanNotSupportException;
import com.beaglesecurity.execptions.UnAuthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeagleSecurityClientImpl implements BeagleSecurityClient{
	
	private static final String baseUrl = "https://api.dev.beaglesecure.com/rest/v2/";
	
	private String token;
	
	public BeagleSecurityClientImpl(String token) {
		this.token = token;
	}
	
	@Override
	public List<Project> getAllProjects() { 
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "projects", token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve projects.");
		}
		ProjectsResult result = null;		
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), ProjectsResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			return result.getProjects();
		} else if (ret.getCode() == HttpStatus.SC_BAD_REQUEST) {
			APIResult apiResult = convertJsonToObject(ret.getResultJson(), APIResult.class);
			if (apiResult == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			switch (apiResult.getCode()) {
				case "PLAN_NOT_SUPPORTED":
					throw new PlanNotSupportException("Your current plan is not supported API calls.");					
				case "INVALID_SESSION":					
					throw new InvalidSessionException("The given token is invalid.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}

	@Override
	public List<ProjectWithApplication> getAllProjectsWithApplications() { 
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "projects?include_applications=true", token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve projects.");
		}
		ProjectsWithApplicationResult result = null;		
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), ProjectsWithApplicationResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			return result.getProjects();
		} else if (ret.getCode() == HttpStatus.SC_BAD_REQUEST) {
			APIResult apiResult = convertJsonToObject(ret.getResultJson(), APIResult.class);
			if (apiResult == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			switch (apiResult.getCode()) {
				case "PLAN_NOT_SUPPORTED":
					throw new PlanNotSupportException("Your current plan is not supported API calls.");					
				case "INVALID_SESSION":					
					throw new InvalidSessionException("The given token is invalid.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	private <T> T convertJsonToObject(String json, Class<T> type) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, type);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	private void handleCommonExceptions(int errorCode) {
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
