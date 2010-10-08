/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * PasswordReset.java
 * Created on Feb 23, 2008, 9:04:13 PM
 */
package com.ideyatech.core.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author allanctan
 *
 */
@Entity
@Table(name="PASSWORD_RESET")
public class PasswordReset extends BaseEntity implements BaseCriteria {

	private static final long serialVersionUID = 6437073263044026761L;
	
	public static final String STATUS_ACTIVE="active";
	public static final String STATUS_USED="used";
	public static final String STATUS_EXPIRED="expired";
	

	@Column(name="EMAIL")
	private String emailAddress;
	@Column(name="TOKEN")
	private String token;
	@Column(name="CIPHER")
	private String cipher;
	@Column(name="_STATUS")
	private String status;
	
	private transient String password;
	private transient String confirmPassword;
	
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the cipher
	 */
	public String getCipher() {
		return cipher;
	}
	/**
	 * @param cipher the cipher to set
	 */
	public void setCipher(String cipher) {
		this.cipher = cipher;
	}
	
	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("emailAddress");
		props.add("token");
		props.add("status");		
		return props;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
}
