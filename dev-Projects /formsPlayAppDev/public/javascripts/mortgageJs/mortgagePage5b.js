/**
 * MortgagePage5b js file
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
	var thinkproperty11 = document.forms["myForm"]["thinkproperty1"].value;
    if(thinkproperty11 !="" && thinkproperty11 != null)
	$scope.newItemType11 = thinkproperty11;
    
	var imaginesamejob11 = document.forms["myForm"]["imaginesamejob1"].value;
    if(imaginesamejob11 !="" && imaginesamejob11 != null)
	$scope.newItemType12 = imaginesamejob11;
    
    var incomeraise11 = document.forms["myForm"]["incomeraise1"].value;
    if(incomeraise11 !="" && incomeraise11 != null)
	$scope.newItemType13 = incomeraise11;
    
    var rentalproperty11 = document.forms["myForm"]["rentalproperty1"].value;
    if(rentalproperty11 !="" && rentalproperty11 != null)
	$scope.newItemType14 = rentalproperty11;
    
});

/**
 * For validation of page5b
 */
function validateForm() {

	var likelyProperty = document.forms["myForm"]["likelyProperty"].value;
		if(!likelyProperty){
		        document.getElementById('widget_settings_65').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("widget_settings_65").innerHTML="";
    }
		
		var sameJob = document.forms["myForm"]["sameJob"].value;
		if(!sameJob){
		        document.getElementById('widget_settings_66').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("widget_settings_66").innerHTML="";
    }
		
		var incomeGoing = document.forms["myForm"]["incomeGoing"].value;
		if(!incomeGoing){
		        document.getElementById('widget_settings_67').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("widget_settings_67").innerHTML="";
    }
		
		var OneMoreProp = document.forms["myForm"]["OneMoreProp"].value;
		if(!OneMoreProp){
		        document.getElementById('widget_settings_68').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("widget_settings_68").innerHTML="";
    }
		
		return true;
}
