/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * StringUtilTest.java
 * Created on Feb 10, 2008, 1:27:19 PM
 */
package com.ideyatech.util;


import junit.framework.Assert;

import org.opentides.bean.SystemCodes;
import org.opentides.bean.user.UserCredential;

import org.junit.Test; 
import org.opentides.util.CrudUtil;

import org.opentides.InvalidImplementationException;


import com.ideyatech.core.bean.UserCriteria;

/**
 * @author allanctan
 *
 */
public class CrudUtilTest {
	
	@Test
	public void testBuildCreateMessage() {
		SystemCodes sc = new SystemCodes("category","key","value");
		String expected =   "Added SystemCodes key:key - key=key and value=value " +
							"and category=category";
		Assert.assertEquals(expected,
				CrudUtil.buildCreateMessage(sc));
	}
	 
	@Test
	public void testBuildUpdateMessage() {
		SystemCodes oldsc = new SystemCodes("categoryold","keyold","value");
		SystemCodes newsc = new SystemCodes("categorynew","keynew","value");
		
		String expected =   "Changed SystemCodes key:keyold - key from 'keyold' to 'keynew' " +
							"and category from 'categoryold' to 'categorynew'";
		Assert.assertEquals(expected,
				CrudUtil.buildUpdateMessage(oldsc, newsc));
	}

    @Test
    public void testGetPropertyName() {
    	Assert.assertEquals("name", CrudUtil.getPropertyName("getName"));
    	Assert.assertEquals("name", CrudUtil.getPropertyName("setName"));
    	Assert.assertNull(CrudUtil.getPropertyName("garbage"));
    	Assert.assertNull(CrudUtil.getPropertyName(""));
    	Assert.assertNull(CrudUtil.getPropertyName("get"));
       	Assert.assertNull(CrudUtil.getPropertyName("set"));
    	Assert.assertEquals("sampleUnit",CrudUtil.getPropertyName("getsampleUnit"));
    	Assert.assertEquals("sampleUnit",CrudUtil.getPropertyName("getSampleUnit"));    	
    }
    
    @Test
    public void testGetGetterMethodName() {
    	Assert.assertEquals("getName", CrudUtil.getGetterMethodName("name"));
    	Assert.assertEquals("getA",CrudUtil.getGetterMethodName("A"));
    	Assert.assertEquals("getA",CrudUtil.getGetterMethodName("a"));
    	Assert.assertEquals("getAb",CrudUtil.getGetterMethodName("Ab"));    	
    	Assert.assertNull(CrudUtil.getPropertyName(""));
       	Assert.assertNull(CrudUtil.getPropertyName(null));
    }
    
    @Test 
    public void testBuildJpaQueryString() {
    	SystemCodes sc = new SystemCodes();
    	Assert.assertEquals("", CrudUtil.buildJpaQueryString(sc, true));
    	sc.setValue("");
    	Assert.assertEquals("", CrudUtil.buildJpaQueryString(sc, true));
    	
    	sc.setKey("PH");
    	Assert.assertEquals(" where key = 'PH'", CrudUtil.buildJpaQueryString(sc, true));
    	Assert.assertEquals(" where key like '%PH%'", CrudUtil.buildJpaQueryString(sc, false));
 
    	sc.setValue("Philippines");
       	Assert.assertEquals(" where key = 'PH' and value = 'Philippines'",
       						CrudUtil.buildJpaQueryString(sc, true));
       	Assert.assertEquals(" where key like '%PH%' and value like '%Philippines%'",
       						CrudUtil.buildJpaQueryString(sc, false));
       	
//       	Category cat = new Category();
//       	cat.setId(12l);
//       	sc.setCategory(cat);
//       	Assert.assertEquals(" where key = 'PH' and value = 'Philippines' and category.id = 12",
//       						CrudUtil.buildJpaQueryString(sc, true));
//       	Assert.assertEquals(" where key like '%PH%' and value like '%Philippines%' and category.id = 12",
//       						CrudUtil.buildJpaQueryString(sc, false));
    }
    
    /**
     * Test for inner class
     */
    @Test 
    public void testBuildJpaQueryString2() {
    	UserCriteria user = new UserCriteria();
    	UserCredential cred = new UserCredential();
    	user.setFirstName("Test");
      	cred.setUsername("testname");
    	cred.setEnabled(true);
    	user.setCredential(cred);

    	Assert.assertEquals(" where firstName like '%Test%' and credential.username like '%testname%' and credential.enabled = 'true'",
					CrudUtil.buildJpaQueryString(user, false));

    	Assert.assertEquals(" where firstName = 'Test' and credential.username = 'testname' and credential.enabled = 'true'",
					CrudUtil.buildJpaQueryString(user, true));

    }
    
