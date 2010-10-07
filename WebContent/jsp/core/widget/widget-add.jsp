<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
		<c:if test="${frameRedirect == true}">
			<script type="text/javascript">window.top.location="home.jspx";</script>
		</c:if>
        <div class="main" id="widget">
            <div class="title-wrapper">
                <div class="title" id="widget-title"><span><spring:message code="label.add-widget" /></span></div>
            </div> 
            <div class="contents">
				<form:form commandName="userWidgets" id="userWidgets" action="widget-add.jspx">
            	<table cellspacing="1" class="widget_table">
                    <tr>
						<th>&nbsp;</th>
						<th><spring:message code="label.widget-name" /></th>
						<th><spring:message code="label.widget-description" /></th>
					</tr>
					<c:forEach items="${widgets}" var="record" varStatus="status">
					<tr>
						<td class="widget_checkbox">
							<c:if test="${record.installed}">
							Already Installed
							</c:if>
							<c:if test="${!record.installed}">
							<input type="checkbox" name="checkboxes" value="${record.id}"/>
							</c:if>
						</td>
						<td class="widget_name">
							<c:choose>	
								<c:when test="${not empty record.files}">
									<c:forEach items = "${record.files}" var="fileInfo">
										<img src="view-image.jspx?FileInfoId=${fileInfo.id}" width="150"/>
									</c:forEach>	
								</c:when>
								<c:otherwise>
									<img src="${url_context}<spring:theme code="no_thumbnail"/>" width="150"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td class="widget_description"> 
							<p class="title">${record.title}</p> 
							${record.description} </td>
					</tr>
					</c:forEach>
				</table>
				<div id="save-button" class="button">
					<input type="hidden" id="mergedCheckboxes" name="mergedCheckboxes" value="">
					<input class="button" type="button" value="Save" onclick="IDEYATECH.checkbox.mergeSubmit('userWidgets','checkboxes','mergedCheckboxes')" />
					<input class="button" type="button" value="Cancel" onclick="top.location='home.jspx'" />
				</div>
				</form:form>
        	</div><!-- end of contents -->
        </div>
