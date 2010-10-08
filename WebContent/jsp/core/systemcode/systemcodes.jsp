<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<idy:header title_webpage="label.system-look-up-code">
  <script src="${url_yui}/yahoo-dom-event/yahoo-dom-event.js"></script>
  <script src="${url_yui}/animation/animation-min.js"></script>
  <script src="${url_yui}/connection/connection-min.js"></script>
  <script src="${url_context}/script/ideyatech-0.1/crud.js"></script>
</idy:header>
<!-- BODY -->

<div id="doc2" class="yui-t7">
  <div id="bd">
    <div id="yui-main">
      <div class="yui-gf top">
        <!-- LEFT - -->
        <div class="graybig yui-u first">
          <idy:box-section position="top" />
          <div class="boxmiddle">
          	<idy:admin-navigation active="systemcodes" />
          </div>
          <idy:box-section position="bottom" />
        </div>
        <!-- END LEFT  -->
        <!-- RIGHT -->
        <div class="graybig yui-u">
          <idy:box-section position="top" />
          <div class="boxmiddle">
			 <h1><spring:message code="label.system-look-up-code"/></h1>
			<div id="search-criteria">
				<form:form commandName="systemCode" id="systemCodeSearch" action="">
				<label for="category"><spring:message code="label.category" /></label>
				<form:select path="category">
					<form:option value="0">Select</form:option>
					<form:options items="${categories}" itemValue="name" itemLabel="name" />
				</form:select>
				<label for="key"><spring:message code="label.key" /></label>
				<form:input path="key" maxlength="20" />
				<label for="value"><spring:message code="label.value" /></label>
				<form:input path="value" maxlength="50" />		
				<input type="submit" name="Submit_" value="<spring:message code="label.submit" />" />
				</form:form>
			</div>
			<hr/>
			<ot:paging results="${results}" 
					 baseURL="lookup.jspx"
					 pageParamName="page"
					 displaySummary="false" displayPageLinks="true"/>
			<div id="table-header">
			  <span class="systemcode-category"><spring:message code="label.category"/></span>
			  <span class="systemcode-key"><spring:message code="label.key"/></span>
			  <span class="systemcode-value"><spring:message code="label.value"/></span>
			  <span class="action"></span>
			</div>
			<div id="table-results">
			<c:forEach items="${results.results}" var="record" varStatus="status">
			<div id="row-${record.id}" class="row-style-${status.count%2}">
			  <span class="systemcode-category"><c:out value="${record.category}"/></span>
			  <span class="systemcode-key"><c:out value="${record.key}"/></span>
			  <span class="systemcode-value"><c:out value="${record.value}"/></span>
			  <span class="action">
			  	<ot:update_button id="${record.id}" page="admin/lookup.jspx"/>
			  	<ot:delete_button id="${record.id}" page="admin/lookup.jspx" title="${record.key}"/>
			  </span> 
			</div>
			</c:forEach>
			</div>
			<div id="row-new"></div>
			<div id="add-new" class="button">
				<ot:add_button page="admin/lookup.jspx"/>
			</div>          
          </div>
          <idy:box-section position="bottom" />
        </div>
<!-- END RIGHT -->
      </div>
    </div>
  </div>
</div>
<idy:footer />