/**
 * 
 */
package com.ideyatech.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ideyatech.core.bean.user.BaseUser;
import com.ideyatech.core.persistence.listener.AuditLogListener;

/**
 * This class is responsible for handling all audit functions
 * needed to be attached to the classes.
 * 
 * @author allantan
 */
@Entity
@EntityListeners({AuditLogListener.class})
@Table(name="HISTORY_LOG")
public class AuditLog extends BaseEntity implements Serializable, BaseCriteria {

	@Column(name = "ENTITY_ID",nullable=false,updatable=false)
	private Long entityId;
	@Column(name = "ENTITY_CLASS",nullable=false,updatable=false)
	private Class entityClass;
	@Lob
	@Column(name = "MESSAGE",nullable=false,updatable=false)
	private String message;
	@Column(name = "USER_ID",nullable=false,updatable=false)
	private Long userId;
	@Transient
	private transient Object object;
	@Transient
	private transient BaseUser user;
	
	public AuditLog() {
	};
	
	public AuditLog(String message, Long entityId, Class entityClass, Long userId) {
		this.message = message; 
		this.entityId = entityId; 
		this.entityClass = entityClass; 
		this.userId = userId; 
		this.setCreateDate(new Date());
	}

	/**
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * @return the entityClass
	 */
	public Class getEntityClass() {
		return entityClass;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return the user
	 */
	public BaseUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(BaseUser user) {
		this.user = user;
	}

	public List<String> getSearchProperties() {
		List<String> fields = new ArrayList<String>();
		fields.add("userId");
		fields.add("entityClass");
		return fields;
	}
}
