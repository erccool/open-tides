/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * StringUtilTest.java
 * Created on Feb 10, 2008, 1:27:19 PM
 */
package org.hightides;

import junit.framework.TestCase;

import org.hightides.annotations.factory.CodeFactory.Language;




/**
 * @author allanctan
 *
 */
public class HighTidesTest extends TestCase {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HighTides ht = new HighTides();
		ht.generate(Language.OPENTIDES);
	}

	public void testSomething() {
		assertTrue(true);
	}
    
}
