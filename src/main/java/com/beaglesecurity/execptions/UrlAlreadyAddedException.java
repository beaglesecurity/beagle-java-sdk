package com.beaglesecurity.execptions;

public class UrlAlreadyAddedException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public UrlAlreadyAddedException(String message) {
		super(message);
	}

}
