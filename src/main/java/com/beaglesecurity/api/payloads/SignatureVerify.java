package com.beaglesecurity.api.payloads;

import com.beaglesecurity.entities.PluginType;
import com.beaglesecurity.entities.SignatureType;

public class SignatureVerify extends PayloadBase{
	private String applicationToken;	
	private SignatureType signatureType;
	private PluginType pluginType;
	public String getApplicationToken() {
		return applicationToken;
	}
	public void setApplicationToken(String applicationToken) {
		this.applicationToken = applicationToken;
	}
	public SignatureType getSignatureType() {
		return signatureType;
	}
	public void setSignatureType(SignatureType signatureType) {
		this.signatureType = signatureType;
	}
	public PluginType getPluginType() {
		return pluginType;
	}
	public void setPluginType(PluginType pluginType) {
		this.pluginType = pluginType;
	}
}
