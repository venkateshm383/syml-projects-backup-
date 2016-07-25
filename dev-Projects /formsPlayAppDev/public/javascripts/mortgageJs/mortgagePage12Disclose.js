





/**
 * use for asking that are you want to stay on current page or leave the page on back button or next button
 */


window.onbeforeunload = function () {
    return "Are you sure?";
};

$(document).ready(function () {
    $('form').submit(function () {
        window.onbeforeunload = null;
    });
});







/**
 * 
 * @returns
 */







function validateForm() {
				
					 var fname=document.forms["myForm"]["touchScreen"].value;
					
					
				 	var value= true;
					 var fname=document.forms["myForm"]["touchScreen"].value;
					 var f_name=document.forms["myForm"]["touch_Screen"].value;
					console.log(fname +"++++ "+f_name);
				
						if(!fname){
							document.getElementById("touchScreen1").innerHTML="<p style=\"color:red\">Please Select which Device Using </p>";
							document.getElementById("submiterror").innerHTML="<p style=\"color:red\">There are errors on the form. Please fix them before continuing.</p>";


							value= false;
						}else{
					    	document.getElementById("touchScreen1").innerHTML="";

						} 
					
						var lname = document.forms["myForm"]["agree"].checked;
						if (!lname) {
							
							document.getElementById("agree1").innerHTML = "<p style=\"color:red\">Please agree the condtion</p>";
							document.getElementById("submiterror").innerHTML = "<p style=\"color:red\">There are errors on the form. Please fix them before continuing.</p>";

							value= false;
						} else {

							document.getElementById("agree1").innerHTML = "";

						}
						

					
						

						if (f_name == 'no') {
							console.log("inside f_name");
							if(fname=='no'){
								console.log("inside fname");
							var typedName1 = document.forms["myForm"]["typedName1"].value;
							//var typedName2 = document.forms["myForm"]["typedName2"].value;
							if(!typedName1){
							
							document.getElementById("typedName1").innerHTML = "<p style=\"color:red\">Please enter the name</p>";
							document.getElementById("submiterror").innerHTML = "<p style=\"color:red\">There are errors on the form. Please fix them before continuing.</p>";
							value= false;
							}

							
						else {
							document.getElementById("typedName1").innerHTML = "";

						}
						}else if(fname=='yes'){
							var sign = document.forms["myForm"]["sign1"].value;
							var conva=document.getElementById("blank").toDataURL();

							
							
							if(sign==conva){
								document.getElementById("sign1").innerHTML = "<p style=\"color:red\">Please write elctonic sign</p>";
								document.getElementById("submiterror").innerHTML = "<p style=\"color:red\">There are errors on the form. Please fix them before continuing.</p>";


								value= false;
							}else {
								document.getElementById("sign1").innerHTML = "";

							}
							
							
						}
							
						
						}
						
						if(f_name == 'yes'){
							
							if(fname=='no'){
							console.log("inside the f_name yes");
							
							var typedName1 = document.forms["myForm"]["typedName1"].value;
							var typedName2 = document.forms["myForm"]["typedName2"].value;
							console.log(""+typedName1+" 2nd "+typedName2);
							if(!typedName1 && !typedName2){
								document.getElementById("div_typedName1").innerHTML = "<p style=\"color:red\">Please enter the name</p>";
								document.getElementById("div_typedName2").innerHTML = "<p style=\"color:red\">Please enter the name</p>";
								document.getElementById("submiterror").innerHTML = "<p style=\"color:red\">There are errors on the form. Please fix them before continuing.</p>";

								value=false;
							}else{
								document.getElementById("div_typedName1").innerHTML = "";
								document.getElementById("div_typedName2").innerHTML = "";
							}
							}
							else if(fname=='yes'){
								console.log("fname in yes yes");
								var sign3 = document.forms["myForm"]["sign2"].value;
								var conva2=document.getElementById("blank2").toDataURL();
								console.log("sing3"+sign3+"conva3 +"+conva2);
								if(sign3==conva2){
									console.log("inside sing3 conva2");
									document.getElementById("sign1").innerHTML = "<p style=\"color:red\">Please write elctonic sign</p>";
									document.getElementById("sign2").innerHTML = "<p style=\"color:red\">Please write elctonic sign</p>";
									document.getElementById("submiterror").innerHTML = "<p style=\"color:red\">There are errors on the form. Please fix them before continuing.</p>";

									value= false;
								}else {
									document.getElementById("sign1").innerHTML = "";
									document.getElementById("sign2").innerHTML = "";

								}
							}
							
						}
						

						
						console.log("The value is return "+value);
						
						if(value){
							console.log("inside the loading ");
							 $('#loading').show();
						} 
	return value;
					
					}

