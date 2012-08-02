<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
 "http://www.w3.org/TR/html4/strict.dtd">
<%@ tag dynamic-attributes="attributes" isELIgnored="false" body-content="scriptless" import="org.opentides.util.StringUtil" %>
<%@ attribute name="title_webpage" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="url_yui" value="http://yui.yahooapis.com/2.5.1/build" scope="application"/>
<c:set var="url_context" value="${pageContext.request.contextPath}" scope="application" />
<c:set var="url_jquery" value="${url_context}/script/ideyatech-1.0/jquery-1.5.1-dev.js" scope="application" />
<c:set var="url_jquery_ui" value="${url_context}/script/ideyatech-1.0/jquery-ui-1.8.11.custom.min.js" scope="application"/>
<c:set var="ot_version" value="1.0" scope="application" />
<c:set var="url_script" value="${url_context}/script/ideyatech-${ot_version}" scope="application" />

<jsp:useBean id="currentUser" class="org.opentides.util.SecurityUtil" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title><spring:message code="${title_webpage}"/></title> 
  	<link rel="stylesheet" type="text/css" href="${url_yui}/reset-fonts-grids/reset-fonts-grids.css"  /> 
	<link rel="stylesheet" type="text/css" href="${url_yui}/base/base-min.css"/>
  	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/style.css"/>"/>
  	
  	<script type="text/javascript" src="${url_jquery}"></script>
    <script type="text/javascript" src="${url_jquery_ui}"></script>
    <script type="text/javascript" src="${url_context}/script/jtooltip.js" ></script> 
    <script type="text/javascript" src="${url_script}/crud.js"></script>
    
    <script type="text/javascript" src="${url_context}/script/ideyatech-${ot_version}/jquery.form.js"></script>
  	
    <jsp:doBody />  
</head>


<div id="doc4" class="yui-t3">
	<!-- HEADER -->
    <div id="hd">
        <div class="logo"><a href="<c:url value="/index.jspx"/>"><img src="${url_context}/images/logo.jpg" alt="Open-tides" title="Open-tides"></a></div>
		<c:if test="${empty currentUser.user}">
			<div class="clear index-spacer"></div> 
		</c:if>
		<c:if test="${not empty currentUser.user}">
	        <ul class="hd-list">
	        	<li>Welcome ${currentUser.user.firstName} ${currentUser.user.lastName}</li>
	            <li>|</li>
	            <li><a href="<c:url value="${url_context}/j_spring_security_logout"/>">Logout</a></li>
	        </ul>               
	        <div class="clear"></div>
	        <div class="nav-wrapper">
	        	<ul class="nav">
	            	<li><a href="<c:url value="/admin/users.jspx"/>">Users</a></li>
	                <li>|</li>
	            	<li><a href="<c:url value="/admin/usergroup.jspx"/>">Groups</a></li>
	                <li>|</li>
	                <li><a href="<c:url value="/admin/system-codes.jspx"/>">Lookup</a></li>
	                <li>|</li>
	                <li><a href="#">Link 3</a></li>
	                <li>|</li>
	                <li><a href="#">Link 4</a></li>
	                <li>|</li>
	                <li><a href="#">Link 5</a></li>
	            </ul>
	        </div>
    	</c:if>
	</div>
    <!-- END OF HEADER -->