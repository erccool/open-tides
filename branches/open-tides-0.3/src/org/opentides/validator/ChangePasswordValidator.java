
/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * SystemCodesValidator.java
 * Created on Jan 6, 2008, 4:34:32 PM
 */

package org.opentides.validator;

import org.opentides.bean.PasswordReset;
import org.opentides.persistence.UserDAO;
import org.opentides.util.StringUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


/**
 * @author allanctan
 *
 */
public class ChangePasswordValidator implements Validator {
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
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "error.required", new Object[]{"Password"});
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "confirmPassword", "error.required", new Object[]{"Confirm Password"});
		if (!StringUtil.isEmpty(obj.getPassword()) && (obj.getPassword().length()<6)) {
			e.reject("err.your-password-should-be-at-least-6-characters-long","Your password should be at least 6 characters long.");
		}
		if (!StringUtil.isEmpty(obj.getPassword()) && !StringUtil.isEmpty(obj.getConfirmPassword()) &&
				!obj.getPassword().equals(obj.getConfirmPassword()) ) {				
			e.reject("err.your-password-confirmation-did-not-match-with-password",
					"Your password confirmation did not match with password.");
		}
	}

	/**
	 * @param userService the userService to set
	 */
	public void setCoreUserDAO(UserDAO coreUserDAO) {
		this.coreUserDAO = coreUserDAO;
	}

}
