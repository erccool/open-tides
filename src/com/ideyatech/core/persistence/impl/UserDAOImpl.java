package com.ideyatech.core.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ideyatech.core.bean.user.BaseUser;
import com.ideyatech.core.persistence.UserDAO;
import com.ideyatech.core.util.StringUtil;


public class UserDAOImpl extends BaseEntityDAOJpaImpl<BaseUser, Long> implements UserDAO {
	
	@SuppressWarnings("unused")
	private static Log _log = LogFactory.getLog(UserDAOImpl.class);

	public final boolean isRegisteredByEmail(String emailAddress) {
		if (StringUtil.isEmpty(emailAddress))
			return false;
		String queryString = getJpqlQuery("BaseUser.countByEmailAddress");
		Query queryObject = getEntityManager().createQuery(queryString);
		queryObject.setParameter("emailAddress", emailAddress);
		long count = (Long) queryObject.getSingleResult();
		return count!=0;
	}

	public final BaseUser loadByUsername(String username) {
		if (StringUtil.isEmpty(username))
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		List<BaseUser> result = findByNamedQuery("BaseUser.findByUsername", params);
		if (result==null || result.size()==0) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public final BaseUser loadByEmailAddress(String emailAddress) {
		if (StringUtil.isEmpty(emailAddress))
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("emailAddress", emailAddress);
		List<BaseUser> result = findByNamedQuery("BaseUser.findByEmailAddress", params);
		if (result==null || result.size()==0) {
			return null;
		} else {
			return result.get(0);
		}
	}

}
