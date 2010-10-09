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
package org.hightides.annotations.param;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.hightides.annotations.DropDown;
import org.opentides.util.StringUtil;

public class DropDownParamReader extends BaseParamReader implements	FieldParamReader {

	public Map<String, Object> getParameters(Field field) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		String[] options = field.getAnnotation(DropDown.class).options();
		String categoryName = field.getAnnotation(DropDown.class).category();
		// put list type params (e.g. category, options or object)
		params.putAll(populateListTypeParams(field, categoryName, options));
				
		if (!BaseParamReader.isValidation()) {
			BaseParamReader.setValidation(field.getAnnotation(DropDown.class).requiredField());		
		}
		
		if (!StringUtil.isEmpty(field.getAnnotation(DropDown.class).label())) {
			params.put("label", field.getAnnotation(DropDown.class).label());
		}
		
		params.put("isListed", field.getAnnotation(DropDown.class).listed());
		params.put("isSearchable", field.getAnnotation(DropDown.class).searchable());
		params.put("isRequiredField", field.getAnnotation(DropDown.class).requiredField());
		params.put("springParams", field.getAnnotation(DropDown.class).springParams());
		params.put("fieldType", "dropDown");
		params.putAll(super.getStandardParams(field));
		
		return params;
	}

}
