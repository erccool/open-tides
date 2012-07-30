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

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSender;


/**
 * @author allan
 *
 */

public class MailingContext implements InitializingBean {
	
	private static JavaMailSender mailSender;
	private static VelocityEngine velocityEngine;
	/**
	 * @return the mailSender
	 */
	public static JavaMailSender getMailSender() {
		return MailingContext.mailSender;
	}

	/**
	 * @param mailSender the mailSender to set
	 */
	
	public void setMailSender(JavaMailSender mailSender) {
		MailingContext.mailSender = mailSender;
	}

	/**
	 * @return the velocityEngine
	 */
	public static VelocityEngine getVelocityEngine() {
		return MailingContext.velocityEngine;
	}

	/**
	 * @param velocityEngine the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {		
		MailingContext.velocityEngine = velocityEngine;
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
