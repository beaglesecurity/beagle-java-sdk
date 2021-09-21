/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamProject {
	private String teamName;
	private UUID teamId;
	private List<ProjectWithApplication> projects;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public UUID getTeamId() {
		return teamId;
	}
	public void setTeamId(UUID teamId) {
		this.teamId = teamId;
	}
	public List<ProjectWithApplication> getProjects() {
		if (projects == null) {
			projects = new ArrayList<ProjectWithApplication>();
		}
		return projects;
	}
	public void setProjects(List<ProjectWithApplication> projects) {
		this.projects = projects;
	}
}
