package com.ideyatech.core.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.ideyatech.core.InvalidImplementationException;
import com.ideyatech.core.bean.BaseCriteria;
import com.ideyatech.core.bean.BaseEntity;
import com.ideyatech.core.bean.BaseProtectedEntity;
import com.ideyatech.core.persistence.BaseEntityDAO;
import com.ideyatech.core.util.CrudUtil;
import com.ideyatech.core.util.StringUtil;

/**
 * Base class for all business data access objects. Extend this class to inherit
 * all CRUD operations.
 * This implementation is patterned after GenericEJB3DAO from Hibernate CaveatEmptor.
 *
 * @author allanctan
 */
public class BaseEntityDAOJpaImpl<T extends BaseEntity,ID extends Serializable> 
		implements BaseEntityDAO<T, ID>  {
	
    @SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(BaseEntityDAOJpaImpl.class);

    private Properties properties;
	// contains the class type of the bean
    private Class<T> entityBeanType;
    // the entity manager
	@PersistenceContext
    private EntityManager em;
   
	@SuppressWarnings("unchecked")
	public BaseEntityDAOJpaImpl() {
		// identify the bean we are processing for this DAO
        this.entityBeanType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T loadEntityModel(ID id, boolean lock) {
        T entity;
        entity = getEntityManager().find(getEntityBeanType(), id);
        if (lock) {      
        	getEntityManager().lock(entity, javax.persistence.LockModeType.WRITE);
        } 
        return entity;
	}

	public T loadEntityModel(ID id) {
		return this.loadEntityModel(id, false);
	}
	
	public void saveEntityModel(T obj) {
		if (obj.isNew())
			this.addEntityModel(obj);
		else
			this.updateEntityModel(obj);
	}
	
	public void deleteEntityModel(ID id) {
		Object obj = getEntityManager().find(getEntityBeanType(), id);
		getEntityManager().remove(obj);
	}
	
	public void deleteEntityModel(T obj) {
		getEntityManager().remove(obj);
	}

    @SuppressWarnings("unchecked")
	public List<T> findAll() {
    	return findAll(-1,-1);
    }
    
	@SuppressWarnings("unchecked")
	public List<T> findAll(int start, int total) {
		checkFiltering();
		Query query =  getEntityManager().createQuery("from " + 
        		getEntityBeanType().getName() );
		if (start > -1) 
			query.setFirstResult(start);
		if (total > -1)
			query.setMaxResults(total);		
        return query.getResultList();
	}
    
	public long countAll() {
		checkFiltering();
		return (Long) getEntityManager().createQuery("select count(*) from " + 
				getEntityBeanType().getName()).getSingleResult();
	}
	
	public long countByExample(T example) {
		return countByExample(example,false);
	}
	
	public long countByExample(T example, boolean exactMatch) {
		checkFiltering();		
		if (example instanceof BaseCriteria) {
			BaseCriteria criteria = (BaseCriteria) example;
			String whereClause = CrudUtil.buildJpaQueryString(criteria, exactMatch);
			String append = appendClauseToExample(example, exactMatch);
			if (!StringUtil.isEmpty(append)) {
				if (StringUtil.isEmpty(whereClause))
					whereClause += " where ";
				else
					whereClause += " and ";
				whereClause += append;
			}	
			return (Long) getEntityManager().createQuery("select count(*) from " + 
					getEntityBeanType().getName() + whereClause).getSingleResult();
		} else {
			throw new InvalidImplementationException("BaseEntityDAOJpaImpl","findByExample",example.getClass().getName(),
					"Parameter example is not an instance of BaseCriteria");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example, boolean exactMatch) {
		return findByExample(example, exactMatch, -1,-1);
	}

	public List<T> findByExample(T example) {
		return findByExample(example,false,-1,-1);
	}

	public List<T> findByExample(T example, int start, int total) {
		return findByExample(example,false,start,total);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example, boolean exactMatch, int start, int total) {
		checkFiltering();
		if (example instanceof BaseCriteria) {
			BaseCriteria criteria = (BaseCriteria) example;
			String whereClause = CrudUtil.buildJpaQueryString(criteria, exactMatch);
			String orderClause = " " + appendOrderToExample(example);
			String append = appendClauseToExample(example, exactMatch);
			if (!StringUtil.isEmpty(append)) {
				if (StringUtil.isEmpty(whereClause))
					whereClause += " where ";
				else
					whereClause += " and ";
				whereClause += append;
			}				
			if (_log.isDebugEnabled()) _log.debug("QBE >> "+whereClause);
			if (_log.isDebugEnabled()) _log.debug("QBE ORDER >> "+orderClause);
			Query query = getEntityManager().createQuery("from " + 
	        		getEntityBeanType().getName() + whereClause + orderClause);
			if (start > -1) 
				query.setFirstResult(start);
			if (total > -1)
				query.setMaxResults(total);	
			return query.getResultList();
		} else {
			throw new InvalidImplementationException("BaseEntityDAOJpaImpl","findByExample",example.getClass().getName(),
					"Parameter example is not an instance of BaseCriteria");
		}
	}

	/**
	 * Override this method to append additional query conditions for findByExample.
	 * Useful for date range and other complex queries.
	 * @param example
	 * @param exactMatch
	 * @return
	 */
	protected String appendClauseToExample(T example, boolean exactMatch) {
		return "";
	}
	
	/**
	 * Override this method to append order clause to findByExample.
	 * @param example
	 * @return
	 */
	protected String appendOrderToExample(T example) {
		return "";
	}

	protected void addEntityModel(T obj) {
		getEntityManager().persist(obj);
	}
	
	protected void updateEntityModel(T obj) {
		getEntityManager().merge(obj);
		getEntityManager().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(final String name, final Map<String,Object> params) {
		checkFiltering();
		String queryString = getJpqlQuery(name);
		Query queryObject = getEntityManager().createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, Object> entry:params.entrySet())
				queryObject.setParameter(entry.getKey(), entry.getValue());
		}
		return queryObject.getResultList();
	}

    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public String getJpqlQuery(String key) {
		String query = (String) properties.get(key);
		if (StringUtil.isEmpty(query)) {
			throw new InvalidImplementationException(this.getClass().getName(),
					"getJpqlQuery",key,"Key is not defined in custom jpql property file.");
		} else
			return query;
	}
	
	public Session getHibernateSession() {
		return (Session) em.getDelegate();
	}
	
	public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    protected EntityManager getEntityManager() {
        if (em == null)
            throw new IllegalStateException("EntityManager has not been set on DAO before usage");
        return em;
    }
    
    private void checkFiltering() {
    	// limit the query if entity is a protected type
		if (BaseProtectedEntity.class.isAssignableFrom(entityBeanType)) {
			enableFilter();
		}
    }
    
    protected void enableFilter(){
    	throw new InvalidImplementationException(this.getClass().getName(),
				"enableFilter","Empty Implementation", 
				this.getClass().getName()+" must implement enableFilter() to provide record filtering.");
    }

}
