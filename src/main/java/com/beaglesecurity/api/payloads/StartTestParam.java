package com.beaglesecurity.api.payloads;

public class StartTestParam extends PayloadBase{
	private String applicationToken;

	public String getApplicationToken() {
		return applicationToken;
	}

	public void setApplicationToken(String applicationToken) {
		this.applicationToken = applicationToken;
	}
}
