package com.beaglesecurity.execptions;

public class GeneralAPIException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public GeneralAPIException(String message) {
		super(message);
	}

}
