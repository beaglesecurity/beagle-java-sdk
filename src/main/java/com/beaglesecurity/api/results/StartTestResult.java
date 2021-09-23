/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.api.results;

public class StartTestResult {
	private String code;
	private String status_url;
	private String result_url;
	private String result_token;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus_url() {
		return status_url;
	}
	public void setStatus_url(String status_url) {
		this.status_url = status_url;
	}
	public String getResult_url() {
		return result_url;
	}
	public void setResult_url(String result_url) {
		this.result_url = result_url;
	}
	public String getResult_token() {
		return result_token;
	}
	public void setResult_token(String result_token) {
		this.result_token = result_token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
