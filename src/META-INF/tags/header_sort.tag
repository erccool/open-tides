<%--
	- sort_header
	--%>

<%@ tag isELIgnored="false" body-content="empty" %>
<%@ attribute name="headerField" required="true" type="java.lang.String" %>
<%@ attribute name="headerLabel" type="java.lang.String" %>
<%@ attribute name="prefix" required="true" type="org.opentides.bean.Sortable" %>
<%@ attribute name="searchFormId" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<a href="javascript:void(0);" onclick="IDEYATECH.sort.sortByHeader('${headerField}', '${searchFormId}'); return false;">
	<span class="column-title"><spring:message code="${headerLabel}" /></span>
	<c:if test="${ prefix.orderOption == headerField && prefix.orderFlow == 'ASC' }">
		<span id="arrow-up"></span>
	</c:if>
	<c:if test="${ prefix.orderOption == headerField && prefix.orderFlow == 'DESC' }">
		<span id="arrow-down"></span>
	</c:if>
</a>