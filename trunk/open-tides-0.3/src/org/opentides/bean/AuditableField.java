/**
 * 
 */
package org.opentides.bean;

/**
 * AuditableField contains definition of fields used for audit logging.
 * Classes that implemented Auditable are required to implement 
 * getAuditableFields which returns list of AuditableField. 
 * Only fields defined with AuditableField are tracked for audit logging.
 * 
 * @author allantan
 *
 */
public class AuditableField {

	private String title;
	private String fieldName;
	
	/**
	 * Creates a new AuditableField instance.
	 * @param fieldName
	 * @param title
	 */
	public AuditableField(String fieldName, String title) {
		this.fieldName = fieldName;
		this.title = title;
	}
	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the fieldName
	 */
	public final String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public final void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
