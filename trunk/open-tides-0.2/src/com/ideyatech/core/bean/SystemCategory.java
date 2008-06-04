/**
 * 
 */
package com.ideyatech.core.bean;

/**
 * @author allantan
 *
 */
public interface SystemCategory {

	public String getName();
	
	public String getDescription();
	
	public SystemCategory[] getAllCategories();
	
	public SystemCategory getCategoryByName(String name);

}
