/**
 * mortgagePage7a js file
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
 * Form validation for page7a
 */


function validateForm(){
	
	var currentAddress1 = document.forms["myForm"]["currentAddress1"].value;
	
	var regeUpperCase="[ABCEGHJKLMNPRSTVXY][0-9][ABCEGHJKLMNPRSTVWXYZ] ?[0-9][ABCEGHJKLMNPRSTVWXYZ][0-9]";
	var regexLowerCase="[abceghjklmnprstvxy][0-9][abceghjklmnprstvwxyz] ?[0-9][abceghjklmnprstvwxyz][0-9]";

	
		if(!currentAddress1){
		        document.getElementById('input_2001').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('geocomplete').focus();
		        return false;
		}else if(!(currentAddress1.match(regeUpperCase) || currentAddress1.match(regexLowerCase)) ){
			document.getElementById('input_2001').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
			document.getElementById('geocomplete').focus();	
			return false;
		} 
		
		else{
    	document.getElementById("input_2001").innerHTML="";
    	}
		
		var movedIn1 = document.forms["myForm"]["movedIn1"].value;
		if(!movedIn1){
		        document.getElementById('input_2002').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        document.getElementById('datepicker').focus();
		        return false;
		}else{
    	document.getElementById("input_2002").innerHTML="";
    	}
		
		var canadacheckinform = document.forms["myForm"]["canadacheckinform"].value;
		
		if(canadacheckinform=='no'){
			
			console.log("canadacheckinform "+canadacheckinform);
			
			
			
			
			
			
			
			
			
			
			/**
			 * changes from here  for new address
			 */
			
			
			var currentAddress2 = document.forms["myForm"]["currentAddress2"].value;
			if(!currentAddress2){
			        document.getElementById('input_2003').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        document.getElementById('geocomplete1').focus();
			        return false;
			}else if(!(currentAddress2.match(regeUpperCase) || currentAddress2.match(regexLowerCase)) ){
				document.getElementById('input_2003').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
				document.getElementById('geocomplete1').focus();	
				return false;
			} 
			
			else{
	    	document.getElementById("input_2003").innerHTML="";
	    	}
			
			var movedIn2 = document.forms["myForm"]["movedIn2"].value;
			
			if(!movedIn2){
				
			        document.getElementById('input_2004').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        document.getElementById('datepicker1').focus();
			        return false;
			}else{
	    	document.getElementById("input_2004").innerHTML="";
	    	}
			
			var currentAddress3 = document.forms["myForm"]["currentAddress3"].value;	
			if(!currentAddress3){
		    	    document.getElementById('input_2005').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('geocomplete2').focus();
			        return false;
			}else if(!(currentAddress3.match(regeUpperCase) || currentAddress3.match(regexLowerCase)) ){
				document.getElementById('input_2005').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
				document.getElementById('geocomplete2').focus();	
				return false;
			} 
			
			else{
			document.getElementById("input_2005").innerHTML="";
			}
		
			var movedIn3 = document.forms["myForm"]["movedIn3"].value;
			if(!movedIn3){
		    	    document.getElementById('input_2006').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('datepicker2').focus();
		        	return false;
			}else{
			document.getElementById("input_2006").innerHTML="";
			}
			
			/** changes for new 3 fields for address */
			
			var currentAddress4 = document.forms["myForm"]["currentAddress4"].value;	
			if(!currentAddress4){
		    	    document.getElementById('input_2007').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('geocomplete3').focus();
			        return false;
			}else if(!(currentAddress4.match(regeUpperCase) || currentAddress4.match(regexLowerCase)) ){
				document.getElementById('input_2007').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
				document.getElementById('geocomplete3').focus();	
				return false;
			} 
			
			else{
			document.getElementById("input_2007").innerHTML="";
			}
			
			var movedIn4 = document.forms["myForm"]["movedIn4"].value;
			if(!movedIn4){
		    	    document.getElementById('input_2008').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('datepicker3').focus();
		        	return false;
			}else{
			document.getElementById("input_2008").innerHTML="";
			}
			
			var currentAddress5 = document.forms["myForm"]["currentAddress5"].value;	
			if(!currentAddress5){
		    	    document.getElementById('input_2009').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('geocomplete4').focus();
			        return false;
			}else if(!(currentAddress5.match(regeUpperCase) || currentAddress5.match(regexLowerCase)) ){
				document.getElementById('input_2009').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
				document.getElementById('geocomplete4').focus();	
				return false;
			} 
			else{
			document.getElementById("input_2009").innerHTML="";
			}
			
			var movedIn5 = document.forms["myForm"]["movedIn5"].value;
			if(!movedIn5){
		    	    document.getElementById('input_2010').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('datepicker4').focus();
		        	return false;
			}else{
			document.getElementById("input_2010").innerHTML="";
			}
			
			
			var currentAddress6 = document.forms["myForm"]["currentAddress6"].value;	
			if(!currentAddress6){
		    	    document.getElementById('input_2011').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('geocomplete5').focus();
			        return false;
			}else if(!(currentAddress6.match(regeUpperCase) || currentAddress6.match(regexLowerCase)) ){
				document.getElementById('input_2011').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
				document.getElementById('geocomplete5').focus();	
				return false;
			} 
			else{
			document.getElementById("input_2011").innerHTML="";
			}
			
			var movedIn6 = document.forms["myForm"]["movedIn6"].value;
			if(!movedIn6){
		    	    document.getElementById('input_2012').innerHTML="<span style='color:red'>*This field is Required.</span>";
		    	    document.getElementById('datepicker5').focus();
		        	return false;
			}else{
			document.getElementById("input_2012").innerHTML="";
			}
			
		}
		
		
		
		
	
		return true;	
	}

