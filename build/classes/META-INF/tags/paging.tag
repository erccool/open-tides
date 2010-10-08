<%--
	- paging
	- 
	- Generates paging hyperlinks for a given BaseResults object
	-
	- @param results - the results object, must be of type BaseResults
	- @param baseURL - URL to use in hyperlinks
	- @param pageParamName - page parameter to pass in baseURL 
	- @param displaySummary - boolean to indicate whether summary will be displayed
	- @param displayPageLinks - boolean to indicate whether page links will be displayed
	--%>
<%@ tag dynamic-attributes="attributes" isELIgnored="false" body-content="empty" %>
<%@ attribute name="results" required="true" type="com.ideyatech.core.bean.BaseResults" %>
<%@ attribute name="baseURL" required="true" %>
<%@ attribute name="pageParamName" required="true" %>
<%@ attribute name="displaySummary" required="false" type="java.lang.Boolean" %>
<%@ attribute name="displayPageLinks" required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${(results.totalResults == 0) && (displaySummary)}">
    <div class="pagingSummary"> 
    <spring:message code="msg.no-results-found"/>
    </div>
</c:if>
<c:if test="${results.totalResults > 0}">
	<c:if test="${displaySummary}">
    <div class="pagingSummary"> 
	    <span class="records"> Records <strong>${results.startIndex+1}</strong> - <strong>${results.endIndex+1}</strong> of <strong>${results.totalResults}</strong></span>
	    <span class="searchTime"> (${results.searchTime} seconds)</span>
    </div>
	</c:if>	
	
	<c:if test="${results.totalPages > 1 && displayPageLinks}">
	<div class="pagingLinks">
		<!-- display first/previous page -->		
		<c:if test="${results.startPage != 1}">
	        <c:url var="linkFirst" value="${baseURL}">
	            <c:param name="${pageParamName}" value="1"/>
	        </c:url>
            <strong><a href="${linkFirst}" title="First Page" class="ilink">&lt;&lt;</a></strong>
        </c:if>
		<c:if test="${results.currPage > 1}">
	        <c:url var="linkPrev" value="${baseURL}">
	            <c:param name="${pageParamName}" value="${results.currPage-1}"/>
	        </c:url>
            <strong><a href="${linkPrev}" title="Previous Page" class="ilink">&lt;</a></strong>            
		</c:if>
		<!--  display paging -->
	    <c:forEach begin="${results.startPage}" end="${results.endPage}" step="1" var="page">
	        <c:url var="link" value="${baseURL}">
	            <c:param name="${pageParamName}" value="${page}"/>
	        </c:url>
	        <c:choose><c:when test="${page == results.currPage}">
	            <strong>${page}</strong>
	        </c:when>
	        <c:otherwise>
	            <strong><a href="${link}" class="nlink">${page}</a></strong>
	        </c:otherwise></c:choose>
	    </c:forEach>
	    <!-- display last/next page -->
		<c:if test="${results.currPage < results.endPage}">
	        <c:url var="linkNext" value="${baseURL}">
	            <c:param name="${pageParamName}" value="${results.currPage+1}"/>
	        </c:url>
            <strong><a href="${linkNext}" title="Next Page" class="ilink">&gt;</a></strong>            
		</c:if>
		<c:if test="${results.endPage < results.totalPages}">
	        <c:url var="linkLast" value="${baseURL}">
	            <c:param name="${pageParamName}" value="${results.totalPages}"/>
	        </c:url>
            <strong><a href="${linkLast}" title="Last Page" class="ilink">&gt;&gt;</a></strong>
        </c:if>
	</div>
	</c:if>
</c:if>