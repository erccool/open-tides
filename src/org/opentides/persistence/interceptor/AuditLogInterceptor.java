/**
 * 
 */
package org.opentides.persistence.interceptor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.opentides.util.CrudUtil;
import org.opentides.util.HibernateUtil;
import org.opentides.util.StringUtil;


import org.opentides.bean.Auditable;
import org.opentides.persistence.impl.AuditLogDAOImpl;
import org.opentides.persistence.interceptor.AuditLogInterceptor;

/**
 * This is the interceptor responsible in tracking audit trails.
 * Source is patterned after the book "Java Persistence with Hibernate" - page 546 onwards
 * and merged with http://www.hibernate.org/318.html
 * 
 * @author allantan
 */
public class AuditLogInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 582549003254963262L;
	
	private static Logger _log = Logger.getLogger(AuditLogInterceptor.class);
	
    private Set<Auditable> inserts    = new HashSet<Auditable>(); 
    private Set<Auditable> updates    = new HashSet<Auditable>(); 
    private Set<Auditable> deletes    = new HashSet<Auditable>(); 
    private Map<Long, Auditable> oldies     = new HashMap<Long, Auditable>(); 
    
    @Override
    public boolean onSave(Object entity, 
                          Serializable id, 
                          Object[] state, 
                          String[] propertyNames, 
                          Type[] types) 
            throws CallbackException { 
        if (entity instanceof Auditable)
            inserts.add((Auditable)entity);
        return false; 
    } 
    
	/* (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onDelete(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
        if (entity instanceof Auditable)
            deletes.add((Auditable)entity);
	}

    
    @Override
    public boolean onFlushDirty(Object entity, 
                                Serializable id, 
                                Object[] currentState, 
                                Object[] previousState, 
                                String[] propertyNames, 
                                Type[] types) 
            throws CallbackException { 
        if (entity instanceof Auditable) {
        	try {
	        	// Use the id and class to get the pre-update state from the database
	        	Session tempSession = 
	                HibernateUtil.getSessionFactory().openSession(); 
	        	Auditable old = (Auditable) tempSession.get(entity.getClass(), ((Auditable) entity).getId());
	            oldies.put(old.getId(), old);
	        	updates.add((Auditable)entity);
        	} catch (Throwable e) {
        		_log.error(e,e);
        	}
        }
        return false; 
    } 
    
    @SuppressWarnings("unchecked")
	@Override
    public void postFlush(Iterator iterator) 
                    throws CallbackException { 
        try { 
        	for (Auditable entity:inserts) {
        		if (!entity.skipAudit()) {
        			if (StringUtil.isEmpty(entity.getAuditMessage()))
        				AuditLogDAOImpl.logEvent(
        						CrudUtil.buildCreateMessage(entity), entity); 
        			else
        				AuditLogDAOImpl.logEvent(
        						entity.getAuditMessage(), entity);         				
        		}
        	}
        	for (Auditable entity:deletes) {
        		if (!entity.skipAudit()) {
        			if (StringUtil.isEmpty(entity.getAuditMessage()))
        				AuditLogDAOImpl.logEvent(
        						CrudUtil.buildDeleteMessage(entity), entity); 
        			else
        				AuditLogDAOImpl.logEvent(
        						entity.getAuditMessage(), entity);         				
        		}
        	}        	
        	for (Auditable entity:updates) {
        		if (!entity.skipAudit()) {
        			if (StringUtil.isEmpty(entity.getAuditMessage())) {
		        		Auditable old = oldies.get(entity.getId());
		        		String message = CrudUtil.buildUpdateMessage(old, entity);
		        		if (!StringUtil.isEmpty(message))
		        		AuditLogDAOImpl.logEvent(message, entity); 
        			} else
        				AuditLogDAOImpl.logEvent(
        						entity.getAuditMessage(), entity);        				
        		}
        	}
        } catch (Throwable e) {
    		_log.error(e,e);
    	} finally { 
            inserts.clear(); 
            updates.clear();
            deletes.clear();
            oldies.clear();
        } 
    }    
}
