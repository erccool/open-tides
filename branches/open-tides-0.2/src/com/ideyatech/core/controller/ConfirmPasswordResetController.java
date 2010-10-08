/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * ConfirmPasswordResetController.java
 * Created on Feb 25, 2008, 10:50:52 AM
 */
package com.ideyatech.core.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.ideyatech.core.bean.PasswordReset;
import com.ideyatech.core.service.UserService;
import com.ideyatech.core.util.StringUtil;

/**
 * @author allanctan
 *
 */
public class ConfirmPasswordResetController extends SimpleFormController {
	
	private UserService userService;
	public static final String SECURE_SESSION_KEY = "confirmedPasswordReset";
	public static final String SECURE_SESSION_CODE = "aYotNaP0!";
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map model = new HashMap<String,String>();
		model.put("action", "confirm");
		return model;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#isFormSubmission(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		return super.isFormSubmission(request) ||
				!StringUtil.isEmpty(request.getParameter("cipher"));
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		boolean success = false;
		PasswordReset passwd = (PasswordReset) command;
		if (StringUtil.isEmpty(passwd.getCipher())) {
			success = userService.confirmPasswordReset(passwd.getEmailAddress(), passwd.getToken());	
			if (!success)
				errors.reject("error.unauthorized-password-reset-by-email-token", "Invalid email and confirmation code combination.");
		} else {
			success = userService.confirmPasswordResetByCipher(passwd);			
			if (!success)
				errors.reject("error.unauthorized-password-reset-by-cipher", "Invalid link or password request has expired. " +
						"You can retry using the form below or request for new password reset.");
		}
		if (success) {
			Map<String,Object> model = new HashMap<String,Object>();
			model.put(getCommandName(), passwd);
			request.getSession().setAttribute(SECURE_SESSION_KEY, SECURE_SESSION_CODE+passwd.getEmailAddress());
			// next page is to change password
			model.put("action", "change");
			return new ModelAndView(getSuccessView(),model);
		} else {
			return showForm(request, errors, getFormView());
		}
	}
	
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
