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
package org.opentides.bean;

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
	private String msgCc;
	private String msgBcc;	
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
	 * @return the msgCc
	 */
	public String getMsgCc() {
		return msgCc;
	}

	/**
	 * @param msgCc the msgCc to set
	 */
	public void setMsgCc(String msgCc) {
		this.msgCc = msgCc;
	}

	/**
	 * @return the msgBcc
	 */
	public String getMsgBcc() {
		return msgBcc;
	}

	/**
	 * @param msgBcc the msgBcc to set
	 */
	public void setMsgBcc(String msgBcc) {
		this.msgBcc = msgBcc;
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
