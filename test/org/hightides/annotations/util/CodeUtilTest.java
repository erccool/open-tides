package org.hightides.annotations.util;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;


public class CodeUtilTest {

	@Test 
	public void testHashCodes() {
		File testFile = new File("test/resources/hashcode.txt");
		CodeUtil.updateHashCode(testFile, "junit.test");
		Assert.assertEquals("tvtXoq2NluYx6fJlAwk+TA==", CodeUtil.getHashCode("junit.test"));
	}
}
