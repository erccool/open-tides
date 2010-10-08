/**
 * 
 */
package org.opentides.bean;

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
	public List<AuditableField> getAuditableFields();
	
	/**
	 * Returns the field of the primary identifier (e.g. title).
	 * This field is used to uniquely identify the record.
	 * @return
	 */
	public AuditableField getPrimaryField();
	
	/**
	 * Returns whether to skip audit for this object. 
	 * This field can be used to programmatically disable logging
	 * of auditable objects.
	 * @return
	 */
	public Boolean skipAudit();
	
	/**
	 * Returns customized audit log message. When empty, audit logging
	 * uses standard audit message.
	 * @return
	 */
	public String getAuditMessage();
	
	/**
	 * Returns a reference for this audit object. 
	 * Useful for querying detail objects from a reference.
	 * @return
	 */
	public String getReference();
		
	/**
	 * Returns the user ID who made the change.
	 * This is needed because AcegiUtil is not able to return 
	 * correct user session on Hibernate Interceptor.
	 * 
	 * @return
	 */
	public Long getUserId();
	
	/**
	 * Sets the value of userId based on Acegi Context implemented 
	 * by BaseEntity.
	 */
	public void setUserId();
}
