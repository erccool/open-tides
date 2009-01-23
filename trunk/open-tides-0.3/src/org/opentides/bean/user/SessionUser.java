/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * SessionUser.java
 * Created on Feb 13, 2008, 9:54:37 PM
 */
package org.opentides.bean.user;

import java.util.Date;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;

/**
 * This class is used by ACEGI to represent the currently logged
 * user for the session. To retrieve the SessionUser object
 * use AcegiUtil.getSessionUser();
 * 
 * @author allantan
 *
 */
public class SessionUser extends User {

	private static final long serialVersionUID = 8493532913557193485L;
	
	private Long   id;
	private String firstName;
	private String lastName;
	private String position;
	private String company;
	private String emailAddress;
	private String pictureUrl;
	private Date lastLogin;

	public SessionUser(String username, String password, boolean isEnabled,
			GrantedAuthority[] authorities) {
		super(username, password, isEnabled, true, true, true, authorities);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**
	 * @return the pictureUrl
	 */
	public String getPictureUrl() {
		return pictureUrl;
	}

	/**
	 * @param pictureUrl the pictureUrl to set
	 */
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Returns the real id of user. 
	 * Override this method in case of simulating/mimicking user access rights.
	 * @return
	 */
	public Long getRealId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
