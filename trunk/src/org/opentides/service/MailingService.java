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
package org.opentides.service;

import java.util.Date;

import org.opentides.bean.MailMessage;

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
