/**
 * Copyright (c) Beagle Cyber Innovations Pvt. Ltd. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for
 * license information.
 */

package com.beaglesecurity.execptions;

public class InvalidPluginTypeException extends RuntimeException {
	private static final long serialVersionUID = 1890382087686616814L;

	public InvalidPluginTypeException(String message) {
		super(message);
	}

}
