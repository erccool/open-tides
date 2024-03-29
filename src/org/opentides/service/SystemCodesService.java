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
package org.opentides.service;

import java.util.List;

import org.hibernate.stat.Statistics;
import org.opentides.bean.SystemCodes;



/**
 * @author allantan
 * 
 */
public interface SystemCodesService extends BaseCrudService<SystemCodes> {
	public Long incrementValue(String key);
	public SystemCodes findByKey(SystemCodes systemCodes);
	public SystemCodes findByKey(String key);
	public List<SystemCodes> findSystemCodesByCategory(String category);
	public List<SystemCodes> getAllCategories();
	public List<String> getAllCategoryValues();
	public Statistics getHibernateStatistics();
	public List<SystemCodes> getAllCategoriesExcept(String ... categories);
	public List<String> getAllCategoryValuesExcept(String ... categories);
}