/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * AcegiAjaxFilter.java
 * Created on Feb 13, 2008, 10:17:04 AM
 */
package com.ideyatech.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ui.AbstractProcessingFilter;
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
                content = "error:" + ((AuthenticationException) request.
                                    getSession().getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage();
            }
            response.getOutputStream().write(content.getBytes("UTF-8"));
        }
    }

 
    private boolean isAjaxRequest(HttpServletRequest request) {
        return request.getParameter("ajax") != null;
    }

}
