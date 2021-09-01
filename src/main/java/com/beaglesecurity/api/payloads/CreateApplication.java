package com.beaglesecurity.api.payloads;

import java.util.UUID;

import com.beaglesecurity.entities.ApplicationType;

public class CreateApplication extends PayloadBase {
	private String name;
	private String url;
	private UUID projectKey;
	private ApplicationType type;
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
	public ApplicationType getType() {
		return type;
	}
	public void setType(ApplicationType type) {
		this.type = type;
	}
}
