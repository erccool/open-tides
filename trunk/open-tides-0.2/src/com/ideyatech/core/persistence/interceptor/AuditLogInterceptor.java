/**
 * 
 */
package com.ideyatech.core.persistence.interceptor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import com.ideyatech.core.bean.Auditable;
import com.ideyatech.core.persistence.impl.AuditLogDAOImpl;
import com.ideyatech.core.util.CrudUtil;
import com.ideyatech.core.util.HibernateUtil;

/**
 * This is the interceptor responsible in tracking audit trails.
 * Source is patterned after the book "Java Persistence with Hibernate" - page 546 onwards
 * and merged with http://www.hibernate.org/318.html
 * 
 * @author allantan
 */
public class AuditLogInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 582549003254963262L;
	
    private Set<Auditable> inserts    = new HashSet<Auditable>(); 
    private Set<Auditable> updates    = new HashSet<Auditable>(); 
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
    
    @Override
    public boolean onFlushDirty(Object entity, 
                                Serializable id, 
                                Object[] currentState, 
                                Object[] previousState, 
                                String[] propertyNames, 
                                Type[] types) 
            throws CallbackException { 
        if (entity instanceof Auditable) {
        	// Use the id and class to get the pre-update state from the database
        	Session tempSession = 
                HibernateUtil.getSessionFactory().openSession(); 
        	Auditable old = (Auditable) tempSession.get(entity.getClass(), ((Auditable) entity).getId());
            oldies.put(old.getId(), old);
        	updates.add((Auditable)entity);
        }
        return false; 
    } 
    
    @Override
    public void postFlush(Iterator iterator) 
                    throws CallbackException { 
        try { 
        	for (Auditable entity:inserts) {
        		AuditLogDAOImpl.logEvent(
        				CrudUtil.buildCreateMessage(entity), entity); 
        	}
        	for (Auditable entity:updates) {
        		Auditable old = oldies.get(entity.getId());
        		AuditLogDAOImpl.logEvent(CrudUtil.buildUpdateMessage(old, entity), entity); 
        	}
        } finally { 
            inserts.clear(); 
            updates.clear();
            oldies.clear();
        } 
    }
}
