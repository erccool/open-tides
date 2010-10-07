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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hightides.annotations.Page;
import org.hightides.annotations.bean.SyncMode;
import org.hightides.annotations.util.AnnotationUtil;

/**
 * Appends parameters for Page annotation.
 * 
 * @author allantan
 *
 */
public class PageParamReader extends BaseParamReader implements
		ClassParamReader {

	private static Logger _log = Logger.getLogger(PageParamReader.class);

	/**
	 * Returns map of every field parameters.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getParameters(Class clazz) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Field>fields = getAllAnnotatedFields(clazz);
		List<Map<String,Object>> fieldParams = new ArrayList<Map<String,Object>>();
		for (Field field:fields) {
			FieldParamReader paramReader = ParamReaderFactory.getReader(field);
			if (paramReader != null) {
				fieldParams.add(paramReader.getParameters(field));				
			}
		}
		params.put("fields", fieldParams);
		params.put("titleField", AnnotationUtil.getTitleField(clazz));
		Annotation annotation = clazz.getAnnotation(Page.class);
		if (annotation instanceof Page) {
			Page page = (Page) annotation;
			params.put("syncMode", page.synchronizeMode());
		} else {
			// unexpected case... but handle it nevertheless
			params.put("syncMode", SyncMode.UPDATE);
			_log.error("Unable to retrieve syncMode for ["+clazz.getName()+"]");
		}
		return params;
	}

}
