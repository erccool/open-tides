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
package org.opentides;

/**
 * This exception is called to handle additional error occurred on the
 * controller. (e.g. unable to upload file)
 * 
 * @author allanctan
 */
public class ControllerException extends RuntimeException {

    /**
     * Generated class UID
     */
    private static final long serialVersionUID = -1063625975927856754L;

    /**
     * Default constructor.
     */
    public ControllerException() {
        super();
    }

    /**
     * Inherited constructor from RuntimeException.
     * 
     * @param message - error message
     * @param cause - root case
     */
    public ControllerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Inherited constructor from RuntimeException.
     * 
     * @param message - error message
     */
    public ControllerException(final String message) {
        super(message);
    }

    /**
     * Inherited constructor from RuntimeException.
     * 
     * @param cause - root cause
     */
    public ControllerException(final Throwable cause) {
        super(cause);
    }
}
