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

package org.opentides.persistence.listener;

import javax.persistence.PrePersist;

import org.opentides.bean.BaseProtectedEntity;
import org.opentides.util.StringUtil;

/**
 * This listener is set owner value to user who created the object.
 * 
 * @author allantan
 * 
 */
public class EntityOwnerListener {
    /**
     * Listener method to set owner and owner office.
     * 
     * @param entity - object tracked by listener
     */
    @PrePersist
    public final void setOwner(final BaseProtectedEntity entity) { 
        if (StringUtil.isEmpty(entity.getOwner())) {
            entity.setOwner(entity.getAuditUsername());
        }
        if (StringUtil.isEmpty(entity.getOwnerOffice())) {
            entity.setOwnerOffice(entity.getAuditOfficeName());
        }
    }
}
