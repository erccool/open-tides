package com.ideyatech.core.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ideyatech.core.bean.user.UserGroup;
import com.ideyatech.core.service.UserGroupService;

public class UserGroupServiceImpl extends BaseCrudServiceImpl<UserGroup>
		implements UserGroupService {
	private static Map<String, String> roles;

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
		this.roles = roles;
	}
}
