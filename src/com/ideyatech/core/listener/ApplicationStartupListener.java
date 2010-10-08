/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * ApplicationStartupListener.java
 * Created on March 1, 2008, 2:26:14 PM
 */
package com.ideyatech.core.listener;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ideyatech.core.service.UserService;
import com.ideyatech.core.util.AcegiUtil;
/**
 * This class is configured to be executed during application startup.
 * Checks to ensure admin user is created in case of new installation.
 * Also creates application variables.
 *
 * @author allanctan
 *
 */

// TODO: Can't get this to work!!
public class ApplicationStartupListener implements ApplicationListener {
	
	private static Logger _log = Logger.getLogger(ApplicationStartupListener.class);
	
	private String context;
	
	private Boolean debug=false;
	
	private Map<String,String> applicationVariables;
	
	private UserService userService;

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ApplicationEvent event) {
//		if (!StringUtil.isEmpty(context) && !applicationVariables.isEmpty()) {
//			for (String key:applicationVariables.keySet()) {
//				event.getServletContext().getServletContextName();
//				event.getServletContext().getContext(context).setAttribute(key, applicationVariables.entrySet());
//			}		
//		}
//		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(event.
//				getServletContext());
//		UserService userService = (UserService) ac.getBean("userService");
//		userService.setupAdminUser();
		_log.info("Initializing debug mode to "+debug);
		AcegiUtil.setDebug(debug);
	}
	
	/**
	 * @param applicationVariables the applicationVariables to set
	 */
	public void setApplicationVariables(Map<String, String> applicationVariables) {
		this.applicationVariables = applicationVariables;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			contextInitialized(event);
		}		
	}
}
