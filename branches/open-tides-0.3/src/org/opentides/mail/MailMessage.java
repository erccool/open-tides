package org.opentides.mail;

import java.io.Serializable;
import java.util.Map;

/**
 * Class for setting and getting the mail message details
 * 
 * @author ninodgalvan
 * 
 */
public class MailMessage implements Serializable {
	private static final long serialVersionUID = -1216499456656774151L;
	private String msgTo;
	private String msgFrom;
	private String subject;
	private String template;
	private Map<String, Object> dataMap;

	public MailMessage() {
	}

	public MailMessage(String msgFrom, String msgTo, String subject,
			String template, Map<String, Object> dataMap) {
		this.msgFrom = msgFrom;
		this.msgTo = msgTo;
		this.subject = subject;
		this.template = template;
		this.dataMap = dataMap;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * @return the dataMap
	 */
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap
	 *            the dataMap to set
	 */
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @return the msgTo
	 */
	public String getMsgTo() {
		return msgTo;
	}

	/**
	 * @param msgTo
	 *            the msgTo to set
	 */
	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}

	/**
	 * @return the msgFrom
	 */
	public String getMsgFrom() {
		return msgFrom;
	}

	/**
	 * @param msgFrom
	 *            the msgFrom to set
	 */
	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
