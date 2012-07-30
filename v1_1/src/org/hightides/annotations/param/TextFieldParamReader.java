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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hightides.annotations.TextField;
import org.opentides.util.StringUtil;

public class TextFieldParamReader extends BaseParamReader implements
		FieldParamReader {
	
	public Map<String, Object> getParameters(Field field) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (!BaseParamReader.isValidation())
			BaseParamReader.setValidation(field.getAnnotation(TextField.class).requiredField());
		
		if (!StringUtil.isEmpty(field.getAnnotation(TextField.class).label()))
			params.put("label", field.getAnnotation(TextField.class).label());
		
		if (Date.class.isAssignableFrom(field.getType())) {
			params.put("isDate", true);
			params.put("dateFormat", field.getAnnotation(TextField.class).dateFormat());
		}
		
		if (field.getType().isPrimitive())
			params.put("isNumeric", true);
		
		params.put("isListed", field.getAnnotation(TextField.class).listed());
		params.put("isEmail", field.getAnnotation(TextField.class).email());
		params.put("isSearchable", field.getAnnotation(TextField.class).searchable());
		params.put("isRequiredField", field.getAnnotation(TextField.class).requiredField());
		params.put("springParams", field.getAnnotation(TextField.class).springParams());
		params.put("defaultValue", field.getAnnotation(TextField.class).defaultValue());		
		params.put("fieldType", "textField");
		params.putAll(super.getStandardParams(field));
		
		return params;
	}

}
