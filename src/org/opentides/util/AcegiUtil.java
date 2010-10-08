package org.opentides.util;

import org.opentides.bean.user.SessionUser;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;

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
							new GrantedAuthority[] {new GrantedAuthorityImpl("SUPER_USER")});
					user.setId(999999l);
					user.setFirstName("Superuser");
					user.setLastName("Debugger");
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
	 * Static helper to check if currently logged-in user has access
	 * to given permission.
	 * @param permission
	 * @return
	 */
	public static boolean currentUserHasPermission(String permission) {
		SessionUser user = AcegiUtil.getSessionUser();
		if (user!=null) {
			GrantedAuthority[] granted = user.getAuthorities();
			for (int i=0; i<granted.length; i++) {
				if (permission.equals(granted[i].getAuthority()))
					return true;
			}
		}
		return false;
	}
	/**
	 * @param debug the debug to set
	 */
	public static void setDebug(Boolean debug) {
		AcegiUtil.debug = debug;
	}
}
