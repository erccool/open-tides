<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="org.acegisecurity.context.SecurityContextHolder" %>
<%@ page import="org.acegisecurity.Authentication" %>
<%@ page import="org.acegisecurity.ui.AccessDeniedHandlerImpl" %>

    <table width="100%" height="100%" border="0" cellpadding="5" cellspacing="15">
	<tr>
		<td height="200" align="center">
		<hr class="hrbroken" /> 		
		<strong><spring:message code="msg.sorry-but-you-dont-have-permission-to-access-this-page"/></strong>
		<hr class="hrbroken" />
		</td>
	</tr><tr>
		<td align="center" valign="top">
		<%= request.getAttribute(AccessDeniedHandlerImpl.ACEGI_SECURITY_ACCESS_DENIED_EXCEPTION_KEY)%>
		<%		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth != null) { %>
					Authentication object as a String: <%= auth.toString() %><BR><BR>
		<%      } %>  
		</td>
	</tr>
	</table>