/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */

package org.opentides.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Extend this class to implement sortable search results.
 * 
 * @author allanctan
 */
public class SortableResults<T> extends BaseResults {

	public static final String SORT_ASCENDING = "asc";
	public static final String SORT_DESCENDING = "desc";

	/** list containing matching results */
	private List<T> results;
	private String sortDirection;
	private String sortOrder;

	public SortableResults(int pageSize, int numLinks) {
		super(pageSize, numLinks);
		results = new ArrayList<T>();
	}

	public SortableResults(int pageSize, int numLinks, String sortOrder,
			String sortDirection) {
		this(pageSize, numLinks);
		this.sortOrder = sortOrder;
		this.sortDirection = sortDirection;
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
