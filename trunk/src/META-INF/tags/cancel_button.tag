<%--
	- cancel_button
	- 
	- Creates the cancel button for Ajaxified action
	-
	- @param id - the primary key reference
	- @param page - the page to request
	- @param formName - name of form
	--%>
	
<%@ tag isELIgnored="false" body-content="empty" %>

<%@ attribute name="id" required="true" type="java.lang.Long" %>
<%@ attribute name="page" required="true" type="java.lang.String" %>
<%@ attribute name="prefix" type="java.lang.String" %>
<%@ attribute name="urlParam" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var='varName' value='${fn:replace(prefix, "-", "_")}'/>

<c:if test="${id>0}">
  <c:if test="${ot_version == '0.1'}"> 
	<input type="button" value="<spring:message code="label.cancel" />" 
		onclick="IDEYATECH.util.loadPage('row-${id}',
			'${url_context}/${page}?action=read&codeId=${id}');"/>
  </c:if>
  <c:if test="${ot_version == '0.2'}"> 
	<input type="button" value="<spring:message code="label.cancel" />" 
		onclick="
		var args_${prefix}_cancel_${id} = { 
	    	divId: '${prefix}-row-${id}',
	    	multipart: '<c:out value="${multipart}" default="false"/>'
	    }
	    IDEYATECH.util.loadPage('${url_context}/${page}?action=read&codeId=${id}',
	    	args_${prefix}_cancel_${id});"/>
  </c:if>
  <c:if test="${ot_version == '0.3'}"> 
	<input type="button" value="<spring:message code="label.cancel" />" 
		onclick="
		var args_${varName}_cancel_${id} = { 
	    	divId: '${prefix}-row-${id}',
	    	multipart: '<c:out value="${multipart}" default="false"/>'
	    }
	    IDEYATECH.util.loadPage('${url_context}/${page}?action=read&codeId=${id}${urlParam}',
	    	args_${varName}_cancel_${id});"/>
  </c:if>
  <c:if test="${ot_version == '0.5'}"> 
	<input type="button" value="<spring:message code="label.cancel" />" 
		onclick="
		var args_${varName}_cancel_${id} = { 
	    	divId: '${prefix}-row-${id}'
	    }
	    IDEYATECH.util.loadPage('${url_context}/${page}?action=read&codeId=${id}${urlParam}',
	    	args_${varName}_cancel_${id});"/>
  </c:if>
</c:if>

<c:if test="${id==0}">
  <c:if test="${ot_version == '0.1'}"> 
	<input type="button" value="<spring:message code="label.cancel" />" 
		onclick="IDEYATECH.crud.cancelNew();"/>
  </c:if>
  <c:if test="${ot_version == '0.2'}"> 
	<input type="button" value="<spring:message code="label.cancel" />" 
		onclick="IDEYATECH.crud.cancelNew('${prefix}-');"/>
  </c:if>
  <c:if test="${ot_version == '0.3'}"> 
	<input type="button" value="<spring:message code="label.cancel" />" 
		onclick="IDEYATECH.crud.cancelNew('${prefix}-');"/>
  </c:if>
  <c:if test="${ot_version == '0.5'}"> 
	<input type="button" value="<spring:message code="label.cancel" />" 
		onclick="IDEYATECH.crud.cancelNew('${prefix}-');"/>
  </c:if>
</c:if>			