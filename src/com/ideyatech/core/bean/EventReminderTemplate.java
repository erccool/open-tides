package com.ideyatech.core.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 
 * @author
 * 
 */
@Entity
@Table(name = "EVENTREMINDER")
public class EventReminderTemplate extends BaseEntity implements BaseCriteria {

	@Column(name = "EVENT_NAME")
	private String name;

	@Column(name = "FREQUENCY")
	private String frequency;

	@Column(name = "TIME_ALLOWANCE")
	private int allowanceTime;

	@Column(name = "REMINDER_TIME") 
	private String reminderTime = "12:00 AM";

	@Column(name = "IMMEDIATE")
	private int immediate;

	@Column(name = "VM_FILE")
	private String velocityFile;

	public List<String> getSearchProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFrequency() {
		return frequency;
	}

	/**
	 * 
	 * @param frequency
	 *            frequency of reminder alert for event "daily" or ""
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * preferred allowance seconds from scheduled event time to run reminder
	 * 
	 * @return seconds
	 */
	public int getAllowanceTime() {
		return allowanceTime;
	}

	/**
	 * preferred allowance seconds from scheduled event time to run reminder
	 * 
	 * @param allowanceTime
	 *            seconds
	 */
	public void setAllowanceTime(int allowanceTime) {
		this.allowanceTime = allowanceTime;
	}

	public String getVelocityFile() {
		return velocityFile;
	}

	public void setVelocityFile(String velocityFile) {
		this.velocityFile = velocityFile;
	}

	/**
	 * tag to indicate if event reminder is immediate *
	 * 
	 * @return 0 for not immediate, 1 for immediate
	 */
	public int getImmediate() {
		return immediate;
	}

	/**
	 * tag to indicate if event reminder is immediate
	 * 
	 * @param immediate
	 *            0 for not immediate, 1 for immediate
	 */
	public void setImmediate(int immediate) {
		this.immediate = immediate;
	}

	/**
	 * specific time to run reminder format "h:mm a" eg. 12:08 PM (for multiple
	 * frequency reminder)
	 * 
	 * @return
	 */
	public String getReminderTime() {
		return reminderTime;
	}

	/**
	 * time to run reminder (for multiple frequency reminder eg. daily)
	 * 
	 * @param reminderTime
	 *            format "h:mm a" eg. 12:08 PM
	 */
	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}
}
