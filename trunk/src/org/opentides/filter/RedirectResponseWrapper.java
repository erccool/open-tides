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

package org.opentides.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * Support class for AcegiAjaxFilter
 * 
 * @author allanctan
 */
public class RedirectResponseWrapper extends HttpServletResponseWrapper {
    private String redirect;
 
    public RedirectResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }
 
    public String getRedirect() {
        return redirect;
    }
 
    public void sendRedirect(String string) throws IOException {
        this.redirect = string;
    }
}
