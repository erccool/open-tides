package org.opentides.persistence.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opentides.bean.user.BaseUser;
import org.opentides.persistence.UserDAO;
import org.opentides.util.DateUtil;
import org.opentides.util.StringUtil;

public class UserDAOImpl extends BaseEntityDAOJpaImpl<BaseUser, Long> implements
		UserDAO {

	@SuppressWarnings("unused")
	private static Log _log = LogFactory.getLog(UserDAOImpl.class);

	public final boolean isRegisteredByEmail(String emailAddress) {
		if (StringUtil.isEmpty(emailAddress))
			return false;
		String queryString = getJpqlQuery("jpql.user.countByEmailAddress");
		Query queryObject = getEntityManager().createQuery(queryString);
		queryObject.setParameter("emailAddress", emailAddress);
		long count = (Long) queryObject.getSingleResult();
		return count != 0;
	}

	public final BaseUser loadByUsername(String username) {
		if (StringUtil.isEmpty(username))
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		List<BaseUser> result = findByNamedQuery("jpql.user.findByUsername",
				params);
		if (result == null || result.size() == 0) {
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
		List<BaseUser> result = findByNamedQuery("jpql.user.findByEmailAddress",
				params);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result.get(0);
		}
	}
	
	public List<BaseUser> getUsersByUsergroupName(String userGroupName) {
		if (StringUtil.isEmpty(userGroupName))
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", userGroupName);
		List<BaseUser> result = findByNamedQuery(
				"jpql.user.findByUserGroupName", params);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result;
		}
	}

	public void updateLastLogin(String username) {
		Query update = getEntityManager().createNativeQuery(
				"update USER_PROFILE up, USERS u set LASTLOGIN='"
						+ DateUtil.dateToString(new Date(),
								"yyyy-MM-dd hh:mm:ss")
						+ "' where u.id=up.id and u.username='" + username
						+ "'");
		update.executeUpdate();
	}

}
