package org.opentides.util;

import java.util.List;

import org.junit.Test;

public class BlockSplitterTest {
	
	@Test
	public void testSplitComment() {
		BlockSplitter split = new BlockSplitter("/*","*/");
		String test1 = "Hello /* Comment */ ko to";
		List<String> out1 = split.split(test1);
		List
		out1.containsAll(c)
	}
}
