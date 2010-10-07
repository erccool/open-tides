<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<idy:header title_webpage="label.system-look-up-code" active="systemcodes">
  <script src="${url_yui}/yahoo-dom-event/yahoo-dom-event.js"></script>
  <script src="${url_yui}/animation/animation-min.js"></script>
  <script src="${url_yui}/connection/connection-min.js"></script>
  <script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
  
  <script type="text/javascript">
  	function checkNewCategory(fromDropdown) {
 		var dropDownHTML  ='<label for="category"><spring:message code="label.category" /></label><select name="category" onclick="javascript:checkNewCategory(true);"><option value="0">Select a Category</option><option value="0">-- New Category --</option><c:forEach items="${categories}" var="record" varStatus="status"><option>${record.category}</option></c:forEach></select>';
 		var textFieldHTML ='<label for="category"><spring:message code="label.category" /></label><input type="text" name="category" id="categoryText"/> <a href="javascript:void(0)" onclick="javascript:checkNewCategory(false)">Cancel</a>';
 		var ni = yDom.get('categorySelector');
  		if (fromDropdown) {
  			// check if "New Category" is selected
  			var select = yDom.get('categorySelect');
	  		if (select.selectedIndex==1)
	  			ni.innerHTML = textFieldHTML;
  		} else
  			ni.innerHTML = dropDownHTML;
	}
  </script>
</idy:header>

    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main" id="systemcodes">

            <div class="title-wrapper">
            	<div class="title" id="systemcodes-title"><span><spring:message code="label.system-look-up-code" /></span></div>
            </div> 
            
            <div class="contents">
	            <div id="search-criteria">
					<form:form commandName="systemCode" id="systemCodeSearch" action="${url_context}/admin/lookup.jspx">
						<p>
						<label for="category"><spring:message code="label.category" /></label>
						<form:select path="category">
							<form:option value="">Select a Category</form:option>
							<form:options items="${categories}" itemValue="category" itemLabel="category" />
						</form:select>
						</p>
						<p>
						<label for="key"><spring:message code="label.key" /></label>
						<form:input path="key" maxlength="20" />
						</p>
						<p>
						<label for="value"><spring:message code="label.value" /></label>
						<form:input path="value" maxlength="50" />		
						</p>
						<ot:sort_input searchFormId="systemCodeSearch"/>
						<input type="submit" name="Submit_" value="<spring:message code="label.submit" />" />
					</form:form>
				</div>
				<hr class="broad"/>
            	<table class="admin-table">
            		<thead id="lookup-table-results">
            		<tr>
            			<th><ot:header_sort headerField="category" headerLabel="label.category" prefix="${systemCode}" searchFormId="systemCodeSearch"/></th>
            			<th><ot:header_sort headerField="key" headerLabel="label.key" prefix="${systemCode}" searchFormId="systemCodeSearch"/></th>
            			<th><ot:header_sort headerField="value" headerLabel="label.value" prefix="${systemCode}" searchFormId="systemCodeSearch"/></th>
            			<th></th>
            		</tr>
            		</thead>
            		<tbody id="lookup-table-results">
            		<c:forEach items="${results.results}" var="record" varStatus="status">
            		<tr id="lookup-row-${record.id}" class="row-style-${status.count%2}">
            			<td><c:out value="${record.category}" /></td>
            			<td><c:out value="${record.key}" /></td>
            			<td><c:out value="${record.value}" /></td>
                  		<td>
							<ot:update_button id="${record.id}" page="admin/lookup.jspx" prefix="lookup"/>
                    		<ot:delete_button id="${record.id}" page="admin/lookup.jspx" title="${record.key}" prefix="lookup"/>                  		
                  		</td>	
            		</tr>
            		</c:forEach>
            		</tbody>
            		<tr id="lookup-row-new">
            			<td colspan="4">  	
            			</td>
            		</tr>
            		
            	</table>
            	<ot:add_button page="admin/lookup.jspx" prefix="lookup"/>
            	<ot:paging results="${results}" baseURL="/admin/lookup.jspx" pageParamName="page" displaySummary="false" displayPageLinks="true" />	            	
        	</div>
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->
<!-- FOOT -->
<idy:footer />