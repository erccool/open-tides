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

package org.opentides.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentides.bean.Widget;
import org.opentides.service.UserService;
import org.opentides.service.WidgetService;
import org.opentides.util.StringUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author ideyatech
 * 
 */
public class LoadWidgetController extends AbstractController {

	private WidgetService widgetService;
	private String viewName = "core/widget/widget-load";
	private UserService userService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String name = req.getParameter("name");
		Map<String, Object> model = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(name)) {
			String widgetUrl = req.getRequestURL().toString();
			Widget widget = widgetService
					.requestWidget(widgetUrl, name, req);
			if (widget != null) {
				if (widget.getCacheType().startsWith(
						Widget.TYPE_IMAGE)) {
					// return as image
					res.setContentType(widget.getCacheType());
					OutputStream outputStream = res.getOutputStream();
					outputStream.write(widget.getCache());
					outputStream.flush();
					return null;
				}
				model.put("body", new String(widget.getCache()));
				model.put("cacheDate", widget.getLastCacheUpdate());
			} else {
				model.put("body", "Unable to load widget. Name [" + name
						+ "] not found");
				model.put("cacheDate", new Date());
			}
		} else {
			model
					.put("body",
							"Unable to load widget. Please specify name in request parameter.");
			model.put("cacheDate", new Date());
		}
		return new ModelAndView(viewName, model);
	}

	/**
	 * @param dashboardSettingsService
	 *            the dashboardSettingsService to set
	 */
	public void setWidgetService(
			WidgetService widgetService) {
		this.widgetService = widgetService;
	}

	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName
	 *            the viewName to set
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
