package com.beaglesecurity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectWithApplication {
	private String name;
	private String description;
	private UUID projectKey;
	private List<Application> applications;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UUID getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(UUID projectKey) {
		this.projectKey = projectKey;
	}
	public List<Application> getApplications() {
		if (applications == null) {
			applications = new ArrayList<Application>();
		}
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
}
