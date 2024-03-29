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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 * This utility allows creation of Hibernate session directly.
 * Used for logging purposes.
 *
 * @author allantan
 */
public class HibernateUtil {
	
	private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);

    /**
     * Local session factory to manage database sessions.
     */
    private static SessionFactory sessionFactory;

    /**
     * Database driver class name (e.g. com.mysql.jdbc.Driver)
     */
    private static String driverClassName;

    /**
     * Database connection url (e.g. jdbc:mysql://localhost/javatest)
     */
    private static String url;

    /**
     * Database connection username
     */
    private static String username;

    /**
     * Database connection password
     */
    private static String password;

    /**
     * Database connection jndi name.
     * Optional but if this has value, url, username and password is ignored.
     */
    private static String jndiName;
    
    /**
     * Persistence name in hibernate.
     */
    private static String persistenceUnitName = "hibernateConfig";
    
    /**
     * Persistence file
     */
    private static String persistenceFile;

    /**
     * Static initializer to establish database connection.
     */
    private static void initialize() {
        try { 
        	URL[] urls = ClasspathUrlFinder.findResourceBases(persistenceFile);
        	List<URL> toAdd = new ArrayList<URL>();
        	List<URL> finalUrls = new ArrayList<URL>();
        	for(URL url : urls) {
        		LOGGER.debug("URL is : [" + url.getPath() + "] with protocol: [" + url.getProtocol() + "] with file: [" + url.getFile() + "]");
        		String file = url.getFile();
        		if(!file.startsWith("file:")) {
        			file = "file:" + file;
        		}
        		if("zip".equals(url.getProtocol())) {
        			toAdd.add(new URL("jar", url.getHost(), url.getPort(), file));
        		} else {
        			toAdd.add(url);
        		}
        	}
    		finalUrls.addAll(toAdd);
    		for(URL url : finalUrls) {
    			LOGGER.debug("URL full path: [" + url.toString() +"]");
        		LOGGER.debug("Final URL is : [" + url.getPath() + 
        				"] with protocol: [" + url.getProtocol() + 
        				"] with file: [" + url.getFile() + "]");
        	}
        	AnnotationDB db = new AnnotationDB();
        	db.scanArchives(finalUrls.toArray(new URL[finalUrls.size()]));
        	Set<String> entityClasses = db.getAnnotationIndex().get(javax.persistence.Entity.class.getName());
            // Create the SessionFactory
        	Configuration ac =  new Configuration();
        	Properties properties = XMLPersistenceUtil.getProperties(persistenceFile, persistenceUnitName);
        	ac.setProperties(properties);
        	if (StringUtil.isEmpty(jndiName)) {
	            ac.setProperty("hibernate.connection.driver_class", driverClassName);
	            ac.setProperty("hibernate.connection.url", url);
	            ac.setProperty("hibernate.connection.username", username);
	            ac.setProperty("hibernate.connection.password", password);
        	} else {
        		LOGGER.debug("JNDI not empty. [" + jndiName + "] using persistence [" + persistenceFile + "]" );
        		ac.setProperty("hibernate.connection.datasource", jndiName);
        	}
            for (String clazz:entityClasses) {
            	LOGGER.debug("Entity Class : [" + clazz + "]");
            	ac.addAnnotatedClass(Class.forName(clazz));
            }
            ac.addAnnotatedClass(Class.forName("org.opentides.bean.AuditLog"));
            ac.addAnnotatedClass(Class.forName("org.opentides.bean.BaseEntity"));
            ac.addAnnotatedClass(Class.forName("org.opentides.bean.FileInfo"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.SystemCodes"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.Widget"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.UserWidgets"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.user.BaseUser"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.PasswordReset"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.user.UserRole"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.user.UserCredential"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.user.UserGroup"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.UserDefinedRecord"));
			ac.addAnnotatedClass(Class.forName("org.opentides.bean.UserDefinedField"));
            sessionFactory = ac.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    	
    }
    
    public static SessionFactory getSessionFactory() {
    	if (sessionFactory==null)
    		HibernateUtil.initialize();
        return sessionFactory;
    }

	/**
	 * @param driverClass the driverClass to set
	 */
	public void setDriverClassName(String driverClass) {
		HibernateUtil.driverClassName = driverClass;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		HibernateUtil.url = url;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		HibernateUtil.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		HibernateUtil.password = password;
	}

	/**
	 * Deprecated. Use jndiName instead.
	 * 
	 * @param jndiPath
	 */
	@Deprecated
	public void setJndiPath(String jndiPath) {
		HibernateUtil.jndiName = jndiPath;
	}
	
	/**
	 * When jndiName is set, other database settings 
	 * (e.g. driverClassName, url, username and password)
	 * are ignored.
	 * 
	 * @param jndiName
	 */
	public void setJndiName(String jndiName) {
		HibernateUtil.jndiName = jndiName;
	}

	/**
	 * Setter method for persistenceUnitName.
	 *
	 * @param persistenceUnitName the persistenceUnitName to set
	 */
	public static final void setPersistenceUnitName(String persistenceUnitName) {
		HibernateUtil.persistenceUnitName = persistenceUnitName;
	}
	
	/**
	 * Setter method for persistenceFile.
	 *
	 * @param persistenceFile the persistenceFile to set
	 */
	public void setPersistenceFile(String persistenceFile) {
		HibernateUtil.persistenceFile = persistenceFile;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("META-INF/attache-persistence.xml").getPath());
		URL[] urls = ClasspathUrlFinder.findResourceBases("META-INF/attache-persistence.xml");
		for(URL url : urls) {
			System.out.println(url.toString());
    		System.out.println("URL is : [" + url.getPath() + "] with protocol: [" + url.getProtocol() + "]");
    		System.out.println(url.getFile());
    	}
		AnnotationDB db = new AnnotationDB();
    	db.scanArchives(urls);
    	Set<String> entityClasses = db.getAnnotationIndex().get(javax.persistence.Entity.class.getName());
    	System.out.println(entityClasses);
	}
}
