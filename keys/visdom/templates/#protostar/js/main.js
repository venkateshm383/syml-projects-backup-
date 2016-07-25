var sval='0';
	var $j = jQuery.noConflict();

	$j(document).ready(function() {
		//hide Property Mortgage Info on load
		$j('.Property-Mortgage-Info').hide();

		//hide or show property-asset-mortgage-info
		$j('.property_ownership').change(function(){
			var elem = $j(this).closest('table').next();
			if ($j(this).val() == 'financed') elem.show();
			else elem.hide();
		});
		
		
		$j(".row a").click(function(e){
			//alert(this);
			e.preventDefault();
			if($(this).hasClass("sel")) { 
				// already selected 
			} else {
				$j("a.sel").removeClass("sel");
				$j(this).addClass("sel");
				
				var rid = $j(this).attr('id');
				//alert(rid);
				var value = '[value="'+rid+'"]';
				
				//$j('input:radio[name="category"]').filter(value).attr('checked', true);			
			}
		});
	});
	
	function print_r(theObj){
	  if(theObj.constructor == Array ||
		 theObj.constructor == Object){
		document.write("<ul>")
		for(var p in theObj){
		  if(theObj[p].constructor == Array||
			 theObj[p].constructor == Object){
	document.write("<li>["+p+"] => "+typeof(theObj)+"</li>");
			document.write("<ul>")
			print_r(theObj[p]);
			document.write("</ul>")
		  } else {
	document.write("<li>["+p+"] => "+theObj[p]+"</li>");
		  }
		}
		document.write("</ul>")
	  }
	}

	function setarray()
	{
		imgArr = Array(Array("oNumber-1-icon.png"), Array("Number-2-icon.png", "oNumber-2-icon.png"), Array("Number-3-icon.png", "oNumber-3-icon.png"), Array("Number-4-icon.png", "oNumber-4-icon.png"), Array("Number-5-icon.png", "oNumber-5-icon.png"), Array("Number-6-icon.png", "oNumber-6-icon.png"), Array("Number-7-icon.png", "oNumber-7-icon.png"),Array("Number-7-icon.png", "oNumber-7-icon.png"), Array("Number-8-icon.png", "oNumber-8-icon.png"),Array("Number-8-icon.png", "oNumber-8-icon.png"));
			
		stepArr = new Array("GeneralInfo","DisclosureInfo", "Objectives", "LoanDetails","LoanDetails2","EmploymentDetails","AssetDetails","LiabilitiesDetails","finish");
		
		tBox=9;
	}
	
	function unsetarray()
	{
		imgArr = Array(Array("Number-2-icon.png", "oNumber-2-icon.png"), Array("Number-3-icon.png", "oNumber-3-icon.png"), Array("Number-4-icon.png", "oNumber-4-icon.png"), Array("Number-5-icon.png", "oNumber-5-icon.png"), Array("Number-6-icon.png", "oNumber-6-icon.png"), Array("Number-7-icon.png", "oNumber-7-icon.png"),Array("Number-7-icon.png", "oNumber-7-icon.png"), Array("Number-8-icon.png", "oNumber-8-icon.png"), Array("Number-9-icon.png", "oNumber-9-icon.png"),Array("icon_done.png", "icon_done.png"), Array("Number-8-icon.png", "oNumber-8-icon.png"),Array("Number-8-icon.png", "oNumber-8-icon.png"));
			
		stepArr = new Array("GeneralInfo","DisclosureInfo", "Objectives", "PropertyA", "PropertyB","LoanDetails","LoanDetails2","EmploymentDetails","AssetDetails","LiabilitiesDetails","finish");
		
		tBox=11;
	} 
		
	imgArr = Array(Array("Number-2-icon.png", "oNumber-2-icon.png"), Array("Number-3-icon.png", "oNumber-3-icon.png"), Array("Number-4-icon.png", "oNumber-4-icon.png"), Array("Number-5-icon.png", "oNumber-5-icon.png"), Array("Number-6-icon.png", "oNumber-6-icon.png"), Array("Number-7-icon.png", "oNumber-7-icon.png"),Array("Number-7-icon.png", "oNumber-7-icon.png"), Array("Number-8-icon.png", "oNumber-8-icon.png"), Array("Number-9-icon.png", "oNumber-9-icon.png"),Array("icon_done.png", "icon_done.png"),Array("icon_done.png", "icon_done.png"));
	
	var curPos = 0;
	var holdPos = 0;
	var curX = 0;
	var curY = 0;  
	var tBox = 11;
	var clients = 6;
	var curClient = 0;
	var clientsNum = 1;
	
	stepArr = new Array("GeneralInfo","DisclosureInfo", "Objectives", "PropertyA", "PropertyB","LoanDetails","LoanDetails2","EmploymentDetails","AssetDetails","LiabilitiesDetails","finish");
	
	function nStep() {
		//SetupDataBinding();
		if (curPos < tBox) {
			if (validForm()) {			
				curPos++; 
				holdPos++;
				cPos();
  				alert(curPos);	
			  jQuery.ajax({
                                           url: 'http://firesalesup.com/visdom/components/com_mortgageapplication/views/mortgageapplication/tmpl/xml-rpc.php?pageid='+curPos,
                                           type: 'post',
                                           dataType: 'json',
                                           data: jQuery('#mainform').serialize(),
                                           success: function(data) {
                                           alert("success file called");    
                                           }
                                       });

				if (document.getElementById('previous').style.display == 'none') {
					document.getElementById('previous').style.display = '';
				}
				if(sval=='1')
				{
					if (curPos == 8) {
						document.getElementById('next').style.display = 'none';
					}
					if (curPos == 8) {
						document.getElementById('previous').style.display = 'none';
					}
				}
				else
				{				 
					if (curPos == 10) {
						document.getElementById('next').style.display = 'none';
					}
					if (curPos == 10) {
						document.getElementById('previous').style.display = 'none';
					}
				}
			}  
		}

	}
	
	function stripslashes (str) {
		return (str+'').replace(/\\(.?)/g, function (s, n1) {
			switch (n1) {
				case '\\':
					return '\\';
				case '0':
					return '\0';
				case '':
					return '';
				default:
					return n1;
			}
		});
	}
	
	function pStep() {
		if (curPos > 0) {
			curPos--;
			cPos();
			if (document.getElementById('next').style.display == 'none') {
				document.getElementById('next').style.display = '';
			}
			
			if (curPos == 0) {
				document.getElementById('previous').style.display = 'none';
			}
		}
	}
	
	function goToStep(st) {
	//alert(st);
		if (st <= holdPos) {
			curPos = st;
			cPos();			
			if(sval=='1')
			{
				if (curPos == 8) {
					document.getElementById('next').style.display = 'none';
				}
				else
				{
					document.getElementById('next').style.display = '';
				}
			}
			else
			{
				if (curPos == 10) {
					document.getElementById('next').style.display = 'none';
				}
				else
				{
					document.getElementById('next').style.display = '';
				}
			}
			
			if(curPos == 0)
			{
				document.getElementById('previous').style.display = 'none';
			}
			else
			{
				document.getElementById('previous').style.display = '';
			}
				
		} else {
			alert('You can only move to steps that you have already completed.');
		}
	}
	
	function cPos() {
		
		jQuery(function() {
			jQuery(".meter > span").each(function() {
				jQuery(this)
					.data("origWidth", jQuery(this).width())
					.width(0)
					.animate({
						width: jQuery(this).data("origWidth")
					}, 1200);
			});
		});	
		//alert(imgArr.length);
		for (y=0; y<imgArr.length; y++) {
			document.getElementById('steps_'+y).src = 'images/wizard/'+imgArr[y][0];
		}
		document.getElementById('steps_'+curPos).src = 'images/wizard/'+imgArr[curPos][1];
		
		for (x=0; x<stepArr.length; x++) {
			if (document.getElementById(stepArr[x])) {
				document.getElementById(stepArr[x]).style.display = 'none';
			}
		}
		if (document.getElementById(stepArr[curPos])) {
			document.getElementById(stepArr[curPos]).style.display = '';
		}
	}
	
	// function addApp() {
		// curClient++;
		// //alert("client Pos: "+curClient);
		// document.getElementById('Clients'+curClient).style.display = 'block';
		// if (curClient == clients) {
			// document.getElementById('addApp').style.display = 'none';
		// }
	// }
	
	// function remApp(num) {
		// //alert("client Pos: "+curClient);
		// document.getElementById('Clients'+num).style.display = 'none';
	// }
	
	//Function for validation
	function signalError(nID, check, arr, name) {	
//	alert(nID);
		if (arr) {
			if (name) {
				var field_n = document.getElementsByName(nID)[0];
			} else {
				var field_n = document.getElementById(nID)[0];
			}
		} else {
			var field_n = $(nID);
		}
		
		if (check) {				
			var cname1="[name='"+nID+"']";
			jQuery(cname1).addClass("shadow");
		} else {
			field_n.style.background = "url('images/wizard/input.gif')";
			var cname1="[name='"+nID+"']";
			jQuery(cname1).removeClass("shadow");
		}
	}
	
	//Function for validation
	function radioError(nID, check) {	
		
		if (check) {
			var cname="[name='"+nID+"']";
			jQuery(cname).addClass("radioerror");
		} else {
			var cname="[name='"+nID+"']";
			jQuery(cname).removeClass("radioerror");
		}
	}
	
	function clientAdd() {
		clientsNum++;
	}
	
	function clientRemove() {
		clientsNum--;
	}
	
	function findPosX(obj) {
		var curleft = 0;
		if(obj.offsetParent)
			while(1) 
			{
			  curleft += obj.offsetLeft;
			  if(!obj.offsetParent)
				break;
			  obj = obj.offsetParent;
			}
		else if(obj.x)
			curleft += obj.x;
		return curleft;
	  }
	
	  function findPosY(obj) {
		var curtop = 0;
		if(obj.offsetParent)
			while(1)
			{
			  curtop += obj.offsetTop;
			  if(!obj.offsetParent)
				break;
			  obj = obj.offsetParent;
			}
		else if(obj.y)
			curtop += obj.y;
		return curtop;
	  }

	
	function addHelp(bT, a) {
		bB = $('helpB');
		if (bB.hasClass('Hide')) {
			b = document.getElementById('helpB');
			btxt = document.getElementById('help_txt');
			btxt.innerHTML = stripslashes(bT);
			
			bB.removeClass('Hide');
			
			b.style.left = (findPosX(a)+15)+"px";/*(curX-5)+"px";*/
			b.style.top = ((findPosY(a)-b.offsetHeight)+1)+"px"; /*(curY-b.offsetHeight)+"px";*/
			
		}
	}
	function getMouseXY(e) {
		var IE = document.all?true:false;
		if (IE) {
			curX = event.clientX + document.body.scrollLeft;
			curY = event.clientY + document.body.scrollTop;
		} else {
			curX = e.pageX;
			curY = e.pageY;
		}
	}
	
	window.addEvent('domready', function() {
		//alert("count: "+getElemsByRel('reqField').length);
		var IE = document.all?true:false;
		if (!IE) {
			document.addEventListener("mousemove", getMouseXY, false);
		} else {
			document.onmousemove = getMouseXY;
		}
		var previous_form_values = null;
		for (key in previous_form_values)
			if (key != 'brokerage_id' && key != 'agent_id') {
				var node = document.getElementsByName(key)[0];
				if (node) {
					node.value = previous_form_values[key];
					if (node.name == 'Mortgage::amortization') {
						var yearsPD = $('y_' + node.name);
						
						var months = Number(node.value);
						yearsPD.value = Math.floor(months/12);
					}
				}
			}		
	});
	
			

	var sCheck = true; //true for testing
	vChange = function(e) {
		sCheck = true; 
	}
    window.onbeforeunload =
            function(e)
            {
				if (!sCheck) {
					e = e || window.event;
					var message = "You will lose all your application information if you use the browser back button or refresh the page."
					if (e) {
						e.returnValue = message;
					}
					return message;
				}
            }

		jQuery(function() {
			jQuery(".meter > span").each(function() {
				jQuery(this)
					.data("origWidth", jQuery(this).width())
					.width(0)
					.animate({
						width: jQuery(this).data("origWidth")
					}, 1200);
			});
		});
 
	
