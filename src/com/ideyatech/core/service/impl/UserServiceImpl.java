/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * UserServiceImpl.java
 * Created on Feb 22, 2008, 12:31:18 AM
 */
package com.ideyatech.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;

import com.ideyatech.core.InvalidImplementationException;
import com.ideyatech.core.bean.PasswordReset;
import com.ideyatech.core.bean.user.BaseUser;
import com.ideyatech.core.bean.user.UserCredential;
import com.ideyatech.core.bean.user.UserGroup;
import com.ideyatech.core.mail.MailMessage;

import com.ideyatech.core.persistence.PasswordResetDAO;
import com.ideyatech.core.persistence.UserDAO;
import com.ideyatech.core.persistence.UserGroupDAO;
import com.ideyatech.core.service.UserService;
import com.ideyatech.core.util.StringUtil;

public class UserServiceImpl extends BaseCrudServiceImpl<BaseUser> implements
		UserService {

	private static Logger _log = Logger.getLogger(UserServiceImpl.class);

	private static Map<String, String> roles;

	private PasswordResetDAO passwordResetDAO;

	

	private JavaMailSender mailSender;

	private int tokenLength = 10;

	private String confirmURL;

	private UserGroupDAO userGroupDAO;

	private MailMessage mailMessage;
	/**
	 * @return the roles
	 */
	public Map<String, String> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Map<String, String> roles) {
		UserServiceImpl.roles = roles;
	}

	public void requestPasswordReset(String emailAddress) {
		UserDAO userDAO = (UserDAO) getDao();
		if (!userDAO.isRegisteredByEmail(emailAddress)) {
			throw new InvalidImplementationException("UserServiceImpl",
					"requestPasswordReset", emailAddress,
					"Email was not validated prior to calling this service. Please validate first.");
		}
		PasswordReset passwd = new PasswordReset();
		String token = StringUtil.generateRandomString(tokenLength);
		String cipher = StringUtil.encrypt(token + emailAddress);
		passwd.setEmailAddress(emailAddress);
		passwd.setToken(token);
		passwd.setStatus("active");
		passwd.setCipher(cipher);
		passwordResetDAO.saveEntityModel(passwd);
		// send email for confirmation
		sendEmailConfirmation(emailAddress, token, cipher);
	}

	/**
	 * @param emailAddress
	 * @param token
	 * @param cipher
	 */
	private void sendEmailConfirmation(String emailAddress, String token,
			String cipher) {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("address", emailAddress);
		dataMap.put("confirmationCode", token);
		dataMap.put("confirmURL", confirmURL);
		dataMap.put("link", confirmURL + "?cipher=" + cipher);
//		mailMessage.setMsgTo(emailAddress);
//		mailMessage
//				.setSubject("Information regarding your password reset");
//		mailMessage.setPlainTextTemplate("password_reset.vm");
//		mailMessage.setDataMap(dataMap);
//		mailSender.send(messagePreparator);
	}

	/**
	 * Resets the password by specifying email address and token.
	 */
	public boolean confirmPasswordReset(String emailAddress, String token) {
		// check if email and token matched
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(emailAddress);
		example.setToken(token);
		example.setStatus("active");
		List<PasswordReset> actuals = passwordResetDAO.findByExample(example,
				true);
		if (actuals == null || actuals.size() == 0) {
			_log.info("Failed to confirm password reset. No records matched in password reset database for email "
							+ emailAddress);
			return false;
		}
		// check if password reset is active and not expired
		PasswordReset actual = actuals.get(0);
		Date updated = actual.getUpdateDate();
		Date expireDate = new Date(updated.getTime() + 86400000);
		Date today = new Date();
		if (expireDate.getTime() < today.getTime()) {
			// expired
			_log.info("Password reset has expired for " + emailAddress);
			actual.setStatus(PasswordReset.STATUS_EXPIRED);
			passwordResetDAO.saveEntityModel(actual);
			return false;
		}
		return true;
	}

	/**
	 * Validates the cipher for password reset and returns the corresponding
	 * email address and token.
	 * 
	 * @param passwd
	 * @return
	 */
	public boolean confirmPasswordResetByCipher(PasswordReset passwd) {
		String decrypted = StringUtil.decrypt(passwd.getCipher());
		if (StringUtil.isEmpty(decrypted)) {
			_log
					.info("Failed attempt to confirm password reset due to wrong cipher key.["
							+ passwd.getCipher() + "]");
			return false;
		}
		String token = decrypted.substring(0, tokenLength);
		String email = decrypted.substring(tokenLength);
		passwd.setToken(token);
		passwd.setEmailAddress(email);
		return confirmPasswordReset(email, token);
	}

	/**
	 * Resets the password
	 * 
	 * @param passwd
	 * @return
	 */
	public boolean resetPassword(PasswordReset passwd) {
		// check if password reset is active and not expired
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(passwd.getEmailAddress());
		example.setToken(passwd.getToken());
		example.setStatus("active");
		List<PasswordReset> actuals = passwordResetDAO.findByExample(example,
				true);
		if (actuals == null || actuals.size() == 0) {
			_log
					.info("Failed to reset password. No records found in password reset for email "
							+ passwd.getEmailAddress());
			return false;
		}
		PasswordReset actual = actuals.get(0);
		actual.setStatus(PasswordReset.STATUS_USED);
		passwordResetDAO.saveEntityModel(actual);

		// now reset the password
		UserDAO userDAO = (UserDAO) getDao();
		BaseUser user = userDAO.loadByEmailAddress(passwd.getEmailAddress());
		user.getCredential().setPassword(passwd.getPassword());
		userDAO.saveEntityModel(user);

		return true;
	}

	/**
	 * Ensures that admin user is created into the database. This method is
	 * called by ApplicationStartupListener to ensure admin user is available
	 */
	public boolean setupAdminUser() {
		boolean exist = false;
		// let's check if there are users in the database
		UserDAO userDAO = (UserDAO) getDao();
		if (userDAO.countAll() > 0) {
			exist = true;
		} else {
			// if none, let's create admin user
			BaseUser user = new BaseUser();
			UserCredential cred = new UserCredential();
			cred.setUsername("admin");
			cred.setPassword("ideyatech");
			cred.setEnabled(true);
			user.setCredential(cred);
			user.setEmailAddress("admin@ideyatech.com");
			user.setFirstName("Admin");
			user.setLastName("User");

			// create usergroup for user
			UserGroup userGroup = new UserGroup();
			userGroup.setName("admin group");
			userGroup.setDescription("with all roles");
			// Let's assign all available roles
			List<String> roleNames = new ArrayList<String>();
			for (String key : roles.keySet()) {
				roleNames.add(key);
			}
			userGroup.setRoleNames(roleNames);
			userGroupDAO.saveEntityModel(userGroup);

			user.addGroup(userGroup);
			userDAO.saveEntityModel(user);
			_log
					.info("New database detected, inserted admin/ideyatech user to database.");
		}
		return !exist;
	}

	/**
	 * @param passwordResetDAO
	 *            the passwordResetDAO to set
	 */
	public void setPasswordResetDAO(PasswordResetDAO passwordResetDAO) {
		this.passwordResetDAO = passwordResetDAO;
	}

	/**
	 * @param tokenLength
	 *            the tokenLength to set
	 */
	public void setTokenLength(int tokenLength) {
		this.tokenLength = tokenLength;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	
	

	/**
	 * @param confirmURL
	 *            the confirmURL to set
	 */
	public void setConfirmURL(String confirmURL) {
		this.confirmURL = confirmURL;
	}

	/**
	 * @param userGroupDAO
	 *            the userGroupDAO to set
	 */
	public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
		this.userGroupDAO = userGroupDAO;
	}

	/**
	 * @return the mailMessage
	 */
	public MailMessage getMailMessage() {
		return mailMessage;
	}

	/**
	 * @param mailMessage the mailMessage to set
	 */
	public void setMailMessage(MailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
}
