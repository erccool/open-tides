package com.ideyatech.core.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.InitializingBean;

import com.ideyatech.core.mail.MailJob;
import com.ideyatech.core.mail.MailMessage;
import com.ideyatech.core.service.MailingService;
import com.ideyatech.core.util.StringUtil;

/**
 * Class the implements MailingService
 * @author ninodgalvan
 *
 */
public class MailingServiceImpl implements MailingService, InitializingBean{
		
	private static Logger _log = Logger.getLogger(MailingServiceImpl.class);
	private SchedulerFactory schedulerFactory;
	private Scheduler scheduler;
	private static long counter = 0;
	
	public void sendMailImmediate(MailMessage mailMessage) {
		mailScheduler(mailMessage, new Date());	
	}
	
	public void sendMailByTime(MailMessage mailMessage, Date startTime) {
		mailScheduler(mailMessage, startTime);
	}
	
	/**
	 * private helper method for setting the mail scheduler
	 * @param mailMessage the mail message details
	 * @param startTime - specifies the time when to start the job if time equals new Date() means start immediately
	 */
	private void  mailScheduler(MailMessage mailMessage,Date startTime) {
		
		//must define a unique job group name
		String jobGroup = "jobGroup" + StringUtil.generateRandomString(6) + counter++;
		String jobName = "mailJob";
		String triggerName = "mailTrigger";
		_log.info(jobGroup);
		/*
		 * Create a job with a job name as "mailJob", a randomize job group name
		 * and use the MailJob.class that will execute the job
		 */
		JobDetail jobDetail = new JobDetail(jobName,jobGroup,MailJob.class);
		
		//set a JobDataMap to be used in sending the mail
		jobDetail.getJobDataMap().put("message", mailMessage);
		
		/*
		 * creates a simple trigger with a name "mailTrigger", a randomize job group name, and
		 * a startTime based on the parameter passed, with null endtime, and
		 * repeatcount and repeatInterval set to zero(0) which means this trigger will
		 * fires only once. 
		 */
		
		SimpleTrigger mailTrigger = new SimpleTrigger(triggerName,jobGroup,startTime,null, 0,0L);
		try {
			//schedule the job with the trigger
			scheduler.scheduleJob(jobDetail, mailTrigger);
		} catch (SchedulerException e) {
			_log.error("Failed to schedule job.", e);
		}
	}

	/**
	 * Check if factory is injected via Spring and creates an instance of the scheduler.
	 */
	public void afterPropertiesSet() throws Exception {
		// check if schedulerFactory is injected
		if (schedulerFactory==null) {
			throw new IllegalArgumentException("SchedulerFactory is required for MailingService. " +
					"Please specify a SchedulerFactory to the service.");
		}
		
		//instantiates and start the quartz scheduler	
		try {
			scheduler = schedulerFactory.getScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			_log.error("Failed to initialize scheduler.", e);
			throw e;
		}
	}

	/**
	 * @param schedulerFactory the schedulerFactory to set
	 */
	public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
		this.schedulerFactory = schedulerFactory;
	}

}
