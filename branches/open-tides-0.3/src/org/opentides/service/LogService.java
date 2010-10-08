/**
 * 
 */
package org.opentides.service;

import java.util.List;

import org.opentides.bean.AuditLog;


/**
 * @author allantan
 *
 */
public interface LogService extends BaseCrudService<AuditLog> {

	@SuppressWarnings("unchecked")
	public List<AuditLog> findLogByReferenceAndClass(String reference, List<Class> types);
}
