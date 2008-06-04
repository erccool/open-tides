/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * SearchResults.java
 * Created on Feb 10, 2008, 9:06:37 PM
 */

package com.ideyatech.core.bean;

import java.util.ArrayList;
import java.util.List;

public class SearchResults<T> extends BaseResults {

	/** list containing matching results */
	private List<T> results;
	
	public SearchResults(int pageSize, int numLinks) {
		super(pageSize, numLinks);
		results = new ArrayList<T>();
	}
	public void addResults(T row) {
		results.add(row);		
	}
	public void addResults(List<T> rows) {
		results.addAll(rows);
	}
	public List<T> getResults() {
		return results;
	}	
	@Override
	public int getDisplayedResultsCount() {
		return results.size();
	}	
}
