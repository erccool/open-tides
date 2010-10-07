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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This entity supports file upload functionality. Use this entity to enable
 * support for file upload. 
 * 
 * Standard upload file via BaseCrudController uses this class and stored into
 * UPLOAD_ENTITY table but complete database reference is stored in FILEINFO
 * with the corresponding UPLOAD_ID.
 * 
 * @author allantan
 */
@Entity
@Table(name = "UPLOAD_ENTITY")
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseUploadEntity extends BaseEntity implements Uploadable {

    /**
     * Auto generated class UID.
     */
    private static final long serialVersionUID = 3526479205130157604L;

    /** 
     * This is the property that will hold the file information.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "UPLOAD_ID")
    private List<FileInfo> files;

    /**
     * Getter method for files uploaded.
     * 
     * @return the files
     */
    public final List<FileInfo> getFiles() {
        return this.files;
    }

    /**
     * Setter method for files uploaded.
     * 
     * @param files
     *            the files to set
     */
    public final void setFiles(final List<FileInfo> files) {
        this.files = files;
    }

    /**
     * Returns the last uploaded file.
     * 
     * @return FileInfo
     */
    public final FileInfo getLastUploadedFileInfo() {
        final int size = this.files.size();
        if (size > 0) {
            return this.files.get(size - 1);
        } else {
            return null;
        }
    }
}
