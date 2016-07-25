/**
 * MortgagePage5b js file
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
 * Form Validation of page6
 */

function validateForm() {
	var applWorkPhone = document.forms["myForm"]["applWorkPhone"].value;
	if (!applWorkPhone) {
		document.getElementById('input_4002').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('phonedatata2').focus();
		return false;
	} else {
		document.getElementById("input_4002").innerHTML = "";
	}

	var applBirthday = document.forms["myForm"]["applBirthday"].value;
	if (!applBirthday) {
		document.getElementById('input_4004').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('datepicker').focus();
		return false;
	} else {
		document.getElementById("input_4004").innerHTML = "";
	}

	var applInsurNum = document.forms["myForm"]["applInsurNum"].value;
	if (!applInsurNum) {
		document.getElementById('input_4005').innerHTML = "<span style='color:red;'>*Social Insurance number is required to verify credit history. If you do not have a Social Insurance Number, please call Visdom at 1-855-699-2400.</span>";

		document.getElementById('phonedatata7').focus();
		return false;
	} else {
		document.getElementById("input_4005").innerHTML = "";
	}
	if(!(isNum(applInsurNum,"input_4005"))){
		console.log("coming ");
		//alert("coming")
		return false;
	}
	var applDependants = document.forms["myForm"]["applDependants"].value;

	if (!applDependants) {
		document.getElementById('input_4007').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('dependentId').focus();
		return false;
	} else {
		document.getElementById("input_4007").innerHTML = "";
	}

	/*
	 * var areYouCanadianRess =
	 * document.forms["myForm"]["areYouCanadianRess"].value; if
	 * (!areYouCanadianRess) { document.getElementById('input_4008').innerHTML = "<span
	 * style='color:red'>*This field is Required.</span>";
	 * document.getElementById('input_4008').focus(); return false; } else {
	 * document.getElementById("input_4008").innerHTML = ""; }
	 * 
	 * var movedCanadas = document.forms["myForm"]["movedCanadas"].value; if
	 * (!movedCanadas) { document.getElementById('input_4009').innerHTML = "<span
	 * style='color:red'>*This field is Required.</span>";
	 * document.getElementById('input_4009').focus(); return false; } else {
	 * document.getElementById("input_4009").innerHTML = ""; }
	 */

	var additionalAppli12 = document.forms["myForm"]["additionalApplicants"].value;

	if (additionalAppli12 == 'yes') {

		var coApplWorkPhone = document.forms["myForm"]["coApplWorkPhone"].value;
		if (!coApplWorkPhone) {
			document.getElementById('input_4101').innerHTML = "<span style='color:red'>*This field is Required.</span>";
			document.getElementById('phonedatata5').focus();
			return false;
		} else {
			document.getElementById("input_4101").innerHTML = "";
		}

		var coApplBirthday = document.forms["myForm"]["coApplBirthday"].value;
		if (!coApplBirthday) {
			document.getElementById('input_4103').innerHTML = "<span style='color:red'>*This field is Required.</span>";
			document.getElementById('datepicker1').focus();
			return false;
		} else {
			document.getElementById("input_4103").innerHTML = "";
		}

		var coApplInsurNum = document.forms["myForm"]["coApplInsurNum"].value;
		if (!coApplInsurNum) {
			document.getElementById('input_4104').innerHTML = "<span style='color:red'>*Social Insurance number is required to verify credit history. If you do not have a Social Insurance Number, please call Visdom at 1-855-699-2400.</span>";
			document.getElementById('phonedatata8').focus();
			return false;
		} else {
			document.getElementById("input_4104").innerHTML = "";
		}
		if(!(isNum(coApplInsurNum,"input_4104"))){
			console.log("coming ");
			//alert("coming")
			return false;
		}
		var coAppDependants = document.forms["myForm"]["coAppDependants"].value;
		if (!coAppDependants) {
			document.getElementById('input_4106').innerHTML = "<span style='color:red'>*This field is Required.</span>";
			document.getElementById('coDependentId').focus();
			return false;
		} else {
			document.getElementById("input_4106").innerHTML = "";
		}

		
		
		if(applInsurNum==coApplInsurNum){
			document.getElementById('input_4005').innerHTML = "<span style='color:red;'>Social Insurance number Should be Unique.</span>";

			return false;

		}
		var movedCanadas = document.forms["myForm"]["movedCanadas"].value;
		if (!movedCanadas) {
			document.getElementById('input_4009').innerHTML = "<span style='color:red'>*This field is Required.</span>";
			//document.getElementById('coDependentId').focus();
			return false;
		} else {
			document.getElementById("input_4009").innerHTML = "";
		}
		var coAppMovedCanadae = document.forms["myForm"]["coAppMovedCanadae"].value;
		if (!coAppMovedCanadae) {
			document.getElementById('input_4108').innerHTML = "<span style='color:red'>*This field is Required.</span>";
			//document.getElementById('coDependentId').focus();
			return false;
		} else {
			document.getElementById("input_4108").innerHTML = "";
		}

		
		
		
		/*
		 * var coApplicantss = document.forms["myForm"]["coApplicantss"].value;
		 * if (!coApplicantss) { document.getElementById('input_4107').innerHTML = "<span
		 * style='color:red'>*This field is Required.</span>";
		 * document.getElementById('input_4107').focus(); return false; } else {
		 * document.getElementById("input_4107").innerHTML = ""; }
		 * 
		 * var coAppMovedCanadae =
		 * document.forms["myForm"]["coAppMovedCanadae"].value; if
		 * (!coAppMovedCanadae) {
		 * document.getElementById('input_4108').innerHTML = "<span
		 * style='color:red'>*This field is Required.</span>";
		 * document.getElementById('input_4108').focus(); return false; } else {
		 * document.getElementById("input_4108").innerHTML = ""; }
		 */
	}
}
/**
 * SIN NUMBER VALIDATION
 */
