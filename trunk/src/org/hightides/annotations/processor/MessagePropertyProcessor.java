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

package org.hightides.annotations.processor;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hightides.annotations.util.NamingUtil;
import org.opentides.util.FileUtil;

/**
 * This class is responsible in updating the message property file.
 * @author allantan
 *
 */
public class MessagePropertyProcessor implements Processor {
	
	private static Logger _log = Logger.getLogger(MessagePropertyProcessor.class);
	private static final String messageFilename = "src/resources/languages/messages.properties";

	/* (non-Javadoc)
	 * @see org.hightides.annotations.processor.Processor#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void execute(Map<String, Object> params) {
		
		// process only when @Page annotation
		if (!"org.hightides.annotations.Page".equals(params.get("annotation"))) {
			return;
		}
		// read the property files
		Properties origProps = FileUtil.readProperty(messageFilename);

		// add model name label
		Properties newProps = new Properties();
		String prefix = "label."+NamingUtil.toElementName((String)params.get("modelName"))+".";
		String key = prefix.substring(0, prefix.length()-1); // remove the dot 
		String label  = (String)params.get("label");
		if (label == null)
			label = (String) params.get("className");
		if (!origProps.containsKey(key) || !label.equals(origProps.get(prefix))) {
			newProps.put(key, label);
		}		

		// retrieve the message keys and values
		List<Map<String,Object>> fieldParams = (List<Map<String,Object>>) params.get("fields");
		for (Map<String,Object> props:fieldParams) {
			key   = prefix+props.get("fieldName");
			label = (String) props.get("label");
			if (label == null)
				label = NamingUtil.toLabel((String)props.get("fieldName"));
			// update only when changes exist
			if (!origProps.containsKey(key) || !label.equals(origProps.get(key))) {
				newProps.put(key, label);
			}
			// add model name with field name when field is numeric or a date field
			if ((Boolean)props.get("isNumeric") != null || (Boolean)props.get("isDate") != null) {
				key = NamingUtil.toElementName((String)params.get("modelName"))+"."+props.get("fieldName");
				// update only when changes exist
				if (!origProps.containsKey(key) || !label.equals(origProps.get(key)))
					newProps.put(key, label);
			}
		}
		if (newProps.size()==0) {
			// no update needed
			_log.info("No update on message property necessary.");
			return;
		}
		// add to property file
		origProps.putAll(newProps);
		// backup property file
		FileUtil.backupFile(messageFilename);
		// save property file
		FileUtil.saveProperty(messageFilename, origProps, "Saved by Hightides for message generation.");
		_log.info("Updated message property file with "+newProps.size()+ " key/value pairs.");
	}
}
