package com.ideyatech.core.editor;

import java.beans.PropertyEditorSupport;

import com.ideyatech.core.bean.user.UserGroup;
import com.ideyatech.core.service.UserGroupService;
import com.ideyatech.core.util.StringUtil;

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
		return new String("");
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
