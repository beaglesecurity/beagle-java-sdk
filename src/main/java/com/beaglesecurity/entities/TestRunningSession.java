package com.beaglesecurity.entities;

import java.util.UUID;

public class TestRunningSession {
	private String title;
    private String url;
    private String applicationToken;
	private String resultToken;
	private Long startTime;
	private UUID sessionKey;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getResultToken() {
		return resultToken;
	}
	public void setResultToken(String resultToken) {
		this.resultToken = resultToken;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public UUID getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(UUID sessionKey) {
		this.sessionKey = sessionKey;
	}
}
