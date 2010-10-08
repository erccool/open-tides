/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * RequestPasswordResetController.java
 * Created on Feb 25, 2008, 10:50:22 AM
 */
package org.opentides.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.opentides.bean.PasswordReset;
import org.opentides.service.UserService;
import org.opentides.service.impl.UserServiceImpl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


/**
 * @author allanctan
 *
 */
public class RequestPasswordResetController extends SimpleFormController {
	
	private static Logger _log = Logger.getLogger(UserServiceImpl.class);

	private UserService userService;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Map<String,String> referenceData(HttpServletRequest request) throws Exception {
		Map<String,String> model = new HashMap<String,String>();
		model.put("action", "request");
		return model;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
	 */
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		PasswordReset passwd = (PasswordReset) command;
		Map<String,String> model = new HashMap<String,String>();
		try{
			userService.requestPasswordReset(passwd.getEmailAddress());
			model.put("message", "msg.instructions-for-reset-sent");
			model.put("title", "label.forgot-password");
		} catch (Exception ce) {
			try {
				_log.error("Failed to send password request email.",ce);
			} catch (Exception e) {
				_log.error("Failed to send password request email.");
			}
			model.put("message", "msg.failed-to-send-email");
			model.put("title", "label.forgot-password");			
		}
		return new ModelAndView(getSuccessView(), model);
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
