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

package org.opentides.util;

import org.opentides.util.StringUtil;

/**
 * This class performs continuous matching of a pattern in a given string.
 * Useful when looping for the records within the search results.
 * 
 * @author allanctan
 */
public class SimpleMatcher {
	private int startIndex = 0;
	private String toParse;
	private String prePattern;
	private String endPattern;
	/**
	 * 
	 * @param toParse - string where search will be performed
	 * @param prePattern - starting pattern
	 * @param endPattern - ending pattern
	 */
	public SimpleMatcher(String toParse, String prePattern, String endPattern) {
		super();
		this.toParse = toParse;
		this.prePattern = prePattern;
		this.endPattern = endPattern;
	}
	/**
	 * Performs matching for the declared prePattern and endPattern
	 * @return - the next matching string. null if not found.
	 */
	public String next() {
		if (StringUtil.isEmpty(toParse))
			return null;
		int preIndex =  toParse.indexOf(prePattern, startIndex);
		if ( preIndex != -1 ) { 
			int endIndex =  toParse.indexOf(endPattern, preIndex);
			if (endIndex != -1) {
				startIndex = endIndex+endPattern.length();
				return toParse.substring(preIndex+prePattern.length(), endIndex);
			}			
		}
		return null;
	}

}
