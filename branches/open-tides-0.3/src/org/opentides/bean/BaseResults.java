/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * BaseResults.java
 * Created on Feb 10, 2008, 9:04:37 PM
 */

package org.opentides.bean;

/**
 * Extend this abstract class to implement paging 
 * on search results.
 * @author allanctan
 */
public abstract class BaseResults {
	/** number of matching results */
	private long totalResults;
	/** current page for display */	
	private int currPage;
	/** number of records per page */
	private int pageSize;
	/** number of links to display */
	private int numLinks;
	/** duration of search time in seconds */
	private long searchTime;
	
	protected BaseResults(int pageSize, int numLinks) {
		this.pageSize = pageSize;
		this.numLinks = numLinks;
	}
	
	/**
	 * Computes for starting record number based on current page and page size.
	 * @return int - start record index
	 */
	public int getStartIndex() {
		int start = (currPage-1)*pageSize;
		if (start < 0) {
			// ensure minimum
			start = 0;
		}
		if ((start+pageSize) > totalResults) {
			// ensure maximum
			start = (int) (totalResults / pageSize) * pageSize;
		}
		return start;
	}	
	
	/**
	 * Computes for ending record number based on current page contents
	 * @return int - end record index
	 */
	public int getEndIndex() {
		int endIndex = getStartIndex() + getDisplayedResultsCount() - 1;
		if (endIndex < 0)
			endIndex = 0;
		return endIndex;
	}	
	/**
	 * Total number of pages
	 * @return int - total number of pages
	 */	
	public int getTotalPages() {
		int totalPages = (int) (totalResults / pageSize);
		if ((totalResults % pageSize) != 0)
			totalPages += 1;
		return totalPages;
	}
	/**
	 * Get starting page number
	 * @return int - start page
	 */
	public int getStartPage() {		
		int startPage = currPage-(numLinks/2);
		// check for empty results
		if (getTotalResults()==0)
			return 0;
		// check for maximum
		int totalPages = getTotalPages();
		if ((startPage+(numLinks-1)) > totalPages) {
			// we will exceed maximum....
			startPage = totalPages - (numLinks-1); 
		}
		// check for minimum
		if (startPage < 1)
			startPage = 1;
		return startPage;
	}
	
	/**
	 * Get starting page number
	 * @return int - start page
	 */
	public int getEndPage() {
		int endPage = getStartPage() + (numLinks-1);
		int totalPages = getTotalPages(); 
		if (endPage > totalPages)
			endPage = totalPages;
		return endPage;		
	}
	
	/** abstract methods */	
	public abstract int getDisplayedResultsCount();

	/** Standard getters/setters */	
	protected void setNumLinks(int numLinks) {
		this.numLinks = numLinks;
	}
	public int getNumLinks() {
		return numLinks;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	protected void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}
	public long getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(long searchTime) {
		this.searchTime = searchTime;
	}
}