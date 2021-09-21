/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.api.payloads;

public class ModifyApplication extends PayloadBase {
	private String applicationToken;
	private String name;
	private String url;
	public String getApplicationToken() {
		return applicationToken;
	}
	public void setApplicationToken(String applicationToken) {
		this.applicationToken = applicationToken;
	}
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
}
