/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * HTTPConnectionException.java
 * Created on Feb 8, 2008, 10:30:57 PM
 */
package org.opentides;

/**
 * This exception is thrown whenever connection request
 * via http web service fails.
 * @author allanctan
 *
 */
public class HTTPConnectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public HTTPConnectionException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HTTPConnectionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public HTTPConnectionException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public HTTPConnectionException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
