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
			<div class='errorBox'>
			<spring:message code='err.login-failed-please-try-again'/><br/>
            </div>
        </c:if>
    </div>
    <p id="loginUsernameSection"><label for="username"><span><spring:message code="label.username"/></span></label><input type='text' name='j_username'/></p>
    <p id="loginPasswordSection"><label for="password"><span><spring:message code="label.password"/></span></label><input type='password' name='j_password'></p>
    <p id="loginAutoLoginSection"><label for="auto-login">&nbsp;</label><input type="checkbox" name="_acegi_security_remember_me"> <spring:message code="msg.remember-me"/></p>
    <p id="loginSubmitSection"><label for="sign-in">&nbsp;</label><input type="submit" value="Login" onclick="IDEYATECH.login.ajaxLogin();return false" > </p>
    <c:if test="${forgot_password_check eq 'true'}">
    <p id="loginForgotPasswordSection" class="forgot-password"><a href="<c:url value='request-password-reset.jspx'/>"><spring:message code="msg.forgot-your-password"/></a></p>
    </c:if>
  </form>