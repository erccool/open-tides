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

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

/**
 * @author allantan
 *
 */
public class ValidatorUtilTest {

	/**
	 * Test method for {@link org.opentides.util.ValidatorUtil#isEmail(java.lang.String)}.
	 */
	@Test
	public void testIsEmail() {
		Assert.assertTrue(ValidatorUtil.isEmail("allan@ideyatech.com"));
		Assert.assertTrue(ValidatorUtil.isEmail("ab.test@ic.com"));
		Assert.assertTrue(ValidatorUtil.isEmail("ab.test@bing-gom.com"));
		Assert.assertTrue(ValidatorUtil.isEmail("a@b.com"));
		Assert.assertFalse(ValidatorUtil.isEmail("com"));
		Assert.assertFalse(ValidatorUtil.isEmail("a@boo"));
	}

	/**
	 * Test method for {@link org.opentides.util.ValidatorUtil#isPhoneNumber(java.lang.String)}.
	 */
	@Test
	public void testIsPhoneNumber() {
		Assert.assertTrue(ValidatorUtil.isPhoneNumber("120-234-1234"));
		Assert.assertTrue(ValidatorUtil.isPhoneNumber("12"));
		Assert.assertTrue(ValidatorUtil.isPhoneNumber("123-456"));

		Assert.assertFalse(ValidatorUtil.isPhoneNumber("a1234-3"));
		Assert.assertFalse(ValidatorUtil.isPhoneNumber("0.12.4556"));
	}


	/**
	 * Test method for {@link org.opentides.util.ValidatorUtil#isNumeric(java.lang.String)}.
	 */
	@Test
	public void testIsNumeric() {
		Assert.assertTrue(ValidatorUtil.isNumeric("12345678"));
		Assert.assertTrue(ValidatorUtil.isNumeric("12"));
		Assert.assertTrue(ValidatorUtil.isNumeric("0123"));

		Assert.assertFalse(ValidatorUtil.isNumeric("a1234-3"));
		Assert.assertFalse(ValidatorUtil.isNumeric("0.12.4556"));
	}

	/**
	 * Test method for {@link org.opentides.util.ValidatorUtil#isValidDateRange(java.util.Date, java.util.Date, java.lang.String)}.
	 */
	@Test
	public void testIsValidDateRange() {
	}

}
