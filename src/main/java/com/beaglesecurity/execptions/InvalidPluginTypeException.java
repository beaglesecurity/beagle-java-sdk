package com.beaglesecurity.execptions;

public class InvalidPluginTypeException extends RuntimeException {
	private static final long serialVersionUID = 1890382087686616814L;

	public InvalidPluginTypeException(String message) {
		super(message);
	}

}
