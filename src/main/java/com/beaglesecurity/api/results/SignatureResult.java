package com.beaglesecurity.api.results;

import com.beaglesecurity.entities.APISignature;
import com.beaglesecurity.entities.DnsSignature;
import com.beaglesecurity.entities.FileSignature;
import com.beaglesecurity.entities.SignatureStatus;

public class SignatureResult {
	private SignatureStatus status;
	private String url;
    private String code;
    private String message;
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
