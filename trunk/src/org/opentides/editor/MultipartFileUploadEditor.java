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
package org.opentides.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * PropertyEditor for handling SystemCodes.
 * Used for mapping drop-down objects to forms values.
 * @author allantan
 */
public class MultipartFileUploadEditor extends PropertyEditorSupport {
	
	@Override
	public String getAsText() {
		if (getValue() != null) {
			CommonsMultipartFile multipart = (CommonsMultipartFile) getValue();
			return multipart.getOriginalFilename();
		}
		return "";
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// cannot convert to Multipart
		setValue(null);
	}
}
