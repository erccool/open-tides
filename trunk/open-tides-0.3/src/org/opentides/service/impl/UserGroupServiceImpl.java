package org.opentides.service.impl;

import java.util.Map;

import org.opentides.bean.user.UserGroup;

import org.apache.log4j.Logger;
import org.opentides.persistence.UserGroupDAO;
import org.opentides.service.UserGroupService;


public class UserGroupServiceImpl extends BaseCrudServiceImpl<UserGroup>
		implements UserGroupService {
	private static Map<String, String> roles;
	private UserGroupDAO userGroupDAO;

	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(UserGroupServiceImpl.class);

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
		UserGroupServiceImpl.roles = roles;
	}
	
	public UserGroup loadUserGroupByName(String name){
		return userGroupDAO.loadUserGroupByName(name);
	}

	/**
	 * @param userGroupDAO the userGroupDAO to set
	 */
	public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
		this.userGroupDAO = userGroupDAO;
	}
	
	
}
