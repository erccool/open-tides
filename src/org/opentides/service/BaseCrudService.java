package org.opentides.service;

import java.util.List;

import org.opentides.bean.BaseEntity;
import org.opentides.persistence.BaseEntityDAO;


/**
 * @author allanctan
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseCrudService<T extends BaseEntity> extends BaseService {
	public void save(T entity);
	public List<T> findAll();	
	public List<T> findAll(int start, int total);
	public List<T> findByExample(T example);	
	public List<T> findByExample(T example, int start, int total);
	public List<T> findByExample(T example, boolean exactMatch);	
	public List<T> findByExample(T example, boolean exactMatch, int start, int total);	
	public long countAll();
	public long countByExample(T example);
	public T load(String sid);
	public T load(Long id);
	public void delete(String sid);
	public void delete(Long id);
	public void setDao(BaseEntityDAO<T,Long>  dao);
}
