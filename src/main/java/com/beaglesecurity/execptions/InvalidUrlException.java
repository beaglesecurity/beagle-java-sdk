package com.beaglesecurity.execptions;

public class InvalidUrlException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public InvalidUrlException(String message) {
		super(message);
	}

}
