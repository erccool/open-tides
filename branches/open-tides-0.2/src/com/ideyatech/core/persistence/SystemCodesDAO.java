/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * SystemCodesDAO.java
 * Created on Jan 27, 2008, 4:09:49 PM
 */

package com.ideyatech.core.persistence;

import java.util.List;

import com.ideyatech.core.bean.SystemCategory;
import com.ideyatech.core.bean.SystemCodes;

public interface SystemCodesDAO extends BaseEntityDAO<SystemCodes, Long> {
	
	/**
	 * Return List of SystemCodes by Category.
	 * @param category
	 * @return
	 */
	public List<SystemCodes> getSystemCodesByCategory(SystemCategory category);
	
	/**
	 * Return SystemCode entity by key.
	 * @param key
	 * @return
	 */
	public SystemCodes loadBySystemCodesByKey(String key);
}
