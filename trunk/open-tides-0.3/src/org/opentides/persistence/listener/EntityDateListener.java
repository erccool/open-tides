/**
 * 
 */
package org.opentides.persistence.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.opentides.bean.BaseEntity;


/**
 * This listener is listening in BaseEntity to ensure 
 * create date and update date are populated.
 * @author allantan
 * 
 */
public class EntityDateListener {

	@PrePersist
	public void setDates(BaseEntity entity) {
		// set dateCreated and dateUpdated fields
		Date now = new Date();
		if (entity.getCreateDate() == null) {
			entity.setCreateDate(now);
		}
		entity.setUpdateDate(now);
	}

	@PreUpdate
	public void updateDates(BaseEntity entity) {
		// set dateUpdated fields
		Date now = new Date();
		entity.setUpdateDate(now);
	}
}
