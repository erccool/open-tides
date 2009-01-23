/* 
Copyright (c) 2008, Ideyatech, Inc. All rights reserved.
Code licensed under the BSD License
*/

/** 
 * The global namespace.
 */   
var IDEYATECH = {};

/** 
 * Yahoo Connection Manager used for AJAX calls.
 */   
var yConnect = YAHOO.util.Connect; 

/** 
 * Yahoo DOM utility.
 */   
var yDom = YAHOO.util.Dom; 

/**
 * Provides common utility functions.
 * @class util
 * @namespace IDEYATECH
 */ 
 
 IDEYATECH.util = function () {
  
    /**
     * Private helper to replace the div with the HTML response 
     */
	var __replace = function(o){
		var replaceDiv = yDom.get(o.argument[1]);
		var userCallback = o.argument[2];
		replaceDiv.innerHTML = o.responseText;
		if (typeof(userCallback) != "undefined" && userCallback != "") {
		  try {
			 userCallback.call();
		  } catch (err) {
		  }
		}
	};
	
   /**
     * Private helper to append the response as sibling of the div.
     * This method ensures that element
     * 
     */	
	var __append = function(o) {
		var parent = yDom.get(o.argument[0]);
		if (o.responseText.match('status="new"')) {
			parent.innerHTML = parent.innerHTML + o.responseText;
			IDEYATECH.crud.refreshTable('table-results'); 
			var formObject = yDom.get(o.argument[2]);
			formObject.reset();
		} else {
			__replace(o);
		}
	}
	
	/**
	 * Private helper that will evaluate the ajax response
	 */
	var __evaluate = function(o) {
		var evalElement = yDom.get(o.argument[1]);
		eval(o.responseText||'');
	}
	
	var __failure = function(o){
	  	alert('Connection failed :: '+o.statusText);
	}
	
	return {
	    /**
	     * Loads update page into the given div and parameters.
	     * @method loadForm
	     * @param {String} divid Element id containing the row to insert update page.
	     * @return {String} param Parameter to be appended in the query string
	     */
	    loadPage: function(divid, url) {
		  var args = ['table-results',divid];
		  var callback =
		  {
		    success:__replace,
		    failure:__failure,
		    argument:args
		  };
		  yConnect.asyncRequest('GET', url, callback); 	    
	    },
	    /**
	     * Loads update page into the given div and parameters.
	     * @method loadForm
	     * @param {String} divid Element id containing the row to insert update page.
	     * @param {String} url URL where request will be sent
	     * @param {String} callback method to execute after loading     
	     */
	    loadPageWithCallback: function(divid, url, userCallback) {
		  var args = ['table-results', divid, userCallback];
		  var callback =
		  {
		    success:__replace,
		    failure:__failure,
		    argument:args
		  };
		  yConnect.asyncRequest('GET', url, callback); 	    
	    },	    
	    /**
	     * Submits a form and execute the javascript from the Ajax response
	     * @method loadPageWithEval
	     * @param {String} url URL where request will be sent
	     * @param {String} evalid the element containing script for eval.
	     */
	    submitFormWithEval: function(formName, evalid, url, postdata) {
		  var formObject = yDom.get(formName);
		  yConnect.setForm(formObject); 
		  var args = ['table-results',evalid];
		  if (url==null || url.length == 0)
			  url = formObject.action;
		  var callback =
		  {
		    success:__evaluate,
		    failure:__failure,
		    argument:args
		  };
		  if (postdata==null || postdata.length <=0) {
		    yConnect.asyncRequest('POST', url, callback); 				  
		  } else {
		    yConnect.asyncRequest('POST', url, callback, postdata); 		
		  }	
	    },
	   /**
	     * Submits the form for processing.
	     * @method submitForm
	     * @param {String} formName form to submit
	     * @param {String} divid where response will be applied
	     * @param {String} url URL to submit
	     * @param {String} postdata additional information to post
	     * @param {Boolean} append if true, will append result instead of replace
	     * @return {String} String without HTML tags.
	     */	    
		submitForm: function(formName, divid, url, postdata, append) {
		  var formObject = yDom.get(formName);
		  yConnect.setForm(formObject);
		  var args = ['table-results',divid,formName];
		  var callback =
		  {
		    success:__replace,
		    failure:__failure,
		    argument:args
		  };
		  if (append) {
		  	callback['success']=__append;
		  }
		  if (postdata==null || postdata.length <=0) {
		    yConnect.asyncRequest('POST', url, callback); 				  
		  } else {
		    yConnect.asyncRequest('POST', url, callback, postdata); 		
		  }
		}
	}
 }();
 
