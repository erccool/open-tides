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
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;
import org.hightides.annotations.util.PackageUtil;

/**
 * This is a generic template processor that retrieves velocity templates and
 * clones the corresponding source codes.
 * 
 * @author allantan
 */
public class JarCloningProcessor extends BaseCloningProcessor implements Processor {
	
	private static Logger _log = Logger.getLogger(JarCloningProcessor.class);
	public static final String propFilename="resources/hightides.properties";
	
	public JarCloningProcessor(String templateFolder, String outputFolder) {
		super(templateFolder, outputFolder);
	}

	public void recurseTemplates(File path, Map<String, Object> params) {
		try {
			Properties props = new Properties();
			props.load(getClass().getClassLoader().getResourceAsStream(propFilename));
			String jarPath = props.getProperty("jar.resource.loader.path");			
			JarFile jarFile = new JarFile(PackageUtil.getJarFile(jarPath));
			
			for (Enumeration<JarEntry> e = jarFile.entries() ; e.hasMoreElements() ;) {
				JarEntry jarEntry =e.nextElement();
				if(jarEntry.getName().startsWith(path.getPath().replace("\\", "/"))){		
					if(jarEntry.getName().endsWith(".vm")&&!jarEntry.getName().endsWith("_.vm")){
						ClassLoader cl = this.getClass().getClassLoader();						
						String fileSelectPath = PackageUtil.getTemplateInJar(cl,jarEntry).replaceAll("\\\\", "/");
						clone(new File(fileSelectPath), params);					
					}					
				}
		     }
		} catch (IOException e) {
			_log.error("Cannot read vm files.",e);
		}	
	}
	
	/**
	 * Returns the location of the template relative to base path specified 
	 * in velocity.
	 * @param absTemplate - Absolute path 
	 * @return
	 */
	public final String getTemplateForMerge(File template) {
		return template.toString();
	}

}