/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * ApplicationStartupListener.java
 * Created on March 1, 2008, 2:26:14 PM
 */
package org.opentides.listener;

import org.apache.log4j.Logger;
import org.opentides.persistence.evolve.DBEvolveManager;
import org.opentides.util.AcegiUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * This class is configured to be executed during application startup.
 * Checks to ensure admin user is created in case of new installation.
 * Also creates application variables.
 *
 * @author allanctan
 */

public class ApplicationStartupListener implements ApplicationListener {
	
	private static Logger _log = Logger.getLogger(ApplicationStartupListener.class);
		
	private static boolean applicationStarted = false;
	
	private Boolean debug=false;
	
	private String propertyName;
	
	private DBEvolveManager evolveManager;

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ApplicationEvent event) {

		_log.info("Starting up system using " + propertyName + " properties.");
		_log.info("Checking for schema evolve...");
		evolveManager.evolve();

		_log.info("Initializing debug mode to "+debug);
		AcegiUtil.setDebug(debug);
	}
	
	/**
	 * @param debug the debug to set
	 */
	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	/**
	 * Triggered when context is started.
	 * For unknown reason, ContextStartedEvent is not triggered properly.
	 * So, we are using ContextRefreshedEvent with a static indicator.
	 */
	public void onApplicationEvent(ApplicationEvent event) {
		if (!applicationStarted && (event instanceof ContextRefreshedEvent)) {
			contextInitialized(event);
			applicationStarted = true;
		}		
	}

	/**
	 * @param evolveManager the evolveManager to set
	 */
	public final void setEvolveManager(DBEvolveManager evolveManager) {
		this.evolveManager = evolveManager;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public final void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
