/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * BaseEntityDAO.java
 * Created on Jan 27, 2008, 4:09:49 PM
 */

package org.opentides.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.opentides.bean.BaseEntity;
import org.springframework.transaction.annotation.Transactional;


/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared across all DAO implementations. 
 * This implementation is patterned after GenericDAO from Hibernate CaveatEmptor.
 *
 * @author allanctan
 */
public interface BaseEntityDAO<T extends BaseEntity, ID extends Serializable> {	
	/**
	 * Returns all entries of this entity
	 * @param start - start index to fetch results
	 * @param total - total number of records to return
	 * @return List of entity
	 */
	public List<T> findAll();
	
	/**
	 * Returns all entries of this entity
	 * @return List of entity
	 */
	public List<T> findAll(int start, int total);
	
	
	/**
	 * Returns all entries found by the named query
	 * @return List of entity
	 */
	public List<T> findByNamedQuery(String name, Map<String, Object> params);
	
	/**
	 * Returns an entry found by the named query
	 * @return Single entity
	 */
	public T findSingleResultByNamedQuery(String name, Map<String,Object> params);
	
	/**
	 * Performs a query by example.
	 * 
	 * @param example - example to match
	 * @param exactMatch - true to find only exact matches, otherwise use like
	 * @return
	 */
	public List<T> findByExample(T example, boolean exactMatch);

	/**
	 * Performs a query by example.
	 * 
	 * @param example - example to match
	 * @param exactMatch - true to find only exact matches, otherwise use like
	 * @param start - start index to fetch results
	 * @param total - total number of records to return
	 * @return
	 */
	public List<T> findByExample(T example, boolean exactMatch, int start, int total);
	
	/**
	 * Performs a query by example. Assumes String will be match using like.
	 * 
	 * @param example - example to match
	 * @return
	 */
	public List<T> findByExample(T example);
	
	/**
	 * Performs a query by example.
	 * 
	 * @param example - example to match
	 * @param start - start index to fetch results
	 * @param total - total number of records to return
	 * @return
	 */
	public List<T> findByExample(T example, int start, int total);
	
	/**
	 * Counts all entries of this entity
	 * @return number of entity
	 */
	public long countAll();
	
	/**
	 * Counts all the matching entries with the given example
	 */
	public long countByExample(T example);
	
	/**
	 * Counts all the matching entries with the given example
	 * @param example - example to match
	 * @param exactMatch - true to find only exact matches, otherwise use like
	 */
	public long countByExample(T example, boolean exactMatch);
    
	/**
	 * Returns instance of the entity.
	 * @param id
	 * @return
	 */
	public T loadEntityModel(ID id);
	
	/**
	 * Returns instance of the entity.
	 * @param id
	 * @param lock - do we acquire a lock for writing
	 * @return
	 */
	@Transactional(readOnly=false)
	public T loadEntityModel(ID id, boolean lock);
	
	/**
	 * Removes the entity
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void deleteEntityModel(ID id);

	/**
	 * Removes the entity
	 * @param obj
	 */
	@Transactional(readOnly=false)
	public void deleteEntityModel(T obj);

	/**
	 * Add or update the entity as appropriate
	 * @param obj
	 */
	@Transactional(readOnly=false)
	public void saveEntityModel(T obj);
	
	/**
	 * Retrieves the jpql statement from preloaded properties file.
	 * @param key - key of jpql to retrieve
	 * @return - jpql statement of the key
	 */
	public String getJpqlQuery(String key);
	
	/**
	 * Retrieves Hibernate Session
	 */
	public Session getHibernateSession();
}