/**
 * Get a regular interval for drawing to the screen
 */
(function() {

	// Get a regular interval for drawing to the screen
	window.requestAnimFrame = (function(callback) {
		return window.requestAnimationFrame
				|| window.webkitRequestAnimationFrame
				|| window.mozRequestAnimationFrame
				|| window.oRequestAnimationFrame
				|| window.msRequestAnimaitonFrame
				|| function(callback) {
					window.setTimeout(callback, 1000 / 60);
				};
	})();

	// Set up the canvas
	var canvas = document.getElementById("sig-canvas1");
	var ctx = canvas.getContext("2d");
	ctx.strokeStyle = "#222222";
	ctx.lineWith = 2;

	// Set up the UI
	var sigText = document.getElementById("sig-dataUrl1");
	var sigImage = document.getElementById("sig-image1");
	var clearBtn = document.getElementById("sig-clearBtn1");
	var submitBtn = document
			.getElementById("sig-submitBtn");
	clearBtn
			.addEventListener(
					"click",
					function(e) {
						clearCanvas();
						sigText.innerHTML = "Data URL for your signature will go here!";
						sigImage.setAttribute("src", "");
					}, false);
	submitBtn.addEventListener("click", function(e) {
		var dataUrl = canvas.toDataURL();
		sigText.innerHTML = dataUrl;
		sigImage.setAttribute("src", dataUrl);
	}, false);

	// Set up mouse events for drawing
	var drawing = false;
	var mousePos = {
		x : 0,
		y : 0
	};
	var lastPos = mousePos;
	canvas.addEventListener("mousedown", function(e) {
		drawing = true;
		lastPos = getMousePos(canvas, e);
	}, false);
	canvas.addEventListener("mouseup", function(e) {
		drawing = false;
	}, false);
	canvas.addEventListener("mousemove", function(e) {
		mousePos = getMousePos(canvas, e);
	}, false);

	// Set up touch events for mobile, etc
	canvas.addEventListener("touchstart", function(e) {
		mousePos = getTouchPos(canvas, e);
		var touch = e.touches[0];
		var mouseEvent = new MouseEvent("mousedown", {
			clientX : touch.clientX,
			clientY : touch.clientY
		});
		canvas.dispatchEvent(mouseEvent);
	}, false);
	canvas.addEventListener("touchend", function(e) {
		var mouseEvent = new MouseEvent("mouseup", {});
		canvas.dispatchEvent(mouseEvent);
	}, false);
	canvas.addEventListener("touchmove", function(e) {
		var touch = e.touches[0];
		var mouseEvent = new MouseEvent("mousemove", {
			clientX : touch.clientX,
			clientY : touch.clientY
		});
		canvas.dispatchEvent(mouseEvent);
	}, false);

	// Prevent scrolling when touching the canvas
	document.body.addEventListener("touchstart",
			function(e) {
				if (e.target == canvas) {
					e.preventDefault();
				}
			}, false);
	document.body.addEventListener("touchend", function(e) {
		if (e.target == canvas) {
			e.preventDefault();
		}
	}, false);
	document.body.addEventListener("touchmove",
			function(e) {
				if (e.target == canvas) {
					e.preventDefault();
				}
			}, false);

	// Get the position of the mouse relative to the canvas
	function getMousePos(canvasDom, mouseEvent) {
		var rect = canvasDom.getBoundingClientRect();
		return {
			x : mouseEvent.clientX - rect.left,
			y : mouseEvent.clientY - rect.top
		};
	}

	// Get the position of a touch relative to the canvas
	function getTouchPos(canvasDom, touchEvent) {
		var rect = canvasDom.getBoundingClientRect();
		return {
			x : touchEvent.touches[0].clientX - rect.left,
			y : touchEvent.touches[0].clientY - rect.top
		};
	}

	// Draw to the canvas
	function renderCanvas() {
		if (drawing) {
			ctx.moveTo(lastPos.x, lastPos.y);
			ctx.lineTo(mousePos.x, mousePos.y);
			ctx.stroke();
			lastPos = mousePos;
		}
	}

	// Clear the canvas
	function clearCanvas() {
		canvas.width = canvas.width;
	}

	// Allow for animation
	(function drawLoop() {
		requestAnimFrame(drawLoop);
		renderCanvas();
	})();

})();



