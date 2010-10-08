/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * SystemCodesController.java
 * Created on Feb 5, 2008, 11:50:35 PM
 */
package com.ideyatech.core.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ideyatech.core.bean.SystemCategory;
import com.ideyatech.core.bean.SystemCodes;

/**
 * This controller is responsible in handling system codes (aka lookup) 
 * management. This class extended BaseCrudController to extend
 * standard CRUD operations.
 * 
 * @author allanctan
 */
public class SystemCodesController extends BaseCrudController<SystemCodes> {

	private SystemCategory category;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("categories", category.getAllCategories());
		return map;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(SystemCategory category) {
		this.category = category;
	}
	
}
