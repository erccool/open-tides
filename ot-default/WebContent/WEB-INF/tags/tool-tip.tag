<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<%@ attribute name="formName" required="true" type="java.lang.String"%>
<%@ attribute name="attributeName" required="true" type="java.lang.String"%>

<c:forEach items="${applicationScope.applicationTextContainerBean.toolTips }" var="toolTip">
	<c:if test="${toolTip.formName eq formName && toolTip.attributeName eq attributeName}">
	<%--PUT CODES HERE --%>
	<img src="${url_context}<spring:theme code='image_folder'/>icon-information.png" class="icon-information jtooltip" jt_pos="T71" jt_val="${toolTip.toolTipText}" />
	</c:if>
</c:forEach>