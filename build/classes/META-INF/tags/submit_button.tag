<%--
	- submit_button
	- 
	- Creates the submit button for Ajaxified action
	-
	- @param id - the primary key reference
	- @param page - the page to request
	- @param formName - name of form
	--%>
	
<%@ tag isELIgnored="false" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ attribute name="id" required="true" type="java.lang.Long" %>
<%@ attribute name="page" required="true" type="java.lang.String" %>
<%@ attribute name="prefix" type="java.lang.String" %>
<%@ attribute name="multipart" type="java.lang.String" %>
<%@ attribute name="formName" required="true" type="java.lang.String" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="empty ${multipart}">
	<c:set property="multipart" value="false"/>
</c:if>

<c:if test="${id>0}">
<c:if test="${ot_version == '0.1'}"> 
	<input type="button" value="<spring:message code="label.submit" />" 
		onclick="IDEYATECH.util.submitForm('${formName}', 'row-${id}',
					'${url_context}/${page}','action=update&codeId=${id}');"/>
</c:if>
<c:if test="${ot_version == '0.2'}"> 
	<input type="button" value="<spring:message code="label.submit" />" 
		onclick="
	    var args_${prefix}_rownew = { 
	    	divId: '${prefix}-row-${id}', 
	    	postdata: 'action=update&codeId=${id}', 
	    	append: 'false', 
	    	formName: '${formName}',
	    	multipart: '${multipart}'
	    }
	    IDEYATECH.util.submitForm('${url_context}/${page}', args_${prefix}_rownew);"/>
</c:if>		
<c:if test="${ot_version == '0.3'}"> 
	<input type="button" value="<spring:message code="label.submit" />" 
		onclick="
	    var args_${prefix}_rownew = { 
	    	divId: '${prefix}-row-${id}', 
	    	postdata: 'action=update&codeId=${id}', 
	    	append: 'false', 
	    	formName: '${formName}',
	    	multipart: '${multipart}'
	    }
	    IDEYATECH.util.submitForm('${url_context}/${page}', args_${prefix}_rownew);"/>
</c:if>		
</c:if>

<c:if test="${id==0}">
<c:if test="${ot_version == '0.1'}"> 
	<input type="button" value="<spring:message code="label.submit" />" 
		onclick="IDEYATECH.util.submitForm('${formName}', 'row-new',
					'${url_context}/${page}','action=create',true);"/>
</c:if>
<c:if test="${ot_version == '0.2'}"> 
	<input type="button" value="<spring:message code="label.submit" />" 
		onclick="
	    var args_${prefix}_rownew = { 
	    	divId: '${prefix}-table-results', 
	    	postdata: 'action=create', 
	    	append: 'true', 
			evaluate: 'true',
	    	formName: '${formName}',
	    	multipart: '${multipart}'
	    }
	    IDEYATECH.util.submitForm('${url_context}/${page}', args_${prefix}_rownew);"/>
	</script>
</c:if>		
<c:if test="${ot_version == '0.3'}"> 
	<input type="button" value="<spring:message code="label.submit" />" 
		onclick="
	    var args_${prefix}_rownew = { 
	    	divId: '${prefix}-table-results', 
	    	postdata: 'action=create', 
	    	append: 'true', 
			evaluate: 'true',
	    	formName: '${formName}',
	    	multipart: '${multipart}'
	    }
	    IDEYATECH.util.submitForm('${url_context}/${page}', args_${prefix}_rownew);"/>
	</script>
</c:if>		
</c:if>