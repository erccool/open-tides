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
package org.hightides.annotations.factory;

import java.util.Map;

import org.hightides.annotations.processor.Processor;

/**
 * @author allantan
 * 
 */
public abstract class CodeFactory {
	/**
	 * Enumeration of language being supported.
	 * @author allantan
	 */
	public enum Language {
		OPENTIDES, OPENTIDES_JAR, SPRING
	};

	protected Language language;
	
	
	/**
	 * Path of field templates 
	 */
	public static String fieldTemplatePath;
	
	/**
	 *  Map of supported annotations for this code factory
	 */
	@SuppressWarnings("unchecked")
	protected Map<Class, Processor> processorMap;


	/**
	 * Initialization of supported processors.
	 * This method must be implemented by concrete classes to
	 * populate processorMap
	 */
	protected abstract void initializeProcessors();
	
	/**
	 * Retrieves the code factory for the specified language.
	 * @param language
	 * @return
	 */
	public static CodeFactory getFactory(Language language) {
		CodeFactory factory = null;
		if (language==Language.OPENTIDES) {
			factory = new OpentidesFactory();
			fieldTemplatePath = "opentides/jsp/fields/";
		} else if (language==Language.OPENTIDES_JAR) {
			factory = new OpentidesJarFactory();
			fieldTemplatePath = "resources/templates/opentides/jsp/fields/";
		}
		if (factory!=null) {
			factory.initializeProcessors();
			return factory;
		}
		throw new UnsupportedOperationException(
				"No factory available for "
						+ language.toString());
	}
	
	/**
	 * Retrieves the corresponding processor for an annotation
	 * and language. Returns null if no processor has been defined.
	 * @param annotation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Processor getProcessor(Class annotation) {
		return processorMap.get(annotation);
	}
	
	/**
	 * @return the fieldTemplatePath
	 */
	public static String getFieldTemplatePath() {
		return fieldTemplatePath;
	}
}
