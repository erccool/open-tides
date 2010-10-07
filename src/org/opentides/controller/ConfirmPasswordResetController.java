/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */

package org.opentides.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentides.bean.PasswordReset;
import org.opentides.service.UserService;
import org.opentides.util.StringUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


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
	@SuppressWarnings("unchecked")
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
