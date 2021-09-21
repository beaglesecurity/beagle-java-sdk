/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.entities;

public class Signature {
	private SignatureStatus status;
	private String url;
    private FileSignature fileSignature;
    private DnsSignature dnsSignature;
    private APISignature apiSignature;
	public SignatureStatus getStatus() {
		return status;
	}
	public void setStatus(SignatureStatus status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public FileSignature getFileSignature() {
		return fileSignature;
	}
	public void setFileSignature(FileSignature fileSignature) {
		this.fileSignature = fileSignature;
	}
	public DnsSignature getDnsSignature() {
		return dnsSignature;
	}
	public void setDnsSignature(DnsSignature dnsSignature) {
		this.dnsSignature = dnsSignature;
	}
	public APISignature getApiSignature() {
		return apiSignature;
	}
	public void setApiSignature(APISignature apiSignature) {
		this.apiSignature = apiSignature;
	}
}
