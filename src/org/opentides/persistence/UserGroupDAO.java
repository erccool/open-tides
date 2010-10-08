package org.opentides.persistence;

import java.util.Set;

import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;

import org.opentides.persistence.BaseEntityDAO;


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
	
	/**
	 * get User Group by Name
	 * 
	 * @param name
	 */
	public UserGroup loadUserGroupByName(String name);
}
