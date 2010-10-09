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
