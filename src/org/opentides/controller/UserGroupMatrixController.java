/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */

package org.opentides.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentides.bean.user.UserGroup;
import org.opentides.service.UserGroupService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class UserGroupMatrixController extends AbstractController {
	
	private String viewName = "core/usergroup/usergroup-matrix";
	
	private UserGroupService userGroupService;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> roles = userGroupService.getRoles();
		List<UserGroup> userGroups = userGroupService.findByExample(new UserGroup());	
		model.put("roles", roles);
		for (UserGroup userGroup:userGroups)
			userGroup.mapRoleMatrix(roles);
		model.put("userGroups", userGroups);
				return new ModelAndView(viewName, model);
	}

	public final void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public final void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}
		
}
