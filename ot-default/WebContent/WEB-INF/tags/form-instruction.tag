<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<%@ attribute name="formName" required="true" type="java.lang.String"%>

<c:set var="instructionText"/>
<c:forEach items="${applicationScope.applicationTextContainerBean.formInstructions }" var="instruction">
	<c:if test="${instruction.formName eq formName}">
		<c:set var="instructionText" value="${instruction.instructionText}"/>
	</c:if>
</c:forEach>

<c:if test="${not empty instructionText}">
<div id="instructions" class="instruction-highlight">
	${instructionText }
</div> 
</c:if>
