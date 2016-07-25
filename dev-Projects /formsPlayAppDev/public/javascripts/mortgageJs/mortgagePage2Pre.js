/**
 * MortgagePage2Pre js file
 * 
 */

/**
 * onload page for quit
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
 * for selection option
 */
window.onload = function() {
	SelectElement();
};
function SelectElement() {
	var buildRendered = document.forms["myForm"]["applicantProvience33"].value;
	document.forms["myForm"]["provience"].value = buildRendered;
}

/**
 * angular code here
 */
var app = angular.module("demoApp", [ 'ui.bootstrap' ]);

app.controller('DemoController', function($scope) {
	
	init();
	function init() {
	
		$scope.newItemType = '';
		$scope.company = "";
		$scope.change = function() {
			console.log($scope.newItemType)
		};
	}

	$scope.singleModel = 1;

	$scope.paymtSrcModel = {
		BankAccount : false,
		RRSPS : false,
		Investments : false,
		Borrowed : false,
		SaleofProperty : false,
		Gift : false,
		PersonalCash : false,
		ExistingEquity : false,
		SweatEnquity : false
	};

	$scope.checkResults = [];

	$scope.$watchCollection('paymtSrcModel', function() {
		$scope.checkResults = [];
		angular.forEach($scope.paymtSrcModel, function(value, key) {
			if (value) {
				$scope.checkResults.push(key);
			}
		});
	});
	$scope.singleModel = 2;
	var whoWillLive333 = document.forms["myForm"]["whoWillLive33"].value;
	if (whoWillLive333 != null && whoWillLive333 != "")
		$scope.newItemType3 = whoWillLive333;
});

/**
 * validate the entire form
 * 
 */

function validateForm() {
	var provience = document.forms["myForm"]["provience"].value;
	if (!provience) {
		document.getElementById('1234').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('1234').focus();
		return false;
	} else {
		document.getElementById("1234").innerHTML = "";
	}
	var purchaseprice = document.forms["myForm"]["purchaseprice"].value;
	if (!purchaseprice) {
		document.getElementById('cid_24').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('purchasepriceID').focus();
		return false;
	} else {
		document.getElementById("cid_24").innerHTML = "";
	}

	var downpayment = document.forms["myForm"]["downpayment"].value;
	if (!downpayment) {
		document.getElementById('cid_25').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('input_25').focus();
		return false;
	} else {
		document.getElementById("cid_25").innerHTML = "";
	}

	var bankAccount = document.forms["myForm"]["bankAccount"].value;
	var rrsps = document.forms["myForm"]["rrsps"].value;
	var investments = document.forms["myForm"]["investments"].value;
	var borrowed = document.forms["myForm"]["borrowed"].value;
	var saleofProperty = document.forms["myForm"]["saleofProperty"].value;
	var gift = document.forms["myForm"]["gift"].value;
	var personalCash = document.forms["myForm"]["personalCash"].value;
	var existingEquity = document.forms["myForm"]["existingEquity"].value;
	var sweatEnquity = document.forms["myForm"]["sweatEnquity"].value;
	var retVal = true;

	if (bankAccount == 'false' && rrsps == 'false' && investments == 'false'
			&& borrowed == 'false' && saleofProperty == 'false'
			&& gift == 'false' && personalCash == 'false'
			&& existingEquity == 'false' && sweatEnquity == 'false') {

		console.log("after inside bank Account -->");
		document.getElementById('widget_settings_27').innerHTML = "<span style='color:red'>*This field is Required , Atleast Select one of them.</span>";

		retVal = false;
	} else {

		document.getElementById("widget_settings_27").innerHTML = "";
	}

	var living4Financing = document.forms["myForm"]["living4Financing"].value;
	if (!living4Financing) {
		document.getElementById('input_203').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		return false;
	} else {
		document.getElementById("input_203").innerHTML = "";
	}

	if (living4Financing == "Renter" || living4Financing == "OwnerAndRenter") {
		var rentalAmount = document.forms["myForm"]["rentalAmount"].value;
		if (!rentalAmount) {
			document.getElementById('input_39').innerHTML = "<span style='color:red'>*This field is Required.</span>";
			document.getElementById('input_394').focus();
			retVal = false;
		} else {
			document.getElementById("input_39").innerHTML = "";
		}
	}

	return retVal;
}

/**
 * for Number input
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

/**
 * data-toggle
 */

