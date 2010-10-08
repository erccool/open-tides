package org.opentides.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.opentides.bean.user.UserGroup;

import org.opentides.controller.BaseCrudController;

import org.opentides.service.UserGroupService;


/**
 * This controller is responsible handling usergroup management.
 * 
 * 
 */
public class UserGroupController extends BaseCrudController<UserGroup> {
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roles", ((UserGroupService) getService()).getRoles());
		return model;
	}
}
