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
package org.opentides.editor;

import java.beans.PropertyEditorSupport;

import org.opentides.bean.user.UserGroup;

import org.opentides.service.UserGroupService;
import org.opentides.util.StringUtil;



public class UserGroupEditor extends PropertyEditorSupport {
	private UserGroupService userGroupService;

	public UserGroupEditor(UserGroupService userGroupService) {
		super();
		this.userGroupService = userGroupService;
	}

	@Override
	public String getAsText() {
		if (getValue() != null) {
			UserGroup editor = (UserGroup) getValue();
			return editor.getId().toString();
		}
		return "";
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtil.isEmpty(text)) {
			UserGroup group = userGroupService.load(text);
			setValue(group);
		}
	}

	/**
	 * @param userGroupService
	 *            the userGroupService to set
	 */
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}
}
