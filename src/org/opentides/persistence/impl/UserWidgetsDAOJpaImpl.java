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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.opentides.bean.UserWidgets;
import org.opentides.persistence.UserWidgetsDAO;


/**
 * This is the dao implementation for userWidgets.
 * Auto generated by high tides.
 * @author hightides
 *
 */
public class UserWidgetsDAOJpaImpl extends BaseEntityDAOJpaImpl<UserWidgets, Long>
		implements UserWidgetsDAO {
	//-- Start custom codes. Do not delete this comment line.
	@SuppressWarnings("unchecked")
	public List<UserWidgets> findUserWidgets(long userId, Integer... widgetStatus) {
		List<UserWidgets> defaultList = new ArrayList<UserWidgets>();
		String widgetStatusStr = "";
		for (Integer myInt : widgetStatus) {
			if (StringUtils.isNotEmpty(widgetStatusStr)) {
				widgetStatusStr += ",";
			}
			widgetStatusStr += "'" + myInt + "'";
		}
		
		Query query = getEntityManager().createQuery("SELECT i from UserWidgets i where i.user ="+userId+
				" AND i.status in ("+widgetStatusStr+") ORDER BY i.column asc, i.row asc");		
		if (!query.getResultList().isEmpty() || query.getResultList().size() > 0)
			return (List<UserWidgets>) query.getResultList();
		else
			return defaultList;
	}
	
	@Override
	protected String appendOrderToExample(UserWidgets example) {
		return "order by column asc, row asc";
	}
	public long countUserWidgetsColumn(Integer column, long userId) {
		Query query = getEntityManager().createQuery(
				"SELECT COUNT(i) FROM " + getEntityBeanType().getName()
						+ " i WHERE i.user="+userId + " AND i.column=" + column);
		return ((Long) query.getSingleResult()).intValue();
	}

	@Override
	public void deleteUserWidget(long widgetId, long baseUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", baseUserId);
		params.put("widgetId", widgetId);
		
		executeByNamedQuery("jpql.widget.deleteUserWidgetsByWidgetAndUser", params);
	}
	//-- End custom codes. Do not delete this comment line.
}
