package org.opentides.util;

import java.net.URL;
import java.util.Properties;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 * This utility allows creation of Hibernate session directly.
 * Used for logging purposes.
 * 
 * @author allantan
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static String driverClass;
    private static String url;
    private static String username;
    private static String password;
    private static String jndiPath;
    
    private static void initialize() {
        try { 
        	URL[] urls = ClasspathUrlFinder.findResourceBases("META-INF/persistence.xml");
        	AnnotationDB db = new AnnotationDB();
        	db.scanArchives(urls);
        	Set<String> entityClasses = db.getAnnotationIndex().get(javax.persistence.Entity.class.getName());
            // Create the SessionFactory
        	AnnotationConfiguration ac =  new AnnotationConfiguration();
        	Properties properties = XMLPersistenceUtil.getProperties("META-INF/persistence.xml", "hibernateConfig");
        	ac.setProperties(properties);
        	if (StringUtil.isEmpty(jndiPath)) {
	            ac.setProperty("hibernate.connection.driver_class", driverClass);
	            ac.setProperty("hibernate.connection.url", url);
	            ac.setProperty("hibernate.connection.username", username);
	            ac.setProperty("hibernate.connection.password", password);
        	} else
        		ac.setProperty("hibernate.connection.datasource", jndiPath);
            for (String clazz:entityClasses) {
            	ac.addAnnotatedClass(Class.forName(clazz));
            }
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
	public void setDriverClass(String driverClass) {
		HibernateUtil.driverClass = driverClass;
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

	public void setJndiPath(String jndiPath) {
		HibernateUtil.jndiPath = jndiPath;
	}
}