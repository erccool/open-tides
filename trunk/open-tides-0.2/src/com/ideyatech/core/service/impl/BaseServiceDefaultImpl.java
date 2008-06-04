/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * BaseServiceDefaultImpl.java
 * Created on Jan 8, 2008, 11:14:42 AM
 */
package com.ideyatech.core.service.impl;

import com.ideyatech.core.service.BaseService;

/**
 * @author allanctan
 *
 */
public class BaseServiceDefaultImpl implements BaseService {

	private int recordsPerPage;
	
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	/**
	 * @return the recordsPerPage
	 */
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

}