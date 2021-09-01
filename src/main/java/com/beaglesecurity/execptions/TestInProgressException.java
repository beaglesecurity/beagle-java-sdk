package com.beaglesecurity.execptions;

public class TestInProgressException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public TestInProgressException(String message) {
		super(message);
	}

}
