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
package org.opentides.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * 
 * @author allantan
 * 
 */
public class DBUtil {
	private static Logger _log = Logger.getLogger(DBUtil.class);

	public static void importCSV(String filename, String tableName, Session session)
			throws Exception {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(DBUtil.class.getClassLoader()
							.getResourceAsStream(filename)));
			// read the column header
			String csvLine = reader.readLine();
			if (csvLine==null) {
				_log.warn("Import file ["+filename+"] has no contents.");
				return;
			}
			String[] headers = StringUtil.parseCsvLine(csvLine);
			StringBuffer baseQuery = new StringBuffer(100);
			baseQuery.append("INSERT INTO ").append(tableName).append("(");
			StringBuffer valueQuery = new StringBuffer(30);
			int count = 0;
			for (String column : headers) {
				if (count++ > 0) {
					baseQuery.append(",");
					valueQuery.append(",");
				}
				baseQuery.append(column);
				valueQuery.append("?");
			}
			baseQuery.append(") VALUES (").append(valueQuery).append(")");
			SQLQuery query = session.createSQLQuery(baseQuery.toString());
			int line = 1;
			while ((csvLine = reader.readLine()) != null) {
				String[] values = StringUtil.parseCsvLine(csvLine);
				count = 0;
				if (headers.length != values.length)
					_log.error("Column number mismatch. "
							+ "Failed to import line #:" + line
							+ " with data as follows: \n[" + csvLine + "].");
				int index = 0;
				for (String value : values) {
					query.setParameter(index++, value);
				}
				query.executeUpdate();
				line++;
			}
			reader.close();
		} catch (Exception e) {
			_log.error("Failed to import csv file [" + filename + "]. ", e);
			throw e;
		}
		return;
	}
}
