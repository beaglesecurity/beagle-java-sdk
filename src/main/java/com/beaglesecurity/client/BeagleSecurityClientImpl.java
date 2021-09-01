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
import com.beaglesecurity.api.payloads.CreateProject;
import com.beaglesecurity.api.payloads.CreateProjectResult;
import com.beaglesecurity.api.payloads.ModifyApplication;
import com.beaglesecurity.api.payloads.ModifyProject;
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
import com.beaglesecurity.execptions.ProjectAlreadyExistsException;
import com.beaglesecurity.execptions.TestInProgressException;
import com.beaglesecurity.execptions.UnAuthorizedException;
import com.beaglesecurity.execptions.UrlAlreadyAddedException;
import com.beaglesecurity.execptions.ValidationException;

public class BeagleSecurityClientImpl extends BeagleSecurityClientBase implements BeagleSecurityClient{

	/**
	 * @param token
	 */
	public BeagleSecurityClientImpl(String token) {
		this.token = token;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getAllProjects()
	 */
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

	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getAllProjectsWithApplications()
	 */
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
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#createProject(java.lang.String, java.lang.String)
	 */
	@Override
	public Project createProject(String projectName, String description) {
		if (projectName == null || projectName.trim().length() == 0) {
			throw new ValidationException("Invalid project name.");
		}
		CreateProject proj = new CreateProject();
		proj.setName(projectName);
		proj.setDescription(description);
		HttpReturn ret = HttpUtil.postRequest(baseUrl + "projects", proj, token);
		CreateProjectResult result = null;
		if (ret.getCode() == HttpStatus.SC_CREATED) {
			result = convertJsonToObject(ret.getResultJson(), CreateProjectResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			Project project = new Project();
			project.setName(result.getName());
			project.setDescription(result.getDescription());
			project.setProjectKey(result.getProjectKey());
			return project;
			
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
				case "PROJECT_ALREADY_EXISTS":
					throw new ProjectAlreadyExistsException("Project with name already exists.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#modifyProject(java.util.UUID, java.lang.String, java.lang.String)
	 */
	@Override
	public Project modifyProject(UUID projectKey, String projectName, String description) {
		if (projectKey == null) {
			throw new ValidationException("Invalid project key.");
		}
		if (projectName == null || projectName.trim().length() == 0) {
			throw new ValidationException("Invalid project name.");
		}
		ModifyProject proj = new ModifyProject();
		proj.setProjectKey(projectKey);
		proj.setName(projectName);
		proj.setDescription(description);
		HttpReturn ret = HttpUtil.putRequest(baseUrl + "projects", proj, token);
		CreateProjectResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), CreateProjectResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			Project project = new Project();
			project.setName(result.getName());
			project.setDescription(result.getDescription());
			project.setProjectKey(result.getProjectKey());
			return project;
			
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
				case "PROJECT_ALREADY_EXISTS":
					throw new ProjectAlreadyExistsException("Project with name already exists.");
				case "INVALID_PROJECT_KEY":
					throw new InvalidProjectKeyException("Invalid project key provided.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#deleteProject(java.util.UUID)
	 */
	@Override
	public Project deleteProject(UUID projectKey) {
		if (projectKey == null) {
			throw new ValidationException("Invalid project key.");
		}
		HttpReturn ret = HttpUtil.deleteRequest(baseUrl + "projects?project_key=" + projectKey, token);
		CreateProjectResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), CreateProjectResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			Project project = new Project();
			project.setName(result.getName());
			project.setDescription(result.getDescription());
			project.setProjectKey(result.getProjectKey());
			return project;
			
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
				case "INVALID_PROJECT_KEY":
					throw new InvalidProjectKeyException("Invalid project key provided.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
		
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getApplication(java.lang.String)
	 */
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
				case "INVALID_APPLICATION_TOKEN":					
					throw new InvalidApplicationTokenException("Invalid application token.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getApplications(java.util.UUID)
	 */
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
				case "INVALID_PROJECT_KEY":					
					throw new InvalidProjectKeyException("Invalid project key.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#createApplication(java.lang.String, java.lang.String, java.util.UUID, com.beaglesecurity.entities.ApplicationType)
	 */
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
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#modifyApplication(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Application modifyApplication(String applicationToken, String applicationName, String url) {
		if (applicationToken == null || applicationToken.trim().length() == 0) {
			throw new ValidationException("Invalid application token.");
		}
		
		if (applicationName == null || applicationName.trim().length() == 0) {
			throw new ValidationException("Invalid application name.");
		}

		ModifyApplication app = new ModifyApplication();
		app.setName(applicationName);		
		app.setApplicationToken(applicationToken);
		app.setUrl(url);
		
		HttpReturn ret = HttpUtil.putRequest(baseUrl + "applications", app, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to modify application.");
		}
		CreateApplicationResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
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
				case "INVALID_APPLICATION_TOKEN":
					throw new InvalidApplicationTokenException("Invalid application token.");					
				case "INVALID_URL":					
					throw new InvalidUrlException("Invalid url provided.");
				case "URL_ALREADY_ADDED":
					throw new UrlAlreadyAddedException("Url is already added in another application.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#deleteApplication(java.lang.String)
	 */
	@Override
	public Application deleteApplication(String applicationToken) {
		HttpReturn ret = HttpUtil.deleteRequest(baseUrl + "applications?application_token=" + applicationToken, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to delete application.");
		}
		CreateApplicationResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
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
				case "INVALID_APPLICATION_TOKEN":
					throw new InvalidApplicationTokenException("Invalid application token.");
				case "TEST_RUNNING":
					throw new TestInProgressException("A test is already running.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
}
