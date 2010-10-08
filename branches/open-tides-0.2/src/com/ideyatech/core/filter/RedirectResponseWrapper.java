/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * RedirectResponseWrapper.java
 * Created on Feb 13, 2008, 10:17:04 AM
 */

package com.ideyatech.core.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * Support class for AcegiAjaxFilter
 * 
 * @author allanctan
 */
public class RedirectResponseWrapper extends HttpServletResponseWrapper {
    private String redirect;
 
    public RedirectResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }
 
    public String getRedirect() {
        return redirect;
    }
 
    public void sendRedirect(String string) throws IOException {
        this.redirect = string;
    }
}
