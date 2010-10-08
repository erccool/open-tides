package com.ideyatech.core.bean;

import java.util.ArrayList;
import java.util.List;

import com.ideyatech.core.bean.user.BaseUser;

/**
 * This is a test class used to check if CrudUtil.retrieveObjectValue
 * and retrieveObjectType are working
 * @author allantan
 *
 */
public class UserCriteria extends BaseUser {

	/* (non-Javadoc)
	 * @see com.ideyatech.core.bean.user.BaseUser#getSearchProperties()
	 */
	@Override
	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("firstName");
		props.add("lastName");
		props.add("emailAddress");
		props.add("credential.username");
		props.add("credential.enabled");
		props.add("credential.id");
		return props;
	}

}
