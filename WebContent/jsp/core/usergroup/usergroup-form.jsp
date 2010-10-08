<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="idy" uri="http://www.ideyatech.com/tides"%>

<div class="form">
<form:form commandName="userGroup" action="">
	<idy:form_title isNew="${userGroup.isNew}" label="label.usergroup"/>
	<div class="content">	
    	<div class="errorBox">
    		<form:errors path="*"/>
    	</div>
    	<p> <label for="name"><spring:message code="label.name" /></label>
			<form:input path="name" size="40" maxlength="100" /> </p>
		<p> <label for="description"><spring:message code="label.description" /></label>
			<form:input path="description" size="40" maxlength="100" /> </p>
		<p>	<label for="roles"><spring:message code="label.roles" /></label>		
		<c:forEach items="${roles}" var="role">
			<form:checkbox path="roleNames" value="${role.key}"/>
			<c:out value="${role.value}"/>
		</c:forEach>
		</p>
		<div class="button">
		  	<idy:submit_button id="${userGroup.id}" page="admin/usergroup.jspx" formName="userGroup"/>
  			<idy:cancel_button id="${userGroup.id}" page="admin/usergroup.jspx"/>
		</div>
	</div>
</form:form>
</div>