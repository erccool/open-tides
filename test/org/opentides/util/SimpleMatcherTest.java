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

import junit.framework.Assert;

import org.junit.Test;


/**
 * @author allantan
 *
 */
public class SimpleMatcherTest {
	String test ="<b>one</b>sdfs<b></b> df <b>two</b> <b>";

	/**
	 * Test method for {@link com.zuula.core.SimpleMatcher#next()}.
	 */
	@Test
	public final void testNext() {
		SimpleMatcher sm = new SimpleMatcher(test,"<b>","</b>");
		Assert.assertEquals("one", sm.next());
		Assert.assertEquals("", sm.next());
		Assert.assertEquals("two", sm.next());
		Assert.assertNull("end of string", sm.next());
	}
	
	/**
	 * Test method for {@link com.zuula.core.SimpleMatcher#next()}.
	 */
	@Test
	public final void testNextNoMatch() {
		SimpleMatcher sm = new SimpleMatcher(test,"<b1>","</b>");
		Assert.assertNull("not found", sm.next());
	}
}
