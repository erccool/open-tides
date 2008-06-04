<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<c:if test="${newRow}">
<div id="row-${userGroup.id}" status="new">
</c:if>
  <span class="usergroup-name"><c:out value="${userGroup.name}"/></span>
  <span class="usergroup-description"><c:out value="${userGroup.description}"/></span> 
  <span class="usergroup-role">
	<c:forEach items="${userGroup.roleNames}" var="role">
  		<c:out value="${role}"/> &nbsp;-
  	</c:forEach> 
  </span>
  <span class="usergroup-action">
  	<ot:update_button id="${userGroup.id}" page="admin/usergroup.jspx"/>
  	<ot:delete_button id="${userGroup.id}" page="admin/usergroup.jspx" title="${userGroup.name}"/>
  </span> 
<c:if test="${newRow}">
</div>
</c:if>