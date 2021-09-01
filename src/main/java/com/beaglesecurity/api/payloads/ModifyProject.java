package com.beaglesecurity.api.payloads;

import java.util.UUID;

public class ModifyProject extends PayloadBase{
	private UUID projectKey;
	private String name;
	private String description;
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
}
