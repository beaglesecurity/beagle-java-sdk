/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.entities;

import java.util.UUID;

public class Project {
	private String name;
	private String description;
	private UUID projectKey;
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
