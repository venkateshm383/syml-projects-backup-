/**
 * for angular js using 
 */
 var app = angular.module("demoApp", ['ui.bootstrap']);

    app.controller('DemoController', function ($scope) {
    	
    	console.log("inside angular controller ");
        init();
        function init() {
        	console.log("inside init() called of controller ");
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
 * For showing alert on back button and close the tab.
 * 
 */
 window.onbeforeunload = function () {
	 
	 alert("are ");
            return "Are you sure?";
        };

        $(document).ready(function () {
        	
            $('form').submit(function () {
                window.onbeforeunload = null;
            });
        });
        
        /**
         * 
         */
        
        
        window.onload = function() {
        	console.log("inside onload");
        	SelectElement();
        	
        };
        function SelectElement() {  
        	console.log("inside SelectElement() ");
            var ownership123 = document.forms["myForm"]["ownership12"].value;  
            document.forms["myForm"]["ownership1"].value=ownership123;
        }
        
        
/**
 * validate the form
 * @returns {Boolean}
 */
	function validateForm(){
		
		console.log("inside the validateForm ");
		var howManyProperties111 = document.forms["myForm"]["howManyProperties"].value;
		console.log("howManyProperties111" +howManyProperties111);
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
			var  mortgage1 =document.forms["myForm"]["mortgage1"].value;
			if(!mortgage1){
		        document.getElementById('input_200_212').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_200_212").innerHTML="";
    	}
			var rentMo1 =document.forms["myForm"]["rentMo1"].value;
			if(!rentMo1){
		        document.getElementById('input_200_213').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_200_213").innerHTML="";
    	}
	var condoFees1 =document.forms["myForm"]["condoFees1"].value;
	if(!condoFees1){
        document.getElementById('input_200_214').innerHTML="<span style='color:red'>*This field is Required.</span>";
        return false;
}else{
document.getElementById("input_200_214").innerHTML="";
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
			        document.getElementById('input_2A1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2A1").innerHTML="";
	    	}

			var appxValue211 = document.forms["myForm"]["appxValue21"].value;
			if(!appxValue211){
			        document.getElementById('input_2B1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2B1").innerHTML="";
	    	}
			//changes here 
			var mortgage21 = document.forms["myForm"]["mortgage21"].value;
			if(!mortgage21){
			        document.getElementById('input_2C1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2C1").innerHTML="";
	    	}
			var rentMo21 = document.forms["myForm"]["rentMo21"].value;
			if(!rentMo21){
			        document.getElementById('input_2D1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2D1").innerHTML="";
	    	}
			
			var condoFees21 = document.forms["myForm"]["condoFees21"].value;
			if(!condoFees21){
		        document.getElementById('input_2E1').innerHTML="<span style='color:red'>*This field is Required.</span>";
		        return false;
		}else{
    	document.getElementById("input_2E1").innerHTML="";
    	}
			
			
			//end here
			var ownership211 = document.forms["myForm"]["ownership21"].value;
			if(!ownership211){
			        document.getElementById('input_2F1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2F1").innerHTML="";
	    	}
			
			var currentAddress221 = document.forms["myForm"]["currentAddress22"].value;
			if(!currentAddress221){
			        document.getElementById('input_2A2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2A2").innerHTML="";
	    	}

			var appxValue221 = document.forms["myForm"]["appxValue22"].value;
			if(!appxValue221){
			        document.getElementById('input_2B2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2B2").innerHTML="";
	    	}
			var mortgage22 = document.forms["myForm"]["mortgage22"].value;
			if(!mortgage22){
			        document.getElementById('input_2C2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2C2").innerHTML="";
	    	}
			var rentMo22 = document.forms["myForm"]["rentMo22"].value;
			if(!rentMo22){
			        document.getElementById('input_2D2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2D2").innerHTML="";
	    	}
			
			var condoFees22 = document.forms["myForm"]["condoFees22"].value;
			if(!condoFees22){
			        document.getElementById('input_2E2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2E2").innerHTML="";
	    	}
			var ownership221 = document.forms["myForm"]["ownership22"].value;
			if(!ownership221){
			        document.getElementById('input_2F2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_2F2").innerHTML="";
	    	}
			
		}
		
		if(howManyProperties111 == "three"){
			var currentAddress311 = document.forms["myForm"]["currentAddress31"].value;
			if(!currentAddress311){
			        document.getElementById('input_3A1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3A1").innerHTML="";
	    	}
			 
			var appxValue311 = document.forms["myForm"]["appxValue31"].value;
			if(!appxValue311){
			        document.getElementById('input_3B1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3B1").innerHTML="";
	    	}
			 //changes here 
			
			 var mortgage31 = document.forms["myForm"]["mortgage31"].value;
				if(!mortgage31){
				        document.getElementById('input_3C1').innerHTML="<span style='color:red'>*This field is Required.</span>";
				        return false;
				}else{
		    	document.getElementById("input_3C1").innerHTML="";
		    	}
				
				 var rentMo31 = document.forms["myForm"]["rentMo31"].value;
					if(!rentMo31){
					        document.getElementById('input_3D1').innerHTML="<span style='color:red'>*This field is Required.</span>";
					        return false;
					}else{
			    	document.getElementById("input_3D1").innerHTML="";
			    	}
					
					 var condoFees31 = document.forms["myForm"]["condoFees31"].value;
						if(!condoFees31){
						        document.getElementById('input_3E1').innerHTML="<span style='color:red'>*This field is Required.</span>";
						        return false;
						}else{
				    	document.getElementById("input_3E1").innerHTML="";
				    	}
				
						
								
			 //end here
			var ownership311 = document.forms["myForm"]["ownership31"].value;
			if(!ownership311){
			        document.getElementById('input_3F1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3F1").innerHTML="";
	    	}
			
			var currentAddress321 = document.forms["myForm"]["currentAddress32"].value;
			if(!currentAddress321){
			        document.getElementById('input_3A2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3A2").innerHTML="";
	    	}
			 
			var appxValue321 = document.forms["myForm"]["appxValue32"].value;
			if(!appxValue321){
			        document.getElementById('input_3B2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3B2").innerHTML="";
	    	}
			 	
			 var mortgage32 = document.forms["myForm"]["mortgage32"].value;
				if(!mortgage32){
				        document.getElementById('input_3C2').innerHTML="<span style='color:red'>*This field is Required.</span>";
				        return false;
				}else{
		    	document.getElementById("input_3C2").innerHTML="";
		    	}
				
				 var rentMo32 = document.forms["myForm"]["rentMo32"].value;
					if(!rentMo32){
					        document.getElementById('input_3D2').innerHTML="<span style='color:red'>*This field is Required.</span>";
					        return false;
					}else{
			    	document.getElementById("input_3D2").innerHTML="";
			    	}
				
					 var condoFees32 = document.forms["myForm"]["condoFees32"].value;
						if(!condoFees32){
						        document.getElementById('input_3E2').innerHTML="<span style='color:red'>*This field is Required.</span>";
						        return false;
						}else{
				    	document.getElementById("input_3E2").innerHTML="";
				    	}	
			var ownership321 = document.forms["myForm"]["ownership32"].value;
			if(!ownership321){
			        document.getElementById('input_3F2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3F2").innerHTML="";
	    	}
			
			var currentAddress331 = document.forms["myForm"]["currentAddress33"].value;
			if(!currentAddress331){
			        document.getElementById('input_3A3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3A3").innerHTML="";
	    	}
			 
			var appxValue331 = document.forms["myForm"]["appxValue33"].value;
			if(!appxValue331){
			        document.getElementById('input_3B3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3B3").innerHTML="";
	    	}
			var mortgage33 = document.forms["myForm"]["mortgage33"].value;
			if(!mortgage33){
			        document.getElementById('input_3C3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3C3").innerHTML="";
	    	}
			var rentMo33 = document.forms["myForm"]["rentMo33"].value;
			if(!rentMo33){
			        document.getElementById('input_3D3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3D3").innerHTML="";
	    	}
			var condoFees33 = document.forms["myForm"]["condoFees33"].value;
			if(!condoFees33){
			        document.getElementById('input_3E3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3E3").innerHTML="";
	    	}
						
			var ownership331 = document.forms["myForm"]["ownership33"].value;
			if(!ownership331){
			        document.getElementById('input_3F3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_3F3").innerHTML="";
	    	}
			
		}
		
		if(howManyProperties111 == "four"){
			
			var currentAddress412 = document.forms["myForm"]["currentAddress41"].value;
			if(!currentAddress412){
			        document.getElementById('input_4A1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4A1").innerHTML="";
	    	}

			var appxValue412 = document.forms["myForm"]["appxValue41"].value;
			if(!appxValue412){
			        document.getElementById('input_4B1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4B1").innerHTML="";
	    	}
				//changes here 1
				var mortgage41 = document.forms["myForm"]["mortgage41"].value;
			if(!mortgage41){
			        document.getElementById('input_4C1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4C1").innerHTML="";
	    	}
			var rentMo41 = document.forms["myForm"]["rentMo41"].value;
			if(!rentMo41){
			        document.getElementById('input_4D1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4D1").innerHTML="";
	    	}
			var condoFees41 = document.forms["myForm"]["condoFees41"].value;
			if(!condoFees41){
			        document.getElementById('input_4E1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4E1").innerHTML="";
	    	}
				
				
				
				//end here
			var ownership411 = document.forms["myForm"]["ownership41"].value;
			
			if(!ownership411){
			        document.getElementById('input_4F1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4F1").innerHTML="";
	    	}
			
			var currentAddress422 = document.forms["myForm"]["currentAddress42"].value;
			if(!currentAddress422){
			        document.getElementById('input_4A2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4A2").innerHTML="";
	    	}

			var appxValue422 = document.forms["myForm"]["appxValue42"].value;
			if(!appxValue422){
			        document.getElementById('input_4B2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4B2").innerHTML="";
	    	}
				// changes here 2
					var mortgage42 = document.forms["myForm"]["mortgage42"].value;
			if(!mortgage42){
			        document.getElementById('input_4C2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4C2").innerHTML="";
	    	}
			
			var rentMo42 = document.forms["myForm"]["rentMo42"].value;
			if(!rentMo42){
			        document.getElementById('input_4D2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4D2").innerHTML="";
	    	}
			var condoFees42 = document.forms["myForm"]["condoFees42"].value;
			if(!condoFees42){
			        document.getElementById('input_4E2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4E2").innerHTML="";
	    	}
				
				
				
				// end here
			var ownership4221 = document.forms["myForm"]["ownership42"].value;
			if(!ownership4221){
			        document.getElementById('input_4F2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4F2").innerHTML="";
	    	}
			
			var currentAddress431 = document.forms["myForm"]["currentAddress43"].value;
			if(!currentAddress431){
			        document.getElementById('input_4A3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4A3").innerHTML="";
	    	}

			var appxValue432 = document.forms["myForm"]["appxValue43"].value;
			if(!appxValue432){
			        document.getElementById('input_4B3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4B3").innerHTML="";
	    	}
			//chagnes here 3 
			var mortgage43 = document.forms["myForm"]["mortgage43"].value;
			if(!mortgage43){
			        document.getElementById('input_4C3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4C3").innerHTML="";
	    	}
			var rentMo43 = document.forms["myForm"]["rentMo43"].value;
			if(!rentMo43){
			        document.getElementById('input_4D3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4D3").innerHTML="";
	    	}
			
			var condoFees43 = document.forms["myForm"]["condoFees43"].value;
			if(!condoFees43){
			        document.getElementById('input_4E3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4E3").innerHTML="";
	    	}
				
				
				
				
				//end here
			var ownership431 = document.forms["myForm"]["ownership43"].value;
			if(!ownership431){
			        document.getElementById('input_4F3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4F3").innerHTML="";
	    	}
			
			var currentAddress441 = document.forms["myForm"]["currentAddress44"].value;
			if(!currentAddress441){
			        document.getElementById('input_4A4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4A4").innerHTML="";
	    	}

			var appxValue441 = document.forms["myForm"]["appxValue44"].value;
			if(!appxValue441){
			        document.getElementById('input_4B4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4B4").innerHTML="";
	    	}
					//changes here 4 
					var mortgage44 = document.forms["myForm"]["mortgage44"].value;
			if(!mortgage44){
			        document.getElementById('input_4C4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4C4").innerHTML="";
	    	}
			var rentMo44 = document.forms["myForm"]["rentMo44"].value;
			if(!rentMo44){
			        document.getElementById('input_4D4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4D4").innerHTML="";
	    	}
			
			
			var condoFees44 = document.forms["myForm"]["condoFees44"].value;
			if(!condoFees44){
			        document.getElementById('input_4E4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4E4").innerHTML="";
	    	}
				
					
					
					
					// end here
			var ownership441 = document.forms["myForm"]["ownership44"].value;
			if(!ownership441){
			        document.getElementById('input_4F4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_4F4").innerHTML="";
	    	}
			
		}
		if(howManyProperties111 == "more"){
			var currentAddress51 = document.forms["myForm"]["currentAddress51"].value;
			if(!currentAddress51){
			        document.getElementById('input_5A1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5A1").innerHTML="";
	    	}
			
			var appxValue51 = document.forms["myForm"]["appxValue51"].value;
			if(!appxValue51){
			        document.getElementById('input_5B1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5B1").innerHTML="";
	    	}
			
			var mortgage51 = document.forms["myForm"]["mortgage51"].value;
			if(!mortgage51){
			        document.getElementById('input_5C1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5C1").innerHTML="";
	    	}
			
			var rentMo51 = document.forms["myForm"]["rentMo51"].value;
			if(!rentMo51){
			        document.getElementById('input_5D1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5D1").innerHTML="";
	    	}
			var condoFees51 = document.forms["myForm"]["condoFees51"].value;
			if(!condoFees51){
			        document.getElementById('input_5E1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5E1").innerHTML="";
	    	}
				
			var ownership51 = document.forms["myForm"]["ownership51"].value;
			
			if(!ownership51){
			        document.getElementById('input_5F1').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5F1").innerHTML="";
	    	}
			
			/**	 1st end here */
			
			var currentAddress52 = document.forms["myForm"]["currentAddress52"].value;
			if(!currentAddress52){
			        document.getElementById('input_5A2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5A2").innerHTML="";
	    	}
			
			var appxValue52 = document.forms["myForm"]["appxValue52"].value;
			if(!appxValue52){
			        document.getElementById('input_5B2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5B2").innerHTML="";
	    	}
			
			var mortgage52 = document.forms["myForm"]["mortgage52"].value;
			if(!mortgage52){
			        document.getElementById('input_5C2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5C2").innerHTML="";
	    	}
			
			var rentMo52 = document.forms["myForm"]["rentMo52"].value;
			if(!rentMo52){
			        document.getElementById('input_5D2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5D2").innerHTML="";
	    	}
			var condoFees52 = document.forms["myForm"]["condoFees52"].value;
			if(!condoFees52){
			        document.getElementById('input_5E2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5E2").innerHTML="";
	    	}
				
			var ownership52 = document.forms["myForm"]["ownership52"].value;
			
			if(!ownership52){
			        document.getElementById('input_5F2').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5F2").innerHTML="";
	    	}
			/** 2nd end here */
			
			var currentAddress53 = document.forms["myForm"]["currentAddress53"].value;
			
			if(!currentAddress53){
			        document.getElementById('input_5A3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5A3").innerHTML="";
	    	}
			
			var appxValue53 = document.forms["myForm"]["appxValue53"].value;
			if(!appxValue53){
			        document.getElementById('input_5B3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5B3").innerHTML="";
	    	}
			
			var mortgage53 = document.forms["myForm"]["mortgage53"].value;
			if(!mortgage53){
			        document.getElementById('input_5C3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5C3").innerHTML="";
	    	}
			
			var rentMo53 = document.forms["myForm"]["rentMo53"].value;
			if(!rentMo53){
			        document.getElementById('input_5D3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5D3").innerHTML="";
	    	}
			var condoFees53 = document.forms["myForm"]["condoFees53"].value;
			if(!condoFees53){
			        document.getElementById('input_5E3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5E3").innerHTML="";
	    	}
				
			var ownership53 = document.forms["myForm"]["ownership53"].value;
			
			if(!ownership53){
			        document.getElementById('input_5F3').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5F3").innerHTML="";
	    	}
			/**3 rd end here*/
			
			
			var currentAddress54 = document.forms["myForm"]["currentAddress54"].value;
			if(!currentAddress54){
			        document.getElementById("input_5A4").innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5A4").innerHTML="";
	    	}
			
			var appxValue54 = document.forms["myForm"]["appxValue54"].value;
			if(!appxValue54){
			        document.getElementById('input_5B4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5B4").innerHTML="";
	    	}
			
			var mortgage54 = document.forms["myForm"]["mortgage54"].value;
			if(!mortgage54){
			        document.getElementById('input_5C4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5C4").innerHTML="";
	    	}
			
			var rentMo54 = document.forms["myForm"]["rentMo54"].value;
			if(!rentMo54){
			        document.getElementById('input_5D4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5D4").innerHTML="";
	    	}
			var condoFees54 = document.forms["myForm"]["condoFees54"].value;
			if(!condoFees54){
			        document.getElementById('input_5E4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5E4").innerHTML="";
	    	}
				
			var ownership54 = document.forms["myForm"]["ownership54"].value;
			
			if(!ownership54){
			        document.getElementById('input_5F4').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5F4").innerHTML="";
	    	}
			
			/**4th end here*/
			
			
			

			var currentAddress55 = document.forms["myForm"]["currentAddress55"].value;
			if(!currentAddress55){
			        document.getElementById("input_5A5").innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5A5").innerHTML="";
	    	}
			
			var appxValue55 = document.forms["myForm"]["appxValue55"].value;
			if(!appxValue55){
			        document.getElementById('input_5B5').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5B5").innerHTML="";
	    	}
			
			var mortgage55 = document.forms["myForm"]["mortgage55"].value;
			if(!mortgage55){
			        document.getElementById('input_5C5').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5C5").innerHTML="";
	    	}
			
			var rentMo55 = document.forms["myForm"]["rentMo55"].value;
			if(!rentMo55){
			        document.getElementById('input_5D5').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5D5").innerHTML="";
	    	}
			var condoFees55 = document.forms["myForm"]["condoFees55"].value;
			if(!condoFees55){
			        document.getElementById('input_5E5').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5E5").innerHTML="";
	    	}
				
			var ownership55 = document.forms["myForm"]["ownership55"].value;
			
			if(!ownership55){
			        document.getElementById('input_5F5').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5F5").innerHTML="";
	    	}
			
			/**5th end here */
			

			var currentAddress56 = document.forms["myForm"]["currentAddress56"].value;
			if(!currentAddress56){
			        document.getElementById("input_5A6").innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5A6").innerHTML="";
	    	}
			
			var appxValue56 = document.forms["myForm"]["appxValue56"].value;
			if(!appxValue56){
			        document.getElementById('input_5B6').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5B6").innerHTML="";
	    	}
			
			var mortgage56 = document.forms["myForm"]["mortgage56"].value;
			if(!mortgage56){
			        document.getElementById('input_5C6').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5C6").innerHTML="";
	    	}
			
			var rentMo56 = document.forms["myForm"]["rentMo56"].value;
			if(!rentMo56){
			        document.getElementById('input_5D6').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5D6").innerHTML="";
	    	}
			var condoFees56 = document.forms["myForm"]["condoFees56"].value;
			if(!condoFees56){
			        document.getElementById('input_5E6').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5E6").innerHTML="";
	    	}
				
			var ownership56 = document.forms["myForm"]["ownership56"].value;
			
			if(!ownership56){
			        document.getElementById('input_5F6').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5F6").innerHTML="";
	    	}
			/**6th end here*/
			var currentAddress57 = document.forms["myForm"]["currentAddress57"].value;
			if(!currentAddress57){
			        document.getElementById("input_5A7").innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5A7").innerHTML="";
	    	}
			
			var appxValue57 = document.forms["myForm"]["appxValue57"].value;
			if(!appxValue57){
			        document.getElementById('input_5B7').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5B7").innerHTML="";
	    	}
			
			var mortgage57 = document.forms["myForm"]["mortgage57"].value;
			if(!mortgage57){
			        document.getElementById('input_5C7').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5C7").innerHTML="";
	    	}
			
			var rentMo57 = document.forms["myForm"]["rentMo57"].value;
			if(!rentMo57){
			        document.getElementById('input_5D7').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5D7").innerHTML="";
	    	}
			var condoFees57 = document.forms["myForm"]["condoFees57"].value;
			if(!condoFees57){
			        document.getElementById('input_5E7').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5E7").innerHTML="";
	    	}
				
			var ownership57 = document.forms["myForm"]["ownership57"].value;
			
			if(!ownership57){
			        document.getElementById('input_5F7').innerHTML="<span style='color:red'>*This field is Required.</span>";
			        return false;
			}else{
	    	document.getElementById("input_5F7").innerHTML="";
	    	}
			
			/**7th end here*/
			
		}
		return true;	
	}

	
	/**
	 * For address using when selected button ONE 
	 */
	
	
	  $(function(){
		  console.log("inside the geocomplete where  id using geocomplete0");
		 
    	  $("#geocomplete0").geocomplete({
    	        types:["geocode", "establishment"]
    	    })
    	    .bind("geocode:result", function (event, result) {
    	       
    	    	document.getElementById("geocomplete0").value = result.formatted_address;
    	    })
    	    .bind("geocode:error", function (event, status) {
    	        alert("ERROR: " + status);
    	    })
    	    .bind("geocode:multiple", function (event, results) {
    	       alert("Multiple: " + results.length + " results found");
    	    });   
      });
	
	/**
	 * For address using when selected button TWO
	 */
	  $(function(){
		 
		  console.log("inside the geocomplete where  id using geocomplete71");
	    	$("#geocomplete71").geocomplete({
	  	        types:["geocode", "establishment"]
	  	    })
	  	    .bind("geocode:result", function (event, result) {
	  	       
	  	    	document.getElementById("geocomplete71").value = result.formatted_address;
	  	    })
	  	    .bind("geocode:error", function (event, status) {
	  	        alert("ERROR: " + status);
	  	    })
	  	    .bind("geocode:multiple", function (event, results) {
	  	       alert("Multiple: " + results.length + " results found");
	  	    });   
	      });
	  
	  /**
	   * For address using when selected button THREE
	   */
	  
	  $(function(){
		 
		  console.log("inside the geocomplete where  id using geocomplete72");
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
	  
	  /**
	   *  For address using when selected button FOUR
	   */
	  
	  $(function(){
		
		  console.log(" inside the geocomplete where  id using geocomplete2 ");
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
	  /**
	   *  For address using when selected button FIVE
	   */
	  
	  $(function(){
		  
		  console.log(" inside the geocomplete where  id using geocomplete3 ");
		 
	    
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
	  
	  
	  /**
	   * 
	   */
	  $(function(){
		  console.log("inside the geocomplete where  id using geocomplete4");
		  
	    
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
	  
	  /**
	   * 
	   */
	  
	  $(function(){
		  console.log("inside the geocomplete where  id using geocomplete41");
		 
	    
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
	  
	  /**
	   * 
	   */
	  
	  $(function(){ 
		  console.log("inside the geocomplete where  id using geocomplete42");
		 
	    
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
	  
	  
	  /**
	   * 
	   */
	  
	  $(function(){
		  console.log("inside the geocomplete where  id using geocomplete43");
		 
	    
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
	  /**
	   * 
	   */
	  
	  $(function(){
		  
		  console.log("inside the geocomplete where  id using geocomplete44");
		  
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
	  /**
	   * 
	   */
	  
	  $(function(){
		  
		  console.log("inside the geocomplete where  id using geocomplete51");
		 
	    
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
	  
	  /**
	   * 
	   */
	  
	  $(function(){
		  
		  console.log("inside the geocomplete where  id using geocomplete52");
		 
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
	  
	  
	  
	  /**
	   * 
	   */
	  
	  $(function(){
		  console.log("inside the geocomplete where  id using geocomplete53");
		 
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
	   * 
	   */
	  
	  $(function(){
		  
		  console.log("inside the geocomplete where  id using geocomplete54");
		 
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
	  
	  /**
	   * 
	   */
	  
	  $(function(){
		  
		  console.log("inside the geocomplete where  id using geocomplete55");
		    
		  
	    
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
	  
	  
	  /**
	   * 
	   */
	  
	  $(function(){
		  
		  console.log("inside the geocomplete where  id using geocomplete56");
		 
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
	  
	  /**
	   * 
	   */
	  
	  
	  $(function(){
		  console.log("inside the geocomplete where  id using geocomplete57");
		
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
	  
	  
	  
	  
	  /**
	   * 
	   * @param evt
	   * @returns {Boolean}
	   */
	  
	  function isNumber(evt) {
		  console.log("isNumber functon is called ");
	        evt = (evt) ? evt : window.event;
	        var charCode = (evt.which) ? evt.which : evt.keyCode;
	        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	        	 alert("Please enter a number (no comma or dash)");
	            return false;
	        }
	        return true;
	    }
	  
	  
	
	 
	  
	  
	
	  