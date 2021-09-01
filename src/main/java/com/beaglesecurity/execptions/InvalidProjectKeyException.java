package com.beaglesecurity.execptions;

public class InvalidProjectKeyException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public InvalidProjectKeyException(String message) {
		super(message);
	}

}
