/**
 * MortgagePage4 js file
 * 
 */

/**
 * for load the page to quit disply before
 */
window.onbeforeunload = function() {
	return "Are you sure?";
};

$(document).ready(function() {
	$('form').submit(function() {
		window.onbeforeunload = null;
	});
});


/**
 * angular code here
 */
var app = angular.module("demoApp", ['ui.bootstrap']);

app.controller('DemoController', function ($scope) {
    init();
    function init() {
        $scope.newItemType = '';
        $scope.company="";
        $scope.change = function () {
            console.log($scope.newItemType)
        };
    }
    $scope.singleModel = 1;
	var mortgageType333 = document.forms["myForm"]["mortgageType33"].value;
	
    if(mortgageType333 !="" && mortgageType333 != null)
	$scope.mortgageInMind = mortgageType333;
    
	var mortgageTerm333 = document.forms["myForm"]["mortgageTerm33"].value;
	
    if(mortgageTerm333 !="" && mortgageTerm333 != null)
	$scope.mortgageTerm = mortgageTerm333;
    
    var lookingFor333 = document.forms["myForm"]["lookingFor33"].value;
    
    if(lookingFor333 !="" && lookingFor333 != null)
	$scope.lookingForAmortize = lookingFor333;
    
});

/**
 * for validate the entire page4
 */

function validateForm() {
	var mortgageInMind = document.forms["myForm"]["mortgageInMind"].value;
	if (!mortgageInMind) {
		document.getElementById('input_204').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		return false;
	} else {

		document.getElementById("input_204").innerHTML = "";
	}

	var mortgageTerm = document.forms["myForm"]["mortgageTerm"].value;
	if (!mortgageTerm) {
		document.getElementById('input_205').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		return false;
	} else {
		document.getElementById("input_205").innerHTML = "";
	}

	var lookingForAmortize = document.forms["myForm"]["lookingForAmortize"].value;
	if (!lookingForAmortize) {
		document.getElementById('widget_settings_38').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		return false;
	} else {
		document.getElementById("widget_settings_38").innerHTML = "";
	}

	var amortizeYear = document.forms["myForm"]["amortizeYear"].value;

	if (!amortizeYear) {
		document.getElementById('cid_244').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		return false;
	} else {
		document.getElementById("cid_244").innerHTML = "";
	}

	return true;
}





/**
 * 
 * @param evt
 * @returns {Boolean}
 */
function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		// alert("Please enter a number (no comma or dash)");
		return false;
	}
	return true;
}