/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * UserCredential.java
 * Created on Feb 13, 2008, 9:54:37 PM
 */
package com.ideyatech.core.bean.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ideyatech.core.bean.BaseEntity;

@Entity
@Table(name="USERS")
public class UserCredential extends BaseEntity{
	private static final long serialVersionUID = -8078097647300665926L;
	
	@Column(name = "USERNAME", unique=true)
	private String username;
	
	@Column(name = "PASSWORD", nullable=false)
	private String password;
	
	private transient String confirmPassword;
	
	@Column(name="ENABLED")
	private Boolean enabled;
	
	@OneToOne
    @JoinColumn(name="USERID", nullable=false)
	private BaseUser user;
	
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
	public UserCredential() {
		enabled=false;
	}
	public Boolean isEnabled() {
		return enabled;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
			result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	/* (non-Javadoc)
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
		final UserCredential other = (UserCredential) obj;
		if (enabled != other.enabled)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}	
}
