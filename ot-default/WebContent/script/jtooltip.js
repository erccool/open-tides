/*
 * JQuery jtooltip v1.0
 *
 * Copyright 2011, Gianrocco Giaquinta
 * http://www.jscripts.hostoi.com/
 *
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 */

(function( $ ){
		
    var methods = {
        
		display_tip: function(ctl, obj, lv){
		
			var msg="";
			var offset, jtw, jth, ctlw, ctlh, conw, conh, ori, o1, o2, o3;
			var jt_x, jt_y, jt_ix, jt_iy, offx, px, py;
			
			obj.find("#jtt_uni_box_con").css( { 'border':lv.oborder } );
			
			msg = $(ctl).attr("jt_val");
				
			if ( msg.substr(0,1) == "#") {
				obj.find("#jtt_uni_box_inn").html( $(msg).html() );	
			} else
			if ( msg.substr(0,4) == "url:") {
			
				msg = msg.substr(4);				
				var s1 = msg.split("?");
				
				objXml = new XMLHttpRequest();
     			objXml.open("GET",msg,false);
     			objXml.send(null);
     			msg = objXml.responseText;
				
				obj.find("#jtt_uni_box_inn").html( msg );	
					
			} else
				obj.find("#jtt_uni_box_inn").html( msg );
											
			ori = $(ctl).attr('jt_pos').toLowerCase(); if ( !ori) ori="b88";
			o1 = ori.substr(0,1); o2=ori.substr(1,1); o3=ori.substr(2,1);
			
			offset = ctl.offset();
			ctlw=$(ctl).outerWidth();
			ctlh=$(ctl).outerHeight();
			
			if ( o1 == "t" ) obj.css('padding','0 0 '+((lv.indlen/2)-lv.border)+'px 0');
			if ( o1 == "r" || o1 == "l" ) obj.css('padding','0 '+(lv.indlen/2)+'px 0 0');
						
			conw=obj.outerWidth();
			conh=obj.outerHeight();
			//$("#debug").val((lv.indlen/2-lv.border)+"> "+conw+":"+conh+" > "+px+" : "+py);
			
			if ( o1 == "b" || o1 == "t" ) {
			
				if ("147".indexOf(o3) != -1) { px = lv.p_offset+(lv.indlen/2); jt_ix = lv.p_offset; }
				if ("28".indexOf(o3) != -1) { px = conw*.5; jt_ix = conw*.5 - (lv.indlen/2); }
				if ("369".indexOf(o3) != -1) { px = conw-lv.p_offset - (lv.indlen/2); jt_ix = conw -lv.p_offset -lv.indlen; }
								
				if (o2 == "1" || o2 == "7") {					
					jt_x = offset.left + lv.c_offset - px;
				}
				if (o2 == "2" || o2 == "8") {
					jt_x = offset.left + ctlw/2 - px;
				}
				if (o2 == "3" || o2 == "9") {
					jt_x = offset.left + ctlw - lv.c_offset -px; 
				}
			}
				
			
			if ( o1 == "r" || o1 == "l" ) {
				
				if ("79".indexOf(o3) != -1) { py = lv.p_offset+(lv.indlen/2-lv.border); jt_iy = lv.p_offset; }
				if ("46".indexOf(o3) != -1) { py = conh*.5; jt_iy = conh*.5 - (lv.indlen*.5); }
				if ("13".indexOf(o3) != -1) { py = conh-lv.p_offset - (lv.indlen/2); jt_iy = conh -lv.p_offset -(lv.indlen); }
							
				if (o2 == "7" || o2 == "9") {					
					jt_y = offset.top + lv.c_offset - py;
				}
				if (o2 == "4" || o2 == "6") {
					jt_y = offset.top + ctlh/2 - py;
				}
				if (o2 == "1" || o2 == "3") {
					jt_y = offset.top + ctlh - lv.c_offset - py;
				}				
				
			}			
			
			if ( o1 == "b" ) {
				obj.css('padding-top',((lv.indlen/2)-lv.border)+'px');
				obj.find("#jtt_uni_box_con").css( { 'top': '0px', left: '0px' } );
				obj.find("#jtt_uni_box_ind").css( { top:0, left:jt_ix+'px', right:'auto', bottom:'auto',height:(lv.indlen/2-lv.adj)+'px', 'width': (lv.indlen)+'px'  } );
				//obj.find("#jtt_uni_box_con").css( { 'border-right': lv.border+'px hidden', 'border-bottom': lv.border+'px hidden' } );
				jt_y = offset.top + ctlh + lv.offset;
			}
			
			if ( o1 == "t" ) {
				obj.css('padding-bottom',((lv.indlen/2)-lv.border)+'px');
				obj.find("#jtt_uni_box_ind").css( { top:'auto', bottom:0, left:jt_ix+'px', right:'auto', height:(lv.indlen/2)+'px', 'width': (lv.indlen)+'px'  } );
				obj.find("#jtt_uni_box_con").css( { top:-(lv.indlen/2)+'px', bottom:0, left: '0px' } );
				//obj.find("#jtt_uni_box_con").css( { 'border-left':0, 'border-top':0 } );
				jt_y = offset.top - conh - lv.offset;
			}	
			if ( o1 == "r" ) {
				obj.css('padding-left',((lv.indlen/2)-lv.border)+'px');
				obj.find("#jtt_uni_box_con").css( { 'top': '0px', left: '0px' } );
				obj.find("#jtt_uni_box_ind").css( { left:0,bottom:'auto', top:jt_iy+'px', bottom:'auto', width:(lv.indlen/2-lv.adj)+'px', 'height':  (lv.indlen)+'px'  } );
				//obj.find("#jtt_uni_box_con").css( { 'border-top':0, 'border-right':0 } );
				jt_x = offset.left + ctlw + lv.offset;
			}
			
			if ( o1 == "l" ) {
				obj.css('padding-right',((lv.indlen/2)-lv.border)+'px');
				obj.find("#jtt_uni_box_con").css( { 'top': '0px', left: (-(lv.indlen/2))+'px' } );
				obj.find("#jtt_uni_box_ind").css( { left:'auto', right:'0px', top:jt_iy+'px', bottom:'auto', width:(lv.indlen/2)+'px', 'height':  (lv.indlen)+'px'  } );
				//obj.find("#jtt_uni_box_con").css( { 'border-left':0, 'border-bottom':0 } );
				jt_x = offset.left - (conw-lv.border) - lv.offset;
			}			
					
			obj.css( {left:jt_x+"px", top:jt_y+"px"} );
			obj.show();			
					
			
			
		}
	}

	$.fn.jtooltip = function(opt) {
		
        var timer=0, offtimer=0;
		var jt_obj=null;
		var lv;
					
		settings = {
			'radius'	  : 3,
            'shadow'      : '3px 3px 3px #ccc',
        	'delay'		  : 200,
			'offset'	  : 2,
			'p_offset'	  : 10,
			'c_offset'	  : 10,
			'pointer'	  : 8,
			'border'	  : '1px solid #000',
			'bgcolor'	  : '#eee',
			'font'		  : '11px "lucida grande", tahoma, verdana, arial, sans-serif',
			'color'		  : '#333',
			'padding'	  : '10px',
			'mode'		  : 'tooltip'
		};
		
		if(opt)	$.extend(settings, opt);
		
		var	cborder = parseInt(settings.border);
		var	cinlato = parseInt(((settings.pointer)*1.414213562373095)-cborder*2);
		var	cindlen = settings.pointer*2;
		var	cindmar = parseInt( (cindlen-cinlato-cborder*2)/2 );
		var cadj=0;
		
		var browser = navigator.appName.toLowerCase();
		
		if ( browser.indexOf("explorer") != -1) cadj=1;
		
		lv = {		
			border: cborder,
			inlato: cinlato,
			indlen: cindlen,
			indmar: cindmar,
			c_offset: settings.c_offset,
			p_offset: settings.p_offset,
			offset: settings.offset,
			pointer: settings.pointer,
			oborder:settings.border,
			adj:cadj
		};
								
		jt_obj = $('<div id="jtt_uni_box"><div id="jtt_uni_box_ind"><div id="jtt_uni_box_con"></div></div><div id="jtt_uni_box_inn"></div></div>')
            .css({position:'absolute', zIndex:'9999', 'background-color':'transparent'})
            .appendTo('body')
				
		jt_obj.hide();
		
			jt_obj.find("#jtt_uni_box_inn").css({
				'border': settings.border,
				'font-size':'12px',
				'backgroundColor':settings.bgcolor,
				'zIndex':'500',
				'-moz-border-radius': settings.radius+'px',
			  	'-webkit-border-radius': settings.radius+'px',
			  	'border-radius': settings.radius+'px',
				'padding': settings.padding,
				'font': settings.font,
				'color': settings.color,
				 zIndex:'50'
				 });
			
			if (settings.shadow)
				jt_obj.find("#jtt_uni_box_inn").css( {
				'-moz-box-shadow': settings.shadow,
			  	'-webkit-box-shadow': settings.shadow,
			  	'box-shadow': settings.shadow });
			
			jt_obj.find("#jtt_uni_box_ind").css( {
				//'background-color': 'green',
				'position':'absolute',
				'z-index':'52',
				'overflow':'hidden',
				'width':lv.indlen+'px',
				'height':lv.indlen+'px'				
			});
					
			
			jt_obj.find("#jtt_uni_box_con").css( {
				'background-color':settings.bgcolor,
				'position':'absolute', 
				'width': lv.inlato+'px',
				'height': lv.inlato+'px',
			  	'border': settings.border,
				'-webkit-transform': 'translate('+lv.indmar+'px, '+lv.indmar+'px) rotate(45deg)',
    			'-moz-transform': 'translate('+lv.indmar+'px, '+lv.indmar+'px) rotate(45deg)',
    			'-o-transform': 'translate('+lv.indmar+'px, '+lv.indmar+'px) rotate(45deg)',
    			'filter': "progid:DXImageTransform.Microsoft.Matrix(M11=0.7071067811865476,M12=-0.7071067811865475,M21=0.7071067811865475,M22=0.7071067811865476,SizingMethod='auto expand')",
				'MSFilter': "progid:DXImageTransform.Microsoft.Matrix(M11=0.7071067811865476,M12=-0.7071067811865475,M21=0.7071067811865475,M22=0.7071067811865476,SizingMethod='auto expand')",
				'zoom': 1,				
				'z-index':'51'
			});	
			
		
	  this.each(function(){	
		
		if (settings.mode == "tooltip")
		$(this).bind({
                mouseover: function (e) {
                    		e.preventDefault();
							ctl=$(this);
							if (!timer) timer = setTimeout( function() {
								methods.display_tip(ctl, jt_obj, lv);
								clearTimeout(timer); timer=0;
								intooltip=false;
								clearTimeout(offtimer);	offtimer=0;							
							}, settings.delay);
							
							},
					
				mouseout: function (e) {
                    		e.preventDefault();						
							clearTimeout(timer);
							timer=0							
							if (!offtimer) offtimer = setTimeout( function() {
								$(this).find("#jtt_uni_box_inn").html("");
								jt_obj.hide();
							}, 300);
							
         		}
		});
		
		if (settings.mode == "menu")
		$(this).bind({
                click: function (e) {
                    		e.preventDefault();						
							clearTimeout(timer); timer=0;
							intooltip=false;
							clearTimeout(offtimer);	offtimer=0;
							ctl=$(this);
							methods.display_tip(ctl, jt_obj, lv);
						},
				
				mouseout: function (e) {
                    		e.preventDefault();						
							clearTimeout(timer);
							timer=0							
							if (!offtimer) offtimer = setTimeout( function() {
								$(this).find("#jtt_uni_box_inn").html("");
								jt_obj.hide();
							}, 300);
						}
								
		});		
		
	});
	
	jt_obj.bind({
			 	mouseover: function (e) {
                    		e.preventDefault();
							clearTimeout(offtimer);
							offtimer=0;
							intooltip=true;
						
		 		},
		 
		 		mouseout: function (e) {
						if (intooltip) {
                    		e.preventDefault();

							var reltg = (e.relatedTarget) ? e.relatedTarget : e.toElement;
							while (reltg.id != "jtt_uni_box" && reltg.nodeName != 'BODY')
								reltg= reltg.parentNode
								
							if ( reltg.id == "jtt_uni_box" ) return;
							$(this).find("#jtt_uni_box_inn").html("");
							jt_obj.hide();
							intooltip=false;
						}
				},
				
				mouseup: function() {
					clearTimeout(offtimer);	offtimer=0;
					intooltip=true;
					jt_obj.hide();   
			   }
	 });
	

 }
 
})( jQuery );