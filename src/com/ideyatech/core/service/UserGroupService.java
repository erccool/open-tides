package com.ideyatech.core.service;

import java.util.Map;

import com.ideyatech.core.bean.user.UserGroup;

public interface UserGroupService extends BaseCrudService<UserGroup> {
	public Map<String, String> getRoles();

	public void setRoles(Map<String, String> roles);
}
