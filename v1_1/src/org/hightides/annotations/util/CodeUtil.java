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

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.opentides.util.FileUtil;
import org.opentides.util.StringUtil;


/**
 * Utilities to process / manipulate source codes.
 * @author allantan
 */
public class CodeUtil {
	
	private static Logger _log = Logger.getLogger(CodeUtil.class);
	public static final String hashFile  ="./.hashcodes";
	private static Properties  hashProps = null;
	/**
	 * Extracts list of code segments for Java
	 * @param code
	 * @return
	 */
//	public static final String[] parseJavaSimple(String code) {
//		return code.split(javaSplitter);
//	}
	
	/**
	 * Updates the hashcode for the given filename.
	 * Used for tracking changes on generated source codes
	 */
	public static final void updateHashCode(File file, String key) {
		if (hashProps==null) hashProps = FileUtil.readProperty(hashFile);
		String code = FileUtil.readFile(file);
		String hash = StringUtil.hashSourceCode(code);
		// check if hashCode exist and remains the same
		String origHash = CodeUtil.getHashCode(key);
		if (hash.equals(origHash)) {
			_log.debug("Hashcode for "+key+" unchanged. No updates made.");
		} else {
			// update only when hash is different
			hashProps.put(key, hash);
			FileUtil.saveProperty(hashFile, hashProps, "Open-tides hash codes for code generation.");
		} 
	}
	
	/**
	 * Retrieves the stored hashcode for a given filename
	 * @param filename
	 * @return
	 */
	public static final String getHashCode(String key) {
		if (hashProps==null) hashProps = FileUtil.readProperty(hashFile);
		return hashProps.getProperty(key);
	}
}