/**
 * Get a regular interval for drawing to the screen
 */



(function() {

	// Get a regular interval for drawing to the screen
	window.requestAnimFrame = (function(callback) {
		return window.requestAnimationFrame
				|| window.webkitRequestAnimationFrame
				|| window.mozRequestAnimationFrame
				|| window.oRequestAnimationFrame
				|| window.msRequestAnimaitonFrame
				|| function(callback) {
					window.setTimeout(callback, 1000 / 60);
				};
	})();

	// Set up the canvas
	var canvas = document.getElementById("sig-canvas2");
	var ctx = canvas.getContext("2d");
	ctx.strokeStyle = "#222222";
	ctx.lineWith = 2;

	// Set up the UI
	var sigText = document.getElementById("sig-dataUrl2");
	var sigImage = document.getElementById("sig-image");
	var clearBtn = document.getElementById("sig-clearBtn2");
	var submitBtn = document
			.getElementById("sig-submitBtn");
	clearBtn
			.addEventListener(
					"click",
					function(e) {
						clearCanvas();
						sigText.innerHTML = "Data URL for your signature will go here!";
						sigImage.setAttribute("src", "");
					}, false);
	
	submitBtn.addEventListener("click", function(e) {
		var dataUrl = canvas.toDataURL();
		sigText.innerHTML = dataUrl;
		sigImage.setAttribute("src", dataUrl);
	}, false);

	// Set up mouse events for drawing
	var drawing = false;
	var mousePos = {
		x : 0,
		y : 0
	};
	var lastPos = mousePos;
	canvas.addEventListener("mousedown", function(e) {
		drawing = true;
		lastPos = getMousePos(canvas, e);
	}, false);
	canvas.addEventListener("mouseup", function(e) {
		drawing = false;
	}, false);
	canvas.addEventListener("mousemove", function(e) {
		mousePos = getMousePos(canvas, e);
	}, false);

	// Set up touch events for mobile, etc
	canvas.addEventListener("touchstart", function(e) {
		mousePos = getTouchPos(canvas, e);
		var touch = e.touches[0];
		var mouseEvent = new MouseEvent("mousedown", {
			clientX : touch.clientX,
			clientY : touch.clientY
		});
		canvas.dispatchEvent(mouseEvent);
	}, false);
	canvas.addEventListener("touchend", function(e) {
		var mouseEvent = new MouseEvent("mouseup", {});
		canvas.dispatchEvent(mouseEvent);
	}, false);
	canvas.addEventListener("touchmove", function(e) {
		var touch = e.touches[0];
		var mouseEvent = new MouseEvent("mousemove", {
			clientX : touch.clientX,
			clientY : touch.clientY
		});
		canvas.dispatchEvent(mouseEvent);
	}, false);

	// Prevent scrolling when touching the canvas
	document.body.addEventListener("touchstart",
			function(e) {
				if (e.target == canvas) {
					e.preventDefault();
				}
			}, false);
	document.body.addEventListener("touchend", function(e) {
		if (e.target == canvas) {
			e.preventDefault();
		}
	}, false);
	document.body.addEventListener("touchmove",
			function(e) {
				if (e.target == canvas) {
					e.preventDefault();
				}
			}, false);

	// Get the position of the mouse relative to the canvas
	function getMousePos(canvasDom, mouseEvent) {
		var rect = canvasDom.getBoundingClientRect();
		return {
			x : mouseEvent.clientX - rect.left,
			y : mouseEvent.clientY - rect.top
		};
	}

	// Get the position of a touch relative to the canvas
	function getTouchPos(canvasDom, touchEvent) {
		var rect = canvasDom.getBoundingClientRect();
		return {
			x : touchEvent.touches[0].clientX - rect.left,
			y : touchEvent.touches[0].clientY - rect.top
		};
	}

	// Draw to the canvas
	function renderCanvas() {
		if (drawing) {
			ctx.moveTo(lastPos.x, lastPos.y);
			ctx.lineTo(mousePos.x, mousePos.y);
			ctx.stroke();
			lastPos = mousePos;
		}
	}

	// Clear the canvas
	function clearCanvas() {
		canvas.width = canvas.width;
	}

	// Allow for animation
	(function drawLoop() {
		requestAnimFrame(drawLoop);
		renderCanvas();
	})();

})();

/**
 * 
 */
