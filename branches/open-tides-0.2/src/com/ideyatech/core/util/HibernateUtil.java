package com.ideyatech.core.util;

import java.net.URL;
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

    private static final SessionFactory sessionFactory;

    static {
        try { 
        	URL[] urls = ClasspathUrlFinder.findResourceBases("META-INF/persistence.xml");
        	AnnotationDB db = new AnnotationDB();
        	db.scanArchives(urls);
        	Set<String> entityClasses = db.getAnnotationIndex().get(javax.persistence.Entity.class.getName());
            // Create the SessionFactory
        	AnnotationConfiguration ac =  new AnnotationConfiguration();
            ac.setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/ideyatech");
            ac.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
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
        return sessionFactory;
    }

}