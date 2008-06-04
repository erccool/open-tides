<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

<div class="form">
<form:form commandName="systemCode" id="systemCode${systemCode.id}"action="">
	<idy:form_title isNew="${systemCode.isNew}" label="label.system-look-up-code"/>
	<div class="content">	
    	<div class="errorBox">
    		<form:errors path="*"/>
    	</div>
    	<p> <label for="category"><spring:message code="label.category" /></label>
			<form:select path="category">
				<form:option value="0">Select</form:option>
				<form:options items="${categories}" itemValue="name" itemLabel="name" />
			</form:select> </p>
		<p> <label for="key"><spring:message code="label.key" /></label>
			<form:input path="key" maxlength="20" /> </p>
		<p> <label for="value"><spring:message code="label.value" /></label>
			<form:input path="value" maxlength="50" /> </p>
		<div class="button">
		  	<ot:submit_button id="${systemCode.id}" page="admin/lookup.jspx" formName="systemCode${systemCode.id}"/>
  			<ot:cancel_button id="${systemCode.id}" page="admin/lookup.jspx"/>
		</div>
	</div>
</form:form>
</div>