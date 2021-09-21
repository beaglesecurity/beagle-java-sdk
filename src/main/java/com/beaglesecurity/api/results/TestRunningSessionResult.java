/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.api.results;

import java.util.List;

import com.beaglesecurity.entities.TestRunningSession;

public class TestRunningSessionResult {
	private List<TestRunningSession> sessions;
	private String code;
	private String message;
	public List<TestRunningSession> getSessions() {
		return sessions;
	}
	public void setSessions(List<TestRunningSession> sessions) {
		this.sessions = sessions;
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
}
