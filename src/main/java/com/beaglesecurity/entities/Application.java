/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.entities;

public class Application {
	private String name;
	private String url;
	private String applicationToken;
	private String applicationType;
	private SignatureStatus signatureStatus;
	private String hostingType;
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
	public String getApplicationToken() {
		return applicationToken;
	}
	public void setApplicationToken(String applicationToken) {
		this.applicationToken = applicationToken;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public SignatureStatus getSignatureStatus() {
		return signatureStatus;
	}
	public void setSignatureStatus(SignatureStatus signatureStatus) {
		this.signatureStatus = signatureStatus;
	}
	public String getHostingType() {
		return hostingType;
	}
	public void setHostingType(String hostingType) {
		this.hostingType = hostingType;
	}
}
