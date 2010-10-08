package com.ideyatech.core.persistence.impl;

import java.util.Map;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.acegisecurity.userdetails.jdbc.JdbcDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.ideyatech.core.bean.user.SessionUser;

/**
 * This class is responsible in retrieving the user information.
 * TODO: Rewrite this to use JPA EntityManager... if at all, possible.
 * @author allantan
 *
 */
public class AuthenticationDAOJdbcImpl extends JdbcDaoImpl {

	private static Log _log = LogFactory.getLog(AuthenticationDAOJdbcImpl.class);	
	private static String loadUserByUsernameQuery = 
		"select U.USERID ID, FIRSTNAME, LASTNAME, EMAIL, '' POSITION,'' COMPANY,'' PICTUREURL" +
		" from USER_PROFILE P, USERS U where P.ID=U.USERID and U.USERNAME=?";
	
	@SuppressWarnings("unchecked")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
            UserDetails user = super.loadUserByUsername(username);
            SessionUser sessUser = new SessionUser(user.getUsername(), user.getPassword(), user.isEnabled(), user.getAuthorities());
            Map<String,Object> result = getJdbcTemplate().queryForMap(loadUserByUsernameQuery.replace("?", "'"+username+"'"));
            sessUser.setFirstName(result.get("FIRSTNAME").toString());
            sessUser.setLastName(result.get("LASTNAME").toString());
            sessUser.setEmailAddress(result.get("EMAIL").toString());
            sessUser.setPosition(result.get("POSITION").toString());
            sessUser.setCompany(result.get("COMPANY").toString());
            sessUser.setPictureUrl(result.get("PICTUREURL").toString());
            sessUser.setId((Long) result.get("ID"));
            return sessUser;
		} catch (UsernameNotFoundException ex1) {
			_log.error(ex1);
		    throw ex1;
		} catch (DataAccessException ex2) {
			_log.error(ex2);
		    throw ex2;
		}
	}
}
