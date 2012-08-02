<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Page</title>

	<link rel="stylesheet" type="text/css" href="../../yui/fonts-min.css" />  
	<link rel="stylesheet" type="text/css" href="../../yui/tabview.css" />
	<link rel="stylesheet" type="text/css" href="../../style/style.css" />
	<script type="text/javascript" src="../../yui/utilities.js"></script>  
	<script type="text/javascript" src="../../yui/tabview.js"></script>  

	<!--begin custom header content for this example-->
	<style type="text/css">
		.yui-navset div.loading div {
    		background:url(assets/loading.gif) no-repeat center center;
    		height:8em; /* hold some space while loading */
		}
	</style>

<div class="yui-skin-sam">

	<div id="container"></div>
		<script type="text/javascript">
		(function() {
    		var tabView = new YAHOO.widget.TabView();
		    
    		tabView.addTab( new YAHOO.widget.Tab({
        		label: 'Home',
        		dataSrc: 'assets/news.php?query=opera+browser',
        		cacheData: true,
        		active: true
    		}));
		
    		tabView.addTab( new YAHOO.widget.Tab({
        		label: 'Categories',
        		dataSrc: '../../categories.jspx',
        		cacheData: true
    		}));
		
    		tabView.addTab( new YAHOO.widget.Tab({
        		label: 'Third',
        		dataSrc: '../../categories-edit.jspx',
        		cacheData: true
    		}));

		    tabView.addTab( new YAHOO.widget.Tab({
		        label: 'About',
		        dataSrc: 'assets/news.php?query=apple+safari+browser',
		        cacheData: true
    		}));

		    YAHOO.log("The example has finished loading; as you interact with it, you'll see log messages appearing here.", "info", "example");

    		tabView.appendTo('container');
		})();
		</script>
	</div>
<div>   		
</div>  