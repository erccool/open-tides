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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentides.bean.SearchResults;
import org.opentides.bean.SystemCodes;
import org.opentides.bean.user.BaseUser;
import org.opentides.service.SystemCodesService;
import org.opentides.util.StringUtil;
import org.springframework.validation.BindException;


/**
 * This controller is responsible in handling system codes (aka lookup) 
 * management. This class extended BaseCrudController to extend
 * standard CRUD operations.
 * 
 * @author allanctan
 */
public class SystemCodesController extends BaseCrudController<SystemCodes> {
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		SystemCodesService scService = (SystemCodesService) this.getService();
		//model.put("categories", scService.getAllCategories());
		model.put("categories", scService.getAllCategoriesExcept("KEYGEN"));
		model.put("officeList", scService.findSystemCodesByCategory(BaseUser.CATEGORY_OFFICE));
		return model;
	}

	
	@Override
	protected SearchResults<SystemCodes> postSearchAction(
			HttpServletRequest request, HttpServletResponse response,
			SystemCodes command, BindException errors,
			SearchResults<SystemCodes> result) {
		SearchResults<SystemCodes> finalResult = new SearchResults<SystemCodes>(getPageSize(), getNumLinks());
		List<SystemCodes> systemCodesList = new ArrayList<SystemCodes>();
		int count = 0;
		for (SystemCodes s : result.getResults()){
			if (!StringUtil.isEmpty(s.getCategory()) && !s.getCategory().equals("KEYGEN")){
				systemCodesList.add(s);
			}
			count++;
		}
		finalResult.addResults(systemCodesList);
		finalResult.setCurrPage(result.getCurrPage());
		finalResult.setTotalResults(result.getTotalResults() - count);
		
		return finalResult;
	}
}
