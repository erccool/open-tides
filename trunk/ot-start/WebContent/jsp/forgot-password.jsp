<%@ page contentType="text/html;utf-8"%>

<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<idy:header title_webpage="label.login">
	<link href="${url_context}/styles/style.css" rel="stylesheet" type="text/css" />
</idy:header>

    <!-- BODY -->
    <div id="bd">
        <div class="yui-g">
        <div class="main">
            <div class="title-wrapper">
            	<div class="title"><span><spring:message code="label.forgot-password" /></span></div>
            </div>            
            <div class="contents">
				<ot:forgot_password_form action="${action}"/>
            </div>
        </div>
        </div>    
    </div>
    <!-- END OF BODY -->

<idy:footer/>