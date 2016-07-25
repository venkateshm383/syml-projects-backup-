/**
 * mortgagePage11Properties js file
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
   			
		}
		/* document.getElementById("applicantTotal2").value=total; */
    }
    
    $scope.calculateDate2 = function ($scope) {
    	$scope.employetotal=0;
    	var one= $scope.CoMovedIn1;

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
		$scope.coApplicantTotal = total;
		
		if(total <36){
			one= $scope.CoMovedIn2;
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
        		$scope.coApplicantTotal2 = total;
			
		}
		}
    }
    
	  $scope.singleModel = 1;
	  var howManyProperties1 = document.forms["myForm"]["howManyProperties123"].value;
      if(howManyProperties1 !="" && howManyProperties1 != null)
	  $scope.noOfProperty = howManyProperties1;
});


/**
 * for validate the form
 */





function validateForm(){
	
	var howManyProperties111 = document.forms["myForm"]["howManyProperties"].value;
	if(howManyProperties111 == "dummy" || !howManyProperties111){
	    document.getElementById('role1').innerHTML="<span style='color:red'>*This field is Required.</span>";
    	return false;
	}else{
	document.getElementById("role1").innerHTML="";
	}
	
	if(howManyProperties111 == "one"){
				
		var address111 = document.forms["myForm"]["address0"].value;
		if(!address111){
		        document.getElementById('input_200211').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_200211").innerHTML="";
    	}

		var appxValue111 = document.forms["myForm"]["appxValue1"].value;
		if(!appxValue111){
		        document.getElementById('input_200212').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_200212").innerHTML="";
    	}
		
		var ownership111 = document.forms["myForm"]["ownership1"].value;
		if(!ownership111){
		        document.getElementById('input_200213').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_200213").innerHTML="";
    	}
		
	}
	
	if(howManyProperties111 == "two"){
		
		var currentAddress211 = document.forms["myForm"]["currentAddress21"].value;
		if(!currentAddress211){
		        document.getElementById('input_2001').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2001").innerHTML="";
    	}

		var appxValue211 = document.forms["myForm"]["appxValue21"].value;
		if(!appxValue211){
		        document.getElementById('input_200111').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_200111").innerHTML="";
    	}
		
		var ownership211 = document.forms["myForm"]["ownership21"].value;
		if(!ownership211){
		        document.getElementById('input_701').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_701").innerHTML="";
    	}
		
		var currentAddress221 = document.forms["myForm"]["currentAddress22"].value;
		if(!currentAddress221){
		        document.getElementById('input_2002').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2002").innerHTML="";
    	}

		var appxValue221 = document.forms["myForm"]["appxValue22"].value;
		if(!appxValue221){
		        document.getElementById('input_20022').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_20022").innerHTML="";
    	}
		
		var ownership221 = document.forms["myForm"]["ownership22"].value;
		if(!ownership221){
		        document.getElementById('input_20012').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_20012").innerHTML="";
    	}
		
	}
	
	if(howManyProperties111 == "three"){
		var currentAddress311 = document.forms["myForm"]["currentAddress31"].value;
		if(!currentAddress311){
		        document.getElementById('input_20011').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_20011").innerHTML="";
    	}
		 
		var appxValue311 = document.forms["myForm"]["appxValue31"].value;
		if(!appxValue311){
		        document.getElementById('input_200131').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_200131").innerHTML="";
    	}
		 
		var ownership311 = document.forms["myForm"]["ownership31"].value;
		if(!ownership311){
		        document.getElementById('input_200132').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_200132").innerHTML="";
    	}
		
		var currentAddress321 = document.forms["myForm"]["currentAddress32"].value;
		if(!currentAddress321){
		        document.getElementById('input_203211').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_203211").innerHTML="";
    	}
		 
		var appxValue321 = document.forms["myForm"]["appxValue32"].value;
		if(!appxValue321){
		        document.getElementById('input_203212').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_203212").innerHTML="";
    	}
		 			
		var ownership321 = document.forms["myForm"]["ownership32"].value;
		if(!ownership321){
		        document.getElementById('input_203214').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_203214").innerHTML="";
    	}
		
		var currentAddress331 = document.forms["myForm"]["currentAddress33"].value;
		if(!currentAddress331){
		        document.getElementById('input_2043').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2043").innerHTML="";
    	}
		 
		var appxValue331 = document.forms["myForm"]["appxValue33"].value;
		if(!appxValue331){
		        document.getElementById('input_2044').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2044").innerHTML="";
    	}
		 			
		var ownership331 = document.forms["myForm"]["ownership33"].value;
		if(!ownership331){
		        document.getElementById('input_2045').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2045").innerHTML="";
    	}
		
	}
	
	if(howManyProperties111 == "four"){
		
		var currentAddress412 = document.forms["myForm"]["currentAddress41"].value;
		if(!currentAddress412){
		        document.getElementById('input_2032e11ww1s1').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2032e11ww1s1").innerHTML="";
    	}

		var appxValue412 = document.forms["myForm"]["appxValue41"].value;
		if(!appxValue412){
		        document.getElementById('input_2032').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2032").innerHTML="";
    	}
						
		var ownership411 = document.forms["myForm"]["ownership41"].value;
		
		if(!ownership411){
		        document.getElementById('input_2033').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2033").innerHTML="";
    	}
		
		var currentAddress422 = document.forms["myForm"]["currentAddress42"].value;
		if(!currentAddress422){
		        document.getElementById('input_2212').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2212").innerHTML="";
    	}

		var appxValue422 = document.forms["myForm"]["appxValue42"].value;
		if(!appxValue422){
		        document.getElementById('input_20322').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_20322").innerHTML="";
    	}
						
		var ownership4221 = document.forms["myForm"]["ownership42"].value;
		if(!ownership4221){
		        document.getElementById('input_20323').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_20323").innerHTML="";
    	}
		
		var currentAddress431 = document.forms["myForm"]["currentAddress43"].value;
		if(!currentAddress431){
		        document.getElementById('input_20121').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_20121").innerHTML="";
    	}

		var appxValue432 = document.forms["myForm"]["appxValue43"].value;
		if(!appxValue432){
		        document.getElementById('input_201212').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_201212").innerHTML="";
    	}
						
		var ownership431 = document.forms["myForm"]["ownership43"].value;
		if(!ownership431){
		        document.getElementById('input_201213').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_201213").innerHTML="";
    	}
		
		var currentAddress441 = document.forms["myForm"]["currentAddress44"].value;
		if(!currentAddress441){
		        document.getElementById('input_2032121').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2032121").innerHTML="";
    	}

		var appxValue441 = document.forms["myForm"]["appxValue44"].value;
		if(!appxValue441){
		        document.getElementById('input_20212').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_20212").innerHTML="";
    	}
						
		var ownership441 = document.forms["myForm"]["ownership44"].value;
		if(!ownership441){
		        document.getElementById('input_20213').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_20213").innerHTML="";
    	}
		
	}
	
	return true;	
}

