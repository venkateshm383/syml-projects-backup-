/**
 * For exceptional interruption
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
 * Angular code
 * */
var app = angular.module("demoApp", ['ui.bootstrap']);

    app.controller('DemoController', function ($scope) {
        init();
        function init() {
        	
     console.log("<--- inside init function --> ");
            $scope.newItemType = '';
            $scope.company="";
            $scope.change = function () {
            	
            		$scope.asset.None='false'
            			$scope.asset.None=''
            			
                console.log(""+$scope.newItemType)
               
            };
            
 $scope.change1 = function () {
	 console.log("new change ");
	 			$scope.asset.Vehicle='false'
				 $scope.asset.Vehicle=''
			 	 $scope.asset.BankAccount='false'
			 	 $scope.asset.BankAccount=''
				 $scope.asset.RRSPTSFA='false'
				 $scope.asset.RRSPTSFA=''
				 $scope.asset.Investments='false'
				 $scope.asset.Investments=''
				 $scope.asset.Other='false'
				 $scope.asset.Other=''
			 
         };
            
           	$scope.singleModel = 1;
			
			  $scope.asset = {
					  
				Vehicle:false,
				BankAccount:false,
				RRSPTSFA:false,
				Investments:false,
				Other:false,
				None:false
			  };
				
			  $scope.assetResults = [];

			  $scope.$watchCollection('asset', function () {
			    $scope.assetResults = [];
			    angular.forEach($scope.asset, function (value, key) {
			      if (value) {
			    	  console.log("value for checkbox"+value);
			        $scope.assetResults.push(key);
			      }
			    });
			    console.log("assetResults-->"+$scope.assetResults);
			  });	
        }
    });
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
 * 
 * @returns {Boolean}
 * this function validate the form
 */

