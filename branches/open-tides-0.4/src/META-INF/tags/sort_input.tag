<%--
	- sort_input
	- this hidden forms provide the values for the field to be used in sorting and its direction
	- this should be added on your -list view
	--%>

<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="searchFormId" required="true" type="java.lang.String" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:hidden path="orderOption" id="${searchFormId}-order-by"/>
<form:hidden path="orderFlow" id="${searchFormId}-order-flow"/>