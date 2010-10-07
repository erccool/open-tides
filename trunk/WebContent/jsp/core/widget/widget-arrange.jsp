<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
<%@page import="org.opentides.util.WidgetConfiguration"%>
 <% response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
     response.setHeader("Pragma","no-cache"); //HTTP 1.0 
     response.setDateHeader ("Expires", 0); //prevents caching 
     response.setHeader("Cache-Control","no-store"); //HTTP 1.1 %>
<idy:header title_webpage="label.home" active="home">
<script src="${url_js}/crud.js"></script>
<script src="${url_js}/tides-dragdrop.js"></script>
<style>
#Column1, #Column2 {
	position: relative;
	float: left;
	width: 36%;
	height: 200px;
}

.DashboardBox {
	position: static;
	border-top: 1px solid #EEEEEE;
	border-right: 1px solid #EEEEEE;
	border-bottom: 1px solid #EEEEEE;
	border-left: 1px solid #EEEEEE;
	background-color: #fff;
	color: #000;
	margin: 5px;
	margin-bottom: 20px;
	font-size: 100%;

.Handle {
	-x-system-font:none;
	background-color:#0076A3;
	color:#FFFFFF;
	font-family:"Myriad Pro",tahoma;
	font-size:13px;
	font-size-adjust:none;
	font-stretch:normal;
	font-style:normal;
	font-variant:normal;
	font-weight:normal;
	line-height:normal;
	margin:0.05em 0 0.5em;
	padding:0.25em 0.5em 0;
	text-align:left;
	cursor: pointer;
}
}
</style>
</idy:header>
     
<%
int widgetConfigMinimize = WidgetConfiguration.WIDGET_STATUS_MINIMIZE;
pageContext.setAttribute("widgetConfigMinimize",widgetConfigMinimize);
%>

<c:set var="columnClassName" value="dashboard-column" scope="page" />
<c:set var="widgetClassName" value="widget-box" scope="page" />
<c:set var="handleClassName" value="tides-dd-handle" scope="page" />

		<div id="dashboard-main">	
			 <c:forEach var="i" begin="1" end="${columnNumber}" varStatus ="status">
				  	<div id="Column${i}" class="${columnClassName}">
						<c:forEach items="${userWidgets}" var="userWidget" varStatus ="widgetStatus">
							<c:set var="widgetState" value="widget-collapse" scope="page" />
							<c:if test="${userWidget.status == widgetConfigMinimize}">
								<c:set var="widgetState" value="widget-expand" scope="page" />
							</c:if>						
							<c:if test="${userWidget.column == i}">
								<div id="${userWidget.widget.name}" class="${widgetClassName}">					
									<div id="${userWidget.widget.name}Handle" class="${handleClassName}">
										<a class="widget-close" onclick="TIDES.dragdrop.executeSaveStatus(this,'${userWidget.status}')"></a>
	                                    <a class="${widgetState}" onclick="TIDES.dragdrop.executeSaveStatus(this,'${userWidget.status}')"></a>
	                                	<h4>${userWidget.widget.title}</h4>
									</div>
									<div id="ajaxContainer${widgetStatus.count}" style="${widgetState=='widget-collapse' ? '' : 'display:none;'}"><center><img src="${url_context}/images/widgets/ajax-loader.gif" /></center></div>
									<script type="text/javascript">ajaxinclude("widget-load.jspx?name=${userWidget.widget.name}","ajaxContainer${widgetStatus.count}")</script>
								</div>
							</c:if>
						</c:forEach>
					
					</div>  
				  </c:forEach>
		</div>
		
<idy:footer />
<script type="text/javascript">
function ajaxinclude(url, targetDivId) {
	var args_div = { 
	    	divId: targetDivId
	    }
	IDEYATECH.util.loadPage(url,
	    	args_div);

}

	function addLoadEvent(func) {
		var oldonload = window.onload;
		if (typeof window.onload != 'function') 
		{
			window.onload = func;
		} 
		else 
		{
		  	window.onload = function() {
		    if (oldonload) {
		    	oldonload();
		    }
		    func();
		  }
		}
	}

yEvent.addListener(window, "load", init);

function init() {
		TIDES.dragdrop.setWidgetBoxClassName("DashboardBox");
    	TIDES.dragdrop.setSupportsHandle(true);
    	TIDES.dragdrop.setHandleClassName("Handle");
		TIDES.dragdrop.setSupportsPersistState(true);
    	TIDES.dragdrop.init('dashboard-main');
}


</script>