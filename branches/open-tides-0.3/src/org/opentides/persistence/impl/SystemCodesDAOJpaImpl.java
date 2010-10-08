package org.opentides.persistence.impl;

import java.util.List;


import org.opentides.bean.SystemCategory;
import org.opentides.bean.SystemCodes;
import org.opentides.persistence.SystemCodesDAO;
import org.opentides.persistence.impl.BaseEntityDAOJpaImpl;




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

	/**
	 * Retrieves system code based on key.
	 */
	public SystemCodes loadBySystemCodesByKey(String key) {
		SystemCodes example = new SystemCodes(key);
		List<SystemCodes> systemCodesList = findByExample(example);
		if (systemCodesList != null && systemCodesList.size() > 0) {
			return systemCodesList.get(0);
		}
		return null;
	}

	/**
	 * Increments value of numberValue by used. 
	 * Useful for custom generation of id.
	 */
	public Long incrementValue(String key) {
		SystemCodes code = new SystemCodes(key);
		List<SystemCodes> results = findByExample(code, true);
		if (results != null && results.size() > 0)
			code = results.get(0);
		code.incrementNumberValue();
		code.setCategory("KEYGEN");
		code.setSkipAudit(true); 	// no need to audit auto-generated keys
		this.saveEntityModel(code);
		return code.getNumberValue();
	}

	/**
	 * Sort the results by name
	 */
	@Override
	protected String appendOrderToExample(SystemCodes example) {
		return "order by obj.value";
	}
}