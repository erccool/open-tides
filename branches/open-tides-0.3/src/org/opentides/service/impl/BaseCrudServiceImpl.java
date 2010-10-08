/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * BaseCrudServiceImpl.java
 * Created on Jan 31, 2008, 10:46:14 PM
 */
package org.opentides.service.impl;

import java.util.List;

import org.opentides.bean.BaseEntity;
import org.opentides.persistence.BaseEntityDAO;
import org.opentides.service.BaseCrudService;
import org.opentides.util.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import org.opentides.InvalidImplementationException;

/**
 * This is a base implementation that supports CRUD operations.
 * 
 * @author allanctan
 * 
 */
@Transactional
public class BaseCrudServiceImpl<T extends BaseEntity> extends
		BaseServiceDefaultImpl implements BaseCrudService<T>, InitializingBean {

	private BaseEntityDAO<T, Long> dao;

	/**
	 * Retrieves all the records on this object.
	 * 
	 * @return List of objects
	 */
	@Transactional(readOnly=true)
	public final List<T> findAll(int start, int total) {
		return dao.findAll(start, total);
	}

	/**
	 * Retrieves all the records on this object.
	 * 
	 * @return List of objects
	 */
	@Transactional(readOnly=true)
	public final List<T> findAll() {
		return dao.findAll();
	}

	/**
	 * Retrieves matching records based on the object passed.
	 * 
	 * @return List of objects
	 */
	@Transactional(readOnly=true)
	public final List<T> findByExample(T example, int start, int total) {
		return dao.findByExample(example, start, total);
	}

	/**
	 * Retrieves matching records based on the object passed.
	 * 
	 * @return List of objects
	 */
	@Transactional(readOnly=true)
	public final List<T> findByExample(T example) {
		return dao.findByExample(example);
	}

	/**
	 * Retrieves matching records based on the object passed.
	 * 
	 * @return List of objects
	 */
	@Transactional(readOnly=true)
	public final List<T> findByExample(T example, boolean exactMatch) {
		return dao.findByExample(example, exactMatch);
	}

	/**
	 * Retrieves matching records based on the object passed.
	 * 
	 * @return List of objects
	 */
	@Transactional(readOnly=true)
	public final List<T> findByExample(T example, boolean exactMatch,
			int start, int total) {
		return dao.findByExample(example, exactMatch, start, total);
	}

	/**
	 * Counts all the record of this object
	 */
	@Transactional(readOnly=true)
	public final long countAll() {
		return dao.countAll();
	}

	/**
	 * Counts the matching record of this object
	 */
	@Transactional(readOnly=true)
	public final long countByExample(T example) {
		return dao.countByExample(example);
	}

	/**
	 * Loads an object based on the given id
	 * 
	 * @param id
	 *            to load
	 * @return object
	 */
	@Transactional(readOnly=true) 
	public final T load(String sid) {
		if (StringUtil.isEmpty(sid)) {
			throw new InvalidImplementationException("ID parameter is empty.");
		} else {
			try {
				Long id = Long.parseLong(sid);
				return load(id);
			} catch (NumberFormatException nfe) {
				throw new InvalidImplementationException("ID parameter is not numeric.");
			}
		}
	}

	/**
	 * Loads an object based on the given id
	 * 
	 * @param id
	 *            to load
	 * @return object
	 */
	public final T load(Long id) {
		return dao.loadEntityModel(id);
	}

	/**
	 * Save the object via DAO.
	 * 
	 * @param object
	 *            to save
	 */
	public final void save(T obj) {
		dao.saveEntityModel(obj);
	}

	/**
	 * Deletes the object from the given id.
	 * 
	 * @param sid -
	 *            id to delete
	 */
	public final void delete(String sid) {
		if (StringUtil.isEmpty(sid)) {
			throw new InvalidImplementationException("ID parameter is empty.");
		} else {
			try {
				Long id = Long.parseLong(sid);
				delete(id);
			} catch (NumberFormatException nfe) {
				throw new InvalidImplementationException("ID parameter is not numeric.");
			}
		}
	}

	/**
	 * Deletes the object from the given id.
	 * 
	 * @param sid -
	 *            id to delete
	 */
	public final void delete(Long id) {
		dao.deleteEntityModel(id);
	}

	/**
	 * Make sure DAO is properly set.
	 */
	public final void afterPropertiesSet() throws Exception {
		if (dao == null) {
			throw new IllegalArgumentException("Must specify a dao for "
					+ getClass().getName());
		}
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public final void setDao(BaseEntityDAO<T, Long> dao) {
		this.dao = dao;
	}

	/**
	 * @return the dao
	 */
	public BaseEntityDAO<T, Long> getDao() {
		return dao;
	}
}