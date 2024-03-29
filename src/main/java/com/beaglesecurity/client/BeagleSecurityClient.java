/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.client;

import java.util.List;
import java.util.UUID;

import com.beaglesecurity.entities.Application;
import com.beaglesecurity.entities.ApplicationType;
import com.beaglesecurity.entities.PluginType;
import com.beaglesecurity.entities.Project;
import com.beaglesecurity.entities.ProjectWithApplication;
import com.beaglesecurity.entities.ProjectWithTeam;
import com.beaglesecurity.entities.Signature;
import com.beaglesecurity.entities.SignatureType;
import com.beaglesecurity.entities.StartTest;
import com.beaglesecurity.entities.TestRunningSession;
import com.beaglesecurity.entities.TestSession;
import com.beaglesecurity.entities.TestStatus;

public interface BeagleSecurityClient {

	/**
     * <p>
     * Returns all the projects and applications for a particular user
     * </p>
     * 
     * @return list of project
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	List<ProjectWithApplication> getAllProjects();
	
	/**
     * <p>
     * Returns all the projects and team projects
     * </p>
     * 
     * @return all the projects and team projects
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	ProjectWithTeam getAllProjectWithTeams();
	
	/**
     * <p>
     * Create a new project and return it
     * </p>
     * 
     * @param projectName Name of project
     * @param description project description
     * @return created project
     * @throws com.beaglesecurity.execptions.ValidationException
     * 		   Parameter validation failure exception
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidUrlException
     * 		   Given url is not valid
     * @throws com.beaglesecurity.execptions.ProjectAlreadyExistsException
     * 		   The given project already exists
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	Project createProject(String projectName, String description);
	
	/**
     * <p>
     * Create a new project in a team and return it
     * </p>
     * 
     * @param projectName Name of project
     * @param description project description
     * @param teamId team id to create the project
     * @return created project
     * @throws com.beaglesecurity.execptions.ValidationException
     * 		   Parameter validation failure exception
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidUrlException
     * 		   Given url is not valid
     * @throws com.beaglesecurity.execptions.ProjectAlreadyExistsException
     * 		   The given project already exists
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	Project createProject(String projectName, String description, String teamId);
	
	/**
     * <p>
     * Modify an existing project and return it
     * </p>
     * 
     * @param projectKey project key to modify
     * @param projectName Name of project
     * @param description project description
     * @return modified project
     * @throws com.beaglesecurity.execptions.ValidationException
     * 		   Parameter validation failure exception
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidUrlException
     * 		   Given url is not valid
     * @throws com.beaglesecurity.execptions.ProjectAlreadyExistsException
     * 		   The given project already exists
     * @throws com.beaglesecurity.execptions.InvalidProjectKeyException
     * 		   The given project key is not valid
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	Project modifyProject(UUID projectKey, String projectName, String description);	
	
	/**
     * <p>
     * Delete a project and return it
     * </p>
     * 
     * @param projectKey project key to delete
     * @return deleted project
     * @throws com.beaglesecurity.execptions.ValidationException
     * 		   Parameter validation failure exception
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidUrlException
     * 		   Given url is not valid
     * @throws com.beaglesecurity.execptions.InvalidProjectKeyException
     * 		   The given project key is not valid
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	Project deleteProject(UUID projectKey);

	/**
     * <p>
     * Returns application object corresponds to the given application token.
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * @return application object
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidApplicationTokenException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	Application getApplication(String applicationToken);

	/**
     * <p>
     * Returns application object corresponds to the given application token.
     * </p>
     * 
     * @param  projectKey project key to retrieve applications
     * @return list of applications under project
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidProjectKeyException
     * 		   Given project key is not valid
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
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
     * @return created application
     * @throws com.beaglesecurity.execptions.ValidationException
     * 		   Parameter validation failure exception
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidUrlException
     * 		   Given url is not valid
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
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
     * @return modified application
     * @throws com.beaglesecurity.execptions.ValidationException
     * 		   Parameter validation failure exception
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidUrlException
     * 		   Given url is not valid
     * @throws com.beaglesecurity.execptions.UrlAlreadyAddedException
     * 		   The url is added in another application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	Application modifyApplication(String applicationToken, String applicationName, String url);

	/**
     * <p>
     * Delete an application using application token
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * @return application object
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidApplicationTokenException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.TestInProgressException
     * 		   A test is in progress
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	Application deleteApplication(String applicationToken);

	/**
     * <p>
     * Gets signature of the given application
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * @return application object
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidApplicationTokenException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	Signature getSignature(String applicationToken);

	/**
     * <p>
     * Verify signature of an application
     * if signatureType is Plug-in, then pluginType needs to provide. Otherwise pluginType will be null
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * @param signatureType is type of signature
     * @param pluginType is type of plug-in
     * @return true if verify signature success
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions.
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.InvalidApplicationTokenException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	boolean verifySignature(String applicationToken, SignatureType signatureType, PluginType pluginType);

	/**
     * <p>
     * Get the result json, which contains entire vulnerabilities found in the penetration testing process in json format
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * @param resultToken is the test start token to fetch result
     * @return json result
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	String getTestResultJson(String applicationToken, String resultToken);

	/**
     * <p>
     * Starts a new test
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * 
     * @return start test result
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	StartTest startTest(String applicationToken);

	/**
     * <p>
     * Gets the status of a running session
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * @param resultToken token of a session
     * 
     * @return status of the session
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	TestStatus getTestStatus(String applicationToken, String resultToken);

	/**
     * <p>
     * Stop a running test
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * 
     * @return boolean true if stop test success, false otherwise
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	boolean stopTest(String applicationToken);

	/**
     * <p>
     * Gets a number of test sessions under an application
     * </p>
     * 
     * @param applicationToken is the unique token of application
     * @param count is the number of records to fetch
     * 
     * @return list of sessions
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	List<TestSession> getTestSessions(String applicationToken, int count);

	/**
     * <p>
     * Gets all running sessions under a user
     * </p>
     * 
     * @return list of running sessions
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	List<TestRunningSession> getTestRunningSessions();

	/**
     * <p>
     * Gets all running sessions under a team
     * </p>
     * 
     * @param teamId team id to find running session
     * @return list of running sessions
     * @throws com.beaglesecurity.execptions.GeneralAPIException
     *         General exceptions
     * @throws com.beaglesecurity.execptions.PlanNotSupportException
     *         The user plan is not supported
     * @throws com.beaglesecurity.execptions.InvalidSessionException
     * 		   Given token is not valid
     * @throws com.beaglesecurity.execptions.UnAuthorizedException
     * 		   No permission to view the application
     * @throws com.beaglesecurity.execptions.ForbiddenException
     *		   Forbidden
     * @throws com.beaglesecurity.execptions.InternalServerErrorException
     * 		   Internal server error
     */
	List<TestRunningSession> getTeamTestRunningSessions(String teamId);
}