(function() {

	// Get a regular interval for drawing to the screen
	window.requestAnimFrame = (function(callback) {
		return window.requestAnimationFrame
				|| window.webkitRequestAnimationFrame
				|| window.mozRequestAnimationFrame
				|| window.oRequestAnimationFrame
				|| window.msRequestAnimaitonFrame
				|| function(callback) {
					window.setTimeout(callback, 1000 / 60);
				};
	})();

	// Set up the canvas
	var canvas = document.getElementById("sig-canvas");
	var ctx = canvas.getContext("2d");
	ctx.strokeStyle = "#222222";
	ctx.lineWith = 2;

	// Set up the UI
	var sigText = document.getElementById("sig-dataUrl");
	var sigImage = document.getElementById("sig-image");
	var clearBtn = document.getElementById("sig-clearBtn");
	var submitBtn = document
			.getElementById("sig-submitBtn");
	clearBtn
			.addEventListener(
					"click",
					function(e) {
						clearCanvas();
						sigText.innerHTML = "Data URL for your signature will go here!";
						sigImage.setAttribute("src", "");
					}, false);
	submitBtn.addEventListener("click", function(e) {
		var dataUrl = canvas.toDataURL();
		sigText.innerHTML = dataUrl;
		sigImage.setAttribute("src", dataUrl);
	}, false);

	// Set up mouse events for drawing
	var drawing = false;
	var mousePos = {
		x : 0,
		y : 0
	};
	var lastPos = mousePos;
	canvas.addEventListener("mousedown", function(e) {
		drawing = true;
		lastPos = getMousePos(canvas, e);
	}, false);
	canvas.addEventListener("mouseup", function(e) {
		drawing = false;
	}, false);
	canvas.addEventListener("mousemove", function(e) {
		mousePos = getMousePos(canvas, e);
	}, false);

	// Set up touch events for mobile, etc
	canvas.addEventListener("touchstart", function(e) {
		mousePos = getTouchPos(canvas, e);
		var touch = e.touches[0];
		var mouseEvent = new MouseEvent("mousedown", {
			clientX : touch.clientX,
			clientY : touch.clientY
		});
		canvas.dispatchEvent(mouseEvent);
	}, false);
	canvas.addEventListener("touchend", function(e) {
		var mouseEvent = new MouseEvent("mouseup", {});
		canvas.dispatchEvent(mouseEvent);
	}, false);
	canvas.addEventListener("touchmove", function(e) {
		var touch = e.touches[0];
		var mouseEvent = new MouseEvent("mousemove", {
			clientX : touch.clientX,
			clientY : touch.clientY
		});
		canvas.dispatchEvent(mouseEvent);
	}, false);

	// Prevent scrolling when touching the canvas
	document.body.addEventListener("touchstart",
			function(e) {
				if (e.target == canvas) {
					e.preventDefault();
				}
			}, false);
	document.body.addEventListener("touchend", function(e) {
		if (e.target == canvas) {
			e.preventDefault();
		}
	}, false);
	document.body.addEventListener("touchmove",
			function(e) {
				if (e.target == canvas) {
					e.preventDefault();
				}
			}, false);

	// Get the position of the mouse relative to the canvas
	function getMousePos(canvasDom, mouseEvent) {
		var rect = canvasDom.getBoundingClientRect();
		return {
			x : mouseEvent.clientX - rect.left,
			y : mouseEvent.clientY - rect.top
		};
	}

	// Get the position of a touch relative to the canvas
	function getTouchPos(canvasDom, touchEvent) {
		var rect = canvasDom.getBoundingClientRect();
		return {
			x : touchEvent.touches[0].clientX - rect.left,
			y : touchEvent.touches[0].clientY - rect.top
		};
	}

	// Draw to the canvas
	function renderCanvas() {
		if (drawing) {
			ctx.moveTo(lastPos.x, lastPos.y);
			ctx.lineTo(mousePos.x, mousePos.y);
			ctx.stroke();
			lastPos = mousePos;
		}
	}

	// Clear the canvas
	function clearCanvas() {
		canvas.width = canvas.width;
	}

	// Allow for animation
	(function drawLoop() {
		requestAnimFrame(drawLoop);
		renderCanvas();
	})();

})();

/**
 *
 */

var app = angular.module("demoApp", [ 'ui.bootstrap' ]);

app.controller('DemoController', function($scope) {
	init();
	function init() {
		console.log("init mehthod called when loading the DemoController ");
		$scope.newItemType = '';
		$scope.company = "";
		$scope.change = function() {
			console.log($scope.newItemType)
		};
	}
});

