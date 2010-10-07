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

import java.io.File;
import java.util.Map;

import org.hightides.annotations.util.PackageUtil;

/**
 * This is a generic template processor that retrieves velocity templates and
 * clones the corresponding source codes.
 * 
 * @author allantan
 */
public class FileCloningProcessor extends BaseCloningProcessor implements Processor {

	public FileCloningProcessor(String templateFolder, String outputFolder) {
		super(templateFolder, outputFolder);
	}
	
	/**
	 * Override execute method because we don't want application 
	 * config and message properties to be generated.
	 */
	public void execute(Map<String, Object> params) {
		// recurse all the subdirectories in the templatePath
		File filePath = getPackager().getTemplateFolder();
		recurseTemplates(filePath, params);
	}

	/**
	 * Recurse all the directories under templatePath and clone all vm files.
	 */
	@Override
	public void recurseTemplates(File path, Map<String, Object> params) {
		if (path.isDirectory()) {
			// let's recurse subdirectories
			String[] children = path.list();
			for (int i = 0; i < children.length; i++) {
				recurseTemplates(new File(path, children[i]), params);
			}
		} else {
			// this is a file, let's clone.
			if (path.isFile() && path.getName().endsWith(".vm") && !path.getName().endsWith("_.vm"))
				clone(path, params);
		}
	}

	/**
	 * Returns the location of the template relative to base path specified 
	 * in velocity.
	 * @param absTemplate - Absolute path 
	 * @return
	 */
	public final String getTemplateForMerge(File template) {
		// File pointer to base path
		File base = new File(PackageUtil.getBaseTemplatePath());
		// Pass only filename relative to templatePath since
		// baseTemplatePath is already defined in Velocity.properties
		return template.getAbsolutePath().replace(base.getAbsolutePath(), "");
	}
}