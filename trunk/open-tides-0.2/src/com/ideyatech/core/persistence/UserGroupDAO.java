package com.ideyatech.core.persistence;

import java.util.Set;

import com.ideyatech.core.bean.user.UserGroup;
import com.ideyatech.core.bean.user.UserRole;

/**
 * UserGroup DAO, methods for accessing data related to user group such as
 * saving, editing, deleting and querying specific to usergroup
 * 
 * @author rjimenez
 * 
 */
public interface UserGroupDAO extends BaseEntityDAO<UserGroup, Long> {
	/**
	 * Add roles to a userGroup
	 * 
	 * @param userGroup
	 * @param roles
	 *            set of roles to add
	 */
	public void assignRoles(UserGroup userGroup, Set<UserRole> roles);

	/**
	 * Add a role to a userGroup
	 * 
	 * @param userGroup
	 * @param role
	 *            role to add
	 */
	public void assignRoles(UserGroup userGroup, UserRole role);
}
