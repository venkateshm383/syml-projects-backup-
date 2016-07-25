//Water Mark 
jQuery(document).ready(function() {
		var watermark = 'First Name';
		 jQuery('#W1TxtFirstNsme').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark).addClass('watermark');
		 
		 //////////////////////////////////////////
		 var watermarklast = 'e.g. Pension,Child Tax Credit,Interest Income,Dividend Income ,Vechicle Allowance ,Living Allowance';
		 jQuery('#W7TxtRetadditionalinc').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarklast).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarklast)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarklast).addClass('watermark');
		 
		 //////////////////////////////////////////
		 		 //////////////////type_Interest////////////////////////
		 var watermarktype_Interest ='Interest';
		 jQuery('.type_Interest').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarktype_Interest).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarktype_Interest)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarktype_Interest).addClass('watermark');
		 
		  /////////////type_Pension//////////////
		 		var watermarktype_Pension ='Pension';
			jQuery('.type_Pension').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarktype_Pension).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarktype_Pension)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarktype_Pension).addClass('watermark');
		 //////////////////////////////////////////
		 		  /////////////type_Rental//////////////
		 var watermarktype_Rental ='Rental';
			jQuery('.type_Rental').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarktype_Rental).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarktype_Rental)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarktype_Rental).addClass('watermark');
		 //////////////////////////////////////////
		 		 		  /////////////type_ChildTaxCredit//////////////
		 var watermarktype_ChildTaxCredit ='Child Tax Credit';
			jQuery('.type_ChildTaxCredit').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarktype_ChildTaxCredit).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarktype_ChildTaxCredit)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarktype_ChildTaxCredit).addClass('watermark');
		 //////////////////////////////////////////
		 		 		 		  /////////////type_LivingAllowance//////////////
		 var watermarktype_LivingAllowance ='Living Allowance';
			jQuery('.type_LivingAllowance').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarktype_LivingAllowance).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarktype_LivingAllowance)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarktype_LivingAllowance).addClass('watermark');
		 //////////////////////////////////////////
		 		 		 		 		  /////////////type_CarAllowance//////////////
		 var watermarktype_CarAllowance ='Car Allowance';
			jQuery('.type_CarAllowance').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarktype_CarAllowance).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarktype_CarAllowance)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarktype_CarAllowance).addClass('watermark');
		 //////////////////////////////////////////
		 	 		 		 		 		  /////////////type_Investment//////////////
		 var watermarktype_Investment ='Investment';
			jQuery('.type_Investment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarktype_Investment).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarktype_Investment)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarktype_Investment).addClass('watermark');
		 //////////////////////////////////////////
		 		 	 		 		 		 		  /////////////type_other//////////////
		 var watermarktype_other ='other';
			jQuery('.type_other').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarktype_other).addClass('watermark');
		 }).focus(function(){   
		  if (jQuery(this).val() == watermarktype_other)
			jQuery(this).val('').removeClass('watermark');     
		 }).val(watermarktype_other).addClass('watermark');
		 //////////////////////////////////////////     
		 ///////////////////////////////////////
		 var watermark1 = 'Middle Name';
		 jQuery('#W1TxtMiddleName').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark1).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark1)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark1).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermarkgift = 'Gift';
		 jQuery('.gift_amount').blur(function(){      
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarkgift).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarkgift)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarkgift).addClass('watermark');
		 //
 
		 var watermarkequity = 'Existing Equity';
		 jQuery('.equity_amount').blur(function(){      
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarkequity).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarkequity)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarkequity).addClass('watermark');
		 //

		 var watermarksweatequity = 'Sweat Equity';
		 jQuery('.sweatequity_amount').blur(function(){      
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarksweatequity).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarksweatequity)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarksweatequity).addClass('watermark');
		 //
		  var watermarksecfinance = 'Secondary Financing ';
		 jQuery('.finance_amount').blur(function(){        
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarksecfinance).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarksecfinance)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarksecfinance).addClass('watermark');
		 //
   
		  //
		  var watermarkcash =' Personal Cash';
		 jQuery('.cash_amount').blur(function(){        
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarkcash).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarkcash)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarkcash).addClass('watermark');
		 //
		  var watermarkbank = 'Bank Account Chequing/Savings';
		 jQuery('.bank_amount').blur(function(){        
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarkbank).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarkbank)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarkbank).addClass('watermark');
		 //
		 var watermarkin = 'RRSPs or Investments';
		 jQuery('.RRSPs_amount').blur(function(){      
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarkin).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarkin)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarkin).addClass('watermark');	 
		 
		 //
		 
		 var watermarkborrowed = 'Borrowed(e.g LOC)';
		 jQuery('.borrowed_amount').blur(function(){      
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarkborrowed).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarkborrowed)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarkborrowed).addClass('watermark');	  
		 
		 //  
		  var watermarksale = 'Sale of Asset';
		 jQuery('.sale_amount').blur(function(){      
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarksale).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarksale)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarksale).addClass('watermark');	
		 //
		  var watermarkother = 'Other';    
		 jQuery('.other_amount').blur(function(){      
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermarkother).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermarkother)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermarkother).addClass('watermark');	
		 //////////////////////
		 
		 var watermark2 = 'Last Name';
		 jQuery('#W1TxtLastName').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark2).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark2)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark2).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark3 = 'Personal Email Address';
		 jQuery('#W1TxtEmail').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark3).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark3)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark3).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark4 = 'Home Phone #';
		 jQuery('#W1TxtHome').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark4).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark4)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark4).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark5 = 'Business Phone #';
		 jQuery('#W1TxtWork').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark5).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark5)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark5).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark6 = 'Cell Phone #';
		 jQuery('#W1TxtCell').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark6).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark6)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark6).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		  var watermark7 = 'Street';
		 jQuery('#W1TxtStreet').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark7N3Y = 'Street';
		 jQuery('#W2TxtStreetN3Y').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7N3Y).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7N3Y)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7N3Y).addClass('watermark');
		 
		 //////////////////////////////////////////
		 		 
		  var watermark7N3Y2 = 'Street';
		 jQuery('#W2TxtStreetN3Y1').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7N3Y2).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7N3Y2)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7N3Y2).addClass('watermark');
		 //////////////////////////////////////////
		  var watermark7N3Y2a = 'Street';
		 jQuery('#W2TxtStreetN3Y2').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7N3Y2a).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7N3Y2a)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7N3Y2a).addClass('watermark');
		 	 //////////////////////////////////////////
		  var watermark7N3Y2a3 = 'Street';
		 jQuery('#W2TxtStreetN3Y3').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7N3Y2a3).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7N3Y2a3)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7N3Y2a3).addClass('watermark');
		 ////////////////////////////////////////
		 ////////////////////////////////////////
		  var watermark8 = 'City';
		 jQuery('#W1TxtCity').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8).addClass('watermark');
		 		 //////////////////////////////////////////
		  var watermark8N3Y = 'City';
		 jQuery('#W2TxtCityN3Y').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8N3Y).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8N3Y)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8N3Y).addClass('watermark');
		 	 		 //////////////////////////////////////////
		  var watermark8N3Y3 = 'City';
		 jQuery('#W2TxtCityN3Y3').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8N3Y3).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8N3Y3)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8N3Y3).addClass('watermark');
		 		 		 //////////////////////////////////////////
		 		 		 //////////////////////////////////////////
		  var watermark8N3Y1 = 'City';
		 jQuery('#W2TxtCityN3Y1').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8N3Y1).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8N3Y1)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8N3Y1).addClass('watermark');
		 		 		 		 //////////////////////////////////////////
		  var watermark8N3Y2 = 'City';
		 jQuery('#W2TxtCityN3Y2').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8N3Y2).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8N3Y2)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8N3Y2).addClass('watermark');
		 //////////////////////secapp//////////
		   //////////////////////////////////////////
		 
		  var watermark7N3YSecapp = 'Street';
		 jQuery('#W2TxtStreetN3YSecapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7N3YSecapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7N3YSecapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7N3YSecapp).addClass('watermark');
		 
		 //////////////////////////////////////////
		 		 
		  var watermark7N3Y2Secapp = 'Street';
		 jQuery('#W2TxtStreetN3Y1Secapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7N3Y2Secapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7N3Y2Secapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7N3Y2Secapp).addClass('watermark');
		 //////////////////////////////////////////
		  var watermark7N3Y2aSecapp = 'Street';
		 jQuery('#W2TxtStreetN3Y2Secapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7N3Y2aSecapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7N3Y2aSecapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7N3Y2aSecapp).addClass('watermark');
		 	 //////////////////////////////////////////
		  var watermark7N3Y2a3Secapp = 'Street';
		 jQuery('#W2TxtStreetN3Y3Secapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark7N3Y2a3Secapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark7N3Y2a3Secapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark7N3Y2a3Secapp).addClass('watermark');
		 ////////////////////////////////////////

		  var watermark8N3YSecapp = 'City';
		 jQuery('#W2TxtCityN3YSecapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8N3YSecapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8N3YSecapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8N3YSecapp).addClass('watermark');
		 	 		 //////////////////////////////////////////
		  var watermark8N3Y3Secapp = 'City';
		 jQuery('#W2TxtCityN3Y3Secapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8N3Y3Secapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8N3Y3Secapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8N3Y3Secapp).addClass('watermark');
		 		 		 //////////////////////////////////////////
		 		 		 //////////////////////////////////////////
		  var watermark8N3Y1Secapp = 'City';
		 jQuery('#W2TxtCityN3Y1Secapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8N3Y1Secapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8N3Y1Secapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8N3Y1Secapp).addClass('watermark');
		 		 		 		 //////////////////////////////////////////
		  var watermark8N3Y2Secapp = 'City';
		 jQuery('#W2TxtCityN3Y2Secapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark8N3Y2Secapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark8N3Y2Secapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark8N3Y2Secapp).addClass('watermark');
		 //////////////////////////////////////////
		  var watermark9N3Y = 'Postal Code';
		 jQuery('#W1TxtCodeN3Y').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark9N3Y).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark9N3Y)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark9N3Y).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark9 = 'Postal Code';
		 jQuery('#W1TxtCode').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark9).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark9)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark9).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark10= 'MM/dd/yyyy';
		 jQuery('#W1TxtDOB').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark10).addClass('watermark');
		 ///////////////////////////////////////////
	  	var watermark10a= 'MM/dd/yyyy';
		 jQuery('#W2textcanada').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10a).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10a)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark10a).addClass('watermark');
		 //////////////////////////////////////////
		   	var watermark10N3Y= 'MM/dd/yyyy';
		 jQuery('#W2TxtMoveN3Y').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10N3Y).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10N3Y)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtMoveN3Y').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMoveN3Y').val(watermark10N3Y);
			jQuery('#W2TxtMoveN3Y').addClass('watermark');
		 }
		 //////////////////////////////////////////
		var watermark10N3Y1= 'MM/dd/yyyy';
		 jQuery('#W2TxtMoveN3Y2').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10N3Y1).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10N3Y1)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtMoveN3Y2').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMoveN3Y2').val(watermark10N3Y1);
			jQuery('#W2TxtMoveN3Y2').addClass('watermark');
		 }
		 ///////////////////////////////////
		 	var watermark10N3Y13= 'MM/dd/yyyy';
		 jQuery('#W2TxtMoveN3Y3').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10N3Y13).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10N3Y13)
			jQuery(this).val('').removeClass('watermark');
		 });		 
		 var firstval=jQuery('#W2TxtMoveN3Y3').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMoveN3Y3').val(watermark10N3Y13);
			jQuery('#W2TxtMoveN3Y3').addClass('watermark');
		 }
		 	 ///////////////////////////////////////////
		var watermark10atsec= 'MM/dd/yyyy';
		 jQuery('#W2TxtMoveSecapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10atsec).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10atsec)
			jQuery(this).val('').removeClass('watermark');
		 });		 
		 var firstval=jQuery('#W2TxtMoveSecapp').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMoveSecapp').val(watermark10atsec);
			jQuery('#W2TxtMoveSecapp').addClass('watermark');
		 }
		 	 ///////////////////////////////////////////
			 	var watermark10atsecapp= 'MM/dd/yyyy';
		 jQuery('#W2TxtMoveN3YSecapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10atsecapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10atsecapp)
			jQuery(this).val('').removeClass('watermark');																											
		 });
		 var firstval=jQuery('#W2TxtMoveN3YSecapp').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMoveN3YSecapp').val(watermark10atsecapp);
			jQuery('#W2TxtMoveN3YSecapp').addClass('watermark');
		 }
		 		 	 ///////////////////////////////////////////
					 			 	var watermark10atsecapp223= 'MM/dd/yyyy';
		 jQuery('#W2TxtMoveN3Y2Secapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10atsecapp223).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10atsecapp223) 
			jQuery(this).val('').removeClass('watermark');     
		 });
		 var firstval=jQuery('#W2TxtMoveN3Y2Secapp').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMoveN3Y2Secapp').val(watermark10atsecapp223);
			jQuery('#W2TxtMoveN3Y2Secapp').addClass('watermark');
		 }
			 ////////////////////////////////////
			 	var watermark10atsecapp23= 'MM/dd/yyyy';
		 jQuery('#W2TxtMoveN3Y3Secapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10atsecapp23).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10atsecapp23)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtMoveN3Y3Secapp').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMoveN3Y3Secapp').val(watermark10atsecapp23);
			jQuery('#W2TxtMoveN3Y3Secapp').addClass('watermark');
		 }
			 ////////////////////////////////////
	  	var watermark10at= 'MM/dd/yyyy';
		 jQuery('#W2TxtMove').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark10at).addClass('watermark'); 
		 }).focus(function(){
		  if (jQuery(this).val() == watermark10at)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtMove').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMove').val(watermark10at);
			jQuery('#W2TxtMove').addClass('watermark');
		 }
		 //////////////////////////////////////////
		   var watermark11 = 'SIN #';
		 jQuery('#W1TxtSIN').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark11).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark11)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark11).addClass('watermark');
		 		 
		  //////////////////////////////////////////
		   var watermark12 = 'Name';
		 jQuery('#W1TxtName').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark12).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark12)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark12).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark13 = 'Username';
		 jQuery('#W1TxtLoginEmail').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark13).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark13)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark13).addClass('watermark');
		 
		  //////////////////////////////////////////
		 
		 
		 //Wizard 2
		 
		 var watermark15 = 'First Name';
		 jQuery('#W2TxtFirstNsme').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark15).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark15)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtFirstNsme').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtFirstNsme').val(watermark15);
			jQuery('#W2TxtFirstNsme').addClass('watermark');
		 }
		 
		 //////////////////////////////////////////
		 var watermark16 = 'Middle Name';
		 jQuery('#W2TxtMiddleName').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark16).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark16)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtMiddleName').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtMiddleName').val(watermark16);
			jQuery('#W2TxtMiddleName').addClass('watermark');
		 }
		 
		 //////////////////////////////////////////
		 var watermark17 = 'Last Name';
		 jQuery('#W2TxtLastName').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark17).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark17)
			jQuery(this).val('').removeClass('watermark');
		 });
		  var firstval=jQuery('#W2TxtLastName').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtLastName').val(watermark17);
			jQuery('#W2TxtLastName').addClass('watermark');
		 }
		 
		 //////////////////////////////////////////
		 var watermark18 = 'Personal Email Address';
		 jQuery('#W2TxtEmail').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark18).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark18)
			jQuery(this).val('').removeClass('watermark');
		 });
		  var firstval=jQuery('#W2TxtEmail').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtEmail').val(watermark18);
			jQuery('#W2TxtEmail').addClass('watermark');
		 }
		 
		 //////////////////////////////////////////
		 var watermark19 = '(111)-111-1111';   
		 jQuery('#W2TxtHome').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark19).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark19)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtHome').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtHome').val(watermark19);
			jQuery('#W2TxtHome').addClass('watermark');
		 }
		 
		 //////////////////////////////////////////
		 var watermark20 = 'Business Phone #';
		 jQuery('#W2TxtWork').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark20).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark20)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtWork').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtWork').val(watermark20);
			jQuery('#W2TxtWork').addClass('watermark');
		 }
		 
		 //////////////////////////////////////////
		 var watermark21 = '(111)-111-1111';
		 jQuery('#W2TxtCell').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark21).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark21)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtCell').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtCell').val(watermark21);
			jQuery('#W2TxtCell').addClass('watermark');
		 }
		 
		 //////////////////////////////////////////
		 var watermark22 = 'Street';
		 jQuery('#W2TxtStreet').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark22).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark22)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtStreet').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtStreet').val(watermark22);
			jQuery('#W2TxtStreet').addClass('watermark');
		 }
		 ///////  

		 	 //////////////////////////////////////////
		 //////////////////////////////////////////
		 var watermark23 = 'City';
		 jQuery('#W2TxtCity').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark23).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark23)
			jQuery(this).val('').removeClass('watermark');
		 });
		  var firstval=jQuery('#W2TxtCity').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtCity').val(watermark23);
			jQuery('#W2TxtCity').addClass('watermark');
		 }
		 	 //////////////////////////////////////////
		 var watermark23b = 'City';
		 jQuery('#W4TxtNewCity55b').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark23b).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark23b)
			jQuery(this).val('').removeClass('watermark');
		 }); 
		  var firstval=jQuery('#W4TxtNewCity55b').val();
		 if(firstval=="")
		 {
			jQuery('#W4TxtNewCity55b').val(watermark23b);
			jQuery('#W4TxtNewCity55b').addClass('watermark');
		 }
		 //
		 	 var watermark23a = 'City';
		 jQuery('#W4TxtNewCity55a').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark23a).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark23a)
			jQuery(this).val('').removeClass('watermark');
		 }); 
		  var firstval=jQuery('#W4TxtNewCity55a').val();
		 if(firstval=="")
		 {
			jQuery('#W4TxtNewCity55a').val(watermark23a);
			jQuery('#W4TxtNewCity55a').addClass('watermark');
		 }
		 //
		  	 var watermark23c = 'City';
		 jQuery('#W4TxtNewCity55c').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark23c).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark23c)
			jQuery(this).val('').removeClass('watermark');
		 }); 
		  var firstval=jQuery('#W4TxtNewCity55c').val();
		 if(firstval=="")
		 {
			jQuery('#W4TxtNewCity55c').val(watermark23c);
			jQuery('#W4TxtNewCity55c').addClass('watermark');
		 }
		 //
		 	  	 var watermark23d = 'City';
		 jQuery('#W4TxtNewCity55').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark23d).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark23d)
			jQuery(this).val('').removeClass('watermark');
		 }); 
		  var firstval=jQuery('#W4TxtNewCity55').val();
		 if(firstval=="")
		 {
			jQuery('#W4TxtNewCity55').val(watermark23d);
			jQuery('#W4TxtNewCity55').addClass('watermark');
		 }
		 //

		 	  	 var watermark23pre = 'City';
		 jQuery('#W4TxtNewCity55pre').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark23pre).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark23pre)
			jQuery(this).val('').removeClass('watermark');
		 }); 
		  var firstval=jQuery('#W4TxtNewCity55pre').val();
		 if(firstval=="")
		 {
			jQuery('#W4TxtNewCity55pre').val(watermark23pre);
			jQuery('#W4TxtNewCity55pre').addClass('watermark');
		 }
		 //////////////////////////////////////////

		 
		 //////////////////////////////////////////
		 var watermark25 = 'MM/dd/yyyy';
		 jQuery('#W2TxtDOB').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark25).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark25)
			jQuery(this).val('').removeClass('watermark');
		 });
		  var firstval=jQuery('#W2TxtDOB').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtDOB').val(watermark25);
			jQuery('#W2TxtDOB').addClass('watermark');
		 }
		 //////////////////////////////////////////
		  var watermark26 = 'SIN #';
		 jQuery('#W2TxtSIN').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark26).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark26)
			jQuery(this).val('').removeClass('watermark');
		 });
		   var firstval=jQuery('#W2TxtSIN').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtSIN').val(watermark26);
			jQuery('#W2TxtSIN').addClass('watermark');
		 }
		 //////////////////////////////////////////
		 
		 
		 
		 
		 var watermark116 = 'First Name';
		 jQuery('#W2TxtFirstNsmeSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark116).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark116)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark116).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark117 = 'Middle Name';
		 jQuery('#W2TxtMiddleNameSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark117).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark117)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark117).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark118 = 'Last Name';
		 jQuery('#W2TxtLastNameSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark118).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark118)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark118).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark119 = 'Personal Email Address';
		 jQuery('#W2TxtEmailSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark119).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark119)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark119).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark120 = 'Home Phone #';
		 jQuery('#W2TxtHomeSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark120).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark120)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark120).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark121 = 'Business Phone #';
		 jQuery('#W2TxtWorkSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark121).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark121)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark121).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		 var watermark122 = 'Cell Phone #';
		 jQuery('#W2TxtCellSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark122).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark122)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark122).addClass('watermark');
		 
		 //////////////////////////////////////////
		 
		  var watermark123 = 'Street';
		 jQuery('#W2TxtStreetSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark123).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark123)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark123).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark124 = 'City';
		 jQuery('#W2TxtCitySec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark124).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark124)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark124).addClass('watermark');
		 		 //////////////////////////////////////////

		  var watermark126= 'MM/dd/yyyy';
		 jQuery('#W2TxtDOBSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark126).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark126)
			jQuery(this).val('').removeClass('watermark');
		 });
		 var firstval=jQuery('#W2TxtDOBSec').val();
		 if(firstval=="")
		 {
			jQuery('#W2TxtDOBSec').val(watermark126);
			jQuery('#W2TxtDOBSec').addClass('watermark');
		 }
		 
		 //////////////////////////////////////////
		   var watermark127 = 'SIN #';
		 jQuery('#W2TxtSINSec').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark127).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark127)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark127).addClass('watermark');
		 
		 
		 //////////////////////////////////////////
		  var watermark27 = 'Broker Name';   
		 jQuery('#W2TxtBroker1').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark27).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark27)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark27).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark28 = 'Paid Before Taxes';
		 jQuery('.clsW7TxtPaidPeriod').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark28).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark28)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark28).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark29 = 'Average annual bonus / commission';
		 jQuery('.clsW7TxtAnnualBonus').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark29).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark29)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark29).addClass('watermark');
		 
		 
		  //////////////////////////////////////////
		  var watermark30 = '$ Per Hour';
		 jQuery('.clsW7TxtHourlyWage').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark30).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark30)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark30).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark31 = 'Hours Per Week';
		 jQuery('.clsW7TxtWeeklyWorkingHour').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark31).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark31)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark31).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark32 = 'Overtime income';
		 jQuery('.clsW7AverageOverTime').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark32).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark32)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark32).addClass('watermark');
		 
		 
		 //////////////////////////////////////////
		  var watermark33 = 'Average annual income';
		 jQuery('.clsW7TxtAvgIncome').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark33).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark33)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark33).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark34 = 'Please Describe';
		 jQuery('.clsW7TxtIndustryTypeDesc').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark34).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark34)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark34).addClass('watermark');
		 
	
	 //////////////////////////////////////////
		  var watermark35 = 'Job Title';
		 jQuery('.clsW7TxtJobTitle').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark35).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark35)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark35).addClass('watermark');
		 
	
	 //////////////////////////////////////////
		  var watermark36 = 'Company Name';
		 jQuery('.clsW7TxtCompName').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark36).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark36)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark36).addClass('watermark');
		 
	
	
	 //////////////////////////////////////////
		  var watermark37 = 'Previous employer';
		 jQuery('.clsW7TxtPreEmp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark37).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark37)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark37).addClass('watermark');
		
		//////////////////////////////////////////
		  var watermark38 = 'Job title';
		 jQuery('.clsW7TxtPreJobTitle').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark38).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark38)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark38).addClass('watermark');
		 
		 
		 //////////////////////////////////////////
		  var watermark39 = 'Annual Income $';
		 jQuery('.clsW7TxtPreAnnuallyPaid').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark39).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark39)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark39).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark40 = 'Average annual income';
		 jQuery('.clsW7TxtSelfAvgIncome').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark40).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark40)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark40).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark41 = 'Company Name';
		 jQuery('.clsW7TxtSelfCompName').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark41).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark41)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark41).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark42 = 'Please Describe';
		 jQuery('.clsW7TxtSelfIndustryTypeDesc').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark42).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark42)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark42).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark43 = 'Previous employer';
		 jQuery('.clsW7SelfTxtPreEmp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark43).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark43)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark43).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark44 = 'Job title';
		 jQuery('.clsW7SelfTxtPreJobTitle').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark44).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark44)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark44).addClass('watermark');
		 
		 
		  //////////////////////////////////////////
		  var watermark45 = 'Annually Paid';
		 jQuery('.clsW7SelfTxtPreAnnuallyPaid').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark45).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark45)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark45).addClass('watermark');
		 
		 //Wizard 4
		  //////////////////////////////////////////
		  var watermark46 = 'Street';
		 jQuery('#W4TxtStreet').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark46).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark46)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark46).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark47 = 'Street 2';
		 jQuery('#W4TxtStreet2').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark47).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark47)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark47).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark48 = 'City';
		 jQuery('#W4TxtCity').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark48).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark48)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark48).addClass('watermark');
		 
		  //////////////////////////////////////////
	
		 
		  //////////////////////////////////////////
		  var watermark50 = 'Postal Code';
		 jQuery('#W4TxtCode').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark50).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark50)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark50).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark51 = 'Street';
		 jQuery('#W4TxtNewStreet').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark51).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark51)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark51).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark52 = 'Street 2';
		 jQuery('#W4TxtNewStreet2').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark52).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark52)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark52).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark53 = 'City';
		 jQuery('#W4TxtNewCity').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark53).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark53)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark53).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark54 = 'Province';
		 jQuery('#W4TxtNewProvince').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark54).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark54)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark54).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark55 = 'Postal Code';
		 jQuery('#W4TxtNewCode').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark55).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark55)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark55).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark56 = 'MLS #';
		 jQuery('#W4TxtMLS').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark56).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark56)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark56).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark57 = 'Plan';
		 jQuery('#W4TxtPlan').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark57).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark57)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark57).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark58 = 'Lot';
		 jQuery('#W4TxtLot').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark58).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark58)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark58).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark59 = 'Block';
		 jQuery('#W4TxtBlock').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark59).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark59)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark59).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark60 = 'Township/PID';
		 jQuery('#W4TxtPID').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark60).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark60)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark60).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark61 = 'Please Describe';
		 jQuery('#W4TxtPropStyle').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark61).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark61)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark61).addClass('watermark');
		 
		 //////////////////////////////////////////
		  var watermark62 = 'Please Describe';
		 jQuery('#W4TxtTypeBuilding').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark62).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark62)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark62).addClass('watermark');
		
		//Wizard 5
		 //////////////////////////////////////////
		  var watermark63 = 'Please Describe';
		 jQuery('#W5TxtHatedProperty').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark63).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark63)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark63).addClass('watermark');
		
		 //////////////////////////////////////////
		  var watermark64 = 'Please Describe';
		 jQuery('#W5TxtWaterProperty').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark64).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark64)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark64).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark65 = 'Please Describe';
		 jQuery('#W5TxtWasteProperty').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark65).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark65)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark65).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark66 = 'Add a value for outbuildings ';
		 jQuery('#W5TxtAdditionalProperty').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark66).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark66)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark66).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark67 = 'Years';
		 jQuery('#W5TxtYears').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark67).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark67)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark67).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark68 = 'Square Footage';
		 jQuery('#W5TxtSpace').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark68).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark68)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark68).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark69 = 'Taxes Per Year';
		 jQuery('#W5TxtTaxes').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark69).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark69)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark69).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark70 = 'Condo Fees Per Month';
		 jQuery('#W5TxtCondoFee').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark70).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark70)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark70).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark71 = 'Monthly Lot Rental Fee';
		 jQuery('#W5TxtRentalFee').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark71).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark71)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark71).addClass('watermark');
		 
		 //Wizard 6
		  //////////////////////////////////////////
		   var watermark72 = 'Purchase price';
		 jQuery('#W6TxtConsPrice').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark72).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark72)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark72).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark73 = 'Approximate Value';
		 jQuery('#W6TxtConsValue').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark73).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark73)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark73).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark74 = 'Currently Owed';
		 jQuery('#W6TxtConsMoney').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark74).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark74)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark74).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark75 = 'Please Describe';
		 jQuery('#W6TxtConsDescribe1').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark75).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark75)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark75).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark76 = 'Down payment';
		 jQuery('#W6TxtMoneyDPayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark76).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark76)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark76).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark77 = 'Please Describe';
		 jQuery('#W6TxtConsDescribePaymentFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark77).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark77)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark77).addClass('watermark');
	 
		 
		  //////////////////////////////////////////
		  //////////////////////////////////////////
		   var watermark77a = 'Please Describe';
		 jQuery('#W6Txtgiftfrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark77a).addClass('watermark');
		 }).focus(function(){ 
		  if (jQuery(this).val() == watermark77a)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark77a).addClass('watermark');
	 
		 
		  //////////////////////////////////////////
		   var watermark78 = 'Click for Calendar';
		 jQuery('#W6TxtConsPossession').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark78).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark78)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark78).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark79 = 'Click for Calendar';
		 jQuery('#W6TxtConsFinancingDate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark79).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark79)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark79).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark80 = 'Purchase price';
		 jQuery('#W6TxtPortPrice').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark80).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark80)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark80).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark81 = 'Click for Calendar';
		 jQuery('#W6TxtPortPossession').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark81).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark81)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark81).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark82 = 'Click for Calendar';
		 jQuery('#W6TxtPortFinancingDate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark82).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark82)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark82).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark83 = 'Current balance';
		 jQuery('#W6TxtPortCrntBalance').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark83).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark83)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark83).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark84 = 'Interest Rate';
		 jQuery('#W6TxtPortCrntInterestRate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark84).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark84)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark84).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark85 = 'Monthly Payment';
		 jQuery('#W6TxtPortCrntPayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark85).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark85)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark85).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark86 = 'Click for Calendar';
		 jQuery('#W6TxtPortRenewalDate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark86).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark86)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark86).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark87 = 'Company Name';
		 jQuery('#W6TxtPortMortgageProvider').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark87).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark87)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark87).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark88 = 'Down payment';
		 jQuery('#W6TxtPortDownPayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark88).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark88)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark88).addClass('watermark');
		   
		  //////////////////////////////////////////
		   var watermark89 = 'Is this gift coming from immediate family member such as mom, dad, sibling, mother-in-law or father-in-law? (Sorry cannot be from a friend)';
		 jQuery('#W6TxtPortDescribePaymentFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)    
			jQuery(this).val(watermark89).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark89)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark89).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark90 = 'Value of property';
		 jQuery('#W6TxtPreAPrice').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark90).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark90)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark90).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark91 = 'Down payment';
		 jQuery('#W6TxtPreADownPayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark91).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark91)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark91).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark92 = 'Please Describe';
		 jQuery('#W6TxtPreADescribePaymentFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark92).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark92)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark92).addClass('watermark');
		 
		  //////////////////////////////////////////
		   var watermark93 = 'Purchase price';
		 jQuery('#W6TxtPurcPrice').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark93 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark93 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark93 ).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark94 = 'Down payment';
		 jQuery('#W6TxtPurcDownPayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark94 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark94 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark94 ).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark95 = 'Click for Calendar';
		 jQuery('#W6TxtPurcPossession').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark95).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark95)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark95).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark96 = 'Click for Calendar';
		 jQuery('#W6TxtPurcFinancing').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark96).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark96)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark96).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark97 = 'Current balance';
		 jQuery('#W6TxtRefnCBalance').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark97).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark97)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark97).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark98 = 'Interest Rate';
		 jQuery('#W6TxtRefnInterestRate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark98).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark98)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark98).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark99 = 'Monthly Payment';  
		 jQuery('#W6TxtRefnMortgagePayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark99).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark99)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark99).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark100 = 'Click for Calendar';
		 jQuery('#W6TxtRefnDate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark100).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark100)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark100).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark101 = 'Purchase Price';
		 jQuery('#W6TxtPImpPrice').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark101).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark101)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark101).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark102 = 'Down Payment';
		 jQuery('#W6TxtPImpDPayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark102).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark102)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark102).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark103 = 'Please Describe';
		 jQuery('#W6TxtPImpDescribePaymentFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark103).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark103)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark103).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark104 = 'Additional Financing';
		 jQuery('#W6TxtPImpRenovationAmount').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark104).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark104)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark104).addClass('watermark');
		 
		 //////////////////////////////////////////
	     var watermark105 ='Example description: \n + Kitchen cabinets \n - New carpet in living room \n - New flooring (hardwood) main floor \n - Renovate master bathroom'; 
		 jQuery('#W6TxtPimpHighDesc').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark105).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark105)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark105).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark106 = 'Click for Calendar';
		 jQuery('#W6TxtPImpPossession').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark106).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark106)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark106).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark107 = 'Click for Calendar';
		 jQuery('#W6TxtPImpFinancingDate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark107).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark107)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark107).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark108 = 'Current Balance';
		 jQuery('#W6TxtRPlusBalance').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark108).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark108)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark108).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark109 = 'Interest Rate';
		 jQuery('#W6TxtRPlusCrntInterestRate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark109).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark109)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark109).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark110 = 'Monthly Payment';
		 jQuery('#W6TxtRPlusCrntPayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark110).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark110)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark110).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark111 = 'Click for Calendar';
		 jQuery('#W6TxtRPlusRenewalDate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark111).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark111)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark111).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark112 = 'Additional Money';
		 jQuery('#W6TxtRPlusDownPayment').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark112).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark112)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark112).addClass('watermark');
		/////////////////////////////////////////////    
		   var watermark112Rtwo = 'Additional Money';  
		 jQuery('#W6TxtRPlusDownPaymentRTwo').blur(function()    {
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark112Rtwo).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark112Rtwo)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark112Rtwo).addClass('watermark');
		 		
		 //////////////////////////////////////////
		   var watermark113 = 'Maximum Amount';
		 jQuery('#W6TxtRPlusMaxAmount').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark113).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark113)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark113).addClass('watermark');
		 
		 //////////////////////////////////////////
		   var watermark114 = 'Please Describe';
		 jQuery('#W6TxtRPlusDescribeAdditionalMoney').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark114).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark114)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark114).addClass('watermark');
		   
		 //////////////////////////////////////////
  		   var watermark11455renewaldate = 'Click for Calendar';  
		 jQuery('#W2Txtrdate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark11455renewaldate).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark11455renewaldate)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark11455renewaldate).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark128 = "Applicant's Full Name";
		 jQuery('#W0TxtAppSign').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark128).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark128)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark128).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark129 = "2nd Applicant's Full Name";
		 jQuery('#W0TxtCoAppSign').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark129).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark129)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark129).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark130 = "$ Per Month";
		 jQuery('.clsW7TxtMonthlySupport').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark130).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark130)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark130).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark130coapp3 = "$ Per Month";
		 jQuery('.clsW7TxtMonthlySupportCoapp3').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark130coapp3 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark130coapp3 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark130coapp3 ).addClass('watermark');
		 
		  /////////////////////////////////////////
		  
		   var watermark130coapp2 = "$ Per Month";
		 jQuery('.clsW7TxtMonthlySupportCoapp2').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark130coapp2).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark130coapp2)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark130coapp2).addClass('watermark');
		 
		  		  //////////////////////////////////////////
		  var watermark130coapp = "$ Per Month";
		 jQuery('.clsW7TxtMonthlySupportCoapp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark130coapp).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark130coapp)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark130coapp).addClass('watermark');
		 
		  //////////////////////////////////////////
		  var watermark131 = "$ Per Month";
		 jQuery('.clsW7SelfTxtMonthlySupport').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark131).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark131)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark131).addClass('watermark');
		 
		  //////////////////////////////////////////
		 
		  var watermark132 = '$';
		 jQuery('#W8TxtMoneyAccount').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark132).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark132)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark132).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark133 = 'Make and Model';
		 jQuery('.clsmodel').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark133).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark133)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark133).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark134 = 'Value';
		 jQuery('.clsvalue').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark134).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark134)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark134).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark135 = 'Year';
		 jQuery('.clsyear').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark135).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark135)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark135).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark136 = 'Company';
		 jQuery('.clscompany').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark136).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark136)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark136).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark137 = 'Value';
		 jQuery('.clsRRSPsvalue').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark137).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark137)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark137).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark138 = 'Company';
		 jQuery('.clsNonComp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark138).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark138)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark138).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark139 = 'Value';
		 jQuery('.clsNonValue').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark139).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark139)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark139).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark140 = 'Type';
		 jQuery('.clsNonType').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark140).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark140)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark140).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark141 = 'Address';
		 jQuery('.clsAddress').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark141).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark141)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark141).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark142 = 'City';
		 jQuery('.clsCity').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark142).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark142)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark142).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark143 = 'Province';
		 jQuery('.clsProvince').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark143).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark143)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark143).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark144 = 'Postal Code';
		 jQuery('.clsCode').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark144).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark144)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark144).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark145 = 'Current Lender';
		 jQuery('.clsHoldMortgage').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark145).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark145)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark145).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark146 = 'Amount';
		 jQuery('.clsMortAmount').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark146).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark146)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark146).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark147 = 'Payment/Month';
		 jQuery('.clsMonth').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark147).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark147)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark147).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark148 = 'Interest Rate';
		 jQuery('.clsIntRate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark148).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark148)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark148).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark149 = 'Click for Calendar';
		 jQuery('.clsRDate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark149).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark149)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark149).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark150 = 'Value';
		 jQuery('#W8Txtcontents').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark150).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark150)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark150).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark151 = 'Current Lender';
		 jQuery('.clsCurrentLender').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark151).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark151)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark151).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark152 = 'Amount';
		 jQuery('.clsMortgageAmount').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark152).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark152)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark152).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark153 = 'Payment/Month';
		 jQuery('.clsPaymentMonth').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark153).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark153)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark153).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark154 = 'Interest Rate';
		 jQuery('.clsInterestRate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark154).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark154)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark154).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark155 = 'Click for Calendar';
		 jQuery('.clsRenewalDate').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark155).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark155)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark155).addClass('watermark');
		  //////////////////////////////////////////
		 
		  var watermark156 = 'Monthly Support';
		 jQuery('#W9TxtMonthlySupport').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark156).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark156)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark156).addClass('watermark');
		 
		   //////////////////////////////////////////
		 
		  var watermark157 = 'Monthly Rent';
		 jQuery('.clsMonthlyRent').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark157).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark157)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark157).addClass('watermark');
		 
		   //////////////////////////////////////////
		 
		  var watermark158 = 'Property Taxes';
		 jQuery('.clsPropTaxes').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark158).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark158)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark158).addClass('watermark');
		 
		   //////////////////////////////////////////
		 
		  var watermark159 = 'Monthly Condo Fees';
		 jQuery('.clsFees').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark159).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark159)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark159).addClass('watermark');
		 
		   //////////////////////////////////////////
	
	
		  var watermark160 = 'Estimated Value';
		 jQuery('.clsEstimatedValue').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark160).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark160)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark160).addClass('watermark');
		 
		  var watermark161 = 'Company';
		 jQuery('.clscomp').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark161).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark161)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark161).addClass('watermark');
		 
		  var watermark162 = 'Amount Being Paid';
		 jQuery('.clsamtpaid').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark162).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark162)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark162).addClass('watermark');
		 
		   var watermark163 = 'Password';
		 jQuery('#txtPlainPassword').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark163).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark163)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark163).addClass('watermark');
		 
		  var watermark164 = 'Purchase Price';
		 jQuery('#W6TxtConsPriceInclude').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark164).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark164)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark164).addClass('watermark');
		 
		 var watermark165 = 'What Length';   
		 jQuery('#W6ConsHowLongAmortization').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark165).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark165)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark165).addClass('watermark');
		 
		  var watermark166 = 'Months';
		 jQuery('.clsW7TxtPreNumberofMonths').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark166).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark166)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark166).addClass('watermark');
		 ///////
		 
		 
		  var watermark167 = 'You have indicated you are either Married or Common-Law.  Adding your significant other as a co-applicant can significantly strengthen your position with lenders.  If you choose not to include them on this application, many lenders require a reason for not doing so.  Can you please provide your reason? ';
		 jQuery('#W2TxtWhy').blur(function(){
		  if (jQuery(this).val().trim().length == 0) 
			jQuery(this).val(watermark167).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark167)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark167).addClass('watermark');
		 
		 
		  var watermark168 = 'Please Describe';
		 jQuery('#W6TxtPurcDescribePaymentFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark168).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark168)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark168).addClass('watermark');
		 
		  var watermark169 = 'Is this gift coming from immediate family member such as mom, dad, sibling, mother-in-law or father-in-law? (Sorry cannot be from a friend)';
		 jQuery('#W6TxtPreGiftFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark169).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark169)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark169).addClass('watermark');
		 
		  var watermark170 = 'Is this gift coming from immediate family member such as mom, dad, sibling, mother-in-law or father-in-law? (Sorry cannot be from a friend)';  
		 jQuery('#W6TxtPurcGiftFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark170).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark170)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark170).addClass('watermark');
		 
		 
		 var watermark170aa3 = 'Is this gift coming from immediate family member such as mom, dad, sibling, mother-in-law or father-in-law? (Sorry cannot be from a friend)';  
		 jQuery('#W6TxtPImpgiftFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark170aa3).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark170aa3)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark170aa3).addClass('watermark');
		 
		 var watermark170aa4 = 'Is this gift coming from immediate family member such as mom, dad, sibling, mother-in-law or father-in-law? (Sorry cannot be from a friend)';  
		 jQuery('#W6TxtConsDescribePaymentFrom').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark170aa4).addClass('watermark');
		 }).focus(function(){  
		  if (jQuery(this).val() == watermark170aa4)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark170aa4).addClass('watermark');
		 
		 
		  var watermark171 = '$ Per Month';
		 jQuery('#W7TxtRetGetMonth').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark171).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark171)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark171).addClass('watermark');
		 
		  var watermark172 = '$ Per Month';
		 jQuery('.W72Sourcesofincome_Amountcls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark172).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark172)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark172).addClass('watermark');
		 //////
		 	  var watermark172ba = '$ Per Month';
		 jQuery('.W72Sources2ofincome_Amountcls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark172ba).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark172ba)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark172ba).addClass('watermark');
		 //////
		 	  var watermark172ab = '$ Per Month';
		 jQuery('.W72typeofadd2income_Amountcls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark172ab).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark172ab)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark172ab).addClass('watermark');
		 //////
		 	  var watermark172a = '$ Per Month';
		 jQuery('.W72typeofaddincome_Amountcls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark172a).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark172a)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark172a).addClass('watermark');
		 
		 /////
		  var watermark173 = 'Please Describe';
		 jQuery('.W72Sourcesofincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark173).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark173)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark173).addClass('watermark');
		 
		 // jQuery('.clswatermarked').each(function() {
		  // jQuery(this).watermark('watermark', jQuery(this).attr('title'));
		// });
		//jQuery('#W1TxtLoginPassword').watermark('watermark', 'Enter text here');
		
		//Mask Input




	  	 var watermark174 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_1').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark175).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark175)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark175).addClass('watermark');
		  //////////////////////////////////////////
		 
  		var watermark175 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_2').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark175).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark175)
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark175).addClass('watermark');
		  //////////////////////////////////////////
		 
	  	 var watermark176 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_3').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark176 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark176 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark176 ).addClass('watermark');
		  //////////////////////////////////////////
		 
  		var watermark177 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_4').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark177 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark177 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark177 ).addClass('watermark');
		  //////////////////////////////////////////
		 

  		 var watermark178 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_5').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark178 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark178 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark178 ).addClass('watermark');
		  //////////////////////////////////////////
		 
  		var watermark179 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_6').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark179 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark179 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark179 ).addClass('watermark');
		  //////////////////////////////////////////
		 
	  	 var watermark180 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_7').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark180 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark180 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark180 ).addClass('watermark');
		  //////////////////////////////////////////
		 
  		var watermark181 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_8').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark181 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark181 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark181 ).addClass('watermark');
		  //////////////////////////////////////////
		 
 
	  	 var watermark182 = 'Click for Calendar';
		 jQuery('#W8Txt1RDate_9').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark182 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark182 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark182 ).addClass('watermark');
		  //////////////////////////////////////////
		 
  		var watermark183 = 'Click for Calendar';
		 jQuery('#W9TxtInterestRate1').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark183 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark183 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark183 ).addClass('watermark');
		  //////////////////////////////////////////
		 
		var watermark184 = 'Please describe';
		 jQuery('.W72typeofincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark184 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark184 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark184 ).addClass('watermark');
		  //////////////////////////////////////////
		var watermark185 = 'Please describe';
		 jQuery('.W72Selftypeofincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark185 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark185 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark185 ).addClass('watermark');
		  //////////////////////////////////////////
		var watermark186 = 'Please describe';
		 jQuery('.W73typeofincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark186 ).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark186 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark186 ).addClass('watermark');
		  //////////////////////////////////////////
		var watermark187 = 'Please describe';
		 jQuery('.W73Selftypeofincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark187).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark187 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark187 ).addClass('watermark');
		  //////////////////////////////////////////
		  //////////////////////////////////////////
		var watermark188 = 'Please describe';
		 jQuery('.W72typeofaddincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark188).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark188 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark188 ).addClass('watermark');
		  //////////////////////////////////////////
		var watermark189 = 'Please describe';
		 jQuery('.W72typeofaddincomedetail_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark189).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark189 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark189 ).addClass('watermark');
////////////////
		var watermark190 = 'Please describe';
		 jQuery('.W72typeofadd2income_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark190).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark190 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark190 ).addClass('watermark');
////////////////
		var watermark191 = 'Please describe';
		 jQuery('.W73typeofadd2income_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark191).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark191 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark191 ).addClass('watermark');

////////////////
		var watermark192 = 'Please describe';
		 jQuery('.W72Sources2ofincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark192).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark192 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark192 ).addClass('watermark');
////////////////
		var watermark193 = 'Please describe';
		 jQuery('.W72Selftype2ofincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark193).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark193 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark193 ).addClass('watermark');
//////////////
		var watermark194 = 'Please describe';
		 jQuery('.W73Selftype2ofincome_Desccls').blur(function(){
		  if (jQuery(this).val().trim().length == 0)
			jQuery(this).val(watermark194).addClass('watermark');
		 }).focus(function(){
		  if (jQuery(this).val() == watermark194 )
			jQuery(this).val('').removeClass('watermark');
		 }).val(watermark194 ).addClass('watermark');




























		
		// jQuery("#W1TxtCell").mask("9999999999");
		// jQuery("#W2TxtCellSec").mask("9999999999"); 
		// jQuery("#W2TxtCell").mask("9999999999");
		// jQuery("#W1TxtHome").mask("9999999999");
		// jQuery("#W1TxtWork").mask("9999999999");
		// jQuery("#W2TxtHome").mask("9999999999");
		// jQuery("#W2TxtWork").mask("9999999999");
		// jQuery("#W2TxtHomeSec").mask("9999999999");
		// jQuery("#W2TxtWorkSec").mask("9999999999");
		// jQuery("#W2TxtSIN").mask("999999999");
		// jQuery("#W2TxtSINSec").mask("999999999");		
		//Password Box Watermark
		jQuery("#txtPlainPassword").show();
		jQuery("#W1TxtLoginPassword").hide();		
		jQuery("#txtPlainPassword").focus(function() {
		  jQuery(this).hide();  
		  jQuery("#W1TxtLoginPassword").show();
		  jQuery("#W1TxtLoginPassword").focus();
		})
		jQuery("#W1TxtLoginPassword").blur(function() {
			if(jQuery(this).val().length == 0)
			{
			   jQuery(this).hide();  
			   jQuery("#txtPlainPassword").show();
			}      
		});
		
		//WaterMark for Home Phone Number
		jQuery("#W1TxtHome").blur(function(){
			if(jQuery("#W1TxtHome").val()!='')
			{
				jQuery("#W1TxtHome").removeClass("watermark");
			}
			else
			{
				jQuery("#W1TxtHome").addClass("watermark");
			}
		});
		
		//WaterMark for Cell Number
		jQuery("#W1TxtCell").blur(function(){
			if(jQuery("#W1TxtCell").val()!='')
			{
				jQuery("#W1TxtCell").removeClass("watermark");
			}
			else
			{
				jQuery("#W1TxtCell").addClass("watermark");
			}
		});
		
		//WaterMark for Cell Number
		jQuery("#W2TxtCell").blur(function(){
			if(jQuery("#W2TxtCell").val()!='')
			{
				jQuery("#W2TxtCell").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtCell").addClass("watermark");
			}
		});
		
			//WaterMark for Cell Number
		jQuery("#W2TxtCellSec").blur(function(){
			if(jQuery("#W2TxtCellSec").val()!='')
			{
				jQuery("#W2TxtCellSec").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtCellSec").addClass("watermark");
			}
		});
		
		
		//WaterMark for Work Phone Number
		jQuery("#W1TxtWork").blur(function(){
			if(jQuery("#W1TxtWork").val()!='')
			{
				jQuery("#W1TxtWork").removeClass("watermark");
			}
			else
			{
				jQuery("#W1TxtWork").addClass("watermark");
			}
		});
		
		//WaterMark for Home Phone Number
		jQuery("#W2TxtHome").blur(function(){
			if(jQuery("#W2TxtHome").val()!='')
			{
				jQuery("#W2TxtHome").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtHome").addClass("watermark");
			}
		});
		
		//WaterMark for Work Phone Number
		jQuery("#W2TxtWork").blur(function(){
			if(jQuery("#W2TxtWork").val()!='')
			{
				jQuery("#W2TxtWork").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtWork").addClass("watermark");
			}
		});
		 
		//WaterMark for Home Phone Number
		jQuery("#W2TxtHomeSec").blur(function(){
			if(jQuery("#W2TxtHomeSec").val()!='')
			{
				jQuery("#W2TxtHomeSec").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtHomeSec").addClass("watermark");
			}
		});
		
		//WaterMark for Work Phone Number
		jQuery("#W2TxtWorkSec").blur(function(){
			if(jQuery("#W2TxtWorkSec").val()!='')
			{
				jQuery("#W2TxtWorkSec").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtWorkSec").addClass("watermark");
			}
		});
		
		//WaterMark for Work Phone Number
		jQuery("#W2TxtWorkSec").blur(function(){
			if(jQuery("#W2TxtWorkSec").val()!='')
			{
				jQuery("#W2TxtWorkSec").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtWorkSec").addClass("watermark");
			}
		});
		
		
		//WaterMark for SIN
		jQuery("#W2TxtSIN").blur(function(){
			if(jQuery("#W2TxtSIN").val()!='')
			{
				jQuery("#W2TxtSIN").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtSIN").addClass("watermark");
			}
		});
		
		//WaterMark for Sec APP SIN
		jQuery("#W2TxtSINSec").blur(function(){
			if(jQuery("#W2TxtSINSec").val()!='')
			{
				jQuery("#W2TxtSINSec").removeClass("watermark");
			}
			else
			{
				jQuery("#W2TxtSINSec").addClass("watermark");
			}
		});
		
		
		//Function for drop down list water mark
		jQuery("#W1DrpProvince option:not([disabled])").css("color", "#000");
		jQuery("#W1DrpProvince").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W4TxtProvince option:not([disabled])").css("color", "#000");
		jQuery("#W4TxtProvince").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W1DrpRelationship option:not([disabled])").css("color", "#000");
		jQuery("#W1DrpRelationship").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W2DrpProvince option:not([disabled])").css("color", "#000");
		jQuery("#W2DrpProvince").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W2DrpRelationship option:not([disabled])").css("color", "#000");
		jQuery("#W2DrpRelationship").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W2DrpProvinceSec option:not([disabled])").css("color", "#000");
		jQuery("#W2DrpProvinceSec").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W2DrpRelationshipSec option:not([disabled])").css("color", "#000");
		jQuery("#W2DrpRelationshipSec").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W8Drp1NonType option:not([disabled])").css("color", "#000");
		jQuery("#W8Drp1NonType").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W8Drp2NonType option:not([disabled])").css("color", "#000");
		jQuery("#W8Drp2NonType").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W8Drp3NonType option:not([disabled])").css("color", "#000");
		jQuery("#W8Drp3NonType").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W8Drp4NonType option:not([disabled])").css("color", "#000");
		jQuery("#W8Drp4NonType").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W8Drp5NonType option:not([disabled])").css("color", "#000");
		jQuery("#W8Drp5NonType").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W9DrpLoanType1 option:not([disabled])").css("color", "#000");
		jQuery("#W9DrpLoanType1").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W9DrpLoanType2 option:not([disabled])").css("color", "#000");
		jQuery("#W9DrpLoanType2").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W9DrpLoanType3 option:not([disabled])").css("color", "#000");
		jQuery("#W9DrpLoanType3").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W9DrpLoanType4 option:not([disabled])").css("color", "#000");
		jQuery("#W9DrpLoanType4").change(function() {
			jQuery(this).removeClass('watermark ');
		});
		
		jQuery("#W9DrpLoanType5 option:not([disabled])").css("color", "#000");
		jQuery("#W9DrpLoanType5").change(function() {
			jQuery(this).removeClass('watermark ');
		});



		
		/* additional script 55% page*/
	/* 		var fun_bank_gift	= function(show,hide)
			{
			
				jQuery(function($) {
				$('#W6ConsDPaymentFrom5').click(function(){ 
              
                  var selectVal2=$('#W6ConsDPaymentFrom1').val();
				 
				  if(selectVal2=='1')
				  { 
					$("#W6Divgiftfrom2").show();
				  }
				  else
				  {
					$("#W6Divgiftfrom").show();
				  }
		
                
					});
					});
								
			} */
		/* additional script */


});

