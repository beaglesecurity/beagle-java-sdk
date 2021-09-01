package com.beaglesecurity.execptions;

public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public InternalServerErrorException(String message) {
		super(message);
	}

}
