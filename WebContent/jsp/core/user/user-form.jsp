<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="idy" uri="http://www.ideyatech.com/tides"%>

<div class="form">
<form:form commandName="user" action="">
	<idy:form_title isNew="${user.isNew}" label="label.user"/>
	<div class="content">	
    	<div class="errorBox">
    		<form:errors path="*"/>
    	</div>
    	<p> <label for="fname"><spring:message code="label.firstname" /></label>
			<form:input path="firstName" size="40" maxlength="100" /> </p>
		<p> <label for="lname"><spring:message code="label.lastname" /></label>
			<form:input path="lastName" size="40" maxlength="100" /> </p>	
		<p> <label for="emailAddress"><spring:message code="label.email-address" /></label>
			<form:input path="emailAddress" size="40" maxlength="100" /> </p>
		<p> <label for="password"><spring:message code="label.password" /></label>
			<form:input path="credential.password" size="40" maxlength="100" /> </p>
		<p> <label for="confirm-password"><spring:message code="label.confirm-password" /></label>
			<form:input path="credential.confirmPassword" size="40" maxlength="100" /> </p>		
		<p>	<label for="groups"><spring:message code="label.usergroups" /></label>	
			<c:if test="${groups != null}">
				<form:select path="groups" size="4" multiple="true">  					
	  				<form:options items="${groups}" itemValue="id" itemLabel="name"/>
				</form:select>
			</c:if>
			<c:if test="${groups == null}">
				<spring:message code="msg.no-existing-usergroups" />				
			</c:if>
		</p>
		<div class="button">
		  	<idy:submit_button id="${user.id}" page="admin/people.jspx" formName="user"/>
  			<idy:cancel_button id="${user.id}" page="admin/people.jspx"/>
		</div>
	</div>
</form:form>
</div>