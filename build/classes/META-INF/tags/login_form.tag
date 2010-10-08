<%--
	- login_form
	- 
	- Displays the login form
	-
	--%>	
	

<%@ tag isELIgnored="false" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

   <form id="loginForm" action="<c:url value='j_acegi_security_check'/>" method="POST">
   	<div id="loginMessage" align="left">
		<c:if test="${not empty param.login_error}">
			<div class='errorBox'><spring:message code='err.login-failed-please-try-again'/><br/>
             </div>
        </c:if>
    </div>
    <p><label for="username"><spring:message code="label.username"/></label><input type='text' name='j_username'/></p>
    <p><label for="password"><spring:message code="label.password"/></label><input type='password' name='j_password'></p>
    <p><label for="auto-login">&nbsp;</label><input type="checkbox" name="_acegi_security_remember_me"> <spring:message code="msg.remember-me"/></p>
    <p><label for="sign-in">&nbsp;</label><input type="submit" value="Login" onclick="IDEYATECH.login.ajaxLogin();return false" > </p>
    <p class="forgot-password"><a href="<c:url value='request-password-reset.jspx'/>"><spring:message code="msg.forgot-your-password"/></a></p>
  </form>