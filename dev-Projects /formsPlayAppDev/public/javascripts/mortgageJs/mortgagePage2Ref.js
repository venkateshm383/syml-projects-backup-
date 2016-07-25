/**
 * MortgagePage2Ref js file
 * 
 */

/**
 * for load the page if quit alert before quit the page.
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
	
	  $scope.addFunds = {
		BuyProperty: false,
		PayOffDebt: false,
		BuyInvestment: false,
		BuyVehicle: false,
		Renovate: false,
		Refurnish: false,
		Vacation: false,
		RecVehicle: false,
		Other: false
		
	  };
		
	  $scope.checkResults = [];

	  $scope.$watchCollection('addFunds', function () {
	    $scope.checkResults = [];
	    angular.forEach($scope.addFunds, function (value, key) {
	      if (value) {
	        $scope.checkResults.push(key);
	      }
	    });
	  });
	  $scope.singleModel = 2;
	  var whoWillLive333 = document.forms["myForm"]["whoWillLive33"].value;
      if(whoWillLive333 != null && whoWillLive333 !="")
	  $scope.newItemType3 = whoWillLive333;
    
});

/**
 * Validate the entire form of pageRef
 */
function validateForm() {
	var formatted_address = document.forms["myForm"]["formatted_address"].value;
	var regeUpperCase="[ABCEGHJKLMNPRSTVXY][0-9][ABCEGHJKLMNPRSTVWXYZ] ?[0-9][ABCEGHJKLMNPRSTVWXYZ][0-9]";
	var regexLowerCase="[abceghjklmnprstvxy][0-9][abceghjklmnprstvwxyz] ?[0-9][abceghjklmnprstvwxyz][0-9]";

	
	if (!formatted_address) {
		document.getElementById('formatted_address11111s').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('geocomplete').focus();
		return false;
	}else if(!(formatted_address.match(regeUpperCase) || formatted_address.match(regexLowerCase)) ){
		document.getElementById('formatted_address11111s').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
		document.getElementById('geocomplete').focus();	
		return false;
	}else {
		document.getElementById("formatted_address11111s").innerHTML = "";
	}

	var refivalue = document.forms["myForm"]["refivalue"].value;
	if (!refivalue) {
		document.getElementById('input_101').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('refivalueID').focus();
		return false;
	} else {
		document.getElementById("input_101").innerHTML = "";
	}
	/**
	 * need to change here
	 * 
	 */
	

	var additionalFunds = document.forms["myForm"]["additionalFunds"].value;
	if (!additionalFunds) {
		document.getElementById('widget_settings_46').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('additionalFundsID').focus();
		return false;
	} else {
		document.getElementById("widget_settings_46").innerHTML = "";
	}
	if(parseInt(refivalue) < parseInt(additionalFunds)){
		alert("coming")
		document.getElementById('not_be_greater_than_property').innerHTML = "<span style='color:red'>*Property value cannot be less than Additional Amount.</span>";
		
		return false;
	}else{
		document.getElementById('not_be_greater_than_property').innerHTML = "";
	}
	var buyProperty = document.forms["myForm"]["buyProperty"].value;
	var payOffDebt = document.forms["myForm"]["payOffDebt"].value;
	var buyInvestment = document.forms["myForm"]["buyInvestment"].value;
	var buyVehicle = document.forms["myForm"]["buyVehicle"].value;
	var renovate = document.forms["myForm"]["renovate"].value;
	var refurnish = document.forms["myForm"]["refurnish"].value;
	var vacation = document.forms["myForm"]["vacation"].value;
	var recVehicle = document.forms["myForm"]["recVehicle"].value;
	var other = document.forms["myForm"]["other"].value;
	var retVal = true;

	if (buyProperty == 'false' && payOffDebt == 'false'
			&& buyInvestment == 'false' && buyVehicle == 'false'
			&& renovate == 'false' && refurnish == 'false'
			&& vacation == 'false' && recVehicle == 'false' && other == 'false') {

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
 * Google Address dynamically on given first letter .
 */
$(function() {
	$("#geocomplete")
			.geocomplete({
				types : [ "geocode", "establishment" ]
			})
			.bind(
					"geocode:result",
					function(event, result) {
						// $.log("Result: " + result.formatted_address);
						// alert("Result: " + result.formatted_address);
						document.getElementById("geocomplete").value = result.formatted_address;
					}).bind("geocode:error", function(event, status) {
				alert("ERROR: " + status);
			}).bind("geocode:multiple", function(event, results) {
				alert("Multiple: " + results.length + " results found");
			});
});
/**
 * for number input
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
