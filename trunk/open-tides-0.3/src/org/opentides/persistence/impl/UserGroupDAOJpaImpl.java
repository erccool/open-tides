package org.opentides.persistence.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;

import org.opentides.persistence.UserGroupDAO;



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
	
	public UserGroup loadUserGroupByName(String name){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		List<UserGroup> list = findByNamedQuery("jpql.usergroup.findByName", map);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}
	
}
