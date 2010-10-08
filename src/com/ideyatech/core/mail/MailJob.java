package com.ideyatech.core.mail;

import javax.mail.internet.MimeMessage;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import com.ideyatech.core.util.MailingUtil;

/**
 * Class that implements the Job Class from quartz
 * It does the actual sending of mail job based on the 
 * specified startime defined in the mailScheduler method of
 * MailingServiceImpl class
 * @author ninodgalvan
 *
 */
public class MailJob implements Job{
	
	//required no-arg constructor
	public MailJob() {
	}
	/**
	 * Implements the execute method from Job class
	 * @param context - quartz defined paramater. Contains the context of currently
	 * executing job
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//creates a JobDataMap from the specified jobDetail of JobExecutionContext
		final JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		//creates the mail message preparator from JobDataMap set in MailingServiceImpl
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
          public void prepare(MimeMessage mimeMessage) throws Exception {
             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
             MailMessage mailMessage = (MailMessage) dataMap.get("message");
             message.setFrom(mailMessage.getMsgFrom());
             message.setTo(mailMessage.getMsgTo());
             message.setSubject(mailMessage.getSubject());
             //creates a new instance of mailing util to get the VelocityEngine object
             MailingUtil mailingUtil = new MailingUtil();
             String text = VelocityEngineUtils.mergeTemplateIntoString(
            
            mailingUtil.getVelocityEngine(), mailMessage.getTemplate(), mailMessage.getDataMap());
            message.setText(text, true);
          }
       };
       //creates a new instance of mailing util to get the JavaMailSender object
       MailingUtil mailingUtil = new MailingUtil();
       //get the JavaMailSender instance from MailingUtil
		JavaMailSender mailSender = mailingUtil.getMailSender();		
		//send the mail message...
		mailSender.send(preparator);
	}
}
