package com.ideyatech.core.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Holder bean for event details
 * 
 * @author MICROSOFT
 * 
 */
public class EventDetail implements Serializable {
	private String id;

	private String eventName;

	private Date scheduleDate;

	private String eventTime;

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
