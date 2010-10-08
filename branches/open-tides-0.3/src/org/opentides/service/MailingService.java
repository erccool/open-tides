package org.opentides.service;

import java.util.Date;

import org.opentides.mail.MailMessage;

/**
 * Interface for sending mail using quartz. It is composed of two methods
 * for sending email immediately or sending email by specified time.
 * The only required parameter are MailMessage bean and date for sending
 * mail by time
 * @author ninodgalvan
 *
 */
public interface MailingService {
	/**
	 * Method for sending email immediately
	 * @param mailMessage - the mail message details
	 */
	public void sendMailImmediate(MailMessage mailMessage);

	/**
	 * Method for sending email with a specified startTime
	 * @param mailMessage - the mail message details
	 * @param startTime
	 */
	public void sendMailByTime(MailMessage mailMessage, Date startTime);
}
