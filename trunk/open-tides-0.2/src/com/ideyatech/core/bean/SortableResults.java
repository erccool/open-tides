/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * SortableResults.java
 * Created on Feb 10, 2008, 9:04:37 PM
 */
package com.ideyatech.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Extend this class to implement sortable search results.
 * @author allanctan
 */
public class SortableResults<T> extends BaseResults {
	
	public static final String SORT_ASCENDING="asc";
	public static final String SORT_DESCENDING="desc";
	
	/** list containing matching results */
	private List<T> results;
	private String sortDirection;
	private String sortOrder;
	
	public SortableResults(int pageSize, int numLinks) {
		super(pageSize, numLinks);
		results = new ArrayList<T>();
	}
	
	public SortableResults(int pageSize, int numLinks, 
			String sortOrder, String sortDirection) {
		this(pageSize, numLinks);
		this.sortOrder=sortOrder;
		this.sortDirection=sortDirection;
	}
	
	@Override
	public int getDisplayedResultsCount() {
		return results.size();
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public List<T> getResults() {
		return results;
	}	
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
