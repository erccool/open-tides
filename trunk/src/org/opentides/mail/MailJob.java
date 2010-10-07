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
package org.opentides.mail;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.opentides.bean.MailMessage;
import org.opentides.service.impl.MailingServiceImpl;
import org.opentides.util.StringUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class that implements the Job Class from quartz
 * It does the actual sending of mail job based on the 
 * specified startime defined in the mailScheduler method of
 * MailingServiceImpl class
 * @author ninodgalvan
 *
 */
public class MailJob extends QuartzJobBean {
	
	private static Logger _log = Logger.getLogger(MailingServiceImpl.class);
	
	//required no-arg constructor
	public MailJob() {
	}
	
	/**
	 * Implements the execute method from Job class
	 * @param context - quartz defined paramater. Contains the context of currently
	 * executing job
	 */
	@Override	
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		//creates a JobDataMap from the specified jobDetail of JobExecutionContext
		final JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		
		JavaMailSender mailSender = MailingContext.getMailSender();
		//creates the mail message preparator from JobDataMap set in MailingServiceImpl
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
          public void prepare(MimeMessage mimeMessage) throws Exception {
        	 VelocityEngine velocityEngine = MailingContext.getVelocityEngine();
             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
             MailMessage mailMessage = (MailMessage) dataMap.get("message");
             message.setFrom(mailMessage.getMsgFrom());
             message.setTo(mailMessage.getMsgTo());
             if (!StringUtil.isEmpty(mailMessage.getMsgBcc()))
            	 message.setBcc(mailMessage.getMsgBcc());
             if (!StringUtil.isEmpty(mailMessage.getMsgCc()))
             message.setCc(mailMessage.getMsgCc());
             message.setSubject(mailMessage.getSubject());
             String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, mailMessage.getTemplate(), mailMessage.getDataMap());
             message.setText(text, true);
             _log.info("Sending email to ["+mailMessage.getMsgTo()+"] with subject ["+mailMessage.getSubject()+"].");
          }
       };
       //send the mail message...
       mailSender.send(preparator);
	}
}