function isNum(text,inputfield) {
	var esum = 0;
	var enumbers = "";
	var checknum = 0;
	var ch_sum = "";
	var checkdigit = 0;
	var sin = "";
	var lastdigit = 0;
    console.log("inside the isNum  --> text "+text);
    console.log("inside the isNum  --> inputfield"+inputfield);
    
	  if(text == "") {
		  document.getElementById(inputfield).innerHTML = "<span style='color:red'>*You left the SIN field blank.</span>";
	        //alert("You left the SIN field blank.");
             	return false;
	        }
	    	inStr = text;
              sin = text;
              inLen = inStr.length;

		if (inLen > 11 || inLen < 11) {
			document.getElementById(inputfield).innerHTML = "<span style='color:red'>*SIN must be 11 characters long.</span>";
      		//alert("SIN must be 11 characters long");
			return false;
			}

      	 for (var i = 0; i < text.length; i++) {
	 		var ch = text.substring(i, i + 1)

			if ((ch < "0" || "9" < ch) && (ch != "-"))  {
				document.getElementById(inputfield).innerHTML = "<span style='color:red'>*You must enter a 9 digits and two dashes.\nFormat 999-999-999..</span>";
	               		//alert("You must enter a 9 digits and two dashes.\nFormat 999-999-999.")
				return false;
		              	}
                      if ((i == 3 || i == 7) && (ch != "-")) {
                    	  document.getElementById(inputfield).innerHTML = "<span style='color:red'>*Invalid character in position 4 or 8;\nMust be a dash!.</span>";
                              //alert("Invalid character in position 4 or 8;\nMust be a dash!");
                              return false;
                              }
			}
                      lastdigit = text.substring(10, 10 + 1);
                      /** add numbers in odd positions; IE 1, 3, 6, 8 */		
			var odd = ((text.substring(0,0 + 1)) * (1.0)  + (text.substring(2,2 + 1)) * (1.0) 
			+(text.substring(5, 5+1)) * (1.0) + (text.substring(8,8 + 1)) * (1.0));
                      			
                      /** form texting of numbers in even positions IE 2, 4, 6, 8*/
                      var enumbers =  (text.substring(1,1 + 1)) + (text.substring(4,4 + 1))+
                      (text.substring(6,6 + 1)) + (text.substring(9,9 + 1));
                      /**
                       add together numbers in new text string
                       take numbers in even positions; IE 2, 4, 6, 8
                       and double them to form a new text string
                       EG if numbers are 2,5,1,9 new text string is 410218
                       */
                       
                      for (var i = 0; i < enumbers.length; i++) {
                              var ch = (enumbers.substring(i, i + 1) * 2);
                              ch_sum = ch_sum + ch;
                              }
                      
                      for (var i = 0; i < ch_sum.length; i++) {
                              var ch = (ch_sum.substring(i, i + 1));
                              esum = ((esum * 1.0) + (ch * 1.0));
                              }
			

			checknum = (odd + esum);
			
                      /** subtextact checknum from next highest multiple of 10
                       to give check digit which is last digit in valid SIN*/
			if (checknum <= 10) {
      			(checdigit = (10 - checknum));
				}
			if (checknum > 10 && checknum <= 20) {
				(checkdigit = (20 - checknum));
				}
                      if (checknum > 20 && checknum <= 30) {
				(checkdigit = (30 - checknum));
				}
                      if (checknum > 30 && checknum <= 40) {
				(checkdigit = (40 - checknum));
				}
                      if (checknum > 40 && checknum <= 50) {
				(checkdigit = (50 - checknum));
				}
                      if (checknum > 50 && checknum <= 60) {
				(checkdigit = (60 - checknum));
				}
						
                      if (checkdigit != lastdigit) {
                    	  document.getElementById(inputfield).innerHTML = "<span style='color:red'>"+sin+"*is an invalid SIN; \nPlease Enter Correct SIN .</span>";
                              //alert(sin + "  is an invalid SIN; \nCheck digit incorrect!\nShould be: " + checkdigit);
                              //history.go(0);
                              return false;
                              }					  			
	               	return true;
	}

