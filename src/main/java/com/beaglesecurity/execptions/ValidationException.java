package com.beaglesecurity.execptions;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public ValidationException(String message) {
		super(message);
	}

}
