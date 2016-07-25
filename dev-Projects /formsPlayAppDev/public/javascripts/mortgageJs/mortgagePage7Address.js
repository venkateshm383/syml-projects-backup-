/**
 * mortgagePage7Address js file
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
 * for validate the form
 */
function copyAddress(){
	 var agree = document.forms["myForm"]["agree"].value;
	 console.log("agree : "+agree);
}


/**
 * for angular code here
 */
var app = angular.module("demoApp", ['ui.bootstrap']);


app.controller('DemoController', function ($scope) {
    init();
   
    $scope.movedIn11="1/1/1990";
   
   
   
    function init() {
    	console.log("inside init ()");
        $scope.newItemType = '';
        $scope.data={};
        $scope.company="";
        $scope.date = '03/19/2013';
        var additionalApplicants = document.forms["myForm"]["additionalApplicants"].value;
        var additionalApplicanttt = document.forms["myForm"]["additionalApplicanttt"].value;
        console.log("additionalApplicanttt  value" + additionalApplicanttt);
        console.log("additionalApplicants "+additionalApplicants);
      
        $scope.data=additionalApplicants;
        console.log("scope data value "+ $scope.data);

        var totalMonth1322 = document.forms["myForm"]["totalMonth131"].value;
        if(totalMonth1322 !="")
        $scope.applicantTotal1=totalMonth1322;
       
        var totalMonth13222 = document.forms["myForm"]["totalMonth132"].value;
        if(totalMonth13222 !="")
        $scope.applicantTotal2=totalMonth13222;
       
        var totalMonth1333 = document.forms["myForm"]["totalMonth133"].value;
        if(totalMonth1333 !="")
        $scope.applicantTotal3=totalMonth1333;
        
        
        var totalMonth13433 = document.forms["myForm"]["totalMonth134"].value;
        if(totalMonth13433 !="")
        $scope.applicantTotal4=totalMonth13433;
        
        var totalMonth13533 = document.forms["myForm"]["totalMonth135"].value;
        if(totalMonth13533 !="")
        $scope.applicantTotal5=totalMonth13533;
        
        var totalMonth136323 = document.forms["myForm"]["totalMonth136"].value;
        if(totalMonth136323 !="")
        $scope.applicantTotal6=totalMonth136323;
      
        
        
        
        
        
        var coTotalMonth2311 = document.forms["myForm"]["coTotalMonth231"].value;
        if(coTotalMonth2311 !="")
        $scope.coApplicantTotal=coTotalMonth2311;
       
        var coTotalMonth2322 = document.forms["myForm"]["coTotalMonth232"].value;
        if(coTotalMonth2322 !="")
        $scope.coApplicantTotal2=coTotalMonth2322;
       
        var coTotalMonth2333 = document.forms["myForm"]["coTotalMonth233"].value;
        if(coTotalMonth2333 !="")
        $scope.coApplicantTotal3=coTotalMonth2333;
        
        var coTotalMonth2311121 = document.forms["myForm"]["coTotalMonth2311"].value;
        if(coTotalMonth2311121 !="")
        $scope.coApplicantTotal4=coTotalMonth2311121;
        
        var coTotalMonth2322232 = document.forms["myForm"]["coTotalMonth2322"].value;
        if(coTotalMonth2322232 !="")
        $scope.coApplicantTotal5=coTotalMonth2322232;
        
        var coTotalMonth233323 = document.forms["myForm"]["coTotalMonth2333"].value;
        if(coTotalMonth233323 !="")
        $scope.coApplicantTotal6=coTotalMonth233323;
        
        
        
        
        
        
        var newCanadaCheck = document.forms["myForm"]["newCanadaCheck"].value;
        if(newCanadaCheck !="")
        $scope.newCanadaForCheck=newCanadaCheck;
        
        console.log("newCanandaForCheck "+$scope.newCanadaForCheck);
        
        var conewCanadaCheck = document.forms["myForm"]["conewCanadaCheck"].value;
        if(conewCanadaCheck !="")
        $scope.conewCanadaForCheck=conewCanadaCheck;
        console.log("conewCanadaForCheck "+$scope.conewCanadaForCheck);
        
        
      
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
        
        
        $( "#datepicker21" ).datepicker({
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
        
        
        $( "#datepicker22" ).datepicker({
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
        
        
        $( "#datepicker23" ).datepicker({
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
                    
                     }
              
        }
        /* document.getElementById("applicantTotal2").value=total; */
    }
   
    $scope.calculateDate2 = function ($scope) {
    	
    	$( "#datepicker3" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.CoMovedIn1 = date;
           
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
                $scope.CoMovedIn2 = date;
           
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
                $scope.CoMovedIn3 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
    	
    	
    	
    	$( "#datepicker51" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.CoMovedIn4 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
    	
    	
    	$( "#datepicker52" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.CoMovedIn5 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
    	
    	
    	$( "#datepicker53" ).datepicker({
            changeMonth: true,
            changeYear: true,
            onSelect: function (date) {
                $scope.CoMovedIn6 = date;
           
                 $scope.$apply(function($scope){
                     // Change binded variable
                     $scope.assign($scope, date);
                 });
                 
            }
           
          });
    	
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
               one= $scope.CoMovedIn3;
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
                $scope.coApplicantTotal3 = total;
            }
               
               
               one= $scope.CoMovedIn4;
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
                   $scope.coApplicantTotal4 = total;
               }
                  
                  one= $scope.CoMovedIn5;
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
                      $scope.coApplicantTotal5 = total;
                  }
               
                     one= $scope.CoMovedIn6;
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
                         $scope.coApplicantTotal6 = total;
                     }
               
              
        }
    }
   
    /* $scope.copyValue = function () {
        $scope.coCurrentAddress1 = $scope.currentAddress1;
        $scope.coAppAddress2 = $scope.applAddress2;
        $scope.coAppAddress3 = $scope.appAddress3;
        
    } */
    
    
    $scope.CheckClicked = function(){
    	
  console.log("inside the checkClicked() : ");
    	//debugger;
    	  if($scope.SameAddress){
    		  console.log("$scope.SameAddress : "+$scope.SameAddress);
    		  var currentAddress1 = document.forms["myForm"]["currentAddress1"].value;
    	        if(currentAddress1 !="")
    	        	$scope.currentAddress1=currentAddress1;
    	        var currentAddress2 = document.forms["myForm"]["currentAddress2"].value;  
    	        if(currentAddress2 !="")
    	        	$scope.applAddress2=currentAddress2;
    	        
    	        var currentAddress3 = document.forms["myForm"]["currentAddress3"].value;  
    	        
    	        if(currentAddress3 !="")
    	        	$scope.appAddress3=currentAddress3;
    	        var currentAddress4 = document.forms["myForm"]["currentAddress4"].value;  
    	        
    	        if(currentAddress4 !="")
    	        	$scope.appAddress4=currentAddress4;
    	        var currentAddress5 = document.forms["myForm"]["currentAddress5"].value;  
    	        if(currentAddress5 !="")
    	        	$scope.appAddress5=currentAddress5;
    	        var currentAddress6 = document.forms["myForm"]["currentAddress6"].value;
    	        if(currentAddress6 !="")
    	        	$scope.appAddress6=currentAddress6;
    	        
    		 /**for co-application address symbol : */
    	       /* var CoCurrentAddress1 = document.forms["myForm"]["CoCurrentAddress1"].value;
    	        if(CoCurrentAddress1 !="")
    	        	$scope.coCurrentAddress1="";
    	        var CoMovedIn1 = document.forms["myForm"]["CoMovedIn1"].value;
    	        if(CoMovedIn1 !="")
    	        	$scope.CoMovedIn1="";*/
    	        
    	        
    	      
    	   
    	    $scope.coCurrentAddress1= $scope.currentAddress1;
    	    
    	    $scope.coAppAddress2= $scope.applAddress2;
    	    $scope.coAppAddress3= $scope.appAddress3;
    	    $scope.coAppAddress4= $scope.appAddress4;
    	    $scope.coAppAddress5= $scope.appAddress5;
    	    $scope.coAppAddress6= $scope.appAddress6;
    	    
    	    
    	    
    	    
    	    $scope.CoMovedIn1 = $scope.movedIn1;
    	    $scope.CoMovedIn2 = $scope.movedIn2;
    	    $scope.CoMovedIn3 = $scope.movedIn3;
    	    $scope.CoMovedIn4 = $scope.movedIn4;
    	    $scope.CoMovedIn5 = $scope.movedIn5;
    	    $scope.CoMovedIn6 = $scope.movedIn6;
    	    
    	    console.log("$scope.CoMovedIn1 : "+$scope.CoMovedIn1);
    	    console.log("$scope.coCurrentAddress1 : "+$scope.coCurrentAddress1);
    	  }
    	  else{
    		  console.log("$scope.SameAddress : "+$scope.SameAddress);
    		  document.forms["myForm"]["CoCurrentAddress1"].value="";
    		  $scope.coCurrentAddress1="";
    		  $scope.coAppAddress2="";
    		  $scope.coAppAddress3="";
    		  $scope.coAppAddress4="";
    		  $scope.coAppAddress5="";
    		  $scope.coAppAddress6="";
    		  
    	    $scope.CoMovedIn1 = "";
    	    document.forms["myForm"]["CoMovedIn1"].value="";
    	    $scope.CoMovedIn2 = "";
    	    $scope.CoMovedIn3 = "";
    	    $scope.CoMovedIn4 = "";
    	    $scope.CoMovedIn5 = "";
    	    $scope.CoMovedIn6 = "";
    	    
    	    console.log("else condition for co-current address : "+$scope.CoMovedIn1);
    	  }
    	};
    
           
});


/**
 * validate the form
 */



function validateForm(){
	   
	
	var regeUpperCase="[ABCEGHJKLMNPRSTVXY][0-9][ABCEGHJKLMNPRSTVWXYZ] ?[0-9][ABCEGHJKLMNPRSTVWXYZ][0-9]";
	var regexLowerCase="[abceghjklmnprstvxy][0-9][abceghjklmnprstvwxyz] ?[0-9][abceghjklmnprstvwxyz][0-9]";

	
    var currentAddress1 = document.forms["myForm"]["currentAddress1"].value;
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
        
        
        var totalcurrentmonths121 = document.forms["myForm"]["totalcurrentmonths"].value;
        
        var canadacheckinform = document.forms["myForm"]["canadacheckinform"].value;
        console.log("canadacheckinform ---  "+canadacheckinform);
       if(canadacheckinform=='no'){
        if(totalcurrentmonths121<36){
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
           
            var totalpriormonths112 = document.forms["myForm"]["totalpriormonths1"].value;
           
            if(totalpriormonths112<36){
                var currentAddress3 = document.forms["myForm"]["currentAddress3"].value;   
                if(!currentAddress3.match(regeUpperCase)|| currentAddress3.match(regexLowerCase)){
                        document.getElementById('input_2005').innerHTML="<span style='color:red'>*Please Enter Proper Address.</span>";
                        document.getElementById('geocomplete2').focus();
                        return false;
                }else{
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
            /**  changes for more 3 field here */
                var totalpriormonths2212 = document.forms["myForm"]["totalpriormonths2"].value; 
                
                if(totalpriormonths2212<36){
                	
                	var currentAddress4 = document.forms["myForm"]["currentAddress4"].value; 
                	
                	 if(!currentAddress4.match(regeUpperCase)|| currentAddress4.match(regexLowerCase)){
                         document.getElementById('input_2007').innerHTML="<span style='color:red'>*Please Enter Proper Address.</span>";
                         document.getElementById('geocomplete21').focus();
                         return false;
                 }else{
                 document.getElementById("input_2007").innerHTML="";
                 }
                	 var movedIn4 = document.forms["myForm"]["movedIn4"].value;
                     if(!movedIn4){
                             document.getElementById('input_2008').innerHTML="<span style='color:red'>*This field is Required.</span>";
                             document.getElementById('datepicker21').focus();
                             return false;
                     }else{
                     document.getElementById("input_2008").innerHTML="";
                     }
                	
                     
                     
                     var totalpriormonths32323 = document.forms["myForm"]["totalpriormonths3"].value; 
                     if(totalpriormonths32323<36){
                    	 
                    	 
                    	 var currentAddress5 = document.forms["myForm"]["currentAddress5"].value; 
                     	
                    	 if(!currentAddress5.match(regeUpperCase)|| currentAddress5.match(regexLowerCase)){
                             document.getElementById('input_2009').innerHTML="<span style='color:red'>*Please Enter Proper Address.</span>";
                             document.getElementById('geocomplete22').focus();
                             return false;
                     }else{
                     document.getElementById("input_2009").innerHTML="";
                     }
                    	 var movedIn5 = document.forms["myForm"]["movedIn5"].value;
                         if(!movedIn5){
                                 document.getElementById('input_2010').innerHTML="<span style='color:red'>*This field is Required.</span>";
                                 document.getElementById('datepicker22').focus();
                                 return false;
                         }else{
                         document.getElementById("input_2010").innerHTML="";
                         }
                    	
                         var totalpriormonths42343 = document.forms["myForm"]["totalpriormonths4"].value; 
                         
                         if(totalpriormonths42343<36){
                        	 
                        	 var currentAddress6 = document.forms["myForm"]["currentAddress6"].value; 
                          	
                        	 if(!currentAddress6.match(regeUpperCase)|| currentAddress6.match(regexLowerCase)){
                                 document.getElementById('input_2011').innerHTML="<span style='color:red'>*Please Enter Proper Address.</span>";
                                 document.getElementById('geocomplete23').focus();
                                 return false;
                         }else{
                         document.getElementById("input_2011").innerHTML="";
                         }
                        	 var movedIn6 = document.forms["myForm"]["movedIn6"].value;
                             if(!movedIn6){
                                     document.getElementById('input_2012').innerHTML="<span style='color:red'>*This field is Required.</span>";
                                     document.getElementById('datepicker23').focus();
                                     return false;
                             }else{
                             document.getElementById("input_2012").innerHTML="";
                             } 
                        	 
                         }
                     }
                     
                     
                }
            
            
            }
        }
       }
        // end of applicant 
         var additionalApplicants1121 = document.forms["myForm"]["additionalApplicants"].value;
       

         if(additionalApplicants1121 == 'yes'){
        	 
        	 
        	 
            var CoCurrentAddress1 = document.forms["myForm"]["CoCurrentAddress1"].value;
            if(!CoCurrentAddress1){
                    document.getElementById('input_2021').innerHTML="<span style='color:red'>*This field is Required.</span>";
                    document.getElementById('geocomplete3').focus();
                    return false;
            }else if(!(CoCurrentAddress1.match(regeUpperCase) || CoCurrentAddress1.match(regexLowerCase)) ){
    			document.getElementById('input_2021').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
    			document.getElementById('geocomplete3').focus();	
    			return false;
    		} else{
            document.getElementById("input_2021").innerHTML="";
            }
           
            var CoMovedIn1 = document.forms["myForm"]["CoMovedIn1"].value;
            if(!CoMovedIn1){
                    document.getElementById('input_2022').innerHTML="<span style='color:red'>*This field is Required.</span>";
                    document.getElementById('datepicker3').focus();
                    return false;
            }else{
            document.getElementById("input_2022").innerHTML="";
            }
           
            var coAppTotalcurrentMonths121 = document.forms["myForm"]["coAppTotalcurrentMonths"].value;
            
            var cocanadacheckinform = document.forms["myForm"]["cocanadacheckinform"].value;
            
            console.log("cocanadacheckinform ---  "+cocanadacheckinform);
            
            if(cocanadacheckinform=='no'){
            
            
            if(coAppTotalcurrentMonths121<36){
                var CoCurrentAddress2 = document.forms["myForm"]["CoCurrentAddress2"].value;
                if(!CoCurrentAddress2){
                        document.getElementById('input_2023').innerHTML="<span style='color:red'>*This field is Required.</span>";
                        document.getElementById('geocomplete4').focus();
                        return false;
                }else if(!(CoCurrentAddress2.match(regeUpperCase) || CoCurrentAddress2.match(regexLowerCase)) ){
        			document.getElementById('input_2023').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
        			document.getElementById('geocomplete4').focus();	
        			return false;
        		}
                else{
                document.getElementById("input_2023").innerHTML="";
                }
               
                var CoMovedIn2 = document.forms["myForm"]["CoMovedIn2"].value;
                if(!CoMovedIn2){
                        document.getElementById('input_2024').innerHTML="<span style='color:red'>*This field is Required.</span>";
                        document.getElementById('datepicker4').focus();
                        return false;
                }else{
                document.getElementById("input_2024").innerHTML="";
                }
               
                var coAppTotalpriorcurrentmonths121 = document.forms["myForm"]["coAppTotalpriorcurrentmonths1"].value;
                if(coAppTotalpriorcurrentmonths121<36){
                    var CoCurrentAddress3 = document.forms["myForm"]["CoCurrentAddress3"].value;
                    if(!CoCurrentAddress3){
                            document.getElementById('input_2025').innerHTML="<span style='color:red'>*This field is Required.</span>";
                            document.getElementById('geocomplete5').focus();
                            return false;
                    }else if(!(CoCurrentAddress3.match(regeUpperCase) || CoCurrentAddress3.match(regexLowerCase)) ){
            			document.getElementById('input_2025').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
            			document.getElementById('geocomplete5').focus();	
            			return false;
            		}
                    else{
                    document.getElementById("input_2025").innerHTML="";
                    }
                   
                    var CoMovedIn3 = document.forms["myForm"]["CoMovedIn3"].value;
                    if(!CoMovedIn3){
                            document.getElementById('input_2026').innerHTML="<span style='color:red'>*This field is Required.</span>";
                            document.getElementById('datepicker5').focus();
                            return false;
                    }else{
                    document.getElementById("input_2026").innerHTML="";
                    }
                    
                    var coApptotalpriorcurrentmonths22342 = document.forms["myForm"]["coApptotalpriorcurrentmonths2"].value;   
                    if(coApptotalpriorcurrentmonths22342<36){
                    	
                    	  var CoCurrentAddress4 = document.forms["myForm"]["CoCurrentAddress4"].value;
                          if(!CoCurrentAddress4){
                                  document.getElementById('input_2027').innerHTML="<span style='color:red'>*This field is Required.</span>";
                                  document.getElementById('geocomplete51').focus();
                                  return false;
                          }else if(!(CoCurrentAddress4.match(regeUpperCase) || CoCurrentAddress4.match(regexLowerCase)) ){
                  			document.getElementById('input_2027').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
                  			document.getElementById('geocomplete51').focus();	
                  			return false;
                  		}
                          else{
                          document.getElementById("input_2027").innerHTML="";
                          }
                         
                          var CoMovedIn4 = document.forms["myForm"]["CoMovedIn4"].value;
                          if(!CoMovedIn4){
                                  document.getElementById('input_2028').innerHTML="<span style='color:red'>*This field is Required.</span>";
                                  document.getElementById('datepicker51').focus();
                                  return false;
                          }else{
                          document.getElementById("input_2028").innerHTML="";
                          }
                   
                          
                          var coApptotalpriorcurrentmonths332423 = document.forms["myForm"]["coApptotalpriorcurrentmonths3"].value;
                          if(coApptotalpriorcurrentmonths332423<36){
                        	  var CoCurrentAddress5 = document.forms["myForm"]["CoCurrentAddress5"].value;
                              if(!CoCurrentAddress5){
                                      document.getElementById('input_2029').innerHTML="<span style='color:red'>*This field is Required.</span>";
                                      document.getElementById('geocomplete52').focus();
                                      return false;
                              }else if(!(CoCurrentAddress5.match(regeUpperCase) || CoCurrentAddress5.match(regexLowerCase)) ){
                      			document.getElementById('input_2029').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
                      			document.getElementById('geocomplete52').focus();	
                      			return false;
                      		}
                              else{
                              document.getElementById("input_2029").innerHTML="";
                              }
                             
                              var CoMovedIn5 = document.forms["myForm"]["CoMovedIn5"].value;
                              if(!CoMovedIn5){
                                      document.getElementById('input_2030').innerHTML="<span style='color:red'>*This field is Required.</span>";
                                      document.getElementById('datepicker52').focus();
                                      return false;
                              }else{
                              document.getElementById("input_2030").innerHTML="";
                              }
                       
                              
                              var coApptotalpriorcurrentmonths4 = document.forms["myForm"]["coApptotalpriorcurrentmonths4"].value; 
                              if(coApptotalpriorcurrentmonths4<36){
                            	  var CoCurrentAddress6 = document.forms["myForm"]["CoCurrentAddress6"].value;
                                  if(!CoCurrentAddress6){
                                          document.getElementById('input_2031').innerHTML="<span style='color:red'>*This field is Required.</span>";
                                          document.getElementById('geocomplete53').focus();
                                          return false;
                                  }else if(!(CoCurrentAddress6.match(regeUpperCase) || CoCurrentAddress6.match(regexLowerCase)) ){
                          			document.getElementById('input_2031').innerHTML = "<span style='color:red'>*Please Enter Proper Address.</span>";
                          			document.getElementById('geocomplete53').focus();	
                          			return false;
                          		}
                                  else{
                                  document.getElementById("input_2031").innerHTML="";
                                  }
                                  var CoMovedIn6 = document.forms["myForm"]["CoMovedIn6"].value;
                                  if(!CoMovedIn6){
                                          document.getElementById('input_2032').innerHTML="<span style='color:red'>*This field is Required.</span>";
                                          document.getElementById('datepicker53').focus();
                                          return false;
                                  }else{
                                  document.getElementById("input_2032").innerHTML="";
                                  }
                           
                            	  
                              }
                              
                        	  
                          }
                    
                    }
                    
                    
                    
                }
                
                
                
                
            }}// end of co-applicant
        }
       
        return true;   
    }

/**
 * for selet function
 */


window.onload = function() {
    SelectElement();
};

function getFormattedDate(date) {
	 
	}
function SelectElement() {  
    var currentAddress  = document.forms["myForm"]["currentAddress1212"].value;
    document.forms["myForm"]["currentAddress1"].value = currentAddress;   
   
    var moveIn1212  = document.forms["myForm"]["moveIn1212"].value; 
    document.forms["myForm"]["movedIn1"].value = moveIn1212;
 
   
  
	 
   
  
  
   
   
   
    var currentAddress21212  = document.forms["myForm"]["currentAddress2121"].value; 
    document.forms["myForm"]["currentAddress2"].value = currentAddress21212;
    var moveIn21212  = document.forms["myForm"]["moveIn2121"].value; 
    document.forms["myForm"]["movedIn2"].value = moveIn21212;
   
    var currentAddress313  = document.forms["myForm"]["currentAddress3131"].value; 
    document.forms["myForm"]["currentAddress3"].value = currentAddress313;
    var moveIn313132  = document.forms["myForm"]["moveIn3131"].value; 
    document.forms["myForm"]["movedIn3"].value = moveIn313132;
    
    var currentAddress4141  = document.forms["myForm"]["currentAddress4141"].value; 
    document.forms["myForm"]["currentAddress4"].value = currentAddress4141;
    var moveIn4141  = document.forms["myForm"]["moveIn4141"].value; 
    document.forms["myForm"]["movedIn4"].value = moveIn4141;
    
    
    var currentAddress5151  = document.forms["myForm"]["currentAddress5151"].value; 
    document.forms["myForm"]["currentAddress5"].value = currentAddress5151;
    var moveIn5151  = document.forms["myForm"]["moveIn5151"].value; 
    document.forms["myForm"]["movedIn5"].value = moveIn5151;
    
    
    var currentAddress6161  = document.forms["myForm"]["currentAddress6161"].value; 
    document.forms["myForm"]["currentAddress6"].value = currentAddress6161;
    var moveIn6161  = document.forms["myForm"]["moveIn6161"].value; 
    document.forms["myForm"]["movedIn6"].value = moveIn6161;
    
    
    
  
    var coCurrentAddress4311  = document.forms["myForm"]["coCurrentAddress431"].value; 
    document.forms["myForm"]["CoCurrentAddress1"].value = coCurrentAddress4311;
    var coMoveIn4322  = document.forms["myForm"]["coMoveIn432"].value; 
    document.forms["myForm"]["CoMovedIn1"].value = coMoveIn4322;
   
    var coCurrentAddress4333  = document.forms["myForm"]["coCurrentAddress433"].value; 
    document.forms["myForm"]["CoCurrentAddress2"].value = coCurrentAddress4333;
    var coMoveIn4344  = document.forms["myForm"]["coMoveIn434"].value; 
    document.forms["myForm"]["CoMovedIn2"].value = coMoveIn4344;
   
    var coCurrentAddress4355  = document.forms["myForm"]["coCurrentAddress435"].value; 
    document.forms["myForm"]["CoCurrentAddress3"].value = coCurrentAddress4355;
    var coMoveIn4366  = document.forms["myForm"]["coMoveIn436"].value; 
    document.forms["myForm"]["CoMovedIn3"].value = coMoveIn4366;
    
    
    
    var coCurrentAddress4351  = document.forms["myForm"]["coCurrentAddress4351"].value; 
    document.forms["myForm"]["CoCurrentAddress4"].value = coCurrentAddress4351;
    var coMoveIn4361  = document.forms["myForm"]["coMoveIn4361"].value; 
    document.forms["myForm"]["CoMovedIn4"].value = coMoveIn4361;
    
    var coCurrentAddress4352  = document.forms["myForm"]["coCurrentAddress4352"].value; 
    document.forms["myForm"]["CoCurrentAddress5"].value = coCurrentAddress4352;
    var coMoveIn4362  = document.forms["myForm"]["coMoveIn4362"].value; 
    document.forms["myForm"]["CoMovedIn5"].value = coMoveIn4362;
    
    
    var coCurrentAddress4353  = document.forms["myForm"]["coCurrentAddress4353"].value; 
    document.forms["myForm"]["CoCurrentAddress6"].value = coCurrentAddress4353;
    var coMoveIn4363  = document.forms["myForm"]["coMoveIn4363"].value; 
    document.forms["myForm"]["CoMovedIn6"].value = coMoveIn4363;
    
}

/**
 * geocomplete function code here for every field
 */


$(function(){
    $("#geocomplete").geocomplete({
          types:["geocode", "establishment"]
      })
      .bind("geocode:result", function (event, result) {
          //$.log("Result: " + result.formatted_address);
          //alert("Result: " + result.formatted_address);
          document.getElementById("geocomplete").value = result.formatted_address;
          //copying value of geocomplete id to geocomplete3
          //document.getElementById("geocomplete3").value = result.formatted_address;
      })
      .bind("geocode:error", function (event, status) {
         // alert("ERROR: " + status);
      })
      .bind("geocode:multiple", function (event, results) {
         //alert("Multiple: " + results.length + " results found");
      });
});



$(function(){
    $("#geocomplete1").geocomplete({
          types:["geocode", "establishment"]
      })
      .bind("geocode:result", function (event, result) {
          //$.log("Result: " + result.formatted_address);
          //alert("Result: " + result.formatted_address);
          document.getElementById("geocomplete1").value = result.formatted_address;
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
          //alert("ERROR: " + status);
      })
      .bind("geocode:multiple", function (event, results) {
         //alert("Multiple: " + results.length + " results found");
      }); 
});



$(function(){
    $("#geocomplete3").geocomplete({
          types:["geocode", "establishment"]
      })
      .bind("geocode:result", function (event, result) {
          //$.log("Result: " + result.formatted_address);
        //  alert("Result: " + result.formatted_address);
        document.getElementById("geocomplete3").value = result.formatted_address;
      })
      .bind("geocode:error", function (event, status) {
          //alert("ERROR: " + status);
      })
      .bind("geocode:multiple", function (event, results) {
         //alert("Multiple: " + results.length + " results found");
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
          //alert("ERROR: " + status);
      })
      .bind("geocode:multiple", function (event, results) {
         //alert("Multiple: " + results.length + " results found");
      });
   
  });

$(function(){
    $("#geocomplete5").geocomplete({
          types:["geocode", "establishment"]
      })
      .bind("geocode:result", function (event, result) {
          //$.log("Result: " + result.formatted_address);
          //alert("Result: " + result.formatted_address);
          document.getElementById("geocomplete5").value = result.formatted_address;
      })
      .bind("geocode:error", function (event, status) {
          alert("ERROR: " + status);
      })
      .bind("geocode:multiple", function (event, results) {
         alert("Multiple: " + results.length + " results found");
      }); 
  });


$(function(){
    $("#geocomplete21").geocomplete({
          types:["geocode", "establishment"]
      })
      .bind("geocode:result", function (event, result) {
          //$.log("Result: " + result.formatted_address);
          //alert("Result: " + result.formatted_address);
          document.getElementById("geocomplete21").value = result.formatted_address;
      })
      .bind("geocode:error", function (event, status) {
          alert("ERROR: " + status);
      })
      .bind("geocode:multiple", function (event, results) {
         alert("Multiple: " + results.length + " results found");
      }); 
  });

$(function(){
    $("#geocomplete22").geocomplete({
          types:["geocode", "establishment"]
      })
      .bind("geocode:result", function (event, result) {
          //$.log("Result: " + result.formatted_address);
          //alert("Result: " + result.formatted_address);
          document.getElementById("geocomplete22").value = result.formatted_address;
      })
      .bind("geocode:error", function (event, status) {
          alert("ERROR: " + status);
      })
      .bind("geocode:multiple", function (event, results) {
         alert("Multiple: " + results.length + " results found");
      }); 
  });


$(function(){
    $("#geocomplete23").geocomplete({
          types:["geocode", "establishment"]
      })
      .bind("geocode:result", function (event, result) {
          //$.log("Result: " + result.formatted_address);
          //alert("Result: " + result.formatted_address);
          document.getElementById("geocomplete23").value = result.formatted_address;
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




/**
 * for data-toggle 
 */

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();  
});