<%--
	- delete_button
	- 
	- Creates the update button for Ajaxified action
	-
	- @param id - the primary key reference
	- @param page - the page to request
	- @param title - title to display during confirmation
	--%>
	
<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="id" required="true" type="java.lang.String" %>
<%@ attribute name="page" required="true" type="java.lang.String" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="prefix" type="java.lang.String" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<input type="image" src="${url_context}/images/db_remove.gif" title="<spring:message code="label.delete"/>"
		onclick="IDEYATECH.crud.confirmDelete('${prefix}-row-${id}','${url_context}/${page}?action=delete&codeId=${id}',
				'${title}');"/>
