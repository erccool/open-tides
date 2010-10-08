/**
 * 
 */
package org.opentides.persistence.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.opentides.bean.AuditLog;
import org.opentides.bean.Auditable;
import org.opentides.persistence.AuditLogDAO;
import org.opentides.util.AcegiUtil;
import org.opentides.util.HibernateUtil;

/**
 * Logging DAO for audit tracking.
 * @author allantan
 *
 */
public class AuditLogDAOImpl extends BaseEntityDAOJpaImpl<AuditLog, Long> 
		implements AuditLogDAO {	
	
	private static Logger _log = Logger.getLogger(AuditLogDAOImpl.class);
	
    /* (non-Javadoc)
	 * @see com.ideyatech.core.persistence.impl.BaseEntityDAOJpaImpl#appendClauseToExample(com.ideyatech.core.bean.BaseEntity, boolean)
	 */
	@Override
	protected String appendOrderToExample(AuditLog example) {
		return "order by createDate desc";
	}

	public static void logEvent(String message, Auditable entity) { 
		Long userId = entity.getUserId();
		if (userId==null) {
			_log.warn("No userId specified for audit logging on object ["+entity.getClass().getName()
						+ "] for message ["+message+"]. Retrieving user from interceptor.");
			userId = AcegiUtil.getSessionUser().getRealId();
		} else if (userId != AcegiUtil.getSessionUser().getRealId()) {
			_log.debug("============= Hooray! We fixed audit logging.=============");
			_log.debug("userId = " + userId + " interceptor userId = " + AcegiUtil.getSessionUser().getRealId());
		}
		
    	Session tempSession = 
            HibernateUtil.getSessionFactory().openSession(); 
		try { 
			AuditLog record = 
	            new AuditLog(  message, 
	                           entity.getId(), 
	                           entity.getClass(), 
	                           userId ); 
			record.setReference(entity.getReference());
		    tempSession.save(record); 
		    tempSession.flush(); 
		} finally { 
		    tempSession.close(); 
		}
    }
}