    /**
     * Test for numeric types
     */
    @Test 
    public void testBuildJpaQueryString3() {
    	UserCriteria user = new UserCriteria();
    	UserCredential cred = new UserCredential();
    	user.setFirstName("Test");
    	cred.setId(123l);
    	cred.setEnabled(null);
    	user.setCredential(cred);

    	Assert.assertEquals(" where firstName like '%Test%' and credential.id = 123",
					CrudUtil.buildJpaQueryString(user, false));

    	Assert.assertEquals(" where firstName = 'Test' and credential.id = 123",
					CrudUtil.buildJpaQueryString(user, true));

    }
    @Test 
    public void testRetrieveObjectValue() {
    	UserCriteria user = new UserCriteria();
    	UserCredential cred = new UserCredential();
    	user.setFirstName("Test");
       	user.setEmailAddress("admin@ideyatech.com");
      	cred.setUsername("testname");
    	cred.setPassword("password");
    	cred.setId(123l);
    	cred.setEnabled(true);
    	user.setCredential(cred);
    	Assert.assertEquals("Test", CrudUtil.retrieveObjectValue(user, "firstName"));
    	Assert.assertEquals("admin@ideyatech.com", CrudUtil.retrieveObjectValue(user, "emailAddress"));
    	Assert.assertEquals("testname", CrudUtil.retrieveObjectValue(user, "credential.username"));
    	Assert.assertEquals("password", CrudUtil.retrieveObjectValue(user, "credential.password"));
    	Assert.assertEquals(123l,CrudUtil.retrieveObjectValue(user, "credential.id"));
    	try {
    		Assert.assertEquals(null,CrudUtil.retrieveObjectValue(user, "garbage"));
    		Assert.fail("No exception thrown on invalid property [garbage]");
    	} catch (InvalidImplementationException iie) {
    		
    	}
    	try {
    		Assert.assertEquals(null,CrudUtil.retrieveObjectValue(user, "credential.garbage"));
    		Assert.fail("No exception thrown on invalid property [credential.garbage]");
    	} catch (InvalidImplementationException iie) {
    		
    	}
    }

    @Test 
    public void testRetrieveObjectType() {
    	UserCriteria user = new UserCriteria();
    	UserCredential cred = new UserCredential();
    	user.setFirstName("Test");
       	user.setEmailAddress("admin@ideyatech.com");
      	cred.setUsername("testname");
    	cred.setPassword("password");
    	cred.setId(123l);
    	cred.setEnabled(true);
    	user.setCredential(cred);
    	Assert.assertEquals(String.class, CrudUtil.retrieveObjectType(user, "firstName"));
    	Assert.assertEquals(String.class, CrudUtil.retrieveObjectType(user, "credential.username"));
    	Assert.assertEquals(Long.class, CrudUtil.retrieveObjectType(user, "credential.id"));
    	try {
    		Assert.assertEquals(null,CrudUtil.retrieveObjectType(user, "garbage"));
    		Assert.fail("No exception thrown on invalid property [garbage]");
    	} catch (InvalidImplementationException iie) {
    		
    	}
    	try {
    		Assert.assertEquals(null,CrudUtil.retrieveObjectType(user, "credential.garbage"));
    		Assert.fail("No exception thrown on invalid property [credential.garbage]");
    	} catch (InvalidImplementationException iie) {
    		
    	}   	
    }

    @Test 
    public void testReplaceSQLParameters() {
    	UserCriteria user = new UserCriteria();
    	UserCredential cred = new UserCredential();
    	user.setFirstName("Test");
       	user.setEmailAddress("admin@ideyatech.com");
      	cred.setUsername("testname");
    	cred.setPassword("password");
    	cred.setId(123l);
    	cred.setEnabled(true);
    	user.setCredential(cred);
    	Assert.assertEquals("firstName='Test' and credential.id=123", 
    			CrudUtil.replaceSQLParameters("firstName=:firstName and credential.id=:credential.id", user));
    }
    
    @Test
    public void testGetReadableName() {
    	Assert.assertEquals(" System Codes", CrudUtil.getReadableName("com.ideyatech.core.bean.SystemCodes"));
    	Assert.assertEquals("", CrudUtil.getReadableName("com.core.bean.SystemCodes"));
    }
}

