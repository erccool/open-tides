<%@ page contentType="text/html;utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>
 
<jsp:useBean id="currentUser" class="org.opentides.util.AcegiUtil" />
<c:if test="${not empty currentUser.user}">
	<c:redirect url="${url_context}/home.jspx"/>
</c:if>
<c:if test="${empty currentUser.user}">
<idy:header title_webpage="label.welcome">
    <script src="${url_yui}/yahoo-dom-event/yahoo-dom-event.js"></script>
    <script src="${url_yui}/animation/animation-min.js"></script>  
    <script src="${url_yui}/connection/connection-min.js"></script>
    <script src="${url_context}/script/ideyatech-${ot_version}/crud.js"></script>
</idy:header>
    <!-- BODY -->
    <div id="bd">
    
    	<!-- MAIN --> 
        <div id="yui-main">
        
            <div class="yui-b">
            <div class="yui-g">
            
            <div class="main">
			
                <div class="title-wrapper">
                    <div class="title"><span>Sample Title</span></div>
                </div>   
                
                <div class="contents">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce condimentum. Donec rhoncus nibh quis mauris. Donec erat est, mattis sit amet, molestie a, vulputate at, ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Pellentesque vel erat non dui malesuada aliquam. Vestibulum aliquam convallis elit. Morbi sit amet nibh ac orci convallis fermentum. Duis dapibus. Quisque vel mauris nec ligula venenatis egestas. Pellentesque sed neque. Sed orci. Sed orci quam, gravida eget, cursus vitae, sagittis vel, mauris. Mauris aliquam adipiscing eros. Pellentesque pulvinar felis in arcu. Aenean velit tortor, tristique vitae, gravida vel, euismod eget, diam. Aliquam eros. Aliquam pulvinar, massa commodo pretium rutrum, quam mauris tincidunt ligula, vitae bibendum lorem eros non nisl. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Integer vitae sapien sit amet ipsum commodo imperdiet.            
                </div>          	

            </div>
            </div>
            </div>
       
        </div>
        
        <!-- SIDE -->
        <div class="yui-b">
        
        <div class="main">

            <div class="title-wrapper">
                <div class="title"><span>Log-in</span></div>
            </div>   
            
            <div class="contents">
            
				<div class="form-wrapper">
                <ot:login_form />	
                </div>	
                
			</div>

        </div>        
        
        </div>
    
    </div><!-- END OF BODY -->
<idy:footer/>
</c:if>