function validateForm() {
	
	var vehicle = document.forms["myForm"]["Vehicle"].value;
	console.log("vehicle-->" + vehicle);
	var bankAccount = document.forms["myForm"]["BankAccount"].value;
	console.log("BANKaCCOUNT---." + bankAccount);
	var rrsp = document.forms["myForm"]["RRSPTSFA"].value;
	var investments = document.forms["myForm"]["Investments"].value;
	var other = document.forms["myForm"]["Other"].value;
	var none = document.forms["myForm"]["None"].value;
	

console.log("---->"+vehicle+"--");
	if ((vehicle == "false" || vehicle=='') && (bankAccount == "false" ||  bankAccount=='') && (rrsp == "false" || rrsp=='')  
			&& (investments == "false" || investments=='') && (other == "false" || other=='')&& (none == "false" || none=='')) {
		document.getElementById('role1').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		return false;
	} else 
	
	{
		
		
		
		console.log("vehicle-->"+vehicle);
		console.log("bankAccount-->"+bankAccount);
		console.log("rrsp-->"+rrsp);
		console.log("investments-->"+investments);
		console.log("other-->"+other);
		console.log("none-->"+none);
		
		document.getElementById("role1").innerHTML = "";
	}
/**
 * If Vehicle selected  the vehicle table row add dynamically max row is 6
 * For each row , which the row added has validation.
 */
	
	if (vehicle=="true") {
	
		console.log("Inside Vehicle selected.");
		var vehicletable = document.getElementById('customFields');
		var vehicle_row = vehicletable.rows.length;
		console.log("length of table Vehicle--->" + vehicle_row);

		for (i = 0; i < vehicle_row; i++) {
		
			var vehicle_inputs = vehicletable.rows.item(i)
					.getElementsByTagName("input");
			var Vehichle_length = vehicle_inputs.length;

			for (var j = 0; j < Vehichle_length; j++) {
				var vehicle_designation = vehicle_inputs[1].value;
				var vehicle_value = vehicle_inputs[2].value;
				
				if (vehicle_designation == "") {

					document.getElementById('description_id').innerHTML = "<span style='color:red'>*Description  field is Required.</span>";
					return false;
				} else {
					document.getElementById('description_id').innerHTML = "";
				}
				if (vehicle_value == "") {
					document.getElementById('description_id').innerHTML = "<span style='color:red'>*Value  field is Required.</span>";
					return false;
				} else {
					document.getElementById('description_id').innerHTML = "";
				}

			}
			
			var vehicle_select = vehicletable.rows.item(i)
			.getElementsByTagName("select");
			var Vehichle_select_length = vehicle_select.length;
			for (var k = 0; k < Vehichle_select_length; k++) {
				var vehicle_selected_value = vehicle_select[0].value;
				if (vehicle_selected_value == "") {

					document.getElementById('description_id').innerHTML = "<span style='color:red'>*Ownership  field is Required.</span>";
					return false;
				} else {
					document.getElementById('description_id').innerHTML = "";
				}
			}
			
			
		}

	}
	
	/**
	 * If Bank Account selected  the Bank Account table row add dynamically max row is 6
	 * For each row , which the row added has validation.
	 */
	 if (bankAccount == "true") {

		 console.log("Inside bankAccount.");
		 var banktable = document.getElementById('customFields2');
		 var bank_row = banktable.rows.length;
		 console.log("length of table Vehicle--->" + bank_row);

		 for (i = 0; i < bank_row; i++) {
			 var bank_inputs = banktable.rows.item(i)
			 	.getElementsByTagName("input");
			 var bank_length = bank_inputs.length;

			 for (var j = 0; j < bank_length; j++) {
				 var bank_designation = bank_inputs[1].value;
				 var bank_value = bank_inputs[2].value;

					if (bank_designation == "") {
			
						document.getElementById('description_id_2').innerHTML = "<span style='color:red'>*Description  field is Required.</span>";
						return false;
					} else {
						document.getElementById('description_id_2').innerHTML = "";
					}
					if (bank_value == "") {
						document.getElementById('description_id_2').innerHTML = "<span style='color:red'>*Value  field is Required.</span>";
						return false;
					} else {
						document.getElementById('description_id_2').innerHTML = "";
					}
					
			 }
			 var bank_select = banktable.rows.item(i)
			 	.getElementsByTagName("select");
			
				var bank_select_length = bank_select.length;
				for (var k = 0; k < bank_select_length; k++) {
					var bank_selected_value = bank_select[0].value;
					if (bank_selected_value == "") {

						document.getElementById('description_id_2').innerHTML = "<span style='color:red'>*Ownership  field is Required.</span>";
						return false;
					} else {
						document.getElementById('description_id_2').innerHTML = "";
					}
				}
}


	}
	
	/**
	 * If RRSP selected  the RRSP table row add dynamically max row is 6
	 * For each row , which the row added has validation.
	 */
	
	if(rrsp=="true"){
		console.log("inside RRSP");
		
		var rrsptable = document.getElementById('customFields3');
		var rrsp_row = rrsptable.rows.length;
		console.log("length of table-rrsp-->" + rrsp_row);

		for (i = 0; i < rrsp_row; i++) {
			var rrsp_inputs = rrsptable.rows.item(i)
					.getElementsByTagName("input");
			var rrsp_length = rrsp_inputs.length;

			for (var j = 0; j < rrsp_length; j++) {
				var rrsp_designation = rrsp_inputs[1].value;
				var rrsp_value = rrsp_inputs[2].value;

				if (rrsp_designation == "") {

					document.getElementById('description_id_3').innerHTML = "<span style='color:red'>*Description  field is Required.</span>";
					return false;
				}else{
					document.getElementById('description_id_3').innerHTML = "";
				} 
				
				 if (rrsp_value == "") {
					 document.getElementById('description_id_3').innerHTML = "<span style='color:red'>*Value  field is Required.</span>";
					return false;
				}else{
					document.getElementById('description_id_3').innerHTML = "";
				}

			}
			
			var rrsp_select = rrsptable.rows.item(i)
			.getElementsByTagName("select");
			
				var rrsp_select_length = rrsp_select.length;
				for (var k = 0; k < rrsp_select_length; k++) {
					var rrsp_selected_value = rrsp_select[0].value;
					if (rrsp_selected_value == "") {

						document.getElementById('description_id_3').innerHTML = "<span style='color:red'>*Ownership  field is Required.</span>";
						return false;
					} else {
						document.getElementById('description_id_3').innerHTML = "";
					}
				}
			
		}

	}
	
	
	/**
	 * If investments selected  the investments table row add dynamically max row is 6
	 * For each row , which the row added has validation.
	 */
	
	if(investments=="true"){
		console.log("inside investments");
		
		var investmenttable = document.getElementById('customFields4');
		var investment_row = investmenttable.rows.length;
		console.log("length of table-investments-->" + investment_row);

		for (i = 0; i < investment_row; i++) {
			var investment_inputs = investmenttable.rows.item(i)
					.getElementsByTagName("input");
			var investment_length = investment_inputs.length;

			for (var j = 0; j < investment_length; j++) {
				var investment_designation = investment_inputs[1].value;
				var investment_value = investment_inputs[2].value;

				if (investment_designation == "") {

					document.getElementById('description_id_4').innerHTML = "<span style='color:red'>*Description  field is Required.</span>";
					return false;
				}else{
					document.getElementById('description_id_4').innerHTML = "";
				} 
				
				 if (investment_value == "") {
					 document.getElementById('description_id_4').innerHTML = "<span style='color:red'>*Value  field is Required.</span>";
					return false;
				}else{
					document.getElementById('description_id_4').innerHTML = "";
				}

			}
			var investment_select = investmenttable.rows.item(i)
			.getElementsByTagName("select");
			
				var investment_select_length = investment_select.length;
				for (var k = 0; k < investment_select_length; k++) {
					var investment_selected_value = investment_select[0].value;
					if (investment_selected_value == "") {

						document.getElementById('description_id_4').innerHTML = "<span style='color:red'>*Ownership  field is Required.</span>";
						return false;
					} else {
						document.getElementById('description_id_4').innerHTML = "";
					}
				}
		}

	}
	
	
	/**
	 * If Other Selected  the Other table row add dynamically max row is 6
	 * For each row , which the row added has validation.
	 */
	
	if(other=="true"){
		console.log("inside other");
		
		var othertable = document.getElementById('customFields5');
		var other_row = othertable.rows.length;
		console.log("length of table-other-->" + other_row);

		for (i = 0; i < other_row; i++) {
			var other_inputs = othertable.rows.item(i)
					.getElementsByTagName("input");
			var other_length = other_inputs.length;

			for (var j = 0; j < other_length; j++) {
				var other_designation = other_inputs[1].value;
				var other_value = other_inputs[2].value;

				if (other_designation == "") {

					document.getElementById('description_id_5').innerHTML = "<span style='color:red'>*Description  field is Required.</span>";
					return false;
				}else{
					document.getElementById('description_id_5').innerHTML = "";
				} 
				
				 if (other_value == "") {
					 document.getElementById('description_id_5').innerHTML = "<span style='color:red'>*Value  field is Required.</span>";
					return false;
				}else{
					document.getElementById('description_id_5').innerHTML = "";
				}

			}
			
			var other_select = othertable.rows.item(i)
			.getElementsByTagName("select");
			
				var other_select_length = other_select.length;
				for (var k = 0; k < other_select_length; k++) {
					var other_selected_value = other_select[0].value;
					if (other_selected_value == "") {

						document.getElementById('description_id_5').innerHTML = "<span style='color:red'>*Ownership  field is Required.</span>";
						return false;
					} else {
						document.getElementById('description_id_5').innerHTML = "";
					}
				}
		}

	}
	
	
	
	return true;

	

}

