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

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.opentides.persistence.listener.EntityOwnerListener;

/**
 * All object model that needs to be filtered by record based on user profile
 * must extend this class.
 * 
 * Unfortunately, Hibernate filtering does not support MappedSuperClass at the
 * moment. So, filtering will have to be done for every entity class.
 * 
 * See: http://forum.hibernate.org/viewtopic.php?p=2380282#2380282
 * http://opensource.atlassian.com/projects/hibernate/browse/ANN-594
 * 
 * @author allantan
 * 
 */
@MappedSuperclass
@EntityListeners({ EntityOwnerListener.class })
public abstract class BaseProtectedEntity extends BaseEntity {

    /**
     * Auto-generated class UID.
     */
    private static final long serialVersionUID = 5860396433545961390L;

    /**
     * Owner of this object.
     */
    @Column(name = "OWNER")
    private String owner;
    
    /**
     * Office that owns this object.
     * In most cases, this is office of the owner.
     */
    @Column(name = "OWNER_OFFICE")
    private String ownerOffice;

    /**
     * Indicator if data should be filtered during search.
     */
    @Transient
    private transient boolean disableProtection;

    /**
     * Getter method of disableProtection.
     * 
     * @return the disableProtection
     */
    public final boolean isDisableProtection() {
        return this.disableProtection;
    }

    /**
     * Setter method of disableProtection
     * @param disableProtection
     *            the disableProtection to set
     */
    public final void setDisableProtection(final boolean disableProtection) {
        this.disableProtection = disableProtection;
    }

    /**
     * Getter method of owner.
     * 
     * @return the ownerId
     */
    public final String getOwner() {
        return this.owner;
    }

    /**
     * Setter method of owner.
     * 
     * @param owner
     *            the ownerId to set
     */
    public final void setOwner(final String owner) {
        this.owner = owner;
    }

    /**
     * Getter method of owner office.
     * 
     * @return the office
     */
    public final String getOwnerOffice() {
        return this.ownerOffice;
    }

    /**
     * Setter method of owner office.
     * 
     * @param ownerOffice
     *            the office to set
     */
    public final void setOwnerOffice(final String ownerOffice) {
        this.ownerOffice = ownerOffice;
    }
}
