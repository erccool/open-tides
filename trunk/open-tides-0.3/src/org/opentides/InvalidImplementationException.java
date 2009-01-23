/*
 * This source code is property of Ideyatech,Inc.
 * All rights reserved. 
 * 
 * InvalidImplementationException.java
 * Created on November 2, 2007, 9:26 PM
 */

package org.opentides;

/**
 * This exception is called when unexpected return value is received from a method.
 * Usually due to logical error in program implementation.
 * @author allanctan
 */
public class InvalidImplementationException extends RuntimeException {

	private static final long serialVersionUID = -5571782216124026964L;

	public InvalidImplementationException() {
		super();
	}

	public InvalidImplementationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidImplementationException(String message) {
		super(message);
	}

	public InvalidImplementationException(Throwable cause) {
		super(cause);
	}
	
}
