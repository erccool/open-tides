package com.ideyatech.core.bean;

import org.opentides.bean.SystemCategory;

public enum VeeSystemCategory implements SystemCategory {
	COUNTRY("List of Countries");
	
	private final String description;
	
	private VeeSystemCategory(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return this.toString();
	}

	@SuppressWarnings("static-access")
	public SystemCategory[] getAllCategories() {
		return this.values();
	}
	
	@SuppressWarnings("static-access")
	public SystemCategory getCategoryByName(String name) {
		return this.valueOf(name);
	}
	
	public static final SystemCategory createInstance() {
		return VeeSystemCategory.valueOf("COUNTRY");
	}

}
