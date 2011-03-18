package org.opentides.util;

import org.junit.Assert;
import org.junit.Test;

public class BlockSplitterTest {
	
	@Test
	public void testSplitComment() {
		BlockSplitter split = new BlockSplitter("/*","*/");
		String test1 = "Hello /* Comment */ ko to";
		String[] expected1 = {"Hello ", " Comment ", " ko to"};
		Assert.assertArrayEquals(expected1, split.split(test1).toArray());
        String test2 = "Hello /* Comment */";
        String[] expected2 = {"Hello ", " Comment "};
        Assert.assertArrayEquals(expected2, split.split(test2).toArray());
        String test3 = "/* Comment */ ko to";
        String[] expected3 = {""," Comment ", " ko to"};
        Assert.assertArrayEquals(expected3, split.split(test3).toArray());
	}
	
	@Test
    public void testNoSplit() {
        BlockSplitter split = new BlockSplitter("/*","*/");
        String test1 = "Hello /* Comment";
        String[] expected1 = {"Hello /* Comment"};
        Assert.assertArrayEquals(expected1, split.split(test1).toArray());
        String test2 = "";
        String[] expected2 = {};
        Assert.assertArrayEquals(expected2, split.split(test2).toArray());
        
    }	
}