/**
 * Provides ajax based CRUD functions.
 * @class crud
 * @namespace IDEYATECH
 */
 IDEYATECH.crud = function () {
 
 	/**
 	 * Private helper function that fades an element and
 	 * removes it from display
 	 */
	var __fade_then_remove = function(o) {
		var removeDiv = yDom.get(o.argument[0]);
		var fade = new YAHOO.util.ColorAnim(o.argument[0],__fadeOut);
		fade.onComplete.subscribe(__remove);
		fade.animate();	
	}
	
	/**
	 * Private helper that removes element  
	 * called by __fade_element
	 */
	var __remove = function() {
   	   var el = this.getEl(); 
	   el.parentNode.removeChild(el);
	   IDEYATECH.crud.refreshTable('table-results'); 
	}

	var __failure = function(o){
	  alert('Connection failed :: '+o.statusText);
	}
	
	var __fadeOut = {
	    color: {to:'#fff'},
	    backgroundColor: {to:'#fff'}	    
	}
	
	return {
		/**
		 * Applies alternative color on the result table
		 */
		refreshTable: function(divid) {
			// var children = yDom.getChildren(divid,function(el){return el.getAttribute('id')=='theMiddleB';});
			var table = yDom.get(divid);
			var children = table.getElementsByTagName('div');
			for (var i=0;i<children.length;i++) {
				yDom.removeClass(children[i],'row-style-1');
				yDom.removeClass(children[i],'row-style-0');
				yDom.addClass(children[i],'row-style-'+i%2);
			}
		},
	    /**
		 * Prompts for deletion and delete if confirmed.
	     * @method confirmDelete
	     * @param {String} id id of record to delete
	     * @return {String} title Main title of record to display.
	     */
		confirmDelete: function(divid, url, title) {
		    if (confirm('Are you sure to delete ['+title+']?')) {
		    	var args = [divid];
			    var callback =
			    {
			        success:__fade_then_remove,
			        failure:__failure,
			        argument:args
			    };
			    yConnect.asyncRequest('GET', url, callback); 
		    }
		},
		/**
		 * Replace the new form displayed with add new button
		 * @method cancelNew
		 */
		cancelNew: function() {
			var rownew = yDom.get('row-new');
			rownew.innerHTML = "";
		}
	}
}();

IDEYATECH.anim = function() {
	
	/**
	 * Private helper that removes element  
	 * called by __fade_element
	 */
	var __remove = function() {
   	   var el = this.getEl(); 
	   el.parentNode.removeChild(el);
	}
	
	var __fadeOut = {
	    color: {to:'#fff'},
	    backgroundColor: {from:'#c00000', to:'#fff'}
	}
	
	return {
		/**
		 * Fades and remove element
		 */
		fadeElement: function(divid) {
			var removeDiv = yDom.get(divid);
			var fade = new YAHOO.util.ColorAnim(removeDiv, __fadeOut);
			fade.onComplete.subscribe(__remove);
			fade.animate();	
		}
	}
}();

/**
 * Provides ajax login functionalities
 * @class util
 * @namespace IDEYATECH
 */
 
 IDEYATECH.login = function () {
	/**
	 * Private helper to process response of ajax based login.
	 */
	var __loginSuccess = function(response) {
		var loadingDiv = yDom.get('loadingDiv');
		if (loadingDiv) yDom.setStyle('loadingDiv','display','none');
		var msg = response.responseText;
		if ("error:" == msg.substr(0, 6)) {    
	        var fp = "<span class='errorBox'>Login Failed. Please try again. <br/>" 
	                +"Reason: "+msg.substring(6, msg.length) + "</span>";
	        yDom.get('loginMessage').innerHTML = fp;
	    } else if ("url:" == msg.substr(0, 4)) {    
	        location.href = msg.substring(4, msg.length);    
	    } else if ("message:" == msg.substr(0, 8)) {    
	        yDom.get('loginMessage').innerHTML = msg.substring(8, msg.length);    
	    }
	}
	  
	var __loginFailure = function(o) {
		yDom.get('loginForm').submit();
	}
	  
	return {
		/**
		 * Submits the login form via ajax
		 */
	    ajaxLogin: function() {
	   		var loginCallback =
			 	{
			        success:__loginSuccess,
			        failure:__loginFailure,
			        timeout:5000
			    };
			var formObject = yDom.get('loginForm');
			var loadingDiv = yDom.get('loadingDiv');
			if (loadingDiv) yDom.setStyle('loadingDiv','display','block');
			yConnect.setForm(formObject);   
			yConnect.asyncRequest('POST', 'j_acegi_security_check?ajax=true', loginCallback);   
		} 
	}  
 }();

/**
 * Javascript loading pop-up
 */ 
 
IDEYATECH.loading = function() {
	var loader;
	
	return {
		hide:function () {
			loader.hide();
		},
		show:function () {		
		 	loader = new YAHOO.widget.Panel("wait",  
                                             { width: "300px", 
                                               fixedcenter: true, 
                                               close: false, 
                                               draggable: false, 
                                               zindex:4,
                                               modal: true,
                                               visible: false
                                             } 
                                         );
            loader.render(document.body);
            loader.show();
        }    
	 };
}();