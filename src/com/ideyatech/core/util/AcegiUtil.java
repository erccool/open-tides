package com.ideyatech.core.util;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;

import com.ideyatech.core.bean.user.SessionUser;
/**
 * This class is an ACEGI helper that retrieves the currently logged in user.
 * 
 * @author allantan
 */
public class AcegiUtil {
	
	private static Logger _log = Logger.getLogger(AcegiUtil.class);
	
	// flag to allow null user (user not logged-in) 
	// turn this flag to false when deploying to production
	private static Boolean debug = false;
	/**
	 * Static helper to retrieve currently logged user.
	 * @return
	 */
	public static SessionUser getSessionUser() {
		try {
			Object userObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userObj instanceof SessionUser)
				return (SessionUser) userObj;
			else { 
				if (debug) {
					// in debug mode, we provide temporary Acegi user
					SessionUser user = new SessionUser("debug","debug",true,
							new GrantedAuthority[] {new GrantedAuthorityImpl("ROLE_ADMIN")});
					user.setId(999999l);
					user.setFirstName("Debugger");
					user.setLastName("Debugged");
					return user;
				}
				return null;
			}
		} catch (Exception e) {
			_log.error(e,e);
			return null;
		}
	}
	/**
	 * Non-static accessor for JSTL. 
	 * Calls getSessionUser.
	 * @return 
	 */
	public SessionUser getUser() {
		return AcegiUtil.getSessionUser();
	}
	/**
	 * @param debug the debug to set
	 */
	public static void setDebug(Boolean debug) {
		AcegiUtil.debug = debug;
	}
}
