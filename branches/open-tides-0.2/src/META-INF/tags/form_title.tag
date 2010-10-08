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
<%@ attribute name="label" required="true" type="java.lang.String" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="title">
<c:if test="${isNew}">
  <spring:message code="label.new" />
</c:if>
<c:if test="${!isNew}">
  <spring:message code="label.edit" />
</c:if>
  <spring:message code="${label}" />
</div>