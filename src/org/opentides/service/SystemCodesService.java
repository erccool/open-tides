/**
 * 
 */
package org.opentides.service;

import org.opentides.bean.SystemCodes;



/**
 * @author allantan
 * 
 */
public interface SystemCodesService extends BaseCrudService<SystemCodes> {
	public Long incrementValue(String key);
}
