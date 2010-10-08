package com.ideyatech.core.persistence.impl;

import java.util.HashSet;
import java.util.Set;

import com.ideyatech.core.bean.user.UserGroup;
import com.ideyatech.core.bean.user.UserRole;
import com.ideyatech.core.persistence.UserGroupDAO;

/**
 * 
 * @author rjimenez
 * 
 */
public class UserGroupDAOJpaImpl extends BaseEntityDAOJpaImpl<UserGroup, Long>
		implements UserGroupDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ideyatech.ecmt.dao.UserGroupDAO#assignRoles(com.ideyatech.ecmt.bean.admin.UserGroup,
	 *      java.util.Set)
	 */
	public void assignRoles(UserGroup userGroup, Set<UserRole> roles)
			throws NullPointerException {
		if (userGroup == null) {
			throw new NullPointerException();
		}
		Set<UserRole> oldRoles = userGroup.getRoles();
		if (oldRoles == null) {
			oldRoles = new HashSet<UserRole>();
		}
		oldRoles.addAll(roles);
		updateEntityModel(userGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ideyatech.ecmt.dao.UserGroupDAO#assignRoles(com.ideyatech.ecmt.bean.admin.UserGroup,
	 *      com.ideyatech.core.bean.user.UserRole)
	 */
	public void assignRoles(UserGroup userGroup, UserRole role)
			throws NullPointerException {
		if (userGroup == null || role == null) {
			throw new NullPointerException();
		}
		userGroup.addRole(role);
		updateEntityModel(userGroup);
	}

}
