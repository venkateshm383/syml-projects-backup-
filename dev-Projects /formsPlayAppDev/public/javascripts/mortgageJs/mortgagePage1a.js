/**
 * 
 *  mortgagePage1a js file  
 */

/**
 * for onload function if user quit the page it show alert for are u sure
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
	 var additionalPrperty1234 = document.forms["myForm"]["additional"].value;
    if(additionalPrperty1234 !="" && additionalPrperty1234 != null)
	$scope.additionalApplicant = additionalPrperty1234;
    
	var currentLendingGoal1 = document.forms["myForm"]["lendingGoal"].value;
    if(currentLendingGoal1 !="" && currentLendingGoal1 != null)
	$scope.newItemType = currentLendingGoal1;
    
    var appRelStatu=document.forms["myForm"]["status"].value;
    if(appRelStatu !="" && appRelStatu != null)
        $scope.appRelStatuss=appRelStatu; 
    
});

/**
 * for Number Mask definitions 
 */
$(function() {
	
    $.mask.definitions['~'] = "[+-]";

    $("#phonedatata").mask("999-999-9999");
});



/**
 * validate the entire form 
 * 
 */

function validateForm(){
	
	var firstName = document.forms["myForm"]["firstName"].value;
		if(!firstName){
		        document.getElementById('input_1101').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('first_name').focus();
		        return false;
		}else{
    	document.getElementById("input_1101").innerHTML="";
    	}
		
		var lastName = document.forms["myForm"]["lastName"].value;
		if(!lastName){
		        document.getElementById('input_1102').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('last_name').focus();
		        return false;
		}else{
    	document.getElementById("input_1102").innerHTML="";
    	}
		
		var email = document.forms["myForm"]["email"].value;
		if(!email){
		        document.getElementById('input_1103').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('email_id').focus();
		        return false;
		}else{
    	document.getElementById("input_1103").innerHTML="";
    	}
		var re=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
		//var re =/^[a-zA-Z0-9\.\_]+\@@{1}[a-zA-Z0-9]+\.\w{2,4}$/;
	    console.log("before-->"+re.test(email));
		if(!re.test(email)){
	    	
	    	document.getElementById('input_1103').innerHTML="<span style='color:red'>*Provide valid email.</span>";
	    	document.getElementById('email_id').focus();
	    	return false;
	    }else{
	    	document.getElementById("input_1103").innerHTML="";
	    }
		
		var confirmEmail = document.forms["myForm"]["confirmEmail"].value;
		if(!confirmEmail){
		        document.getElementById('input_1104').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('co_email_id').focus();
		        return false;
		}else{
    	document.getElementById("input_1104").innerHTML="";
    	}
		
		//var re =/^[a-zA-Z0-9\.\_]+\@@{1}[a-zA-Z0-9]+\.\w{2,4}$/;
		var re=/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	    if(!re.test(confirmEmail)){
	    	document.getElementById('input_1104').innerHTML="<span style='color:red'>*Provide valid email.</span>";
	    	document.getElementById('co_email_id').focus();
	    	return false;
	    }else{
	    	document.getElementById("input_1104").innerHTML="";
	    }
		
	    if(email != confirmEmail) {
			document.getElementById('input_1104').innerHTML="<span style='color:red'>*Email mismatch.</span>";
			document.getElementById('co_email_id').focus();
	        return false;
        }else{
        	document.getElementById("input_1104").innerHTML="";
        }
	    
		/* var additionalApplicants = document.forms["myForm"]["additionalApplicants"].value;
		if(!additionalApplicants){
	    	    document.getElementById('input_1109').innerHTML="<span style='color:red'>*This field is Required.</span>";
	        	return false;
		}else{
		document.getElementById("input_1109").innerHTML="";
		} */
		var applMobPhone = document.forms["myForm"]["applMobPhone"].value;
		if(!applMobPhone){
	        document.getElementById('input_11044').innerHTML="<span style='color:red'>*This field is Required.</span>";
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
		
		
	    $(function() {
	        $.mask.definitions['~'] = "[+-]";

	        $("#phonedatata").mask("999-999-9999");
	    });
		
		if(additionalApplicants != true && additionalApplicants == '' && appRelStatus == 'Married' || appRelStatus == 'Common-Law'){
			var reasonNotInclude = document.forms["myForm"]["reasonNotInclude"].value;
			if(!reasonNotInclude){
				document.getElementById('input_4006312').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('reason').focus();
		        return false;
			}else{
			document.getElementById("input_4006312").innerHTML="";
			}
		}
		
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

/**
 * on change  background color of select drop down field
 */

function changeBackgroundOfSelectedField() {
	var appRelStatus = document.forms["myForm"]["appRelStatus"].value;
	if (!appRelStatus) {
		document.getElementById('relStatus').style.backgroundColor = "#ffffff";
	}else{
		document.getElementById('relStatus').style.backgroundColor = "#33cc33";
	}
	
	var additionalApplicants = document.forms["myForm"]["additionalApplicants"].checked;
	if(additionalApplicants == false && additionalApplicants == '' && appRelStatus == 'Married' || appRelStatus == 'Common-Law'){
		var reasonNotInclude = document.forms["myForm"]["reasonNotInclude"].value;
		if (!reasonNotInclude) {
			document.getElementById('reason').style.backgroundColor = "#ffffff";
		} else {
			document.getElementById('reason').style.backgroundColor = "#33cc33";
		}
	}
}
