package org.opentides.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.acegisecurity.context.SecurityContextHolder;
import org.opentides.persistence.listener.EntityDateListener;
import org.opentides.util.AcegiUtil;

/**
 * 
 * This is the base class for all entity objects (model) of open-tides.
 * 
 * @author allantan
 */
@MappedSuperclass
@EntityListeners( { EntityDateListener.class })
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -2166505182954839082L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "CREATEDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@Column(name = "UPDATEDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	@Transient
	private Long userId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public boolean isNew() {
		return (this.id == null);
	}

	public boolean getIsNew() {
		return isNew();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		if (this.createDate == null)
			this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 
	 * @param userId
	 *            the userId to set
	 */

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * Sets the userId based on Acegi Context
	 */

	public void setUserId() {
		if (this.userId == null && SecurityContextHolder.getContext() != null
		&& SecurityContextHolder.getContext().getAuthentication() != null)
			this.userId = AcegiUtil.getSessionUser().getRealId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
