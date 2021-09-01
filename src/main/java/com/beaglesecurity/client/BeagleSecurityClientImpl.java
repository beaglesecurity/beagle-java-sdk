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
import java.util.UUID;

import org.apache.http.HttpStatus;

import com.beaglesecurity.api.payloads.CreateApplication;
import com.beaglesecurity.api.payloads.CreateApplicationResult;
import com.beaglesecurity.api.results.APIResult;
import com.beaglesecurity.api.results.ApplicationResult;
import com.beaglesecurity.api.results.ApplicationsResult;
import com.beaglesecurity.api.results.ProjectsResult;
import com.beaglesecurity.api.results.ProjectsWithApplicationResult;
import com.beaglesecurity.client.helper.HttpReturn;
import com.beaglesecurity.client.helper.HttpUtil;
import com.beaglesecurity.entities.Application;
import com.beaglesecurity.entities.ApplicationType;
import com.beaglesecurity.entities.Project;
import com.beaglesecurity.entities.ProjectWithApplication;
import com.beaglesecurity.entities.SignatureStatus;
import com.beaglesecurity.execptions.GeneralAPIException;
import com.beaglesecurity.execptions.InvalidApplicationTokenException;
import com.beaglesecurity.execptions.InvalidProjectKeyException;
import com.beaglesecurity.execptions.InvalidSessionException;
import com.beaglesecurity.execptions.InvalidUrlException;
import com.beaglesecurity.execptions.PlanNotSupportException;
import com.beaglesecurity.execptions.UnAuthorizedException;
import com.beaglesecurity.execptions.ValidationException;

public class BeagleSecurityClientImpl extends BeagleSecurityClientBase implements BeagleSecurityClient{

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
	
	@Override
	public Application getApplication(String applicationToken) {
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "applications?application_token=" + applicationToken, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve application.");
		}
		ApplicationResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), ApplicationResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			return result.getApplication();
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
				case "NOT_AUTHORIZED":					
					throw new UnAuthorizedException("You are not authorized.");
				case "INVALI_APPLICATION_TOKEN":					
					throw new InvalidApplicationTokenException("Invalid application token.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	@Override
	public List<Application> getApplications(UUID projectKey) {
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "applications?project_key=" + projectKey, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve applications.");
		}
		ApplicationsResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), ApplicationsResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			return result.getApplications();
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
				case "NOT_AUTHORIZED":					
					throw new UnAuthorizedException("You are not authorized.");
				case "INVALI_PROJECT_KEY":					
					throw new InvalidProjectKeyException("Invalid project key.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	@Override
	public Application createApplication(String applicationName, String url, UUID projectKey, ApplicationType type) {
		if (applicationName == null || applicationName.trim().length() == 0) {
			throw new ValidationException("Invalid application name.");
		}
		if (projectKey == null) {
			throw new ValidationException("Invalid project key.");
		}
		if (url == null || url.trim().length() == 0) {
			throw new ValidationException("Invalid url.");
		}
		CreateApplication app = new CreateApplication();
		app.setName(applicationName);
		app.setProjectKey(projectKey);
		app.setUrl(url);
		app.setType(type == null ? ApplicationType.WEB : type);
		HttpReturn ret = HttpUtil.postRequest(baseUrl + "applications", app, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to create application.");
		}
		CreateApplicationResult result = null;
		if (ret.getCode() == HttpStatus.SC_CREATED) {
			result = convertJsonToObject(ret.getResultJson(), CreateApplicationResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			Application application = new Application();
			application.setApplicationToken(result.getApplicationToken());
			String appType = result.getType() == null? "WEB" : result.getType().name();
			application.setApplicationType(appType);
			application.setName(result.getName());
			SignatureStatus sigStatus = SignatureStatus.NotVerified;
			if (Boolean.TRUE.equals(result.getSignatureVerified())) {
				sigStatus = SignatureStatus.Verified;
			}
			application.setSignatureStatus(sigStatus);
			application.setUrl(result.getUrl());
			return application;
			
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
				case "NOT_AUTHORIZED":					
					throw new UnAuthorizedException("You are not authorized.");
				case "INVALID_URL":					
					throw new InvalidUrlException("Invalid url provided.");
				case "INVALI_APPLICATION_TOKEN":
					throw new InvalidApplicationTokenException("Invalid application token.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
}
