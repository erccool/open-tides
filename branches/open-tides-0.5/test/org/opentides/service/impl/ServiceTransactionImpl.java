/**
 * 
 */
package org.opentides.service.impl;


import org.opentides.bean.SystemCodes;
import org.opentides.service.ServiceTransaction;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author allantan
 * 
 */
public class ServiceTransactionImpl extends BaseCrudServiceImpl<SystemCodes>
	implements ServiceTransaction {

	@Transactional
	public void saveWithException(SystemCodes obj) {
		getDao().saveEntityModel(obj);
		throw new RuntimeException("Forced runtime exception for unit test.");
	}
}
