package org.opentides.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class performs continuous matching of a pattern in a given string.
 * Useful when looping for the records within the search results.
 * 
 * @author allanctan
 */
public class BlockSplitter {
	
	private static Logger _log = Logger.getLogger(BlockSplitter.class);
	
	private String prePattern;
	private String endPattern;

	/**
	 * @param prePattern - starting pattern
	 * @param endPattern - ending pattern
	 */
	public BlockSplitter(String prePattern, String endPattern) {
		super();
		this.prePattern = prePattern;
		this.endPattern = endPattern;
	}

	/**
	 * Performs matching for the declared prePattern and endPattern
	 * @return - the next matching string. null if not found.
	 */
	public List<String> split(String code) {
		int startIndex = 0;
		List<String> ret = new ArrayList<String>();
		if (StringUtil.isEmpty(code))
			return ret;
		while (startIndex < code.length()) {
			int preIndex =  code.indexOf(prePattern, startIndex);
			if ( preIndex != -1 ) { 
				ret.add(code.substring(startIndex, preIndex));
				int endIndex =  code.indexOf(endPattern, preIndex);
				if (endIndex != -1) {
					startIndex = endIndex+endPattern.length();
					ret.add(code.substring(preIndex+prePattern.length(), endIndex));
				} else {
					_log.error("No matching end string ["+prePattern+"] found for ["+endPattern+"]");
				}
			} else {
				// no more match found
				ret.add(code.substring(startIndex));
				break;
			}
		}
		return ret;
	}
}
