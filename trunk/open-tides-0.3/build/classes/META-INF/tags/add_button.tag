<%--
	- add_button
	- 
	- Creates the add button for Ajaxified action
	-
	- @param page - the page to request
	--%>
	
<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="page" required="true" type="java.lang.String" %>
<%@ attribute name="prefix" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${ot_version == '0.1'}"> 
<input type="image" src="${url_context}/images/db_edit.ico" title="<spring:message code="label.edit"/>"
	onclick="IDEYATECH.util.loadPage('row-new',
			'${url_context}/${page}?action=create');"/>
</c:if>

<c:if test="${ot_version == '0.2'}"> 
<input type="image" src="${url_context}/images/db_add.gif" title="<spring:message code="label.add"/>"
	onclick="IDEYATECH.crud.addRecord('${prefix}-',
			'${url_context}/${page}?action=create');"/>
</c:if>

<c:if test="${ot_version == '0.3'}"> 
<input type="image" src="${url_context}/images/db_add.gif" title="<spring:message code="label.add"/>"
	onclick="IDEYATECH.crud.addRecord('${prefix}-',
			'${url_context}/${page}?action=create');"/> <a href="#" onclick="IDEYATECH.crud.addRecord('${prefix}-',
			'${url_context}/${page}?action=create');"> <spring:message code="label.add"/></a>
</c:if>