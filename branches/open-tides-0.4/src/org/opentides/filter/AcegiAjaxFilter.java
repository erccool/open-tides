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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.opentides.filter.RedirectResponseWrapper;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Tomcat filter to allow ajax driven login page using ACEGI.
 * @author allanctan
 */
public class AcegiAjaxFilter extends OncePerRequestFilter {

	@Override	 
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {	
		
		if (!isAjaxRequest(request)) {
			filterChain.doFilter(request, response);
        	if(request.getSession().getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY) != null)
        	{
        		String errorMessage = ((AuthenticationException) request.
                        getSession().getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage();
        		request.getSession().setAttribute("otherLoginIssues", errorMessage);
        	}
        	return;
        }
		        
        RedirectResponseWrapper redirectResponseWrapper = new RedirectResponseWrapper(response);
        
        filterChain.doFilter(request, redirectResponseWrapper);
        
        if (redirectResponseWrapper.getRedirect() != null) {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain;charset=utf-8");
 
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "no-cache");

 
            String redirectURL = redirectResponseWrapper.getRedirect();
            String content;
            if (redirectURL.indexOf("login_error=1") == -1) {
                content = "url:" + redirectURL;
            } else {
                content = "error:"; 
                String errorMessage = ((AuthenticationException) request.
                                    getSession().getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage();
                content += errorMessage;
            }
            response.getOutputStream().write(content.getBytes("UTF-8"));
        }
    }

 
    private boolean isAjaxRequest(HttpServletRequest request) {
        return request.getParameter("ajax") != null;
    }

}
