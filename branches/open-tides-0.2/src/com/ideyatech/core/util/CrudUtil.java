/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * CrudUtil.java
 * Created on Feb 10, 2008, 2:03:48 PM
 */
package com.ideyatech.core.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideyatech.core.InvalidImplementationException;
import com.ideyatech.core.bean.Auditable;
import com.ideyatech.core.bean.BaseCriteria;
import com.ideyatech.core.bean.BaseEntity;

/**
 * @author allanctan
 *
 */
public class CrudUtil {
	
    private static Logger _log = Logger.getLogger(CrudUtil.class);
    
    /**
     * Creates the logging message for new audit logs 
     * @param obj
     * @return
     */
    public static String buildCreateMessage(Auditable obj) {
    	StringBuffer message = new StringBuffer("Added ");
    	message.append(obj.getClass().getSimpleName())  // class name
    		.append(" ")
    		.append(obj.getPrimaryField())
    		.append(":")
    		.append(retrieveObjectValue(obj, obj.getPrimaryField()).toString())
    		.append(" - ");
    	// loop through the fields list
		List<String> auditFields = obj.getAuditableFields();
		int count = 0;
		for (String property:auditFields) {
			Object ret = retrieveObjectValue(obj, property);
			if (ret!=null && ret.toString().trim().length()>0) {
				if (count > 0) 
					message.append(" and ");
				message.append(property)
					.append("=")
					.append(ret.toString());
				count++;
			}
		}
    	
    	return message.toString();    	
    }
    
    /**
     * Creates the logging message for update audit logs 
     * @param obj
     * @return
     */ 
    public static String buildUpdateMessage(Auditable oldObject, Auditable newObject) {
    	
    	StringBuffer message = new StringBuffer("Changed ");
    	message.append(oldObject.getClass().getSimpleName())  // class name
    		.append(" ")
    		.append(oldObject.getPrimaryField())
    		.append(":")
    		.append(retrieveObjectValue(oldObject, oldObject.getPrimaryField()).toString())
    		.append(" - ");
    	// loop through the fields list
		List<String> auditFields = oldObject.getAuditableFields();
		int count = 0;
		for (String property:auditFields) {
			Object oldValue = retrieveObjectValue(oldObject, property);
			Object newValue = retrieveObjectValue(newObject, property);
			if (!oldValue.equals(newValue)) {
				if (count > 0) 
					message.append(" and ");
				message.append(property)
					.append(" from '")
					.append(oldValue.toString())
					.append("' to '")
					.append(newValue.toString())
					.append("'");
				count++;
			}
		}
    	
    	return message.toString();    	
    }
    
    /**
     * Builds the query string appended to queryByExample
     * @param example
     * @param exactMatch
     * @return
     */
    public static String buildJpaQueryString(BaseCriteria example, boolean exactMatch) {
		int count = 0;
		StringBuffer clause = new StringBuffer(" where ");
		List<String> exampleFields = example.getSearchProperties();
		for (String property:exampleFields) {
			Object ret = retrieveObjectValue(example, property);
			if (ret!=null && ret.toString().trim().length()>0) {
				if (count > 0) 
					clause.append(" and ");
				if (String.class.isAssignableFrom(ret.getClass()) && !exactMatch) {
					clause.append(property)
						.append(" like '%")
						.append(ret.toString())
						.append("%'");
				} else if(BaseEntity.class.isAssignableFrom(ret.getClass())) {
					BaseEntity be = (BaseEntity) ret;
					clause.append(property)
					.append(".id")
					.append(" = ")
					.append(be.getId());
				} else if (Integer.class.isAssignableFrom(ret.getClass()) ||
						   Float.class.isAssignableFrom(ret.getClass()) ||
						   Long.class.isAssignableFrom(ret.getClass()) ||
						   Double.class.isAssignableFrom(ret.getClass()) ||
						   BigDecimal.class.isAssignableFrom(ret.getClass()) ) {
					// numeric types doesn't need to be enclosed in single quotes
					clause.append(property)
						.append(" = ")
						.append(ret.toString());
				} else {
					clause.append(property)
						.append(" = '")
						.append(ret.toString())
						.append("'");
				}
				count++;
			}
		}
	    if (count > 0)
	    	return clause.toString();
	    else
	    	return "";
    }

    /**
     * Retrieves the property name for a method name. 
     * (e.g. getName will return name)
     * @param methodName
     * @return
     */
    public static String getPropertyName(String methodName) {
    	if (StringUtil.isEmpty(methodName) || methodName.length()<=3)
    		return null;
    	if (methodName.startsWith("get") || methodName.startsWith("set")) {
    		String prop = methodName.substring(4);
    		char c = Character.toLowerCase(methodName.charAt(3));
    		return c+prop;
    	} else 
    		return null;
    }
    /**
     * Retrieves the getter method name for a given property.
     * (e.g. name will return getName)
     * @param propertyName
     * @return
     */
    public static String getGetterMethodName(String propertyName) {
    	if (StringUtil.isEmpty(propertyName) || propertyName.length()<=0)
    		return null;
    	char c = Character.toUpperCase(propertyName.charAt(0));
    	return "get"+c+propertyName.substring(1);
    }

	/**
	 * This method retrieves the object value that corresponds to the property specified.
	 * This method can recurse inner classes until specified property is reached.
	 * 
	 * For example:
	 * obj.firstName
	 * obj.address.Zipcode
	 * 
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Object retrieveObjectValue(Object obj, String property) {
		if (property.contains(".")) {
			// we need to recurse down to final object
			String props[] = property.split("\\.");
			try {
				Method method = obj.getClass().getMethod(getGetterMethodName(props[0]));
				Object ivalue = method.invoke(obj);
				if (ivalue==null)
					return null;
				return retrieveObjectValue(ivalue,property.substring(props[0].length()+1));
			} catch (Exception e) {
				_log.error("Failed to retrieve value for "+property);
				throw new InvalidImplementationException("CrudUtil","retrieveObjectValue",null,"", e);
			} 
		} else {
			// let's get the object value directly
			try {
				Method method = obj.getClass().getMethod(getGetterMethodName(property));
				return method.invoke(obj);
			} catch (Exception e) {
				_log.error("Failed to retrieve value for "+property);
				throw new InvalidImplementationException("CrudUtil","retrieveObjectValue",null,"", e);
			} 
		}
	}


	/**
	 * This method retrieves the object type that corresponds to the property specified.
	 * This method can recurse inner classes until specified property is reached.
	 * 
	 * For example:
	 * obj.firstName
	 * obj.address.Zipcode
	 * 
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Class<?> retrieveObjectType(Object obj, String property) {
		if (property.contains(".")) {
			// we need to recurse down to final object
			String props[] = property.split("\\.");
			try {
				Method method = obj.getClass().getMethod(getGetterMethodName(props[0]));
				Object ivalue = method.invoke(obj);
				return retrieveObjectType(ivalue,property.substring(props[0].length()+1));
			} catch (Exception e) {
				_log.error("Failed to retrieve value for "+property);
				throw new InvalidImplementationException("CrudUtil","retrieveObjectValue",null,"", e);
			} 
		} else {
			// let's get the object value directly
			try {
				Method method = obj.getClass().getMethod(getGetterMethodName(property));
				return method.getReturnType();
			} catch (Exception e) {
				_log.error("Failed to retrieve value for "+property);
				throw new InvalidImplementationException("CrudUtil","retrieveObjectValue",null,"", e);
			} 
		}
	}
}