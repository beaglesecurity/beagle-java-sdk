/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.api.results;

import java.util.UUID;

import com.beaglesecurity.entities.ApplicationType;
import com.beaglesecurity.entities.SignatureStatus;

public class CreateApplicationResult {
	
	private String name;

	private String url;
	
	private UUID projectKey;
	
	private String applicationToken;
	
	private SignatureStatus signatureStatus;
	
	private ApplicationType applicationType;
	
	private String hostingType;
	
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

	public SignatureStatus getSignatureStatus() {
		return signatureStatus;
	}

	public void setSignatureStatus(SignatureStatus signatureStatus) {
		this.signatureStatus = signatureStatus;
	}

	public ApplicationType getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(ApplicationType applicationType) {
		this.applicationType = applicationType;
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

	public String getHostingType() {
		return hostingType;
	}

	public void setHostingType(String hostingType) {
		this.hostingType = hostingType;
	}
}
