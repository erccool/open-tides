/**
 * 
 */
package org.opentides.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.concurrent.SessionRegistry;
import org.acegisecurity.concurrent.SessionRegistryUtils;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.logout.SecurityContextLogoutHandler;
import org.apache.log4j.Logger;

/**
 * @author allantan
 *
 */
public class LogoutFilter extends SecurityContextLogoutHandler {
	
	private static Logger _log = Logger.getLogger(LogoutFilter.class);

	private SessionRegistry sessionRegistry;

	/* (non-Javadoc)
	 * @see org.acegisecurity.ui.logout.SecurityContextLogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) {
		try {
	        SecurityContext context = SecurityContextHolder.getContext();
	        if (context == null) return;
	        Authentication authentication = context.getAuthentication();
	        if (authentication == null) return;
	        String sessionId = SessionRegistryUtils.obtainSessionIdFromAuthentication(authentication);
	        this.sessionRegistry.removeSessionInformation(sessionId);
		} catch (Exception e) {
			_log.error("Failed to invalidate user session.",e);
		}
	}

	/**
	 * @param sessionRegistry the sessionRegistry to set
	 */
	public final void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

}
