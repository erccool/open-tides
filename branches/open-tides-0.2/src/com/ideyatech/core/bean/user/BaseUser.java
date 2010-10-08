/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * BaseUser.java
 * Created on Feb 13, 2008, 9:54:37 PM
 */
package com.ideyatech.core.bean.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ideyatech.core.bean.BaseCriteria;
import com.ideyatech.core.bean.BaseEntity;

@Entity
@Table(name = "USER_PROFILE")
public class BaseUser extends BaseEntity implements BaseCriteria {

	private static final long serialVersionUID = 7634675501487373408L;

	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;
	
	@Column(name = "MIDDLENAME", nullable=true)
	private String middleName;

	@Column(name = "EMAIL", unique = true)
	private String emailAddress;

	/*
	 * Lob Annotation Specifies that a persistent property or field should be
	 * persisted as a large object to a database-supported large object type
	 */
	@Lob
	@Column(name = "IMAGE", columnDefinition = "LONGBLOB")
	private byte[] image;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	private UserCredential credential;

	@ManyToMany
	@JoinTable(name = "USER_GROUP", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "GROUP_ID") })
	private Set<UserGroup> groups;

	public void addGroup(UserGroup group) {
		if (group == null)
			throw new IllegalArgumentException("Null group.");
		if (groups != null)
			groups.remove(group);
		groups.add(group);
	}

	public void removeGroup(UserGroup group) {
		if (group == null)
			throw new IllegalArgumentException("Null group.");
		if (groups != null)
			groups.remove(group);
	}

	public BaseUser() {
		super();
		this.setCredential(new UserCredential());
		groups = new HashSet<UserGroup>();
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserCredential getCredential() {
		return credential;
	}

	public void setCredential(UserCredential userAccount) {
		this.credential = userAccount;
		userAccount.setUser(this);
	}

	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("firstName");
		props.add("lastName");
		props.add("emailAddress");
		return props;
	}

	/**
	 * @return the groups
	 */
	public Set<UserGroup> getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public void setGroups(Set<UserGroup> groups) {
		this.groups = groups;
	}

	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BaseUser other = (BaseUser) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		return true;
	}

}
