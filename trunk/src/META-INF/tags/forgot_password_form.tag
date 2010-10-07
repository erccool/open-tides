<%--
	- forgot_password_form.tag
	- 
	- Displays the different forms used in forgot password process.
	- 
	- @param type - can be 'request', 'confirm', 'change'
	-
	--%>	
	

<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="action" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${action == 'request'}">
    <spring:message code="msg.forgot-password-instructions"/>
    <form:form commandName="passwordReset">
		<spring:bind path="passwordReset.*">
		<c:if test="${status.error}">
			<p style="margin-bottom:2em;"><form:errors path="*" cssClass="errorBox" delimiter="<br/>"/></p>
		</c:if>
		</spring:bind>
	    <p><label for="emailAddress"><spring:message code="label.email-address"/></label> &nbsp;
	    		<form:input path="emailAddress" size="40" maxlength="100" cssStyle="text"/></p>
	    <p><label for="submit">&nbsp;</label><input type="submit" value="Submit"></p>
    </form:form>
</c:if> 

<c:if test="${action == 'confirm'}">
    <spring:message code="msg.confirm-password-reset-instructions"/>
    <form:form commandName="passwordReset">
		<spring:bind path="passwordReset.*">
		<c:if test="${status.error}">
			<p style="margin-bottom:2em;"><form:errors path="*" cssClass="errorBox" delimiter="<br/>"/></p>
		</c:if>
		</spring:bind>
	    <p><label for="emailAddress"><spring:message code="label.email-address"/></label><form:input path="emailAddress" size="40" maxlength="100" cssStyle="text"/></p>
	    <p><label for="token"><spring:message code="label.confirmation-code"/></label><form:input path="token" size="40" maxlength="100" cssStyle="text" /></p>	    
	    <p><label for="submit">&nbsp;</label><input type="submit" value="Submit"></p>	 
    </form:form>
</c:if>

<c:if test="${action == 'change'}">
    <spring:message code="msg.change-password-instructions"/>
    <form:form commandName="passwordReset" action="change-password.jspx">
		<spring:bind path="passwordReset.*">
		<c:if test="${status.error}">
			<p style="margin-bottom:2em;"><form:errors path="*" cssClass="errorBox" delimiter="<br/>"/></p>
		</c:if>
		</spring:bind>
    	<form:hidden path="emailAddress"/>
	    <p><label for="emailAddress"><spring:message code="label.email-address"/></label> ${passwordReset.emailAddress}</p>
	    <p><label for="password"><spring:message code="label.password"/></label><form:password path="password" size="40" maxlength="100" cssStyle="text"/></p>
	    <p><label for="confirmPassword"><spring:message code="label.confirm-password"/></label><form:password path="confirmPassword" size="40" maxlength="100"  cssStyle="text"/></p>
	    <p><label for="submit">&nbsp;</label><input type="submit" value="Submit"></p>
    </form:form>
</c:if>