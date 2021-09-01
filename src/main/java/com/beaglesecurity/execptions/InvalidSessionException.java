package com.beaglesecurity.execptions;

public class InvalidSessionException extends RuntimeException {
	private static final long serialVersionUID = 1890382087686616814L;

	public InvalidSessionException(String message) {
		super(message);
	}

}
