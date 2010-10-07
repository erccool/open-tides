/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */
package org.opentides.persistence.impl;

import org.apache.log4j.Logger;
import org.opentides.bean.BaseEntity;
import org.opentides.bean.BaseSortableEntity;
import org.opentides.persistence.BaseSortableEntityDAO;
import org.opentides.util.StringUtil;

/**
 * For open-tides 0.5, there is no need to extend this class.
 * Sorting function has been added to BaseEntityDAO
 */
@SuppressWarnings({ "unchecked" })
@Deprecated
public class BaseSortableEntityDAOJpaImpl<T extends BaseSortableEntity, ID> extends BaseEntityDAOJpaImpl implements BaseSortableEntityDAO<T, ID>{

	private static Logger _log = Logger.getLogger(BaseSortableEntityDAOJpaImpl.class);
	
	/* (non-Javadoc)
	 * @see org.opentides.persistence.impl.BaseEntityDAOJpaImpl#appendOrderToExample(org.opentides.bean.BaseEntity)
	 */
	@Override
	protected final String appendOrderToExample(BaseEntity example) {
		String clause="";
		
		if (example instanceof BaseSortableEntity) {
			T criteria = (T) example;
		
			//for search list ordering
			if(!StringUtil.isEmpty(criteria.getOrderOption()) && !StringUtil.isEmpty(criteria.getOrderFlow())){
				clause="ORDER BY "+ criteria.getOrderOption() +" "+ criteria.getOrderFlow() +"";
			}
		
		}else {
			_log.warn("example is not an instanceof BaseSortEntity. " +
					"Skipping appendOrderToExample...");
		}
		return clause;
	}

}
