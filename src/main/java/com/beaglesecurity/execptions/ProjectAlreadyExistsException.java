package com.beaglesecurity.execptions;

public class ProjectAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public ProjectAlreadyExistsException(String message) {
		super(message);
	}

}
