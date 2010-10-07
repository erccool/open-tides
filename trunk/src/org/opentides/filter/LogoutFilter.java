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
