/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.execptions;

public class PlanNotSupportException extends RuntimeException {
	private static final long serialVersionUID = -6867131497392658614L;

	public PlanNotSupportException(String message) {
		super(message);
	}

}
