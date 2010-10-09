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
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hightides.annotations.Service;
import org.hightides.annotations.bean.SyncMode;

/**
 * Appends parameters for Page annotation.
 * 
 * @author allantan
 *
 */
public class ServiceParamReader extends BaseParamReader implements
		ClassParamReader {

	private static Logger _log = Logger.getLogger(ServiceParamReader.class);

	/**
	 * Returns map of every field parameters.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getParameters(Class clazz) {
		Map<String, Object> params = new HashMap<String, Object>();
		Annotation annotation = clazz.getAnnotation(Service.class);
		if (annotation instanceof Service) {
			Service service = (Service) annotation;
			params.put("syncMode", service.synchronizeMode());
		} else {
			// unexpected case... but handle it nevertheless
			params.put("syncMode", SyncMode.UPDATE);
			_log.error("Unable to retrieve syncMode for ["+clazz.getName()+"]");
		}
		return params;
	}

}
