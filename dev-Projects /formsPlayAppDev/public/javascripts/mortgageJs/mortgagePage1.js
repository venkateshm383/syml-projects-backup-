/**
 * MortgagePage 1 js file
 * 
 */


/**
 * 
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
	var additionalApplicants1 = document.forms["myForm"]["additionalApplicants1"].value;
    if(additionalApplicants1 !="" && additionalApplicants1 != null && additionalApplicants1=='yes')
	$scope.additionalApplicant = true;
   
	var lendingGoal1 = document.forms["myForm"]["lendingGoal"].value;
    if(lendingGoal1 !="" && lendingGoal1 != null)
	$scope.newItemType = lendingGoal1;
    
    var appRelStatus=document.forms["myForm"]["status"].value;
    if(appRelStatus !="" && appRelStatus != null)
        $scope.appRelStatuss=appRelStatus;

    var coappRelStatus=document.forms["myForm"]["costatus"].value;
    if(appRelStatus !="" && appRelStatus != null)
        $scope.coappRelStatuss=coappRelStatus;
    
    var additionalReason=document.forms["myForm"]["additionalReason"].value;
    if(additionalReason !="" && additionalReason != null)
        $scope.additionalReason=additionalReason;
    
    
    var otherbackTextArea=document.forms["myForm"]["otherbackTextArea"].value;
    if(otherbackTextArea !="" && otherbackTextArea != null)
        $scope.otherTextArea=additionalReason;
    
    
    $scope.showModal = false;
      $scope.toggleModal = function(){
    	
          $scope.showModal = !$scope.showModal;
         
      };
});
 




/**
 * for Mask Number input
 */

$(function() {
    $.mask.definitions['~'] = "[+-]";

    $("#phonedatata").mask("999-999-9999");
});

$(function() {
$.mask.definitions['~'] = "[+-]";

$("#phonedatata7").mask("999-999-9999");
});

/**
 * on change  background color of select drop down field
 */

function changeBackgroundOfSelectedField() {
	
	
	
	
		var reasonNotInclude = document.forms["myForm"]["reasonNotInclude"].value;
		console.log(" reasonNotInclude : " +reasonNotInclude);
		if (!reasonNotInclude) {
			document.getElementById('reason').style.backgroundColor = "#ffffff";
		} else {
			document.getElementById('reason').style.backgroundColor = "#33cc33";
		}
		
		console.log(" reasonNotInclude : " +reasonNotInclude);
	
}

/**
 * validate the form entire form
 * 
 */

