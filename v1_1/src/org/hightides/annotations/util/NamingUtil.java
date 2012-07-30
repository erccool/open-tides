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

package org.hightides.annotations.util;

import org.opentides.util.StringUtil;

/**
 * @author allantan
 *
 */
public class NamingUtil {

	/**
	 * Ensures that name starts with big letter.
	 * For example:
	 * input - fieldName; output - Field Name
	 * @param name
	 * @return
	 */
	public static String toLabel(String name) {

		if (StringUtil.isEmpty(name))
			return "";

		StringBuffer buffer = new StringBuffer();
		for (int i=0; i<name.length();i++) {
			if (i==0) {
				// capitalize first character
				buffer.append(name.substring(0, 1).toUpperCase());
			} else {
				// append spaces between words
				if (name.charAt(i) >= 'A' && name.charAt(i)<= 'Z') 
					buffer.append(" ");
				buffer.append(name.charAt(i));
			}
		}
		return buffer.toString();
	}
	
	/**
	 * Create a getter name. (e.g. getSystemCodes)
	 * @param name
	 * @return
	 */
	public static String toGetterName(String name) {
		return "get" + name.substring(0,1).toUpperCase() + name.substring(1);
	}
	
	/**
	 * Create a getter name. (e.g. setSystemCodes)
	 * @param name
	 * @return
	 */
	public static String toSetterName(String name) {
		return "set" + name.substring(0,1).toUpperCase() + name.substring(1);
	}

	/**
	 * Ensures that name starts with small letter.
	 * @param name
	 * @return
	 */
	public static String toAttributeName(String name) {

		if (StringUtil.isEmpty(name))
			return "";
		
		if (name.length()>=2) {
			return name.substring(0,1).toLowerCase() + name.substring(1);
		} else
			return name.toLowerCase();
		
		// how about names with several words?
	}
	
	/**
	 * Ensures that name are in html element format (e.g. system-codes)
	 * @param name
	 * @return
	 */
	public static String toElementName(String name) {
		if (StringUtil.isEmpty(name))
			return "";
		StringBuffer buffer = new StringBuffer();
		int startIndex = 0;
		for (int i=0; i<name.length();i++) {
			if (name.charAt(i) >= 'A' && name.charAt(i)<= 'Z') {
				if (startIndex!=0)
					buffer.append("-");
				buffer.append(name.substring(startIndex, i).toLowerCase());
				startIndex = i;				
			}
		}
		if (startIndex<name.length()) {
			if (startIndex!=0)
				buffer.append("-");
			buffer.append(name.substring(startIndex).toLowerCase());
		}
		return buffer.toString();
	}
}
