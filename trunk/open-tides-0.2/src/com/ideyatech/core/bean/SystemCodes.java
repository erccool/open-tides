package com.ideyatech.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SYSTEM_CODES")
/**
 * The system codes class is a lookup table used for drop-downs.
 * Developer must ensure category is consistently used in the application.
 */
public class SystemCodes extends BaseEntity implements Serializable, BaseCriteria, Auditable {
	
	private static final long serialVersionUID = -4142599915292096152L;
	@Column(name = "KEY_", unique=true)
	private String key;
	@Column(name = "VALUE_")
	private String value;
	@Column (name="CATEGORY_")
	private String category;
	
	public SystemCodes() {
	}
	
	public SystemCodes(String category, String key, String value) {
		super();
		setCategory(category);
		this.key = key;
		this.value = value;
	}	
	
	public SystemCodes(SystemCategory systemCategory, String key, String value) {
		super();
		setCategory(systemCategory.toString());
		this.key = key;
		this.value = value;
	}
	
	public SystemCodes(SystemCategory systemCategory, String key) {
		super();
		setCategory(systemCategory.toString());
		this.key = key;
	}
	
	public SystemCodes(String key) {
		super();
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SystemCodes other = (SystemCodes) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	public List<String> getSearchProperties() {
		List<String> props = new ArrayList<String>();
		props.add("key");
		props.add("value");
		props.add("category");
		return props;
	}

	public List<String> getAuditableFields() {
		List<String> props = new ArrayList<String>();
		props.add("key");
		props.add("value");
		props.add("category");
		return props;
	}

	public String getPrimaryField() {
		return "key";
	}	
}