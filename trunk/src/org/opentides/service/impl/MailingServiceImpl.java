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

package org.opentides.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.opentides.bean.MailMessage;
import org.opentides.mail.MailJob;
import org.opentides.service.MailingService;
import org.opentides.util.DateUtil;
import org.opentides.util.StringUtil;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.InitializingBean;

/**
 * Class the implements MailingService
 * 
 * @author ninodgalvan
 * 
 */
public class MailingServiceImpl implements MailingService, InitializingBean {

	private static Logger _log = Logger.getLogger(MailingServiceImpl.class);
//	private SchedulerFactory schedulerFactory;
	private StdScheduler scheduler;
	private static long counter = 0;
	private String bcc = null;

	public void sendMailImmediate(MailMessage mailMessage) {
		mailScheduler(mailMessage, new Date());
	}

	public void sendMailByTime(MailMessage mailMessage, Date startTime) {
		mailScheduler(mailMessage, startTime);
	}

	/**
	 * private helper method for setting the mail scheduler
	 * 
	 * @param mailMessage
	 *            the mail message details
	 * @param startTime -
	 *            specifies the time when to start the job if time equals new
	 *            Date() means start immediately
	 */
	private void mailScheduler(MailMessage mailMessage, Date startTime) {

		// must define a unique job group name
		String jobGroup = "jobGroup" + StringUtil.generateRandomString(6)
				+ counter++;
		String jobName = "mailJob";
		String triggerName = "mailTrigger";
		_log.debug("Scheduling mail send [" + jobGroup +"] on ["+DateUtil.dateToString(startTime,"MM/dd/yyyy hh:mm:ss")+"].");
		
		if (!StringUtil.isEmpty(bcc)) 
			mailMessage.setMsgBcc(bcc);
		
		/*
		 * Create a job with a job name as "mailJob", a randomize job group name
		 * and use the MailJob.class that will execute the job
		 */
		JobDetail jobDetail = new JobDetail(jobName, jobGroup, MailJob.class);
		
		// set a JobDataMap to be used in sending the mail
		jobDetail.getJobDataMap().put("message", mailMessage);

		/*
		 * creates a simple trigger with a name "mailTrigger", a randomize job
		 * group name, and a startTime based on the parameter passed, with null
		 * endtime, and repeatcount and repeatInterval set to zero(0) which
		 * means this trigger will fires only once.
		 */

		SimpleTrigger mailTrigger = new SimpleTrigger(triggerName, jobGroup,
				startTime, null, 0, 0L);
		try {
			// schedule the job with the trigger
			scheduler.scheduleJob(jobDetail, mailTrigger);
		} catch (SchedulerException e) {
			_log.error("Failed to schedule job.", e);
		}
	}

	/**
	 * Check if factory is injected via Spring and creates an instance of the
	 * scheduler.
	 */
	public void afterPropertiesSet() throws Exception {
		if(scheduler == null){			
			throw new IllegalArgumentException(
					"Scheduler is required for MailingService. "
							+ "Please specify a Scheduler to the service.");
		}
	}

	/**
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(StdScheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * @return the scheduler
	 */
	protected StdScheduler getScheduler() {
		return scheduler;
	}

	/**
	 * @return the bcc
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}	
	
}
