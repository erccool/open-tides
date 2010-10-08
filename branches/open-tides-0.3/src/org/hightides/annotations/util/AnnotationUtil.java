/**
 * 
 */
package org.hightides.annotations.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.hightides.annotations.bean.SyncMode;

/**
 * Utilities to retrieve annotation values 
 * @author allantan
 */
public class AnnotationUtil {
	
	private static Logger _log = Logger.getLogger(AnnotationUtil.class);

	@SuppressWarnings("unchecked")
	public static final SyncMode getSyncMode(Class annotationClass, AnnotatedElement element) {
		Annotation[] classAnnotations = element.getAnnotations();
		for(Annotation annotation : classAnnotations)
		{               
            Method m;
			try {
				m = annotation.getClass().getDeclaredMethod("synchronizeMode");
	            if (m!=null) {
					return (SyncMode) m.invoke(annotation);
	            }				
			} catch (SecurityException e1) {
				_log.error(e1,e1);
			} catch (NoSuchMethodException e1) {
				_log.error(e1,e1);
			} catch (IllegalArgumentException e) {
				_log.error(e,e);
			} catch (IllegalAccessException e) {
				_log.error(e,e);
			} catch (InvocationTargetException e) {
				_log.error(e,e);
			}
		}	
		return null;
	}
}
