/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */

package org.opentides.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
@Deprecated
public class Category extends BaseEntity implements Serializable, BaseCriteria {

    /**
     * Auto-generated class UID.
     */
    private static final long serialVersionUID = 3727440262397230958L;

    /**
     * Name of the category.
     */
    @Column(name = "CATEGORY_NAME", unique = true, nullable = false)
    private String name;

    /**
     * Description of the category.
     */
    @Column(name = "CATEGORY_DESC")
    private String description;

    /**
     * Default constructor.
     */
    public Category() {
    }

    /**
     * Getter method for description.
     * 
     * @return category description
     */
    public final String getDescription() {
        return this.description;
    }

    /**
     * Setter method for description.
     * 
     * @param description to set
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter method for category name.
     * 
     * @return category name
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Setter method for category name.
     * 
     * @param name to set
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Searchable properties.
     * 
     * @return search list
     */
    public final List<String> getSearchProperties() {
        final List<String> props = new ArrayList<String>();
        props.add("name");
        props.add("description");
        return props;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
    
}
