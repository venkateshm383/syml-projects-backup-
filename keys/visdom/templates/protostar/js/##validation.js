//Function For Form Validation
function validForm() {       
	var debug = false; //      ONLY TRUE FOR TEST PURPOSES                
	if (debug) {   		     		
		return true;           
	}
	var mCheck = false;

	switch (curPos) 
	{
		
		case 0:			
		// FIRST NAME
		if (document.getElementsByName("W2TxtFirstNsme")[0].value == '') {
			document.getElementById("W2TxtFirstNsme_lbl").innerHTML="Please Enter First Name";
			signalError("W2TxtFirstNsme", true, true, true);
			mCheck = true;  
			//location.href = "#W2TxtFirstNsme";
		} else {
			document.getElementById("W2TxtFirstNsme_lbl").innerHTML="";
			signalError("W2TxtFirstNsme", false, true, true);
			window.scrollTo(0,0);   
		}

		// LAST NAME

		if (document.getElementsByName("W2TxtLastName")[0].value == '' ) {
			document.getElementById("W2TxtLastName_lbl").innerHTML="Please Enter Last Name";
			signalError("W2TxtLastName", true, true, true);
			mCheck = true;
			//location.href = "#W2TxtLastName";
		} else {
			document.getElementById("W2TxtLastName_lbl").innerHTML="";
			signalError("W2TxtLastName", false, true, true);
			window.scrollTo(0,0); 
		}

		//Email
		var email = document.getElementsByName("W2TxtEmail")[0].value.trim(); 				
		if (document.getElementsByName("W2TxtEmail")[0].value == '') {
			document.getElementById("W2TxtEmail_lbl").innerHTML="Please Enter Email";
			signalError("W2TxtEmail", true, true, true);
			mCheck = true;
			//location.href = "#W2TxtEmail";
			//	window.scrollTo(300,300);   
		}
		else if(!isValidEmailAddress(email))
		{
			document.getElementById("W2TxtEmail_lbl1").innerHTML="Please Enter Valid Email";
			signalError("W2TxtEmail", true, true, true);
			mCheck = true;
		}
		else {
			document.getElementById("W2TxtEmail_lbl").innerHTML="";
			document.getElementById("W2TxtEmail_lbl1").innerHTML="";
			signalError("W2TxtEmail", false, true, true);
			window.scrollTo(0,0); 
		}

		//Street
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{  
			document.getElementById("W2TxtStreet_lbl").innerHTML="";
			signalError("W2TxtStreet", false, true, true);
		}
		else
		{
			if (document.getElementsByName("W2TxtStreet")[0].value == '') {
				document.getElementById("W2TxtStreet_lbl").innerHTML="Please Enter Street";
				signalError("W2TxtStreet", true, true, true);
				mCheck = true;

				//location.href = "#W2TxtStreet";
			} else {
				document.getElementById("W2TxtStreet_lbl").innerHTML="";
				signalError("W2TxtStreet", false, true, true);
				window.scrollTo(0,0); 
			}
		}


		//Cell
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{  
			document.getElementById("W2TxtCell_lbl").innerHTML="";
			signalError("W2TxtCell", false, true, true);
		}
		else          
		{  
			if (document.getElementsByName("W2TxtCell")[0].value =='') {
				document.getElementById("W2TxtCell_lbl").innerHTML="Please Enter Cell Phone #";
				signalError("W2TxtCell", true, true, true);
				mCheck = true;  
				jQuery("#W2TxtCell").removeClass("ifocus"); 

				//location.href = "#W2TxtCell";
			} else {
				document.getElementById("W2TxtCell_lbl").innerHTML="";   
				signalError("W2TxtCell", false, true, true);
			}
		}  
		//Home
		if(jQuery('#W2Cbox_redsi2').is(':checked') )  
		{  
			document.getElementById("W2TxtHome_lbl").innerHTML="";   
			signalError("W2TxtHome", false, true, true);
		}
		else  
		{
			if (document.getElementsByName("W2TxtHome")[0].value.trim() == '(999)-999-9999' || document.getElementsByName("W2TxtHome")[0].value.trim() == '(111)-111-1111' || document.getElementsByName("W2TxtHome")[0].value.trim() == '' || document.getElementsByName("W2TxtHome")[0].value.trim() == '(___)-___-____') {   
				document.getElementById("W2TxtHome_lbl").innerHTML="Please Enter Home Phone #";
				signalError("W2TxtHome", true, true, true);
				mCheck = true;
				jQuery("#W2TxtHome").removeClass("ifocus");   
				//location.href = "#W2TxtHome";
			} else {
				document.getElementById("W2TxtHome_lbl").innerHTML="";
				signalError("W2TxtHome", false, true, true);
			}				
		}
		//City
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{  
			document.getElementById("W2TxtCity_lbl").innerHTML="";
			signalError("W2TxtCity", false, true, true);
		}
		else
		{
			if (document.getElementsByName("W2TxtCity")[0].value == '') {
				document.getElementById("W2TxtCity_lbl").innerHTML="Please Enter City";
				signalError("W2TxtCity", true, true, true);
				mCheck = true;
				//location.href = "#W2TxtCity";
			} else {
				document.getElementById("W2TxtCity_lbl").innerHTML="";
				signalError("W2TxtCity", false, true, true);
			}
		}

		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{  
			document.getElementById("W2DrpProvince_lbl").innerHTML="";
			signalError("W2DrpProvince", false, true, true);
		}
		else
		{
			//Province
			if (document.getElementsByName("W2DrpProvince")[0].value == "0") {	
				document.getElementById("W2DrpProvince_lbl").innerHTML="Please Select Province";
				signalError("W2DrpProvince", true, true, true);
				mCheck = true;
				//location.href = "#W2DrpProvince";
			} else {
				document.getElementById("W2DrpProvince_lbl").innerHTML="";
				signalError("W2DrpProvince", false, true, true);				
			}
		}



		//Postal code
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{  
			document.getElementById("W2TxtCode_lbl").innerHTML="";
			signalError("W2TxtCode", false, true, true);
		}
		else
		{
			if (document.getElementsByName("W2TxtCode")[0].value.trim()=='') {	
				document.getElementById("W2TxtCode_lbl").innerHTML="Please Enter Postal Code"; 
				signalError("W2TxtCode", true, true, true);
				mCheck = true;
				jQuery("#W2TxtCode").removeClass("ifocus"); 
				//location.href = "#W2TxtCode";
			} else {
				document.getElementById("W2TxtCode_lbl").innerHTML="";
				signalError("W2TxtCode", false, true, true);
			}
		}     
		//move to this address
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{  
			document.getElementById("W2TxtMove_lbl").innerHTML="";
			signalError("W2TxtMove", false, true, true);
			//location.href = "#W2TxtMove";
		}
		else
		{
			if (document.getElementsByName("W2TxtMove")[0].value == '') {
				document.getElementById("W2TxtMove_lbl").innerHTML="Please Enter Date ";
				signalError("W2TxtMove", true, true, true);
				mCheck = true;
				//location.href = "#W2TxtMove";
			} else {
				document.getElementById("W2TxtMove_lbl").innerHTML="";
				signalError("W2TxtMove", false, true, true);
			} 
		}

		//DOB
		if (document.getElementsByName("W2TxtDOB")[0].value == '') {
			document.getElementById("W2TxtDOB_lbl").innerHTML="Please Enter Date of Birth";
			signalError("W2TxtDOB", true, true, true);
			mCheck = true;
			//location.href = "#W2TxtDOB";
		} else {
			document.getElementById("W2TxtDOB_lbl").innerHTML="";
			signalError("W2TxtDOB", false, true, true);
		}

		//Social Insurance Number
		if(jQuery('#W2Cbox_redsi1').is(':checked') || jQuery('#W2Cbox_redsi2').is(':checked')  )
		{
			document.getElementById("W2TxtSIN_lbl").innerHTML="";
			signalError("W2TxtSIN", false, true, true);

		}
		else
		{
			if (document.getElementsByName("W2TxtSIN")[0].value == '') {
				document.getElementById("W2TxtSIN_lbl").innerHTML="Please Enter Social Insurance Number";
				signalError("W2TxtSIN", true, true, true);
				mCheck = true;
				jQuery("#W2TxtSIN").removeClass("ifocus");   
				//location.href = "#W2TxtSIN";
			} else {
				document.getElementById("W2TxtSIN_lbl").innerHTML="";
				signalError("W2TxtSIN", false, true, true);
			}
		}  

		//Relationship
		if (document.getElementsByName("W2DrpRelationship")[0].value == "0") {	
			document.getElementById("W2DrpRelationship_lbl").innerHTML="Please Select Relationship";
			signalError("W2DrpRelationship", true, true, true);
			mCheck = true;
			//location.href = "#W2DrpRelationship";
		} else {
			document.getElementById("W2DrpRelationship_lbl").innerHTML="";
			signalError("W2DrpRelationship", false, true, true);
		}
			
		//StreetN3Y	
		if(jQuery('#W2Cbox_redsi2').is(':checked'))
		{
			document.getElementById("W2TxtStreetN3Y_lbl").innerHTML="";
			signalError("W2TxtStreetN3Y", false, true, true);

		}  
		else
		{
			var start = new Date();
			var end   = jQuery('#W2TxtMove').val();
			var dayDiff = Math.ceil((start - end) / (1000 * 60 * 60 * 24));
			//alert (dayDiff);
			if(dayDiff<='1095')
			{
				if (document.getElementsByName("W2TxtStreetN3Y")[0].value == '') {
					document.getElementById("W2TxtStreetN3Y_lbl").innerHTML="Please Enter Street";
					signalError("W2TxtStreetN3Y", true, true, true);
					mCheck = true;
					//location.href = "#W2TxtStreetN3Y";
				} else {
					document.getElementById("W2TxtStreetN3Y_lbl").innerHTML="";
					signalError("W2TxtStreetN3Y", false, true, true);
				}
			}
		}

		//CityN3Y
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
			document.getElementById("W2TxtCityN3Y_lbl").innerHTML="";
			signalError("W2TxtCityN3Y", false, true, true);

		}  
		else
		{
			if(dayDiff<='1095')
			{
				if (document.getElementsByName("W2TxtCityN3Y")[0].value == '') {
					document.getElementById("W2TxtCityN3Y_lbl").innerHTML="Please Enter City";
					signalError("W2TxtCityN3Y", true, true, true);
					mCheck = true;
					//location.href = "#W2TxtCityN3Y";
				} else {
					document.getElementById("W2TxtCityN3Y_lbl").innerHTML="";
					signalError("W2TxtCityN3Y", false, true, true);
				}
			}
		}


		//ProvinceN3Y
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
			document.getElementById("W2DrpProvinceN3Y_lbl").innerHTML="";
			signalError("W2DrpProvinceN3Y", false, true, true);

		}  
		else
		{
			if(dayDiff<='1095')
			{

				if (document.getElementsByName("W2DrpProvinceN3Y")[0].value == "0") {	
					document.getElementById("W2DrpProvinceN3Y_lbl").innerHTML="Please Select Province";
					signalError("W2DrpProvinceN3Y", true, true, true);
					mCheck = true;
					//location.href = "#W2DrpProvinceN3Y";
				} else {
					document.getElementById("W2DrpProvinceN3Y_lbl").innerHTML="";
					signalError("W2DrpProvinceN3Y", false, true, true);
				}
			}
		}

		//move to this address1
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
			document.getElementById("W2TxtMoveN3Y_lbl").innerHTML="";
			signalError("W2TxtMoveN3Y", false, true, true);

		}  
		else
		{
			if(dayDiff<='1095')
			{
				if (document.getElementsByName("W2TxtMoveN3Y")[0].value == '') {
					document.getElementById("W2TxtMoveN3Y_lbl").innerHTML="Please Enter Date ";
					signalError("W2TxtMoveN3Y", true, true, true);
					mCheck = true;
					//location.href = "#W2TxtMoveN3Y";
				} 
				else  
				{ 
					document.getElementById("W2TxtMoveN3Y_lbl").innerHTML="";
					signalError("W2TxtMoveN3Y", false, true, true);
				}

			}
		}
		////



		//StreetN3Y1


		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
			document.getElementById("W2TxtStreetN3Y1_lbl").innerHTML="";
			signalError("W2TxtStreetN3Y1", false, true, true);

		}    
		else
		{

			if(dayDiff<='1095')
			{
				if(dayDiff+dayDiff1<='1095')
				{
					if (document.getElementsByName("W2TxtStreetN3Y1")[0].value == '') {
						document.getElementById("W2TxtStreetN3Y1_lbl").innerHTML="Please Enter Street";
						signalError("W2TxtStreetN3Y1", true, true, true);
						mCheck = true;
						//location.href = "#W2TxtStreetN3Y1";
					} else {
						document.getElementById("W2TxtStreetN3Y1_lbl").innerHTML="";
						signalError("W2TxtStreetN3Y1", false, true, true);
					}
				}
			}
		}

		//CityN3Y1
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
			document.getElementById("W2TxtCityN3Y1_lbl").innerHTML="";
			signalError("W2TxtCityN3Y1", false, true, true);

		}    
		else
		{	
			if(dayDiff<='1095')
			{
				if(dayDiff+dayDiff1<='1095')
				{
					if (document.getElementsByName("W2TxtCityN3Y1")[0].value == '') {
						document.getElementById("W2TxtCityN3Y1_lbl").innerHTML="Please Enter City";
						signalError("W2TxtCityN3Y1", true, true, true);
						mCheck = true;
						//location.href = "#W2TxtCityN3Y1";
					} else {
						document.getElementById("W2TxtCityN3Y1_lbl").innerHTML="";
						signalError("W2TxtCityN3Y1", false, true, true);
					}
				}
			}
		}
		//ProvinceN3Y1

		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
			document.getElementById("W2DrpProvinceN3Y1_lbl").innerHTML="";
			signalError("W2DrpProvinceN3Y1", false, true, true);

		}    
		else
		{
			if(dayDiff<='1095')
			{
				if(dayDiff+dayDiff1<='1095')
				{

					if (document.getElementsByName("W2DrpProvinceN3Y1")[0].value == "0") {	
						document.getElementById("W2DrpProvinceN3Y1_lbl").innerHTML="Please Select Province";
						signalError("W2DrpProvinceN3Y1", true, true, true);
						mCheck = true;
						//location.href = "#W2DrpProvinceN3Y1";
					} else {
						document.getElementById("W2DrpProvinceN3Y1_lbl").innerHTML="";
						signalError("W2DrpProvinceN3Y1", false, true, true);
					}
				}
			}				
		}

		
		//move to this address2
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
			document.getElementById("W2TxtMoveN3Y2_lbl").innerHTML="";
			signalError("W2TxtMoveN3Y2", false, true, true);

		}    
		else
		{

			if(dayDiff<='1095')
			{
				if(dayDiff+dayDiff1<='1095')
				{
					if (document.getElementsByName("W2TxtMoveN3Y2")[0].value == '') {
						document.getElementById("W2TxtMoveN3Y2_lbl").innerHTML="Please Enter Date ";
						signalError("W2TxtMoveN3Y2", true, true, true);
						//location.href = "#W2TxtMoveN3Y2";
						mCheck = true;
					} 
					else  
					{ 
						document.getElementById("W2TxtMoveN3Y2_lbl").innerHTML="";
						signalError("W2TxtMoveN3Y2", false, true, true);
					}
				}
			}
		}

		////////////////endddddddd

		//StreetN3Y2

		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
		document.getElementById("W2TxtStreetN3Y2_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y2", false, true, true);

		}    
		else
		{
		if(dayDiff<='1095')
		{
		if(dayDiff+dayDiff1<='1095')
		{

		if(dayDiff+dayDiff1+dayDiff2<='1095')
		{
		if (document.getElementsByName("W2TxtStreetN3Y2")[0].value == '') {
		document.getElementById("W2TxtStreetN3Y2_lbl").innerHTML="Please Enter Street";
		signalError("W2TxtStreetN3Y2", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtStreetN3Y2";
		} else {
		document.getElementById("W2TxtStreetN3Y2_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y2", false, true, true);
		}
		}
		}
		}
		}
		//CityN3Y2
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
		document.getElementById("W2TxtCityN3Y2_lbl").innerHTML="";
		signalError("W2TxtCityN3Y2", false, true, true);

		}    
		else
		{

		if(dayDiff<='1095')
		{
		if(dayDiff+dayDiff1<='1095')
		{

		if(dayDiff+dayDiff1+dayDiff2<='1095')
		{
		if (document.getElementsByName("W2TxtCityN3Y2")[0].value == '') {
		document.getElementById("W2TxtCityN3Y2_lbl").innerHTML="Please Enter City";
		signalError("W2TxtCityN3Y2", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtCityN3Y2";
		} else {
		document.getElementById("W2TxtCityN3Y2_lbl").innerHTML="";
		signalError("W2TxtCityN3Y2", false, true, true);
		}
		}
		}
		}

		}
		//ProvinceN3Y2
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
		document.getElementById("W2DrpProvinceN3Y2_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y2", false, true, true);

		}    
		else
		{

		if(dayDiff<='1095')
		{
		if(dayDiff+dayDiff1<='1095')
		{

		if(dayDiff+dayDiff1+dayDiff2<='1095')
		{

		if (document.getElementsByName("W2DrpProvinceN3Y2")[0].value == "0") {	
		document.getElementById("W2DrpProvinceN3Y2_lbl").innerHTML="Please Select Province";
		signalError("W2DrpProvinceN3Y2", true, true, true);
		mCheck = true;
		//location.href = "#W2DrpProvinceN3Y2";
		} 
		else {
		document.getElementById("W2DrpProvinceN3Y2_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y2", false, true, true);
		}
		}
		}
		}
		}

		
		//move to this address2
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
		document.getElementById("W2TxtMoveN3Y3_lbl").innerHTML="";
		signalError("W2TxtMoveN3Y3", false, true, true);

		}    
		else  
		{

		if(dayDiff<='1095')
		{
		if(dayDiff+dayDiff1<='1095')
		{

		if(dayDiff+dayDiff1+dayDiff2<='1095')
		{
		if (document.getElementsByName("W2TxtMoveN3Y3")[0].value == '') {
		document.getElementById("W2TxtMoveN3Y3_lbl").innerHTML="Please Enter Date ";
		signalError("W2TxtMoveN3Y3", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtMoveN3Y3";
		} 
		else    
		{ 
		document.getElementById("W2TxtMoveN3Y3_lbl").innerHTML="";
		signalError("W2TxtMoveN3Y3", false, true, true);
		}
		}}
		}
		}


		//enddddd2
		//StreetN3Y3	


		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
		document.getElementById("W2TxtStreetN3Y3_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y3", false, true, true);

		}    
		else
		{

		if(dayDiff<='1095')
		{
		if(dayDiff+dayDiff1<='1095')
		{
		if(dayDiff+dayDiff1+dayDiff2<='1095')
		{
		if(dayDiff+dayDiff1+dayDiff2+dayDiff3<='1095')
		{
		if (document.getElementsByName("W2TxtStreetN3Y3")[0].value == '') {
		document.getElementById("W2TxtStreetN3Y3_lbl").innerHTML="Please Enter Street";
		signalError("W2TxtStreetN3Y3", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtStreetN3Y3";
		} else {
		document.getElementById("W2TxtStreetN3Y3_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y3", false, true, true);
		}
		}

		}}}
		}
		//CityN3Y1
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
		document.getElementById("W2TxtCityN3Y3_lbl").innerHTML="";
		signalError("W2TxtCityN3Y3", false, true, true);

		}    
		else
		{

		if(dayDiff<='1095')
		{
		if(dayDiff+dayDiff1<='1095')
		{
		if(dayDiff+dayDiff1+dayDiff2<='1095')
		{
		if(dayDiff+dayDiff1+dayDiff2+dayDiff3<='1095')
		{
		if (document.getElementsByName("W2TxtCityN3Y3")[0].value == '') {
		document.getElementById("W2TxtCityN3Y3_lbl").innerHTML="Please Enter City";
		signalError("W2TxtCityN3Y3", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtCityN3Y3";
		} else {
		document.getElementById("W2TxtCityN3Y3_lbl").innerHTML="";
		signalError("W2TxtCityN3Y3", false, true, true);
		}
		}

		}}}
		}
		//ProvinceN3Y3
		if(jQuery('#W2Cbox_redsi2').is(':checked') )
		{
		document.getElementById("W2DrpProvinceN3Y3_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y3", false, true, true);

		}    
		else
		{
		if(dayDiff<='1095')
		{
		if(dayDiff+dayDiff1<='1095')
		{
		if(dayDiff+dayDiff1+dayDiff2<='1095')
		{
		if(dayDiff+dayDiff1+dayDiff2+dayDiff3<='1095')
		{

		if (document.getElementsByName("W2DrpProvinceN3Y3")[0].value == "0") {	
		document.getElementById("W2DrpProvinceN3Y3_lbl").innerHTML="Please Select Province";
		signalError("W2DrpProvinceN3Y3", true, true, true);
		mCheck = true;
		//location.href = "#W2DrpProvinceN3Y3";
		} else {
		document.getElementById("W2DrpProvinceN3Y3_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y3", false, true, true);
		}
		}

		}}}
		}
		
		////endddd


		//Second Co-Applicant
		if (jQuery('#W2SecondApp1').is(':checked')) 
		{			


		// FIRST NAME
		if (document.getElementsByName("W2TxtFirstNsmeSec")[0].value == '') {
		document.getElementById("W2TxtFirstNsmeSec_lbl").innerHTML="Please Enter First Name";
		signalError("W2TxtFirstNsmeSec", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtFirstNsmeSec";
		} else {
		document.getElementById("W2TxtFirstNsmeSec_lbl").innerHTML="";
		signalError("W2TxtFirstNsmeSec", false, true, true);
		}

		// LAST NAME

		if (document.getElementsByName("W2TxtLastNameSec")[0].value == '' ) {
		document.getElementById("W2TxtLastNameSec_lbl").innerHTML="Please Enter Last Name";
		signalError("W2TxtLastNameSec", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtLastNameSec";
		} else {
		document.getElementById("W2TxtLastNameSec_lbl").innerHTML="";
		signalError("W2TxtLastNameSec", false, true, true);
		}

		//Email
		var email = document.getElementsByName("W2TxtEmailSec")[0].value.trim(); 				
		if (document.getElementsByName("W2TxtEmailSec")[0].value == '') {
		document.getElementById("W2TxtEmailSec_lbl").innerHTML="Please Enter Email";
		signalError("W2TxtEmailSec", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtEmailSec";
		}
		else if(!isValidEmailAddress(email))
		{
		document.getElementById("W2TxtEmailSec_lbl1").innerHTML="Please Enter Valid Email";
		signalError("W2TxtEmailSec", true, true, true);
		mCheck = true;
		}
		else {
		document.getElementById("W2TxtEmailSec_lbl").innerHTML="";
		document.getElementById("W2TxtEmailSec_lbl1").innerHTML="";
		signalError("W2TxtEmailSec", false, true, true);
		}

		//Street
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2TxtStreetSec_lbl").innerHTML="";
		signalError("W2TxtStreetSec", false, true, true);

		}    
		else
		{  
		if (document.getElementsByName("W2TxtStreetSec")[0].value == '') {
		document.getElementById("W2TxtStreetSec_lbl").innerHTML="Please Enter Street";
		signalError("W2TxtStreetSec", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtStreetSec";
		} else {
		document.getElementById("W2TxtStreetSec_lbl").innerHTML="";
		signalError("W2TxtStreetSec", false, true, true);
		}
		}


		//Cell
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') )
		{
		document.getElementById("W2TxtCellSec_lbl").innerHTML="";
		signalError("W2TxtCellSec", false, true, true);

		}    
		else
		{  

		if (document.getElementsByName("W2TxtCellSec")[0].value == '') {
		document.getElementById("W2TxtCellSec_lbl").innerHTML="Please Enter Cell Phone #";
		signalError("W2TxtCellSec", true, true, true);
		mCheck = true;
		jQuery("#W2TxtCellSec").removeClass("ifocus");  
		//location.href = "#W2TxtCellSec";
		} else {
		document.getElementById("W2TxtCellSec_lbl").innerHTML="";
		signalError("W2TxtCellSec", false, true, true);
		}

		}
		//Home

		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2TxtHomeSec_lbl").innerHTML="";
		signalError("W2TxtHomeSec", false, true, true);

		}    
		else
		{  
		if (document.getElementsByName("W2TxtHomeSec")[0].value == '') {
		document.getElementById("W2TxtHomeSec_lbl").innerHTML="Please Enter Home Phone #";
		signalError("W2TxtHomeSec", true, true, true);
		mCheck = true;
		jQuery("#W2TxtHomeSec").removeClass("ifocus"); 
		//location.href = "#W2TxtHomeSec";
		} else {
		document.getElementById("W2TxtHomeSec_lbl").innerHTML="";
		signalError("W2TxtHomeSec", false, true, true);
		}
		}


		//City

		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2TxtCitySec_lbl").innerHTML="";
		signalError("W2TxtCitySec", false, true, true);

		} 
		else{
		if (document.getElementsByName("W2TxtCitySec")[0].value == '') {
		document.getElementById("W2TxtCitySec_lbl").innerHTML="Please Enter City";
		signalError("W2TxtCitySec", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtCitySec";
		} else {
		document.getElementById("W2TxtCitySec_lbl").innerHTML="";
		signalError("W2TxtCitySec", false, true, true);
		}
		}

		//Province


		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2DrpProvinceSec_lbl").innerHTML="";
		signalError("W2DrpProvinceSec", false, true, true);

		}  
		else{
		if (document.getElementsByName("W2DrpProvinceSec")[0].value == "0") {	
		document.getElementById("W2DrpProvinceSec_lbl").innerHTML="Please Select Province";
		signalError("W2DrpProvinceSec", true, true, true);
		mCheck = true;
		//location.href = "#W2DrpProvinceSec";
		} else {
		document.getElementById("W2DrpProvinceSec_lbl").innerHTML="";
		signalError("W2DrpProvinceSec", false, true, true);
		}


		}
		//Postal code

		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2TxtCodeSec_lbl").innerHTML="";
		//signalError("W2TxtCodeSec", false, true, true);

		}  
		else{
		if (document.getElementsByName("W2TxtCodeSec")[0].value =='') {	
		document.getElementById("W2TxtCodeSec_lbl").innerHTML="Please Enter Postal Code"; 
		signalError("W2TxtCodeSec", true, true, true);
		mCheck = true;
		jQuery("#W2TxtCodeSec").removeClass("ifocus");       
		//location.href = "#W2TxtCodeSec";
		} 
		else 
		{       
		document.getElementById("W2TxtCodeSec_lbl").innerHTML="";
		signalError("W2TxtCodeSec", false, true, true);
		} 
		}


		//move to this address
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2TxtMoveSecapp_lbl").innerHTML="";
		signalError("W2TxtMoveSecapp", false, true, true);

		}  
		else{		
		if (document.getElementsByName("W2TxtMoveSecapp")[0].value == '') {
		document.getElementById("W2TxtMoveSecapp_lbl").innerHTML="Please Enter Date ";
		signalError("W2TxtMoveSecapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtMoveSecapp";
		} else {
		document.getElementById("W2TxtMoveSecapp_lbl").innerHTML="";
		signalError("W2TxtMoveSecapp", false, true, true);
		} 

		}
		//DOB
		if (document.getElementsByName("W2TxtDOBSec")[0].value == '') {
		document.getElementById("W2TxtDOBSec_lbl").innerHTML="Please Enter Date of Birth";
		signalError("W2TxtDOBSec", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtDOBSec";
		} else {
		document.getElementById("W2TxtDOBSec_lbl").innerHTML="";
		signalError("W2TxtDOBSec", false, true, true);
		}

		//Social Insurance Number
		if(jQuery('#W2Cbox_redsi1Sec').is(':checked') ||jQuery('#W2Cbox_redsi2').is(':checked') ) 
		{ 
		document.getElementById("W2TxtSINSec_lbl").innerHTML="";
		signalError("W2TxtSINSec", false, true, true);

		}

		else{
		if (document.getElementsByName("W2TxtSINSec")[0].value == '') {
		document.getElementById("W2TxtSINSec_lbl").innerHTML="Please Enter Social Insurance Number";
		signalError("W2TxtSINSec", true, true, true);
		mCheck = true;  
		jQuery("#W2TxtSINSec").removeClass("ifocus");
		//location.href = "#W2TxtSINSec";
		} else {
		document.getElementById("W2TxtSINSec_lbl").innerHTML="";
		signalError("W2TxtSINSec", false, true, true);
		}

		}



		//Relationship
		//StreetN3Y	
		if(dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{  
		document.getElementById("W2TxtStreetN3YSecapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3YSecapp", false, true, true);
		}
		else
		{
		if (document.getElementsByName("W2DrpRelationshipSec")[0].value == "0") {	
		document.getElementById("W2DrpRelationshipSec_lbl").innerHTML="Please Select Relationship";
		signalError("W2DrpRelationshipSec", true, true, true);
		mCheck = true;
		//location.href = "#W2DrpRelationshipSec";
		} else {
		document.getElementById("W2DrpRelationshipSec_lbl").innerHTML="";
		signalError("W2DrpRelationshipSec", false, true, true);
		}	
		}
		//StreetN3Y	
		if(jQuery('#W2Cbox_redsi2').is(':checked')|| dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2TxtStreetN3YSecapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3YSecapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if (document.getElementsByName("W2TxtStreetN3YSecapp")[0].value == '') {
		document.getElementById("W2TxtStreetN3YSecapp_lbl").innerHTML="Please Enter Street";
		signalError("W2TxtStreetN3YSecapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtStreetN3YSecapp";
		} else {
		document.getElementById("W2TxtStreetN3YSecapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3YSecapp", false, true, true);
		}
		}
		}
		//CityN3Y
		if(jQuery('#W2Cbox_redsi2').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2TxtCityN3YSecapp_lbl").innerHTML="";
		signalError("W2TxtCityN3YSecapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if (document.getElementsByName("W2TxtCityN3YSecapp")[0].value == '') {
		document.getElementById("W2TxtCityN3YSecapp_lbl").innerHTML="Please Enter City";
		signalError("W2TxtCityN3YSecapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtCityN3YSecapp";
		} else {
		document.getElementById("W2TxtCityN3YSecapp_lbl").innerHTML="";
		signalError("W2TxtCityN3YSecapp", false, true, true);
		}
		}
		}

		//ProvinceN3Y
		if(jQuery('#W2Cbox_redsi2').is(':checked')|| dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2DrpProvinceN3YSecapp_lbl").innerHTML="";
		signalError("W2DrpProvinceN3YSecapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{

		if (document.getElementsByName("W2DrpProvinceN3YSecapp")[0].value == "0") {	
		document.getElementById("W2DrpProvinceN3YSecapp_lbl").innerHTML="Please Select Province";
		signalError("W2DrpProvinceN3YSecapp", true, true, true);
		mCheck = true;
		//location.href = "#W2DrpProvinceN3YSecapp";
		} else {
		document.getElementById("W2DrpProvinceN3YSecapp_lbl").innerHTML="";
		signalError("W2DrpProvinceN3YSecapp", false, true, true);
		}
		}
		}

		

		//move to this address1
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2TxtMoveN3YSecapp_lbl").innerHTML="";
		signalError("W2TxtMoveN3YSecapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if (document.getElementsByName("W2TxtMoveN3YSecapp")[0].value == '') {
		document.getElementById("W2TxtMoveN3YSecapp_lbl").innerHTML="Please Enter Date ";
		signalError("W2TxtMoveN3YSecapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtMoveN3YSecapp";
		} 
		else  
		{ 
		document.getElementById("W2TxtMoveN3YSecapp_lbl").innerHTML="";
		signalError("W2TxtMoveN3YSecapp", false, true, true);
		}

		}
		}
		////



		//StreetN3Y1
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2TxtStreetN3Y1Secapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y1Secapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{	
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{
		if (document.getElementsByName("W2TxtStreetN3Y1Secapp")[0].value == '') {
		document.getElementById("W2TxtStreetN3Y1Secapp_lbl").innerHTML="Please Enter Street";
		signalError("W2TxtStreetN3Y1Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtStreetN3Y1Secapp";
		} else {
		document.getElementById("W2TxtStreetN3Y1Secapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y1Secapp", false, true, true);
		}
		}
		}
		}

		//CityN3Y1
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked')  || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2TxtCityN3Y1Secapp_lbl").innerHTML="";
		signalError("W2TxtCityN3Y1Secapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{
		if (document.getElementsByName("W2TxtCityN3Y1Secapp")[0].value == '') {
		document.getElementById("W2TxtCityN3Y1Secapp_lbl").innerHTML="Please Enter City";
		signalError("W2TxtCityN3Y1Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtCityN3Y1Secapp";
		} else {
		document.getElementById("W2TxtCityN3Y1Secapp_lbl").innerHTML="";
		signalError("W2TxtCityN3Y1Secapp", false, true, true);
		}
		}
		}
		}
		//ProvinceN3Y1
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked')  || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2DrpProvinceN3Y1Secapp_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y1Secapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{

		if (document.getElementsByName("W2DrpProvinceN3Y1Secapp")[0].value == "0") {	
		document.getElementById("W2DrpProvinceN3Y1Secapp_lbl").innerHTML="Please Select Province";
		signalError("W2DrpProvinceN3Y1Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2DrpProvinceN3Y1Secapp";
		} else {
		document.getElementById("W2DrpProvinceN3Y1Secapp_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y1Secapp", false, true, true);
		}
		}
		}
		}
		
		//move to this address2
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2TxtMoveN3Y2Secapp_lbl").innerHTML="";
		signalError("W2TxtMoveN3Y2Secapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{
		if (document.getElementsByName("W2TxtMoveN3Y2Secapp")[0].value == '') {
		document.getElementById("W2TxtMoveN3Y2Secapp_lbl").innerHTML="Please Enter Date ";
		signalError("W2TxtMoveN3Y2Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtMoveN3Y2Secapp";
		} 
		else  
		{ 
		document.getElementById("W2TxtMoveN3Y2Secapp_lbl").innerHTML="";
		signalError("W2TxtMoveN3Y2Secapp", false, true, true);
		}

		}
		}
		}

		///// endddd

		//StreetN3Y2
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2TxtStreetN3Y2Secapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y2Secapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{


		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp<='1095')
		{
		if (document.getElementsByName("W2TxtStreetN3Y2Secapp")[0].value == '') {
		document.getElementById("W2TxtStreetN3Y2Secapp_lbl").innerHTML="Please Enter Street";
		signalError("W2TxtStreetN3Y2Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtStreetN3Y2Secapp";
		} else {
		document.getElementById("W2TxtStreetN3Y2Secapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y2Secapp", false, true, true);
		}
		}
		}
		}
		}

		//CityN3Y2
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2TxtCityN3Y2Secapp_lbl").innerHTML="";
		signalError("W2TxtCityN3Y2Secapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{

		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp<='1095')
		{
		if (document.getElementsByName("W2TxtCityN3Y2Secapp")[0].value == '') {
		document.getElementById("W2TxtCityN3Y2Secapp_lbl").innerHTML="Please Enter City";
		signalError("W2TxtCityN3Y2Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtCityN3Y2Secapp";
		} else {
		document.getElementById("W2TxtCityN3Y2Secapp_lbl").innerHTML="";
		signalError("W2TxtCityN3Y2Secapp", false, true, true);
		}
		}
		}}
		}
		//ProvinceN3Y2
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked')|| dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2DrpProvinceN3Y2Secapp_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y2Secapp", false, true, true);

		}  
		else{

		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{

		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp<='1095')
		{

		if (document.getElementsByName("W2DrpProvinceN3Y2Secapp")[0].value == "0") {	
		document.getElementById("W2DrpProvinceN3Y2Secapp_lbl").innerHTML="Please Select Province";
		signalError("W2DrpProvinceN3Y2Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2DrpProvinceN3Y2Secapp";
		} else {
		document.getElementById("W2DrpProvinceN3Y2Secapp_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y2Secapp", false, true, true);
		}

		}
		}
		}
		}			
		

		//move to this address2
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2TxtMoveN3Y3Secapp_lbl").innerHTML="";
		signalError("W2TxtMoveN3Y3Secapp", false, true, true);

		}  
		else{
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp<='1095')
		{
		if (document.getElementsByName("W2TxtMoveN3Y3Secapp")[0].value == '') {
		document.getElementById("W2TxtMoveN3Y3Secapp_lbl").innerHTML="Please Enter Date ";
		signalError("W2TxtMoveN3Y3Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtMoveN3Y3Secapp";
		} 
		else    
		{ 
		document.getElementById("W2TxtMoveN3Y3Secapp_lbl").innerHTML="";
		signalError("W2TxtMoveN3Y3Secapp", false, true, true);
		}}
		}
		}  
		}
		// endddd
		//StreetN3Y3
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law'  )
		{
		document.getElementById("W2TxtStreetN3Y3Secapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y2Secapp", false, true, true);

		}  
		else{	
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp+dayDiff3Secapp<='1095')
		{
		if (document.getElementsByName("W2TxtStreetN3Y3Secapp")[0].value == '') {
		document.getElementById("W2TxtStreetN3Y3Secapp_lbl").innerHTML="Please Enter Street";
		signalError("W2TxtStreetN3Y3Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtStreetN3Y3Secapp";
		} else {
		document.getElementById("W2TxtStreetN3Y3Secapp_lbl").innerHTML="";
		signalError("W2TxtStreetN3Y2Secapp", false, true, true);
		}
		}
		}	
		}
		}
		}	
		//CityN3Y1
		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2TxtCityN3Y3Secapp_lbl").innerHTML="";
		signalError("W2TxtCityN3Y1Secapp", false, true, true);

		}  
		else{	
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp+dayDiff3Secapp<='1095')
		{
		if (document.getElementsByName("W2TxtCityN3Y3Secapp")[0].value == '') {
		document.getElementById("W2TxtCityN3Y3Secapp_lbl").innerHTML="Please Enter City";
		signalError("W2TxtCityN3Y1Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2TxtCityN3Y1Secapp";
		} else {
		document.getElementById("W2TxtCityN3Y3Secapp_lbl").innerHTML="";
		signalError("W2TxtCityN3Y3Secapp", false, true, true);
		}
		}
		}}}
		}

		//ProvinceN3Y3

		if(jQuery('#W2Cbox_redsi2Sec').is(':checked') || dd_relationship=='Married' || dd_relationship=='Common_Law' )
		{
		document.getElementById("W2DrpProvinceN3Y3Secapp_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y3Secapp", false, true, true);

		}  
		else{				
		if(dayDiffSecapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp<='1095')
		{
		if(dayDiffSecapp+dayDiff1Secapp+dayDiff2Secapp+dayDiff3Secapp<='1095')
		{

		if (document.getElementsByName("W2DrpProvinceN3Y3Secapp")[0].value == "0") {	
		document.getElementById("W2DrpProvinceN3Y3Secapp_lbl").innerHTML="Please Select Province";
		signalError("W2DrpProvinceN3Y3Secapp", true, true, true);
		mCheck = true;
		//location.href = "#W2DrpProvinceN3Y3Secapp";
		} else { 
		document.getElementById("W2DrpProvinceN3Y3Secapp_lbl").innerHTML="";
		signalError("W2DrpProvinceN3Y3Secapp", false, true, true);
		}

		}}}}
		}
		
		}

		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;




		}
		break;

		case 1:					
		// Applicant's Signature

		// AJAX FOR THE CSV FILE


		// APPLICANT DATA //
		jQuery(function($) {

		name = $('#W2TxtFirstNsme').val();
		lastName = $('#W2TxtLastName').val();
		email = $('#W2TxtEmail').val();
		cell = $('#W2TxtCell').val();
		home = $('#W2TxtHome').val();
		work = $('#W2TxtWork').val();
		address = $('#W2TxtStreet').val();
		city = $('#W2TxtCity').val();
		postal_code = $('#W2TxtCode').val();
		moveDate = $('#W2TxtMove').val();
		dob = $('#W2TxtDOB').val();
		sin = $('#W2TxtSIN').val();

		//SECONDAPPLICANT

		name2 = $('#W2TxtFirstNsmeSec').val();
		lastName2 = $('#W2TxtLastNameSec').val();
		email2 = $('#W2TxtEmailSec').val();
		cell2 = $('#W2TxtCellSec').val();
		home2 = $('#W2TxtHomeSec').val();
		work2 = $('#W2TxtWorkSec').val();
		address2 = $('#W2TxtStreetSec').val();
		city2 = $('#W2TxtCitySec').val();
		postal_code2 = $('#W2TxtCodeSec').val();
		moveDate2 = $('#W2TxtMoveSecapp').val();
		dob2 = $('#W2TxtDOBSec').val();


		///////////////////




		$.ajax({
		type: "POST",
		url: "saveCsv.php",
		data: { name: name,
		lastname: lastName,
		email: email,
		cell: cell,
		home: home,
		work: work,
		address: address,
		city: city,
		postalcode: postal_code,
		movedate: moveDate,
		dob: dob,
		sin: sin,
		//
		name2: name2,
		lastname2: lastName2,
		email2: email2,
		cell2: cell2,
		home2: home2,
		work2: work2,
		address2: address2,
		city2: city2,
		postalcode2: postal_code2,
		movedate2: moveDate2,
		dob2: dob2,
		}
		})
		.done(function( msg ) {
		//alert( "Data Saved: " + msg );
		});
		});

		//

		//
		jQuery('#customField').val('new');
		if (document.getElementsByName("W0TxtAppSign")[0].value == "") {
		signalError("W0TxtAppSign", true, true, true);
		mCheck = true;
		} else {
		signalError("W0TxtAppSign", false, true, true);
		}
		jQuery(function($) {
		var selectVal2=$('#W2TxtFirstNsme').val().toLowerCase();
		var trimmedval=selectVal2.replace(/\s/g, '');   

		var selectVal2last=$('#W2TxtLastName').val().toLowerCase();
		var trimmedval1=selectVal2last.replace(/\s/g, '');

		var sign=trimmedval+" "+trimmedval1;    
		var selectVal2a=$('#W0TxtAppSign').val().toLowerCase();

		if(selectVal2a!=sign)       
		{
		$(alert("Signature Should be same as FirstName& LastName"));
		mCheck = true;
		}
		});

		if (jQuery('#W2SecondApp1').is(':checked')) 
		{
		// Co-Applicant's Signature
		if (document.getElementsByName("W0TxtCoAppSign")[0].value == "") {
		signalError("W0TxtCoAppSign", true, true, true);
		mCheck = true;
		} else {
		signalError("W0TxtCoAppSign", false, true, true);
		}

		jQuery(function($) {  
		var selectVal2r=$('#W2TxtFirstNsmeSec').val().toLowerCase();;
		var trimmedval=selectVal2r.replace(/\s/g, ''); 
		var selectVal2rlast=$('#W2TxtLastNameSec').val().toLowerCase();; 
		var trimmedval1=selectVal2rlast.replace(/\s/g, ''); 
		var signsec=trimmedval+" "+trimmedval1;  
		var selectVal2ar=$('#W0TxtCoAppSign').val().toLowerCase();;
		if(selectVal2ar!=signsec)
		{ 
		$(alert("Co-Applicant's Signature Should be same as FirstName& LastName"));
		mCheck = true;
		}
		});
		}

		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}




		break;				
		case 2:
		jQuery('#customField').val('');
		//current Lending Goal?
		var radio = jQuery('input:radio[name="W3Goal"]:checked');
		if (radio.length == 0) {
		radioError("W3Goal_lbl", true);
		mCheck = true;
		} else {
		radioError("W3Goal_lbl", false);
		window.scrollTo(0,0); 
		}							 

		//Check Condition 
		if (jQuery('#W3Goal1').is(':checked')) {

		var radio = jQuery('input:radio[name="W3Goal1Look"]:checked');
		if (radio.length == 0) {
		radioError("W3Goal1Look_lbl", true);
		mCheck = true;
		//location.href = "#W3Goal1Look2";
		} else {
		radioError("W3Goal1Look_lbl", false);
		window.scrollTo(0,0); 
		}					
		}

		if (jQuery('#W3Goal2').is(':checked')) {

		//I'm looking for a:
		var radio = jQuery('input:radio[name="W3Goal2Look"]:checked');
		if (radio.length == 0) {
		radioError("W3Goal2Look_lbl", true);
		mCheck = true;
		//location.href = "#W3Goal2Look2";
		} else {
		radioError("W3Goal2Look_lbl", false);
		window.scrollTo(0,0); 
		}	

		//When do you require funds for construction?
		if (jQuery('#W3Goal2Look1').is(':checked')) 
		{
		var radio = jQuery('input:radio[name="W3Goal2Funds"]:checked');
		if (radio.length == 0) {
		radioError("W3Goal2Funds_lbl", true);
		mCheck = true;
		//location.href = "#W3Goal2Funds1";
		} else {
		radioError("W3Goal2Funds_lbl", false);
		}

		//I'm Building a:
		var radio = jQuery('input:radio[name="W3Goal2Building"]:checked');
		if (radio.length == 0) {
		radioError("W3Goal2Building_lbl", true);
		mCheck = true;
		//location.href = "#W3Goal2Building2";
		} else {
		radioError("W3Goal2Building_lbl", false);
		}
		}
		else
		{
		//I'm Purchasing a:
		var radio = jQuery('input:radio[name="W3Goal2Building"]:checked');
		if (radio.length == 0) {
		radioError("W3Goal2Building_lbl", true);
		mCheck = true;
		} else {
		radioError("W3Goal2Building_lbl", false);
		}
		}
		}
		////

		if (jQuery('#W3Goal2Funds2').is(':checked') || jQuery('#W3Goal2Funds3').is(':checked'))

		{


		if (document.getElementsByName("W2DrpADbuilder")[0].value == "0") 
		{	
		document.getElementById("W2DrpADbuilder_lbl").innerHTML="Please Select Advances";
		signalError("W2DrpADbuilder", true, true, true);
		mCheck = true;     
		//location.href = "#W2DrpADbuilder";
		}       
		else
		{
		document.getElementById("W2DrpADbuilder_lbl").innerHTML="";
		signalError("W2DrpADbuilder", false, true, true);
		}

		//	jQuery("#W2DrpADbuilder").addClass("shadow");
		//mCheck=true;

		}



		///

		if (jQuery('#W3Goal3').is(':checked')) {

		//I am looking to:
		var radio = jQuery('input:radio[name="W3Goal3Look"]:checked');
		if (radio.length == 0) {
		radioError("W3Goal3Look_lbl", true);
		mCheck = true;
		} else {
		radioError("W3Goal3Look_lbl", false);
		window.scrollTo(0,0);    
		}

		//I'm renewing / refinancing a:
		var radio = jQuery('input:radio[name="W3Goal3Renewing"]:checked');
		if (radio.length == 0) {
		radioError("W3Goal3Renewing_lbl", true);
		mCheck = true;
		} else {
		radioError("W3Goal3Renewing_lbl", false);
		}
		}				

		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}
		break;

		case 3:	
		//alert(sval);
		jQuery('#customField').val('new');
		if(sval=='1')
		{	
		//Case 6 LoanDetails
		var radios=jQuery('[name="W6PreADPaymentFrom"]');
		for (var i = 0, length = radios.length; i < length; i++) {
		if (radios[i].checked) {
		if(radios[i].value =='6')
		{	
		if (document.getElementsByName("W6TxtPreADescribePaymentFrom")[0].value == '') 
		{
		document.getElementById("W6TxtPreADescribePaymentFrom_lbl").innerHTML="Please Enter Description";
		signalError("W6TxtPreADescribePaymentFrom", true, true, true);
		mCheck = true;
		} else {
		document.getElementById("W6TxtPreADescribePaymentFrom_lbl").innerHTML="";
		signalError("W6TxtPreADescribePaymentFrom", false, true, true);
		}
		}
		}
		}   
		if(document.getElementById("W3Goal1Look2").checked==true 
		|| document.getElementById("W3Goal1Look3").checked==true 
		|| document.getElementById("W3Goal1Look4").checked==true
		|| document.getElementById("W3Goal1Look5").checked==true 
		|| document.getElementById("W3Goal1Look6").checked==true 
		|| document.getElementById("W3Goal1Look7").checked==true
		|| document.getElementById("W3Goal1Look1").checked==true) 
		{
			if (document.getElementsByName("W6TxtPreAPrice")[0].value == '')
			{
				document.getElementById("W6TxtPreAPrice_lbl").innerHTML="Please Enter value";
				signalError("W6TxtPreAPrice", true, true, true);
				mCheck = true;
				// location.href="#W6TxtPreAPrice";
			} else 
			{
				document.getElementById("W6TxtPreAPrice_lbl").innerHTML="";
				signalError("W6TxtPreAPrice", false, true, true);
			}
		}
		
		//
		if(document.getElementById("W3Goal1Look2").checked==true 
		|| document.getElementById("W3Goal1Look3").checked==true 
		|| document.getElementById("W3Goal1Look4").checked==true 
		|| document.getElementById("W3Goal1Look5").checked==true 
		|| document.getElementById("W3Goal1Look6").checked==true
		|| document.getElementById("W3Goal1Look7").checked==true 
		|| document.getElementById("W3Goal1Look1").checked==true)
		{
		if (document.getElementsByName("W4TxtNewCity55pre")[0].value == '')
		{
		document.getElementById("W4TxtNewCity55pre_lbl").innerHTML="Please Enter city";
		signalError("W4TxtNewCity55pre", true, true, true);
		mCheck = true;
		//location.href="#W4TxtNewCity55pre";

		}
		else 
		{
		document.getElementById("W4TxtNewCity55pre_lbl").innerHTML="";
		signalError("W4TxtNewCity55pre", false, true, true);
		window.scrollTo(0,0);   
		}
		}
		//

		//
		if(document.getElementById("W3Goal1Look2").checked==true || document.getElementById("W3Goal1Look3").checked==true || document.getElementById("W3Goal1Look4").checked==true || document.getElementById("W3Goal1Look5").checked==true || document.getElementById("W3Goal1Look6").checked==true|| document.getElementById("W3Goal1Look7").checked==true || document.getElementById("W3Goal1Look1").checked==true)   
		{   
		if (document.getElementsByName("W6TxtPreADownPayment")[0].value =='') {
		document.getElementById("W6TxtPreADownPayment_lbl").innerHTML="Please Enter down payment";
		signalError("W6TxtPreADownPayment", true, true, true);  
		mCheck = true;
		//location.href="#W6TxtPreADownPayment";      

		} else {
		document.getElementById("W6TxtPreADownPayment_lbl").innerHTML="";
		signalError("W6TxtPreADownPayment", false, true, true);
		}
		}	
		//
		if(document.getElementById("W3Goal1Look2").checked==true || document.getElementById("W3Goal1Look3").checked==true || document.getElementById("W3Goal1Look4").checked==true || document.getElementById("W3Goal1Look5").checked==true || document.getElementById("W3Goal1Look6").checked==true || document.getElementById("W3Goal1Look7").checked==true || document.getElementById("W3Goal1Look1").checked==true)       
		{
		if (document.getElementsByName("W4DrpNewProvince55pre")[0].value =="0") {
		document.getElementById("W4DrpNewProvince55pre_lbl").innerHTML="Please Enter province";
		signalError("W4DrpNewProvince55pre", true, true, true);  
		mCheck = true;  
		jQuery("#W4DrpNewProvince55pre").removeClass('ifocus');
		// location.href="#W4DrpNewProvince55pre";

		} else {
		document.getElementById("W4DrpNewProvince55pre_lbl").innerHTML="";
		signalError("W4DrpNewProvince55pre", false, true, true);
		}

		if(jQuery('#W6PreADPaymentFrom1').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom2').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom3').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom4').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom5').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom6').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom7').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom8').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom9').is(':checked') 
		|| jQuery('#W6PreADPaymentFrom10').is(':checked') 
		)   
		{
		radioError("W6PreADPaymentFrom1_lbl", false);
		radioError("W6PreADPaymentFrom2_lbl", false);
		radioError("W6PreADPaymentFrom3_lbl", false);
		radioError("W6PreADPaymentFrom4_lbl", false);
		radioError("W6PreADPaymentFrom5_lbl", false);
		radioError("W6PreADPaymentFrom6_lbl", false);
		radioError("W6PreADPaymentFrom7_lbl", false);
		radioError("W6PreADPaymentFrom8_lbl", false);
		radioError("W6PreADPaymentFrom9_lbl", false);
		radioError("W6PreADPaymentFrom10_lbl", false);
		}


		else
		{

		var radio = jQuery('input:radio[name="W6PreADPaymentFrom1"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom1_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom1_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom2"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom2_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom2_lbl", false);
		}		
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom3"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom3_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom3_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom4"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom4_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom4_lbl", false);
		}		
		//
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom5"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom5_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom5_lbl", false);
		}		
		//
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom6"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom6_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom6_lbl", false);
		}		
		//
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom7"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom7_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom7_lbl", false);
		}		
		//
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom8"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom8_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom8_lbl", false);
		}		
		//
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom9"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom9_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PreADPaymentFrom9_lbl", false);
		}		
		//
		//
		var radio = jQuery('input:radio[name="W6PreADPaymentFrom10"]:checked');
		if (radio.length == 0) {
		radioError("W6PreADPaymentFrom10_lbl", true);
		mCheck = true;  
		} else {
		radioError("W6PreADPaymentFrom10_lbl", false);
		}		
		//
		}
		// end
		
		if(jQuery('#W6PreADPaymentFrom1').is(':checked'))   
		{    
		//
		if (document.getElementsByName("bank_amount1")[0].value == '') 
		{
		document.getElementById("bank_amount1_lbl").innerHTML="Please Enter Bank Account Chequing/Savings";
		signalError("bank_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.bank_amount').offset().top});
		} 
		else
		{
		document.getElementById("bank_amount1_lbl").innerHTML="";
		signalError("bank_amount1", false, true, true);
		}
		}
		//
		if(jQuery('#W6PreADPaymentFrom2').is(':checked'))   
		{ 
		if (document.getElementsByName("RRSPs_amount1")[0].value == '') 
		{
		document.getElementById("RRSPs_amount1_lbl").innerHTML="Please Enter RRSPs or Investments";
		signalError("RRSPs_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.RRSPs_amount').offset().top});
		} 
		else
		{
		document.getElementById("RRSPs_amount1_lbl").innerHTML="";
		signalError("RRSPs_amount1", false, true, true);
		}
		}
		//
		if(jQuery('#W6PreADPaymentFrom3').is(':checked'))   
		{ 
		if (document.getElementsByName("borrowed_amount1")[0].value == '') 
		{
		document.getElementById("borrowed_amount1_lbl").innerHTML="Please Enter Borrowed(e.g LOC)";
		signalError("borrowed_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.borrowed_amount').offset().top});
		} 
		else
		{
		document.getElementById("borrowed_amount1_lbl").innerHTML="";
		signalError("borrowed_amount1", false, true, true);
		}
		}
		//
		if(jQuery('#W6PreADPaymentFrom4').is(':checked'))   
		{ 
		if (document.getElementsByName("sale_amount1")[0].value == '') 
		{
		document.getElementById("sale_amount1_lbl").innerHTML="Please Enter Sale of Asset";
		signalError("sale_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.sale_amount').offset().top});
		} 
		else
		{
		document.getElementById("sale_amount1_lbl").innerHTML="";
		signalError("sale_amount1", false, true, true);
		}
		}
		//
		if(jQuery('#W6PreADPaymentFrom5').is(':checked'))   
		{ 
		if (document.getElementsByName("gift_amount1")[0].value == '') 
		{
		document.getElementById("gift_amount1_lbl").innerHTML="Please Enter Gift Amount";
		signalError("gift_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.gift_amount').offset().top});
		} 
		else
		{
		document.getElementById("gift_amount1_lbl").innerHTML="";
		signalError("gift_amount1", false, true, true);
		}
		}

		//
		if(jQuery('#W6PreADPaymentFrom7').is(':checked'))   
		{ 
		if (document.getElementsByName("cash_amount1")[0].value == '') 
		{
		document.getElementById("cash_amount1_lbl").innerHTML="Please Enter Personal Cash";
		signalError("cash_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.cash_amount').offset().top});
		} 
		else
		{
		document.getElementById("cash_amount1_lbl").innerHTML="";
		signalError("cash_amount1", false, true, true);
		}
		}
		//
		if(jQuery('#W6PreADPaymentFrom9').is(':checked'))   
		{ 
		if (document.getElementsByName("equity_amount1")[0].value == '') 
		{
		document.getElementById("equity_amount1_lbl").innerHTML="Please Enter Existing Equity";
		signalError("equity_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.equity_amount').offset().top});
		} 
		else
		{
		document.getElementById("equity_amount1_lbl").innerHTML="";
		signalError("equity_amount1", false, true, true);
		}	
		}
		//    

		if(jQuery('#W6PreADPaymentFrom8').is(':checked'))   
		{ 
		if (document.getElementsByName("sweatequity_amount1")[0].value == '') 
		{
		document.getElementById("sweatequity_amount1_lbl").innerHTML="Please Enter Sweat Equity";
		signalError("sweatequity_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.sweatequity_amount').offset().top});
		} 
		else   
		{
		document.getElementById("sweatequity_amount1_lbl").innerHTML="";
		signalError("sweatequity_amount1", false, true, true);
		}   

		}
		//
		if(jQuery('#W6PreADPaymentFrom10').is(':checked'))   
		{ 
		if (document.getElementsByName("finance_amount1")[0].value == '') 
		{
		document.getElementById("finance_amount1_lbl").innerHTML="Please Enter Secondary Financing";
		signalError("finance_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.finance_amount').offset().top});
		} 
		else   
		{
		document.getElementById("finance_amount1_lbl").innerHTML="";
		signalError("finance_amount1", false, true, true);
		}   

		}
		
		//
		if(jQuery('#W6PreADPaymentFrom6').is(':checked'))   
		{ 
		if (document.getElementsByName("other_amount1")[0].value == '') 
		{
		document.getElementById("other_amount1_lbl").innerHTML="Please Enter Other Amount";
		signalError("other_amount1", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.other_amount').offset().top});
		} 
		else   
		{
		document.getElementById("other_amount1_lbl").innerHTML="";
		signalError("other_amount1", false, true, true);
		}   

		}
		
		}




		//

		}   

		else
		{
		jQuery('#customField').val("");     
		// Street
		if(jQuery('#W2Cbox_redsi2').is(':checked') )  
		{   
		document.getElementById("W4TxtStreet_lbl").innerHTML="";
		signalError("W4TxtStreet", false, true, true);

		}
		else
		{
		if (document.getElementsByName("W4TxtStreet")[0].value == '') {
		document.getElementById("W4TxtStreet_lbl").innerHTML="Please Enter Street";
		signalError("W4TxtStreet", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W4TxtStreet').offset().top});
		} else {
		document.getElementById("W4TxtStreet_lbl").innerHTML="";
		signalError("W4TxtStreet", false, true, true);
		window.scrollTo(0,0);  
		}
		}
		// City
		if(jQuery('#W2Cbox_redsi2').is(':checked') )  
		{
		document.getElementById("W4TxtCity_lbl").innerHTML="";
		signalError("W4TxtCity", false, true, true);

		}
		else
		{
		if (document.getElementsByName("W4TxtCity")[0].value == '') {
		document.getElementById("W4TxtCity_lbl").innerHTML="Please Enter City";
		signalError("W4TxtCity", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W4TxtCity').offset().top});
		} else {
		document.getElementById("W4TxtCity_lbl").innerHTML="";
		signalError("W4TxtCity", false, true, true);
		window.scrollTo(0,0);  
		}
		}
		

		if(jQuery('.W4PropGREYOUT').is(':checked') )
		{  
		//$(alert("clicked"));  
		window.scrollTo(0,0);    
		}

		if(jQuery('#W2Cbox_redsi2').is(':checked') )  
		{  
		document.getElementById("W4TxtProvince_lbl").innerHTML="";
		signalError("W4TxtProvince", false, true, true);

		}  
		else
		{	
		if (document.getElementsByName("W4TxtProvince")[0].value.trim() == "0") {
		document.getElementById("W4TxtProvince_lbl").innerHTML="Please Select Province";
		signalError("W4TxtProvince", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W4TxtProvince').offset().top});
		} else {
		document.getElementById("W4TxtProvince_lbl").innerHTML="";
		signalError("W4TxtProvince", false, true, true);
		}


		}					
		if( jQuery('#W4Cbox_AddressNo').is(':checked')) 
		{	
		// Street
		if(jQuery('#W2Cbox_redsi2').is(':checked') )  
		{  
		document.getElementById("W4TxtNewStreet_lbl").innerHTML="";
		signalError("W4TxtNewStreet", false, true, true);

		}
		else
		{
		if (document.getElementsByName("W4TxtNewStreet")[0].value == '') {
		document.getElementById("W4TxtNewStreet_lbl").innerHTML="Please Enter Street";
		signalError("W4TxtNewStreet", true, true, true);
		mCheck = true;   

		} else {
		document.getElementById("W4TxtNewStreet_lbl").innerHTML="";
		signalError("W4TxtNewStreet", false, true, true);
		}}

		// City
		if(jQuery('#W2Cbox_redsi2').is(':checked') )  
		{  
		document.getElementById("W4TxtNewCity_lbl").innerHTML="";
		signalError("W4TxtNewCity", false, true, true);

		}
		else
		{
		if (document.getElementsByName("W4TxtNewCity")[0].value == '') {
		document.getElementById("W4TxtNewCity_lbl").innerHTML="Please Enter City";
		signalError("W4TxtNewCity", true, true, true);
		mCheck = true;
		} else {
		document.getElementById("W4TxtNewCity_lbl").innerHTML="";
		signalError("W4TxtNewCity", false, true, true);
		}
		}

		//Province
		if(jQuery('#W2Cbox_redsi2').is(':checked') )  
		{  
		document.getElementById("W4DrpNewProvince_lbl").innerHTML="";
		signalError("W4DrpNewProvince", false, true, true);

		}
		else
		{

		if (document.getElementsByName("W4DrpNewProvince")[0].value == "0") {	
		document.getElementById("W4DrpNewProvince_lbl").innerHTML="Please Select Province";
		signalError("W4DrpNewProvince", true, true, true);
		mCheck = true;
		} else {
		document.getElementById("W4DrpNewProvince_lbl").innerHTML="";
		signalError("W4DrpNewProvince", false, true, true);
		}
		}

		} 




		var radio = jQuery('input:radio[name="W4Address"]:checked');
		if (radio.length == 0) {
		radioError("W4Address_lbl", true);
		mCheck = true;
		// location.href="#W4Cbox_AddressYes";   
		jQuery('html,body').animate({scrollTop: jQuery('#W4Cbox_AddressYes').offset().top});
		} 
		else 
		{
		radioError("W4Address_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W4Prop"]:checked');
		if (radio.length == 0) {
		radioError("W4Prop_lbl", true);
		mCheck = true;
		// location.href="#W4Prp1";
		jQuery('html,body').animate({scrollTop: jQuery('#W4Prp1').offset().top});
		} 
		else 
		{
		radioError("W4Prop_lbl", false);
		}

		//
		var radio = jQuery('input:radio[name="W4BType"]:checked');
		if (radio.length == 0) {
		radioError("W4BType_lbl", true);
		mCheck = true;
		//location.href="#W4BType2";
		jQuery('html,body').animate({scrollTop: jQuery('#W4BType2').offset().top});
		} 
		else 
		{
		radioError("W4BType_lbl", false);
		}	
		//

		if(jQuery('#W4BType4').is(':checked') ) 
		{
		var radio = jQuery('input:radio[name="W4CondoType"]:checked');
		if (radio.length == 0) {   
		radioError("W4CondoType_lbl", true);
		mCheck = true;
		//location.href="#W4CondoType2";
		jQuery('html,body').animate({scrollTop: jQuery('#W4CondoType2').offset().top});
		} 
		else 
		{
		radioError("W4CondoType_lbl", false);
		}

		}

		}

		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}
		break;

		case 4:

		if(sval=='1')   
		{  

		if(jQuery(".W6ConsWhoLivinggreyout").is(':checked'))    
		{

		window.scrollTo(0,0);  

		}

		if(jQuery('#W3Goal1Look2').is(':checked')
		|| jQuery('#W3Goal1Look3').is(':checked') 
		|| jQuery('#W3Goal1Look4').is(':checked')
		|| jQuery('#W3Goal1Look5').is(':checked')
		|| jQuery('#W3Goal1Look6').is(':checked')  
		|| jQuery('#W3Goal1Look7').is(':checked') 
		|| jQuery('#W3Goal1Look1').is(':checked'))  
		{

		jQuery('#customField').val('');  



		var radio = jQuery('input:radio[name="W6ConsWhoLiving"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsWhoLiving_lbl", true);
		mCheck = true;
		// location.href="#W6ConsWhoLiving1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsWhoLiving1').offset().top});
		} else {  
		radioError("W6ConsWhoLiving_lbl", false);
		}
		////
		if(jQuery('#W6ConsWhoLiving2').is(':checked'))
		{
		var radio = jQuery('input:radio[name="W6ConsPayForHeat"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsPayForHeat_lbl", true);
		mCheck = true;
		// location.href="#W6ConsPayForHeat1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsPayForHeat1').offset().top});
		} else {  
		radioError("W6ConsPayForHeat_lbl", false);
		}
		}
		//

		//
		if(jQuery('#W6ConsWhoLiving3').is(':checked') ) 
		{
		var radio = jQuery('input:radio[name="W6ConsLegalSuite"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsLegalSuite_lbl", true);
		mCheck = true;
		// location.href="#W6ConsLegalSuite1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsLegalSuite1').offset().top});
		} else {  
		radioError("W6ConsLegalSuite_lbl", false);
		}
		}					 


		//
		var radio = jQuery('input:radio[name="W6ConsMortgageType"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsMortgageType_lbl", true);
		mCheck = true;
		// location.href="#W6ConsMortgageType1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsMortgageType1').offset().top});
		} else {  
		radioError("W6ConsMortgageType_lbl", false);
		}
		//



		if(jQuery('#W6ConsMortgageType2').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsVariableMortgage"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsVariableMortgage_lbl", true);
		mCheck = true;
		// location.href="#W6ConsVariableMortgage1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsVariableMortgage1').offset().top});
		} else {  
		radioError("W6ConsVariableMortgage_lbl", false);
		}
		}
		//


		if(jQuery('#W6ConsMortgageType1').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsTermLength"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsTermLength_lbl", true);
		mCheck = true;
		// location.href="#W6ConsTermLength1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsTermLength1').offset().top});
		} else {  
		radioError("W6ConsTermLength_lbl", false);
		}
		}	    
		//


		if(jQuery('#W6ConsTermLength1').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsBestOption"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsBestOption_lbl", true);
		mCheck = true;
		// location.href="#W6ConsBestOption1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsBestOption1').offset().top});
		} else {     
		radioError("W6ConsBestOption_lbl", false);
		}
		}

		//						
		if(jQuery('#W6ConsMortgageType3').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsCashbackTerm"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsCashbackTerm_lbl", true);
		mCheck = true;
		// location.href="#W6ConsCashbackTerm1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsCashbackTerm1').offset().top});
		} else {  
		radioError("W6ConsCashbackTerm_lbl", false);
		}
		}


		//
		if(jQuery('#W6ConsVariableMortgage1').is(':checked') || jQuery('#W6ConsVariableMortgage2').is(':checked')) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsFixedTerm"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsFixedTerm_lbl", true);
		mCheck = true;
		// location.href="#W6ConsFixedTerm1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsFixedTerm1').offset().top});
		} else {  
		radioError("W6ConsFixedTerm_lbl", false);
		}
		}


		// 

		if(jQuery('#W6ConsVariableMortgage3').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsVariableTerm"]:checked');
		if (radio.length == 0) {  
		radioError("W6ConsVariableTerm_lbl", true);
		mCheck = true;
		// location.href="#W6ConsVariableTerm1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsVariableTerm1').offset().top});
		} else {  
		radioError("W6ConsVariableTerm_lbl", false);
		}
		}



		//


		var radio = jQuery('input:radio[name="W6ConsPaymentFrequency"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsPaymentFrequency_lbl", true);
		mCheck = true;
		// location.href="#W6ConsPaymentFrequency1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsPaymentFrequency1').offset().top});
		} else {  
		radioError("W6ConsPaymentFrequency_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6ConsAmortizationFor"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsAmortizationFor_lbl", true);
		mCheck = true;
		//location.href="#W6ConsAmortizationFor1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsAmortizationFor1').offset().top});
		} else {  
		radioError("W6ConsAmortizationFor_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6Agree1"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree1_lbl", true);
		mCheck = true;
		//location.href="#W6Agree1_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree1_5').offset().top});
		} else {  
		radioError("W6Agree1_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6Agree2"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree2_lbl", true);
		mCheck = true;
		// location.href="#W6Agree2_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree2_5').offset().top});
		} else {  
		radioError("W6Agree2_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6Agree3"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree3_lbl", true);
		mCheck = true;
		// location.href="#W6Agree3_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree3_5').offset().top});
		} else {  
		radioError("W6Agree3_lbl", false);
		}		
		//
		var radio = jQuery('input:radio[name="W6Agree4"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree4_lbl", true);
		mCheck = true;
		// location.href="#W6Agree4_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree4_5').offset().top});
		} else {  
		radioError("W6Agree4_lbl", false);
		}	
		//
		var radio = jQuery('input:radio[name="W6Agree5"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree5_lbl", true);
		mCheck = true;
		//location.href="#W6Agree5_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree5_5').offset().top});
		} else {  
		radioError("W6Agree5_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6Agree6"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree6_lbl", true);
		mCheck = true;
		// location.href="#W6Agree6_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree6_5').offset().top});
		} else {  
		radioError("W6Agree6_lbl", false);  
		}  
		//
		var radio = jQuery('input:radio[name="W6Agree7"]:checked');
		if (radio.length == 0) {   
		radioError("W6Agree7_lbl", true);
		mCheck = true;
		//location.href="#W6Agree7_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree7_5').offset().top});
		} else {  
		radioError("W6Agree7_lbl", false);  
		}     					         

		}
		}				
		else
		{  

		jQuery('#customField').val("");           

		// How old is your property?
		if (document.getElementsByName("W5TxtYears")[0].value == '') {
		document.getElementById("W5TxtYears_lbl").innerHTML="Please Enter Years";
		signalError("W5TxtYears", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtYears";
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtYears').offset().top});
		} else {
		document.getElementById("W5TxtYears_lbl").innerHTML="";
		signalError("W5TxtYears", false, true, true);
		window.scrollTo(0,0);  
		}

		// What is the square footage of your property (above ground)?
		if (document.getElementsByName("W5TxtSpace")[0].value == '') {
		document.getElementById("W5TxtSpace_lbl").innerHTML="Please Enter Square Footage";
		signalError("W5TxtSpace", true, true, true);
		mCheck = true;   
		//location.href = "#W5TxtSpace";						
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtSpace').offset().top});
		} else {
		document.getElementById("W5TxtSpace_lbl").innerHTML="";
		signalError("W5TxtSpace", false, true, true);
		window.scrollTo(0,0);  
		}

		// How much are the Annual Property Taxes?
		if (document.getElementsByName("W5TxtTaxes")[0].value == '') {
		document.getElementById("W5TxtTaxes_lbl").innerHTML="Please Enter Taxes";
		signalError("W5TxtTaxes", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtTaxes";						
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtTaxes').offset().top});
		} else {
		document.getElementById("W5TxtTaxes_lbl").innerHTML="";
		signalError("W5TxtTaxes", false, true, true);
		window.scrollTo(0,0);  
		}
		//

		if (document.getElementById("W4BType6").checked == true)    
		{ 



		if (document.getElementsByName("W5TxtRentalFee")[0].value == '') {
		document.getElementById("W5TxtRentalFee_lbl").innerHTML="Please Enter Lot Rental Fees";
		signalError("W5TxtRentalFee", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtCondoFee";		   
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtRentalFee').offset().top});
		} else 
		{
		document.getElementById("W5TxtRentalFee_lbl").innerHTML="";
		signalError("W5TxtRentalFee", false, true, true);
		window.scrollTo(0,0);  
		}
		}









		//

		//How much are the monthly Condo Fees?
		if (document.getElementById("W3Goal2Look1").checked == true) 
		{ 
		if (document.getElementById("W3Goal2Building1").checked == true)  
		{ 


		if (document.getElementsByName("W5TxtCondoFee")[0].value == '') {
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="Please Enter condo fees";
		signalError("W5TxtCondoFee", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtCondoFee";		
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});
		} else 
		{
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="";
		signalError("W5TxtCondoFee", false, true, true);
		window.scrollTo(0,0);  
		}
		}
		}
		//How much are the monthly Condo Fees?
		if (document.getElementById("W3Goal3Look1").checked == true) 
		{ 
		if (document.getElementById("W3Goal3Renewing1").checked == true)  
		{ 


		if (document.getElementsByName("W5TxtCondoFee")[0].value == '') {
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="Please Enter condo fees";
		signalError("W5TxtCondoFee", true, true, true);
		mCheck = true;
		//	location.href = "#W5TxtCondoFee";
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});
		} else 
		{
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="";
		signalError("W5TxtCondoFee", false, true, true);
		window.scrollTo(0,0);  
		}
		}
		}
		//How much are the monthly Condo Fees?
		if (document.getElementById("W3Goal3Look2").checked == true) 
		{ 
		if (document.getElementById("W3Goal3Renewing1").checked == true)  
		{ 


		if (document.getElementsByName("W5TxtCondoFee")[0].value == '') {
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="Please Enter condo fees";
		signalError("W5TxtCondoFee", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtCondoFee";
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});
		} else 
		{
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="";
		signalError("W5TxtCondoFee", false, true, true);
		window.scrollTo(0,0);  
		}
		}
		}
		//How much are the monthly Condo Fees?
		if (document.getElementById("W3Goal3Look3").checked == true) 
		{ 
		if (document.getElementById("W3Goal3Renewing1").checked == true)  
		{ 


		if (document.getElementsByName("W5TxtCondoFee")[0].value == '') {
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="Please Enter condo fees";
		signalError("W5TxtCondoFee", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtCondoFee";
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});
		} else 
		{
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="";
		signalError("W5TxtCondoFee", false, true, true);
		window.scrollTo(0,0);  
		}
		}
		}
		//How much are the monthly Condo Fees?
		if (document.getElementById("W3Goal3Look4").checked == true) 
		{ 
		if (document.getElementById("W3Goal3Renewing1").checked == true)  
		{ 


		if (document.getElementsByName("W5TxtCondoFee")[0].value == '') {
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="Please Enter condo fees";
		signalError("W5TxtCondoFee", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtCondoFee";
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});
		} else 
		{
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="";
		signalError("W5TxtCondoFee", false, true, true);
		window.scrollTo(0,0);  
		}
		}
		}
		if (document.getElementById("W3Goal2Look3").checked == true)  
		{ 

		if (document.getElementById("W3Goal2Building1").checked == true)  
		{ 

		if (document.getElementsByName("W5TxtCondoFee")[0].value == '') {
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="Please Enter condo fees";
		signalError("W5TxtCondoFee", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtCondoFee";
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});

		} else 
		{
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="";
		signalError("W5TxtCondoFee", false, true, true);
		window.scrollTo(0,0);  
		} 
		}    
		}
		////
		if (document.getElementById("W3Goal2Look2").checked == true )
		{
		if (document.getElementById("W3Goal2Building1").checked == true)  
		{    

		//jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});        
		}
		}
		///  
		if (document.getElementById("W3Goal2Look2").checked == true)  
		{ 
		if (document.getElementById("W3Goal2Building1").checked == true)  
		{ 

		if (document.getElementsByName("W5TxtCondoFee")[0].value == '') {
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="Please Enter condo fees";
		signalError("W5TxtCondoFee", true, true, true);
		mCheck = true; 
		//	location.href = "#W5TxtCondoFee";
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});						

		} else 
		{
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="";
		signalError("W5TxtCondoFee", false, true, true);
		window.scrollTo(0,0);  
		}
		}
		}
		////
		if (document.getElementById("W3Goal2Look4").checked == true)   
		{  
		if (document.getElementById("W3Goal2Building1").checked == true)  
		{ 

		if (document.getElementsByName("W5TxtCondoFee")[0].value == '') {
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="Please Enter condo fees";
		signalError("W5TxtCondoFee", true, true, true);
		mCheck = true;
		//location.href = "#W5TxtCondoFee";
		jQuery('html,body').animate({scrollTop: jQuery('#W5TxtCondoFee').offset().top});
		} else 
		{
		document.getElementById("W5TxtCondoFee_lbl").innerHTML="";
		signalError("W5TxtCondoFee", false, true, true);
		window.scrollTo(0,0);  
		}
		}   
		}  
		//

		// What is the main way your property is heated?
		var radio = jQuery('input:radio[name="W5HeatedProp"]:checked');
		if (radio.length == 0) {
		radioError("W5HeatedProp_lbl", true);      
		mCheck = true;
		//location.href = "#W5HeatedPrp1";
		jQuery('html,body').animate({scrollTop: jQuery('#W5HeatedPrp1').offset().top});
		} else {
		radioError("W5HeatedProp_lbl", false);
		window.scrollTo(0,0);  
		}
		//Where does the property get the water?  
		var radio = jQuery('input:radio[name="W5WaterProp"]:checked');
		if (radio.length == 0) {  
		radioError("W5WaterProp_lbl", true);
		mCheck = true;
		//location.href = "#W5WaterPrp1";
		jQuery('html,body').animate({scrollTop: jQuery('#W5WaterPrp1').offset().top});
		} else {   
		radioError("W5WaterProp_lbl", false);
		window.scrollTo(0,0);  
		}
		//How does the property dispose of sewage and waste water?
		var radio = jQuery('input:radio[name="W5WasteProp"]:checked');
		if (radio.length == 0) {    
		radioError("W5WasteProp_lbl", true);
		mCheck = true;
		//location.href = "#W5WastePrp1";
		jQuery('html,body').animate({scrollTop: jQuery('#W5WastePrp1').offset().top});
		} else {   
		radioError("W5WasteProp_lbl", false);
		window.scrollTo(0,0);  
		}	   
		//What type of garage does your property have?

		var radio = jQuery('input:radio[name="W5GarageProp"]:checked');
		if (radio.length == 0) {    
		radioError("W5GarageProp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W5GaragePrp1').offset().top});
		//location.href = "#W5GaragePrp1";
		} else {   
		radioError("W5GarageProp_lbl", false);
		window.scrollTo(0,0);  
		}	
		//What size is your garage?
		if (jQuery('#W5GaragePrp1').is(':checked') || jQuery('#W5GaragePrp2').is(':checked')) 
		{

		var radio = jQuery('input:radio[name="W5GSizeProp"]:checked');
		if (radio.length == 0) {    
		radioError("W5GSizeProp_lbl", true);
		mCheck = true;    
		//	location.href = "#W5GSizePrp3";
		jQuery('html,body').animate({scrollTop: jQuery('#W5GSizePrp3').offset().top});
		} else {   
		radioError("W5GSizeProp_lbl", false);
		window.scrollTo(0,0);  
		}
		}
		//Does your property have additional out buildings?
		var radio = jQuery('input:radio[name="W5AddBulProp"]:checked');  
		if (radio.length == 0) {      
		radioError("W5AddBulProp_lbl",true);    
		mCheck = true;     
		//location.href = "#W5Add_Bul_Prp1";
		jQuery('html,body').animate({scrollTop: jQuery('#W5Add_Bul_Prp1').offset().top});				
		} else {     
		radioError("W5AddBulProp_lbl",false);
		window.scrollTo(0,0);  
		}

		////         

		}			
		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}
		break;

		case 5:

		if(sval=='1')   
		{
		jQuery('#customField').val(""); 
		//Case 8 EmploymentDetails
		///////////////////////////////////////////////////////////8 th screen 80%/////////////////
		if(jQuery("#W3Goal1Look1").is(':checked') 
		|| jQuery("#W3Goal1Look2").is(':checked') 
		|| jQuery("#W3Goal1Look3").is(':checked')
		|| jQuery("#W3Goal1Look4").is(':checked')
		|| jQuery("#W3Goal1Look5").is(':checked')
		|| jQuery("#W3Goal1Look6").is(':checked')
		|| jQuery("#W3Goal1Look7").is(':checked')   
		)
		{
		if(jQuery("#W7IncomeEarned1").is(':checked') || jQuery("#W7IncomeEarned2").is(':checked') || jQuery("#W7IncomeEarned4").is(':checked'))        
		{
		radioError("W7IncomeEarned_lbl", false);
		radioError("W7IncomeEarned2_lbl", false);
		radioError("W7IncomeEarned4_lbl", false);

		}
		else
		{




		var radio = jQuery('input:radio[name="W7IncomeEarned"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarned_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarned1').offset().top});				
		}
		else
		{   
		radioError("W7IncomeEarned_lbl", false);
		}
		//alert('testing');  
		var radio = jQuery('input:radio[name="W7IncomeEarned2"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarned2_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarned2').offset().top});				
		}
		else
		{   
		radioError("W7IncomeEarned2_lbl", false);
		}
		var radio = jQuery('input:radio[name="W7IncomeEarned4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarned4_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarned4').offset().top});				
		}
		else      
		{   
		radioError("W7IncomeEarned4_lbl", false);
		}  
		}
		//
		if (document.getElementById("W7IncomeEarned1").checked == true)  
		{  
		var radio = jQuery('input:radio[name="W7HowPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7HowPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowPaid1').offset().top});					
		}
		else
		{   
		radioError("W7HowPaid_lbl", false);
		}

		//
		if(document.getElementById("W7HowPaid2").checked == true || document.getElementById("W7HowPaid5").checked == true)
		{
		var radio = jQuery('input:radio[name="W7OverTimePaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OverTimePaid_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7OverTimePaid1').offset().top});							
		}
		else
		{   
		radioError("W7OverTimePaid_lbl", false);
		}
		}


		//

		// 1ST APPLICANT SALARY BUTTON CHECK

		if(document.getElementById("W7HowPaid1").checked == true
		|| document.getElementById("W7HowPaid6").checked == true
		|| document.getElementById("W7HowPaid4").checked == true)
		{
		var radio = jQuery('input:radio[name="W7FrequentlyPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7FrequentlyPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7FrequentlyPaid1').offset().top});							
		}
		else
		{   
		radioError("W7FrequentlyPaid_lbl", false);
		}
		}

		// var radio = jQuery('input:radio[name="W7HowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7HowLongCJob_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJob1').offset().top});					
		// }
		// else
		// {   
		// radioError("W7HowLongCJob_lbl", false);
		// }

		//

		var radio = jQuery('input:radio[name="W7IndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IndustryType1').offset().top});					
		}
		else
		{   
		radioError("W7IndustryType_lbl", false);
		}

		//

		var radio = jQuery('input:radio[name="W7Considered"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7Considered_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7Considered1').offset().top});						
		}
		else
		{   
		radioError("W7Considered_lbl", false);
		}

		//////////////////////
		if (document.getElementsByName("W7TxtJobTitle")[0].value == '') 
		{
		alert("blank");
		document.getElementById("W7TxtJobTitle_lbl").innerHTML="Please Enter job title";
		signalError("W7TxtJobTitle", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtJobTitle').offset().top});
		} else {
		document.getElementById("W7TxtJobTitle_lbl").innerHTML="";
		signalError("W7TxtJobTitle", false, true, true);
		}	
		if (document.getElementsByName("W7TxtCompName")[0].value == '') 
		{
		document.getElementById("W7TxtCompName_lbl").innerHTML="Please Enter company name";
		signalError("W7TxtCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtCompName').offset().top});
		} else {
		document.getElementById("W7TxtCompName_lbl").innerHTML="";
		signalError("W7TxtCompName", false, true, true);
		}	

		//////////////////

		var radio = jQuery('input:radio[name="W7ChildSupport"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ChildSupport_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupport1').offset().top});					
		}
		else
		{   
		radioError("W7ChildSupport_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7OtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7OtherIncome1').offset().top});						
		}
		else
		{   
		radioError("W7OtherIncome_lbl", false);
		}

		var radio = jQuery('input:radio[name="W72Sourcesofincome"]:checked');
		if (radio.length == 0)    
		{        
			radioError("W72Sourcesofincome_lbl", true);
			mCheck = true; 
			jQuery('html,body').animate({scrollTop: jQuery('#W72Sourcesofincome1').offset().top});						
		}
		else    
		{   
			radioError("W72Sourcesofincome_lbl", false);
		}

		//
		//supplementay questions for employed 
		if(document.getElementById("W72Sourcesofincome1").checked == true)
		{
		//
		if(document.getElementById("W72typeofincome1").checked == true
		|| document.getElementById("W72typeofincome2").checked == true
		|| document.getElementById("W72typeofincome3").checked == true
		|| document.getElementById("W72typeofincome4").checked == true
		//|| document.getElementById("W72typeofincome5").checked == true
		|| document.getElementById("W72typeofincome6").checked == true
		|| document.getElementById("W72typeofincome7").checked == true
		|| document.getElementById("W72typeofincome8").checked == true
		)
		{
		radioError("W72typeofincome1_lbl", false);
		radioError("W72typeofincome2_lbl", false);
		radioError("W72typeofincome3_lbl", false);
		radioError("W72typeofincome4_lbl", false);
		//radioError("W72typeofincome5_lbl", false);
		radioError("W72typeofincome6_lbl", false);
		radioError("W72typeofincome7_lbl", false);
		radioError("W72typeofincome8_lbl", false);
		}
		else
		{


		//1
		var radio = jQuery('input:radio[name="W72typeofincome1"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome1_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome1').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome1_lbl", false);
		}
		//2
		var radio = jQuery('input:radio[name="W72typeofincome2"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome2_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome2').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome2_lbl", false);
		}
		//3
		var radio = jQuery('input:radio[name="W72typeofincome3"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome3_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome3').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome3_lbl", false);
		}
		//4
		var radio = jQuery('input:radio[name="W72typeofincome4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome4_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome4').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome4_lbl", false);
		}
		//5
		var radio = jQuery('input:radio[name="W72typeofincome5"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome5_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome5').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome5_lbl", false);
		}
		//6
		var radio = jQuery('input:radio[name="W72typeofincome6"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome6_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome6').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome6_lbl", false);
		}
		//7
		var radio = jQuery('input:radio[name="W72typeofincome7"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome7_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome7').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome7_lbl", false);
		}
		//8
		var radio = jQuery('input:radio[name="W72typeofincome8"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome8_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome8').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome8_lbl", false);
		}
		}//end else

		//

		if(jQuery('#W72typeofincome2').is(':checked'))   
		{    
		//interest
		if (document.getElementsByName("type_Interest")[0].value == '') 
		{
		document.getElementById("type_Interest_lbl").innerHTML="Please Enter ANNUAL Interest income ";
		signalError("type_Interest", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_Interest').offset().top});
		} 
		else
		{
		document.getElementById("type_Interest_lbl").innerHTML="";
		signalError("type_Interest", false, true, true);
		}
		}
		//Pension
		if(jQuery('#W72typeofincome3').is(':checked'))   
		{ 
		if (document.getElementsByName("type_Pension")[0].value == '') 
		{
		document.getElementById("type_Pension_lbl").innerHTML="Please Enter ANNUAL Pension income ";
		signalError("type_Pension", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_Pension').offset().top});
		} 
		else
		{
		document.getElementById("type_Pension_lbl").innerHTML="";
		signalError("type_Pension", false, true, true);
		}
		}
		//Rental
		if(jQuery('#W72typeofincome5').is(':checked'))   
		{ 
		if (document.getElementsByName("type_Rental")[0].value == '') 
		{
		document.getElementById("type_Rental_lbl").innerHTML="Please Enter ANNUAL Rental income ";
		signalError("type_Rental", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_Rental').offset().top});
		} 
		else
		{
		document.getElementById("type_Rental_lbl").innerHTML="";
		signalError("type_Rental", false, true, true);
		}
		}
		//Child Tax Credit
		if(jQuery('#W72typeofincome6').is(':checked'))   
		{ 
		if (document.getElementsByName("type_ChildTaxCredit")[0].value == '') 
		{
		document.getElementById("type_ChildTaxCredit_lbl").innerHTML="Please Enter ANNUAL ChildTaxCredit income ";
		signalError("type_ChildTaxCredit", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_ChildTaxCredit').offset().top});
		} 
		else
		{
		document.getElementById("type_ChildTaxCredit_lbl").innerHTML="";
		signalError("type_ChildTaxCredit", false, true, true);
		}
		}
		//Living Allowance
		if(jQuery('#W72typeofincome7').is(':checked'))   
		{ 
			if (document.getElementsByName("type_LivingAllowance")[0].value == '') 
			{
			document.getElementById("type_LivingAllowance_lbl").innerHTML="Please Enter ANNUAL Living Allowance income ";
			signalError("type_LivingAllowance", true, true, true);
			mCheck = true;
			jQuery('html,body').animate({scrollTop: jQuery('.type_LivingAllowance').offset().top});
			} 
			else
			{
			document.getElementById("type_LivingAllowance_lbl").innerHTML="";
			signalError("type_LivingAllowance", false, true, true);
			}
		}

		//Car Allowance
		if(jQuery('#W72typeofincome8').is(':checked'))   
		{ 
		if (document.getElementsByName("type_CarAllowance")[0].value == '') 
		{
		document.getElementById("type_CarAllowance_lbl").innerHTML="Please Enter ANNUAL Car Allowance income ";
		signalError("type_CarAllowance", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_CarAllowance').offset().top});
		} 
		else
		{
		document.getElementById("type_CarAllowance_lbl").innerHTML="";
		signalError("type_CarAllowance", false, true, true);
		}
		}
		//Investment
		if(jQuery('#W72typeofincome1').is(':checked'))   
		{ 
		if (document.getElementsByName("type_Investment")[0].value == '') 
		{
		document.getElementById("type_Investment_lbl").innerHTML="Please Enter ANNUAL Investment income ";
		signalError("type_Investment", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_Investment').offset().top});
		} 
		else
		{
		document.getElementById("type_Investment_lbl").innerHTML="";
		signalError("type_Investment", false, true, true);
		}	
		}
		//other    

		if(jQuery('#W72typeofincome4').is(':checked'))   
		{ 
		if (document.getElementsByName("type_other")[0].value == '') 
		{
		document.getElementById("type_other_lbl").innerHTML="Please Enter ANNUAL other income ";
		signalError("type_other", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_other').offset().top});
		} 
		else   
		{
		document.getElementById("type_other_lbl").innerHTML="";
		signalError("type_other", false, true, true);
		}   

		}  



		}	


		//




		}




		if(jQuery('#W7OtherIncome1').is(':checked'))   
		{ 
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeTwo"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeTwo_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeTwo1').offset().top});						
		}
		else
		{   
		radioError("W7IncomeEarnedIncomeTwo_lbl", false);
		}
		}


		//income employed 
		if(document.getElementById("W7IncomeEarnedIncomeTwo1").checked == true)
		{

		//SECONDINCOME

		var radio = jQuery('input:radio[name="W72HowPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72HowPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72HowPaid1').offset().top});					
		}
		else
		{   
		radioError("W72HowPaid_lbl", false);
		}
		if(document.getElementById("W72HowPaid2").checked == true || document.getElementById("W72HowPaid5").checked == true)
		{
		var radio = jQuery('input:radio[name="W72OverTimePaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72OverTimePaid_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72OverTimePaid1').offset().top});					
		}
		else
		{   
		radioError("W72OverTimePaid_lbl", false);
		}
		}	
		if(document.getElementById("W72HowPaid1").checked == true
		|| document.getElementById("W72HowPaid6").checked == true
		|| document.getElementById("W72HowPaid4").checked == true)
		{

		var radio = jQuery('input:radio[name="W72FrequentlyPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72FrequentlyPaid_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72FrequentlyPaid1').offset().top});						
		}
		else
		{   
		radioError("W72FrequentlyPaid_lbl", false);
		}
		}
		
		// var radio = jQuery('input:radio[name="W72HowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W72HowLongCJob_lbl", true);
		// mCheck = true;  
		// jQuery('html,body').animate({scrollTop: jQuery('#W72HowLongCJob1').offset().top});						
		// }
		// else
		// {   
		// radioError("W72HowLongCJob_lbl", false);
		// }
		
		var radio = jQuery('input:radio[name="W72IndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72IndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72IndustryType1').offset().top});					
		}
		else
		{   
		radioError("W72IndustryType_lbl", false);
		}
		var radio = jQuery('input:radio[name="W72Considered"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Considered_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72Considered1').offset().top});						
		}
		else
		{   
		radioError("W72Considered_lbl", false);
		}
		///
		//////////////////////
		if (document.getElementsByName("W72TxtJobTitle")[0].value == '') 
		{
		document.getElementById("W72TxtJobTitle_lbl").innerHTML="Please Enter job title";
		signalError("W72TxtJobTitle", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtJobTitle').offset().top});
		} else {
		document.getElementById("W72TxtJobTitle_lbl").innerHTML="";
		signalError("W72TxtJobTitle", false, true, true);
		}	
		if (document.getElementsByName("W72TxtCompName")[0].value == '') 
		{
		document.getElementById("W72TxtCompName_lbl").innerHTML="Please Enter company name";
		signalError("W72TxtCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtCompName').offset().top});
		} else {
		document.getElementById("W72TxtCompName_lbl").innerHTML="";
		signalError("W72TxtCompName", false, true, true);
		}	

		//////////////////			



		///
		var radio = jQuery('input:radio[name="W72OtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72OtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72OtherIncome1').offset().top});					
		}
		else
		{   
		radioError("W72OtherIncome_lbl", false);
		}      
		}

		if(jQuery('#W72OtherIncome1').is(':checked') || jQuery('#W72BothOtherIncome1').is(':checked') )  
		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeThree_lbl", true);  
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeThree').offset().top});					
		}
		else  
		{   
		radioError("W7IncomeEarnedIncomeThree_lbl", false);
		}


		}     
		//income 1 div for co-applicant when click on no button
		if(document.getElementById("W2SecondApp1").checked == true)  
		{
		if(document.getElementById("W7OtherIncome2").checked == true)  
		{
		var radio = jQuery('input:radio[name="W7HowPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7HowPaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowPaidSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7HowPaidSecApp_lbl", false);
		}
		//
		if(document.getElementById("W7HowPaidSecApp2").checked == true || document.getElementById("W7HowPaidSecApp5").checked == true)
		{
		var radio = jQuery('input:radio[name="W7OverTimePaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OverTimePaidSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7OverTimePaidSecApp1').offset().top});								
		}
		else
		{   
		radioError("W7OverTimePaidSecApp_lbl", false);
		}
		}
		//
		if(document.getElementById("W7HowPaidSecApp1").checked == true 
		|| document.getElementById("W7HowPaidSecApp4").checked == true
		|| document.getElementById("W7HowPaidSecApp6").checked == true)  
		{
		var radio = jQuery('input:radio[name="W7FrequentlyPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7FrequentlyPaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7FrequentlyPaidSecApp1').offset().top});							
		}
		else
		{   
		radioError("W7FrequentlyPaidSecApp_lbl", false);
		}

		}
		//
		// var radio = jQuery('input:radio[name="W7HowLongCJobSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7HowLongCJobSecApp_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJobSecApp1').offset().top});							
		// }
		// else
		// {   
		// radioError("W7HowLongCJobSecApp_lbl", false);
		// }
		//



		var radio = jQuery('input:radio[name="W7IndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IndustryTypeSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IndustryTypeSecApp1').offset().top});							
		}
		else
		{   
		radioError("W7IndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7ConsideredSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ConsideredSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W7ConsideredSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7ConsideredSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W7TxtJobTitleSecApp")[0].value == '') 
		{
		document.getElementById("W7TxtJobTitleSecApp_lbl").innerHTML="Please Enter job title";
		signalError("W7TxtJobTitleSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtJobTitleSecApp').offset().top});
		} else {
		document.getElementById("W7TxtJobTitleSecApp_lbl").innerHTML="";
		signalError("W7TxtJobTitleSecApp", false, true, true);
		}	
		if (document.getElementsByName("W7TxtCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W7TxtCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W7TxtCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtCompNameSecApp').offset().top});   
		} else {
		document.getElementById("W7TxtCompNameSecApp_lbl").innerHTML="";
		signalError("W7TxtCompNameSecApp", false, true, true);
		}	

		//////////////////	

		////
		var radio = jQuery('input:radio[name="W7ChildSupportCoapp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ChildSupportCoapp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportCoapp1').offset().top});						
		}
		else
		{   
		radioError("W7ChildSupportCoapp_lbl", false);
		}
		var radio = jQuery('input:radio[name="W7OtherIncomeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OtherIncomeSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7OtherIncomeSecApp1').offset().top});								
		}
		else
		{   
		radioError("W7OtherIncomeSecApp_lbl", false);
		}




		}





		}
		//income 2 div when click on yes button 

		if(document.getElementById("W7OtherIncomeSecAppTwo1").checked == true
		|| document.getElementById("W7SelfOtherIncomeSecApp1").checked == true 
		||	document.getElementById("W7OtherIncomeSecApp1").checked == true		
		)  
		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeTwoSecapp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeTwoSecapp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeTwoSecapp').offset().top});			   			
		}
		else   
		{   
		radioError("W7IncomeEarnedIncomeTwoSecapp_lbl", false);
		}



		}	


		//income 3 div
		if(document.getElementById("W72OtherIncomeSecApp1").checked == true || document.getElementById("W72SelfOtherIncomeSecApp1").checked == true || document.getElementById("W7OtherIncomeSecAppThree1").checked == true)

		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeThreeSecapp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeThreeSecapp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeThreeSecapp1').offset().top});					
		}
		else
		{   
		radioError("W7IncomeEarnedIncomeThreeSecapp_lbl", false);
		}

		}     





		////income 2 employed red alert for co-app

		if(document.getElementById("W7IncomeEarnedIncomeTwoSecapp1").checked == true)

		{

		var radio = jQuery('input:radio[name="W72HowPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72HowPaidSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72HowPaidSecApp1').offset().top});							
		}
		else
		{   
		radioError("W72HowPaidSecApp_lbl", false);
		}
		//


		if(jQuery("#W72HowPaidSecApp2").is(':checked') || jQuery("#W72HowPaidSecApp5").is(':checked'))
		{
		var radio = jQuery('input:radio[name="W72OverTimePaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72OverTimePaidSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W72OverTimePaidSecApp1').offset().top});						
		}
		else
		{   
		radioError("W72OverTimePaidSecApp_lbl", false);
		}
		}
		//

		if(jQuery("#W72HowPaidSecApp1").is(':checked')
		|| jQuery("#W72HowPaidSecApp4").is(':checked')
		|| jQuery("#W72HowPaidSecApp6").is(':checked')
		)
		{

		var radio = jQuery('input:radio[name="W72FrequentlyPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72FrequentlyPaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72FrequentlyPaidSecApp1').offset().top});						
		}
		else
		{   
		radioError("W72FrequentlyPaidSecApp_lbl", false);
		}


		}
		//Rahul 12062014
		// var radio = jQuery('input:radio[name="W72HowLongCJobSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W72HowLongCJobSecApp_lbl", true);
		// mCheck = true;  
		// jQuery('html,body').animate({scrollTop: jQuery('#W72HowLongCJobSecApp1').offset().top});							
		// }
		// else
		// {   
		// radioError("W72HowLongCJobSecApp_lbl", false);
		// }
		//
		var radio = jQuery('input:radio[name="W72IndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72IndustryTypeSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W72IndustryTypeSecApp1').offset().top});							
		}
		else     
		{   
		radioError("W72IndustryTypeSecApp_lbl", false);
		}


		var radio = jQuery('input:radio[name="W72ConsideredSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72ConsideredSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72ConsideredSecApp1').offset().top});						
		}
		else
		{   
		radioError("W72ConsideredSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W72TxtJobTitleSecApp")[0].value == '') 
		{
		document.getElementById("W72TxtJobTitleSecApp_lbl").innerHTML="Please Enter job title";
		signalError("W72TxtJobTitleSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtJobTitleSecApp').offset().top});
		} else {
		document.getElementById("W72TxtJobTitleSecApp_lbl").innerHTML="";
		signalError("W72TxtJobTitleSecApp", false, true, true);
		}	
		if (document.getElementsByName("W72TxtCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W72TxtCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W72TxtCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtCompNameSecApp').offset().top});   
		} else {   
		document.getElementById("W72TxtCompNameSecApp_lbl").innerHTML="";
		signalError("W72TxtCompNameSecApp", false, true, true);
		}	

		//////////////////	
		////30/09/2014

		// var radio = jQuery('input:radio[name="W7ChildSupportCoapptwo"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7ChildSupportCoapptwo_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportCoapptwo1').offset().top});							
		// }
		// else
		// {   
		// radioError("W7ChildSupportCoapptwo_lbl", false);
		// }
		//



		var radio = jQuery('input:radio[name="W72OtherIncomeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72OtherIncomeSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72OtherIncomeSecApp1').offset().top});							
		}
		else
		{   
		radioError("W72OtherIncomeSecApp_lbl", false);
		}
		//


		}

		////income 2 self-employed red alert for co-app

		if(document.getElementById("W7IncomeEarnedIncomeTwoSecapp2").checked == true)

		{	

		var radio = jQuery('input:radio[name="W72SelfBusClassSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfBusClassSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfBusClassSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfBusClassSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W72TxtSelfAvgIncomeSecApp")[0].value == '') 
		{   
		document.getElementById("W72TxtSelfAvgIncomeSecApp_lbl").innerHTML="Please Enter annual income ";
		signalError("W72TxtSelfAvgIncomeSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtSelfAvgIncomeSecApp').offset().top});
		} else {
		document.getElementById("W72TxtSelfAvgIncomeSecApp_lbl").innerHTML="";
		signalError("W72TxtSelfAvgIncomeSecApp", false, true, true);
		}	
		if (document.getElementsByName("W72TxtSelfCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W72TxtSelfCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W72TxtSelfCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtSelfCompNameSecApp').offset().top});     
		} else {   
		document.getElementById("W72TxtSelfCompNameSecApp_lbl").innerHTML="";
		signalError("W72TxtSelfCompNameSecApp", false, true, true);
		}	

		//////////////////		

		////
		// var radio = jQuery('input:radio[name="W72SelfHowLongCJobSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W72SelfHowLongCJobSecApp_lbl", true);
		// mCheck = true;  
		// jQuery('html,body').animate({scrollTop: jQuery('#W72SelfHowLongCJobSecApp1').offset().top});					
		// }
		// else
		// {   
		// radioError("W72SelfHowLongCJobSecApp_lbl", false);
		// }
		//



		var radio = jQuery('input:radio[name="W72SelfIndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{            
		radioError("W72SelfIndustryTypeSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfIndustryTypeSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfIndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W72SelfContractEmpSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfContractEmpSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfContractEmpSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfContractEmpSecApp_lbl", false);
		}


		//30/09/2014
		// var radio = jQuery('input:radio[name="W72SelfChildSupportSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W72SelfChildSupportSecApp_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W72SelfChildSupportSecApp1').offset().top});					
		// }
		// else
		// {   
		// radioError("W72SelfChildSupportSecApp_lbl", false);
		// }
		
		var radio = jQuery('input:radio[name="W72SelfOtherIncomeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfOtherIncomeSecApp_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfOtherIncomeSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfOtherIncomeSecApp_lbl", false);
		}



		}
		////income 2 retired co-applicant
		if(document.getElementById("W7IncomeEarnedIncomeTwoSecapp4").checked == true)

		{
		var radio = jQuery('input:radio[name="W7HowLongCJobSecAppThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7HowLongCJobSecAppThree_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJobSecAppThree1').offset().top});						
		}
		else
		{   
		radioError("W7HowLongCJobSecAppThree_lbl", false);
		}

		//



		var radio = jQuery('input:radio[name="W7ChildSupportSecAppThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ChildSupportSecAppThree_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportSecAppThree1').offset().top});					
		}
		else
		{   
		radioError("W7ChildSupportSecAppThree_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7OtherIncomeSecAppThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OtherIncomeSecAppThree_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7OtherIncomeSecAppThree1').offset().top});					
		}
		else
		{   
		radioError("W7OtherIncomeSecAppThree_lbl", false);
		}
		}
		//		



		//income 3 employed for co-applicant

		if(document.getElementById("W7IncomeEarnedIncomeThreeSecapp1").checked == true)

		{
		var radio = jQuery('input:radio[name="W73HowPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73HowPaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73HowPaidSecApp1').offset().top});							
		}
		else
		{   
		radioError("W73HowPaidSecApp_lbl", false);
		}
		//
		if(jQuery("#W73HowPaidSecApp2").is(':checked') || jQuery("#W73HowPaidSecApp5").is(':checked'))
		{
		var radio = jQuery('input:radio[name="W73OverTimePaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73OverTimePaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73OverTimePaidSecApp1').offset().top});						
		}
		else
		{   
		radioError("W73OverTimePaidSecApp_lbl", false);
		}
		}
		//

		if(jQuery("#W73HowPaidSecApp1").is(':checked')
		|| jQuery("#W73HowPaidSecApp4").is(':checked')
		|| jQuery("#W73HowPaidSecApp6").is(':checked')
		)
		{

		var radio = jQuery('input:radio[name="W73FrequentlyPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73FrequentlyPaidSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73FrequentlyPaidSecApp1').offset().top});							
		}
		else
		{   
		radioError("W73FrequentlyPaidSecApp_lbl", false);
		}


		}	



		//



		// var radio = jQuery('input:radio[name="W73HowLongCJobSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W73HowLongCJobSecApp_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W73HowLongCJobSecApp1').offset().top});						
		// }
		// else
		// {   
		// radioError("W73HowLongCJobSecApp_lbl", false);
		// }
		//



		var radio = jQuery('input:radio[name="W73IndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73IndustryTypeSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73IndustryTypeSecApp1').offset().top});							
		}
		else
		{   
		radioError("W73IndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W73ConsideredSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73ConsideredSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73ConsideredSecApp1').offset().top});						
		}
		else
		{   
		radioError("W73ConsideredSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W73TxtJobTitleSecApp")[0].value == '') 
		{
		document.getElementById("W73TxtJobTitleSecApp_lbl").innerHTML="Please Enter job title";
		signalError("W73TxtJobTitleSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtJobTitleSecApp').offset().top});
		} else {
		document.getElementById("W73TxtJobTitleSecApp_lbl").innerHTML="";
		signalError("W73TxtJobTitleSecApp", false, true, true);
		}	
		if (document.getElementsByName("W73TxtCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W73TxtCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W73TxtCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtCompNameSecApp').offset().top});   
		} else {    
		document.getElementById("W73TxtCompNameSecApp_lbl").innerHTML="";
		signalError("W73TxtCompNameSecApp", false, true, true);
		}	

		//////////////////	

		////30/09/2014
		// var radio = jQuery('input:radio[name="W7ChildSupportCoappthree"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7ChildSupportCoappthree_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportCoappthree1').offset().top});						
		// }
		// else
		// {   
		// radioError("W7ChildSupportCoappthree_lbl", false);
		// }		







		}	


		////income3 self-employed red alert for co-applicant
		if(document.getElementById("W7IncomeEarnedIncomeThreeSecapp2").checked == true)

		{

		var radio = jQuery('input:radio[name="W73SelfBusClassSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfBusClassSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfBusClassSecApp1').offset().top});					
		}
		else
		{   
		radioError("W73SelfBusClassSecApp_lbl", false);
		}

		////
		//////////////////////
		if (document.getElementsByName("W73TxtSelfAvgIncomeSecApp")[0].value == '') 
		{   
		document.getElementById("W73TxtSelfAvgIncomeSecApp_lbl").innerHTML="Please Enter annual income ";
		signalError("W73TxtSelfAvgIncomeSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtSelfAvgIncomeSecApp').offset().top});
		} else {
		document.getElementById("W73TxtSelfAvgIncomeSecApp_lbl").innerHTML="";
		signalError("W73TxtSelfAvgIncomeSecApp", false, true, true);
		}	
		if (document.getElementsByName("W73TxtSelfCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W73TxtSelfCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W73TxtSelfCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtSelfCompNameSecApp').offset().top});     
		} else {   
		document.getElementById("W73TxtSelfCompNameSecApp_lbl").innerHTML="";
		signalError("W73TxtSelfCompNameSecApp", false, true, true);
		}	



		// var radio = jQuery('input:radio[name="W73SelfHowLongCJobSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W73SelfHowLongCJobSecApp_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W73SelfHowLongCJobSecApp1').offset().top});					
		// }
		// else
		// {   
		// radioError("W73SelfHowLongCJobSecApp_lbl", false);
		// }
		//



		var radio = jQuery('input:radio[name="W73SelfIndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfIndustryTypeSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfIndustryTypeSecApp1').offset().top});					
		}
		else
		{   
		radioError("W73SelfIndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W73SelfContractEmpSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfContractEmpSecApp_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfContractEmpSecApp1').offset().top});					
		}
		else
		{   
		radioError("W73SelfContractEmpSecApp_lbl", false);
		}
		//


		//30/09/2014
		// var radio = jQuery('input:radio[name="W73SelfChildSupportSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W73SelfChildSupportSecApp_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W73SelfChildSupportSecApp1').offset().top});					
		// }
		// else
		// {   
		// radioError("W73SelfChildSupportSecApp_lbl", false);
		// }
		//

		}


		////income 3 retired red alert for co-applicant
		if(document.getElementById("W7IncomeEarnedIncomeThreeSecapp4").checked == true)

		{
		var radio = jQuery('input:radio[name="W7HowLongCJobSecAppFour"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7HowLongCJobSecAppFour_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJobSecAppFour1').offset().top})					
		}
		else
		{   
		radioError("W7HowLongCJobSecAppFour_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7ChildSupportSecAppFour"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ChildSupportSecAppFour_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportSecAppFour1').offset().top})					
		}
		else
		{   
		radioError("W7ChildSupportSecAppFour_lbl", false);
		}     


		}					






		////income 2 self-employed red alert
		if(document.getElementById("W7IncomeEarnedIncomeTwo2").checked == true)

		{

		var radio = jQuery('input:radio[name="W72SelfBusClass"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfBusClass_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfBusClass1').offset().top});					
		}
		else
		{   
		radioError("W72SelfBusClass_lbl", false);
		}
		////
		var radio = jQuery('input:radio[name="W72SelfHowLongCJob"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfHowLongCJob_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfHowLongCJob1').offset().top});					
		}
		else
		{   
		radioError("W72SelfHowLongCJob_lbl", false);
		}
		//////////////////////
		if (document.getElementsByName("W72TxtSelfAvgIncome")[0].value == '') 
		{   
		document.getElementById("W72TxtSelfAvgIncome_lbl").innerHTML="Please Enter annual income ";
		signalError("W72TxtSelfAvgIncome", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtSelfAvgIncome').offset().top});
		} else {
		document.getElementById("W72TxtSelfAvgIncome_lbl").innerHTML="";
		signalError("W72TxtSelfAvgIncome", false, true, true);
		}	
		if (document.getElementsByName("W72TxtSelfCompName")[0].value == '') 
		{
		document.getElementById("W72TxtSelfCompName_lbl").innerHTML="Please Enter company name";
		signalError("W72TxtSelfCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtSelfCompName').offset().top});   
		} else {
		document.getElementById("W72TxtSelfCompName_lbl").innerHTML="";
		signalError("W72TxtSelfCompName", false, true, true);
		}	

		//////////////////	


		var radio = jQuery('input:radio[name="W72SelfIndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfIndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfIndustryType1').offset().top});						
		}
		else
		{   
		radioError("W72SelfIndustryType_lbl", false);
		}

		var radio = jQuery('input:radio[name="W72SelfContractEmp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfContractEmp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfContractEmp1').offset().top});						
		}
		else  
		{   
		radioError("W72SelfContractEmp_lbl", false);
		}

		var radio = jQuery('input:radio[name="W72SelfOtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfOtherIncome_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfOtherIncome1').offset().top});					
		}
		else
		{   
		radioError("W72SelfOtherIncome_lbl", false);
		}


		////	

		}


		if(document.getElementById("W72SelfOtherIncome1").checked == true)

		{     

		var radio = jQuery('input:radio[name="W7SelfIncomeEarnedIncomeThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfIncomeEarnedIncomeThree_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfIncomeEarnedIncomeThree1').offset().top});					
		}
		else
		{   
		radioError("W7SelfIncomeEarnedIncomeThree_lbl", false);
		}
		}












		////income 2 retired red alert
		if(document.getElementById("W7IncomeEarnedIncomeTwo4").checked == true)

		{

		var radio = jQuery('input:radio[name="W72BothHowLongCJob"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72BothHowLongCJob_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72BothHowLongCJob1').offset().top});		   			
		}
		else
		{   
		radioError("W72BothHowLongCJob_lbl", false);
		}


		var radio = jQuery('input:radio[name="W72BothOtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72BothOtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72BothOtherIncome1').offset().top});					
		}
		else
		{   
		radioError("W72BothOtherIncome_lbl", false);
		}

		}

		////////////////////////////////////////////




		//////////////////////////////////////////////





		if(document.getElementById("W7IncomeEarnedIncomeThree1").checked == true || document.getElementById("W7SelfIncomeEarnedIncomeThree1").checked == true)
		{

		//THIRDINCOME

		var radio = jQuery('input:radio[name="W73HowPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73HowPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73HowPaid1').offset().top});					
		}
		else
		{   
		radioError("W73HowPaid_lbl", false);
		}
		//



		if(document.getElementById("W73HowPaid2").checked == true || document.getElementById("W73HowPaid5").checked == true)
		{
		var radio = jQuery('input:radio[name="W73OverTimePaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73OverTimePaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73OverTimePaid1').offset().top});					
		}
		else
		{   
		radioError("W73OverTimePaid_lbl", false);
		}
		}
		//



		if(document.getElementById("W73HowPaid1").checked == true
		|| document.getElementById("W73HowPaid6").checked == true
		|| document.getElementById("W73HowPaid4").checked == true)
		{

		var radio = jQuery('input:radio[name="W73FrequentlyPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73FrequentlyPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73FrequentlyPaid1').offset().top});						
		}
		else
		{   
		radioError("W73FrequentlyPaid_lbl", false);
		}
		}
		//



		// var radio = jQuery('input:radio[name="W73HowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W73HowLongCJob_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W73HowLongCJob1').offset().top});					
		// }
		// else
		// {   
		// radioError("W73HowLongCJob_lbl", false);
		// }


		var radio = jQuery('input:radio[name="W73IndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73IndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73IndustryType1').offset().top});					
		}
		else
		{   
		radioError("W73IndustryType_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W73Considered"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73Considered_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73Considered1').offset().top});						
		}
		else
		{   
		radioError("W73Considered_lbl", false);
		}



		///////////
		//////////////////////
		if (document.getElementsByName("W73TxtJobTitle")[0].value == '') 
		{
		document.getElementById("W73TxtJobTitle_lbl").innerHTML="Please Enter job title";
		signalError("W73TxtJobTitle", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtJobTitle').offset().top});
		} 
		else {
		document.getElementById("W73TxtJobTitle_lbl").innerHTML="";
		signalError("W73TxtJobTitle", false, true, true);
		}	

		if (document.getElementsByName("W73TxtCompName")[0].value == '') 
		{
		document.getElementById("W73TxtCompName_lbl").innerHTML="Please Enter company name";
		signalError("W73TxtCompName", true, true, true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtCompName').offset().top});
		} else {
		document.getElementById("W73TxtCompName_lbl").innerHTML="";
		signalError("W73TxtCompName", false, true, true);
		}	
		} 
		//income 3 sel-employed red alert						   
		if(document.getElementById("W7IncomeEarnedIncomeThree2").checked == true || document.getElementById("W7SelfIncomeEarnedIncomeThree2").checked == true)
		{   
		var radio = jQuery('input:radio[name="W73SelfBusClass"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfBusClass_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfBusClass1').offset().top});					
		}
		else
		{   
		radioError("W73SelfBusClass_lbl", false);
		}
		////
		//////////////////////
		if (document.getElementsByName("W73TxtSelfAvgIncome")[0].value == '') 
		{   
		document.getElementById("W73TxtSelfAvgIncome_lbl").innerHTML="Please Enter annual income ";
		signalError("W73TxtSelfAvgIncome", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtSelfAvgIncome').offset().top});
		} else {
		document.getElementById("W73TxtSelfAvgIncome_lbl").innerHTML="";
		signalError("W73TxtSelfAvgIncome", false, true, true);
		}	
		if (document.getElementsByName("W73TxtSelfCompName")[0].value == '') 
		{
		document.getElementById("W73TxtSelfCompName_lbl").innerHTML="Please Enter company name";
		signalError("W73TxtSelfCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtSelfCompName').offset().top});        
		document.getElementById("W73TxtSelfCompName_lbl").innerHTML="";
		signalError("W73TxtSelfCompName", false, true, true);
		}	

		//////////////////		
		////
		var radio = jQuery('input:radio[name="W73SelfHowLongCJob"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfHowLongCJob_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfHowLongCJob1').offset().top});						
		}
		else
		{   
		radioError("W73SelfHowLongCJob_lbl", false);
		}

		var radio = jQuery('input:radio[name="W73SelfIndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfIndustryType_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfIndustryType1').offset().top});						
		}
		else
		{   
		radioError("W73SelfIndustryType_lbl", false);
		}

		var radio = jQuery('input:radio[name="W73SelfContractEmp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfContractEmp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfContractEmp1').offset().top});						
		}
		else
		{   
		radioError("W73SelfContractEmp_lbl", false);
		}




		}	
		//income 3 red alert for retired  
		if(document.getElementById("W7IncomeEarnedIncomeThree4").checked == true || document.getElementById("W7SelfIncomeEarnedIncomeThree4").checked == true )
		{
		var radio = jQuery('input:radio[name="W72Both3HowLongCJob"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Both3HowLongCJob_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72Both3HowLongCJob1').offset().top});			   			
		}
		else
		{   
		radioError("W72Both3HowLongCJob_lbl", false);
		}


		}			
		//income 1 sel-employed button on very first question on 80 %				  

		if(document.getElementById("W7IncomeEarned2").checked == true )
		{
		var radio = jQuery('input:radio[name="W7SelfBusClass"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfBusClass_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfBusClass1').offset().top});					
		}
		else
		{   
		radioError("W7SelfBusClass_lbl", false);
		}
		/////
		//////////////////////
		if (document.getElementsByName("W7TxtSelfAvgIncome")[0].value == '') 
		{   
		document.getElementById("W7TxtSelfAvgIncome_lbl").innerHTML="Please Enter annual income ";
		signalError("W7TxtSelfAvgIncome", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtSelfAvgIncome').offset().top});
		} 
		else {
		document.getElementById("W7TxtSelfAvgIncome_lbl").innerHTML="";
		signalError("W7TxtSelfAvgIncome", false, true, true);
		}

		if (document.getElementsByName("W7TxtSelfCompName")[0].value == '') 
		{
		document.getElementById("W7TxtSelfCompName_lbl").innerHTML="Please Enter company name";
		signalError("W7TxtSelfCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtSelfCompName').offset().top});   
		} else {
		document.getElementById("W7TxtSelfCompName_lbl").innerHTML="";
		signalError("W7TxtSelfCompName", false, true, true);
		}	

		//////////////////				


		//////30/09/2014
		// var radio = jQuery('input:radio[name="W7SelfHowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7SelfHowLongCJob_lbl", true);
		// mCheck = true;  
		// jQuery('html,body').animate({scrollTop: jQuery('#W7SelfHowLongCJob1').offset().top});						
		// }
		// else
		// {   
		// radioError("W7SelfHowLongCJob_lbl", false);
		// }

		var radio = jQuery('input:radio[name="W7SelfIndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfIndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfIndustryType1').offset().top});					
		}
		else
		{   
		radioError("W7SelfIndustryType_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7SelfContractEmp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfContractEmp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfContractEmp1').offset().top});						
		}
		else
		{   
		radioError("W7SelfContractEmp_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7SelfChildSupport"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfChildSupport_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfChildSupport1').offset().top});					
		}
		else
		{   
		radioError("W7SelfChildSupport_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7SelfOtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfOtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfOtherIncome1').offset().top});					
		}
		else
		{   
		radioError("W7SelfOtherIncome_lbl", false);
		}


		var radio = jQuery('input:radio[name="W72SelfSourcesofincome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfSourcesofincome_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfSourcesofincome1').offset().top});					
		}
		else
		{   
		radioError("W72SelfSourcesofincome_lbl", false);
		}
		if(document.getElementById("W72SelfSourcesofincome1").checked == true)
		{

		if(document.getElementById("W72Selftypeofincome1").checked == true
		|| document.getElementById("W72Selftypeofincome2").checked == true
		|| document.getElementById("W72Selftypeofincome3").checked == true
		|| document.getElementById("W72Selftypeofincome4").checked == true
		|| document.getElementById("W72Selftypeofincome5").checked == true
		|| document.getElementById("W72Selftypeofincome6").checked == true
		|| document.getElementById("W72Selftypeofincome7").checked == true
		|| document.getElementById("W72Selftypeofincome8").checked == true
		)
		{
		radioError("W72Selftypeofincome1_lbl", false);
		radioError("W72Selftypeofincome2_lbl", false);
		radioError("W72Selftypeofincome3_lbl", false);
		radioError("W72Selftypeofincome4_lbl", false);
		radioError("W72Selftypeofincome5_lbl", false);
		radioError("W72Selftypeofincome6_lbl", false);
		radioError("W72Selftypeofincome7_lbl", false);
		radioError("W72Selftypeofincome8_lbl", false);
		}
		else
		{


		//1
		var radio = jQuery('input:radio[name="W72Selftypeofincome1"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome1_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome1').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome1_lbl", false);
		}
		//2
		var radio = jQuery('input:radio[name="W72Selftypeofincome2"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome2_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome2').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome2_lbl", false);
		}
		//3
		var radio = jQuery('input:radio[name="W72Selftypeofincome3"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome3_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome3').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome3_lbl", false);
		}
		//4
		var radio = jQuery('input:radio[name="W72Selftypeofincome4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome4_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome4').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome4_lbl", false);
		}
		//5
		var radio = jQuery('input:radio[name="W72Selftypeofincome5"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome5_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome5').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome5_lbl", false);
		}
		//6
		var radio = jQuery('input:radio[name="W72Selftypeofincome6"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome6_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome6').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome6_lbl", false);
		}
		//7
		var radio = jQuery('input:radio[name="W72Selftypeofincome7"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome7_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome7').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome7_lbl", false);
		}
		//8
		var radio = jQuery('input:radio[name="W72Selftypeofincome8"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome8_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome8').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome8_lbl", false);
		}
		}//end else

		if(jQuery('#W72Selftypeofincome1').is(':checked'))   
		{    
		//interest
		if (document.getElementsByName("type_InterestSelf")[0].value == '') 
		{
		document.getElementById("type_InterestSelf_lbl").innerHTML="Please Enter ANNUAL Interest income ";
		signalError("type_InterestSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_InterestSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_InterestSelf_lbl").innerHTML="";
		signalError("type_InterestSelf", false, true, true);
		}
		}
		//Pension
		if(jQuery('#W72Selftypeofincome2').is(':checked'))   
		{ 
		if (document.getElementsByName("type_PensionSelf")[0].value == '') 
		{
		document.getElementById("type_PensionSelf_lbl").innerHTML="Please Enter ANNUAL Pension income ";
		signalError("type_PensionSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_PensionSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_PensionSelf_lbl").innerHTML="";
		signalError("type_PensionSelf", false, true, true);
		}
		}
		//Rental
		if(jQuery('#W72Selftypeofincome3').is(':checked'))   
		{ 
		if (document.getElementsByName("type_RentalSelf")[0].value == '') 
		{
		document.getElementById("type_RentalSelf_lbl").innerHTML="Please Enter ANNUAL Rental income ";
		signalError("type_RentalSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_RentalSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_RentalSelf_lbl").innerHTML="";
		signalError("type_RentalSelf", false, true, true);
		}
		}
		//Child Tax Credit
		if(jQuery('#W72Selftypeofincome5').is(':checked'))   
		{ 
		if (document.getElementsByName("type_ChildTaxCreditSelf")[0].value == '') 
		{
		document.getElementById("type_ChildTaxCreditSelf_lbl").innerHTML="Please Enter ANNUAL ChildTaxCredit income ";
		signalError("type_ChildTaxCreditSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_ChildTaxCreditSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_ChildTaxCreditSelf_lbl").innerHTML="";
		signalError("type_ChildTaxCreditSelf", false, true, true);
		}
		}
		//Living Allowance
		if(jQuery('#W72Selftypeofincome6').is(':checked'))   
		{ 
		if (document.getElementsByName("type_LivingAllowanceSelf")[0].value == '') 
		{
		document.getElementById("type_LivingAllowanceSelf_lbl").innerHTML="Please Enter ANNUAL Living Allowance income ";
		signalError("type_LivingAllowanceSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_LivingAllowanceSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_LivingAllowanceSelf_lbl").innerHTML="";
		signalError("type_LivingAllowanceSelf", false, true, true);
		}
		}

		//Car Allowance
		if(jQuery('#W72Selftypeofincome7').is(':checked'))   
		{ 
		if (document.getElementsByName("type_CarAllowanceSelf")[0].value == '') 
		{
		document.getElementById("type_CarAllowanceSelf_lbl").innerHTML="Please Enter ANNUAL Car Allowance income ";
		signalError("type_CarAllowanceSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_CarAllowanceSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_CarAllowanceSelf_lbl").innerHTML="";
		signalError("type_CarAllowanceSelf", false, true, true);
		}
		}
		//Investment
		if(jQuery('#W72Selftypeofincome8').is(':checked'))   
		{ 
		if (document.getElementsByName("type_InvestmentSelf")[0].value == '') 
		{
		document.getElementById("type_InvestmentSelf_lbl").innerHTML="Please Enter ANNUAL Investment income ";
		signalError("type_InvestmentSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_InvestmentSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_InvestmentSelf_lbl").innerHTML="";
		signalError("type_InvestmentSelf", false, true, true);
		}	
		}
		//other       

		if(jQuery('#W72Selftypeofincome4').is(':checked'))   
		{ 
		if (document.getElementsByName("type_otherSelf")[0].value == '') 
		{
		document.getElementById("type_otherSelf_lbl").innerHTML="Please Enter ANNUAL other income ";
		signalError("type_otherSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_otherSelf').offset().top});
		} 
		else   
		{
		document.getElementById("type_otherSelf_lbl").innerHTML="";
		signalError("type_otherSelf", false, true, true);
		}   

		}       



		}


		}




		if(document.getElementById("W7SelfOtherIncome1").checked == true)
		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeTwo"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeTwo_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeTwo1').offset().top});							
		}
		else
		{   
		radioError("W7IncomeEarnedIncomeTwo_lbl", false);
		}
		}   
		//income1 retired very first question on 80%   
		if(document.getElementById("W7IncomeEarned4").checked == true)
		{
		var radio = jQuery('input:radio[name="W7HowLongCJobBoth"]:checked');
		if (radio.length == 0)         
		{        
		radioError("W7HowLongCJobBoth_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJobBoth1').offset().top});						
		}
		else
		{   
		radioError("W7HowLongCJobBoth_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7BothChildSupport"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7BothChildSupport_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7BothChildSupport1').offset().top});					
		}
		else
		{   
		radioError("W7BothChildSupport_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7BothOtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7BothOtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7BothOtherIncome1').offset().top});					
		}
		else  
		{   
		radioError("W7BothOtherIncome_lbl", false);
		}					

		}

		if(document.getElementById("W7BothOtherIncome1").checked == true)
		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeTwo"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeTwo_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeTwo1').offset().top});		  			
		}
		else
		{   
		radioError("W7IncomeEarnedIncomeTwo_lbl", false);
		}
		}

		//	co-applicant 1 for  retired  
		if(document.getElementById("W2SecondApp1").checked == true)
		{

		if(document.getElementById("W7BothOtherIncome2").checked == true)
		{
		var radio = jQuery('input:radio[name="W7BothHowLongCJobSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7BothHowLongCJobSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7BothHowLongCJobSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7BothHowLongCJobSecApp_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7BothChildSupportSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7BothChildSupportSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7BothChildSupportSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7BothChildSupportSecApp_lbl", false);
		}




		var radio = jQuery('input:radio[name="W7OtherIncomeSecAppTwo"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OtherIncomeSecAppTwo_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7OtherIncomeSecAppTwo1').offset().top});						
		}
		else
		{   
		radioError("W7OtherIncomeSecAppTwo_lbl", false);
		}



		}}




		if(document.getElementById("W7OtherIncomeSecAppThree1").checked == true )
		{       
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeThreeSecapp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeThreeSecapp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeThreeSecapp').offset().top});			   			
		}  
		else     
		{   
		radioError("W7IncomeEarnedIncomeThreeSecapp_lbl", false);
		}


		}


		//self-employed co-app
		//co-applicant self-employed
		if(document.getElementById("W2SecondApp1").checked == true)
		{
		if(document.getElementById("W7SelfOtherIncome2").checked == true)
		{
		var radio = jQuery('input:radio[name="W7SelfBusClassSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfBusClassSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfBusClassSecApp1').offset().top});					
		}
		else
		{   
		radioError("W7SelfBusClassSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W7TxtSelfAvgIncomeSecApp")[0].value == '') 
		{   
		document.getElementById("W7TxtSelfAvgIncomeSecApp_lbl").innerHTML="Please Enter annual income ";
		signalError("W7TxtSelfAvgIncomeSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtSelfAvgIncomeSecApp').offset().top});
		} else {
		document.getElementById("W7TxtSelfAvgIncomeSecApp_lbl").innerHTML="";
		signalError("W7TxtSelfAvgIncomeSecApp", false, true, true);
		}	
		if (document.getElementsByName("W7TxtSelfCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W7TxtSelfCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W7TxtSelfCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtSelfCompNameSecApp').offset().top});     
		} else {
		document.getElementById("W7TxtSelfCompNameSecApp_lbl").innerHTML="";
		signalError("W7TxtSelfCompNameSecApp", false, true, true);
		}	

		//////////////////	

		//
		// var radio = jQuery('input:radio[name="W7SelfHowLongCJobSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7SelfHowLongCJobSecApp_lbl", true);
		// mCheck = true;
		// jQuery('html,body').animate({scrollTop: jQuery('#W7SelfHowLongCJobSecApp1').offset().top});					
		// }
		// else
		// {   
		// radioError("W7SelfHowLongCJobSecApp_lbl", false);
		// }
		//



		var radio = jQuery('input:radio[name="W7SelfIndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfIndustryTypeSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfIndustryTypeSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7SelfIndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7SelfContractEmpSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfContractEmpSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfContractEmpSecApp1').offset().top});					
		}
		else
		{   
		radioError("W7SelfContractEmpSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7SelfChildSupportSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfChildSupportSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfChildSupportSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7SelfChildSupportSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7SelfOtherIncomeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfOtherIncomeSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfOtherIncomeSecApp1').offset().top});					
		}
		else
		{   
		radioError("W7SelfOtherIncomeSecApp_lbl", false);
		}

		}}		










		jQuery(".W7IncomeEarnedgout").is(':checked')
		{
		window.scrollTo(0,0);  

		}   			
		}    
		}							
		else 
		{
		jQuery('#customField').val("");
		//What is the purchase price of the property
		// var radio = jQuery('input:radio[name="W6PurcDPaymentFrom"]:checked');
		// if (radio.length == 0) {
		// radioError("W6PurcDPaymentFrom_lbl", true);
		// mCheck = true;
		// } else {
		// radioError("W6PurcDPaymentFrom_lbl", false);
		// }

		//How much money are you putting as a down payment?
		if (document.getElementById("W3Goal2Look1").checked == true)  
		{  

		if (document.getElementsByName("W6TxtMoneyDPayment")[0].value == '') {
		document.getElementById("W6TxtMoneyDPayment_lbl").innerHTML="Please Enter down payment";
		signalError("W6TxtMoneyDPayment", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtMoneyDPayment').offset().top});
		} else 
		{
		document.getElementById("W6TxtMoneyDPayment_lbl").innerHTML="";
		signalError("W6TxtMoneyDPayment", false, true, true);
		}
		}
		//mutliselection
		////
		if (document.getElementById("W3Goal2Look1").checked == true)  
		{  						 ////down payment
		if(jQuery('#W6ConsDPaymentFrom1').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom2').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom3').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom4').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom6').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom7').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom8').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom9').is(':checked') 
		|| jQuery('#W6ConsDPaymentFrom10').is(':checked') 
		)   
		{
		radioError("W6ConsDPaymentFrom1_lbl", false);
		radioError("W6ConsDPaymentFrom2_lbl", false);
		radioError("W6ConsDPaymentFrom3_lbl", false);
		radioError("W6ConsDPaymentFrom4_lbl", false);
		radioError("W6ConsDPaymentFrom_lbl", false);
		radioError("W6ConsDPaymentFrom6_lbl", false);
		radioError("W6ConsDPaymentFrom7_lbl", false);
		radioError("W6ConsDPaymentFrom8_lbl", false);
		radioError("W6ConsDPaymentFrom9_lbl", false);  
		radioError("W6ConsDPaymentFrom10_lbl", false);
		}


		else   
		{

		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom1"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom1_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsDPaymentFrom1').offset().top});
		} else {
		radioError("W6ConsDPaymentFrom1_lbl", false);
		}  
		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom2"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom2_lbl", true);
		mCheck = true;
		} else {
		radioError("W6ConsDPaymentFrom2_lbl", false);
		} 


		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom3"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom3_lbl", true);
		mCheck = true;
		} else {
		radioError("W6ConsDPaymentFrom3_lbl", false);
		} 

		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom4"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom4_lbl", true);
		mCheck = true;
		} else {
		radioError("W6ConsDPaymentFrom4_lbl", false);
		}  
		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom_lbl", true);
		mCheck = true;
		} else {
		radioError("W6ConsDPaymentFrom_lbl", false);
		}  
		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom6"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom6_lbl", true);
		mCheck = true;
		} else {
		radioError("W6ConsDPaymentFrom6_lbl", false);
		} 
		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom7"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom7_lbl", true);
		mCheck = true;
		} else {
		radioError("W6ConsDPaymentFrom7_lbl", false);
		} 
		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom8"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom8_lbl", true);
		mCheck = true;
		} else {
		radioError("W6ConsDPaymentFrom8_lbl", false);
		} 
		//// 
		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom9"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom9_lbl", true);
		mCheck = true;
		} else {
		radioError("W6ConsDPaymentFrom9_lbl", false);
		} 
		var radio = jQuery('input:radio[name="W6ConsDPaymentFrom10"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsDPaymentFrom10_lbl", true);
		mCheck = true;
		} else {      
		radioError("W6ConsDPaymentFrom10_lbl", false);
		}

		}
		
		// end
		
		if(jQuery('#W6ConsDPaymentFrom1').is(':checked'))   
		{    
		//
		if (document.getElementsByName("bank_amount")[0].value == '') 
		{
		document.getElementById("bank_amount_lbl").innerHTML="Please Enter Bank Account Chequing/Savings";
		signalError("bank_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.bank_amount').offset().top});
		} 
		else
		{
		document.getElementById("bank_amount_lbl").innerHTML="";
		signalError("bank_amount", false, true, true);
		}
		}
		//
		if(jQuery('#W6ConsDPaymentFrom2').is(':checked'))   
		{ 
		if (document.getElementsByName("RRSPs_amount")[0].value == '') 
		{
		document.getElementById("RRSPs_amount_lbl").innerHTML="Please Enter RRSPs or Investments";
		signalError("RRSPs_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.RRSPs_amount').offset().top});
		} 
		else
		{
		document.getElementById("RRSPs_amount_lbl").innerHTML="";
		signalError("RRSPs_amount", false, true, true);
		}
		}
		//
		if(jQuery('#W6ConsDPaymentFrom3').is(':checked'))   
		{ 
		if (document.getElementsByName("borrowed_amount")[0].value == '') 
		{
		document.getElementById("borrowed_amount_lbl").innerHTML="Please Enter Borrowed(e.g LOC)";
		signalError("borrowed_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.borrowed_amount').offset().top});
		} 
		else
		{
		document.getElementById("borrowed_amount_lbl").innerHTML="";
		signalError("borrowed_amount", false, true, true);
		}
		}
		//
		if(jQuery('#W6ConsDPaymentFrom4').is(':checked'))   
		{ 
		if (document.getElementsByName("sale_amount")[0].value == '') 
		{
		document.getElementById("sale_amount_lbl").innerHTML="Please Enter Sale of Asset";
		signalError("sale_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.sale_amount').offset().top});
		} 
		else
		{
		document.getElementById("sale_amount_lbl").innerHTML="";
		signalError("sale_amount", false, true, true);
		}
		}
		//
		if(jQuery('#W6ConsDPaymentFrom5').is(':checked'))   
		{ 
		if (document.getElementsByName("gift_amount")[0].value == '') 
		{
		document.getElementById("gift_amount_lbl").innerHTML="Please Enter Gift Amount";
		signalError("gift_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.gift_amount').offset().top});
		} 
		else
		{
		document.getElementById("gift_amount_lbl").innerHTML="";
		signalError("gift_amount", false, true, true);
		}
		}

		//
		if(jQuery('#W6ConsDPaymentFrom7').is(':checked'))   
		{ 
		if (document.getElementsByName("cash_amount")[0].value == '') 
		{
		document.getElementById("cash_amount_lbl").innerHTML="Please Enter Personal Cash";
		signalError("cash_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.cash_amount').offset().top});
		} 
		else
		{
		document.getElementById("cash_amount_lbl").innerHTML="";
		signalError("cash_amount", false, true, true);
		}
		}
		//
		if(jQuery('#W6ConsDPaymentFrom9').is(':checked'))   
		{ 
		if (document.getElementsByName("equity_amount")[0].value == '') 
		{
		document.getElementById("equity_amount_lbl").innerHTML="Please Enter Existing Equity";
		signalError("equity_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.equity_amount').offset().top});
		} 
		else
		{
		document.getElementById("equity_amount_lbl").innerHTML="";
		signalError("equity_amount", false, true, true);
		}	
		}
		//    

		if(jQuery('#W6ConsDPaymentFrom8').is(':checked'))   
		{ 
		if (document.getElementsByName("sweatequity_amount")[0].value == '') 
		{
		document.getElementById("sweatequity_amount_lbl").innerHTML="Please Enter Sweat Equity";
		signalError("sweatequity_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.sweatequity_amount').offset().top});
		} 
		else   
		{
		document.getElementById("sweatequity_amount_lbl").innerHTML="";
		signalError("sweatequity_amount", false, true, true);
		}   

		}
		//
		if(jQuery('#W6ConsDPaymentFrom10').is(':checked'))   
		{ 
		if (document.getElementsByName("finance_amount")[0].value == '') 
		{
		document.getElementById("finance_amount_lbl").innerHTML="Please Enter Secondary Financing";
		signalError("finance_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.finance_amount').offset().top});
		} 
		else   
		{
		document.getElementById("finance_amount_lbl").innerHTML="";
		signalError("finance_amount", false, true, true);
		}   

		}
		
		//
		if(jQuery('#W6ConsDPaymentFrom6').is(':checked'))   
		{ 
		if (document.getElementsByName("other_amount")[0].value == '') 
		{
		document.getElementById("other_amount_lbl").innerHTML="Please Enter Other Amount";
		signalError("other_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.other_amount').offset().top});
		} 
		else   
		{
		document.getElementById("other_amount_lbl").innerHTML="";
		signalError("other_amount", false, true, true);
		}   

		}
		
		}

		//

		//approved=>3rd button


		if (document.getElementById("W3Goal2Look1").checked == true)  
		{  

		if (document.getElementsByName("W6TxtConsPossession")[0].value == '') {
		document.getElementById("W6TxtConsPossession_lbl").innerHTML="Please Enter Date";  
		signalError("W6TxtConsPossession", true, true, true);
		mCheck = true;
		jQuery("#W6TxtConsPossession").removeClass("ifocus"); 
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtConsPossession').offset().top});
		} else        
		{
		document.getElementById("W6TxtConsPossession_lbl").innerHTML="";
		signalError("W6TxtConsPossession", false, true, true);
		}
		}
		//
		if (document.getElementById("W7finansingrahul1").checked == true)  
		{  

		if (document.getElementsByName("W6TxtConsFinancingDate")[0].value == '') {   
		document.getElementById("W6TxtConsFinancingDate_lbl").innerHTML="Please Enter Date";  
		signalError("W6TxtConsFinancingDate", true, true, true);
		mCheck = true;
		jQuery("#W6TxtConsFinancingDate").removeClass("ifocus"); 
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtConsFinancingDate').offset().top});
		} else 
		{
		document.getElementById("W6TxtConsFinancingDate_lbl").innerHTML="";
		signalError("W6TxtConsFinancingDate", false, true, true);
		}
		}
		//

		//
		if (document.getElementById("W3Goal2Look1").checked == true)  
		{  

		if (document.getElementsByName("W6TxtConsPrice")[0].value == '') {
		document.getElementById("W6TxtConsPrice_lbl").innerHTML="Please Enter Purchase price";
		signalError("W6TxtConsPrice", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtConsPrice').offset().top});
		} else 
		{
		document.getElementById("W6TxtConsPrice_lbl").innerHTML="";
		signalError("W6TxtConsPrice", false, true, true);
		}
		}  


		//What does the purchase price of include?
		if (document.getElementById("W3Goal2Look1").checked == true)  
		{  

		if (jQuery('#W6ConsPrice1').is(':checked') || jQuery('#W6ConsPrice2').is(':checked')) 
		{  

		var radio = jQuery('input:radio[name="W6ConsWarranty"]:checked');
		if (radio.length == 0) {    
		radioError("W6ConsWarranty_lbl", true);    
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsWarranty1').offset().top});					
		} else {   
		radioError("W6ConsWarranty_lbl", false);
		}

		}	
		}	
		//
		if (document.getElementById("W3Goal2Look1").checked == true)  
		{  

		var radio = jQuery('input:radio[name="W6ConsPrice"]:checked');
		if (radio.length == 0) {    
		radioError("W6ConsPrice_lbl", true);  
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsPrice1').offset().top});
		} else {   
		radioError("W6ConsPrice_lbl", false);
		}

		}
		//
		if (document.getElementById("W3Goal2Look1").checked == true)  
		{  

		var radio = jQuery('input:radio[name="W6ConsLandAmt"]:checked');
		if (radio.length == 0) {    
		radioError("W6ConsLandAmt_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsLandAmt1').offset().top});					
		} else {   
		radioError("W6ConsLandAmt_lbl", false);
		}   
		}      
		// if (document.getElementById("W3Goal2Look1").checked == true)  
		// {  

		// var radio = jQuery('input:radio[name="W6ConsNoCondition"]:checked');
		// if (radio.length == 0) {        
		// radioError("W6ConsNoCondition_lbl", true);
		// mCheck = true;   
		// jQuery('html,body').animate({scrollTop: jQuery('#W6ConsNoCondition1').offset().top});							
		// } else {   
		// radioError("W6ConsNoCondition_lbl", false);
		// }


		// }    

		///					  
		if (document.getElementById("W3Goal2Look4").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPortPrice")[0].value == '') {
		document.getElementById("W6TxtPortPrice_lbl").innerHTML="Please Enter Purchase price";
		signalError("W6TxtPortPrice", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPortPrice').offset().top});
		} else 
		{
		document.getElementById("W6TxtPortPrice_lbl").innerHTML="";
		signalError("W6TxtPortPrice", false, true, true);
		}
		} 	
		if (document.getElementById("W3Goal2Look4").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPortPossession")[0].value == '') {
		document.getElementById("W6TxtPortPossession_lbl").innerHTML="Please Enter Date";  
		signalError("W6TxtPortPossession", true, true, true);
		mCheck = true;
		jQuery("#W6TxtPortPossession").removeClass("ifocus"); 
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPortPossession').offset().top});
		} else        
		{
		document.getElementById("W6TxtPortPossession_lbl").innerHTML="";
		signalError("W6TxtPortPossession", false, true, true);
		}
		}
		//
		if (document.getElementById("W71finansingrahul1").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPortFinancingDate")[0].value == '') {   
		document.getElementById("W6TxtPortFinancingDate_lbl").innerHTML="Please Enter Date";  
		signalError("W6TxtPortFinancingDate", true, true, true);
		mCheck = true;
		jQuery("#W6TxtPortFinancingDate").removeClass("ifocus"); 
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPortFinancingDate').offset().top});
		} else   
		{
		document.getElementById("W6TxtPortFinancingDate_lbl").innerHTML="";
		signalError("W6TxtPortFinancingDate", false, true, true);
		}
		}	
		//
		if (document.getElementById("W3Goal2Look4").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPortCrntBalance")[0].value == '') {   
		document.getElementById("W6TxtPortCrntBalance_lbl").innerHTML="Please Enter Current balance";  
		signalError("W6TxtPortCrntBalance", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPortCrntBalance').offset().top});
		} else   
		{
		document.getElementById("W6TxtPortCrntBalance_lbl").innerHTML="";
		signalError("W6TxtPortCrntBalance", false, true, true);
		}
		}	
		//
		if (document.getElementById("W3Goal2Look4").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPortCrntInterestRate")[0].value == '') {   
		document.getElementById("W6TxtPortCrntInterestRate_lbl").innerHTML="Please Enter Interest Rate";  
		signalError("W6TxtPortCrntInterestRate", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPortCrntInterestRate').offset().top});
		} else   
		{
		document.getElementById("W6TxtPortCrntInterestRate_lbl").innerHTML="";
		signalError("W6TxtPortCrntInterestRate", false, true, true);
		}
		}	
		//					
		if (document.getElementById("W3Goal2Look4").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPortCrntPayment")[0].value == '') {   
		document.getElementById("W6TxtPortCrntPayment_lbl").innerHTML="Please Enter Monthly Payment";  
		signalError("W6TxtPortCrntPayment", true, true, true);   
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPortCrntPayment').offset().top});
		} else   
		{
		document.getElementById("W6TxtPortCrntPayment_lbl").innerHTML="";
		signalError("W6TxtPortCrntPayment", false, true, true);
		}
		}		

		if (document.getElementById("W3Goal2Look4").checked == true)  
		{     

		if (document.getElementsByName("W2Txtrdate")[0].value == '') {      
		document.getElementById("W2Txtrdate_lbl").innerHTML="Please Enter Date";  
		signalError("W2Txtrdate", true, true, true);
		mCheck = true;    
		jQuery("#W2Txtrdate").removeClass("ifocus"); 
		jQuery('html,body').animate({scrollTop: jQuery('#W2Txtrdate').offset().top});
		} else   
		{
		document.getElementById("W2Txtrdate_lbl").innerHTML="";
		signalError("W2Txtrdate", false, true, true);
		}
		}	   

		if (document.getElementById("W3Goal2Look4").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPortMortgageProvider")[0].value == '') {      
		document.getElementById("W6TxtPortMortgageProvider_lbl").innerHTML="Please Enter Company Name";  
		signalError("W6TxtPortMortgageProvider", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPortMortgageProvider').offset().top});
		} else      
		{
		document.getElementById("W6TxtPortMortgageProvider_lbl").innerHTML="";
		signalError("W6TxtPortMortgageProvider", false, true, true);
		}
		}	
		// if (document.getElementById("W3Goal2Look4").checked == true)  
		// {  

		// var radio = jQuery('input:radio[name="W6PortNoCondition"]:checked');
		// if (radio.length == 0) {        
		// radioError("W6PortNoCondition_lbl", true);   
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W6PortNoCondition1').offset().top});					
		// } else {   
		// radioError("W6PortNoCondition_lbl", false);
		// }


		// }  	
		if (document.getElementById("W3Goal2Look4").checked == true)  
		{  

		var radio = jQuery('input:radio[name="W6PortPropertyTaxesIncluded"]:checked');
		if (radio.length == 0) {        
		radioError("W6PortPropertyTaxesIncluded_lbl", true);
		mCheck = true;     
		} else {   
		radioError("W6PortPropertyTaxesIncluded_lbl", false);
		}


		}  		








		//How much money are you putting as a down payment?

		if (document.getElementById("W3Goal2Look2").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPurcDownPayment")[0].value == '') {
		document.getElementById("W6TxtPurcDownPayment_lbl").innerHTML="Please Enter down payment";
		signalError("W6TxtPurcDownPayment", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPurcDownPayment').offset().top});
		} else 
		{
		document.getElementById("W6TxtPurcDownPayment_lbl").innerHTML="";
		signalError("W6TxtPurcDownPayment", false, true, true);
		}
		}  
		//How much money are you putting as a down payment?
		if (document.getElementById("W3Goal2Look3").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPImpDPayment")[0].value == '') {
		document.getElementById("W6TxtPImpDPayment_lbl").innerHTML="Please Enter down payment";
		signalError("W6TxtPImpDPayment", true, true, true);
		mCheck = true;
		// location.href="#W6TxtPImpDPayment";
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPImpDPayment').offset().top});
		} else 
		{
		document.getElementById("W6TxtPImpDPayment_lbl").innerHTML="";
		signalError("W6TxtPImpDPayment", false, true, true);
		}
		}
		////
		if (document.getElementById("W3Goal2Look3").checked == true)  
		{  						 ////down payment
		if(jQuery('#W6PImpDPaymentFrom1').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom2').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom3').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom4').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom6').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom7').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom8').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom9').is(':checked') 
		|| jQuery('#W6PImpDPaymentFrom10').is(':checked') 
		)   
		{
		radioError("W6PImpDPaymentFrom1_lbl", false);
		radioError("W6PImpDPaymentFrom2_lbl", false);
		radioError("W6PImpDPaymentFrom3_lbl", false);
		radioError("W6PImpDPaymentFrom4_lbl", false);
		radioError("W6PImpDPaymentFrom_lbl", false);
		radioError("W6PImpDPaymentFrom6_lbl", false);
		radioError("W6PImpDPaymentFrom7_lbl", false);
		radioError("W6PImpDPaymentFrom8_lbl", false);
		radioError("W6PImpDPaymentFrom9_lbl", false);
		radioError("W6PImpDPaymentFrom10_lbl", false);
		}


		else   
		{

		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom1"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom1_lbl", true);
		mCheck = true;
		// location.href="#W6PImpDPaymentFrom1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6PImpDPaymentFrom1').offset().top});
		} else {
		radioError("W6PImpDPaymentFrom1_lbl", false);
		}  
		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom2"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom2_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PImpDPaymentFrom2_lbl", false);
		} 


		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom3"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom3_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PImpDPaymentFrom3_lbl", false);
		} 

		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom4"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom4_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PImpDPaymentFrom4_lbl", false);
		}  
		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PImpDPaymentFrom_lbl", false);
		}  
		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom6"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom6_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PImpDPaymentFrom6_lbl", false);
		} 
		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom7"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom7_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PImpDPaymentFrom7_lbl", false);
		} 
		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom8"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom8_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PImpDPaymentFrom8_lbl", false);
		} 
		//// 
		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom9"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom9_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PImpDPaymentFrom9_lbl", false);
		} 
		var radio = jQuery('input:radio[name="W6PImpDPaymentFrom10"]:checked');
		if (radio.length == 0) {
		radioError("W6PImpDPaymentFrom10_lbl", true);
		mCheck = true;
		} else {   
		radioError("W6PImpDPaymentFrom10_lbl", false);
		}

		}
		
		// end
		
		if(jQuery('#W6PImpDPaymentFrom1').is(':checked'))   
		{    
		//
		if (document.getElementsByName("bank_amount3")[0].value == '') 
		{
		document.getElementById("bank_amount3_lbl").innerHTML="Please Enter Bank Account Chequing/Savings";
		signalError("bank_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.bank_amount').offset().top});
		} 
		else
		{
		document.getElementById("bank_amount3_lbl").innerHTML="";
		signalError("bank_amount3", false, true, true);
		}
		}
		//
		if(jQuery('#W6PImpDPaymentFrom2').is(':checked'))   
		{ 
		if (document.getElementsByName("RRSPs_amount3")[0].value == '') 
		{
		document.getElementById("RRSPs_amount3_lbl").innerHTML="Please Enter RRSPs or Investments";
		signalError("RRSPs_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.RRSPs_amount').offset().top});
		} 
		else
		{
		document.getElementById("RRSPs_amount3_lbl").innerHTML="";
		signalError("RRSPs_amount3", false, true, true);
		}
		}
		//
		if(jQuery('#W6PImpDPaymentFrom3').is(':checked'))   
		{ 
		if (document.getElementsByName("borrowed_amount3")[0].value == '') 
		{
		document.getElementById("borrowed_amount3_lbl").innerHTML="Please Enter Borrowed(e.g LOC)";
		signalError("borrowed_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.borrowed_amount').offset().top});
		} 
		else
		{
		document.getElementById("borrowed_amount3_lbl").innerHTML="";
		signalError("borrowed_amount3", false, true, true);
		}
		}
		//
		if(jQuery('#W6PImpDPaymentFrom4').is(':checked'))   
		{ 
		if (document.getElementsByName("sale_amount3")[0].value == '') 
		{
		document.getElementById("sale_amount3_lbl").innerHTML="Please Enter Sale of Asset";
		signalError("sale_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.sale_amount').offset().top});
		} 
		else
		{
		document.getElementById("sale_amount3_lbl").innerHTML="";
		signalError("sale_amount3", false, true, true);
		}
		}
		//
		if(jQuery('#W6PImpDPaymentFrom5').is(':checked'))   
		{ 
		if (document.getElementsByName("gift_amount3")[0].value == '') 
		{
		document.getElementById("gift_amount3_lbl").innerHTML="Please Enter Gift Amount";
		signalError("gift_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.gift_amount').offset().top});
		} 
		else
		{
		document.getElementById("gift_amount3_lbl").innerHTML="";
		signalError("gift_amount3", false, true, true);
		}
		}

		//
		if(jQuery('#W6PImpDPaymentFrom7').is(':checked'))   
		{ 
		if (document.getElementsByName("cash_amount3")[0].value == '') 
		{
		document.getElementById("cash_amount3_lbl").innerHTML="Please Enter Personal Cash";
		signalError("cash_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.cash_amount').offset().top});
		} 
		else
		{
		document.getElementById("cash_amount3_lbl").innerHTML="";
		signalError("cash_amount3", false, true, true);
		}
		}
		//
		if(jQuery('#W6PImpDPaymentFrom9').is(':checked'))   
		{ 
		if (document.getElementsByName("equity_amount3")[0].value == '') 
		{
		document.getElementById("equity_amount3_lbl").innerHTML="Please Enter Existing Equity";
		signalError("equity_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.equity_amount').offset().top});
		} 
		else
		{
		document.getElementById("equity_amount3_lbl").innerHTML="";
		signalError("equity_amount3", false, true, true);
		}	
		}
		//    

		if(jQuery('#W6PImpDPaymentFrom8').is(':checked'))   
		{ 
		if (document.getElementsByName("sweatequity_amount3")[0].value == '') 
		{
		document.getElementById("sweatequity_amount3_lbl").innerHTML="Please Enter Sweat Equity";
		signalError("sweatequity_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.sweatequity_amount').offset().top});
		} 
		else   
		{
		document.getElementById("sweatequity_amount3_lbl").innerHTML="";
		signalError("sweatequity_amount3", false, true, true);
		}   

		}
		//
		if(jQuery('#W6PImpDPaymentFrom10').is(':checked'))   
		{ 
		if (document.getElementsByName("finance_amount3")[0].value == '') 
		{
		document.getElementById("finance_amount3_lbl").innerHTML="Please Enter Secondary Financing";
		signalError("finance_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.finance_amount').offset().top});
		} 
		else   
		{
		document.getElementById("finance_amount3_lbl").innerHTML="";
		signalError("finance_amount3", false, true, true);
		}   

		}
		
		//
		if(jQuery('#W6PImpDPaymentFrom6').is(':checked'))   
		{ 
		if (document.getElementsByName("other_amount3")[0].value == '') 
		{
		document.getElementById("other_amount3_lbl").innerHTML="Please Enter Other Amount";
		signalError("other_amount3", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.other_amount').offset().top});
		} 
		else   
		{
		document.getElementById("other_amount3_lbl").innerHTML="";
		signalError("other_amount", false, true, true);
		}   

		}
		
		}
		if (document.getElementById("W3Goal2Look3").checked == true)  
		{     

		if (document.getElementsByName("W6TxtPimpHighDesc")[0].value == '') {
		document.getElementById("W6TxtPimpHighDesc_lbl").innerHTML="Please Enter Description";  
		signalError("W6TxtPimpHighDesc", true, true, true);
		mCheck = true; 
		// location.href="#W6TxtPimpHighDesc";	   
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPimpHighDesc').offset().top});	
		} else 
		{
		document.getElementById("W6TxtPimpHighDesc_lbl").innerHTML="";
		signalError("W6TxtPimpHighDesc", false, true, true);
		}
		}	
		//

		if (document.getElementById("W3Goal2Look3").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPImpPossession")[0].value == '') {
		document.getElementById("W6TxtPImpPossession_lbl").innerHTML="Please Enter Date";  
		signalError("W6TxtPImpPossession", true, true, true);
		mCheck = true;        
		jQuery("#W6TxtPImpPossession").removeClass("ifocus"); 
		// location.href="#W6TxtPImpPossession";
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPImpPossession').offset().top});
		} else     
		{
		document.getElementById("W6TxtPImpPossession_lbl").innerHTML="";
		signalError("W6TxtPImpPossession", false, true, true);
		}
		}
		//
		if (document.getElementById("W73finansingrahul1").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPImpFinancingDate")[0].value == '') {
			document.getElementById("W6TxtPImpFinancingDate_lbl").innerHTML="Please Enter Date";  
			signalError("W6TxtPImpFinancingDate", true, true, true);
			mCheck = true;
			jQuery("#W6TxtPImpFinancingDate").removeClass("ifocus"); 
			// location.href="#W6TxtPImpFinancingDate";   
			jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPImpFinancingDate').offset().top});
			} else 
			{
			document.getElementById("W6TxtPImpFinancingDate_lbl").innerHTML="";
			signalError("W6TxtPImpFinancingDate", false, true, true);
			}
		}
		//		

		// if (document.getElementById("W3Goal2Look3").checked == true)  
		// { 
		// var radio = jQuery('input:radio[name="W6PImpNoCondition"]:checked');
		// if (radio.length == 0) {        
		// radioError("W6PImpNoCondition_lbl", true);
		// mCheck = true;   
		////location.href="#W6PImpNoCondition1";	
		// jQuery('html,body').animate({scrollTop: jQuery('#W6PImpNoCondition1').offset().top});	 
		// } else {      
		// radioError("W6PImpNoCondition_lbl", false);
		// }

		// }

		if (document.getElementById("W3Goal2Look3").checked == true)  
		{  
			if (document.getElementsByName("W6TxtPImpRenovationAmount")[0].value == '') {
			document.getElementById("W6TxtPImpRenovationAmount_lbl").innerHTML="Please Enter Additional Financing";  
			signalError("W6TxtPImpRenovationAmount", true, true, true);
			mCheck = true;
			jQuery("#W6TxtPImpFinancingDate").removeClass("ifocus");
			// location.href="#W6TxtPImpRenovationAmount";
			jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPImpRenovationAmount').offset().top});
			} else 
			{
			document.getElementById("W6TxtPImpRenovationAmount_lbl").innerHTML="";
			signalError("W6TxtPImpRenovationAmount", false, true, true);
			}
		}   
		//
		if (document.getElementById("W3Goal2Look3").checked == true)  
		{
			if (document.getElementsByName("W6TxtPImpPrice")[0].value == '') {
				document.getElementById("W6TxtPImpPrice_lbl").innerHTML="Please Enter Purchase price";
				signalError("W6TxtPImpPrice", true, true, true);
				mCheck = true;
				jQuery("#W6TxtPImpFinancingDate").removeClass("ifocus");
				// location.href="#W6TxtPImpPrice";
				jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPImpPrice').offset().top});
				} else 
				{
				document.getElementById("W6TxtPImpPrice_lbl").innerHTML="";
				signalError("W6TxtPImpPrice", false, true, true);
				}
		}  
		if(document.getElementById("W3Goal2").checked == true)
		{

			window.scrollTo(0,0);    

		}

		////////What is the purchase price of the property

		if (document.getElementById("W3Goal2Look2").checked == true)  
		{     

		if (document.getElementsByName("W6TxtPurcPrice")[0].value =='') {
		document.getElementById("W6TxtPurcPrice_lbl").innerHTML="Please Enter purchase price";   
		signalError("W6TxtPurcPrice", true, true, true);
		mCheck = true;
		jQuery("#W6TxtPImpFinancingDate").removeClass("ifocus");
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPurcPrice').offset().top});
		} else 
		{
		document.getElementById("W6TxtPurcPrice_lbl").innerHTML="";
		signalError("W6TxtPurcPrice", false, true, true);
		}
		}   
		//    

		// if (document.getElementById("W3Goal2Look2").checked == true)  
		// { 
		// var radio = jQuery('input:radio[name="W6PurcNoCondition"]:checked');
		// if (radio.length == 0) {        
		// radioError("W6PurcNoCondition_lbl", true);
		// mCheck = true;        
		// } else {   
		// radioError("W6PurcNoCondition_lbl", false);
		// }
		// }
		////////What is the purchase price of the property
		if (document.getElementById("W3Goal2Look2").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPurcPossession")[0].value == '') {
		document.getElementById("W6TxtPurcPossession_lbl").innerHTML="Please Enter Possession Date";   
		signalError("W6TxtPurcPossession", true, true, true);
		mCheck = true;
		jQuery("#W6TxtPurcPossession").removeClass("ifocus"); 
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPurcPossession').offset().top});
		} else 
		{
		document.getElementById("W6TxtPurcPossession_lbl").innerHTML="";
		signalError("W6TxtPurcPossession", false, true, true);
		}
		}  
		//	
		if (document.getElementById("W72finansingrahul1").checked == true)  
		{  

		if (document.getElementsByName("W6TxtPurcFinancing")[0].value == '') {
		document.getElementById("W6TxtPurcFinancing_lbl").innerHTML="Please Enter Date";   
		signalError("W6TxtPurcFinancing", true, true, true);
		mCheck = true;     
		jQuery("#W6TxtPurcFinancing").removeClass("ifocus"); 						
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtPurcFinancing').offset().top});
		} else 
		{    
		document.getElementById("W6TxtPurcFinancing_lbl").innerHTML="";
		signalError("W6TxtPurcFinancing", false, true, true);
		}


		}
		if (document.getElementById("W3Goal2Look2").checked == true)  
		{

		////down payment
		if(jQuery('#W6PurcDPaymentFrom1').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom2').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom3').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom4').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom6').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom7').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom9').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom10').is(':checked') 
		|| jQuery('#W6PurcDPaymentFrom11').is(':checked') 
		)   
		{
		radioError("W6PurcDPaymentFrom1_lbl", false);
		radioError("W6PurcDPaymentFrom2_lbl", false);
		radioError("W6PurcDPaymentFrom3_lbl", false);
		radioError("W6PurcDPaymentFrom4_lbl", false);
		radioError("W6PurcDPaymentFrom_lbl", false);
		radioError("W6PurcDPaymentFrom6_lbl", false);
		radioError("W6PurcDPaymentFrom7_lbl", false);
		radioError("W6PurcDPaymentFrom9_lbl", false);
		radioError("W6PurcDPaymentFrom10_lbl", false);
		radioError("W6PurcDPaymentFrom11_lbl", false);
		}


		else
		{

		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom1"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom1_lbl", true);
		mCheck = true;
		} else {
		radioError("W6PurcDPaymentFrom1_lbl", false);
		}  

		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom2"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom2_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W6PurcDPaymentFrom2').offset().top});
		} else {  
		radioError("W6PurcDPaymentFrom2_lbl", false);
		}

		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom3"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom3_lbl", true);
		mCheck = true;
		} else {  
		radioError("W6PurcDPaymentFrom3_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom4"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom4_lbl", true);
		mCheck = true;
		} else {  
		radioError("W6PurcDPaymentFrom4_lbl", false);
		}

		//
		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom_lbl", true);
		mCheck = true;  
		} else {  
		radioError("W6PurcDPaymentFrom_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom6"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom6_lbl", true);
		mCheck = true;
		} else {  
		radioError("W6PurcDPaymentFrom6_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom7"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom7_lbl", true);
		mCheck = true;
		} else {  
		radioError("W6PurcDPaymentFrom7_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom9"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom9_lbl", true);
		mCheck = true;
		} else {  
		radioError("W6PurcDPaymentFrom9_lbl", false);
		}
		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom10"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom10_lbl", true);
		mCheck = true;
		} else {  
		radioError("W6PurcDPaymentFrom10_lbl", false);

		}
		var radio = jQuery('input:radio[name="W6PurcDPaymentFrom11"]:checked');
		if (radio.length == 0) {
		radioError("W6PurcDPaymentFrom11_lbl", true);
		mCheck = true;
		} else {  
		radioError("W6PurcDPaymentFrom11_lbl", false);

		}

		}
		
		// end
		
		if(jQuery('#W6PurcDPaymentFrom1').is(':checked'))   
		{    
		//
		if (document.getElementsByName("bank_amount2")[0].value == '') 
		{
		document.getElementById("bank_amount2_lbl").innerHTML="Please Enter Bank Account Chequing/Savings";
		signalError("bank_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.bank_amount').offset().top});
		} 
		else
		{
		document.getElementById("bank_amount2_lbl").innerHTML="";
		signalError("bank_amount2", false, true, true);
		}
		}
		//
		if(jQuery('#W6PurcDPaymentFrom2').is(':checked'))   
		{ 
		if (document.getElementsByName("RRSPs_amount2")[0].value == '') 
		{
		document.getElementById("RRSPs_amount2_lbl").innerHTML="Please Enter RRSPs or Investments";
		signalError("RRSPs_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.RRSPs_amount').offset().top});
		} 
		else
		{
		document.getElementById("RRSPs_amount2_lbl").innerHTML="";
		signalError("RRSPs_amount2", false, true, true);
		}
		}
		//
		if(jQuery('#W6PurcDPaymentFrom3').is(':checked'))   
		{ 
		if (document.getElementsByName("borrowed_amount2")[0].value == '') 
		{
		document.getElementById("borrowed_amount2_lbl").innerHTML="Please Enter Borrowed(e.g LOC)";
		signalError("borrowed_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.borrowed_amount').offset().top});
		} 
		else
		{
		document.getElementById("borrowed_amount2_lbl").innerHTML="";
		signalError("borrowed_amount2", false, true, true);
		}
		}
		//
		if(jQuery('#W6PurcDPaymentFrom4').is(':checked'))   
		{ 
		if (document.getElementsByName("sale_amount2")[0].value == '') 
		{
		document.getElementById("sale_amount2_lbl").innerHTML="Please Enter Sale of Asset";
		signalError("sale_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.sale_amount').offset().top});
		} 
		else
		{
		document.getElementById("sale_amount2_lbl").innerHTML="";
		signalError("sale_amount2", false, true, true);
		}
		}
		//
		if(jQuery('#W6PurcDPaymentFrom5').is(':checked'))   
		{ 
		if (document.getElementsByName("gift_amount2")[0].value == '') 
		{
		document.getElementById("gift_amount2_lbl").innerHTML="Please Enter Gift Amount";
		signalError("gift_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.gift_amount').offset().top});
		} 
		else
		{
		document.getElementById("gift_amount2_lbl").innerHTML="";
		signalError("gift_amount2", false, true, true);
		}
		}

		//
		if(jQuery('#W6PurcDPaymentFrom7').is(':checked'))   
		{ 
		if (document.getElementsByName("cash_amount2")[0].value == '') 
		{
		document.getElementById("cash_amount2_lbl").innerHTML="Please Enter Personal Cash";
		signalError("cash_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.cash_amount').offset().top});
		} 
		else
		{
		document.getElementById("cash_amount2_lbl").innerHTML="";
		signalError("cash_amount2", false, true, true);
		}
		}
		//
		if(jQuery('#W6PurcDPaymentFrom9').is(':checked'))   
		{ 
		if (document.getElementsByName("equity_amount2")[0].value == '') 
		{
		document.getElementById("equity_amount2_lbl").innerHTML="Please Enter Existing Equity";
		signalError("equity_amount", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.equity_amount').offset().top});
		} 
		else
		{
		document.getElementById("equity_amount2_lbl").innerHTML="";
		signalError("equity_amount2", false, true, true);
		}	
		}
		//    

		if(jQuery('#W6PurcDPaymentFrom10').is(':checked'))   
		{ 
		if (document.getElementsByName("sweatequity_amount2")[0].value == '') 
		{
		document.getElementById("sweatequity_amount2_lbl").innerHTML="Please Enter Sweat Equity";
		signalError("sweatequity_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.sweatequity_amount').offset().top});
		} 
		else   
		{
		document.getElementById("sweatequity_amount2_lbl").innerHTML="";
		signalError("sweatequity_amount2", false, true, true);
		}   

		}
		//
		if(jQuery('#W6PurcDPaymentFrom11').is(':checked'))   
		{ 
		if (document.getElementsByName("finance_amount2")[0].value == '') 
		{
		document.getElementById("finance_amount2_lbl").innerHTML="Please Enter Secondary Financing";
		signalError("finance_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.finance_amount').offset().top});
		} 
		else   
		{
		document.getElementById("finance_amount2_lbl").innerHTML="";
		signalError("finance_amount2", false, true, true);
		}   

		}
		
		//
		if(jQuery('#W6PurcDPaymentFrom6').is(':checked'))   
		{ 
		if (document.getElementsByName("other_amount2")[0].value == '') 
		{
		document.getElementById("other_amount2_lbl").innerHTML="Please Enter Other Amount";
		signalError("other_amount2", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.other_amount').offset().top});
		} 
		else   
		{
		document.getElementById("other_amount2_lbl").innerHTML="";
		signalError("other_amount2", false, true, true);
		}   

		}
		
		}

		if (document.getElementById("W3Goal3Look1").checked == true || document.getElementById("W3Goal3Look2").checked == true)  
		{    
		if (document.getElementsByName("W6TxtRefnCBalance")[0].value == '') {
		document.getElementById("W6TxtRefnCBalance_lbl").innerHTML="Please Enter Current Balance";   
		signalError("W6TxtRefnCBalance", true, true, true);
		mCheck = true;
		} else 
		{    
		document.getElementById("W6TxtRefnCBalance_lbl").innerHTML="";
		signalError("W6TxtRefnCBalance", false, true, true);
		}


		}   


		//
		if (document.getElementById("W3Goal3Look1").checked == true || document.getElementById("W3Goal3Look2").checked == true)  
		{    
		if (document.getElementsByName("W6TxtRefnInterestRate")[0].value == '') {
		document.getElementById("W6TxtRefnInterestRate_lbl").innerHTML="Please Enter Interest Rate";   
		signalError("W6TxtRefnInterestRate", true, true, true);
		mCheck = true;
		} else 
		{    
		document.getElementById("W6TxtRefnInterestRate_lbl").innerHTML="";
		signalError("W6TxtRefnInterestRate", false, true, true);
		}


		}
		//
		if (document.getElementById("W3Goal3Look1").checked == true || document.getElementById("W3Goal3Look2").checked == true)  
		{    
		if (document.getElementsByName("W6TxtRefnMortgagePayment")[0].value == '') {
		document.getElementById("W6TxtRefnMortgagePayment_lbl").innerHTML="Please Enter Payment ";   
		signalError("W6TxtRefnMortgagePayment", true, true, true);
		mCheck = true;  
		} else 
		{    
		document.getElementById("W6TxtRefnMortgagePayment_lbl").innerHTML="";
		signalError("W6TxtRefnMortgagePayment", false, true, true);
		}


		}   
		//

		if (document.getElementById("W3Goal3Look1").checked == true || document.getElementById("W3Goal3Look2").checked == true)  
		{     
		var radio = jQuery('input:radio[name="W6RefnIncludedAmount"]:checked');
		if (radio.length == 0) {        
		radioError("W6RefnIncludedAmount_lbl", true);
		mCheck = true;        
		} else {   
		radioError("W6RefnIncludedAmount_lbl", false);
		window.scrollTo(0,0);   
		}
		}    
		//       
		if (document.getElementById("W3Goal3Look1").checked == true || document.getElementById("W3Goal3Look2").checked == true)  
		{    
		if (document.getElementsByName("W6TxtRefnDate")[0].value == '') {
		document.getElementById("W6TxtRefnDate_lbl").innerHTML="Please Enter date ";   
		signalError("W6TxtRefnDate", true, true, true);
		mCheck = true;  
		} else 
		{    
		document.getElementById("W6TxtRefnDate_lbl").innerHTML="";
		signalError("W6TxtRefnDate", false, true, true);
		}


		} 
				
		//
		if (document.getElementById("W3Goal3Look3").checked == true || document.getElementById("W3Goal3Look4").checked == true  )  
		{       
		if (document.getElementsByName("W6TxtRPlusBalance")[0].value == '') {
		document.getElementById("W6TxtRPlusBalance_lbl").innerHTML="Please Enter date ";   
		signalError("W6TxtRPlusBalance", true, true, true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtRPlusBalance').offset().top});						
		} else 
		{    
		document.getElementById("W6TxtRPlusBalance_lbl").innerHTML="";
		signalError("W6TxtRPlusBalance", false, true, true);
		}		  


		}
		//
		if (document.getElementById("W3Goal3Look3").checked == true || document.getElementById("W3Goal3Look4").checked == true  )  
		{       
		if (document.getElementsByName("W6TxtRPlusCrntInterestRate")[0].value == '') {
		document.getElementById("W6TxtRPlusCrntInterestRate_lbl").innerHTML="Please Enter Rate ";   
		signalError("W6TxtRPlusCrntInterestRate", true, true, true);
		mCheck = true; 
		//location.href = "#W6TxtRPlusCrntInterestRate";
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtRPlusCrntInterestRate').offset().top});						
		} else 
		{    
		document.getElementById("W6TxtRPlusCrntInterestRate_lbl").innerHTML="";
		signalError("W6TxtRPlusCrntInterestRate", false, true, true);
		}		  


		}

		//
		if (document.getElementById("W3Goal3Look3").checked == true || document.getElementById("W3Goal3Look4").checked == true  )  
		{       
		if (document.getElementsByName("W6TxtRPlusCrntPayment")[0].value == '') {
		document.getElementById("W6TxtRPlusCrntPayment_lbl").innerHTML="Please Enter Monthly Payment ";   
		signalError("W6TxtRPlusCrntPayment", true, true, true);
		mCheck = true;  
		//location.href = "#W6TxtRPlusCrntPayment";
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtRPlusCrntPayment').offset().top});
		} else 
		{    
		document.getElementById("W6TxtRPlusCrntPayment_lbl").innerHTML="";
		signalError("W6TxtRPlusCrntPayment", false, true, true);
		}		  


		}
		//  
		if (document.getElementById("W3Goal3Look3").checked == true || document.getElementById("W3Goal3Look4").checked == true  )  
		{       
		var radio = jQuery('input:radio[name="W6RPlusPropertyTaxesIncluded"]:checked');
		if (radio.length == 0) {        
		radioError("W6RPlusPropertyTaxesIncluded_lbl", true);
		mCheck = true; 
		//location.href = "#W6RPlusPropertyTaxesIncluded1";	
		jQuery('html,body').animate({scrollTop: jQuery('#W6RPlusPropertyTaxesIncluded').offset().top});					
		} else {   
		radioError("W6RPlusPropertyTaxesIncluded_lbl", false);
		window.scrollTo(0,0);  
		}

		}  
		//       

		if (document.getElementById("W3Goal3Look3").checked == true || document.getElementById("W3Goal3Look4").checked == true )     
		{       
		if (document.getElementsByName("W6TxtRPlusRenewalDate")[0].value == '') 
		{
		document.getElementById("W6TxtRPlusRenewalDate_lbl").innerHTML="Please Enter date ";   
		signalError("W6TxtRPlusRenewalDate", true, true, true);
		mCheck = true; 
		//location.href = "#W6TxtRPlusRenewalDate";	
		jQuery('html,body').animate({scrollTop: jQuery('#W6TxtRPlusRenewalDate').offset().top});						
		} 
		else 
		{    
		document.getElementById("W6TxtRPlusRenewalDate_lbl").innerHTML="";
		signalError("W6TxtRPlusRenewalDate", false, true, true);
		}		  


		}  


		//
		if (document.getElementById("W3Goal3Look3").checked == true || document.getElementById("W3Goal3Look4").checked == true )  
		{       
		
		var t=jQuery("#W6RPlusAdditionalMoney1").prop('checked')

		//$(alert(t));   



		if(jQuery('#W6RPlusAdditionalMoney1').is(':checked') 
		|| jQuery('#W6RPlusAdditionalMoney2').is(':checked') 
		|| jQuery('#W6RPlusAdditionalMoney3').is(':checked')
		|| jQuery('#W6RPlusAdditionalMoney4').is(':checked') 
		|| jQuery('#W6RPlusAdditionalMoney5').is(':checked') 
		|| jQuery('#W6RPlusAdditionalMoney6').is(':checked') 
		|| jQuery('#W6RPlusAdditionalMoney7').is(':checked') 		
		|| jQuery('#W6RPlusAdditionalMoney8').is(':checked')  
		|| jQuery('#W6RPlusAdditionalMoney9').is(':checked') 
		|| jQuery('#W6RPlusAdditionalMoney10').is(':checked') 
		|| jQuery('#W6RPlusAdditionalMoney11').is(':checked') 
		|| jQuery('#W6RPlusAdditionalMoney12').is(':checked') 

		)   
		{
		//$(alert("hello"));  

		}


		else
		{    
		//jQuery('[name="W6RPlusAdditionalMoney"]').addClass('shadow')
		jQuery('input[name="W6RPlusAdditionalMoney"] + label').addClass('shadow');
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W6RPlusAdditionalMoney1').offset().top});
		}

		}
		

		//Where is your down payment coming from?
		var radios=jQuery('[name="W6PurcDPaymentFrom"]');
		for (var i = 0, length = radios.length; i < length; i++) {
		if (radios[i].checked) {
		if(radios[i].value =='6')
		{	
		if (document.getElementsByName("W6TxtPurcDescribePaymentFrom")[0].value == '') 
		{
		document.getElementById("W6TxtPurcDescribePaymentFrom_lbl").innerHTML="Please Enter Description";
		signalError("W6TxtPurcDescribePaymentFrom", true, true, true);
		mCheck = true;
		} else {
		document.getElementById("W6TxtPurcDescribePaymentFrom_lbl").innerHTML="";
		signalError("W6TxtPurcDescribePaymentFrom", false, true, true);
		}
		}
		}
		}


		}

		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}
		break;

		case 6:
		if(sval=='1')
		{
		//Case 9 AssetDetails
		//How much money do you normally have in your chequing and savings accounts after payday?
		// if (document.getElementsByName("W8TxtMoneyAccount")[0].value.trim() == 'How Much Money') {
		// signalError("W8TxtMoneyAccount", true, true, true);
		// mCheck = true;
		// } else {
		// signalError("W8TxtMoneyAccount", false, true, true);
		// }
		/**
		* STARTING OF THE VALIDATION
		*
		*
		*
		*
		*/     

		// Do you have any vehicles?
		if(document.getElementById("W3Goal1Look2").checked == true || document.getElementById("W3Goal1Look3").checked == true || document.getElementById("W3Goal1Look4").checked == true || document.getElementById("W3Goal1Look5").checked == true || document.getElementById("W3Goal1Look6").checked == true || document.getElementById("W3Goal1Look7").checked == true || document.getElementById("W3Goal1Look1").checked == true)
		{   

		if (document.getElementsByName("W8TxtMoneyAccount")[0].value == '') 
		{  
		document.getElementById("W8TxtMoneyAccount_lbl").innerHTML="Please Enter money";
		signalError("W8TxtMoneyAccount", true, true, true);
		mCheck = true;
		//location.href = "#W8TxtMoneyAccount";
		} 
		else
		{
		document.getElementById("W8TxtMoneyAccount_lbl").innerHTML="";
		signalError("W8TxtMoneyAccount", false, true, true);
		}	
		//
		if (document.getElementsByName("W8Txtcontents")[0].value == '') 
		{     
		document.getElementById("W8Txtcontents_lbl").innerHTML="Please Enter Value";
		signalError("W8Txtcontents", true, true, true);
		mCheck = true;
		//location.href = "#W8Txtcontents";
		} 
		else
		{
		document.getElementById("W8Txtcontents_lbl").innerHTML="";
		signalError("W8Txtcontents", false, true, true);
		}





		var radio = jQuery('input:radio[name="W8Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8Vehicles_lbl", true);
		mCheck = true; 
		//location.href = "#W8Vehicles1";	  				 
		}
		else
		{   
		radioError("W8Vehicles_lbl", false);
		}
		//	

		if (document.getElementById("W8Vehicles1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1Vehicles_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1Vehicles1";							 
		}
		else  
		{   
		radioError("W8More1Vehicles_lbl", false);
		}

		}	
		//
		if (document.getElementById("W8More1Vehicles1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2Vehicles_lbl", true);
		mCheck = true;  
		//location.href = "#W8More2Vehicles1";						 
		}
		else
		{   
		radioError("W8More2Vehicles_lbl", false);
		}

		}





		//

		if (document.getElementById("W8More2Vehicles1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3Vehicles_lbl", true);
		mCheck = true;  
		//location.href = "#W8More3Vehicles1";						 
		}
		else
		{   
		radioError("W8More3Vehicles_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More3Vehicles1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More4Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More4Vehicles_lbl", true);
		mCheck = true;
		//location.href = "#W8More4Vehicles1";						 
		}
		else
		{   
		radioError("W8More4Vehicles_lbl", false);
		}

		}  
		//	




		// Do you have any RRSP's?

		var radio = jQuery('input:radio[name="W8RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8RRSPs_lbl", true);
		mCheck = true; 
		//location.href = "#W8RRSPs1";					 
		}
		else
		{   
		radioError("W8RRSPs_lbl", false);
		}

		//

		if (document.getElementById("W8RRSPs1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1RRSPs_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1RRSPs1";
		}
		else
		{   
		radioError("W8More1RRSPs_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More1RRSPs1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2RRSPs_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2RRSPs1";						 
		}
		else
		{   
		radioError("W8More2RRSPs_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More2RRSPs1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3RRSPs_lbl", true);
		mCheck = true;
		//location.href = "#W8More3RRSPs1";						 
		}
		else
		{   
		radioError("W8More3RRSPs_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More3RRSPs1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More4RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More4RRSPs_lbl", true);
		mCheck = true;
		//location.href = "#W8More4RRSPs1";						 
		}
		else
		{   
		radioError("W8More4RRSPs_lbl", false);
		}

		}




		// Do you have any non- RRSP,s investments? (E.g. GICs, Term Deposits, Mutual Funds, Public or Private Stocks, Company Assets or other investments)

		var radio = jQuery('input:radio[name="W8NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8NonRSP_lbl", true);
		mCheck = true; 
		//location.href = "#W8NonRSP1";					 
		}
		else
		{   
		radioError("W8NonRSP_lbl", false);
		}

		//

		if (document.getElementById("W8NonRSP1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1NonRSP_lbl", true);
		mCheck = true;
		//location.href = "#W8More1NonRSP1";						 
		}
		else
		{   
		radioError("W8More1NonRSP_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More1NonRSP1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2NonRSP_lbl", true);
		mCheck = true;  
		//location.href = "#W8More2NonRSP1";						 
		}
		else
		{   
		radioError("W8More2NonRSP_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More2NonRSP1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3NonRSP_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3NonRSP1";						 
		}
		else
		{   
		radioError("W8More3NonRSP_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More3NonRSP1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More4NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More4NonRSP_lbl", true);
		mCheck = true; 
		//location.href = "#W8More4NonRSP1";						 
		}
		else
		{   
		radioError("W8More4NonRSP_lbl", false);
		}

		}
		

		// Do you own any Real Estate?

		var radio = jQuery('input:radio[name="W8REstate"]:checked');
		if (radio.length == 0)    
		{          
		radioError("W8REstate_lbl", true);
		mCheck = true; 
		//location.href = "#W8REstate1";					 
		}
		else
		{   
		radioError("W8REstate_lbl", false);
		}

		/////////////////////////////////////////last/////////////////


		//

		if (document.getElementById("W8REstate1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1REstate"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1REstate_lbl", true);
		mCheck = true;
		//location.href = "#W8More1REstate1";
		}
		else
		{   
		radioError("W8More1REstate_lbl", false);
		}


		var radio = jQuery('input:radio[name="W8More1RentalProperty"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1RentalProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1RentalProperty1";						 
		}
		else
		{     
		radioError("W8More1RentalProperty_lbl", false);
		}
		//SELLING PROPERTY 1

		var radio = jQuery('input:radio[name="W8More1RentalPropertySelling"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1RentalPropertySelling_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1RentalPropertySelling1";						 
		}
		else
		{   
		radioError("W8More1RentalPropertySelling_lbl", false);
		}
		//
		//

		var radio = jQuery('input:radio[name="W8More1MoreProperty"]:checked');
		if (radio.length == 0)    
		{           
		radioError("W8More1MoreProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1MoreProperty1";
		}
		else
		{   
		radioError("W8More1MoreProperty_lbl", false);
		}

		}
		//
		if (document.getElementById("W8More1REstate1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1Loans"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1Loans_lbl", true);
		mCheck = true;  
		//location.href = "#W8More1Loans1";						 
		}
		else
		{   
		radioError("W8More1Loans_lbl", false);
		}

		}
		//
		if (document.getElementById("W8More1Loans1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1Loans_2"]:checked');
		if (radio.length == 0)       
		{        
		radioError("W8More1Loans_2_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1Loans1_2";							 
		}
		else
		{   
		radioError("W8More1Loans_2_lbl", false);
		}

		}
		//



		if (document.getElementById("W8More1MoreProperty1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2REstate"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2REstate_lbl", true);
		mCheck = true;  
		//location.href = "#W8More2REstate1";							 
		}
		else
		{   
		radioError("W8More2REstate_lbl", false);
		}


		if(document.getElementById("W8More2REstate1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2Loans"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2Loans_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2Loans1";								 
		}
		else
		{   
		radioError("W8More2Loans_lbl", false);
		}
		}
		if(document.getElementById("W8More2Loans1").checked == true)

		{

		var radio = jQuery('input:radio[name="W8More1Loans_4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1Loans_4_lbl", true);
		mCheck = true;   
		//location.href = "#W8More1Loans1_4";							 
		}
		else
		{   
		radioError("W8More1Loans_4_lbl", false);
		}
		}



		var radio = jQuery('input:radio[name="W8More2RentalProperty"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2RentalProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2RentalProperty1";						 
		}
		else
		{   
		radioError("W8More2RentalProperty_lbl", false);
		}
		//SELLING PROPERTY2

		var radio = jQuery('input:radio[name="W8More2RentalPropertySelling"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2RentalPropertySelling_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2RentalPropertySelling1";						 
		}
		else
		{   
		radioError("W8More2RentalPropertySelling_lbl", false);
		}
		var radio = jQuery('input:radio[name="W8More2MoreProperty"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2MoreProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2MoreProperty1";						 
		}
		else
		{   
		radioError("W8More2MoreProperty_lbl", false);
		}


		}	
		//
		if(document.getElementById("W8More2MoreProperty1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3REstate"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3REstate_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3REstate1";							 
		}
		else
		{   
		radioError("W8More3REstate_lbl", false);
		}


		var radio = jQuery('input:radio[name="W8More3RentalProperty"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3RentalProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3RentalProperty1";								 
		}
		else
		{   
		radioError("W8More3RentalProperty_lbl", false);
		}
		//SELLING PROPERTY3

		var radio = jQuery('input:radio[name="W8More3RentalPropertySelling"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3RentalPropertySelling_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3RentalPropertySelling1";						 
		}
		else
		{   
		radioError("W8More3RentalPropertySelling_lbl", false);
		} 



		if(document.getElementById("W8More3REstate1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3Loans"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3Loans_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3Loans1";							 
		}
		else
		{   
		radioError("W8More3Loans_lbl", false);
		}
		}


		if(document.getElementById("W8More3Loans1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1Loans_6"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1Loans_6_lbl", true);
		mCheck = true;
		//location.href = "#W8More1Loans1_6";								 
		}
		else
		{      
		radioError("W8More1Loans_6_lbl", false);
		}
		}

		}	  

		//
		}    
		}
		else
		{ 

		if(jQuery(".W6ConsWhoLivinggreyout").is(':checked'))    
		{

		window.scrollTo(0,0);  

		}



		jQuery('#customField').val('');  



		var radio = jQuery('input:radio[name="W6ConsWhoLiving"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsWhoLiving_lbl", true);
		mCheck = true;
		// location.href="#W6ConsWhoLiving1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsWhoLiving1').offset().top});
		} else {  
		radioError("W6ConsWhoLiving_lbl", false);
		}
		////
		if(jQuery('#W6ConsWhoLiving2').is(':checked'))
		{
		var radio = jQuery('input:radio[name="W6ConsPayForHeat"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsPayForHeat_lbl", true);
		mCheck = true;
		// location.href="#W6ConsPayForHeat1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsPayForHeat1').offset().top});
		} else {  
		radioError("W6ConsPayForHeat_lbl", false);
		}
		}
		//

		//
		if(jQuery('#W6ConsWhoLiving3').is(':checked') ) 
		{
		var radio = jQuery('input:radio[name="W6ConsLegalSuite"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsLegalSuite_lbl", true);
		mCheck = true;
		// location.href="#W6ConsLegalSuite1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsLegalSuite1').offset().top});
		} else {  
		radioError("W6ConsLegalSuite_lbl", false);
		}
		}					 


		//
		var radio = jQuery('input:radio[name="W6ConsMortgageType"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsMortgageType_lbl", true);
		mCheck = true;
		// location.href="#W6ConsMortgageType1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsMortgageType1').offset().top});
		} else {  
		radioError("W6ConsMortgageType_lbl", false);
		}
		//



		if(jQuery('#W6ConsMortgageType2').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsVariableMortgage"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsVariableMortgage_lbl", true);
		mCheck = true;
		// location.href="#W6ConsVariableMortgage1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsVariableMortgage1').offset().top});
		} else {  
		radioError("W6ConsVariableMortgage_lbl", false);
		}
		}
		//


		if(jQuery('#W6ConsMortgageType1').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsTermLength"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsTermLength_lbl", true);
		mCheck = true;
		// location.href="#W6ConsTermLength1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsTermLength1').offset().top});
		} else {  
		radioError("W6ConsTermLength_lbl", false);
		}
		}	    
		//


		if(jQuery('#W6ConsTermLength1').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsBestOption"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsBestOption_lbl", true);
		mCheck = true;
		// location.href="#W6ConsBestOption1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsBestOption1').offset().top});
		} else {     
		radioError("W6ConsBestOption_lbl", false);
		}
		}

		//						
		if(jQuery('#W6ConsMortgageType3').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsCashbackTerm"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsCashbackTerm_lbl", true);
		mCheck = true;
		// location.href="#W6ConsCashbackTerm1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsCashbackTerm1').offset().top});
		} else {  
		radioError("W6ConsCashbackTerm_lbl", false);
		}
		}


		//
		if(jQuery('#W6ConsVariableMortgage1').is(':checked') || jQuery('#W6ConsVariableMortgage2').is(':checked')) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsFixedTerm"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsFixedTerm_lbl", true);
		mCheck = true;
		// location.href="#W6ConsFixedTerm1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsFixedTerm1').offset().top});
		} else {  
		radioError("W6ConsFixedTerm_lbl", false);
		}
		}


		// 

		if(jQuery('#W6ConsVariableMortgage3').is(':checked') ) 
		{	 

		var radio = jQuery('input:radio[name="W6ConsVariableTerm"]:checked');
		if (radio.length == 0) {  
		radioError("W6ConsVariableTerm_lbl", true);
		mCheck = true;
		// location.href="#W6ConsVariableTerm1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsVariableTerm1').offset().top});
		} else {  
		radioError("W6ConsVariableTerm_lbl", false);
		}
		}



		//


		var radio = jQuery('input:radio[name="W6ConsPaymentFrequency"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsPaymentFrequency_lbl", true);
		mCheck = true;
		// location.href="#W6ConsPaymentFrequency1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsPaymentFrequency1').offset().top});
		} else {  
		radioError("W6ConsPaymentFrequency_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6ConsAmortizationFor"]:checked');
		if (radio.length == 0) {
		radioError("W6ConsAmortizationFor_lbl", true);
		mCheck = true;
		//location.href="#W6ConsAmortizationFor1";
		jQuery('html,body').animate({scrollTop: jQuery('#W6ConsAmortizationFor1').offset().top});
		} else {  
		radioError("W6ConsAmortizationFor_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6Agree1"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree1_lbl", true);
		mCheck = true;
		//location.href="#W6Agree1_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree1_5').offset().top});
		} else {  
		radioError("W6Agree1_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6Agree2"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree2_lbl", true);
		mCheck = true;
		// location.href="#W6Agree2_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree2_5').offset().top});
		} else {  
		radioError("W6Agree2_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6Agree3"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree3_lbl", true);
		mCheck = true;
		// location.href="#W6Agree3_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree3_5').offset().top});
		} else {  
		radioError("W6Agree3_lbl", false);
		}		
		//
		var radio = jQuery('input:radio[name="W6Agree4"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree4_lbl", true);
		mCheck = true;
		// location.href="#W6Agree4_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree4_5').offset().top});
		} else {  
		radioError("W6Agree4_lbl", false);
		}	
		//
		var radio = jQuery('input:radio[name="W6Agree5"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree5_lbl", true);
		mCheck = true;
		//location.href="#W6Agree5_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree5_5').offset().top});
		} else {  
		radioError("W6Agree5_lbl", false);
		}
		//
		var radio = jQuery('input:radio[name="W6Agree6"]:checked');
		if (radio.length == 0) {
		radioError("W6Agree6_lbl", true);
		mCheck = true;
		// location.href="#W6Agree6_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree6_5').offset().top});
		} else {  
		radioError("W6Agree6_lbl", false);  
		}  
		//
		var radio = jQuery('input:radio[name="W6Agree7"]:checked');
		if (radio.length == 0) {   
		radioError("W6Agree7_lbl", true);
		mCheck = true;
		//location.href="#W6Agree7_5";
		jQuery('html,body').animate({scrollTop: jQuery('#W6Agree7_5').offset().top});
		} else {  
		radioError("W6Agree7_lbl", false);  
		}     					         




		}

		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}
		break;

		case 7:	
		if(sval=='1')
		{


		var radio = jQuery('input:radio[name="W9LiabilitiesOwed"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9LiabilitiesOwed_lbl", true);
		mCheck = true; 
		//location.href="#W9LiabilitiesOwed1";						 
		}
		else
		{   
		radioError("W9LiabilitiesOwed_lbl", false);
		}

		if(document.getElementById('W9LiabilitiesOwed1').checked==true)
		{

		var radio = jQuery('input:radio[name="W9MoreLoans1"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9MoreLoans1_lbl", true);
		mCheck = true; 
		//location.href="#W9MoreLoans1_1";							 
		}
		else
		{   
		radioError("W9MoreLoans1_lbl", false);
		}

		//
		var radio = jQuery('input:radio[name="W9MoreLoans2"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9MoreLoans2_lbl", true);
		mCheck = true;  
		//location.href="#W9MoreLoans2_1";							 
		}
		else
		{   
		radioError("W9MoreLoans2_lbl", false);
		}

		//
		var radio = jQuery('input:radio[name="W9MoreLoans3"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9MoreLoans3_lbl", true);
		mCheck = true;
		//location.href="#W9MoreLoans3_1";						 
		}
		else
		{   
		radioError("W9MoreLoans3_lbl", false);
		}


		//
		var radio = jQuery('input:radio[name="W9MoreLoans4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9MoreLoans4_lbl", true);
		mCheck = true;
		//location.href="#W9MoreLoans4_1";						 
		}    
		else
		{   
		radioError("W9MoreLoans4_lbl", false);
		}


		}

		//Case 10 LiabilitiesDetails
		}
		else
		{
		jQuery('#customField').val("");   				
		
		///////////////////////////////////////////////////////////8 th screen 80%/////////////////
		if(jQuery("#W3Goal2Look1").is(':checked')
		|| jQuery("#W3Goal2Look2").is(':checked')
		|| jQuery("#W3Goal2Look3").is(':checked')
		|| jQuery("#W3Goal2Look4").is(':checked')
		|| jQuery("#W3Goal3Look1").is(':checked')
		|| jQuery("#W3Goal3Look2").is(':checked')
		|| jQuery("#W3Goal3Look3").is(':checked')     
		|| jQuery("#W3Goal3Look4").is(':checked'))
		{
		if(jQuery("#W7IncomeEarned1").is(':checked') || jQuery("#W7IncomeEarned2").is(':checked') || jQuery("#W7IncomeEarned4").is(':checked'))        
		{
		radioError("W7IncomeEarned_lbl", false);
		radioError("W7IncomeEarned2_lbl", false);
		radioError("W7IncomeEarned4_lbl", false);

		}
		else
		{




		var radio = jQuery('input:radio[name="W7IncomeEarned"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarned_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarned1').offset().top});				
		}
		else
		{   
		radioError("W7IncomeEarned_lbl", false);
		}
		//alert('testing');  
		var radio = jQuery('input:radio[name="W7IncomeEarned2"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarned2_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarned2').offset().top});				
		}
		else
		{   
		radioError("W7IncomeEarned2_lbl", false);
		}
		var radio = jQuery('input:radio[name="W7IncomeEarned4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarned4_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarned4').offset().top});				
		}
		else      
		{   
		radioError("W7IncomeEarned4_lbl", false);
		}  
		}


		jQuery(".W7IncomeEarnedgout").is(':checked')
		{
		window.scrollTo(0,0);  

		}    
		/////////////////////////////	
		//
		if (document.getElementById("W7IncomeEarned1").checked == true)  
		{  
		var radio = jQuery('input:radio[name="W7HowPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7HowPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowPaid1').offset().top});					
		}
		else
		{   
		radioError("W7HowPaid_lbl", false);
		}

		//
		if(document.getElementById("W7HowPaid2").checked == true || document.getElementById("W7HowPaid5").checked == true)
		{
		var radio = jQuery('input:radio[name="W7OverTimePaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OverTimePaid_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7OverTimePaid1').offset().top});							
		}
		else
		{   
		radioError("W7OverTimePaid_lbl", false);
		}
		}


		//

		// 1ST APPLICANT SALARY BUTTON CHECK

		if(document.getElementById("W7HowPaid1").checked == true
		|| document.getElementById("W7HowPaid6").checked == true
		|| document.getElementById("W7HowPaid4").checked == true)
		{
		var radio = jQuery('input:radio[name="W7FrequentlyPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7FrequentlyPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7FrequentlyPaid1').offset().top});							
		}
		else
		{   
		radioError("W7FrequentlyPaid_lbl", false);
		}
		}

		// var radio = jQuery('input:radio[name="W7HowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7HowLongCJob_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJob1').offset().top});					
		// }
		// else
		// {   
		// radioError("W7HowLongCJob_lbl", false);
		// }

		//

		var radio = jQuery('input:radio[name="W7IndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IndustryType1').offset().top});					
		}
		else
		{   
		radioError("W7IndustryType_lbl", false);
		}

		//

		var radio = jQuery('input:radio[name="W7Considered"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7Considered_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7Considered1').offset().top});						
		}
		else
		{   
		radioError("W7Considered_lbl", false);
		}

		//////////////////////
		if (document.getElementsByName("W7TxtJobTitle")[0].value == '') 
		{
		document.getElementById("W7TxtJobTitle_lbl").innerHTML="Please Enter job title";
		signalError("W7TxtJobTitle", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtJobTitle').offset().top});
		} else {
		document.getElementById("W7TxtJobTitle_lbl").innerHTML="";
		signalError("W7TxtJobTitle", false, true, true);
		}	
		if (document.getElementsByName("W7TxtCompName")[0].value == '') 
		{
		document.getElementById("W7TxtCompName_lbl").innerHTML="Please Enter company name";
		signalError("W7TxtCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtCompName').offset().top});
		} else {
		document.getElementById("W7TxtCompName_lbl").innerHTML="";
		signalError("W7TxtCompName", false, true, true);
		}	

		//////////////////

		var radio = jQuery('input:radio[name="W7ChildSupport"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ChildSupport_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupport1').offset().top});					
		}
		else
		{   
		radioError("W7ChildSupport_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7OtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7OtherIncome1').offset().top});						
		}
		else
		{   
		radioError("W7OtherIncome_lbl", false);
		}

		var radio = jQuery('input:radio[name="W72Sourcesofincome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Sourcesofincome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72Sourcesofincome1').offset().top});						
		}
		else    
		{   
		radioError("W72Sourcesofincome_lbl", false);
		}

		//
		//supplementay questions for employed 
		if(document.getElementById("W72Sourcesofincome1").checked == true)
		{
		//
		if(document.getElementById("W72typeofincome1").checked == true
		|| document.getElementById("W72typeofincome2").checked == true
		|| document.getElementById("W72typeofincome3").checked == true
		|| document.getElementById("W72typeofincome4").checked == true
		//|| document.getElementById("W72typeofincome5").checked == true
		|| document.getElementById("W72typeofincome6").checked == true
		|| document.getElementById("W72typeofincome7").checked == true
		|| document.getElementById("W72typeofincome8").checked == true
		)
		{
		radioError("W72typeofincome1_lbl", false);
		radioError("W72typeofincome2_lbl", false);
		radioError("W72typeofincome3_lbl", false);
		radioError("W72typeofincome4_lbl", false);
		//radioError("W72typeofincome5_lbl", false);
		radioError("W72typeofincome6_lbl", false);
		radioError("W72typeofincome7_lbl", false);
		radioError("W72typeofincome8_lbl", false);
		}
		else
		{


		//1
		var radio = jQuery('input:radio[name="W72typeofincome1"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome1_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome1').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome1_lbl", false);
		}
		//2
		var radio = jQuery('input:radio[name="W72typeofincome2"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome2_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome2').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome2_lbl", false);
		}
		//3
		var radio = jQuery('input:radio[name="W72typeofincome3"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome3_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome3').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome3_lbl", false);
		}
		//4
		var radio = jQuery('input:radio[name="W72typeofincome4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome4_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome4').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome4_lbl", false);
		}
		//5
		var radio = jQuery('input:radio[name="W72typeofincome5"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome5_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome5').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome5_lbl", false);
		}
		//6
		var radio = jQuery('input:radio[name="W72typeofincome6"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome6_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome6').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome6_lbl", false);
		}
		//7
		var radio = jQuery('input:radio[name="W72typeofincome7"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome7_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome7').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome7_lbl", false);
		}
		//8
		var radio = jQuery('input:radio[name="W72typeofincome8"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72typeofincome8_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72typeofincome8').offset().top});							
		}
		else
		{   
		radioError("W72typeofincome8_lbl", false);
		}
		}//end else

		//

		if(jQuery('#W72typeofincome2').is(':checked'))   
		{    
		//interest
		if (document.getElementsByName("type_Interest")[0].value == '') 
		{
		document.getElementById("type_Interest_lbl").innerHTML="Please Enter ANNUAL Interest income ";
		signalError("type_Interest", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_Interest').offset().top});
		} 
		else
		{
		document.getElementById("type_Interest_lbl").innerHTML="";
		signalError("type_Interest", false, true, true);
		}
		}
		//Pension
		if(jQuery('#W72typeofincome3').is(':checked'))   
		{ 
		if (document.getElementsByName("type_Pension")[0].value == '') 
		{
		document.getElementById("type_Pension_lbl").innerHTML="Please Enter ANNUAL Pension income ";
		signalError("type_Pension", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_Pension').offset().top});
		} 
		else
		{
		document.getElementById("type_Pension_lbl").innerHTML="";
		signalError("type_Pension", false, true, true);
		}
		}
		//Rental
		if(jQuery('#W72typeofincome5').is(':checked'))   
		{ 
		if (document.getElementsByName("type_Rental")[0].value == '') 
		{
		document.getElementById("type_Rental_lbl").innerHTML="Please Enter ANNUAL Rental income ";
		signalError("type_Rental", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_Rental').offset().top});
		} 
		else
		{
		document.getElementById("type_Rental_lbl").innerHTML="";
		signalError("type_Rental", false, true, true);
		}
		}
		//Child Tax Credit
		if(jQuery('#W72typeofincome6').is(':checked'))   
		{ 
		if (document.getElementsByName("type_ChildTaxCredit")[0].value == '') 
		{
		document.getElementById("type_ChildTaxCredit_lbl").innerHTML="Please Enter ANNUAL ChildTaxCredit income ";
		signalError("type_ChildTaxCredit", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_ChildTaxCredit').offset().top});
		} 
		else
		{
		document.getElementById("type_ChildTaxCredit_lbl").innerHTML="";
		signalError("type_ChildTaxCredit", false, true, true);
		}
		}
		//Living Allowance
		if(jQuery('#W72typeofincome7').is(':checked'))   
		{ 
		if (document.getElementsByName("type_LivingAllowance")[0].value == '') 
		{
		document.getElementById("type_LivingAllowance_lbl").innerHTML="Please Enter ANNUAL Living Allowance income ";
		signalError("type_LivingAllowance", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_LivingAllowance').offset().top});
		} 
		else
		{
		document.getElementById("type_LivingAllowance_lbl").innerHTML="";
		signalError("type_LivingAllowance", false, true, true);
		}
		}

		//Car Allowance
		if(jQuery('#W72typeofincome8').is(':checked'))   
		{ 
		if (document.getElementsByName("type_CarAllowance")[0].value == '') 
		{
		document.getElementById("type_CarAllowance_lbl").innerHTML="Please Enter ANNUAL Car Allowance income ";
		signalError("type_CarAllowance", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_CarAllowance').offset().top});
		} 
		else
		{
		document.getElementById("type_CarAllowance_lbl").innerHTML="";
		signalError("type_CarAllowance", false, true, true);
		}
		}
		//Investment
		if(jQuery('#W72typeofincome1').is(':checked'))   
		{ 
		if (document.getElementsByName("type_Investment")[0].value == '') 
		{
		document.getElementById("type_Investment_lbl").innerHTML="Please Enter ANNUAL Investment income ";
		signalError("type_Investment", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_Investment').offset().top});
		} 
		else
		{
		document.getElementById("type_Investment_lbl").innerHTML="";
		signalError("type_Investment", false, true, true);
		}	
		}
		//other    

		if(jQuery('#W72typeofincome4').is(':checked'))   
		{ 
		if (document.getElementsByName("type_other")[0].value == '') 
		{
		document.getElementById("type_other_lbl").innerHTML="Please Enter ANNUAL other income ";
		signalError("type_other", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('.type_other').offset().top});
		} 
		else   
		{
		document.getElementById("type_other_lbl").innerHTML="";
		signalError("type_other", false, true, true);
		}   

		}  



		}	


		//




		}




		if(jQuery('#W7OtherIncome1').is(':checked'))   
		{ 
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeTwo"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeTwo_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeTwo1').offset().top});						
		}
		else
		{   
		radioError("W7IncomeEarnedIncomeTwo_lbl", false);
		}
		}


		//income employed 
		if(document.getElementById("W7IncomeEarnedIncomeTwo1").checked == true)
		{

		//SECONDINCOME

		var radio = jQuery('input:radio[name="W72HowPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72HowPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72HowPaid1').offset().top});					
		}
		else
		{   
		radioError("W72HowPaid_lbl", false);
		}
		if(document.getElementById("W72HowPaid2").checked == true || document.getElementById("W72HowPaid5").checked == true)
		{
		var radio = jQuery('input:radio[name="W72OverTimePaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72OverTimePaid_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72OverTimePaid1').offset().top});					
		}
		else
		{   
		radioError("W72OverTimePaid_lbl", false);
		}
		}	
		if(document.getElementById("W72HowPaid1").checked == true
		|| document.getElementById("W72HowPaid6").checked == true
		|| document.getElementById("W72HowPaid4").checked == true)
		{

		var radio = jQuery('input:radio[name="W72FrequentlyPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72FrequentlyPaid_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72FrequentlyPaid1').offset().top});						
		}
		else
		{   
		radioError("W72FrequentlyPaid_lbl", false);
		}
		}
		
		// var radio = jQuery('input:radio[name="W72HowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W72HowLongCJob_lbl", true);
		// mCheck = true;  
		// jQuery('html,body').animate({scrollTop: jQuery('#W72HowLongCJob1').offset().top});						
		// }
		// else
		// {   
		// radioError("W72HowLongCJob_lbl", false);
		// }
		
		var radio = jQuery('input:radio[name="W72IndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72IndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72IndustryType1').offset().top});					
		}
		else
		{   
		radioError("W72IndustryType_lbl", false);
		}
		var radio = jQuery('input:radio[name="W72Considered"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Considered_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72Considered1').offset().top});						
		}
		else
		{   
		radioError("W72Considered_lbl", false);
		}
		///
		//////////////////////
		if (document.getElementsByName("W72TxtJobTitle")[0].value == '') 
		{
		document.getElementById("W72TxtJobTitle_lbl").innerHTML="Please Enter job title";
		signalError("W72TxtJobTitle", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtJobTitle').offset().top});
		} else {
		document.getElementById("W72TxtJobTitle_lbl").innerHTML="";
		signalError("W72TxtJobTitle", false, true, true);
		}	
		if (document.getElementsByName("W72TxtCompName")[0].value == '') 
		{
		document.getElementById("W72TxtCompName_lbl").innerHTML="Please Enter company name";
		signalError("W72TxtCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtCompName').offset().top});
		} else {
		document.getElementById("W72TxtCompName_lbl").innerHTML="";
		signalError("W72TxtCompName", false, true, true);
		}	

		//////////////////			



		///
		var radio = jQuery('input:radio[name="W72OtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72OtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72OtherIncome1').offset().top});					
		}
		else
		{   
		radioError("W72OtherIncome_lbl", false);
		}      
		}

		if(jQuery('#W72OtherIncome1').is(':checked') || jQuery('#W72BothOtherIncome1').is(':checked') )  
		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeThree_lbl", true);  
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeThree').offset().top});					
		}
		else  
		{   
		radioError("W7IncomeEarnedIncomeThree_lbl", false);
		}


		}     
		//income 1 div for co-applicant when click on no button
		if(document.getElementById("W2SecondApp1").checked == true)  
		{
		if(document.getElementById("W7OtherIncome2").checked == true)  
		{
		var radio = jQuery('input:radio[name="W7HowPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7HowPaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowPaidSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7HowPaidSecApp_lbl", false);
		}
		//
		if(document.getElementById("W7HowPaidSecApp2").checked == true || document.getElementById("W7HowPaidSecApp5").checked == true)
		{
		var radio = jQuery('input:radio[name="W7OverTimePaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OverTimePaidSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7OverTimePaidSecApp1').offset().top});								
		}
		else
		{   
		radioError("W7OverTimePaidSecApp_lbl", false);
		}
		}
		//
		if(document.getElementById("W7HowPaidSecApp1").checked == true 
		|| document.getElementById("W7HowPaidSecApp4").checked == true
		|| document.getElementById("W7HowPaidSecApp6").checked == true)  
		{
		var radio = jQuery('input:radio[name="W7FrequentlyPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7FrequentlyPaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7FrequentlyPaidSecApp1').offset().top});							
		}
		else
		{   
		radioError("W7FrequentlyPaidSecApp_lbl", false);
		}

		}
		//
		// var radio = jQuery('input:radio[name="W7HowLongCJobSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7HowLongCJobSecApp_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJobSecApp1').offset().top});							
		// }
		// else
		// {   
		// radioError("W7HowLongCJobSecApp_lbl", false);
		// }
		//



		var radio = jQuery('input:radio[name="W7IndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IndustryTypeSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IndustryTypeSecApp1').offset().top});							
		}
		else
		{   
		radioError("W7IndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7ConsideredSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ConsideredSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W7ConsideredSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7ConsideredSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W7TxtJobTitleSecApp")[0].value == '') 
		{
		document.getElementById("W7TxtJobTitleSecApp_lbl").innerHTML="Please Enter job title";
		signalError("W7TxtJobTitleSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtJobTitleSecApp').offset().top});
		} else {
		document.getElementById("W7TxtJobTitleSecApp_lbl").innerHTML="";
		signalError("W7TxtJobTitleSecApp", false, true, true);
		}	
		if (document.getElementsByName("W7TxtCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W7TxtCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W7TxtCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtCompNameSecApp').offset().top});   
		} else {
		document.getElementById("W7TxtCompNameSecApp_lbl").innerHTML="";
		signalError("W7TxtCompNameSecApp", false, true, true);
		}	

		//////////////////	

		////
		var radio = jQuery('input:radio[name="W7ChildSupportCoapp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ChildSupportCoapp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportCoapp1').offset().top});						
		}
		else
		{   
		radioError("W7ChildSupportCoapp_lbl", false);
		}
		var radio = jQuery('input:radio[name="W7OtherIncomeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OtherIncomeSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7OtherIncomeSecApp1').offset().top});								
		}
		else
		{   
		radioError("W7OtherIncomeSecApp_lbl", false);
		}




		}





		}
		//income 2 div when click on yes button 

		if(document.getElementById("W7OtherIncomeSecAppTwo1").checked == true
		|| document.getElementById("W7SelfOtherIncomeSecApp1").checked == true 
		||	document.getElementById("W7OtherIncomeSecApp1").checked == true		
		)  
		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeTwoSecapp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeTwoSecapp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeTwoSecapp').offset().top});			   			
		}
		else   
		{   
		radioError("W7IncomeEarnedIncomeTwoSecapp_lbl", false);
		}



		}	


		//income 3 div
		if(document.getElementById("W72OtherIncomeSecApp1").checked == true || document.getElementById("W72SelfOtherIncomeSecApp1").checked == true || document.getElementById("W7OtherIncomeSecAppThree1").checked == true)

		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeThreeSecapp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeThreeSecapp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeThreeSecapp1').offset().top});					
		}
		else
		{   
		radioError("W7IncomeEarnedIncomeThreeSecapp_lbl", false);
		}

		}     





		////income 2 employed red alert for co-app

		if(document.getElementById("W7IncomeEarnedIncomeTwoSecapp1").checked == true)

		{

		var radio = jQuery('input:radio[name="W72HowPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72HowPaidSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72HowPaidSecApp1').offset().top});							
		}
		else
		{   
		radioError("W72HowPaidSecApp_lbl", false);
		}
		//


		if(jQuery("#W72HowPaidSecApp2").is(':checked') || jQuery("#W72HowPaidSecApp5").is(':checked'))
		{
		var radio = jQuery('input:radio[name="W72OverTimePaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72OverTimePaidSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W72OverTimePaidSecApp1').offset().top});						
		}
		else
		{   
		radioError("W72OverTimePaidSecApp_lbl", false);
		}
		}
		//

		if(jQuery("#W72HowPaidSecApp1").is(':checked')
		|| jQuery("#W72HowPaidSecApp4").is(':checked')
		|| jQuery("#W72HowPaidSecApp6").is(':checked')
		)
		{

		var radio = jQuery('input:radio[name="W72FrequentlyPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72FrequentlyPaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72FrequentlyPaidSecApp1').offset().top});						
		}
		else
		{   
		radioError("W72FrequentlyPaidSecApp_lbl", false);
		}


		}

		// var radio = jQuery('input:radio[name="W72HowLongCJobSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W72HowLongCJobSecApp_lbl", true);
		// mCheck = true;  
		// jQuery('html,body').animate({scrollTop: jQuery('#W72HowLongCJobSecApp1').offset().top});							
		// }
		// else
		// {   
		// radioError("W72HowLongCJobSecApp_lbl", false);
		// }
		//
		var radio = jQuery('input:radio[name="W72IndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72IndustryTypeSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W72IndustryTypeSecApp1').offset().top});							
		}
		else     
		{   
		radioError("W72IndustryTypeSecApp_lbl", false);
		}


		var radio = jQuery('input:radio[name="W72ConsideredSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72ConsideredSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72ConsideredSecApp1').offset().top});						
		}
		else
		{   
		radioError("W72ConsideredSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W72TxtJobTitleSecApp")[0].value == '') 
		{
		document.getElementById("W72TxtJobTitleSecApp_lbl").innerHTML="Please Enter job title";
		signalError("W72TxtJobTitleSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtJobTitleSecApp').offset().top});
		} else {
		document.getElementById("W72TxtJobTitleSecApp_lbl").innerHTML="";
		signalError("W72TxtJobTitleSecApp", false, true, true);
		}	
		if (document.getElementsByName("W72TxtCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W72TxtCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W72TxtCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtCompNameSecApp').offset().top});   
		} else {   
		document.getElementById("W72TxtCompNameSecApp_lbl").innerHTML="";
		signalError("W72TxtCompNameSecApp", false, true, true);
		}	

		//////////////////	
		////30/09/2014

		// var radio = jQuery('input:radio[name="W7ChildSupportCoapptwo"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7ChildSupportCoapptwo_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportCoapptwo1').offset().top});							
		// }
		// else
		// {   
		// radioError("W7ChildSupportCoapptwo_lbl", false);
		// }
		//



		var radio = jQuery('input:radio[name="W72OtherIncomeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72OtherIncomeSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72OtherIncomeSecApp1').offset().top});							
		}
		else
		{   
		radioError("W72OtherIncomeSecApp_lbl", false);
		}
		//


		}

		////income 2 self-employed red alert for co-app

		if(document.getElementById("W7IncomeEarnedIncomeTwoSecapp2").checked == true)

		{	

		var radio = jQuery('input:radio[name="W72SelfBusClassSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfBusClassSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfBusClassSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfBusClassSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W72TxtSelfAvgIncomeSecApp")[0].value == '') 
		{   
		document.getElementById("W72TxtSelfAvgIncomeSecApp_lbl").innerHTML="Please Enter annual income ";
		signalError("W72TxtSelfAvgIncomeSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtSelfAvgIncomeSecApp').offset().top});
		} else {
		document.getElementById("W72TxtSelfAvgIncomeSecApp_lbl").innerHTML="";
		signalError("W72TxtSelfAvgIncomeSecApp", false, true, true);
		}	
		if (document.getElementsByName("W72TxtSelfCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W72TxtSelfCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W72TxtSelfCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtSelfCompNameSecApp').offset().top});     
		} else {   
		document.getElementById("W72TxtSelfCompNameSecApp_lbl").innerHTML="";
		signalError("W72TxtSelfCompNameSecApp", false, true, true);
		}	

		//////////////////		

		////
		var radio = jQuery('input:radio[name="W72SelfHowLongCJobSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfHowLongCJobSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfHowLongCJobSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfHowLongCJobSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W72SelfIndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{            
		radioError("W72SelfIndustryTypeSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfIndustryTypeSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfIndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W72SelfContractEmpSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfContractEmpSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfContractEmpSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfContractEmpSecApp_lbl", false);
		}


		//30/09/2014
		// var radio = jQuery('input:radio[name="W72SelfChildSupportSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W72SelfChildSupportSecApp_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W72SelfChildSupportSecApp1').offset().top});					
		// }
		// else
		// {   
		// radioError("W72SelfChildSupportSecApp_lbl", false);
		// }
		
		var radio = jQuery('input:radio[name="W72SelfOtherIncomeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfOtherIncomeSecApp_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfOtherIncomeSecApp1').offset().top});					
		}
		else
		{   
		radioError("W72SelfOtherIncomeSecApp_lbl", false);
		}



		}
		////income 2 retired co-applicant
		if(document.getElementById("W7IncomeEarnedIncomeTwoSecapp4").checked == true)

		{
		var radio = jQuery('input:radio[name="W7HowLongCJobSecAppThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7HowLongCJobSecAppThree_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJobSecAppThree1').offset().top});						
		}
		else
		{   
		radioError("W7HowLongCJobSecAppThree_lbl", false);
		}

		//



		var radio = jQuery('input:radio[name="W7ChildSupportSecAppThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ChildSupportSecAppThree_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportSecAppThree1').offset().top});					
		}
		else
		{   
		radioError("W7ChildSupportSecAppThree_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7OtherIncomeSecAppThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OtherIncomeSecAppThree_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7OtherIncomeSecAppThree1').offset().top});					
		}
		else
		{   
		radioError("W7OtherIncomeSecAppThree_lbl", false);
		}
		}
		//		



		//income 3 employed for co-applicant

		if(document.getElementById("W7IncomeEarnedIncomeThreeSecapp1").checked == true)

		{
		var radio = jQuery('input:radio[name="W73HowPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73HowPaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73HowPaidSecApp1').offset().top});							
		}
		else
		{   
		radioError("W73HowPaidSecApp_lbl", false);
		}
		//
		if(jQuery("#W73HowPaidSecApp2").is(':checked') || jQuery("#W73HowPaidSecApp5").is(':checked'))
		{
		var radio = jQuery('input:radio[name="W73OverTimePaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73OverTimePaidSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73OverTimePaidSecApp1').offset().top});						
		}
		else
		{   
		radioError("W73OverTimePaidSecApp_lbl", false);
		}
		}
		//

		if(jQuery("#W73HowPaidSecApp1").is(':checked')
		|| jQuery("#W73HowPaidSecApp4").is(':checked')
		|| jQuery("#W73HowPaidSecApp6").is(':checked')
		)
		{

		var radio = jQuery('input:radio[name="W73FrequentlyPaidSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73FrequentlyPaidSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73FrequentlyPaidSecApp1').offset().top});							
		}
		else
		{   
		radioError("W73FrequentlyPaidSecApp_lbl", false);
		}


		}	



		//



		var radio = jQuery('input:radio[name="W73HowLongCJobSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73HowLongCJobSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73HowLongCJobSecApp1').offset().top});						
		}
		else
		{   
		radioError("W73HowLongCJobSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W73IndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73IndustryTypeSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73IndustryTypeSecApp1').offset().top});							
		}
		else
		{   
		radioError("W73IndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W73ConsideredSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73ConsideredSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73ConsideredSecApp1').offset().top});						
		}
		else
		{   
		radioError("W73ConsideredSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W73TxtJobTitleSecApp")[0].value == '') 
		{
		document.getElementById("W73TxtJobTitleSecApp_lbl").innerHTML="Please Enter job title";
		signalError("W73TxtJobTitleSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtJobTitleSecApp').offset().top});
		} else {
		document.getElementById("W73TxtJobTitleSecApp_lbl").innerHTML="";
		signalError("W73TxtJobTitleSecApp", false, true, true);
		}	
		if (document.getElementsByName("W73TxtCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W73TxtCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W73TxtCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtCompNameSecApp').offset().top});   
		} else {    
		document.getElementById("W73TxtCompNameSecApp_lbl").innerHTML="";
		signalError("W73TxtCompNameSecApp", false, true, true);
		}	

		//////////////////	

		////30/09/2014
		// var radio = jQuery('input:radio[name="W7ChildSupportCoappthree"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7ChildSupportCoappthree_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportCoappthree1').offset().top});						
		// }
		// else
		// {   
		// radioError("W7ChildSupportCoappthree_lbl", false);
		// }		







		}	


		////income3 self-employed red alert for co-applicant
		if(document.getElementById("W7IncomeEarnedIncomeThreeSecapp2").checked == true)

		{

		var radio = jQuery('input:radio[name="W73SelfBusClassSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfBusClassSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfBusClassSecApp1').offset().top});					
		}
		else
		{   
		radioError("W73SelfBusClassSecApp_lbl", false);
		}

		////
		//////////////////////
		if (document.getElementsByName("W73TxtSelfAvgIncomeSecApp")[0].value == '') 
		{   
		document.getElementById("W73TxtSelfAvgIncomeSecApp_lbl").innerHTML="Please Enter annual income ";
		signalError("W73TxtSelfAvgIncomeSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtSelfAvgIncomeSecApp').offset().top});
		} else {
		document.getElementById("W73TxtSelfAvgIncomeSecApp_lbl").innerHTML="";
		signalError("W73TxtSelfAvgIncomeSecApp", false, true, true);
		}	
		if (document.getElementsByName("W73TxtSelfCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W73TxtSelfCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W73TxtSelfCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtSelfCompNameSecApp').offset().top});     
		} else {   
		document.getElementById("W73TxtSelfCompNameSecApp_lbl").innerHTML="";
		signalError("W73TxtSelfCompNameSecApp", false, true, true);
		}	



		var radio = jQuery('input:radio[name="W73SelfHowLongCJobSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfHowLongCJobSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfHowLongCJobSecApp1').offset().top});					
		}
		else
		{   
		radioError("W73SelfHowLongCJobSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W73SelfIndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfIndustryTypeSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfIndustryTypeSecApp1').offset().top});					
		}
		else
		{   
		radioError("W73SelfIndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W73SelfContractEmpSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfContractEmpSecApp_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfContractEmpSecApp1').offset().top});					
		}
		else
		{   
		radioError("W73SelfContractEmpSecApp_lbl", false);
		}
		//


		//30/09/2014
		// var radio = jQuery('input:radio[name="W73SelfChildSupportSecApp"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W73SelfChildSupportSecApp_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W73SelfChildSupportSecApp1').offset().top});					
		// }
		// else
		// {   
		// radioError("W73SelfChildSupportSecApp_lbl", false);
		// }
		//

		}


		////income 3 retired red alert for co-applicant
		if(document.getElementById("W7IncomeEarnedIncomeThreeSecapp4").checked == true)

		{
		var radio = jQuery('input:radio[name="W7HowLongCJobSecAppFour"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7HowLongCJobSecAppFour_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJobSecAppFour1').offset().top})					
		}
		else
		{   
		radioError("W7HowLongCJobSecAppFour_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7ChildSupportSecAppFour"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7ChildSupportSecAppFour_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7ChildSupportSecAppFour1').offset().top})					
		}
		else
		{   
		radioError("W7ChildSupportSecAppFour_lbl", false);
		}     


		}					






		////income 2 self-employed red alert
		if(document.getElementById("W7IncomeEarnedIncomeTwo2").checked == true)

		{

		var radio = jQuery('input:radio[name="W72SelfBusClass"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfBusClass_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfBusClass1').offset().top});					
		}
		else
		{   
		radioError("W72SelfBusClass_lbl", false);
		}
		////
		//Rahul 12062014
		// var radio = jQuery('input:radio[name="W72SelfHowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W72SelfHowLongCJob_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W72SelfHowLongCJob1').offset().top});					
		// }
		// else
		// {   
		// radioError("W72SelfHowLongCJob_lbl", false);
		// }
		//////////////////////
		if (document.getElementsByName("W72TxtSelfAvgIncome")[0].value == '') 
		{   
		document.getElementById("W72TxtSelfAvgIncome_lbl").innerHTML="Please Enter annual income ";
		signalError("W72TxtSelfAvgIncome", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtSelfAvgIncome').offset().top});
		} else {
		document.getElementById("W72TxtSelfAvgIncome_lbl").innerHTML="";
		signalError("W72TxtSelfAvgIncome", false, true, true);
		}	
		if (document.getElementsByName("W72TxtSelfCompName")[0].value == '') 
		{
		document.getElementById("W72TxtSelfCompName_lbl").innerHTML="Please Enter company name";
		signalError("W72TxtSelfCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72TxtSelfCompName').offset().top});   
		} else {
		document.getElementById("W72TxtSelfCompName_lbl").innerHTML="";
		signalError("W72TxtSelfCompName", false, true, true);
		}	

		//////////////////	


		var radio = jQuery('input:radio[name="W72SelfIndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfIndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfIndustryType1').offset().top});						
		}
		else
		{   
		radioError("W72SelfIndustryType_lbl", false);
		}

		var radio = jQuery('input:radio[name="W72SelfContractEmp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfContractEmp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfContractEmp1').offset().top});						
		}
		else  
		{   
		radioError("W72SelfContractEmp_lbl", false);
		}

		var radio = jQuery('input:radio[name="W72SelfOtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfOtherIncome_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfOtherIncome1').offset().top});					
		}
		else
		{   
		radioError("W72SelfOtherIncome_lbl", false);
		}


		////	

		}


		if(document.getElementById("W72SelfOtherIncome1").checked == true)

		{     

		var radio = jQuery('input:radio[name="W7SelfIncomeEarnedIncomeThree"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfIncomeEarnedIncomeThree_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfIncomeEarnedIncomeThree1').offset().top});					
		}
		else
		{   
		radioError("W7SelfIncomeEarnedIncomeThree_lbl", false);
		}
		}












		////income 2 retired red alert
		if(document.getElementById("W7IncomeEarnedIncomeTwo4").checked == true)

		{

		var radio = jQuery('input:radio[name="W72BothHowLongCJob"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72BothHowLongCJob_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72BothHowLongCJob1').offset().top});		   			
		}
		else
		{   
		radioError("W72BothHowLongCJob_lbl", false);
		}


		var radio = jQuery('input:radio[name="W72BothOtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72BothOtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72BothOtherIncome1').offset().top});					
		}
		else
		{   
		radioError("W72BothOtherIncome_lbl", false);
		}

		}

		////////////////////////////////////////////




		//////////////////////////////////////////////





		if(document.getElementById("W7IncomeEarnedIncomeThree1").checked == true || document.getElementById("W7SelfIncomeEarnedIncomeThree1").checked == true)
		{

		//THIRDINCOME

		var radio = jQuery('input:radio[name="W73HowPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73HowPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73HowPaid1').offset().top});					
		}
		else
		{   
		radioError("W73HowPaid_lbl", false);
		}
		//



		if(document.getElementById("W73HowPaid2").checked == true || document.getElementById("W73HowPaid5").checked == true)
		{
		var radio = jQuery('input:radio[name="W73OverTimePaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73OverTimePaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73OverTimePaid1').offset().top});					
		}
		else
		{   
		radioError("W73OverTimePaid_lbl", false);
		}
		}
		//



		if(document.getElementById("W73HowPaid1").checked == true
		|| document.getElementById("W73HowPaid6").checked == true
		|| document.getElementById("W73HowPaid4").checked == true)
		{

		var radio = jQuery('input:radio[name="W73FrequentlyPaid"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73FrequentlyPaid_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73FrequentlyPaid1').offset().top});						
		}
		else
		{   
		radioError("W73FrequentlyPaid_lbl", false);
		}
		}
		//



		// var radio = jQuery('input:radio[name="W73HowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W73HowLongCJob_lbl", true);
		// mCheck = true; 
		// jQuery('html,body').animate({scrollTop: jQuery('#W73HowLongCJob1').offset().top});					
		// }
		// else
		// {   
		// radioError("W73HowLongCJob_lbl", false);
		// }


		var radio = jQuery('input:radio[name="W73IndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73IndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73IndustryType1').offset().top});					
		}
		else
		{   
		radioError("W73IndustryType_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W73Considered"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73Considered_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W73Considered1').offset().top});						
		}
		else
		{   
		radioError("W73Considered_lbl", false);
		}



		///////////
		//////////////////////
		if (document.getElementsByName("W73TxtJobTitle")[0].value == '') 
		{
		document.getElementById("W73TxtJobTitle_lbl").innerHTML="Please Enter job title";
		signalError("W73TxtJobTitle", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtJobTitle').offset().top});
		} 
		else {
		document.getElementById("W73TxtJobTitle_lbl").innerHTML="";
		signalError("W73TxtJobTitle", false, true, true);
		}	

		if (document.getElementsByName("W73TxtCompName")[0].value == '') 
		{
		document.getElementById("W73TxtCompName_lbl").innerHTML="Please Enter company name";
		signalError("W73TxtCompName", true, true, true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtCompName').offset().top});
		} else {
		document.getElementById("W73TxtCompName_lbl").innerHTML="";
		signalError("W73TxtCompName", false, true, true);
		}	
		} 
		//income 3 sel-employed red alert						   
		if(document.getElementById("W7IncomeEarnedIncomeThree2").checked == true || document.getElementById("W7SelfIncomeEarnedIncomeThree2").checked == true)
		{   
		var radio = jQuery('input:radio[name="W73SelfBusClass"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfBusClass_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfBusClass1').offset().top});					
		}
		else
		{   
		radioError("W73SelfBusClass_lbl", false);
		}
		////
		//////////////////////
		if (document.getElementsByName("W73TxtSelfAvgIncome")[0].value == '') 
		{   
		document.getElementById("W73TxtSelfAvgIncome_lbl").innerHTML="Please Enter annual income ";
		signalError("W73TxtSelfAvgIncome", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtSelfAvgIncome').offset().top});
		} else {
		document.getElementById("W73TxtSelfAvgIncome_lbl").innerHTML="";
		signalError("W73TxtSelfAvgIncome", false, true, true);
		}	
		if (document.getElementsByName("W73TxtSelfCompName")[0].value == '') 
		{
		document.getElementById("W73TxtSelfCompName_lbl").innerHTML="Please Enter company name";
		signalError("W73TxtSelfCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73TxtSelfCompName').offset().top});        
		document.getElementById("W73TxtSelfCompName_lbl").innerHTML="";
		signalError("W73TxtSelfCompName", false, true, true);
		}	

		//////////////////		
		////
		var radio = jQuery('input:radio[name="W73SelfHowLongCJob"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfHowLongCJob_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfHowLongCJob1').offset().top});						
		}
		else
		{   
		radioError("W73SelfHowLongCJob_lbl", false);
		}

		var radio = jQuery('input:radio[name="W73SelfIndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfIndustryType_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfIndustryType1').offset().top});						
		}
		else
		{   
		radioError("W73SelfIndustryType_lbl", false);
		}

		var radio = jQuery('input:radio[name="W73SelfContractEmp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W73SelfContractEmp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W73SelfContractEmp1').offset().top});						
		}
		else
		{   
		radioError("W73SelfContractEmp_lbl", false);
		}




		}	
		//income 3 red alert for retired  
		if(document.getElementById("W7IncomeEarnedIncomeThree4").checked == true || document.getElementById("W7SelfIncomeEarnedIncomeThree4").checked == true )
		{
		var radio = jQuery('input:radio[name="W72Both3HowLongCJob"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Both3HowLongCJob_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W72Both3HowLongCJob1').offset().top});			   			
		}
		else
		{   
		radioError("W72Both3HowLongCJob_lbl", false);
		}


		}			
		//income 1 sel-employed button on very first question on 80 %				  

		if(document.getElementById("W7IncomeEarned2").checked == true )
		{
		var radio = jQuery('input:radio[name="W7SelfBusClass"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfBusClass_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfBusClass1').offset().top});					
		}
		else
		{   
		radioError("W7SelfBusClass_lbl", false);
		}
		/////
		//////////////////////
		if (document.getElementsByName("W7TxtSelfAvgIncome")[0].value == '') 
		{   
		document.getElementById("W7TxtSelfAvgIncome_lbl").innerHTML="Please Enter annual income ";
		signalError("W7TxtSelfAvgIncome", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtSelfAvgIncome').offset().top});
		} 
		else {
		document.getElementById("W7TxtSelfAvgIncome_lbl").innerHTML="";
		signalError("W7TxtSelfAvgIncome", false, true, true);
		}

		if (document.getElementsByName("W7TxtSelfCompName")[0].value == '') 
		{
		document.getElementById("W7TxtSelfCompName_lbl").innerHTML="Please Enter company name";
		signalError("W7TxtSelfCompName", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtSelfCompName').offset().top});   
		} else {
		document.getElementById("W7TxtSelfCompName_lbl").innerHTML="";
		signalError("W7TxtSelfCompName", false, true, true);
		}	

		//////////////////				


		//////
		//Rahul 12062014
		// var radio = jQuery('input:radio[name="W7SelfHowLongCJob"]:checked');
		// if (radio.length == 0)    
		// {        
		// radioError("W7SelfHowLongCJob_lbl", true);
		// mCheck = true;  
		// jQuery('html,body').animate({scrollTop: jQuery('#W7SelfHowLongCJob1').offset().top});						
		// }
		// else
		// {   
		// radioError("W7SelfHowLongCJob_lbl", false);
		// }

		var radio = jQuery('input:radio[name="W7SelfIndustryType"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfIndustryType_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfIndustryType1').offset().top});					
		}
		else
		{   
		radioError("W7SelfIndustryType_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7SelfContractEmp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfContractEmp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfContractEmp1').offset().top});						
		}
		else
		{   
		radioError("W7SelfContractEmp_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7SelfChildSupport"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfChildSupport_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfChildSupport1').offset().top});					
		}
		else
		{   
		radioError("W7SelfChildSupport_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7SelfOtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfOtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfOtherIncome1').offset().top});					
		}
		else
		{   
		radioError("W7SelfOtherIncome_lbl", false);
		}


		var radio = jQuery('input:radio[name="W72SelfSourcesofincome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72SelfSourcesofincome_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W72SelfSourcesofincome1').offset().top});					
		}
		else
		{   
		radioError("W72SelfSourcesofincome_lbl", false);
		}
		if(document.getElementById("W72SelfSourcesofincome1").checked == true)
		{

		if(document.getElementById("W72Selftypeofincome1").checked == true
		|| document.getElementById("W72Selftypeofincome2").checked == true
		|| document.getElementById("W72Selftypeofincome3").checked == true
		|| document.getElementById("W72Selftypeofincome4").checked == true
		|| document.getElementById("W72Selftypeofincome5").checked == true
		|| document.getElementById("W72Selftypeofincome6").checked == true
		|| document.getElementById("W72Selftypeofincome7").checked == true
		|| document.getElementById("W72Selftypeofincome8").checked == true
		)
		{
		radioError("W72Selftypeofincome1_lbl", false);
		radioError("W72Selftypeofincome2_lbl", false);
		radioError("W72Selftypeofincome3_lbl", false);
		radioError("W72Selftypeofincome4_lbl", false);
		radioError("W72Selftypeofincome5_lbl", false);
		radioError("W72Selftypeofincome6_lbl", false);
		radioError("W72Selftypeofincome7_lbl", false);
		radioError("W72Selftypeofincome8_lbl", false);
		}
		else
		{


		//1
		var radio = jQuery('input:radio[name="W72Selftypeofincome1"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome1_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome1').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome1_lbl", false);
		}
		//2
		var radio = jQuery('input:radio[name="W72Selftypeofincome2"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome2_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome2').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome2_lbl", false);
		}
		//3
		var radio = jQuery('input:radio[name="W72Selftypeofincome3"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome3_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome3').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome3_lbl", false);
		}
		//4
		var radio = jQuery('input:radio[name="W72Selftypeofincome4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome4_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome4').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome4_lbl", false);
		}
		//5
		var radio = jQuery('input:radio[name="W72Selftypeofincome5"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome5_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome5').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome5_lbl", false);
		}
		//6
		var radio = jQuery('input:radio[name="W72Selftypeofincome6"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome6_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome6').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome6_lbl", false);
		}
		//7
		var radio = jQuery('input:radio[name="W72Selftypeofincome7"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome7_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome7').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome7_lbl", false);
		}
		//8
		var radio = jQuery('input:radio[name="W72Selftypeofincome8"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W72Selftypeofincome8_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W72Selftypeofincome8').offset().top});							
		}
		else
		{   
		radioError("W72Selftypeofincome8_lbl", false);
		}
		}//end else

		if(jQuery('#W72Selftypeofincome1').is(':checked'))   
		{    
		//interest
		if (document.getElementsByName("type_InterestSelf")[0].value == '') 
		{
		document.getElementById("type_InterestSelf_lbl").innerHTML="Please Enter ANNUAL Interest income ";
		signalError("type_InterestSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_InterestSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_InterestSelf_lbl").innerHTML="";
		signalError("type_InterestSelf", false, true, true);
		}
		}
		//Pension
		if(jQuery('#W72Selftypeofincome2').is(':checked'))   
		{ 
		if (document.getElementsByName("type_PensionSelf")[0].value == '') 
		{
		document.getElementById("type_PensionSelf_lbl").innerHTML="Please Enter ANNUAL Pension income ";
		signalError("type_PensionSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_PensionSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_PensionSelf_lbl").innerHTML="";
		signalError("type_PensionSelf", false, true, true);
		}
		}
		//Rental
		if(jQuery('#W72Selftypeofincome3').is(':checked'))   
		{ 
		if (document.getElementsByName("type_RentalSelf")[0].value == '') 
		{
		document.getElementById("type_RentalSelf_lbl").innerHTML="Please Enter ANNUAL Rental income ";
		signalError("type_RentalSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_RentalSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_RentalSelf_lbl").innerHTML="";
		signalError("type_RentalSelf", false, true, true);
		}
		}
		//Child Tax Credit
		if(jQuery('#W72Selftypeofincome5').is(':checked'))   
		{ 
		if (document.getElementsByName("type_ChildTaxCreditSelf")[0].value == '') 
		{
		document.getElementById("type_ChildTaxCreditSelf_lbl").innerHTML="Please Enter ANNUAL ChildTaxCredit income ";
		signalError("type_ChildTaxCreditSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_ChildTaxCreditSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_ChildTaxCreditSelf_lbl").innerHTML="";
		signalError("type_ChildTaxCreditSelf", false, true, true);
		}
		}
		//Living Allowance
		if(jQuery('#W72Selftypeofincome6').is(':checked'))   
		{ 
		if (document.getElementsByName("type_LivingAllowanceSelf")[0].value == '') 
		{
		document.getElementById("type_LivingAllowanceSelf_lbl").innerHTML="Please Enter ANNUAL Living Allowance income ";
		signalError("type_LivingAllowanceSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_LivingAllowanceSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_LivingAllowanceSelf_lbl").innerHTML="";
		signalError("type_LivingAllowanceSelf", false, true, true);
		}
		}

		//Car Allowance
		if(jQuery('#W72Selftypeofincome7').is(':checked'))   
		{ 
		if (document.getElementsByName("type_CarAllowanceSelf")[0].value == '') 
		{
		document.getElementById("type_CarAllowanceSelf_lbl").innerHTML="Please Enter ANNUAL Car Allowance income ";
		signalError("type_CarAllowanceSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_CarAllowanceSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_CarAllowanceSelf_lbl").innerHTML="";
		signalError("type_CarAllowanceSelf", false, true, true);
		}
		}
		//Investment
		if(jQuery('#W72Selftypeofincome8').is(':checked'))   
		{ 
		if (document.getElementsByName("type_InvestmentSelf")[0].value == '') 
		{
		document.getElementById("type_InvestmentSelf_lbl").innerHTML="Please Enter ANNUAL Investment income ";
		signalError("type_InvestmentSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_InvestmentSelf').offset().top});
		} 
		else
		{
		document.getElementById("type_InvestmentSelf_lbl").innerHTML="";
		signalError("type_InvestmentSelf", false, true, true);
		}	
		}
		//other       

		if(jQuery('#W72Selftypeofincome4').is(':checked'))   
		{ 
		if (document.getElementsByName("type_otherSelf")[0].value == '') 
		{
		document.getElementById("type_otherSelf_lbl").innerHTML="Please Enter ANNUAL other income ";
		signalError("type_otherSelf", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#type_otherSelf').offset().top});
		} 
		else   
		{
		document.getElementById("type_otherSelf_lbl").innerHTML="";
		signalError("type_otherSelf", false, true, true);
		}   

		}       



		}


		}




		if(document.getElementById("W7SelfOtherIncome1").checked == true)
		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeTwo"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeTwo_lbl", true);
		mCheck = true;   
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeTwo1').offset().top});							
		}
		else
		{   
		radioError("W7IncomeEarnedIncomeTwo_lbl", false);
		}
		}   
		//income1 retired very first question on 80%   
		if(document.getElementById("W7IncomeEarned4").checked == true)
		{
		var radio = jQuery('input:radio[name="W7HowLongCJobBoth"]:checked');
		if (radio.length == 0)         
		{        
		radioError("W7HowLongCJobBoth_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W7HowLongCJobBoth1').offset().top});						
		}
		else
		{   
		radioError("W7HowLongCJobBoth_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7BothChildSupport"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7BothChildSupport_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7BothChildSupport1').offset().top});					
		}
		else
		{   
		radioError("W7BothChildSupport_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7BothOtherIncome"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7BothOtherIncome_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7BothOtherIncome1').offset().top});					
		}
		else  
		{   
		radioError("W7BothOtherIncome_lbl", false);
		}					

		}

		if(document.getElementById("W7BothOtherIncome1").checked == true)
		{
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeTwo"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeTwo_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeTwo1').offset().top});		  			
		}
		else
		{   
		radioError("W7IncomeEarnedIncomeTwo_lbl", false);
		}
		}

		//	co-applicant 1 for  retired  
		if(document.getElementById("W2SecondApp1").checked == true)
		{

		if(document.getElementById("W7BothOtherIncome2").checked == true)
		{
		var radio = jQuery('input:radio[name="W7BothHowLongCJobSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7BothHowLongCJobSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7BothHowLongCJobSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7BothHowLongCJobSecApp_lbl", false);
		}

		var radio = jQuery('input:radio[name="W7BothChildSupportSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7BothChildSupportSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7BothChildSupportSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7BothChildSupportSecApp_lbl", false);
		}




		var radio = jQuery('input:radio[name="W7OtherIncomeSecAppTwo"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7OtherIncomeSecAppTwo_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7OtherIncomeSecAppTwo1').offset().top});						
		}
		else
		{   
		radioError("W7OtherIncomeSecAppTwo_lbl", false);
		}



		}}




		if(document.getElementById("W7OtherIncomeSecAppThree1").checked == true )
		{       
		var radio = jQuery('input:radio[name="W7IncomeEarnedIncomeThreeSecapp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7IncomeEarnedIncomeThreeSecapp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7IncomeEarnedIncomeThreeSecapp').offset().top});			   			
		}  
		else     
		{   
		radioError("W7IncomeEarnedIncomeThreeSecapp_lbl", false);
		}


		}


		//self-employed co-app
		//co-applicant self-employed
		if(document.getElementById("W2SecondApp1").checked == true)
		{
		if(document.getElementById("W7SelfOtherIncome2").checked == true)
		{
		var radio = jQuery('input:radio[name="W7SelfBusClassSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfBusClassSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfBusClassSecApp1').offset().top});					
		}
		else
		{   
		radioError("W7SelfBusClassSecApp_lbl", false);
		}
		//
		//////////////////////
		if (document.getElementsByName("W7TxtSelfAvgIncomeSecApp")[0].value == '') 
		{   
		document.getElementById("W7TxtSelfAvgIncomeSecApp_lbl").innerHTML="Please Enter annual income ";
		signalError("W7TxtSelfAvgIncomeSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtSelfAvgIncomeSecApp').offset().top});
		} else {
		document.getElementById("W7TxtSelfAvgIncomeSecApp_lbl").innerHTML="";
		signalError("W7TxtSelfAvgIncomeSecApp", false, true, true);
		}	
		if (document.getElementsByName("W7TxtSelfCompNameSecApp")[0].value == '') 
		{
		document.getElementById("W7TxtSelfCompNameSecApp_lbl").innerHTML="Please Enter company name";
		signalError("W7TxtSelfCompNameSecApp", true, true, true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7TxtSelfCompNameSecApp').offset().top});     
		} else {
		document.getElementById("W7TxtSelfCompNameSecApp_lbl").innerHTML="";
		signalError("W7TxtSelfCompNameSecApp", false, true, true);
		}	

		//////////////////	

		//
		var radio = jQuery('input:radio[name="W7SelfHowLongCJobSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfHowLongCJobSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfHowLongCJobSecApp1').offset().top});					
		}
		else
		{   
		radioError("W7SelfHowLongCJobSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7SelfIndustryTypeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfIndustryTypeSecApp_lbl", true);
		mCheck = true;  
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfIndustryTypeSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7SelfIndustryTypeSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7SelfContractEmpSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfContractEmpSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfContractEmpSecApp1').offset().top});					
		}
		else
		{   
		radioError("W7SelfContractEmpSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7SelfChildSupportSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfChildSupportSecApp_lbl", true);
		mCheck = true;
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfChildSupportSecApp1').offset().top});						
		}
		else
		{   
		radioError("W7SelfChildSupportSecApp_lbl", false);
		}
		//



		var radio = jQuery('input:radio[name="W7SelfOtherIncomeSecApp"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W7SelfOtherIncomeSecApp_lbl", true);
		mCheck = true; 
		jQuery('html,body').animate({scrollTop: jQuery('#W7SelfOtherIncomeSecApp1').offset().top});					
		}
		else
		{   
		radioError("W7SelfOtherIncomeSecApp_lbl", false);
		}

		}}		










		jQuery(".W7IncomeEarnedgout").is(':checked')
		{
		window.scrollTo(0,0);  

		}   	    	

		//////////////////////////////	

		}
		}   

		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}
		break;

		case 8:				
		//How much money do you normally have in your chequing and savings accounts after payday?
		// if (document.getElementsByName("W8TxtMoneyAccount")[0].value.trim() == 'How Much Money') {
		// signalError("W8TxtMoneyAccount", true, true, true);
		// mCheck = true;
		// } else {
		// signalError("W8TxtMoneyAccount", false, true, true);
		// }
		if(sval=='1')
		{


		}
		else
		{
		// Do you have any vehicles?
		if (document.getElementsByName("W8TxtMoneyAccount")[0].value == '') 
		{  
		document.getElementById("W8TxtMoneyAccount_lbl").innerHTML="Please Enter money";
		signalError("W8TxtMoneyAccount", true, true, true);
		mCheck = true;
		//location.href = "#W8TxtMoneyAccount";
		} 
		else
		{
		document.getElementById("W8TxtMoneyAccount_lbl").innerHTML="";
		signalError("W8TxtMoneyAccount", false, true, true);
		}	
		//
		if (document.getElementsByName("W8Txtcontents")[0].value == '') 
		{     
		document.getElementById("W8Txtcontents_lbl").innerHTML="Please Enter Value";
		signalError("W8Txtcontents", true, true, true);
		mCheck = true;
		//location.href = "#W8Txtcontents";
		} 
		else
		{
		document.getElementById("W8Txtcontents_lbl").innerHTML="";
		signalError("W8Txtcontents", false, true, true);
		}





		var radio = jQuery('input:radio[name="W8Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8Vehicles_lbl", true);
		mCheck = true; 
		//location.href = "#W8Vehicles1";	  				 
		}
		else
		{   
		radioError("W8Vehicles_lbl", false);
		}
		//	

		if (document.getElementById("W8Vehicles1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1Vehicles_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1Vehicles1";							 
		}
		else  
		{   
		radioError("W8More1Vehicles_lbl", false);
		}

		}	
		//
		if (document.getElementById("W8More1Vehicles1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2Vehicles_lbl", true);
		mCheck = true;  
		//location.href = "#W8More2Vehicles1";						 
		}
		else
		{   
		radioError("W8More2Vehicles_lbl", false);
		}

		}





		//

		if (document.getElementById("W8More2Vehicles1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3Vehicles_lbl", true);
		mCheck = true;  
		//location.href = "#W8More3Vehicles1";						 
		}
		else
		{   
		radioError("W8More3Vehicles_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More3Vehicles1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More4Vehicles"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More4Vehicles_lbl", true);
		mCheck = true;
		//location.href = "#W8More4Vehicles1";						 
		}
		else
		{   
		radioError("W8More4Vehicles_lbl", false);
		}

		}  
		//	




		// Do you have any RRSP's?

		var radio = jQuery('input:radio[name="W8RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8RRSPs_lbl", true);
		mCheck = true; 
		//location.href = "#W8RRSPs1";					 
		}
		else
		{   
		radioError("W8RRSPs_lbl", false);
		}

		//

		if (document.getElementById("W8RRSPs1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1RRSPs_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1RRSPs1";
		}
		else
		{   
		radioError("W8More1RRSPs_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More1RRSPs1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2RRSPs_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2RRSPs1";						 
		}
		else
		{   
		radioError("W8More2RRSPs_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More2RRSPs1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3RRSPs_lbl", true);
		mCheck = true;
		//location.href = "#W8More3RRSPs1";						 
		}
		else
		{   
		radioError("W8More3RRSPs_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More3RRSPs1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More4RRSPs"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More4RRSPs_lbl", true);
		mCheck = true;
		//location.href = "#W8More4RRSPs1";						 
		}
		else
		{   
		radioError("W8More4RRSPs_lbl", false);
		}

		}




		// Do you have any non- RRSP,s investments? (E.g. GICs, Term Deposits, Mutual Funds, Public or Private Stocks, Company Assets or other investments)

		var radio = jQuery('input:radio[name="W8NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8NonRSP_lbl", true);
		mCheck = true; 
		//location.href = "#W8NonRSP1";					 
		}
		else
		{   
		radioError("W8NonRSP_lbl", false);
		}

		//

		if (document.getElementById("W8NonRSP1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1NonRSP_lbl", true);
		mCheck = true;
		//location.href = "#W8More1NonRSP1";						 
		}
		else
		{   
		radioError("W8More1NonRSP_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More1NonRSP1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2NonRSP_lbl", true);
		mCheck = true;  
		//location.href = "#W8More2NonRSP1";						 
		}
		else
		{   
		radioError("W8More2NonRSP_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More2NonRSP1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3NonRSP_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3NonRSP1";						 
		}
		else
		{   
		radioError("W8More3NonRSP_lbl", false);
		}

		}

		//

		if (document.getElementById("W8More3NonRSP1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More4NonRSP"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More4NonRSP_lbl", true);
		mCheck = true; 
		//location.href = "#W8More4NonRSP1";						 
		}
		else
		{   
		radioError("W8More4NonRSP_lbl", false);
		}

		}
		
		// Do you own any Real Estate?

		var radio = jQuery('input:radio[name="W8REstate"]:checked');
		if (radio.length == 0)    
		{          
		radioError("W8REstate_lbl", true);
		mCheck = true; 
		//location.href = "#W8REstate1";					 
		}
		else
		{   
		radioError("W8REstate_lbl", false);
		}

		/////////////////////////////////////////last/////////////////


		//

		if (document.getElementById("W8REstate1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1REstate"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1REstate_lbl", true);
		mCheck = true;
		//location.href = "#W8More1REstate1";
		}
		else
		{   
		radioError("W8More1REstate_lbl", false);
		}


		var radio = jQuery('input:radio[name="W8More1RentalProperty"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1RentalProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1RentalProperty1";						 
		}
		else
		{   
		radioError("W8More1RentalProperty_lbl", false);
		}
		//SELLING PROPERTY 1

		var radio = jQuery('input:radio[name="W8More1RentalPropertySelling"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1RentalPropertySelling_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1RentalPropertySelling1";						 
		}
		else
		{   
		radioError("W8More1RentalPropertySelling_lbl", false);
		}   
		//

		var radio = jQuery('input:radio[name="W8More1MoreProperty"]:checked');
		if (radio.length == 0)    
		{           
		radioError("W8More1MoreProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1MoreProperty1";
		}
		else
		{   
		radioError("W8More1MoreProperty_lbl", false);
		}

		}
		//
		if (document.getElementById("W8More1REstate1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1Loans"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1Loans_lbl", true);
		mCheck = true;  
		//location.href = "#W8More1Loans1";						 
		}
		else
		{   
		radioError("W8More1Loans_lbl", false);
		}

		}
		//
		if (document.getElementById("W8More1Loans1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1Loans_2"]:checked');
		if (radio.length == 0)       
		{        
		radioError("W8More1Loans_2_lbl", true);
		mCheck = true; 
		//location.href = "#W8More1Loans1_2";							 
		}
		else
		{   
		radioError("W8More1Loans_2_lbl", false);
		}

		}
		//



		if (document.getElementById("W8More1MoreProperty1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2REstate"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2REstate_lbl", true);
		mCheck = true;  
		//location.href = "#W8More2REstate1";							 
		}
		else
		{   
		radioError("W8More2REstate_lbl", false);
		}


		if(document.getElementById("W8More2REstate1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More2Loans"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2Loans_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2Loans1";								 
		}
		else
		{   
		radioError("W8More2Loans_lbl", false);
		}
		}
		if(document.getElementById("W8More2Loans1").checked == true)

		{

		var radio = jQuery('input:radio[name="W8More1Loans_4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1Loans_4_lbl", true);
		mCheck = true;   
		//location.href = "#W8More1Loans1_4";							 
		}
		else
		{   
		radioError("W8More1Loans_4_lbl", false);
		}
		}



		var radio = jQuery('input:radio[name="W8More2RentalProperty"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2RentalProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2RentalProperty1";						 
		}
		else
		{   
		radioError("W8More2RentalProperty_lbl", false);
		}
		//SELLING PROPERTY2

		var radio = jQuery('input:radio[name="W8More2RentalPropertySelling"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2RentalPropertySelling_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2RentalPropertySelling1";						 
		}
		else
		{   
		radioError("W8More2RentalPropertySelling_lbl", false);
		}

		var radio = jQuery('input:radio[name="W8More2MoreProperty"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More2MoreProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More2MoreProperty1";						 
		}
		else
		{   
		radioError("W8More2MoreProperty_lbl", false);
		}


		}	
		//
		if(document.getElementById("W8More2MoreProperty1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3REstate"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3REstate_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3REstate1";							 
		}
		else
		{   
		radioError("W8More3REstate_lbl", false);
		}


		var radio = jQuery('input:radio[name="W8More3RentalProperty"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3RentalProperty_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3RentalProperty1";								 
		}
		else
		{   
		radioError("W8More3RentalProperty_lbl", false);
		}

		//SELLING PROPERTY3

		var radio = jQuery('input:radio[name="W8More3RentalPropertySelling"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3RentalPropertySelling_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3RentalPropertySelling1";						 
		}
		else
		{   
		radioError("W8More3RentalPropertySelling_lbl", false);
		} 


		if(document.getElementById("W8More3REstate1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More3Loans"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More3Loans_lbl", true);
		mCheck = true; 
		//location.href = "#W8More3Loans1";							 
		}
		else
		{   
		radioError("W8More3Loans_lbl", false);
		}
		}


		if(document.getElementById("W8More3Loans1").checked == true)
		{
		var radio = jQuery('input:radio[name="W8More1Loans_6"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W8More1Loans_6_lbl", true);
		mCheck = true;
		//location.href = "#W8More1Loans1_6";								 
		}
		else
		{      
		radioError("W8More1Loans_6_lbl", false);
		}
		}
		}   		


		}


		//////////////////////////////////
		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}
		break;

		case 9:				
		


		if(sval=='1')   
		{




		}

		else
		{
		var radio = jQuery('input:radio[name="W9LiabilitiesOwed"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9LiabilitiesOwed_lbl", true);
		mCheck = true; 
		//location.href="#W9LiabilitiesOwed1";						 
		}
		else
		{   
		radioError("W9LiabilitiesOwed_lbl", false);
		}

		if(document.getElementById('W9LiabilitiesOwed1').checked==true)
		{

		var radio = jQuery('input:radio[name="W9MoreLoans1"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9MoreLoans1_lbl", true);
		mCheck = true; 
		//location.href="#W9MoreLoans1_1";							 
		}
		else
		{   
		radioError("W9MoreLoans1_lbl", false);
		}

		//
		var radio = jQuery('input:radio[name="W9MoreLoans2"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9MoreLoans2_lbl", true);
		mCheck = true; 
		//location.href="#W9MoreLoans2_1";						 
		}
		else
		{   
		radioError("W9MoreLoans2_lbl", false);
		}

		//
		var radio = jQuery('input:radio[name="W9MoreLoans3"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9MoreLoans3_lbl", true);
		mCheck = true; 
		//location.href="#W9MoreLoans3_1";						 
		}
		else
		{   
		radioError("W9MoreLoans3_lbl", false);
		}


		//
		var radio = jQuery('input:radio[name="W9MoreLoans4"]:checked');
		if (radio.length == 0)    
		{        
		radioError("W9MoreLoans4_lbl", true);
		mCheck = true; 
		//location.href="#W9MoreLoans4_1";	  					 
		}    
		else
		{   
		radioError("W9MoreLoans4_lbl", false);
		}


		}


		}




		if (mCheck) {
		document.getElementById('errMsg').style.display = '';
		return false;
		} else {
		document.getElementById('errMsg').style.display = 'none';
		return true;
		}
		break;
	}
	return true;
}  

function isValidEmailAddress(emailAddress) {
var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
return pattern.test(emailAddress);
};

