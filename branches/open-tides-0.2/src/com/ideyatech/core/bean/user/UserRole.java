/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * UserRole.java
 * Created on Feb 13, 2008, 9:54:37 PM
 */
package com.ideyatech.core.bean.user;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ideyatech.core.bean.BaseEntity;

@Entity
@AttributeOverride(name = "id", column = @Column(insertable = false, updatable = false))
@Table(name = "AUTHORITIES")
public class UserRole extends BaseEntity {
	private static final long serialVersionUID = -2779918759002560767L;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "AUTHORITY")
	private String role;

	@ManyToOne(optional = false)
	@JoinColumn(name = "USERGROUP_ID", nullable = false)
	private UserGroup userGroup;

	public UserRole() {
		super();
	}

	public UserRole(UserGroup userGroup, String role) {
		Date now = new Date();
		this.setUserGroup(userGroup);
		this.setRole(role);
		this.setCreateDate(now);
		this.setUpdateDate(now);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userGroup
	 */
	public UserGroup getUserGroup() {
		return userGroup;
	}

	/**
	 * @param userGroup
	 *            the userGroup to set
	 */
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((role == null) ? 0 : role.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserRole other = (UserRole) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
}
