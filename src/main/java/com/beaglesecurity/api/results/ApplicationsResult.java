package com.beaglesecurity.api.results;

import java.util.List;

import com.beaglesecurity.entities.Application;

public class ApplicationsResult {
	private String code;
	private String message;
	private List<Application> applications;
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
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
}
