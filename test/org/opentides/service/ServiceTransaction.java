package org.opentides.service;

import org.opentides.bean.SystemCodes;


/**
 * @author allantan
 * 
 */
public interface ServiceTransaction extends BaseCrudService<SystemCodes> {
	public void saveWithException(SystemCodes obj);
}