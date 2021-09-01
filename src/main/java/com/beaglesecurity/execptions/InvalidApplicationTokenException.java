package com.beaglesecurity.execptions;

public class InvalidApplicationTokenException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public InvalidApplicationTokenException(String message) {
		super(message);
	}

}
