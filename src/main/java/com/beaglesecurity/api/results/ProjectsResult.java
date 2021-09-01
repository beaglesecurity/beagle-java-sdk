package com.beaglesecurity.api.results;

import java.util.List;

import com.beaglesecurity.entities.Project;

public class ProjectsResult {
	private List<Project> projects;
	private String code;
	private String message;
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
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
