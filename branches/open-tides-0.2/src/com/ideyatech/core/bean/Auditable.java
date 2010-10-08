/**
 * 
 */
package com.ideyatech.core.bean;

import java.util.List;

/**
 * This is the marker to indicate if a class will 
 * have audit trail.
 * 
 * @author allantan
 *
 */
public interface Auditable {
	
	/**
	 * Retrieve the primary key
	 * @return
	 */
	public Long getId();
	
	/**
	 * Returns the list of fields that should be audited.
	 * @return
	 */
	public List<String> getAuditableFields();
	
	/**
	 * Returns the field of the primary identifier (e.g. title).
	 * This field is used to uniquely identify the record.
	 * @return
	 */
	public String getPrimaryField();
	
}
