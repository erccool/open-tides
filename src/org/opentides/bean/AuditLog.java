/**
 * 
 */
package org.opentides.bean;

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

import org.opentides.bean.user.BaseUser;
import org.opentides.persistence.listener.AuditLogListener;
import org.opentides.util.CrudUtil;


/**
 * This class is responsible for handling all audit functions needed to be
 * attached to the classes.
 * 
 * @author allantan
 */
@Entity
@EntityListeners( { AuditLogListener.class })
@Table(name = "HISTORY_LOG")
public class AuditLog extends BaseProtectedEntity implements Serializable,
		BaseCriteria {
	private static final long serialVersionUID = 269168041517643087L;
	@Column(name = "ENTITY_ID", nullable = false, updatable = false)
	private Long entityId;
	@SuppressWarnings("unchecked")
	@Column(name = "ENTITY_CLASS", nullable = false, updatable = false)
	private Class entityClass;
	@Column(name = "REFERENCE")
	private String reference;
	@Lob
	@Column(name = "MESSAGE", nullable = false, updatable = false)
	private String message;
	@Column(name = "USER_ID", nullable = false, updatable = false)
	private Long userId;
	@Transient
	private transient Object object;
	@Transient
	private transient BaseUser user;

	public AuditLog() {
	};

	@SuppressWarnings("unchecked")
	public AuditLog(String message, Long entityId, Class entityClass,
			Long userId) {
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
	 * @param entityId
	 *            the entityId to set
	 */
	public final void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the entityClass
	 */
	@SuppressWarnings("unchecked")
	public Class getEntityClass() {
		return entityClass;
	}

	/**
	 * @param entityClass
	 *            the entityClass to set
	 */
	@SuppressWarnings("unchecked")
	public final void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Returns a human readable name of the logged entity.
	 * 
	 * @return
	 */
	public String getEntityName() {
		return CrudUtil.getReadableName(entityClass.getName());
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
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
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
	 * @param user
	 *            the user to set
	 */
	public void setUser(BaseUser user) {
		this.user = user;
	}

	public List<String> getSearchProperties() {
		List<String> fields = new ArrayList<String>();
		fields.add("userId");
		fields.add("entityClass");
		fields.add("entityId");
		fields.add("reference");
		return fields;
	}

	/**
	 * @return the referenceId
	 */
	public final String getReference() {
		return reference;
	}

	/**
	 * @param referenceId
	 *            the referenceId to set
	 */
	public final void setReference(String reference) {
		this.reference = reference;
	}

}
