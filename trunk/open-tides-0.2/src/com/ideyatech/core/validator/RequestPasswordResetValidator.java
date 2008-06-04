/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * SystemCodesValidator.java
 * Created on Jan 6, 2008, 4:34:32 PM
 */
package com.ideyatech.core.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ideyatech.core.bean.PasswordReset;
import com.ideyatech.core.persistence.UserDAO;
import com.ideyatech.core.util.StringUtil;

/**
 * @author allanctan
 *
 */
public class RequestPasswordResetValidator implements Validator {
	UserDAO coreUserDAO;

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return PasswordReset.class.isAssignableFrom(clazz);		
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object clazz, Errors e) {
		PasswordReset obj = (PasswordReset) clazz;
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "emailAddress", "error.required", new Object[]{"Email Address"});
		if (!StringUtil.isEmpty(obj.getEmailAddress()) && !coreUserDAO.isRegisteredByEmail(obj.getEmailAddress())) {
			e.rejectValue("emailAddress","msg.email-address-is-not-registered",
					"Sorry, but your email address is not registered.");
		}
	}

	/**
	 * @param userService the userService to set
	 */
	public void setCoreUserDAO(UserDAO coreUserDAO) {
		this.coreUserDAO = coreUserDAO;
	}

}
