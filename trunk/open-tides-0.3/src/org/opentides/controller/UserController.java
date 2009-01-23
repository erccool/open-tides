/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * UserController.java
 * Created on Feb 19, 2008, 9:50:35 PM
 */
package org.opentides.controller;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.opentides.bean.user.BaseUser;
import org.opentides.bean.user.UserGroup;

import org.opentides.controller.BaseCrudController;

import org.opentides.editor.UserGroupEditor;
import org.opentides.service.UserGroupService;

import org.springframework.web.bind.ServletRequestDataBinder;


/**
 * This controller is responsible handling user management.
 * 
 * @author allanctan
 */
public class UserController extends BaseCrudController<BaseUser> {
	private UserGroupService userGroupService;

	/**
	 * @param userGroupService
	 *            the userGroupService to set
	 */
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	/**
	 * Overidden to provide usergroup list to page
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<UserGroup> groups = userGroupService.findAll();
		model.put("groups", (groups.size() > 0) ? groups : null);
		return model;
	}

	/**
	 * Implements initBinder from BaseCrudController to bind UserGroup class
	 */
	@Override
	protected void initBinder(HttpServletRequest arg0,
			ServletRequestDataBinder binder) throws Exception {
		PropertyEditorSupport pes = new UserGroupEditor(userGroupService);
		binder.registerCustomEditor(UserGroup.class, pes);
	}
}
