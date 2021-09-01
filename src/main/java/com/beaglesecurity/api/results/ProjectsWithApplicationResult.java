package com.beaglesecurity.api.results;

import java.util.List;

import com.beaglesecurity.entities.ProjectWithApplication;

public class ProjectsWithApplicationResult {
	private List<ProjectWithApplication> projects;
	private String code;
	private String message;
	public List<ProjectWithApplication> getProjects() {
		return projects;
	}
	public void setProjects(List<ProjectWithApplication> projects) {
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
