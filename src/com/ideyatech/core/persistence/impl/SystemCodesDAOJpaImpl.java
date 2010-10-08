package com.ideyatech.core.persistence.impl;

import java.util.List;

import com.ideyatech.core.bean.SystemCategory;
import com.ideyatech.core.bean.SystemCodes;
import com.ideyatech.core.persistence.SystemCodesDAO;

public class SystemCodesDAOJpaImpl extends BaseEntityDAOJpaImpl<SystemCodes, Long> implements
		SystemCodesDAO {

	/*
	 * (non-Javadoc)
	 * @see com.ideyatech.core.persistence.SystemCodesDAO#findByCategoryName(java.lang.String)
	 */
	public List<SystemCodes> getSystemCodesByCategory(SystemCategory category) {
		SystemCodes example = new SystemCodes();
		example.setCategory(category.getName());
		return findByExample(example);
	}

	public SystemCodes loadBySystemCodesByKey(String key) {
		SystemCodes example = new SystemCodes(key);
		List<SystemCodes> systemCodesList = findByExample(example);
		if (systemCodesList != null && systemCodesList.size() > 0) {
			return systemCodesList.get(0);
		}
		return null;
	}

	
}
