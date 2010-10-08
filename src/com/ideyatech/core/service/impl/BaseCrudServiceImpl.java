/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * BaseCrudServiceImpl.java
 * Created on Jan 31, 2008, 10:46:14 PM
 */
package com.ideyatech.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.ideyatech.core.InvalidImplementationException;
import com.ideyatech.core.bean.BaseEntity;
import com.ideyatech.core.persistence.BaseEntityDAO;
import com.ideyatech.core.service.BaseCrudService;
import com.ideyatech.core.util.StringUtil;

/**
 * This is a base implementation that supports CRUD operations.
 * @author allanctan
 *
 */
public class BaseCrudServiceImpl<T extends BaseEntity> 
			extends BaseServiceDefaultImpl implements BaseCrudService<T>, InitializingBean  {

	private BaseEntityDAO<T,Long> dao;
	
	/**
	 * Retrieves all the records on this object.
	 * @return List of objects
	 */
	public List<T> findAll(int start, int total) {
		return dao.findAll(start, total);
	}
	
	/**
	 * Retrieves all the records on this object.
	 * @return List of objects
	 */	
	public List<T> findAll() {
		return dao.findAll();
	}

	/**
	 * Retrieves matching records based on the object passed.
	 * @return List of objects
	 */
	public List<T> findByExample(T example, int start, int total) {
		return dao.findByExample(example,start,total);
	}
	
	/**
	 * Retrieves matching records based on the object passed.
	 * @return List of objects
	 */
	public List<T> findByExample(T example) {
		return dao.findByExample(example);
	}

	/**
	 * Retrieves matching records based on the object passed.
	 * @return List of objects
	 */
	public List<T> findByExample(T example, boolean exactMatch) {
		return dao.findByExample(example,exactMatch);
	}
	
	/**
	 * Retrieves matching records based on the object passed.
	 * @return List of objects
	 */
	public List<T> findByExample(T example, boolean exactMatch, int start, int total) {
		return dao.findByExample(example, exactMatch, start, total);
	}
	/**
	 * Counts all the record of this object
	 */
	public long countAll() {
		return dao.countAll();
	}

	/**
	 * Counts the matching record of this object
	 */
	public long countByExample(T example) {
		return dao.countByExample(example);
	}


	/**
	 * Loads an object based on the given id
	 * @param id to load
	 * @return object
	 */
	public T load(String sid) {
		if (StringUtil.isEmpty(sid)) {
			throw new InvalidImplementationException("BaseCrudServiceImpl","load","",
						"ID parameter is empty.");
		} else {
			try {
				Long id = Long.parseLong(sid);
				return dao.loadEntityModel(id);
			} catch (NumberFormatException nfe) {
				throw new InvalidImplementationException("BaseCrudServiceImpl","load", 
						sid ,"ID parameter is not numeric.");
			}
		}
	}

	/**
	 * Save the object via DAO.
	 * @param object to save
	 */
	public void save(T obj) {
		dao.saveEntityModel(obj);
	}

	/**
	 * Deletes the object from the given id.
	 * @param sid - id to delete
	 */
	public void delete(String sid) {
		if (StringUtil.isEmpty(sid)) {
			throw new InvalidImplementationException("BaseCrudServiceImpl","delete","",
						"ID parameter is empty.");
		} else {
			try {
				Long id = Long.parseLong(sid);
				dao.deleteEntityModel(id);
			} catch (NumberFormatException nfe) {
				throw new InvalidImplementationException("BaseCrudServiceImpl","delete", 
						sid ,"ID parameter is not numeric.");
			}
		}
	}

	/**
	 * Make sure DAO is properly set.
	 */
	public void afterPropertiesSet() throws Exception {
		if (dao == null) {
			throw new IllegalArgumentException(
					"Must specify a dao for "
							+ getClass().getName());
		}		
	}
	/**
	 * @param dao the dao to set
	 */
	public void setDao(BaseEntityDAO<T,Long>  dao) {
		this.dao = dao;
	}

	/**
	 * @return the dao
	 */
	public BaseEntityDAO<T, Long> getDao() {
		return dao;
	}

}
