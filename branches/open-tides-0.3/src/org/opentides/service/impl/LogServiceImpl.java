/**
 * 
 */
package org.opentides.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opentides.bean.AuditLog;
import org.opentides.service.LogService;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author allantan
 *
 */
public class LogServiceImpl extends BaseCrudServiceImpl<AuditLog> implements LogService {

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<AuditLog> findLogByReferenceAndClass(String reference,
			List<Class> types) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("reference", reference);
		if (types==null || types.isEmpty())
			return new ArrayList<AuditLog>();
		params.put("entityClass", types);
		return getDao().findByNamedQuery("jpql.audit.findByReferenceAndClass",params);
	}
}