/**
 * for angular code
 */

var app = angular.module("demoApp", [ 'ui.bootstrap' ]);

app
		.controller(
				'DemoController',
				function($scope) {
					init();
					function init() {
						
						$scope.newItemType = '';
						$scope.company = "";
						var additionalApplicants = document.forms["myForm"]["additionalApplicants"].value;
						$scope.data = additionalApplicants;
						console.log("data "+$scope.data);
						$scope.change = function() {
							console.log($scope.newItemType)
						};
						
					}

					$scope.singleModel = 1;
					var applicantNonResident333 = document.forms["myForm"]["applicantNonResident33"].value;
					if (applicantNonResident333 != null
							&& applicantNonResident333 != "")
						$scope.areYouCanadianRes = applicantNonResident333;

					var applicantMovedCanada333 = document.forms["myForm"]["applicantMovedCanada33"].value;
					if (applicantMovedCanada333 != null
							&& applicantMovedCanada333 != "")
						$scope.movedCanada = applicantMovedCanada333;

					var coApplicantNonResident333 = document.forms["myForm"]["coApplicantNonResident33"].value;
					if (coApplicantNonResident333 != null
							&& coApplicantNonResident333 != "")
						$scope.coApplicants = coApplicantNonResident333;

					var coApplicantMovedCanada333 = document.forms["myForm"]["coApplicantMovedCanada33"].value;
					if (coApplicantMovedCanada333 != null
							&& coApplicantMovedCanada333 != "")
						$scope.coAppMovedCanada = coApplicantMovedCanada333;

				});

/**
 * for load the select function
 */

window.onload = function() {
	SelectElement();
};
function SelectElement() {   
    
    var applicantDependant  = document.forms["myForm"]["applicantDependant11"].value;  
    document.forms["myForm"]["applDependants"].value = applicantDependant;
    
    var coApplicantDependant  = document.forms["myForm"]["coApplicantDependant11"].value;  
    document.forms["myForm"]["coAppDependants"].value = coApplicantDependant;
    
    /* var applicantNonResident  = document.forms["myForm"]["applicantNonResident11"].value;  
    document.forms["myForm"]["garageSize"].value = applicantNonResident;
    
    var applicantMovedCanada  = document.forms["myForm"]["applicantMovedCanada11"].value;  
    document.forms["myForm"]["garageSize"].value = applicantMovedCanada; */
}
/**
 * Mask Function for number
 */

$(function() {
    $.mask.definitions['~'] = "[+-]";

    $("#phonedatata2").mask("999-999-9999");
});

$(function() {
    $.mask.definitions['~'] = "[+-]";

    $("#phonedatata5").mask("999-999-9999");
});

$(function() {
    $.mask.definitions['~'] = "[+-]";

    $("#phonedatata7").mask("999-999-999");
});

$(function() {
    $.mask.definitions['~'] = "[+-]";

    $("#phonedatata8").mask("999-999-999");
});

/**
 * for data-toggle
 */
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});

/**
 * for datepicker 1
 */

$(function() {
    $( "#datepicker" ).datepicker({
    	yearRange: "-100:-18",
        maxDate: "-18Y", 
        minDate: "-100Y",
      changeMonth: true,
      changeYear: true,
      defaultDate: new Date().getDate() - 365*30+7	
      
    });
  });

/**
 * for datepicker 2
 */

$(function() {
    $( "#datepicker1" ).datepicker({
    	yearRange: "-100:-18",
        maxDate: "-18Y", 
        minDate: "-100Y",
      changeMonth: true,
      changeYear: true,
      defaultDate: new Date().getDate() - 365*30+7
    });
  });


/**
 * on change  background color of select drop down field
 */

function changeBackgroundOfSelectedField() {
	var appRelStatus = document.forms["myForm"]["applDependants"].value;
	if (!appRelStatus) {
		document.getElementById('dependentId').style.backgroundColor = "#ffffff";
	}else{
		document.getElementById('dependentId').style.backgroundColor = "#33cc33";
	}
	
	var appRelStatus = document.forms["myForm"]["coAppDependants"].value;
	if (!appRelStatus) {
		document.getElementById('coDependentId').style.backgroundColor = "#ffffff";
	}else{
		document.getElementById('coDependentId').style.backgroundColor = "#33cc33";
	}
}
