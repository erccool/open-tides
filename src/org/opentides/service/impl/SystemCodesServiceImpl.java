/**
 * 
 */
package org.opentides.service.impl;


import org.opentides.bean.SystemCodes;
import org.opentides.persistence.SystemCodesDAO;
import org.opentides.service.SystemCodesService;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author allantan
 * 
 */
public class SystemCodesServiceImpl extends BaseCrudServiceImpl<SystemCodes>
		implements SystemCodesService {
	/**
	 * Increments value of numberValue by used. Useful for custom generation of
	 * id.
	 */
	@Transactional
	public Long incrementValue(String key) {
		SystemCodesDAO dao = (SystemCodesDAO) getDao();
		return dao.incrementValue(key);
	}
}
