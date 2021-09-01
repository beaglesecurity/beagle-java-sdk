package com.beaglesecurity.execptions;

public class PlanNotSupportException extends RuntimeException {
	private static final long serialVersionUID = -6867131497392658614L;

	public PlanNotSupportException(String message) {
		super(message);
	}

}
