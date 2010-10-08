package org.opentides.scheduler.job;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * All jobs must extend this class. Added ApplicationContext so that quartz can
 * be aware of the spring beans.
 * 
 * @author Jimenez
 * 
 */
public abstract class BaseJob extends QuartzJobBean {
	private transient ApplicationContext applicationContext = null;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
