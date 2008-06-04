/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * UserDAO.java
 * Created on Feb 17, 2008, 4:09:49 PM
 */

package com.ideyatech.core.persistence;

import com.ideyatech.core.bean.user.BaseUser;
/**
 * This is the DAO for users and permissions.
 * @author allanctan
 */
public interface UserDAO extends BaseEntityDAO<BaseUser, Long> {
	public boolean isRegisteredByEmail(String emailAddress);
	public BaseUser loadByUsername(String username);
	public BaseUser loadByEmailAddress(String emailAddress);
}