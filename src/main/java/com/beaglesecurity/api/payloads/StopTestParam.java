/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

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
