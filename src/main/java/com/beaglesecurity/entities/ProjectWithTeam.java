/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.entities;

import java.util.ArrayList;
import java.util.List;

public class ProjectWithTeam {
	private List<ProjectWithApplication> myProjects;
	private List<TeamProject> teamProjects;
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
}
