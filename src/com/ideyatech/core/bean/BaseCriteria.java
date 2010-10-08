/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * BaseWSProxy.java
 * Created on Feb 7, 2008, 9:54:37 PM
 */

package com.ideyatech.core.bean;

import java.util.List;


/**
 * This is the interface for implementing search criteria.
 * When creating a search page, implement this interface to integrate
 * with BaseCrudController for standard implementation of search.
 * 
 * @author allanctan
 *
 */
public interface BaseCriteria {
	public List<String> getSearchProperties();
}
