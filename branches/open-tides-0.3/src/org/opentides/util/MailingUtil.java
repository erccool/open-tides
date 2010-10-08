/**
 * 
 */
package org.opentides.util;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSender;


/**
 * @author allan
 *
 */

public class MailingUtil implements InitializingBean {
	
	private static JavaMailSender mailSender;
	private static VelocityEngine velocityEngine;
	/**
	 * @return the mailSender
	 */
	public JavaMailSender getMailSender() {
		return MailingUtil.mailSender;
	}

	/**
	 * @param mailSender the mailSender to set
	 */
	
	public void setMailSender(JavaMailSender mailSender) {
		MailingUtil.mailSender = mailSender;
	}

	/**
	 * @return the velocityEngine
	 */
	public VelocityEngine getVelocityEngine() {
		return MailingUtil.velocityEngine;
	}

	/**
	 * @param velocityEngine the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {		
		MailingUtil.velocityEngine = velocityEngine;
	}
	
	
	public void afterPropertiesSet() throws Exception {
		// check if mailSender is injected
		if (mailSender==null) {
			throw new IllegalArgumentException("JavaMailSender is required for MailingUtil. " +
					"Please specify a JavaMailSender to the utility class.");
		}
		// check if velocityEngine is injected
		if (velocityEngine==null) {
			throw new IllegalArgumentException("VelocityEngine is required for MailingUtil. " +
					"Please specify a VelocityEngine to the utility class.");
		}
	}

	
	
}
