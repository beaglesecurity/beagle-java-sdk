/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.execptions;

public class TestInProgressException extends RuntimeException {
	private static final long serialVersionUID = 5307864391631188609L;
	public TestInProgressException(String message) {
		super(message);
	}

}
