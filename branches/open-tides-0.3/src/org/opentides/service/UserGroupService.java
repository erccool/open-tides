package org.opentides.service;

import java.util.Map;

import org.opentides.bean.user.UserGroup;



public interface UserGroupService extends BaseCrudService<UserGroup> {
	public Map<String, String> getRoles();

	public void setRoles(Map<String, String> roles);
	
	public UserGroup loadUserGroupByName(String name); 
}