window.onbeforeunload = function () {
    return "Are you sure?";
};

$(document).ready(function () {
    $('form').submit(function () {
        window.onbeforeunload = null;
    });
});

/**
 * dynamically add row for Vehicle
 */
"applName"
$(document).ready(function(){
var count=0;

var appName = document.forms["myForm"]["applName"].value;
var coappName = document.forms["myForm"]["coAppName"].value;

$(".addCF").click(function(){
	count=count+1;
	
	if(count < 6){
	if(coappName !="")
		$("#customFields").append('<tr  valign="top"><th scope="row"></th><td class="code"><input type="text" class="form-control" id="asset" name="asset[]" value="Vehicle" readonly="readonly"/></td><td><input type="text" class="form-control" id="description" name="description[]" value="" placeholder="year/make/model" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value" data-type="input-number" name="value[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation[]" id="designation" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="'+coappName+'">'+coappName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF glyphicon glyphicon-remove">Remove</a></td></tr>');
	else
		$("#customFields").append('<tr  valign="top"><th scope="row"></th><td class="code"><input type="text" class="form-control" id="asset" name="asset[]" value="Vehicle" readonly="readonly"/></td><td><input type="text" class="form-control" id="description" name="description[]" value="" placeholder="year/make/model" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value" data-type="input-number" name="value[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation[]" id="designation" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF glyphicon glyphicon-remove">Remove</a></td></tr>');
}else{
	alert("Sorry!!! You can't add more than 6 Vehicle Assets.");
}
});
$("#customFields").on('click','.remCF',function(){
    $(this).parent().parent().remove();
});
});

/**
 * dynamically add row for Bank Account
 * 
 */
$(document).ready(function(){
	var count=0;
	var appName = document.forms["myForm"]["applName"].value;
	var coappName = document.forms["myForm"]["coAppName"].value;

	$(".addCF2").click(function(){
		count=count+1;
		if(count < 6){
			if(coappName !="")
		$("#customFields2").append('<tr valign="top"><th scope="row"></th><td><input type="text" class="form-control" id="asset1" name="asset1[]" value="Bank Account" readonly="readonly" /></td><td><input type="text" class="form-control" id="description1" name="description1[]" value="" placeholder="Bank/Type" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value1" data-type="input-number" name="value1[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation1[]" id="designation1" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="'+coappName+'">'+coappName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF2 glyphicon glyphicon-remove">Remove</a></td></tr>');
			else
				$("#customFields2").append('<tr valign="top"><th scope="row"></th><td><input type="text" class="form-control" id="asset1" name="asset1[]" value="Bank Account" readonly="readonly" /></td><td><input type="text" class="form-control" id="description1" name="description1[]" value="" placeholder="Bank/Type" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value1" data-type="input-number" name="value1[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation1[]" id="designation1" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF2 glyphicon glyphicon-remove">Remove</a></td></tr>');
		}else{
			alert("Sorry!!! You can't add more than 6 Bank Accounts Assets.");
		}
	});
    $("#customFields2").on('click','.remCF2',function(){
        $(this).parent().parent().remove();
    });
});

/**
 * dynamically add row for RRSP
 * 
 */
$(document).ready(function(){
	var appName = document.forms["myForm"]["applName"].value;
	var coappName = document.forms["myForm"]["coAppName"].value;

	var count=0;
	$(".addCF3").click(function(){
		count=count+1;
		if(count < 6){
			if(coappName !="")
		$("#customFields3").append('<tr valign="top"><th scope="row"></th><td><input type="text" class="form-control" id="asset2" name="asset2[]" value="RRSPTSFA" readonly="readonly" /></td><td><input type="text" class="form-control" id="description2" name="description2[]" value="" placeholder="Bank/Held" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value2" data-type="input-number" name="value2[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation2[]" id="designation2" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="'+coappName+'">'+coappName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF3 glyphicon glyphicon-remove">Remove</a></td></tr>');
		
			else
				$("#customFields3").append('<tr valign="top"><th scope="row"></th><td><input type="text" class="form-control" id="asset2" name="asset2[]" value="RRSPTSFA" readonly="readonly" /></td><td><input type="text" class="form-control" id="description2" name="description2[]" value="" placeholder="Bank/Held" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value2" data-type="input-number" name="value2[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation2[]" id="designation2" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF3 glyphicon glyphicon-remove">Remove</a></td></tr>');
				}else{
			alert("Sorry!!! You can't add more than 6 RRSP / TSFA Assets.");
		}
	});
    $("#customFields3").on('click','.remCF3',function(){
        $(this).parent().parent().remove();
    });
});
/**
 * dynamically add row for Investment
 * 
 */
$(document).ready(function(){
	var appName = document.forms["myForm"]["applName"].value;
	var coappName = document.forms["myForm"]["coAppName"].value;

	var count=0;
	$(".addCF4").click(function(){
		count=count+1;
		if(count < 6){
			if(coappName !="")
		$("#customFields4").append('<tr valign="top"><th scope="row"></th><td><input type="text" class="form-control" id="asset3" name="asset3[]" value="Investments" readonly="readonly" /></td><td><input type="text" class="form-control" id="description3" name="description3[]" value="" placeholder="description" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value3" data-type="input-number" name="value3[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation3[]" id="designation3" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="'+coappName+'">'+coappName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF4 glyphicon glyphicon-remove">Remove</a></td></tr>');
			else
				$("#customFields4").append('<tr valign="top"><th scope="row"></th><td><input type="text" class="form-control" id="asset3" name="asset3[]" value="Investments" readonly="readonly" /></td><td><input type="text" class="form-control" id="description3" name="description3[]" value="" placeholder="description" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value3" data-type="input-number" name="value3[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation3[]" id="designation3" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF4 glyphicon glyphicon-remove">Remove</a></td></tr>');
		}else{
			alert("Sorry!!! You can't add more than 6 Investments Assets.");
		}
	});
    $("#customFields4").on('click','.remCF4',function(){
        $(this).parent().parent().remove();
    });
});

/**
 * dynamically add row for Other
 * 
 */

$(document).ready(function(){
	var appName = document.forms["myForm"]["applName"].value;
	var coappName = document.forms["myForm"]["coAppName"].value;

	var count=0;
	$(".addCF5").click(function(){
		count=count+1;
		if(count < 6){
			if(coappName !="")
		$("#customFields5").append('<tr valign="top"><th scope="row"></th><td><input type="text" class="form-control" id="asset4" name="asset4[]" value="Other" readonly="readonly" /></td><td><input type="text" class="form-control" id="description4" name="description4[]" value="" placeholder="description" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value4" data-type="input-number" name="value4[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation4[]" id="designation4" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="'+coappName+'">'+coappName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF5 glyphicon glyphicon-remove">Remove</a></td></tr>');
			else
				$("#customFields5").append('<tr valign="top"><th scope="row"></th><td><input type="text" class="form-control" id="asset4" name="asset4[]" value="Other" readonly="readonly" /></td><td><input type="text" class="form-control" id="description4" name="description4[]" value="" placeholder="description" /></td><td><input type="text" onkeypress="return isNumber(event)" id="value4" data-type="input-number" name="value4[]" placeholder="e.g. 50,000" class="form-control"/></td><td><select name="designation4[]" id="designation4" class="form-control"><option value="" selected>Please Select</option><option value="'+appName+'">'+appName+'</option><option value="Joint">Joint</option></select></td><td><a href="javascript:void(0);" class="remCF5 glyphicon glyphicon-remove">Remove</a></td></tr>');	
		}else{
			alert("Sorry!!! You can't add more than 6 Other Assets.");
		}
	});
    $("#customFields5").on('click','.remCF5',function(){
        $(this).parent().parent().remove();
    });
});


