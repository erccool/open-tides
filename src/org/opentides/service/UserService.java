/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * UserServiceImpl.java
 * Created on Feb 22, 2008, 12:29:17 AM
 */
package org.opentides.service;

import java.util.List;
import java.util.Map;

import org.acegisecurity.concurrent.SessionInformation;
import org.acegisecurity.event.authentication.AuthenticationSuccessEvent;
import org.opentides.bean.PasswordReset;
import org.opentides.bean.user.BaseUser;



/**
 * @author allanctan
 *
 */
public interface UserService extends BaseCrudService<BaseUser> {
	public Map<String,String>  getRoles();
	/**
	 * Inject roles allowed for the application here.
	 * @param roles
	 */
	public void setRoles(Map<String,String>  roles);
	/**
	 * Requests for password reset 
	 * @param emailAddress
	 */
	public void requestPasswordReset(String emailAddress);
	/**
	 * Confirms the password by validating the email address and token
	 * @param emailAddress
	 * @param token
	 * @return
	 */
	public boolean confirmPasswordReset(String emailAddress, String token);
	/**
	 * Confirms the password by validating the cipher
	 * @param passwd
	 * @return
	 */
	public boolean confirmPasswordResetByCipher(PasswordReset passwd);
	/**
	 * Resets the password
	 * @param passwd
	 * @return
	 */
	public boolean resetPassword(PasswordReset passwd);
	/**
	 * Ensures that admin user is created into the database.
	 * This method is called by ApplicationStartupListener
	 * to ensure admin user is available
	 */
	 public boolean setupAdminUser();
	 /**
	  * Updates last login of the user from a login event
	  */
	 public void updateLastLogin(AuthenticationSuccessEvent authenticationSuccessEvent);
	 /**
	  * Returns the list of user session that is logged-in to the system.
	  * @return
	  */
	 public List<SessionInformation> getAllLoggedUsers();
	 /**
	  * Performs a force logout to specified username
	  * @return
	  */
	 public void forceLogout(String username);
}