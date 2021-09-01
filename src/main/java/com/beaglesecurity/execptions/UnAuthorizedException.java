package com.beaglesecurity.execptions;

public class UnAuthorizedException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public UnAuthorizedException(String message) {
		super(message);
	}

}
