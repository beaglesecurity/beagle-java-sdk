package com.beaglesecurity.api.payloads;

import java.util.UUID;

import com.beaglesecurity.entities.ApplicationType;

public class CreateApplicationResult {
	private String name;

	private String url;
	
	private UUID projectKey;
	
	private String applicationToken;
	
	private Boolean signatureVerified;
	
	private ApplicationType type;
	
	private String code;
	
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UUID getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(UUID projectKey) {
		this.projectKey = projectKey;
	}

	public String getApplicationToken() {
		return applicationToken;
	}

	public void setApplicationToken(String applicationToken) {
		this.applicationToken = applicationToken;
	}

	public Boolean getSignatureVerified() {
		return signatureVerified;
	}

	public void setSignatureVerified(Boolean signatureVerified) {
		this.signatureVerified = signatureVerified;
	}

	public ApplicationType getType() {
		return type;
	}

	public void setType(ApplicationType type) {
		this.type = type;
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