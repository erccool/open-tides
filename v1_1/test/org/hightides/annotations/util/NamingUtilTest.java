package org.hightides.annotations.util;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;


public class NamingUtilTest extends TestCase{

	@Test 
	public void testToElementName() {
		Assert.assertEquals("test", NamingUtil.toElementName("Test"));
		Assert.assertEquals("test", NamingUtil.toElementName("test"));
		Assert.assertEquals("test-codes", NamingUtil.toElementName("TestCodes"));
		Assert.assertEquals("test-codes", NamingUtil.toElementName("testCodes"));
		Assert.assertEquals("test-codes-form5", NamingUtil.toElementName("TestCodesForm5"));
		Assert.assertEquals("", NamingUtil.toElementName(""));
	}
	
	@Test
	public void testToLabel() {
		Assert.assertEquals("Test", NamingUtil.toLabel("Test"));
		Assert.assertEquals("Test", NamingUtil.toLabel("test"));
		Assert.assertEquals("Test Codes", NamingUtil.toLabel("TestCodes"));
		Assert.assertEquals("Test Codes", NamingUtil.toLabel("testCodes"));
		Assert.assertEquals("Test Codes Form Z", NamingUtil.toLabel("TestCodesFormZ"));
		Assert.assertEquals("", NamingUtil.toLabel(""));
	}
	
	@Test
	public void testToGetterName(){
		assertEquals("getFirstName", NamingUtil.toGetterName("firstName"));
	}
	
	@Test
	public void testToSettername(){
		assertEquals("setFirstName", NamingUtil.toSetterName("firstName"));
	}
	
	@Test
	public void testToAttributeName(){
		assertEquals("", NamingUtil.toAttributeName(""));
	}
}