/**
 * select function
 */


window.onload = function() {
	SelectElement();
};
function SelectElement() {   
    var ownership123 = document.forms["myForm"]["ownership12"].value;  
    document.forms["myForm"]["ownership1"].value=ownership123;
}

/**
 * All GeoComplete id code here to use in input field
 */


$(function(){
	  $("#geocomplete0").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete0").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });   
});



$(function(){
	$("#geocomplete71").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete71").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });   
  });


$(function(){
	  $("#geocomplete72").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete72").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });
});


$(function(){
	$("#geocomplete2").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete2").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });
  });


$(function(){
	  $("#geocomplete3").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete3").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    }); 
});


$(function(){
	$("#geocomplete4").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete4").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
  });


$(function(){
	  $("#geocomplete41").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete41").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
});


$(function(){
	$("#geocomplete42").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete42").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });    
  });


$(function(){
	  $("#geocomplete43").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete43").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });
});

$(function(){
	$("#geocomplete44").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete44").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
  });

$(function(){
	  $("#geocomplete51").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete51").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    }); 
});

$(function(){
    $("#geocomplete52").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete52").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
    });

$(function(){
	  $("#geocomplete53").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete53").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
});

$(function(){
    $("#geocomplete54").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete54").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
    });


$(function(){
	  $("#geocomplete55").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete55").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
});


$(function(){
    $("#geocomplete56").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete56").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
    });


$(function(){
	  $("#geocomplete57").geocomplete({
	        types:["geocode", "establishment"]
	    })
	    .bind("geocode:result", function (event, result) {
	        //$.log("Result: " + result.formatted_address);
	        //alert("Result: " + result.formatted_address);
	    	document.getElementById("geocomplete57").value = result.formatted_address;
	    })
	    .bind("geocode:error", function (event, status) {
	        alert("ERROR: " + status);
	    })
	    .bind("geocode:multiple", function (event, results) {
	       alert("Multiple: " + results.length + " results found");
	    });  
});

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    	 alert("Please enter a number (no comma or dash)");
        return false;
    }
    return true;
}
/**
 * data toggle here
 */

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});
