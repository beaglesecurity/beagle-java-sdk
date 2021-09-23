/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.client;

public final class BeagleSecurityClientBuilder {
	
	private BeagleSecurityClientBuilder() {
	}
	
	private String token;
	
	public static BeagleSecurityClientBuilder instance() {
		return new BeagleSecurityClientBuilder();
	}
	
	public BeagleSecurityClientBuilder withAPIToken(String token) {
		this.token = token;
		return this;
	}
	
	public BeagleSecurityClient build() {
		BeagleSecurityClient securityClient = new BeagleSecurityClientImpl(this.token);
		return securityClient;
	}
}
