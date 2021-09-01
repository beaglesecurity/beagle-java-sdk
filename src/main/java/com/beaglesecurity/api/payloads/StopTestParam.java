package com.beaglesecurity.api.payloads;

public class StopTestParam extends PayloadBase{
	private String applicationToken;

	public String getApplicationToken() {
		return applicationToken;
	}

	public void setApplicationToken(String applicationToken) {
		this.applicationToken = applicationToken;
	}
}
