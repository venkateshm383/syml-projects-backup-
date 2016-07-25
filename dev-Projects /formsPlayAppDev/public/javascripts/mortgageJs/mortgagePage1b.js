/**
 * for onload the page
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
 * for angular Code
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
    
    
    var appRelStatu=document.forms["myForm"]["status"].value;
    if(appRelStatu !="" && appRelStatu != null)
        $scope.coappRelStatuss=appRelStatu; 

    
    
});

/**
 * for validate the entire form
 */
function validateForm() {

	var adFirstName = document.forms["myForm"]["adFirstName"].value;
	if (!adFirstName) {
		document.getElementById('input_1105').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('ad_FirstName').focus();
		return false;
	} else {
		document.getElementById("input_1105").innerHTML = "";
	}

	var adLastName = document.forms["myForm"]["adLastName"].value;
	if (!adLastName) {
		document.getElementById('input_1106').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('ad_LastName').focus();
		return false;
	} else {
		document.getElementById("input_1106").innerHTML = "";
	}

	var adEmail = document.forms["myForm"]["adEmail"].value;
	if (!adEmail) {
		document.getElementById('input_1107').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('ad_Email').focus();
		return false;
	} else {
		document.getElementById("input_1107").innerHTML = "";
	}
	var re=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	//var re = /^[a-zA-Z0-9\.\_]+\@@{1}[a-zA-Z0-9]+\.\w{2,4}$/;
	if (!re.test(adEmail)) {
		document.getElementById('input_1107').innerHTML = "<span style='color:red'>*Provide valid email.</span>";
		document.getElementById('ad_Email').focus();
		return false;
	} else {
		document.getElementById("input_1107").innerHTML = "";
	}

	var adConfirmEmail = document.forms["myForm"]["adConfirmEmail"].value;
	if (!adConfirmEmail) {
		document.getElementById('input_1108').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('ad_ConfirmEmail').focus();
		return false;
	} else {
		document.getElementById("input_1108").innerHTML = "";
	}
	var re=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	//var re = /^[a-zA-Z0-9\.\_]+\@@{1}[a-zA-Z0-9]+\.\w{2,4}$/;
	if (!re.test(adConfirmEmail)) {
		document.getElementById('input_1108').innerHTML = "<span style='color:red'>*Provide valid email.</span>";
		document.getElementById('ad_ConfirmEmail').focus();
		return false;
	} else {
		document.getElementById("input_1108").innerHTML = "";
	}
	if (adEmail != adConfirmEmail) {
		document.getElementById('input_1108').innerHTML = "<span style='color:red'>*Email mismatch.</span>";
		document.getElementById('ad_ConfirmEmail').focus();
		return false;
	} else {
		document.getElementById("input_1108").innerHTML = "";
	}

	var coApplMobPhone = document.forms["myForm"]["coApplMobPhone"].value;
	if (!coApplMobPhone) {
		document.getElementById('input_11088').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('phonedatata7').focus();
		return false;
	} else {
		document.getElementById("input_11088").innerHTML = "";
	}

	var coAppRelStatus = document.forms["myForm"]["coAppRelStatus"].value;
	if (!coAppRelStatus) {
		document.getElementById('input_41055').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('coRelStatus').focus();
		return false;
	} else {
		document.getElementById("input_41055").innerHTML = "";
	}

	return true;
}


/**
 * for mask input ----- 
 */
$(function() {
    $.mask.definitions['~'] = "[+-]";

    $("#phonedatata7").mask("999-999-9999");
});




/**
 * on change  background color of select drop down field
 */

function changeBackgroundOfSelectedField() {
		var coAppRelStatus = document.forms["myForm"]["coAppRelStatus"].value;
		if (!coAppRelStatus) {
			document.getElementById('coRelStatus').style.backgroundColor = "#ffffff";
		} else {
			document.getElementById('coRelStatus').style.backgroundColor = "#33cc33";
		}
}