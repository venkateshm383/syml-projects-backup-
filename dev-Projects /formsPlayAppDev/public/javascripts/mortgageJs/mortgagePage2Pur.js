/**
 * MortgagePage2Pur js file
 * 
 */

/**
 * for load the page if quit alert before quit the page. 
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
 * for validate the form
 */

/**
 * for Address  code for google  address mapping
 */
        
        $(function(){
      	  $("#geocomplete").geocomplete({
      	        types:["geocode", "establishment"]
      	    })
      	    .bind("geocode:result", function (event, result) {
      	        //$.log("Result: " + result.formatted_address);
      	        //alert("Result: " + result.formatted_address);
      	    	document.getElementById("geocomplete").value = result.formatted_address;
      	    })
      	    .bind("geocode:error", function (event, status) {
      	        alert("ERROR: " + status);
      	    })
      	    .bind("geocode:multiple", function (event, results) {
      	       alert("Multiple: " + results.length + " results found");
      	    }); 
        });
        /**
         * Validate the entire form of page2Pur
         * @returns {Boolean}
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
	} 
	
	else {
		document.getElementById("formatted_address11111s").innerHTML = "";
	}

	var downpayment30 = document.forms["myForm"]["downpayment30"].value;
	if (!downpayment30) {
		document.getElementById('cid_299').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('downpayment30Id').focus();
		return false;
	} else {
		document.getElementById("cid_299").innerHTML = "";
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

	var mlsList = document.forms["myForm"]["mlsList"].value;
	if (!mlsList) {
		document.getElementById('widget_settings_69').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		return false;
	} else {
		document.getElementById("widget_settings_69").innerHTML = "";
	}

	return retVal;
}

/**
 * for angular code
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
	
	  $scope.paymtSrc = {
		BankAccount: false,
		RRSPS: false,
		Investments: false,
		Borrowed: false,
		SaleofProperty: false,
		Gift: false,
		PersonalCash: false,
		ExistingEquity: false,
		SweatEnquity: false
	  };
		
	  $scope.checkResults = [];

	  $scope.$watchCollection('paymtSrc', function () {
	    $scope.checkResults = [];
	    angular.forEach($scope.paymtSrc, function (value, key) {
	      if (value) {
	        $scope.checkResults.push(key);
	      }
	    });
	  });
	  $scope.singleModel = 2;
	  var whoWillLive333 = document.forms["myForm"]["whoWillLive33"].value;
      if(whoWillLive333 != null && whoWillLive333 !="")
	  $scope.newItemType3 = whoWillLive333;
      
      var mlsListed333 = document.forms["myForm"]["mlsListed33"].value;
      if(mlsListed333 != null && mlsListed333 !="")
	  $scope.newItemType16 = mlsListed333;
     		
});

/**
 * For restricting symbol and character and other character 
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
 * for toggle
 */

