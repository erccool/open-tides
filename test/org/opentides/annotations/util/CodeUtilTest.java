package org.opentides.annotations.util;

import org.hightides.annotations.util.CodeUtil;
import org.junit.Test;
import org.opentides.util.FileUtil;

public class CodeUtilTest {
	
	@Test
	public void testParseJavaCode() {
		String source = FileUtil.readFile("resources/java/Test1.vm");
		String codelist[] = CodeUtil.parseJavaSimple(source);
		for (String code:codelist) {
			System.out.println(code);
		}
	}
}
