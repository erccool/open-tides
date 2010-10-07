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

package org.opentides.security;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.vote.AccessDecisionVoter;

/**
 * This class provides a positive vote for user with role "SUPER_USER".
 * 
 * @author allantan
 * 
 */
public class SuperUserVoter implements AccessDecisionVoter {

	public static final String SUPER_USER = "SUPER_USER";

	public boolean supports(ConfigAttribute attrib) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean supports(Class arg0) {
		return true;
	}

	/**
	 * Grant access if user has authentication of "SUPER_USER"
	 */
	public int vote(Authentication authentication, Object arg1,
			ConfigAttributeDefinition config) {
		for (int i = 0; i < authentication.getAuthorities().length; i++) {
			if (SUPER_USER.equals(
					authentication.getAuthorities()[i].getAuthority())) {
				return ACCESS_GRANTED;
			}
		}
		return ACCESS_ABSTAIN;
	}

}