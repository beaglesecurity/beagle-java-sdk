package com.beaglesecurity.execptions;

public class ForbiddenException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public ForbiddenException(String message) {
		super(message);
	}

}
