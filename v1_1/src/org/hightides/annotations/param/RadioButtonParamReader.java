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

import org.hightides.annotations.RadioButton;
import org.opentides.util.StringUtil;

public class RadioButtonParamReader extends BaseParamReader implements	FieldParamReader {

	public Map<String, Object> getParameters(Field field) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		String[] options = field.getAnnotation(RadioButton.class).options();
		String categoryName = field.getAnnotation(RadioButton.class).category();
		// put list type params (e.g. category, options or object)
		params.putAll(populateListTypeParams(field, categoryName, options));
						
		if (!BaseParamReader.isValidation()) {
			BaseParamReader.setValidation(field.getAnnotation(RadioButton.class).requiredField());
		}

		if (!StringUtil.isEmpty(field.getAnnotation(RadioButton.class).label())) {
			params.put("label", field.getAnnotation(RadioButton.class).label());
		}

		params.put("isListed", field.getAnnotation(RadioButton.class).listed());
		params.put("isSearchable", field.getAnnotation(RadioButton.class).searchable());
		params.put("isRequiredField", field.getAnnotation(RadioButton.class).requiredField());
		params.put("springParams", field.getAnnotation(RadioButton.class).springParams());
		params.put("fieldType", "radioButton");
		params.putAll(super.getStandardParams(field));
		
		return params;
	}

}
