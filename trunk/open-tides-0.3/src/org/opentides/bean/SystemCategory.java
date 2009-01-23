/**
 * 
 */
package org.opentides.bean;


/**
 * Category where system codes are grouped. Usually, there is one 
 * implementation of this interface for every application. 
 * 
 * @author allantan
 */
public interface SystemCategory {

	public String getName();
	
	public String getDescription();
	
	public SystemCategory[] getAllCategories();
	
	public SystemCategory getCategoryByName(String name);

}