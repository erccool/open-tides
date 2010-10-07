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
import java.util.Date;

/**
 * Holder bean for event details
 * 
 * @author roselle
 * 
 */
public class EventDetail implements Serializable {

    /**
     * Auto-generated class UID.
     */
	private static final long serialVersionUID = 1903861504680507865L;

	/**
	 * Event id
	 */
	private String id;

	/**
	 * Event name
	 */
	private String eventName;

	/**
	 * Scheduled trigger
	 */
	private Date scheduleDate;

	/**
	 * Event time in string format.
	 */
	private String eventTime;

	/**
	 * 
	 */
	private String eventPerson;

	private String email;

	EventReminderTemplate eventTemplate;

	public EventReminderTemplate getEventTemplate() {
		return eventTemplate;
	}

	public void setEventTemplate(EventReminderTemplate eventTemplate) {
		this.eventTemplate = eventTemplate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventPerson() {
		return eventPerson;
	}

	public void setEventPerson(String eventPerson) {
		this.eventPerson = eventPerson;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * 
	 * @return String id to be used as trigger and job id
	 */
	public String getId() {
		return id;
	}

	/**
	 * set event id to be used as trigger and job id
	 * @param id to be used as trigger and job id
	 */
	public void setId(String id) {
		this.id = id;
	}
}
