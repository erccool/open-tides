package org.opentides.util;

import org.opentides.util.StringUtil;

/**
 * This class performs continuous matching of a pattern in a given string.
 * Useful when looping for the records within the search results.
 * 
 * @author allanctan
 */
public class SimpleMatcher {
	private int startIndex = 0;
	private String toParse;
	private String prePattern;
	private String endPattern;
	/**
	 * 
	 * @param toParse - string where search will be performed
	 * @param prePattern - starting pattern
	 * @param endPattern - ending pattern
	 */
	public SimpleMatcher(String toParse, String prePattern, String endPattern) {
		super();
		this.toParse = toParse;
		this.prePattern = prePattern;
		this.endPattern = endPattern;
	}
	/**
	 * Performs matching for the declared prePattern and endPattern
	 * @return - the next matching string. null if not found.
	 */
	public String next() {
		if (StringUtil.isEmpty(toParse))
			return null;
		int preIndex =  toParse.indexOf(prePattern, startIndex);
		if ( preIndex != -1 ) { 
			int endIndex =  toParse.indexOf(endPattern, preIndex);
			if (endIndex != -1) {
				startIndex = endIndex+endPattern.length();
				return toParse.substring(preIndex+prePattern.length(), endIndex);
			}			
		}
		return null;
	}

}