/**
 * for angular code here
 */
var app = angular.module("demoApp", ['ui.bootstrap']);

app.controller('DemoController', function ($scope) {
    init();
    function init() {
        $scope.newItemType = '';
        $scope.company="";
        var totalMonth1322 = document.forms["myForm"]["totalMonth131"].value;
        if(totalMonth1322 !="")
        $scope.applicantTotal1=totalMonth1322;
        
        var totalMonth13222 = document.forms["myForm"]["totalMonth132"].value;
        if(totalMonth13222 !="")
        $scope.applicantTotal2=totalMonth13222;
        
        var totalMonth1333 = document.forms["myForm"]["totalMonth133"].value;
        if(totalMonth1333 !="")
        $scope.applicantTotal3=totalMonth1333;
        
        
        var totalMonth13444 = document.forms["myForm"]["totalMonth134"].value;
        if(totalMonth13444 !="")
        $scope.applicantTotal4=totalMonth13444;
        
        
        
        var totalMonth13555 = document.forms["myForm"]["totalMonth135"].value;
        if(totalMonth13555 !="")
        $scope.applicantTotal5=totalMonth13555;
        
        
        
        var totalMonth13666 = document.forms["myForm"]["totalMonth136"].value;
        if(totalMonth13666 !="")
        $scope.applicantTotal6=totalMonth13666;
        
        
        
        var newCanadaCheck = document.forms["myForm"]["newCanadaCheck"].value;
        if(newCanadaCheck !="")
        $scope.newCanadaForCheck=newCanadaCheck;
        
        
        /** CHANGE HERE FOR NEW FIELD */
        
        
        
        
        $( "#datepicker" ).datepicker({
            
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.movedIn1 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
        $( "#datepicker1" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.movedIn2 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
        $( "#datepicker2" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.movedIn3 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
        $( "#datepicker3" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.movedIn4 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
        
        $( "#datepicker4" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.movedIn5 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
        
        $( "#datepicker5" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.movedIn6 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
        
        $scope.change = function () {
            console.log($scope.newItemType)
        };
    }
    
    $scope.calculateDate = function ($scope) {
    	$scope.employetotal=0;
    	var one= $scope.movedIn1;
    	var date =one.split("/");
        var oneDate=date[0]+"/1/"+date[2];
        var total=0;
        
        var today = new Date();
		var datevDate=new Date(oneDate);
		
		var one_day=1000*60*60*24;
		var date1ms=today.getTime();
		var date2ms=datevDate.getTime();
		
		var totmili=date1ms-date2ms;
		var totalDays=Math.round(totmili/one_day);
		var totalMonths=Math.abs((totmili/one_day)/30);
		
		total=Math.floor(totalMonths); 
		
		$scope.applicantTotal1 = total;
         		
		
		if(total <36){
			one= $scope.movedIn2;
			date =one.split("/");
            oneDate=date[0]+"/1/"+date[2];
    	   	datevDate=new Date(oneDate);
    	   	
   		 	one_day=1000*60*60*24;
   		 	date1ms=date2ms;
   		
   			date2ms=datevDate.getTime();
   			totmili=date1ms-date2ms;
   			if(isNaN(totmili) == false){
   				 
          		totalDays=Math.round(totmili/one_day);
          		totalMonths=Math.abs((totmili/one_day)/30);
        		total=total+Math.floor(totalMonths); 
        		$scope.applicantTotal2 = total;
			
		}
   			one= $scope.movedIn3;
			date =one.split("/");
            oneDate=date[0]+"/1/"+date[2];
    	   	datevDate=new Date(oneDate);
    	   	
   		 	one_day=1000*60*60*24;
   		 	date1ms=date2ms;
   		
   			date2ms=datevDate.getTime();
   			totmili=date1ms-date2ms;
   			if(isNaN(totmili) == false){
   				 
          		totalDays=Math.round(totmili/one_day);
          		totalMonths=Math.abs((totmili/one_day)/30);
        		total=total+Math.floor(totalMonths);
        		$scope.applicantTotal3 = total;
        		
			
			}
   			one= $scope.movedIn4;
   			date =one.split("/");
            oneDate=date[0]+"/1/"+date[2];
    	   	datevDate=new Date(oneDate);
    	   	
   		 	one_day=1000*60*60*24;
   		 	date1ms=date2ms;
   		
   			date2ms=datevDate.getTime();
   			totmili=date1ms-date2ms;
   			if(isNaN(totmili) == false){
   				 
          		totalDays=Math.round(totmili/one_day);
          		totalMonths=Math.abs((totmili/one_day)/30);
        		total=total+Math.floor(totalMonths);
        		$scope.applicantTotal4 = total;
        		
			
			}
   			one= $scope.movedIn5;
   			date =one.split("/");
            oneDate=date[0]+"/1/"+date[2];
    	   	datevDate=new Date(oneDate);
    	   	
   		 	one_day=1000*60*60*24;
   		 	date1ms=date2ms;
   		
   			date2ms=datevDate.getTime();
   			totmili=date1ms-date2ms;
   			if(isNaN(totmili) == false){
   				 
          		totalDays=Math.round(totmili/one_day);
          		totalMonths=Math.abs((totmili/one_day)/30);
        		total=total+Math.floor(totalMonths);
        		$scope.applicantTotal5 = total;
        		
			
			}
   			one= $scope.movedIn6;
   			date =one.split("/");
            oneDate=date[0]+"/1/"+date[2];
    	   	datevDate=new Date(oneDate);
    	   	
   		 	one_day=1000*60*60*24;
   		 	date1ms=date2ms;
   		
   			date2ms=datevDate.getTime();
   			totmili=date1ms-date2ms;
   			if(isNaN(totmili) == false){
   				 
          		totalDays=Math.round(totmili/one_day);
          		totalMonths=Math.abs((totmili/one_day)/30);
        		total=total+Math.floor(totalMonths);
        		$scope.applicantTotal6 = total;
        		console.log("$scope.applicantTotal6  "+$scope.applicantTotal6);
			
			}
		}
    }
    
});


/**
 * for select function for select element
 */


window.onload = function() {
	SelectElement();
};
function SelectElement() {   
    var movedIn12  = document.forms["myForm"]["movedIn12"].value;  
    document.forms["myForm"]["movedIn1"].value = movedIn12;
    
    var movedIn13  = document.forms["myForm"]["movedIn13"].value;  
    document.forms["myForm"]["movedIn2"].value = movedIn13;
    
    var movedIn14  = document.forms["myForm"]["movedIn14"].value;  
    document.forms["myForm"]["movedIn3"].value = movedIn14;
    
    var movedIn15  = document.forms["myForm"]["movedIn15"].value;  
    document.forms["myForm"]["movedIn4"].value = movedIn15;
    
    var movedIn16  = document.forms["myForm"]["movedIn16"].value;  
    document.forms["myForm"]["movedIn5"].value = movedIn16;
    
    var movedIn17  = document.forms["myForm"]["movedIn17"].value;  
    document.forms["myForm"]["movedIn6"].value = movedIn17;
    
    
    
    
}

/**
 * geocomplete for address js file using 
 */






$(function(){
    $("#geocomplete").geocomplete({
      map: ".map_canvas",
      details: "form",
      types: ["geocode", "establishment"],
    });

    $("#find").click(function(){
    	console.log("-------------------------load-----------------------");
    	console.log($("#geocomplete").trigger("geocode"));
    	console.log("--------------------------end--------------------------");
      $("#geocomplete").trigger("geocode");
    });
  });

$(function(){
    $("#geocomplete1").geocomplete({
      map: ".map_canvas",
      details: "form",
      types: ["geocode", "establishment"],
    });

    $("#find").click(function(){
      $("#geocomplete1").trigger("geocode");
    });
  });

$(function(){
    $("#geocomplete2").geocomplete({
      map: ".map_canvas",
      details: "form",
      types: ["geocode", "establishment"],
    });

    $("#find").click(function(){
      $("#geocomplete2").trigger("geocode");
    });
  });


$(function(){
    $("#geocomplete3").geocomplete({
      map: ".map_canvas",
      details: "form",
      types: ["geocode", "establishment"],
    });

    $("#find").click(function(){
      $("#geocomplete3").trigger("geocode");
    });
  });

$(function(){
    $("#geocomplete4").geocomplete({
      map: ".map_canvas",
      details: "form",
      types: ["geocode", "establishment"],
    });

    $("#find").click(function(){
      $("#geocomplete4").trigger("geocode");
    });
  });

$(function(){
    $("#geocomplete5").geocomplete({
      map: ".map_canvas",
      details: "form",
      types: ["geocode", "establishment"],
    });

    $("#find").click(function(){
      $("#geocomplete4").trigger("geocode");
    });
  });



/**
 * data-toggle
 */


$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});