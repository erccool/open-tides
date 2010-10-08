/**
 * 
 */
package org.opentides.bean;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.opentides.persistence.listener.EntityOwnerListener;


/**
 * All object model that needs to be filtered by record 
 * based on user profile must extend this class.
 * 
 * Unfortunately, Hibernate filtering does not support MappedSuperClass
 * at the moment. So, filtering will have to be done for every
 * entity class. 
 * 
 * See: 
 *   http://forum.hibernate.org/viewtopic.php?p=2380282#2380282
 *   http://opensource.atlassian.com/projects/hibernate/browse/ANN-594
 * 
 * @author allantan
 *
 */
@MappedSuperclass
@EntityListeners({EntityOwnerListener.class})
public abstract class BaseProtectedEntity extends BaseEntity {

	private static final long serialVersionUID = 5860396433545961390L;
	
	@Column(name = "OWNER")
	private String owner;
	@Column(name = "GROUPID")
	private Long groupId;
	@Transient
	private transient boolean disableProtection;
	
	/**
	 * @return the disableProtection
	 */
	public boolean isDisableProtection() {
		return disableProtection;
	}
	/**
	 * @param disableProtection the disableProtection to set
	 */
	public void setDisableProtection(boolean disableProtection) {
		this.disableProtection = disableProtection;
	}
	/**
	 * @return the ownerId
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
}
