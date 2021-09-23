/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.entities;

import java.util.UUID;

public class TestSession {
	private String resultToken;
	private Long startTime;
	private Long endTime;
	private UUID sessionKey;
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
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public UUID getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(UUID sessionKey) {
		this.sessionKey = sessionKey;
	}
}
