package com.beaglesecurity.api.results;

import com.beaglesecurity.entities.Application;

public class ApplicationResult {
	private String code;
	private String message;
	private Application application;
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
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
}
