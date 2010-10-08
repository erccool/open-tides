/*
 * This source code is property of Ideyatech,Inc.
 * All rights reserved. 
 * 
 * EmptyQueryURLException.java
 * Created on November 2, 2007, 9:26 PM
 */

package com.ideyatech.core;

/**
 * This exception is called when unexpected return value is received from a method.
 * This is usually due to logical error in program implementation.
 * @author allanctan
 */
public class InvalidImplementationException extends RuntimeException {

	private static final long serialVersionUID = 4730256980857234206L;

	private String className;
	private String methodName;
	private Object response;
	private String validation;

	/**
	 * Exception thrown when a method returned an unexpected value, usually due to logical
	 * error in program implementation.
	 * @param className - name of class called.
	 * @param methodName - method of class that returned error.
	 * @param response - String value of returned data.
	 * @param validation - validation message. (e.g. cannot be empty)
	 */
	public InvalidImplementationException(String className, String methodName, Object response,
			String validation) {		
		super();
		this.className = className;
		this.methodName = methodName;
		if (response==null)
			this.response = "null";
		else
			this.response   = response;
		this.validation = validation;
	}

	/**
	 * Exception thrown when a method returned an unexpected value, usually due to logical
	 * error in program implementation.
	 * @param className - name of class called.
	 * @param methodName - method of class that returned error.
	 * @param response - value of returned data.
	 * @param validation - validation message. (e.g. cannot be empty)
	 * @param ex - root cause of exception
	 */
	public InvalidImplementationException(String className, String methodName, Object response,
			String validation,  Throwable ex) {
		super (ex);
		this.className  = className;
		this.methodName = methodName;
		if (response==null)
			this.response = "null";
		else
			this.response   = response;
		this.validation = validation;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return className + "." + methodName + " returned invalid response [" +
		response.toString() +"]." + validation;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return the response
	 */
	public Object getResponse() {
		return response;
	}

	/**
	 * @return the validation
	 */
	public String getValidation() {
		return validation;
	}
	
}
