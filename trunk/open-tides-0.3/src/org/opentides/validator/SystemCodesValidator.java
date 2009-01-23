/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * SystemCodesValidator.java
 * Created on Jan 6, 2008, 4:34:32 PM
 */
package org.opentides.validator;

import org.opentides.bean.SystemCodes;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * @author allanctan
 * 
 */
public class SystemCodesValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return SystemCodes.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object clazz, Errors e) {
		ValidationUtils.rejectIfEmpty(e, "category", "error.required",
				new Object[] { "Category" });
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "key", "error.required",
				new Object[] { "Key" });
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "value", "error.required",
				new Object[] { "Value" });
	}
}
