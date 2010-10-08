/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * ViewMapController.java
 * Created on Feb 13, 2008, 11:50:35 PM
 */

package com.ideyatech.core.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Use this controller to map URL to jsp page directly.
 * 
 * @author allanctan
 */
public class ViewMapController extends AbstractController {
	private Map<String,String> viewMap;
	private String defaultView;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uri = request.getRequestURI();
		String key;
		String viewName;

		int idx = uri.lastIndexOf("/");
		if (idx > 0)
			key = uri.substring(idx+1);
		else
			key = uri;
		if (viewMap.containsKey(key))
			viewName = viewMap.get(key);
		else
			viewName = defaultView;
		return new ModelAndView(viewName);
	}

	public void setViewMap(Map<String,String> viewMap) {
		this.viewMap = viewMap;
	}

	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}
}
