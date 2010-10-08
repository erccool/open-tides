/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * StringUtilTest.java
 * Created on Feb 10, 2008, 1:27:19 PM
 */
package com.ideyatech.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.opentides.util.StringUtil;


/**
 * @author allanctan
 *
 */
public class StringUtilTest {
	private Date jan_01_1901;
	private Date mar_16_1989;
	private Date jun_30_1932;
	private Date mar_01_1999;
	private Date dec_31_2009;
	private Date dec_01_2009;	
	private Date dec_01_2045;
	private Date jan_02_2001;
    
    @Before
	public void setUp() throws Exception {
    	SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyyMMdd");
    	jan_01_1901 = dtFormatter.parse("19010101");
    	mar_16_1989 = dtFormatter.parse("19890316");
    	jun_30_1932 = dtFormatter.parse("19320630");
    	mar_01_1999 = dtFormatter.parse("19990301");
    	dec_31_2009 = dtFormatter.parse("20091231");
    	dec_01_2009 = dtFormatter.parse("20091201");
    	dec_01_2045 = dtFormatter.parse("20451201");
    	jan_02_2001 = dtFormatter.parse("20010102");
	}
    
    @Test
    public void testStringToDate() throws ParseException{
    	Assert.assertEquals(jan_01_1901, StringUtil.stringToDate("01/01/1901", "MM/dd/yyyy"));
    }
    
    @Test
    public void testStringToDateException() {
    	try {
    		Assert.assertEquals(jan_01_1901, StringUtil.stringToDate("la/2345/11", "MM/dd/yyyy"));
	    	Assert.fail("Exception not thrown on invalid date.[   ]"); 
    	} catch (ParseException pex) {
    	}
    }

    @Test
    public void testDateToString() throws ParseException{
    	Assert.assertEquals("12/31/2009", StringUtil.dateToString(dec_31_2009, "MM/dd/yyyy"));
    	Assert.assertEquals("", StringUtil.dateToString(null, "MM/dd/yyyy"));
    }
    
    @Test
    public void testConvertShortDate() throws ParseException{
    	Assert.assertEquals(mar_16_1989,StringUtil.convertShortDate("19890316"));
    	Assert.assertEquals(jun_30_1932,StringUtil.convertShortDate("19320630"));
    	Assert.assertEquals("19320630",StringUtil.convertShortDate(jun_30_1932));
    	Assert.assertEquals("19890316",StringUtil.convertShortDate(mar_16_1989));
    }

    @Test
    public void testConvertShortDateException () {
    	try {
    		StringUtil.convertShortDate("");
    		Assert.fail("Exception not thrown on invalid date.[]"); 
    	} catch (ParseException pex) {
    	}
    	try {
    		String nul = null;
    		StringUtil.convertShortDate(nul);
    		Assert.fail("Exception not thrown on invalid date.[null]"); 
    	} catch (ParseException pex) {
    	}    	
    }
    
    @Test
    public void testConvertFlexibleDate() throws ParseException {
    	Assert.assertEquals(mar_16_1989,StringUtil.convertFlexibleDate("03-16-1989"));
    	Assert.assertEquals(mar_16_1989,StringUtil.convertFlexibleDate("03/16/1989"));
    	Assert.assertEquals(jan_01_1901,StringUtil.convertFlexibleDate("1901"));
    	Assert.assertEquals(dec_01_2009,StringUtil.convertFlexibleDate("December 2009"));
    	Assert.assertEquals(jun_30_1932,StringUtil.convertFlexibleDate("June 30, 1932"));
    	Assert.assertEquals(jun_30_1932,StringUtil.convertFlexibleDate("June 30 1932"));
    	Assert.assertEquals(dec_01_2045,StringUtil.convertFlexibleDate("12/2045"));
    	Assert.assertEquals(mar_01_1999,StringUtil.convertFlexibleDate("03-1999"));
    	Assert.assertEquals(mar_01_1999,StringUtil.convertFlexibleDate("1999-03-01"));
    	Assert.assertEquals(jan_02_2001,StringUtil.convertFlexibleDate("2001-01-02"));
    }
    
    @Test
    public void testConvertFlexibleDateException() throws ParseException {
    	try {
    		StringUtil.convertFlexibleDate("");
    		Assert.fail("Exception not thrown on invalid date.[]"); 
    	} catch (ParseException pex) {
    	}
    	try {
    		StringUtil.convertFlexibleDate("unknown");
    		Assert.fail("Exception not thrown on invalid date.[unknown]"); 
    	} catch (ParseException pex) {
    	}    	
    	try {
    		StringUtil.convertFlexibleDate(null);
    		Assert.fail("Exception not thrown on invalid date.[null]"); 
    	} catch (ParseException pex) {
    	}    	
    }
    
    @Test
    public void testNullableDates() {
    	Date d1 = new Date();
    	Date d2 = new Date(d1.getTime()+14675467);
    	Assert.assertTrue(StringUtil.compareNullableDates(d1, d1));
    	Assert.assertTrue(StringUtil.compareNullableDates(d2, d2));
    	Assert.assertTrue(StringUtil.compareNullableDates(null, null));
    	Assert.assertFalse(StringUtil.compareNullableDates(d1, d2));
    	Assert.assertFalse(StringUtil.compareNullableDates(null, d2));
    	Assert.assertFalse(StringUtil.compareNullableDates(d1, null));    	
    }
    
    @Test
    public void testEncryptDecrypt() {
		String cipher = StringUtil.encrypt("allan7@test.com");
		Assert.assertEquals("allan7@test.com",StringUtil.decrypt(cipher));
		cipher = StringUtil.encrypt("wqwer12345admin@ideyatech.com");
		Assert.assertEquals("wqwer12345admin@ideyatech.com",StringUtil.decrypt(cipher));
    }
    
}