function validateForm(){
	
	var firstName = document.forms["myForm"]["firstName"].value;
		if(!firstName){
		        document.getElementById('input_1101').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('fName').focus();
		        return false;
		}else{
    	document.getElementById("input_1101").innerHTML="";
    	}
		
		var lastName = document.forms["myForm"]["lastName"].value;
		if(!lastName){
		        document.getElementById('input_1102').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('lname').focus();
		        return false;
		}else{
    	document.getElementById("input_1102").innerHTML="";
    	}
		 
		var email = document.forms["myForm"]["email"].value;
		console.log("email"+email);
		if(!email){
		        document.getElementById('input_1103').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('email').focus();
		        return false;
		}else{
    	document.getElementById("input_1103").innerHTML="";
    	}
		
		var re=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
		//var re =/^[a-zA-Z0-9\.\-\_]+\@@{1}[a-zA-Z0-9]+\.\w{2,4}$/;
		console.log("before"+re.test(email));
		
		if(!re.test(email)){
	    	console.log("inside"+re.test(email));
	    	document.getElementById('input_1103').innerHTML="<span style='color:red'>*Provide valid email.</span>";
	    	document.getElementById('email').focus();
	    	
	    	return false;
	    }else{
	    	document.getElementById("input_1103").innerHTML="";
	    	
	    }
		
		var confirmEmail = document.forms["myForm"]["confirmEmail"].value;
		if(!confirmEmail){
		        document.getElementById('input_1104').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('cEmail').focus();
		        return false;
		}else{
    		document.getElementById("input_1104").innerHTML="";
    	}
		var re=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
		//var re =/^[a-zA-Z0-9\-\.\_]+\@@{1}[a-zA-Z0-9]+\.\w{2,4}$/;
	    if(!re.test(confirmEmail)){
	    	document.getElementById('input_1104').innerHTML="<span style='color:red'>*Provide valid email.</span>";
	    	document.getElementById('cEmail').focus();
	    	return false;
	    }else{
	    	document.getElementById("input_1104").innerHTML="";
	    }
		
		if(email != confirmEmail) {
			document.getElementById('input_1104').innerHTML="<span style='color:red'>*Email mismatch.</span>";
			document.getElementById('cEmail').focus();
	        return false;
        }else{
        	document.getElementById("input_1104").innerHTML="";
        }
		
		var applMobPhone = document.forms["myForm"]["applMobPhone"].value;
		
		if(!applMobPhone){
	        document.getElementById('input_11044').innerHTML="<span style='color:red'>*If you do not have a mobile number, please provide the best number to reach you at.</span>";
	        document.getElementById('phonedatata').focus();
	        return false;
		}else{
		document.getElementById("input_11044").innerHTML="";
		}
		
		var appRelStatus = document.forms["myForm"]["appRelStatus"].value;
		if(!appRelStatus){
	        document.getElementById('input_400665').innerHTML="<span style='color:red'>*This field is Required.</span>";
	        document.getElementById('relStatus').focus();
	        return false;
		}else{
		document.getElementById("input_400665").innerHTML="";
		}
		
		var additionalApplicants = document.forms["myForm"]["additionalApplicants"].checked;
		if(additionalApplicants == true){
			var adFirstName = document.forms["myForm"]["adFirstName"].value;	
			if(!adFirstName){
		    	    document.getElementById('input_1105').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('cfName').focus();
			        return false;
			}else{
			document.getElementById("input_1105").innerHTML="";
			}
		
			var adLastName = document.forms["myForm"]["adLastName"].value;
			if(!adLastName){
		    	    document.getElementById('input_1106').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('clName').focus();
		        	return false;
			}else{
			document.getElementById("input_1106").innerHTML="";
			}
		
			var adEmail = document.forms["myForm"]["adEmail"].value;
			if(!adEmail){
		    	    document.getElementById('input_1107').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('coEmail').focus();
		        	return false;
			}else{
			document.getElementById("input_1107").innerHTML="";
			}
			var re=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
			//var re =/^[a-zA-Z0-9\.\-\_]+\@@{1}[a-zA-Z0-9]+\.\w{2,4}$/;
		    if(!re.test(adEmail)){
		    	document.getElementById('input_1107').innerHTML="<span style='color:red'>*Provide valid email.</span>";
		    	document.getElementById('coEmail').focus();
		    	return false;
		    }else{
		    	document.getElementById("input_1107").innerHTML="";
		    }
		
			var adConfirmEmail = document.forms["myForm"]["adConfirmEmail"].value;
			if(!adConfirmEmail){
		    	    document.getElementById('input_1108').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('cocEmail').focus();
		        	return false;
			}else{
			document.getElementById("input_1108").innerHTML="";
			}
			var re=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
			//var re =/^[a-zA-Z0-9\-\.\_]+\@@{1}[a-zA-Z0-9]+\.\w{2,4}$/;
		    if(!re.test(adConfirmEmail)){
		    	document.getElementById('input_1108').innerHTML="<span style='color:red'>*Provide valid email.</span>";
		    	document.getElementById('cocEmail').focus();
		    	return false;
		    }else{
		    	document.getElementById("input_1108").innerHTML="";
		    }
		    if(adEmail != adConfirmEmail) {
				document.getElementById('input_1108').innerHTML="<span style='color:red'>*Email mismatch.</span>";
				document.getElementById('cocEmail').focus();
		        return false;
	        }else{
	        	document.getElementById("input_1108").innerHTML="";
	        }
		    
		    var coApplMobPhone = document.forms["myForm"]["coApplMobPhone"].value;
			
			if(!coApplMobPhone){
		        document.getElementById('input_11088').innerHTML="<span style='color:red'>*If you do not have a mobile number, please provide the best number to reach you at.</span>";
		        document.getElementById('phonedatata7').focus();
		        return false;
			}else{
			document.getElementById("input_11088").innerHTML="";
			}
			
			var coAppRelStatus = document.forms["myForm"]["coAppRelStatus"].value;
			
			if(!coAppRelStatus){
		        document.getElementById('input_41055').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('coRelStatus').focus();
		        return false;
			}else{
			document.getElementById("input_41055").innerHTML="";
			}
		    
		}
		
		 console.log(">>>"+additionalApplicants+"<<<");
		if(additionalApplicants == false && appRelStatus == 'Married' || appRelStatus == 'Common-Law'){
			var reasonNotInclude = document.forms["myForm"]["reasonNotInclude"].value;
			if(!reasonNotInclude){
				document.getElementById('input_4006312').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('reason').focus();
		        return false;
			}else{
			document.getElementById("input_4006312").innerHTML="";
			}
		} 
		var othertextArea = document.forms["myForm"]["otherTextArea"].value;
		
		console.log("othertextArea : "+othertextArea);
		
		var term = document.forms["myForm"]["term"].value;
		if(!term){
    	    document.getElementById('input_201').innerHTML="<span style='color:red'>*This field is Required.</span>";
    	    document.getElementById('input_201').focus();
        	return false;
		}else{
		document.getElementById("input_201").innerHTML="";
		}
		
		return true;	
	}