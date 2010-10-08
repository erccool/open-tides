package org.opentides.mail;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.opentides.scheduler.job.BaseJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class that implements the Job Class from quartz
 * It does the actual sending of mail job based on the 
 * specified startime defined in the mailScheduler method of
 * MailingServiceImpl class
 * @author ninodgalvan
 *
 */
public class MailJob extends BaseJob{
	
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
		//creates the mail message preparator from JobDataMap set in MailingServiceImpl
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
          public void prepare(MimeMessage mimeMessage) throws Exception {
             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
             MailMessage mailMessage = (MailMessage) dataMap.get("message");
             message.setFrom(mailMessage.getMsgFrom());
             message.setTo(mailMessage.getMsgTo());
             message.setSubject(mailMessage.getSubject());
             //retrieve velocity mail engine
             VelocityEngine velocityEngine = (VelocityEngine) getApplicationContext().getBean("velocityEngine");
             String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, mailMessage.getTemplate(), mailMessage.getDataMap());
             message.setText(text, true);
          }
       };
       //retrieve mail sender from spring
       JavaMailSender mailSender = (JavaMailSender) getApplicationContext().getBean("mailSender");		
       //send the mail message...
       mailSender.send(preparator);
	}
}
