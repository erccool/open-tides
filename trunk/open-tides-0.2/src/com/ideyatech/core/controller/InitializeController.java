/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * InitializeController.java
 * Created on Mar 1, 2008, 10:43:23 PM
 */
package com.ideyatech.core.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.ideyatech.core.service.UserService;

/**
 * @author allanctan
 *
 */
public class InitializeController extends AbstractController {

	private UserService userService;
	private String messageView;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		Map<String,String> model = new HashMap<String,String>();
		if (userService.setupAdminUser()) {
			model.put("message", "msg.database-successfully-initialized");
			model.put("title", "label.initializer");		
		} else {
			model.put("message", "msg.database-already-being-used");
			model.put("title", "label.initializer");	
		}
		return new ModelAndView(messageView, model);	
	}
	
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * @param messageView the messageView to set
	 */
	public void setMessageView(String messageView) {
		this.messageView = messageView;
	}

}
