/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.client;

import java.util.List;
import java.util.UUID;

import org.apache.http.HttpStatus;

import com.beaglesecurity.api.payloads.CreateApplication;
import com.beaglesecurity.api.payloads.CreateProject;
import com.beaglesecurity.api.payloads.ModifyApplication;
import com.beaglesecurity.api.payloads.ModifyProject;
import com.beaglesecurity.api.payloads.SignatureVerify;
import com.beaglesecurity.api.payloads.StartTestParam;
import com.beaglesecurity.api.payloads.StopTestParam;
import com.beaglesecurity.api.results.APIResult;
import com.beaglesecurity.api.results.ApplicationResult;
import com.beaglesecurity.api.results.ApplicationsResult;
import com.beaglesecurity.api.results.CreateApplicationResult;
import com.beaglesecurity.api.results.CreateProjectResult;
import com.beaglesecurity.api.results.ProjectWithTeamResult;
import com.beaglesecurity.api.results.ProjectsWithApplicationResult;
import com.beaglesecurity.api.results.SignatureResult;
import com.beaglesecurity.api.results.StartTestResult;
import com.beaglesecurity.api.results.TestResult;
import com.beaglesecurity.api.results.TestRunningSessionResult;
import com.beaglesecurity.api.results.TestSessionResult;
import com.beaglesecurity.api.results.TestStatusResult;
import com.beaglesecurity.client.helper.HttpReturn;
import com.beaglesecurity.client.helper.HttpUtil;
import com.beaglesecurity.entities.Application;
import com.beaglesecurity.entities.ApplicationType;
import com.beaglesecurity.entities.PluginType;
import com.beaglesecurity.entities.Project;
import com.beaglesecurity.entities.ProjectWithApplication;
import com.beaglesecurity.entities.ProjectWithTeam;
import com.beaglesecurity.entities.Signature;
import com.beaglesecurity.entities.SignatureStatus;
import com.beaglesecurity.entities.SignatureType;
import com.beaglesecurity.entities.StartTest;
import com.beaglesecurity.entities.TestRunningSession;
import com.beaglesecurity.entities.TestSession;
import com.beaglesecurity.entities.TestStatus;
import com.beaglesecurity.execptions.GeneralAPIException;
import com.beaglesecurity.execptions.InvalidApplicationTokenException;
import com.beaglesecurity.execptions.InvalidPluginTypeException;
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
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getAllProjectsWithApplications()
	 */
	@Override
	public List<ProjectWithApplication> getAllProjects() { 
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "projects", token);
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
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getAllProjectWithTeams()
	 */
	@Override
	public ProjectWithTeam getAllProjectWithTeams() { 
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "projects?include_team=true", token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve projects with team.");
		}
		ProjectWithTeamResult apiResult = null;		
		if (ret.getCode() == HttpStatus.SC_OK) {
			apiResult = convertJsonToObject(ret.getResultJson(), ProjectWithTeamResult.class);
			if (apiResult == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			ProjectWithTeam result = new ProjectWithTeam();
			result.setMyProjects(apiResult.getMyProjects());
			result.setTeamProjects(apiResult.getTeamProjects());
			return result;
		} else if (ret.getCode() == HttpStatus.SC_BAD_REQUEST) {
			APIResult apiErrorResult = convertJsonToObject(ret.getResultJson(), APIResult.class);
			if (apiErrorResult == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			switch (apiErrorResult.getCode()) {
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
		return createProject(projectName, description, null);
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#createProject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Project createProject(String projectName, String description, String teamId) {
		if (projectName == null || projectName.trim().length() == 0) {
			throw new ValidationException("Invalid project name.");
		}
		CreateProject proj = new CreateProject();
		proj.setName(projectName);
		proj.setDescription(description);
		HttpReturn ret = null;
		if (teamId == null) {
			ret = HttpUtil.postRequest(baseUrl + "projects", proj, token);
		} else {
			ret = HttpUtil.postRequest(baseUrl + "projects?teamid=" + teamId, proj, token);
		}
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
				case "URL_ALREADY_ADDED":
					throw new UrlAlreadyAddedException("Url is already added in another application.");
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
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getSignature(java.lang.String)
	 */
	@Override
	public Signature getSignature(String applicationToken) {
		HttpReturn ret = HttpUtil.deleteRequest(baseUrl + "applications/signature?application_token=" + applicationToken, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to get signature.");
		}
		SignatureResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), SignatureResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			Signature signature = new Signature();
			signature.setApiSignature(result.getApiSignature());
			signature.setDnsSignature(result.getDnsSignature());
			signature.setFileSignature(result.getFileSignature());
			signature.setStatus(result.getStatus());
			signature.setUrl(result.getUrl());
			return signature;			
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
				case "FAILED":
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
	 * @see com.beaglesecurity.client.BeagleSecurityClient#verifySignature(java.lang.String, com.beaglesecurity.entities.SignatureType, com.beaglesecurity.entities.PluginType)
	 */
	@Override
	public boolean verifySignature(String applicationToken, SignatureType signatureType, PluginType pluginType) {		
		if (applicationToken == null || applicationToken.trim().length() == 0) {
			throw new ValidationException("Invalid application token.");
		}
		if (signatureType == null) {
			throw new ValidationException("Invalid signature type.");
		}
		if (signatureType == SignatureType.Plugin && pluginType == null) {
			throw new ValidationException("Invalid plugin type.");
		}
		
		SignatureVerify verify = new SignatureVerify();
		verify.setApplicationToken(applicationToken);
		verify.setSignatureType(signatureType);
		verify.setPluginType(pluginType);
		HttpReturn ret = HttpUtil.postRequest(baseUrl + "applications/signature?application_token=" + applicationToken, verify, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to get signature.");
		}		
		if (ret.getCode() == HttpStatus.SC_OK) {
			return true;					
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
				case "FAILED":
					throw new InvalidApplicationTokenException("Invalid application token.");
				case "INVALID_PLUGIN_TYPE":
					throw new InvalidPluginTypeException("Invalid plugin type.");
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getTestResultJson(java.lang.String, java.lang.String)
	 */
	@Override
	public String getTestResultJson(String applicationToken, String resultToken) {
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "test/result?application_token=" + applicationToken + "&result_token=" + resultToken, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve test result.");
		}
		TestResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), TestResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			return result.getResult();
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
				default:
					throw new GeneralAPIException("Failed to retrieve test result.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#startTest(java.lang.String)
	 */
	@Override
	public StartTest startTest(String applicationToken) {		
		if (applicationToken == null || applicationToken.trim().length() == 0) {
			throw new ValidationException("Invalid application token.");
		}
		StartTestParam start = new StartTestParam();
		start.setApplicationToken(applicationToken);
		
		
		HttpReturn ret = HttpUtil.postRequest(baseUrl + "test/start" + applicationToken, start, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to start test.");
		}		
		if (ret.getCode() == HttpStatus.SC_OK) {			
			StartTestResult result = convertJsonToObject(ret.getResultJson(), StartTestResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			StartTest retResult = new StartTest();
			retResult.setResultToken(result.getResult_token());
			retResult.setResultUrl(result.getResult_url());
			retResult.setStatusUrl(result.getStatus_url());
			return retResult;
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
				case "FAILED":					
					throw new GeneralAPIException(apiResult.getMessage());
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getTestStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public TestStatus getTestStatus(String applicationToken, String resultToken) {
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "test/status?application_token=" + applicationToken + "&result_token=" + resultToken, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve test status.");
		}
		TestStatusResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), TestStatusResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			TestStatus status = new TestStatus();
			status.setProgress(result.getProgress());
			status.setStatus(result.getStatus());
			return status;
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
				case "FAILED":					
					throw new GeneralAPIException(apiResult.getMessage());
				default:
					throw new GeneralAPIException("Failed to retrieve test result.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#stopTest(java.lang.String)
	 */
	@Override
	public boolean stopTest(String applicationToken) {		
		if (applicationToken == null || applicationToken.trim().length() == 0) {
			throw new ValidationException("Invalid application token.");
		}
		StopTestParam stop = new StopTestParam();
		stop.setApplicationToken(applicationToken);
		HttpReturn ret = HttpUtil.postRequest(baseUrl + "test/stop" + applicationToken, stop, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to stop test.");
		}		
		if (ret.getCode() == HttpStatus.SC_OK) {			
			return true;
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
				case "FAILED":					
					throw new GeneralAPIException(apiResult.getMessage());
				default:
					throw new GeneralAPIException("Some error has occured.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getTestSessions(java.lang.String, int)
	 */
	@Override
	public List<TestSession> getTestSessions(String applicationToken, int count) {
		HttpReturn ret = HttpUtil.getRequest(baseUrl + "test/sessions?application_token=" + applicationToken + "&count=" + count, token);
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve test sessions.");
		}
		TestSessionResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), TestSessionResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			return result.getSessions();
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
				case "INVALID_APPLICATION_TOKEN":
					throw new InvalidApplicationTokenException("Application token is invalid.");
				case "NOT_AUTHORIZED":					
					throw new UnAuthorizedException("You are not authorized.");
				case "FAILED":					
					throw new GeneralAPIException(apiResult.getMessage());
				default:
					throw new GeneralAPIException("Failed to retrieve test result.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getTestRunningSessions()
	 */
	@Override
	public List<TestRunningSession> getTestRunningSessions() {
		return getTeamTestRunningSessions(null);
	}
	

	/* (non-Javadoc)
	 * @see com.beaglesecurity.client.BeagleSecurityClient#getTeamTestRunningSessions(java.lang.String)
	 */
	@Override
	public List<TestRunningSession> getTeamTestRunningSessions(String teamId) {
		HttpReturn ret = null;		
		if (teamId == null) {
			ret = HttpUtil.getRequest(baseUrl + "test/runningsessions", token);
		} else {
			ret = HttpUtil.getRequest(baseUrl + "test/runningsessions?teamid=" + teamId, token);
		}
		if (ret == null) {
			throw new GeneralAPIException("Failed to retrieve running sessions.");
		}
		TestRunningSessionResult result = null;
		if (ret.getCode() == HttpStatus.SC_OK) {
			result = convertJsonToObject(ret.getResultJson(), TestRunningSessionResult.class);
			if (result == null) {
				throw new GeneralAPIException("Failed to retrieve json data.");
			}
			return result.getSessions();
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
				case "FAILED":					
					throw new GeneralAPIException(apiResult.getMessage());
				default:
					throw new GeneralAPIException("Failed to retrieve test result.");
			}
		} else {
			handleCommonExceptions(ret.getCode());
		}
		return null;
	}
}
