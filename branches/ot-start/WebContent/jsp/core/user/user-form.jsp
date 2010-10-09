<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="idy" uri="http://www.ideyatech.com/tides"%>

<td colspan="5">
<form:form id="user-form-${user.id}" commandName="user" action="" cssClass="user">
	<idy:form_title isNew="${user.isNew}" label="label.user"/>
	<div class="content">	
		<spring:bind path="user.*">
		<c:if test="${status.error}">
			<div class='errorBox'>
				<form:errors path="*" />
            </div>
		</c:if>
		</spring:bind>
    	<p> <label for="fname"><spring:message code="label.firstname" /></label>
			<form:input path="firstName" size="40" maxlength="100" />  <sup class="required">*</sup></p>
		<p> <label for="lname"><spring:message code="label.lastname" /></label>
			<form:input path="lastName" size="40" maxlength="100" /> <sup class="required">*</sup></p>	
		<p> <label for="emailAddress"><spring:message code="label.email-address" /> </label>
			<form:input path="emailAddress" size="40" maxlength="100" /> <sup class="required">*</sup></p>
		<p> <label for="username"><spring:message code="label.username" /> </label>
			<form:input path="credential.username" size="40" maxlength="100" /> <sup class="required">*</sup></p>
		<p> <label for="password"><spring:message code="label.password" /> </label>
			<form:password path="credential.password" size="40" maxlength="100" /> <sup class="required">*</sup></p>
		<p> <label for="confirm-password"><spring:message code="label.confirm-password" /> </label>
			<form:password path="credential.confirmPassword" size="40" maxlength="100" /> <sup class="required">*</sup></p>		
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
		<p>
			<label for="buttons">&nbsp;</label>	
		  	<idy:submit_button id="${user.id}" page="admin/people.jspx" formName="user-form-${user.id}" prefix="user"/>
  			<idy:cancel_button id="${user.id}" page="admin/people.jspx" prefix="user"/>
		</p>
	</div>
</form:form>
</td>