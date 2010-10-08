<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<c:if test="${newRow}">
<div id="row-${user.id}" status="new">
</c:if>
  <span class="user-fname"><c:out value="${user.firstName}"/></span>
  <span class="user-lname"><c:out value="${user.lastName}"/></span>  
  <span class="user-email"><c:out value="${user.emailAddress}"/></span>  
  <span class="user-role">
     <c:forEach items="${user.groups}" var="group">
     	<c:out value="${group.name}" /> 
     </c:forEach>
  </span>
  <span class="user-action">
  	<ot:update_button id="${user.id}" page="admin/people.jspx"/>
  	<ot:delete_button id="${user.id}" page="admin/people.jspx" title="${user.emailAddress}"/>
  </span> 
<c:if test="${newRow}">
</div>
</c:if>