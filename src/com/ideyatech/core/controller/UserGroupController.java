package com.ideyatech.core.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ideyatech.core.bean.user.UserGroup;
import com.ideyatech.core.service.UserGroupService;

/**
 * This controller is responsible handling usergroup management.
 * 
 * 
 */
public class UserGroupController extends BaseCrudController<UserGroup> {
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roles", ((UserGroupService) getService()).getRoles());
		return model;
	}
}
