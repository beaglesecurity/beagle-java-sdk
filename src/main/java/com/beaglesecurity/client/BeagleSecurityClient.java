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

import com.beaglesecurity.entities.Application;
import com.beaglesecurity.entities.ApplicationType;
import com.beaglesecurity.entities.PluginType;
import com.beaglesecurity.entities.Project;
import com.beaglesecurity.entities.ProjectWithApplication;
import com.beaglesecurity.entities.Signature;
import com.beaglesecurity.entities.SignatureType;
import com.beaglesecurity.execptions.InvalidApplicationTokenException;

public interface BeagleSecurityClient {

	/**
     * <p>
     * Returns all the projects for a user without application in it
     * </p>
     * 
     * @return List<Project> list of project
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	List<Project> getAllProjects();

	/**
     * <p>
     * Returns all the projects and applications for a particular user
     * </p>
     * 
     * @return List<ProjectWithApplication> list of project
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	List<ProjectWithApplication> getAllProjectsWithApplications();
	
	/**
     * <p>
     * Create a new project and return it
     * </p>
     * 
     * @param projectName Name of project
     * @param description project description
     * @return Project created project
     * @throws ValidationException
     * 		   Parameter validation failure exception
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidUrlException
     * 		   Given url is not valid
     * @throws ProjectAlreadyExistsException
     * 		   The given project already exists
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	Project createProject(String projectName, String description);
	
	/**
     * <p>
     * Modify an existing project and return it
     * </p>
     * 
     * @param projectKey project key to modify
     * @param projectName Name of project
     * @param description project description
     * @return Project modified project
     * @throws ValidationException
     * 		   Parameter validation failure exception
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidUrlException
     * 		   Given url is not valid
     * @throws ProjectAlreadyExistsException
     * 		   The given project already exists
     * @throws InvalidProjectKeyException
     * 		   The given project key is not valid
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	Project modifyProject(UUID projectKey, String projectName, String description);	
	
	/**
     * <p>
     * Delete a project and return it
     * </p>
     * 
     * @param projectKey project key to delete
     * @return Project deleted project
     * @throws ValidationException
     * 		   Parameter validation failure exception
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidUrlException
     * 		   Given url is not valid
     * @throws InvalidProjectKeyException
     * 		   The given project key is not valid
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	Project deleteProject(UUID projectKey);

	/**
     * <p>
     * Returns application object corresponds to the given application token.
     * </p>
     * 
     * @param applicationToken
     * @return Application object
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidApplicationTokenException
     * 		   Given token is not valid
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	Application getApplication(String applicationToken);

	/**
     * <p>
     * Returns application object corresponds to the given application token.
     * </p>
     * 
     * @param  projectKey project key to retrieve applications
     * @return List<Application> list of applications under project
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidProjectKeyException
     * 		   Given project key is not valid
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	List<Application> getApplications(UUID projectKey);

	/**
     * <p>
     * Create a new application and return it
     * </p>
     * 
     * @param applicationName Name of application
     * @param url website url for penetration testing
     * @param projectKey project key under the application needs to add
     * @param type application type such as WEB or API
     * @return Application created application
     * @throws ValidationException
     * 		   Parameter validation failure exception
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidUrlException
     * 		   Given url is not valid
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	Application createApplication(String applicationName, String url, UUID projectKey, ApplicationType type);

	/**
     * <p>
     * Modify an existing application and return it
     * </p>
     * 
     * @param applicationToken token of the application
     * @param applicationName Name of application to modify
     * @param url website url to modify. If domain is verified, then unable to modify url
     * @return Application modified application
     * @throws ValidationException
     * 		   Parameter validation failure exception
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidUrlException
     * 		   Given url is not valid
     * @throws UrlAlreadyAddedException
     * 		   The url is added in another application
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	Application modifyApplication(String applicationToken, String applicationName, String url);

	/**
     * <p>
     * Delete an application using application token
     * </p>
     * 
     * @param applicationToken
     * @return Application object
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidApplicationTokenException
     * 		   Given token is not valid
     * @throws TestInProgressException
     * 		   A test is in progress
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	Application deleteApplication(String applicationToken);

	/**
     * <p>
     * Gets signature of the given application
     * </p>
     * 
     * @param applicationToken
     * @return Application object
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidApplicationTokenException
     * 		   Given token is not valid
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	Signature getSignature(String applicationToken);

	/**
     * <p>
     * Verify signature of an application
     * if signatureType is Plugin, then pluginType needs to provide. Otherwise pluginType will be null
     * </p>
     * 
     * @param applicationToken
     * @param signatureType
     * @param pluginType
     * @return boolean true if verify signature success
     * @throws GeneralAPIException
     *         General exceptions.
     * @throws PlanNotSupportException
     *         The user plan is not supported
     * @throws InvalidSessionException
     * 		   Given token is not valid
     * @throws UnAuthorizedException
     * 		   No permission to view the application
     * @throws InvalidApplicationTokenException
     * 		   Given token is not valid
     * @throws ForbiddenException
     *		   Forbidden
     * @throws InternalServerErrorException
     * 		   Internal server error
     */
	boolean verifySignature(String applicationToken, SignatureType signatureType, PluginType pluginType);
}
