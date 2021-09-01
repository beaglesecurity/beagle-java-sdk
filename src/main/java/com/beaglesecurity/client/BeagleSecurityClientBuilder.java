/*
 * Copyright 2021-2025 beaglesecurity.com. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * 
 * You may not use this file except in compliance with the License.
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * 
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