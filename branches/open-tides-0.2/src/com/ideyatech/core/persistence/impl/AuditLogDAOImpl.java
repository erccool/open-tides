/**
 * 
 */
package com.ideyatech.core.persistence.impl;

import org.hibernate.Session;

import com.ideyatech.core.bean.AuditLog;
import com.ideyatech.core.bean.Auditable;
import com.ideyatech.core.bean.user.SessionUser;
import com.ideyatech.core.persistence.AuditLogDAO;
import com.ideyatech.core.util.AcegiUtil;
import com.ideyatech.core.util.HibernateUtil;

/**
 * Logging DAO for audit tracking.
 * @author allantan
 *
 */
public class AuditLogDAOImpl extends BaseEntityDAOJpaImpl<AuditLog, Long> 
		implements AuditLogDAO {
	
	
    /* (non-Javadoc)
	 * @see com.ideyatech.core.persistence.impl.BaseEntityDAOJpaImpl#appendClauseToExample(com.ideyatech.core.bean.BaseEntity, boolean)
	 */
	@Override
	protected String appendOrderToExample(AuditLog example) {
		return "order by createDate DESC";
	}

	public static void logEvent(String message, Auditable entity) { 
    	SessionUser user = AcegiUtil.getSessionUser();
    	Session tempSession = 
            HibernateUtil.getSessionFactory().openSession(); 
		try { 
			AuditLog record = 
	            new AuditLog(  message, 
	                           entity.getId(), 
	                           entity.getClass(), 
	                           user.getId() ); 
		    tempSession.save(record); 
		    tempSession.flush(); 
		} finally { 
		    tempSession.close(); 
		}
    }
}
