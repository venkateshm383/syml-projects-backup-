/**
 * MortgagePage5a js file
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
 * Form validation of page5a 
 */
function validateForm() {
	var mybills = document.forms["myForm"]["mybills"].value;
		if(!mybills){
		        document.getElementById('widget_uniidsettings_60').innerHTML="<span style='color:red'>*This field is Required.</span>";
		       	document.getElementById('widget_uniidsettings_60').focus();
		        return false;
		}else{
    	document.getElementById("widget_uniidsettings_60").innerHTML="";
    }
		
		var largerFamily = document.forms["myForm"]["largerFamily"].value;
		if(!largerFamily){
		        document.getElementById('widget_settings_61').innerHTML="<span style='color:red'>*This field is Required.</span>";
		       	document.getElementById('widget_settings_61').focus();
		        return false;
		}else{
    	document.getElementById("widget_settings_61").innerHTML="";
    }
		
		var buyNewVehicle = document.forms["myForm"]["buyNewVehicle"].value;
		if(!buyNewVehicle){
		        document.getElementById('widget_settings_62').innerHTML="<span style='color:red'>*This field is Required.</span>";
		       	document.getElementById('widget_settings_62').focus();
		        return false;
		}else{
    	document.getElementById("widget_settings_62").innerHTML="";
    }
		
		var recreatStoreHome = document.forms["myForm"]["recreatStoreHome"].value;
		if(!recreatStoreHome){
		        document.getElementById('widget_settings_63').innerHTML="<span style='color:red'>*This field is Required.</span>";
		       	document.getElementById('widget_settings_63').focus();
		        return false;
		}else{
    	document.getElementById("widget_settings_63").innerHTML="";
    }
		
		var riskTaker = document.forms["myForm"]["riskTaker"].value;
		if(!riskTaker){
		        document.getElementById('widget_settings_64').innerHTML="<span style='color:red'>*This field is Required.</span>";
		      	document.getElementById('widget_settings_64').focus();
		        return false;
		}else{
    	document.getElementById("widget_settings_64").innerHTML="";
    }
		
		return true;
}

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
	var incomedown11 = document.forms["myForm"]["incomedown1"].value;
    if(incomedown11 !="" && incomedown11 != null)
	$scope.newItemType6 = incomedown11;
    
	var largerfamily11 = document.forms["myForm"]["largerfamily1"].value;
    if(largerfamily11 !="" && largerfamily11 != null)
	$scope.newItemType7 = largerfamily11;
    
    var buyingnewvechile11 = document.forms["myForm"]["buyingnewvechile1"].value;
    if(buyingnewvechile11 !="" && buyingnewvechile11 != null)
	$scope.newItemType8 = buyingnewvechile11;
    
    var Planninglifestyle11 = document.forms["myForm"]["Planninglifestyle1"].value;
    if(Planninglifestyle11 !="" && Planninglifestyle11 != null)
	$scope.newItemType9 = Planninglifestyle11;
    
    var financialrisk11 = document.forms["myForm"]["financialrisk1"].value;
    if(financialrisk11 !="" && financialrisk11 != null)
	$scope.newItemType10 = financialrisk11;
});


