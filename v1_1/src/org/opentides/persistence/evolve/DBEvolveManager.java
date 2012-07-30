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
package org.opentides.persistence.evolve;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.opentides.InvalidImplementationException;
import org.opentides.bean.SystemCodes;
import org.opentides.persistence.SystemCodesDAO;
import org.opentides.service.UserService;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author allantan
 *
 */
public class DBEvolveManager {

	private List<DBEvolve> evolveList;
	private SystemCodesDAO systemCodesDAO;
	private UserService userService;
	
	private static Logger _log = Logger.getLogger(DBEvolveManager.class);

	@Transactional
	public void evolve() {

		// get current db version
		SystemCodes version;
		try {
		    version = systemCodesDAO.loadBySystemCodesByKey("DB_VERSION");
		} catch (NoResultException nre) {
		    version = null;
		}
		if (version==null) {
			// no version available yet, lets create one
			version = new SystemCodes();
			version.setSkipAudit(true);
			version.setKey("DB_VERSION");
			version.setNumberValue(0l);
			systemCodesDAO.saveEntityModel(version);
			// initialize default admin user
			userService.setupAdminUser();
		}

		// skip evolve if there is nothing in the evolve list
		if (evolveList.isEmpty()) {
			_log.info("No evolve scripts found.");
			return;
		}
		
		// sort the evolve list
		Collections.sort(evolveList, new VersionComparator());
		// check for duplicate version numbers
		for (int i=0; i<(evolveList.size()-1); i++) {
			if (evolveList.get(i).getVersion() == evolveList.get(i+1).getVersion()) {
				// we have a duplicate version... exit
				throw new InvalidImplementationException(
						"Duplicate version number ["+evolveList.get(i).getVersion() +
						"] detected on evolve script for "+evolveList.get(i).getClass().getName() + 
						" and " + evolveList.get(i+1).getClass().getName());
			}
		}
		
		// get number of latest evolve script
		long currVersion   = version.getNumberValue();
		long latestVersion = evolveList.get(evolveList.size()-1).getVersion();
		
		if (currVersion>=latestVersion) {
			_log.info("Database is already at version " + currVersion);
			return;
		} else {
			_log.info("Updating database from version " + currVersion +" to version " + latestVersion );
		}
		
		// execute new evolve scripts
		for (DBEvolve evolve:evolveList) {
			if (evolve.getVersion() > currVersion) {
				// let's execute this evolve script
				_log.info("Executing evolve version ["+evolve.getVersion()+"] - "+evolve.getDescription());
				evolve.execute();
				// if successful, update current db version
				version.setNumberValue(Long.valueOf(evolve.getVersion()));
				version.setSkipAudit(true);
				systemCodesDAO.saveEntityModel(version);
				_log.info("Success.");
			}
		}
		// as precaution let's update db version again
		version.setNumberValue(Long.valueOf(latestVersion));
		version.setSkipAudit(true);
		systemCodesDAO.saveEntityModel(version);
		
		_log.info("Database is now updated to version "+latestVersion);
	}

	/**
	 * @param evolveList the evolveList to set
	 */
	public void setEvolveList(List<DBEvolve> evolveList) {
		this.evolveList = evolveList;
	}

	/**
	 * @param systemCodesDAO the systemCodesDAO to set
	 */
	public void setSystemCodesDAO(SystemCodesDAO systemCodesDAO) {
		this.systemCodesDAO = systemCodesDAO;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}