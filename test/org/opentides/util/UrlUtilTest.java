package org.opentides.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class UrlUtilTest {

	@Test
	public void testGetHostname() {
		assertEquals("www.ideyatech.com", UrlUtil.getHostname("http://www.ideyatech.com"));
		assertEquals("www.ideyatech.com", UrlUtil.getHostname("http://www.ideyatech.com/"));
		assertEquals("www.ideyatech.com", UrlUtil.getHostname("http://www.ideyatech.com/test/original"));
		assertEquals("www.ideyatech.com", UrlUtil.getHostname("www.ideyatech.com"));
		assertEquals("www.ideyatech.com", UrlUtil.getHostname("www.ideyatech.com/"));
		assertEquals("www.ideyatech.com", UrlUtil.getHostname("www.ideyatech.com/test/original"));
	}
	
	@Test 
	public void testGetAbsoluteUrl() {
		assertEquals("http://www.google.com/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com", "/images/one.gif"));
		assertEquals("http://www.google.com/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com/images/test.html", "/images/one.gif"));
		assertEquals("http://www.google.com/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com/images/test.html", "one.gif"));
		assertEquals("http://www.google.com/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com", "images/one.gif"));
		assertEquals("http://www.google.com:8080/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com:8080/images/test.html", "/images/one.gif"));
		assertEquals("http://www.google.com:8080/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com:8080/images/test.html", "one.gif"));
		assertEquals("http://www.google.com/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com/", "/images/one.gif"));
		assertEquals("http://www.google.com/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com/", "images/one.gif"));
		assertEquals("http://www.google.com/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com/test.html", "/images/one.gif"));
		assertEquals("http://www.google.com/images/one.gif", UrlUtil.getAbsoluteUrl("http://www.google.com/test.html", "images/one.gif"));
	}

}
