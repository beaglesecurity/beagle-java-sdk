/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.api.results;

import java.util.ArrayList;
import java.util.List;

import com.beaglesecurity.entities.ProjectWithApplication;
import com.beaglesecurity.entities.TeamProject;

public class ProjectWithTeamResult {
	private List<ProjectWithApplication> myProjects;
	private List<TeamProject> teamProjects;
	private String code;
	private String message;
	public List<ProjectWithApplication> getMyProjects() {
		if (myProjects == null) {
			myProjects = new ArrayList<ProjectWithApplication>();
		}
		return myProjects;
	}
	public void setMyProjects(List<ProjectWithApplication> myProjects) {
		this.myProjects = myProjects;
	}
	public List<TeamProject> getTeamProjects() {
		if (teamProjects == null) {
			teamProjects = new ArrayList<TeamProject>();
		}
		return teamProjects;
	}
	public void setTeamProjects(List<TeamProject> teamProjects) {
		this.teamProjects = teamProjects;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
