/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.client.helper;

public class HttpReturn {
	private int code;
	private String resultJson;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getResultJson() {
		return resultJson;
	}
	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}
}
