<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<idy:header title_webpage="label.users">
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
        <!-- LEFT - HOTEL_SEARCH -->
        <div class="graybig yui-u first">
          <idy:box-section position="top" />
          <div class="boxmiddle">
          	<idy:admin-navigation active="usergroup" />
          </div>
          <idy:box-section position="bottom" />
        </div>
        <!-- END LEFT - HOTEL_SEARCH -->
        <!-- RIGHT - BOOKING_SEARCH-->
        <div class="graybig yui-u">
          <idy:box-section position="top" />
          <div class="boxmiddle">
            <h1>
              <spring:message code="label.usergroups" />
            </h1>
            <div id="table-header">
              <span class="usergroup-name"><spring:message code="label.name"/></span>
			  <span class="usergroup-description"><spring:message code="label.description"/></span>
              <span class="usergroup-role"><spring:message code="label.role" /></span> 
              <span class="usergroup-action"></span>
            </div>
            <div id="table-results">
              <c:forEach items="${results.results}" var="record" varStatus="status">
                <div id="row-${record.id}" class="row-style-${status.count%2}">
                  <span class="usergroup-name"><c:out value="${record.name}" /></span>
			      <span class="usergroup-description"><c:out value="${record.description}" /></span>
                  <span class="usergroup-role">
                    <c:forEach items="${record.roleNames}" var="role">
                      <c:out value="${role}" /> | 
                    </c:forEach>
                  </span> 
                  <span class="usergroup-action">
                    <ot:update_button id="${record.id}" page="admin/usergroup.jspx" />
                    <ot:delete_button id="${record.id}" page="admin/usergroup.jspx" title="${record.name}" />
                  </span>
                </div>
              </c:forEach>
            </div>
            <div id="row-new"></div>
            <div id="add-new" class="button">
              <ot:add_button
	page="admin/usergroup.jspx" />
            </div>
          </div>
          <idy:box-section position="bottom" />
        </div>
        <!-- END RIGHT - BOOKING_SEARCH-->
      </div>
    </div>
  </div>
</div>
<idy:footer />
