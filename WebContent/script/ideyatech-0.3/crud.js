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
 * Extracted from prototype.js
 */
var Try = {
  these: function() {
    var returnValue;

    for (var i = 0, length = arguments.length; i < length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) { }
    }

    return returnValue;
  }
};

/**
 * Provides common utility functions.
 * @class util
 * @namespace IDEYATECH
 */
 
 IDEYATECH.util = function () {
    /**
     * Private helper to replace the div with the HTML response 
     * o.argument[0] {String} element to replace
     * o.argument[1] {function} callback function
     * o.argument[2] {boolean} should javascript be executed
     */
	var __replace = function(o){
	  var replaceDiv = yDom.get(o.argument[0]);
	  var userCallback = o.argument[1];
	  replaceDiv.innerHTML = o.responseText;
	  if ( !IDEYATECH.util.isEmpty(userCallback) ) {
		try {
		  userCallback.call();
		} catch (err) {
		}
	  }
	  if (o.argument[2]) 
		  __evaluateJS(o);
	}; 
	
   /**
     * Private helper to append the response as sibling of the div 
     * o.argument[0] {String} element to append
     * o.argument[1] {function} callback function
     * o.argument[2] {boolean} should javascript be executed
     */	
	var __append = function(o) {
 	  var rootId = __getFirstElementId(o.responseText,"tr");
 	  if (!IDEYATECH.util.isEmpty(rootId) && yDom.get(rootId)==null) {	
  	  	var parent = yDom.get(o.argument[0]);
	  	var userCallback = o.argument[1];
	  	parent.innerHTML = parent.innerHTML + o.responseText;	  
	  	if ( !IDEYATECH.util.isEmpty(userCallback) ) {
		  try {
			userCallback.call();
		  } catch (err) {
		  }
	  	}
	  	if (o.argument[2]) 
	  		__evaluateJS(o);
	  } else {
	    if (!IDEYATECH.util.isEmpty(rootId)) o.argument[0]=rootId;
	    __replace(o);
	  }
	}
	
	/**
	 * Private helper to retrieve the first element id in the string
	 */
    var __getFirstElementId = function(str,tag) {
      var matchExp = new RegExp('<'+tag+'[^>]+id="(.\*?)"[^>]*>', 'img');
      var matches = matchExp.exec(str)||[];
      return matches[1];
    }
	
	/**
	 * Private helper that will evaluate the ajax response
	 */
	var __evaluateJS = function(o) {
		var script = IDEYATECH.util.extractScripts(o.responseText);
		eval(script||'');
	}
	
	/**
	 * Private helper that will evaluate the ajax response
	 */
	var _evaluate = function(o) {
		var evalElement = yDom.get(o.argument[1]);
		eval(o.responseText||'');
	}
	
	var __failure = function(o){
	  	alert('Connection failed :: '+o.statusText);
	}
	
	return {
	    /**
	     * Checks if a given variable is empty
	     * @method isEmpty
	     * @param {object} variable to check
	     * 
	     */
	    isEmpty: function(obj) {
	      if (  obj==null 
	         || typeof(obj) == "undefined" 
	         || obj == "") {
	        return true;
	      } else 
	        return false;
	    },
	    /**
	     * Extracts the script from a set of result
	     * @method extractScripts
	     * @param {String} string to extract for script
	     */
	    extractScripts: function(result) {
	  	  var matchAll = new RegExp('<script[^>]*>([\\S\\s]*?)<\/script>', 'img');
	  	  var stripAll = new RegExp('<\/?script[^>]*>', 'img');
		  var scripts = result.match(matchAll) || [];
		  var script = "";
		  for (i=0;i<scripts.length;i++) {
		    script += scripts[i].replace(stripAll,'');
		  }
		  return script;
  		},
	    /**
	     * Loads update page into the given div and parameters.
	     * @method loadPage
	     * @param {String} url URL where request will be sent
	     * @param {Object} argument - can include any of the following
	     *     - divId    = element id containing the row to insert/update page
	     *     - callback = method to execute after loading     
	     *     - append   = true, will append result instead of replace
	     */
	    loadPage: function(url, argument) {
		  var args = [ argument['divId'], 
		               argument['callback'],
		               argument['evaluate'] ];
		  var callback = {
		  	success:__replace,
		    failure:__failure,
		    argument:args };	    
		  if (argument['append']) callback['success']=__append;
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
		    success:_evaluate,
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
	     * @param {String} url URL where request will be sent
	     * @param {Object} argument - can include any of the following
	     *     - divId    = element id containing the row to insert/update page
	     *     - callback = method to execute after loading     
	     *     - append   = true, will append result instead of replace
	     *     - postdata = parameter to append in the form submission
	     *     - formName = name of the form to be submitted
	     */  
		submitForm: function(url, argument) {
		  var formObject = yDom.get( argument['formName'] );
		  if (argument['multipart'] == "true")
			  yConnect.setForm(formObject, true);
		  else 
			  yConnect.setForm(formObject);		  
		  var args = [ argument['divId'], 
		               argument['callback'],
		               argument['evaluate'] ];
		  var postdata = argument['postdata'];
		  
		  var callback =
		  {
		    success:__replace,
		    failure:__failure,
		    argument:args,
		    upload : __replace // upload is called when using asyncronous uploading so this needs to be called
		  };
		  if (argument['append']) callback['success']=__append;
		  
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
 	 * removes it from display. Element is faded to white
 	 * background and white color.
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
   	   var parent = el.parentNode;
   	   parent.removeChild(el);
	   setTimeout("IDEYATECH.crud.refreshTable('"+parent.id+"')"); 
	}

	var __failure = function(o) {
	  alert('Connection failed :: '+o.statusText);
	}
	
	var __fadeOut = {
	    color: {to:'#fff'},
	    backgroundColor: {to:'#fff'}	    
	}
	
	return {
		/**
		 * Applies alternating color on the result table.
		 * @param {Object} argument - 
	     *     - stylePrefix = prefix containing the style class. Defaults to "row-style". 
	     *                     Appends -1 or -0 on prefix for alternating styles.
		 */
		refreshTable: function(divId, argument) {
			var table = yDom.get(divId);
			var prefix = 'row-style-';
			try {
				prefix = argument['stylePrefix'];
			} catch (err) {};

			var children = table.getElementsByTagName('tr');
			for (var i=0;i<children.length;i++) {
				yDom.removeClass(children[i], prefix+'1');
				yDom.removeClass(children[i], prefix+'0');
				yDom.addClass(children[i], prefix+((i+1)%2));
			}
		},
	    /**
		 * Prompts for deletion and delete if confirmed.
	     * @method confirmDelete
	     * @param {String} id id of record to delete
	     * @return {String} title Main title of record to display.
	     */
		confirmDelete: function(divId, url, title) {
		    if (confirm('Are you sure to delete ['+title+']?')) {
		    	var args = [divId];
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
		cancelNew: function(prefix) {
		  var rownew = yDom.get(prefix+'row-new');
		  rownew.innerHTML = "";
		},
		/**
		 * Loads the add record form
		 * @method addRecord
		 * @param {String} prefix of element for add form, appended to '-row-new'
		 * @param {String} url of the form
		 */ 
		addRecord: function(prefix, url) {
			var args = {
				divId: prefix+'row-new',
				evaluate: true
			}
			IDEYATECH.util.loadPage(url, args);
		},
		/**
		 * Loads the edit record form
		 * @method editRecord
		 * @param {String} prefix of element for add form, appended to '-row-new'
		 * @param {String} url of the form
		 */ 
		editRecord: function(prefix, url) {
			var args = {
				divId: prefix,
				evaluate: false
			}
			IDEYATECH.util.loadPage(url, args);
		}		
	}
}();

/**
 * Provides dom representation for a given string.
 * @class util
 * @namespace IDEYATECH
 */
IDEYATECH.dom = function() {
  
  var __parseMicrosoft = function(str) {
    var xmlDocument = new ActiveXObject('Microsoft.XMLDOM');
  	xmlDocument.async = false;
  	var loaded = xmlDocument.loadXML(str);
  	if (loaded) {
   	  return xmlDocument;
   	} else {
   	  alert(xmlDocument.parseError.reason +
   		    xmlDocument.parseError.srcText);
   	  return null;
   	}
  }
  
  var __parseMozilla = function(str) {
    var domParser = new DOMParser();
    var xmlDocument = domParser.parseFromString(str, 'application/xml');
    var parseError = __checkForParseError(xmlDocument);
    
    if (parseError.errorCode == 0) {
      return xmlDocument;
    } else {
      alert(parseError.reason + '\r\n' + parseError.srcText);
      return null;
    }
  }
  
  var __checkForParseError = function(xmlDocument) {
    var errorNamespace = 'http://www.mozilla.org/newlayout/xml/parsererror.xml';
    var documentElement = xmlDocument.documentElement;
    var parseError = { errorCode : 0 };
    if (documentElement.nodeName == 'parsererror' &&
        documentElement.namespaceURI == errorNamespace) {
      parseError.errorCode = 1;
      var sourceText = documentElement.getElementsByTagNameNS(errorNamespace, 'sourcetext')[0];
      if (sourceText != null) {
        parseError.srcText = sourceText.firstChild.data
      }
      parseError.reason = documentElement.firstChild.data;
    }
    return parseError;
  }
  
  return {
	/**
	 * Converts the given string into a dom object 
	 * @param {String} str - String containing the xml text
	 */
	loadXML: function(str) {
	  return Try.these(
      	function() {__parseMicrosoft(str);},
      	function() {__parseMozilla(str);}
      ) || false;
	}
  }
}();

/**
 * Provides common animation functions.
 * @class anim
 * @namespace IDEYATECH
 */
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
	     * @method fadeElement
	     * @param {Object} argument - can include any of the following
	     *     - divId    = element id to be faded
	     *     - callback = method to execute after loading
	     *     - remove   = should we remove the element after fade   
	     *     - fadeFrom = fade color from
	     *     - fadeTo   = fade color to
	     */
		fadeElement: function(o) {
			var removeDiv = yDom.get(o.argument['divId']);
			if (!IDEYATECH.util.isEmpty(o.argument['fadeFrom'])) __fadeOut['backgroundColor']['from']=o.argument['fadeFrom'];			
			if (!IDEYATECH.util.isEmpty(o.argument['fadeTo'])) {
			  __fadeOut['color']['to']=o.argument['fadeTo'];
			  __fadeOut['backgroundColor']['to']=o.argument['fadeTo'];
			}
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
	        var fp = "<div class='errorBox'>Login Failed. Please try again. <br/>" 
	                +"Reason: "+msg.substring(6, msg.length) + "</div>";
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