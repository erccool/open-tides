<%--
	- form_title
	- 
	- Displays the purpose of form (either New or Edit)
	-
	- @param isNew - is this form containing new record
	- @param label - Name of the object in the form
	- 
	--%>
	
<%@ tag isELIgnored="false" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ attribute name="isNew" required="true" type="java.lang.Boolean" %>
<%@ attribute name="formName" required="true" type="java.lang.String" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${isNew}">
<div class="form-title" id="${formName}-addform-title">
  <span><spring:message code="label.new" />
  <spring:message code="label.${formName}" /></span>
</div></c:if>
<c:if test="${!isNew}">
<div class="form-title" id="${formName}-editform-title">
  <span><spring:message code="label.edit" />
  <spring:message code="label.${formName}" /></span>
</div></c:if>
