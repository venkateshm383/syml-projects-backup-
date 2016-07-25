<?php
	ini_set('max_input_vars', 99999999999999999999);
    /* echo "'W8Txt1Address>>>".$_REQUEST['W8Txt1Address']."<br/>";
     echo "non- RRSP,s==".$_POST['W8Txt1NonCompany']."<br/>";
     echo "child====".$_POST['W7TxtMonthlySupport']."<br/>";
     echo "res==".$_REQUEST['W7TxtMonthlySupport']."<br/>";
     */
	error_reporting(E_ALL);
	ini_set('display_errors',1);

	header('Access-Control-Allow-Origin: *');
	include("lib/xmlrpc.inc");
	define('classpath', dirname(__FILE__));    
	require_once classpath.'/OEfunctions.php';
	
/*	$id = $_POST['crm_lead'];
	$action = $_POST['action'];
	$ip = $_POST['txtpageip'];
	$db_name = $_POST['txtdbname'];
	$user_name = $_POST['txtusername'];
	$password = $_POST['txtpassword'];
	$url = 'http://'.$ip.':8069 xmlrpc/';
	
	echo "id:::".$id;
	echo "action:::".$action;
	echo "ip:::".$ip;
	echo "db_name:::".$db_name;
	echo "user_name:::".$user_name;
	echo "password:::".$password;
	echo "url:::".$url;

	
	$OEobj = new OpenERP($url,$db_name,$user_name,$password);*/
	
    //$OEobj  =  new OpenERP('http://127.0.0.1:8069/xmlrpc/','syml_14_mar','admin','syml');   
    $OEobj  =  new OpenERP('http://openerp.crm.local:8069/xmlrpc/','symlsys','guy@visdom.ca','VisdomTesting');
   //   $OEobj  =  new OpenERP('https://crm.visdom.ca:8069/xmlrpc/','symlsys','admin','BusinessPlatform@Visdom1'); 
	//$OEobj  =  new OpenERP(' https://crm.visdom.ca:8069/xmlrpc/','symlsys','admin','syml');
       // $OEobj  =  new OpenERP('http://192.168.1.11:8069/xmlrpc/','test_default','admin','admin');
	
	$visdom_id = $_POST['txtcrmid'];
	/*echo " crm_id>>>>>".$visdom_id;
	echo "pp_id>>>".$_REQUEST['pageid'];
	echo "your_ip_address>>>>>".$_SERVER['REMOTE_ADDR'];
	*/
	
		

	$key = array(new xmlrpcval(array(new xmlrpcval('visdom_id' , "string"), // here we pass col name 			
			new xmlrpcval('=',"string"), // here we pass condition 
			new xmlrpcval($visdom_id,"int")),"array"), // value it may be int or string 
			);

//page1
	if($_REQUEST['pageid']==1){
		if($_POST['W2DrpRelationship']!='')	{
				$W2DrpRelationship = $_POST['W2DrpRelationship'];
		}
		else{
			$W2DrpRelationship = 'Other';
		}
		
		echo "page id>>>>>>".$_REQUEST['pageid'];
		$W2TxtCell = str_replace('-', '', $_POST['W2TxtCell']);
		$W2TxtWork = str_replace('-', '', $_POST['W2TxtWork']);
		$W2TxtHome = str_replace('-', '', $_POST['W2TxtHome']);
		$W2TxtSIN = str_replace('-', '', $_POST['W2TxtSIN']);
		
		
		
		$arrayVal1 = array(
		    'name'=>new xmlrpcval($_POST['W2TxtFirstNsme'], "string"),
		    'middle_name'=>new xmlrpcval($_POST['W2TxtMiddleName'], "string"),
		    'last_name'=>new xmlrpcval($_POST['W2TxtLastName'], "string"),
		    'email'=>new xmlrpcval($_POST['W2TxtEmail'], "string"),
		    'mobile'=>new xmlrpcval($W2TxtCell, "string"), 
		    'phone'=>new xmlrpcval($W2TxtHome, "string"),
		    'work_phone'=>new xmlrpcval($W2TxtWork, "string"),
		    //Best
		    //'zip'=>new xmlrpcval($_POST['W2TxtCode'], "string"),
		    //'street'=>new xmlrpcval($_POST['W2TxtStreet'], "string"),	
		    //'street2'=>new xmlrpcval($_POST['W2TxtCity'], "string"),
		    //Province
		    'birthdate'=>new xmlrpcval($_POST['W2TxtDOB'], "string"),
		    'sin'=>new xmlrpcval($W2TxtSIN, "string"),
		   // 'move_date'=>new xmlrpcval($_POST['W2TxtMove'],"string"),	
		    'lander_id'=>new xmlrpcval($_POST['txtcrmid'],"int"),	
		    
		    'preferred_phone'=>new xmlrpcval($_POST['W2DrpBest'], "string"),
		    'zip'=>new xmlrpcval($_POST['W2TxtCode'], "string"),
		    'street'=>new xmlrpcval($_POST['W2TxtStreet'], "string"),	
		    'street2'=>new xmlrpcval($_POST['W2TxtCity'], "string"),
		    'province'=>new xmlrpcval($_POST['W2DrpProvince'], "string"),
		    //Relationship Status
		    //2nd Applicant: (All Fields....)		
		    );
			if($_POST['W2TxtMove'])
			{
				$arrayVal1['move_date'] = new xmlrpcval($_POST['W2TxtMove'],"string");
			}
			
		$OEobj->write('res.partner', $_POST['txtresid'], $arrayVal1);
		echo "test===";
		//print_r($arrayVal1);		

		$app_rec_id = $OEobj->new_applicant_record($_POST['txtcrmid'],$W2DrpRelationship,1);
		//$app_rec_id = $OEobj->applicant_record($_POST['txtcrmid'],$W2DrpRelationship,1);
		if ($_POST['W2SecondApp'] == 1){
			if($_POST['W2DrpRelationshipSec']!='')	{
				$W2DrpRelationship = $_POST['W2DrpRelationshipSec'];
			}
			else{
				$W2DrpRelationship = 'Other';
			}			
			//$app_sec_rec_id = $OEobj->applicant_record($_POST['txtcrmid'],$W2DrpRelationship,2);
			$app_sec_rec_id = $OEobj->new_applicant_record($_POST['txtcrmid'],$W2DrpRelationship,2);
		}
			$today = date("m/d/Y H:i:s");
			echo "todays date".$today;
			$txtcrmid=$_POST['txtcrmid'];
			echo "crmid-========================".$txtcrmid;
			
		$arrayVal1_opp = array(
			'type'=>new xmlrpcval("opportunity","string"),
			'application_date'=>new xmlrpcval($today,"string"),
			'application_no'=>new xmlrpcval($txtcrmid,"string"),
		);
		print_r($arrayVal1_opp);
		$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal1_opp);
		
		
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('name' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval('Partial App',"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('crm.case.stage',$key_applicant);
		echo "response111111111111111111111111111111111111";
		$stage_id=$response_app_id[0];
		echo "stage---------------------------idddddddddddddd",$stage_id;
		print_r($response_app_id); 
		
				
		$state_array = array (
			'stage_id'=>new xmlrpcval($stage_id,"int")
		);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $state_array);

	        
	}



//last page
	if($_REQUEST['pageid']==2){
		echo "txt page id 2.";
		echo "date>>>".date('Y-m-d');
		$arrayVal2 = array(
			'signature'=>new xmlrpcval($_POST['W0TxtAppSign'],"string"),
			'signature_ip'=>new xmlrpcval($_SERVER['REMOTE_ADDR'], "string"),
			'consent_dateTime'=>new xmlrpcval(date('Y-m-d'), "string"),
			);
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
		$search_id = $response_app_id[0];		
		if ($search_id){
			$OEobj->write('applicant.record', $search_id, $arrayVal2);
		}
		
		if ($_POST['W2SecondApp'] == 1){
			$arrayVal2 = array(
				'signature'=>new xmlrpcval($_POST['W0TxtCoAppSign'],"string"),
				'signature_ip'=>new xmlrpcval($_SERVER['REMOTE_ADDR'], "string"),
				'consent_dateTime'=>new xmlrpcval(date('Y-m-d'), "string"),
				);
			$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
					new xmlrpcval('=',"string"), // here we pass condition 
					new xmlrpcval($_POST['W2TxtSINSec'],"string")),"array"), // value it may be int or string 
					);
			$response_app_id = $OEobj->search('applicant.record',$key_applicant);
			$search_id = $response_app_id[0];		
			if ($search_id){
				$OEobj->write('applicant.record', $search_id, $arrayVal2);
			}		
			
		}
	}

     
       
	if($_REQUEST['pageid']==3){
		echo "txt page id 3.";
		
		if ($_POST['W3Goal'] == 1){
			$arrayVal4_opp = array(
				'what_is_your_lending_goal'=>new xmlrpcval($_POST['W3Goal'],"string"),
				'looking_fora'=>new xmlrpcval($_POST['W3Goal1Look'],"string"),		
			);
		}
		
		if ($_POST['W3Goal'] == 2 || $_POST['W3Goal'] == 3){
			
			if ($_POST['W3Goal'] == 2){
				if ($_POST['W3Goal2Building'] == 6){
					$W3Goal2Building = 7;
				}
				else{
					$W3Goal2Building = $_POST['W3Goal2Building'];
				}
				if ($_POST['W3Goal2Look'] == 1){
					$looking_to = 7;
				}
				if ($_POST['W3Goal2Look'] == 2){
					$looking_to = 5;
				}
				if ($_POST['W3Goal2Look'] == 3){
					$looking_to = 6;
				}
				if ($_POST['W3Goal2Look'] == 4){
					$looking_to = 8;
				}
			}
			else{
				if ($_POST['W3Goal3Renewing'] == 6){
					$W3Goal2Building = 7;
				}
				else{
					$W3Goal2Building = $_POST['W3Goal3Renewing'];
				}
				$looking_to =  $_POST['W3Goal3Look'];
				if ($looking_to == 4){
					$looking_to =  9;
				}
			}			
			echo "w3gollllllllllllllllllllll===========".$_POST['W3Goal'];
			echo "w3gollll22222222222222222222222222===========".$W3Goal2Building;	
			echo "llll22222222222222222222222222===========".$looking_to;	
			
            $arrayVal4_opp = array(
				'what_is_your_lending_goal'=>new xmlrpcval($_POST['W3Goal'],"string"),
				'looking_fora'=>new xmlrpcval($W3Goal2Building,"string"),		
				'looking_to'=>new xmlrpcval($looking_to,"string"),		
			);
		}
		print_r($arrayVal4_opp);
		$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal4_opp);

	}



	if($_REQUEST['pageid']==4){		
		echo "page nooooooooooooo".$_REQUEST['pageid'];		
		$arrayVal4_opp = array(
				'property_style'=>new xmlrpcval($_POST['W4Prop'],"string"),
				'property_type'=>new xmlrpcval($_POST['W4BType'],"string"),
				'plan'=>new xmlrpcval($_POST['W4TxtPlan'],"string"),	
				'lot'=>new xmlrpcval($_POST['W4TxtLot'],"string"),  	
				'block'=>new xmlrpcval($_POST['W4TxtBlock'],"string"),
				'Township_PID'=>new xmlrpcval($_POST['W4TxtPID'],"string"),
				'mls'=>new xmlrpcval($_POST['W4TxtMLS'],"string"),
				
		);
		$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal4_opp);
		
		if ($_POST['W4Address'] == 1){			
			$a = $_POST['W4TxtCity'];			
			if (!empty($_POST['W4TxtStreet2']))
			{	$street2 = $_POST['W4TxtStreet2'];
			}
			else
			{
				$street2 = " ";
			}			
			$arrayVal4_pro_add = array(
				'address'=>new xmlrpcval($_POST['W4TxtStreet']." ".$street2,"string"),
				'city'=>new xmlrpcval($a,"string"),
				'province'=>new xmlrpcval($_POST['W4TxtProvince'],"string"),
				'postal_code'=>new xmlrpcval($_POST['W4TxtCode'],"string"),
			);
			//print_r($arrayVal4_pro_add);
			$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal4_pro_add);
		}
		else{
		
		if (!empty($_POST['W4TxtNewStreet2']))
			{	$street2 = $_POST['W4TxtNewStreet2'];
			}
			else
			{
				$street2 = " ";
			}	
			
			$arrayVal4_pro_add = array(
				'address'=>new xmlrpcval($_POST['W4TxtNewStreet']." ".$street2,"string"),
				'city'=>new xmlrpcval($_POST['W4TxtNewCity'],"string"),
				'province'=>new xmlrpcval($_POST['W4DrpNewProvince'],"string"),
				'postal_code'=>new xmlrpcval($_POST['W4TxtNewCode'],"string"),
			);
			$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal4_pro_add);
		}


	}

	if($_REQUEST['pageid']==5){		
		echo "page nooooooooooooo".$_REQUEST['pageid'];

		$W5TxtTaxes = str_replace(',', '', $_POST['W5TxtTaxes']);
		$W5TxtCondoFee = str_replace(',', '', $_POST['W5TxtCondoFee']);
		
		$arrayVal5_opp = array(
				'heating'=>new xmlrpcval($_POST['W5HeatedProp'],"string"),
				'water'=>new xmlrpcval($_POST['W5WaterProp'],"string"),
				'sewage'=>new xmlrpcval($_POST['W5WasteProp'],"string"),
				'condo_fees'=>new xmlrpcval($W5TxtCondoFee,"double"),
				'garage_type'=>new xmlrpcval($_POST['W5GarageProp'],"string"),
				'garage_size'=>new xmlrpcval($_POST['W5GSizeProp'],"string"),	
				'age'=>new xmlrpcval($_POST['W5TxtYears'],"int"),
				'square_footage'=>new xmlrpcval($_POST['W5TxtSpace'],"int"),
				'property_taxes'=>new xmlrpcval($W5TxtTaxes,"double"),		
				'outbuildings_value'=>new xmlrpcval($_POST['W5TxtAdditionalProperty'],"double"),
		);	
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $arrayVal5_opp);

	}
	
	if(($_REQUEST['pageid']==6) || ((($_REQUEST['pageid']==4) || ($_REQUEST['pageid']==5) ) && ($_POST['W3Goal']==1))){	
		echo "inside pageee 66666666666666";
		//$l = 1;
               $l = '';
		if ($_POST['W3Goal'] == 1){
			$l = 2;
		}
		if ($_POST['W3Goal'] == 2){
			$l = 3;
		}

		
	//	When is your possession date?	
		$expected_closingdate = '';
		if(!empty($_POST['W6TxtConsPossession'])){
			$expected_closingdate = $_POST['W6TxtConsPossession']; 			
			}
		else if(!empty($_POST['W6TxtPortPossession'])){
			$expected_closingdate = $_POST['W6TxtPortPossession']; 			
			}
		else if(!empty($_POST['W6TxtPurcPossession'])){
			$expected_closingdate = $_POST['W6TxtPurcPossession']; 			
			}
		else if(!empty($_POST['W6TxtPImpPossession'])){
			$expected_closingdate = $_POST['W6TxtPImpPossession']; 			
		}
		else
			echo "inside else";
		
		echo "expected_closing_date".$expected_closingdate;
		
//		When do you need to have condition of financing removed?
		$condition_of_financingdate = '';
		if(!empty($_POST['W6TxtPImpFinancingDate'])){
			$condition_of_financingdate = $_POST['W6TxtPImpFinancingDate']; 			
			}
		else if(!empty($_POST['W6TxtPurcFinancing'])){
			$condition_of_financingdate = $_POST['W6TxtPurcFinancing']; 			
			}
		else if(!empty($_POST['W6TxtPortFinancingDate'])){
			$condition_of_financingdate = $_POST['W6TxtPortFinancingDate']; 			
			}
		else if(!empty($_POST['W6TxtConsFinancingDate'])){
			$condition_of_financingdate = $_POST['W6TxtConsFinancingDate']; 			
			}			
		else
			echo "inside else";

		echo "condition_of_financingdate".$condition_of_financingdate;
	
	// What is the purchase price of the property?		
		$propertyvalue = 0;
		if(!empty($_POST['W6TxtConsPrice'])){
			$propertyvalue = str_replace(',', '', $_POST['W6TxtConsPrice']);				
			}
		else if(!empty($_POST['W6TxtPortPrice'])){
			$propertyvalue = str_replace(',', '', $_POST['W6TxtPortPrice']);							
			}
		else if(!empty($_POST['W6TxtPurcPrice'])){
			$propertyvalue = str_replace(',', '', $_POST['W6TxtPurcPrice']);				
			}
		else if(!empty($_POST['W6TxtPImpPrice'])){
			$propertyvalue = str_replace(',', '', $_POST['W6TxtPImpPrice']);			
			}
		else if(!empty($_POST['W6TxtPreAPriceRefinanceplus'])){
			$propertyvalue = str_replace(',', '', $_POST['W6TxtPreAPriceRefinanceplus']);			
			}			
		else
			echo "inside else";
	    echo "property value".$propertyvalue;
		
//	How much money are you putting as a down payment?			
		$down_paymentamount = 0;
		if(!empty($_POST['W6TxtMoneyDPayment'])){
			$down_paymentamount = str_replace(',', '', $_POST['W6TxtMoneyDPayment']);				
			}
		else if(!empty($_POST['W6TxtPurcDownPayment'])){
			$down_paymentamount = str_replace(',', '', $_POST['W6TxtPurcDownPayment']);							
			}
		else if(!empty($_POST['W6TxtPImpDPayment'])){
			$down_paymentamount = str_replace(',', '', $_POST['W6TxtPImpDPayment']);				
			}			
		else
			echo "inside else";
		echo "down_paymentamount".$down_paymentamount;
//	 What is the current balance owing on your mortgage?(Approximately)	
		 $current_balance_owing = 0;
		 if(!empty($_POST['W6TxtPortCrntBalance'])){
			$current_balance_owing = str_replace(',', '', $_POST['W6TxtPortCrntBalance']);				
			}
		else if(!empty($_POST['W6TxtRefnCBalance'])){
			$current_balance_owing = str_replace(',', '', $_POST['W6TxtRefnCBalance']);							
			}
		else if(!empty($_POST['W6TxtRPlusBalance'])){
			$current_balance_owing = str_replace(',', '', $_POST['W6TxtRPlusBalance']);				
			}			
		else
			echo "inside else";
		echo "current balance owing".$current_balance_owing;
// 	What is your current Mortgage Interest Rate?
		$current_mortgage_interest_rate = 0;
		if(!empty($_POST['W6TxtPortCrntInterestRate'])){
			$current_mortgage_interest_rate = str_replace(',', '', $_POST['W6TxtPortCrntInterestRate']);				
			}
		else if(!empty($_POST['W6TxtRefnInterestRate'])){
			$current_mortgage_interest_rate = str_replace(',', '', $_POST['W6TxtRefnInterestRate']);							
			}
		else if(!empty($_POST['W6TxtRPlusCrntInterestRate'])){
			$current_mortgage_interest_rate = str_replace(',', '', $_POST['W6TxtRPlusCrntInterestRate']);				
			}			
		else
			echo "inside else";
		echo "current Mortgage Interest Rate".$current_mortgage_interest_rate;
	
//	What is the amount of your current Monthly Mortgage Payment? 
		$current_monthly_mortgage_payment = 0;
		if(!empty($_POST['W6TxtPortCrntPayment'])){
			$current_monthly_mortgage_payment = str_replace(',', '', $_POST['W6TxtPortCrntPayment']);				
			}
		else if(!empty($_POST['W6TxtRefnMortgagePayment'])){
			$current_monthly_mortgage_payment = str_replace(',', '', $_POST['W6TxtRefnMortgagePayment']);							
			}
		else if(!empty($_POST['W6TxtRPlusCrntPayment'])){
			$current_monthly_mortgage_payment = str_replace(',', '', $_POST['W6TxtRPlusCrntPayment']);				
			}			
		else
			echo "inside else";
		echo "current Monthly Mortgage Payment".$current_monthly_mortgage_payment;
//	When is the Renewal Date on your current Mortgage?
		$Renewal_current_mortgage_date = "";
		if(!empty($_POST['W2Txtrdate'])){
			$Renewal_current_mortgage_date = $_POST['W2Txtrdate']; 			
			}
		else if(!empty($_POST['W6TxtRefnDate'])){
			$Renewal_current_mortgage_date = $_POST['W6TxtRefnDate']; 			
			}
		else if(!empty($_POST['W6TxtRPlusRenewalDate'])){
			$Renewal_current_mortgage_date = $_POST['W6TxtRPlusRenewalDate']; 			
			}		
		else
			echo "inside else";
		
		echo "Renewal Date on your current Mortgage".$Renewal_current_mortgage_date;

	// Desired Mortgage Amount	
		$desired_mortgageamount = $propertyvalue - $down_paymentamount;
		$arrayVal6_opp = array(
				//'expected_closing_date'=>new xmlrpcval($expected_closingdate,"string"),				
				//'condition_of_financing_date'=>new xmlrpcval($condition_of_financingdate,"string"),				
				'desired_mortgage_amount'=>new xmlrpcval($desired_mortgageamount,"double"),
				'property_value'=>new xmlrpcval($propertyvalue,"double"),
				'down_payment_amount'=>new xmlrpcval($down_paymentamount,"double"),
				'renovation_value'=>new xmlrpcval($_POST['W6TxtPImpRenovationAmount'],"double"),
				'current_mortgage_amount'=>new xmlrpcval($current_balance_owing,"double"),
				'current_interest_rate'=>new xmlrpcval($current_mortgage_interest_rate,"double"),
				'current_monthly_payment'=>new xmlrpcval($current_monthly_mortgage_payment,"double"),
				/* 'current_renewal_date'=>new xmlrpcval($Renewal_current_mortgage_date,"string"),				 */
				'current_lender'=>new xmlrpcval($_POST['W6TxtPortMortgageProvider'],"string"),
				'new_home_warranty'=>new xmlrpcval($_POST['W6ConsWarranty'],"string"),
				
				'living_in_property'=>new xmlrpcval($_POST['W6ConsWhoLiving'],"string"),
				'desired_mortgage_type'=>new xmlrpcval($_POST['W6ConsMortgageType'],"string"),
				'desired_term'=>new xmlrpcval($_POST['W6ConsTermLength'],"string"),
				'desired_amortization'=>new xmlrpcval($_POST['W6ConsAmortizationFor'],"string"),
				'job_5_years'=>new xmlrpcval($_POST['W6Agree1'],"string"),
				'income_decreased_worried'=>new xmlrpcval($_POST['W6Agree2'],"string"),
				'future_family'=>new xmlrpcval($_POST['W6Agree3'],"string"),
				'buy_new_vehicle'=>new xmlrpcval($_POST['W6Agree4'],"string"),
				'lifestyle_change'=>new xmlrpcval($_POST['W6Agree5'],"string"),
				'financial_risk_taker'=>new xmlrpcval($_POST['W6Agree6'],"string"),
				'property_less_then_5_years'=>new xmlrpcval($_POST['W6Agree7'],"string"),
				'downpayment_amount'=>new xmlrpcval($_POST['W6TxtMoneyDPayment'],"double"),
			
		);
		
		if($expected_closingdate){
			$arrayVal6_opp['expected_closing_date']= new xmlrpcval($expected_closingdate,"string");
		}
		if($condition_of_financingdate){
			$arrayVal6_opp['condition_of_financing_date']= new xmlrpcval($condition_of_financingdate,"string");
		}		
		if($Renewal_current_mortgage_date){
			$arrayVal6_opp['current_renewal_date']=new xmlrpcval($Renewal_current_mortgage_date,"string");
		}
		// bank_amount1
		$bank_amount = 0;
		if($_POST['bank_amount1']){
			$bank_amount = $_POST['bank_amount1'];
		}
		else if($_POST['bank_amount']){
			$bank_amount = $_POST['bank_amount'];
		}
		else if($_POST['bank_amount2']){
			$bank_amount = $_POST['bank_amount2'];
		}
		else if($_POST['bank_amount3']){
			$bank_amount = $_POST['bank_amount3'];
		}
		else
		{
			echo "inside bank amount else";
		}
		if($bank_amount){
			$arrayVal6_opp['bank_account'] = new xmlrpcval($bank_amount,"double");
		}
		
		// RRSPs_amount1
		$RRSPs_amount = 0;
		if($_POST['RRSPs_amount1']){
			$RRSPs_amount = $_POST['RRSPs_amount1'];
		}
		else if($_POST['RRSPs_amount']){
			$RRSPs_amount = $_POST['RRSPs_amount'];
		}
		else if($_POST['RRSPs_amount2']){
			$RRSPs_amount = $_POST['RRSPs_amount2'];
		}
		else if($_POST['RRSPs_amount3']){
			$RRSPs_amount = $_POST['RRSPs_amount3'];
		}
		else
		{
			echo "inside RRSPs amount else";
		}
				
		if($RRSPs_amount){
			$arrayVal6_opp['rrsp_amount']= new xmlrpcval($RRSPs_amount,"double");
		}
		
// borrowed_amount
		$borrowed_amount = 0;
		if($_POST['borrowed_amount1']){
			$borrowed_amount = $_POST['borrowed_amount1'];
		}
		else if($_POST['borrowed_amount']){
			$borrowed_amount = $_POST['borrowed_amount'];
		}
		else if($_POST['borrowed_amount2']){
			$borrowed_amount = $_POST['borrowed_amount2'];
		}
		else if($_POST['borrowed_amount3']){
			$borrowed_amount = $_POST['borrowed_amount3'];
		}
		else
		{
			echo "inside borrowed amount else";
		}		
		if($borrowed_amount){
			$arrayVal6_opp['borrowed_amount']= new xmlrpcval($borrowed_amount,"double");
		}
		
		// sale_amount
		$sale_amount = 0;
		if($_POST['sale_amount1']){
			$sale_amount = $_POST['sale_amount1'];
		}
		else if($_POST['sale_amount']){
			$sale_amount = $_POST['sale_amount'];
		}
		else if($_POST['sale_amount2']){
			$sale_amount = $_POST['sale_amount2'];
		}
		else if($_POST['sale_amount3']){
			$sale_amount = $_POST['sale_amount3'];
		}
		else
		{
			echo "inside sale amount else";
		}		
		if($sale_amount){
			$arrayVal6_opp['sale_of_existing_amount']= new xmlrpcval($sale_amount,"double");
		}
		// gift_amount
		$gift_amount = 0;
		if($_POST['gift_amount1']){
			$gift_amount = $_POST['gift_amount1'];
		}
		else if($_POST['gift_amount']){
			$gift_amount = $_POST['gift_amount'];
		}
		else if($_POST['gift_amount2']){
			$gift_amount = $_POST['gift_amount2'];
		}
		else if($_POST['gift_amount3']){
			$gift_amount = $_POST['gift_amount3'];
		}
		else
		{
			echo "inside sale amount else";
		}
		if($gift_amount){
			$arrayVal6_opp['gifted_amount']= new xmlrpcval($gift_amount,"double");
		}
		
		// cash_amount
		$cash_amount = 0;
		if($_POST['cash_amount1']){
			$cash_amount = $_POST['cash_amount1'];
		}
		else if($_POST['cash_amount']){
			$cash_amount = $_POST['cash_amount'];
		}
		else if($_POST['cash_amount2']){
			$cash_amount = $_POST['cash_amount2'];
		}
		else if($_POST['cash_amount3']){
			$cash_amount = $_POST['cash_amount3'];
		}
		else
		{
			echo "inside cash amount else";
		}
		if($cash_amount){
			$arrayVal6_opp['personal_cash_amount']= new xmlrpcval($cash_amount,"double");
		}
		
		// cash_amount
		$equity_amount = 0;
		if($_POST['equity_amount1']){
			$equity_amount = $_POST['equity_amount1'];
		}
		else if($_POST['equity_amount']){
			$equity_amount = $_POST['equity_amount'];
		}
		else if($_POST['equity_amount2']){
			$equity_amount = $_POST['equity_amount2'];
		}
		else if($_POST['equity_amount3']){
			$equity_amount = $_POST['equity_amount3'];
		}
		else
		{
			echo "inside equity amount else";
		}
		if($equity_amount){
			$arrayVal6_opp['existing_equity_amount']= new xmlrpcval($equity_amount,"double");
		}
		
		// sweatequity_amount
		$sweatequity_amount = 0;
		if($_POST['sweatequity_amount1']){
			$sweatequity_amount = $_POST['sweatequity_amount1'];
		}
		else if($_POST['sweatequity_amount']){
			$sweatequity_amount = $_POST['sweatequity_amount'];
		}
		else if($_POST['sweatequity_amount2']){
			$sweatequity_amount = $_POST['sweatequity_amount2'];
		}
		else if($_POST['sweatequity_amount3']){
			$sweatequity_amount = $_POST['sweatequity_amount3'];
		}
		else
		{
			echo "inside sweatequity amount else";
		}
		if($sweatequity_amount){
			$arrayVal6_opp['sweat_equity_amount']= new xmlrpcval($sweatequity_amount,"double");
		}
		
		// finance_amount
		$finance_amount = 0;
		if($_POST['finance_amount1']){
			$finance_amount = $_POST['finance_amount1'];
		}
		else if($_POST['finance_amount']){
			$finance_amount = $_POST['finance_amount'];
		}
		else if($_POST['finance_amount2']){
			$finance_amount = $_POST['finance_amount2'];
		}
		else if($_POST['finance_amount3']){
			$finance_amount = $_POST['finance_amount3'];
		}
		else
		{
			echo "inside finance amount else";
		}
		if($finance_amount){
			$arrayVal6_opp['secondary_financing_amount']= new xmlrpcval($finance_amount,"double");
		}	

// other_amount
		$other_amount = 0;
		if($_POST['other_amount1']){
			$other_amount = $_POST['other_amount1'];
		}
		else if($_POST['other_amount']){
			$other_amount = $_POST['other_amount'];
		}
		else if($_POST['other_amount2']){
			$other_amount = $_POST['other_amount2'];
		}
		else if($_POST['other_amount3']){
			$other_amount = $_POST['other_amount3'];
		}
		else
		{
			echo "inside other amount else";
		}		
		if($other_amount){
			$arrayVal6_opp['other_amount'] = new xmlrpcval($other_amount,"double");
		}
		
		print_r($arrayVal6_opp);
		
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $arrayVal6_opp);
		
		echo "sinvalue>>>>".$_POST['W2TxtSIN'];
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
		//print_r($response_app_id);					
		$search_id = $response_app_id[0];		
		
		
		$obj1 = 'applicant.mortgage';
		$key_mortage = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($search_id,"int")),"array"), // value it may be int or string 
				);
		$response1 = $OEobj->search($obj1,$key_mortage);
		$id_val = array();
		for($j=0;$j<sizeof($response1);$j++){
			$id_val1[$j] = new xmlrpcval($response1[$j], "int");
		}
		$OEobj->unlink($obj1,$id_val1);
		
		$arrayValAppMor = array(
				'applicant_id'=>new xmlrpcval($search_id, "int"),	
				'balance'=>new xmlrpcval($_POST['W6TxtRefnCBalance'],"string"),
				'interest_rate'=>new xmlrpcval($_POST['W6TxtRefnInterestRate'],"string"),
				'monthly_payment'=>new xmlrpcval($_POST['W6TxtRefnMortgagePayment'],"string"),
				'renewal'=>new xmlrpcval($_POST['W6TxtRefnDate'],"string"),
			);
		if ($search_id && $_POST['W3Goal'] == 3){
			$OEobj->create('applicant.mortgage', $arrayValAppMor);
		}



	}
	
	if ($_REQUEST['pageid']==7){
		echo "inside page 7777777777777777777777777777777777777777";
		
		
		/*$obj1 = 'applicant.mortgage';
		$key_mortage = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
		new xmlrpcval('=',"string"), // here we pass condition 
		new xmlrpcval($search_id,"int")),"array"), // value it may be int or string 
			);
		$response1 = $OEobj->search($obj1,$key_mortage);
		$id_val = array();
		for($j=0;$j<sizeof($response1);$j++){
			$id_val1[$j] = new xmlrpcval($response1[$j], "int");
		}
		$monthly_rent= $_POST['W8Txt1MonthlyRent'];
		print "mnthly_rent----------------*******************".$monthly_rent;
		
		if($monthly_rent){
			$arrayVal7monthlyrent_opp['monthly_rent'] = new xmlrpcval($monthly_rent,"double");
		}
		
		print_r($arrayVal6_opp);

		
		$OEobj->write('applicant.mortgage', $id_val1, $arrayVal7monthlyrent_opp);*/
		
		
		// Do you currently have a term length in mind?
		$desired_term = '0';
		if(!empty($_POST['W6ConsCashbackTerm']))
		{
			$desired_term = $_POST['W6ConsCashbackTerm'];
		}
		elseif(!empty($_POST['W6ConsVariableTerm']))
		{
			$desired_term = $_POST['W6ConsVariableTerm'];
		}
		elseif(!empty($_POST['W6ConsFixedTerm']))
		{
			$desired_term = $_POST['W6ConsFixedTerm'];
		}
		elseif(!empty($_POST['W6ConsTermLength']))
		{
			$desired_term = $_POST['W6ConsTermLength'];
		}
		else
		{
			echo "inside else";
		}
		echo "desired termmmmmmmmmm".$desired_term;	
	
		$desired_amortization = 0;
		echo "desired amortization".$_POST['W6ConsAmortizationFor'];
		if($_POST['W6ConsAmortizationFor']==1)
		{
				$desired_amortization = 10;
		}
		else if($_POST['W6ConsAmortizationFor']==2)
		{
				$desired_amortization = 15;
		}
		else if($_POST['W6ConsAmortizationFor']==3)
		{
				$desired_amortization = 20;
		}
		else if($_POST['W6ConsAmortizationFor']==4)
		{
				$desired_amortization = 25;
		}
		else if($_POST['W6ConsAmortizationFor']==5)
		{
				$desired_amortization = 30;
		}
		else if($_POST['W6ConsHowLongAmortization']==5)
		{
				$desired_amortization = $_POST['W6ConsHowLongAmortization'];
		}
		else
		{
			echo "inside desired amortization";
		}
		echo $desired_amortization;

		if ($desired_term==0)
		{
			$desired_term="";
		}
			
		$arrayVal7_opp = array(
				'living_in_property'=>new xmlrpcval($_POST['W6ConsWhoLiving'],"string"),
				'desired_mortgage_type'=>new xmlrpcval($_POST['W6ConsMortgageType'],"string"),
				'desired_term'=>new xmlrpcval($desired_term,"string"),
				'desired_amortization'=>new xmlrpcval($desired_amortization,"int"),
				'job_5_years'=>new xmlrpcval($_POST['W6Agree1'],"string"),
				'income_decreased_worried'=>new xmlrpcval($_POST['W6Agree2'],"string"),
				'future_family'=>new xmlrpcval($_POST['W6Agree3'],"string"),
				'buy_new_vehicle'=>new xmlrpcval($_POST['W6Agree4'],"string"),
				'lifestyle_change'=>new xmlrpcval($_POST['W6Agree5'],"string"),
				'financial_risk_taker'=>new xmlrpcval($_POST['W6Agree6'],"string"),	
				'property_less_then_5_years'=>new xmlrpcval($_POST['W6Agree7'],"string"),
				'renter_pay_heating'=>new xmlrpcval($_POST['W6ConsPayForHeat'],"string"),
				
				
		);
		echo "xxxxxxxx";
		//print_r($arrayVal7_opp);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $arrayVal7_opp);		
	}
	

	if(($_REQUEST['pageid']==8) || (($_REQUEST['pageid']==6) && ($_POST['W3Goal']==1))){
		echo "ashi888888888888888888>>>".$_REQUEST['pageid'];
		echo "sinvalue (7 + 1)>>>>".$_POST['W2TxtSIN'];
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
	//	print_r($response_app_id);					
		$search_id = $response_app_id[0];		
		echo "^^^^^^>>>>>".$search_id;		
		//$monthlychildsupport = $OEobj->check_condition($_POST['W7TxtMonthlySupport']);
		
		##Co-Applicant#
		if ($_POST['W2SecondApp'] == 1){
			$key_sec_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSINSec'],"string")),"array"), // value it may be int or string 
				);
		$response_sec_app_id = $OEobj->search('applicant.record',$key_sec_applicant);	
		$search_sec_id = $response_sec_app_id[0];		
		}
		####
		
		
		 // How is your PRIMARY income earned?
		echo "income====".$_POST['W7IncomeEarned'];
		if ($_POST['W7IncomeEarned'] == 1){
			
			$W7TxtPaidPeriod = str_replace(',', '', $_POST['W7TxtPaidPeriod']);
			//$W7TxtHourlyWage = str_replace(',', '', $_POST['W7TxtHourlyWage']);
			//$W7TxtWeeklyWorkingHour = str_replace(',', '', $_POST['W7TxtWeeklyWorkingHour']);
			$W7TxtAnnualBonus = str_replace(',', '', $_POST['W7TxtAnnualBonus']);
			$W7TxtAvgIncome = str_replace(',', '', $_POST['W7TxtAvgIncome']);
			$W7AverageOverTime = str_replace(',', '', $_POST['W7AverageOverTime']);
	//	How are you paid?
			echo "W7HowPaid>>>".$_POST['W7HowPaid'];
			if ($_POST['W7HowPaid'] == 2){
				$annual_income = $_POST['W7TxtHourlyWage'] * $_POST['W7TxtWeeklyWorkingHour'] * 52;
				echo "annual_income>>>>>>>>>>>>",$annual_income;
				if($_POST['W7OverTimePaid'] == 1)
				{
					$over_time = $W7AverageOverTime * 12 ;
					$annual_income = $annual_income + $over_time ;
				}
				echo "annual income including overtime".$annual_income;
			}			
			if ($_POST['W7HowPaid'] == 1){
				if($_POST['W7FrequentlyPaid'] == 1){
					echo "W7TxtPaidPeriod>>>".$W7TxtPaidPeriod;
					$annual_income = $W7TxtPaidPeriod * 52;
					echo "annual_income>>>>>>>>>>>>".$annual_income;
				}
				if($_POST['W7FrequentlyPaid'] == 2){
					$annual_income = $W7TxtPaidPeriod * 26;
				}
				if($_POST['W7FrequentlyPaid'] == 3){
					$annual_income = $W7TxtPaidPeriod * 24;
				}
				if($_POST['W7FrequentlyPaid'] == 4){
					$annual_income = $W7TxtPaidPeriod * 12;
				}
			}
			// Hourly + Commission
			if ($_POST['W7HowPaid'] == 5){
				$annual_income = ($W7TxtHourlyWage * $W7TxtWeeklyWorkingHour * 52) + $W7TxtAnnualBonus;
				echo "Hourly + Commission annual_income>>>>>>>>>>>>",$annual_income;
				if($_POST['W7OverTimePaid'] == 1)
				{
					$over_time = $W7AverageOverTime * 12 ;
					$annual_income = $annual_income + $over_time ;
				}
				echo "Hourly + Commission annual income including overtime".$annual_income;
				
			}
			//  Salary + Commission
			if ($_POST['W7HowPaid'] == 6){
				echo "how paid 6.***";
				if($_POST['W7FrequentlyPaid'] == 1){
                    echo "free_paid"; 
					$annual_income = $W7TxtPaidPeriod * 52;
				}
				if($_POST['W7FrequentlyPaid'] == 2){
					$annual_income = $W7TxtPaidPeriod * 26;
				}
				if($_POST['W7FrequentlyPaid'] == 3){
					$annual_income = $W7TxtPaidPeriod * 24;
				}
				if($_POST['W7FrequentlyPaid'] == 4){
					$annual_income = $W7TxtPaidPeriod * 12;
					"an_inco=====".$annual_income;
				}
			}
			// 	Commission
			if ($_POST['W7HowPaid'] == 3){
				$annual_income = $W7TxtAvgIncome;
			}
			
			//  Salary + Bonus / Commission
			if ($_POST['W7HowPaid'] == 4){
				if($_POST['W7FrequentlyPaid'] == 1){
					$annual_income = $W7TxtPaidPeriod * 52;
				}
				if($_POST['W7FrequentlyPaid'] == 2){
					$annual_income = $W7TxtPaidPeriod * 26;
				}
				if($_POST['W7FrequentlyPaid'] == 3){
					$annual_income = $W7TxtPaidPeriod * 24;
				}
				if($_POST['W7FrequentlyPaid'] == 4){
					$annual_income = $W7TxtPaidPeriod * 12;
				}
			}
			
			$W7TxtMonthlySupport = str_replace(',', '', $_POST['W7TxtMonthlySupport']);
			$total_other_income = $OEobj->check_condition($_POST['W7OtherIncome']);
			echo "W7TxtMonthlySupport=========".$_POST['W7TxtMonthlySupport'];
			
			$arrayValAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7TxtMonthlySupport,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W72TxtSelfAvgIncome'],"string"),
						'total_other_income'=>new xmlrpcval($total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
			echo "araayyyyyyyyyyyyyyyyyyyyyyyyyyyyy";
			print_r($arrayValAppRec);
			
			print "srchhhhhhhhhhhhhhhid".$search_id;
			
			if ($search_id){
				$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				echo "appplicanttttttttt.....";				
				$OEobj->applicant_income_employer('income.employer',$search_id,1,"income_employer",$annual_income);
			}
			
			echo "cooooooooooo====".$_POST['W2SecondApp'];
			######Co-Applicant####W7CoIncomeEarnedIncomeOne
			if (($_POST['W2SecondApp'] == 1) && (($_POST['W7CoIncomeEarnedIncomeTwo'] == 1) || ($_POST['W7CoIncomeEarnedIncomeOne'] == 1))){
				echo"riyazzzzzzzzzzzsecapp^^^^^^^^^^^^^^^^^^^^^^^^^^^^";
				$W7TxtHourlyWageSecApp = str_replace(',', '', $_POST['W7TxtHourlyWageSecApp']);
				$W7TxtWeeklyWorkingHourSecApp = str_replace(',', '', $_POST['W7TxtWeeklyWorkingHourSecApp']);
				$W7TxtPaidPeriodSecApp = str_replace(',', '', $_POST['W7TxtPaidPeriodSecApp']);
				$W7TxtAnnualBonusSecApp = str_replace(',', '', $_POST['W7TxtAnnualBonusSecApp']);
				$W7TxtAvgIncomeSecApp = str_replace(',', '', $_POST['W7TxtAvgIncomeSecApp']);
				echo "W7HowPaidSecApp>>>".$_POST['W7HowPaidSecApp'];
				if ($_POST['W7HowPaidSecApp'] == 2){
					$annual_income = $W7TxtHourlyWageSecApp * $W7TxtWeeklyWorkingHourSecApp * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
				
				if ($_POST['W7HowPaidSecApp'] == 1){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
					$annual_income = $W7TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $W7TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $W7TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $W7TxtPaidPeriodSecApp * 12;
					}
				}
			
				if ($_POST['W7HowPaidSecApp'] == 5){
					$annual_income = ($W7TxtHourlyWageSecApp * $W7TxtWeeklyWorkingHourSecApp * 52) + $W7TxtAnnualBonusSecApp;
				}
				
				if ($_POST['W7HowPaidSecApp'] == 6){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
						$annual_income = $W7TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $W7TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $W7TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $W7TxtPaidPeriodSecApp * 12;
					}
				}
			
				if ($_POST['W7HowPaidSecApp'] == 3){
					$annual_income = $W7TxtAvgIncomeSecApp;
				}
			
				if ($_POST['W7HowPaidSecApp'] == 4){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
						$annual_income = $W7TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $W7TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $W7TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $W7TxtPaidPeriodSecApp * 12;
					}
				}
			
				echo "annual_income>>>>>>>>>=".$annual_income;					
				
				echo "yyyyyyyyyyyyyyyyy";
				$W7TxtMonthlySupportCoapp = str_replace(',', '', $_POST['W7TxtMonthlySupportCoapp']);
				//$total_other_income = $OEobj->check_condition($_POST['W7OtherIncome']);
				$arrayValSecAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7TxtMonthlySupportCoapp,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncomeSecApp'],"string"),
						//'total_other_income'=>new xmlrpcval($total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
				echo "sec appplicanttttttttttttttttttttttttttttttttttttt";
				print_r($arrayValSecAppRec);
				
			    echo "search_sec_id========>".$search_sec_id;
				if ($search_sec_id){
					$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec );
					$OEobj->co_applicant_income_employer('income.employer',$search_sec_id,1,"co_income_employer",$annual_income);
				}
			}
			#######
	    
	    }
	    
	    //********************************
	    /*
	    $W7OtherIncomeSecApp = $_POST['W7OtherIncomeSecApp'];
	    if ((($_POST['W7OtherIncomeSecApp'] == 1) || ($_POST['W7SelfOtherIncomeSecApp'] == 1)) && ($_POST['W7IncomeEarnedIncomeTwoSecapp'] == 1)){
				echo"inside123123132313231232134214341134434144=======================================================";
				$W72TxtHourlyWageSecApp = str_replace(',', '', $_POST['W72TxtHourlyWageSecApp']);
				$W72TxtWeeklyWorkingHourSecApp = str_replace(',', '', $_POST['W72TxtWeeklyWorkingHourSecApp']);
				$W72TxtPaidPeriodSecApp = str_replace(',', '', $_POST['W72TxtPaidPeriodSecApp']);
				$W72TxtAnnualBonusSecApp = str_replace(',', '', $_POST['W72TxtAnnualBonusSecApp']);
				$W72TxtAvgIncomeSecApp = str_replace(',', '', $_POST['W72TxtAvgIncomeSecApp']);
				echo "W7HowPaidSecApp>>>".$_POST['W72HowPaidSecApp'];
				
				if ($_POST['W72HowPaidSecApp'] == 2){
					$annual_income = $W72TxtHourlyWageSecApp * $W72TxtWeeklyWorkingHourSecApp * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
				
				
				if ($_POST['W72HowPaidSecApp'] == 1){
					if($_POST['W72FrequentlyPaidSecApp'] == 1){
					$annual_income = $W72TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 2){
						$annual_income = $W72TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 3){
						$annual_income = $W72TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 4){
						$annual_income = $W72TxtPaidPeriodSecApp * 12;
					}
				}
				
			   if ($_POST['W72HowPaidSecApp'] == 5){
					$annual_income = ($W72TxtHourlyWageSecApp * $W72TxtWeeklyWorkingHourSecApp * 52) + $W72TxtAnnualBonusSecApp;
				}
				
				
			   if ($_POST['W72HowPaidSecApp'] == 6){
					if($_POST['W72FrequentlyPaidSecApp'] == 1){
						$annual_income = $W72TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 2){
						$annual_income = $W72TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 3){
						$annual_income = $W72TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 4){
						$annual_income = $W72TxtPaidPeriodSecApp * 12;
					}
				}
				
				
				if ($_POST['W72HowPaidSecApp'] == 3){
					$annual_income = $W72TxtAvgIncomeSecApp;
				}
				
				if ($_POST['W72HowPaidSecApp'] == 4){
					if($_POST['W72FrequentlyPaidSecApp'] == 1){
						$annual_income = $W72TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 2){
						$annual_income = $W72TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 3){
						$annual_income = $W72TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 4){
						$annual_income = $W72TxtPaidPeriodSecApp * 12;
					}
				}
				
				//$test=$_POST['W72TxtMonthlySupportCoapp'];
				//$W7TxtMonthlySupportCoapp2 = str_replace(',', '', $_POST['W7TxtMonthlySupportCoapp2']);
				//echo ">>>>>>>>>>>oooooooooooooooooooooooooooooooooooo",$W7TxtMonthlySupportCoapp2;
				
				//$arrayValSecAppRec = array(
						//'monthlychildsupport'=>new xmlrpcval($W7TxtMonthlySupportCoapp2,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W72TxtSelfAvgIncomeSecApp'],"string"),
						//'total_other_income'=>new xmlrpcval($total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				//);
				
				echo "sec appplicanttttttttttttttttttttttttttttttttttttt".$annual_income;
				//print_r($arrayValSecAppRec);
				if ($search_sec_id){
					//$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec );
					$OEobj->co_applicant_income2_employer('income.employer',$search_sec_id,1,"co_income_employer",$annual_income);
				}
			}
			*/
			
			//income333333333333333333333333333333333333333333333
			/*
			$W7OtherIncomeSecApp = $_POST['W72OtherIncomeSecApp'];
	    if((($_POST['W72OtherIncomeSecApp'] == 1) || ($_POST['W72SelfOtherIncomeSecApp'] == 1)) && ($_POST['W7IncomeEarnedIncomeThreeSecapp'] == 1)){
			
				$W73TxtHourlyWageSecApp = str_replace(',', '', $_POST['W73TxtHourlyWageSecApp']);
				$W73TxtWeeklyWorkingHourSecApp = str_replace(',', '', $_POST['W73TxtWeeklyWorkingHourSecApp']);
				$W73TxtPaidPeriodSecApp = str_replace(',', '', $_POST['W73TxtPaidPeriodSecApp']);
				$W73TxtAnnualBonusSecApp = str_replace(',', '', $_POST['W73TxtAnnualBonusSecApp']);
				$W73TxtAvgIncomeSecApp = str_replace(',', '', $_POST['W73TxtAvgIncomeSecApp']);
				echo "W7HowPaidSecApp>>>".$_POST['W73HowPaidSecApp'];
				
				if ($_POST['W73HowPaidSecApp'] == 2){
					$annual_income = $W73TxtHourlyWageSecApp * $W73TxtWeeklyWorkingHourSecApp * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
				
				
				if ($_POST['W73HowPaidSecApp'] == 1){
					if($_POST['W73FrequentlyPaidSecApp'] == 1){
					$annual_income = $W73TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 2){
						$annual_income = $W73TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 3){
						$annual_income = $W73TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 4){
						$annual_income = $W73TxtPaidPeriodSecApp * 12;
					}
				}
				
			   if ($_POST['W73HowPaidSecApp'] == 5){
					$annual_income = ($W73TxtHourlyWageSecApp * $W73TxtWeeklyWorkingHourSecApp * 52) + $W73TxtAnnualBonusSecApp;
				}
				
				
			   if ($_POST['W73HowPaidSecApp'] == 6){
					if($_POST['W73FrequentlyPaidSecApp'] == 1){
						$annual_income = $W73TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 2){
						$annual_income = $W73TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 3){
						$annual_income = $W73TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 4){
						$annual_income = $W73TxtPaidPeriodSecApp * 12;
					}
				}
				
				
				if ($_POST['W73HowPaidSecApp'] == 3){
					$annual_income = $W73TxtAvgIncomeSecApp;
				}
				
				if ($_POST['W73HowPaidSecApp'] == 4){
					if($_POST['W73FrequentlyPaidSecApp'] == 1){
						$annual_income = $W73TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 2){
						$annual_income = $W73TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 3){
						$annual_income = $W73TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 4){
						$annual_income = $W73TxtPaidPeriodSecApp * 12;
					}
				}
				
				//$test=$_POST['W72TxtMonthlySupportCoapp'];
				//$W7TxtMonthlySupportCoapp3 = str_replace(',', '', $_POST['W7TxtMonthlySupportCoapp3']);
				//echo ">>>>>>>>>>>oooooooooooooooooooooooooooooooooooo",$W7TxtMonthlySupportCoapp3;
				
				/*$arrayValSecAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7TxtMonthlySupportCoapp3,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						'total_self_employed'=>new xmlrpcval($_POST['W73TxtSelfAvgIncomeSecApp'],"string"),
						//'total_other_income'=>new xmlrpcval($total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
				
				echo "sec appplicanttttttttttttttttttttttttttttttttttttt";
				//print_r($arrayValSecAppRec);
				if ($search_sec_id){
					//$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec );
					$OEobj->co_applicant_income3_employer('income.employer',$search_sec_id,1,"co_income_employer",$annual_income);
				}
			}
			*/
			
				
				
				//exit;	
				
			

			
		
		echo "annual_income>>>>>>>>>>>>oooooooooooooooooooooooooooooooooooo",$W7OtherIncomeSecApp;
	    //exit;
	    /*
	    if ($_POST['W7OtherIncomeSecApp'] == 1){
				
				$W7TxtHourlyWageSecApp = str_replace(',', '', $_POST['W7TxtHourlyWageSecApp']);
				$W7TxtWeeklyWorkingHourSecApp = str_replace(',', '', $_POST['W7TxtWeeklyWorkingHourSecApp']);
				$W7TxtPaidPeriodSecApp = str_replace(',', '', $_POST['W7TxtPaidPeriodSecApp']);
				$W7TxtAnnualBonusSecApp = str_replace(',', '', $_POST['W7TxtAnnualBonusSecApp']);
				$W7TxtAvgIncomeSecApp = str_replace(',', '', $_POST['W7TxtAvgIncomeSecApp']);
				echo "W7HowPaidSecApp>>>".$_POST['W72HowPaidSecApp'];
				if ($_POST['W72HowPaidSecApp'] == 2){
					$annual_income = $W7TxtHourlyWageSecApp * $W7TxtWeeklyWorkingHourSecApp * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
				
				if ($_POST['W7HowPaidSecApp'] == 1){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
					$annual_income = $W7TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $W7TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $W7TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $W7TxtPaidPeriodSecApp * 12;
					}
				}
			
				if ($_POST['W7HowPaidSecApp'] == 5){
					$annual_income = ($W7TxtHourlyWageSecApp * $W7TxtWeeklyWorkingHourSecApp * 52) + $W7TxtAnnualBonusSecApp;
				}
				
				if ($_POST['W7HowPaidSecApp'] == 6){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
						$annual_income = $W7TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $W7TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $W7TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $W7TxtPaidPeriodSecApp * 12;
					}
				}
			
				if ($_POST['W7HowPaidSecApp'] == 3){
					$annual_income = $W7TxtAvgIncomeSecApp;
				}
			
				if ($_POST['W7HowPaidSecApp'] == 4){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
						$annual_income = $W7TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $W7TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $W7TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $W7TxtPaidPeriodSecApp * 12;
					}
				}
			
				echo "annual_income>>>>>>>>>=".$annual_income;					
				
				echo "yyyyyyyyyyyyyyyyy";
				$W7TxtMonthlySupportCoapp = str_replace(',', '', $_POST['W7TxtMonthlySupportCoapp']);
				//$total_other_income = $OEobj->check_condition($_POST['W7OtherIncome']);
				$arrayValSecAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7TxtMonthlySupportCoapp,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncomeSecApp'],"string"),
						//'total_other_income'=>new xmlrpcval($total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
				echo "sec appplicanttttttttttttttttttttttttttttttttttttt";
				print_r($arrayValSecAppRec);
				
			    echo "search_sec_id========>".$search_sec_id;
				if ($search_sec_id){
					$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec );
					$OEobj->co_applicant_income_employer('income.employer',$search_sec_id,1,"co_income_employer",$annual_income);
				}
			}
			#######
	    
	    }
	    
	    */
	    
	    
	    //***************************
	    

	    
	    
	    
		if ($_POST['W7IncomeEarned'] == 2){
			//$annual_income = $_POST['W7TxtSelfAvgIncome'];
			$W7SelfTxtMonthlySupport = str_replace(',', '', $_POST['W7SelfTxtMonthlySupport']);
			$self_total_other_income = $OEobj->check_condition($_POST['W7SelfOtherIncome']);
			$arrayValAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7SelfTxtMonthlySupport,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncome'],"string"),
						'total_other_income'=>new xmlrpcval($self_total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
			
			if ($search_id){
				$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				$OEobj->self_applicant_income_employer('income.employer',$search_id,1,"self_income_employer");
			}
			
			
		}
			
			
			
			if ((($_POST['W7OtherIncomeSecApp'] == 1) || ($_POST['W7SelfOtherIncomeSecApp'] == 1)) && ($_POST['W7IncomeEarnedIncomeTwoSecapp'] == 1)){
				echo"inside123123132313231232134214341134434144=======================================================";
				$W72TxtHourlyWageSecApp = str_replace(',', '', $_POST['W72TxtHourlyWageSecApp']);
				$W72TxtWeeklyWorkingHourSecApp = str_replace(',', '', $_POST['W72TxtWeeklyWorkingHourSecApp']);
				$W72TxtPaidPeriodSecApp = str_replace(',', '', $_POST['W72TxtPaidPeriodSecApp']);
				$W72TxtAnnualBonusSecApp = str_replace(',', '', $_POST['W72TxtAnnualBonusSecApp']);
				$W72TxtAvgIncomeSecApp = str_replace(',', '', $_POST['W72TxtAvgIncomeSecApp']);
				echo "W7HowPaidSecApp>>>".$_POST['W72HowPaidSecApp'];
				
				if ($_POST['W72HowPaidSecApp'] == 2){
					$annual_income = $W72TxtHourlyWageSecApp * $W72TxtWeeklyWorkingHourSecApp * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
				
				
				if ($_POST['W72HowPaidSecApp'] == 1){
					if($_POST['W72FrequentlyPaidSecApp'] == 1){
					$annual_income = $W72TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 2){
						$annual_income = $W72TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 3){
						$annual_income = $W72TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 4){
						$annual_income = $W72TxtPaidPeriodSecApp * 12;
					}
				}
				
			   if ($_POST['W72HowPaidSecApp'] == 5){
					$annual_income = ($W72TxtHourlyWageSecApp * $W72TxtWeeklyWorkingHourSecApp * 52) + $W72TxtAnnualBonusSecApp;
				}
				
				
			   if ($_POST['W72HowPaidSecApp'] == 6){
					if($_POST['W72FrequentlyPaidSecApp'] == 1){
						$annual_income = $W72TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 2){
						$annual_income = $W72TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 3){
						$annual_income = $W72TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 4){
						$annual_income = $W72TxtPaidPeriodSecApp * 12;
					}
				}
				
				
				if ($_POST['W72HowPaidSecApp'] == 3){
					$annual_income = $W72TxtAvgIncomeSecApp;
				}
				
				if ($_POST['W72HowPaidSecApp'] == 4){
					if($_POST['W72FrequentlyPaidSecApp'] == 1){
						$annual_income = $W72TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 2){
						$annual_income = $W72TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 3){
						$annual_income = $W72TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W72FrequentlyPaidSecApp'] == 4){
						$annual_income = $W72TxtPaidPeriodSecApp * 12;
					}
				}
				
				//$test=$_POST['W72TxtMonthlySupportCoapp'];
				//$W7TxtMonthlySupportCoapp2 = str_replace(',', '', $_POST['W7TxtMonthlySupportCoapp2']);
				//echo ">>>>>>>>>>>oooooooooooooooooooooooooooooooooooo",$W7TxtMonthlySupportCoapp2;
				
				//$arrayValSecAppRec = array(
						//'monthlychildsupport'=>new xmlrpcval($W7TxtMonthlySupportCoapp2,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W72TxtSelfAvgIncomeSecApp'],"string"),
						//'total_other_income'=>new xmlrpcval($total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				//);
				
				echo "sec appplicanttttttttttttttttttttttttttttttttttttt".$annual_income;
				echo "secidddddddddddddddddddddddd-000000-0000000-09".$search_sec_id;
				//print_r($arrayValSecAppRec);
				if ($search_sec_id){
					echo"calledddddddddddddddddddddddddd";
					//$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec );
					$OEobj->co_applicant_income2_employer('income.employer',$search_sec_id,1,"co_income_employer",$annual_income);
				}
			}
			
			
			if((($_POST['W72OtherIncomeSecApp'] == 1) || ($_POST['W72SelfOtherIncomeSecApp'] == 1)) && ($_POST['W7IncomeEarnedIncomeThreeSecapp'] == 1)){
			
				$W73TxtHourlyWageSecApp = str_replace(',', '', $_POST['W73TxtHourlyWageSecApp']);
				$W73TxtWeeklyWorkingHourSecApp = str_replace(',', '', $_POST['W73TxtWeeklyWorkingHourSecApp']);
				$W73TxtPaidPeriodSecApp = str_replace(',', '', $_POST['W73TxtPaidPeriodSecApp']);
				$W73TxtAnnualBonusSecApp = str_replace(',', '', $_POST['W73TxtAnnualBonusSecApp']);
				$W73TxtAvgIncomeSecApp = str_replace(',', '', $_POST['W73TxtAvgIncomeSecApp']);
				echo "W7HowPaidSecApp>>>".$_POST['W73HowPaidSecApp'];
				
				if ($_POST['W73HowPaidSecApp'] == 2){
					$annual_income = $W73TxtHourlyWageSecApp * $W73TxtWeeklyWorkingHourSecApp * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
				
				
				if ($_POST['W73HowPaidSecApp'] == 1){
					if($_POST['W73FrequentlyPaidSecApp'] == 1){
					$annual_income = $W73TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 2){
						$annual_income = $W73TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 3){
						$annual_income = $W73TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 4){
						$annual_income = $W73TxtPaidPeriodSecApp * 12;
					}
				}
				
			   if ($_POST['W73HowPaidSecApp'] == 5){
					$annual_income = ($W73TxtHourlyWageSecApp * $W73TxtWeeklyWorkingHourSecApp * 52) + $W73TxtAnnualBonusSecApp;
				}
				
				
			   if ($_POST['W73HowPaidSecApp'] == 6){
					if($_POST['W73FrequentlyPaidSecApp'] == 1){
						$annual_income = $W73TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 2){
						$annual_income = $W73TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 3){
						$annual_income = $W73TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 4){
						$annual_income = $W73TxtPaidPeriodSecApp * 12;
					}
				}
				
				
				if ($_POST['W73HowPaidSecApp'] == 3){
					$annual_income = $W73TxtAvgIncomeSecApp;
				}
				
				if ($_POST['W73HowPaidSecApp'] == 4){
					if($_POST['W73FrequentlyPaidSecApp'] == 1){
						$annual_income = $W73TxtPaidPeriodSecApp * 52;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 2){
						$annual_income = $W73TxtPaidPeriodSecApp * 26;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 3){
						$annual_income = $W73TxtPaidPeriodSecApp * 24;
					}
					if($_POST['W73FrequentlyPaidSecApp'] == 4){
						$annual_income = $W73TxtPaidPeriodSecApp * 12;
					}
				}
				
				//$test=$_POST['W72TxtMonthlySupportCoapp'];
				//$W7TxtMonthlySupportCoapp3 = str_replace(',', '', $_POST['W7TxtMonthlySupportCoapp3']);
				//echo ">>>>>>>>>>>oooooooooooooooooooooooooooooooooooo",$W7TxtMonthlySupportCoapp3;
				
				/*$arrayValSecAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7TxtMonthlySupportCoapp3,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						'total_self_employed'=>new xmlrpcval($_POST['W73TxtSelfAvgIncomeSecApp'],"string"),
						//'total_other_income'=>new xmlrpcval($total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
				
				echo "sec appplicanttttttttttttttttttttttttttttttttttttt";*/
				//print_r($arrayValSecAppRec);
				if ($search_sec_id){
					//$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec );
					$OEobj->co_applicant_income3_employer('income.employer',$search_sec_id,1,"co_income_employer",$annual_income);
				}
			}
			
			
			
			//1231232341223
			
			
			
			//co app second selfemp
			if ((($_POST['W7OtherIncomeSecApp'] == 1) || ($_POST['W7SelfOtherIncomeSecApp'] == 1)) && ($_POST['W7IncomeEarnedIncomeTwoSecapp'] == 2)){
				echo"999999999999999999999999999999999999999999999991111111111111111111111111111111";
				if ($search_sec_id){
					$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec);
					$OEobj->co_self_applicant_income2_employer('income.employer',$search_sec_id,1,"co_self_income_employer",$annual_income);
				}
				
				
				}
			
			
			//co app third selfemp	
			if ((($_POST['W72OtherIncomeSecApp'] == 1) || ($_POST['W72SelfOtherIncomeSecApp'] == 1)) && ($_POST['W7IncomeEarnedIncomeThreeSecapp'] == 2)){
				
				if ($search_sec_id){
					$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec);
					$OEobj->co_self_applicant_income3_employer('income.employer',$search_sec_id,1,"co_self_income_employer",$annual_income);
				}
				
				
				}
			
		
	    	
	    
		
		
		######Co-Applicant####
			if (($_POST['W2SecondApp'] == 1) && (($_POST['W7CoIncomeEarnedIncomeTwo'] == 2) || ($_POST['W7CoIncomeEarnedIncomeOne'] == 2))){
			
				echo"co self appp enter riyazxaaaaaaaaaaaaaaaaaaaaaaaaaazzz";
				$W7SelfTxtMonthlySupportSecApp = str_replace(',', '', $_POST['W7SelfTxtMonthlySupportSecApp']);
				$self_total_other_income = $OEobj->check_condition($_POST['W7SelfOtherIncomeSecApp']);
				$arrayValSecAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7SelfTxtMonthlySupportSecApp,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncomeSecApp'],"string"),
						'total_other_income'=>new xmlrpcval($self_total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
			
				if ($search_sec_id){
					$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec);
					$OEobj->co_self_applicant_income_employer('income.employer',$search_sec_id,1,"co_self_income_employer");
				}
			}
			#######12312233
		
		
		
		//selfemployed 2nd income
			
			if ((($_POST['W7SelfOtherIncome'] == 1) ||($_POST['W7OtherIncome'] == 1)) && ($_POST['W7IncomeEarnedIncomeTwo'] == 2)){
				
				
			echo "enterrrrrrrrrrrrr2222222222222222222222222RIYAZZZZZZZZZZZZZZZZZZZZ".$search_id;
			//;
			//$annual_income = $_POST['W7TxtSelfAvgIncome'];
			//$W7SelfTxtMonthlySupport = str_replace(',', '', $_POST['W7SelfTxtMonthlySupport']);
			//$self_total_other_income = $OEobj->check_condition($_POST['W7SelfOtherIncome']);
			//$arrayValAppRec = array(
						//'monthlychildsupport'=>new xmlrpcval($W7SelfTxtMonthlySupport,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncome'],"string"),
						//'total_other_income'=>new xmlrpcval($self_total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				//);
			
			if ($search_id){
				//$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				$OEobj->self_applicant_income2_employer('income.employer',$search_id,1,"self_income_employer");
			}
		}
			
			//self second income end
			
	//3rd income start		
	if ((($_POST['W72SelfOtherIncome'] == 1) || ($_POST['W72OtherIncome'] == 1)) && (($_POST['W7SelfIncomeEarnedIncomeThree'] == 2) ||($_POST['W7IncomeEarnedIncomeThree'] == 2))){
				
			
			echo "enterrrrrrrrrrrrr3333333333333333333333333333333333INNNNNNNNNNNNNNNNNNNNNNNN".$search_id;
			//exit;
			//$annual_income = $_POST['W7TxtSelfAvgIncome'];
			//$W7SelfTxtMonthlySupport = str_replace(',', '', $_POST['W7SelfTxtMonthlySupport']);
			//$self_total_other_income = $OEobj->check_condition($_POST['W7SelfOtherIncome']);
			//$arrayValAppRec = array(
						//'monthlychildsupport'=>new xmlrpcval($W7SelfTxtMonthlySupport,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncome'],"string"),
						//'total_other_income'=>new xmlrpcval($self_total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				//);
			
			if ($search_id){
				//$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				$OEobj->self_applicant_income3_employer('income.employer',$search_id,1,"self_income_employer");
			}
		}
		//3rd incomeend

	    
		if ($_POST['W7IncomeEarned'] == 4){
			$W7BothTxtMonthlySupport = str_replace(',', '', $_POST['W7BothTxtMonthlySupport']);
			$both_total_other_income = $OEobj->check_condition($_POST['W7BothOtherIncome']);
			$arrayValAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7BothTxtMonthlySupport,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncome'],"string"),
						'total_other_income'=>new xmlrpcval($both_total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
			
			if ($search_id){
				$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				$OEobj->both_applicant_income_employer('income.employer',$search_id,1,"both_income_employer");
			}
			
			######Co-Applicant####
			if ($_POST['W2SecondApp'] == 1){
				$W7BothTxtMonthlySupport = str_replace(',', '', $_POST['W7BothTxtMonthlySupport']);
				$self_total_other_income = $OEobj->check_condition($_POST['W7OtherIncomeSecAppTwo']);
				$arrayValAppRecSecApp = array(
						'monthlychildsupport'=>new xmlrpcval($W7BothTxtMonthlySupport,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						//'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncomeSecApp'],"string"),
						'total_other_income'=>new xmlrpcval($self_total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
			
				if ($search_sec_id){
					$OEobj->write('applicant.record', $search_sec_id, $arrayValAppRecSecApp);
					$OEobj->co_both_applicant_income_employer('income.employer',$search_sec_id,1,"co_both_income_employer");
				}
			}
			#######			
	    }
	    
	    
	    


	    if ($_POST['W72Sourcesofincome_lbl'] == 1){
			    $SourcesOfIncome = 0;
			    $arraySourcesOfIncome = array();
				for($j=1;$j<9;$j++){
					//$arraySourcesOfIncome = array();	
					if (($_POST['W72typeofincome'.$j] !='')){
						$SourcesOfIncome = $SourcesOfIncome + $_POST['W72typeofincome'.$j];
						//$arraySourcesOfIncome = array(
									//'visdom_id'=>new xmlrpcval($visdom_id, "int"),
				    				//'previous_employment'=>new xmlrpcval($_POST[$w7.'TxtPreEmp'], "string"),
				    				//'job_title'=>new xmlrpcval($_POST[$w7.'TxtPreJobTitle'], "string"),
				    				//'annual'=>new xmlrpcval($_POST[$w7.'TxtPreAnnuallyPaid'], "string"),
				    				//'of_months'=>new xmlrpcval($_POST[$w7.'TxtPreNumberofMonths'], "string"),

						//);
					}
				}
				$arraySourcesOfIncome = array(
									'total_other_income'=>xmlrpcval($SourcesOfIncome, "int"),
						
				);
				echo "SourcesOfIncome=====>".$SourcesOfIncome;
				if (!empty($arraySourcesOfIncome)){
						$OEobj->write('applicant.record', $search_id, $arraySourcesOfIncome);
				}
			
		}
	    

	}






	if(($_REQUEST['pageid']==9) || ((($_REQUEST['pageid']==7) || ($_REQUEST['pageid']==8) ) && ($_POST['W3Goal']==1))){
		echo "txt page id 8+1.";
		echo "ashi9>>>".$_REQUEST['pageid'];
		
		/*
		$arrayVal8 = array(
			      'money_account'=>new xmlrpcval($_POST['W8TxtMoneyAccount'],"string"),
			      'vehicles'=>new xmlrpcval($_POST['W8Vehicles'],"string"),
			      'rrsps'=>new xmlrpcval($_POST['W8RRSPs'],"string"),
			      'non_rsp'=>new xmlrpcval($_POST['W8NonRSP'],"string"),
			      're_state'=>new xmlrpcval($_POST['W8REstate'],"string"),
			      'contents'=>new xmlrpcval($_POST['W8Txtcontents'],"string"),
			      
			      'estimated_value'=>new xmlrpcval($_POST['W8Txt1EstimatedValue'],"string"),	
			      'monthly_rent'=>new xmlrpcval($_POST['W8Txt1MonthlyRent'],"string"),	
			      'prop_taxes'=>new xmlrpcval($_POST['W8Txt1PropTaxes'],"string"),	
			      'fees'=>new xmlrpcval($_POST['W8Txt1Fees'],"string"),
			      'estimated_value1'=>new xmlrpcval($_POST['W8Txt1EstimatedValue1'],"string"),	
			      'monthly_rent1'=>new xmlrpcval($_POST['W8Txt2MonthlyRent'],"string"),	
			      'prop_taxes1'=>new xmlrpcval($_POST['W8Txt2PropTaxes'],"string"),	
			      'fees1'=>new xmlrpcval($_POST['W8Txt2Fees'],"string"),

			      'estimated_value2'=>new xmlrpcval($_POST['W8Txt1EstimatedValue2'],"string"),	
			      'monthly_rent2'=>new xmlrpcval($_POST['W8Txt3MonthlyRent'],"string"),	
			      'prop_taxes2'=>new xmlrpcval($_POST['W8Txt3PropTaxes'],"string"),	
			      'fees2'=>new xmlrpcval($_POST['W8Txt3Fees'],"string"),
		);	
		$OEobj->write('visdom.form', $_POST['txtcrmid'], $arrayVal8);
		*/

               //###########One2Many fields##############
	       //$OEobj->detail('vehicles.detail',$key,$visdom_id,5,"vehicles_detail");	
	       //$OEobj->detail('rrsp.detail',$key,$visdom_id,5,"rrsp_detail");
	       //$OEobj->detail('non.rrsp.detail',$key,$visdom_id,5,"non_rrsp_detail");
	             //$OEobj->detail('own.any.real.estate',$key,$visdom_id,1,"own_any_real_estate");
	       //$OEobj->detail('lender',$key,$visdom_id,3,"lender");
	       //$OEobj->detail('own.any.real.estate2',$key,$visdom_id,1,"own_any_real_estate2");
	       //$OEobj->detail('lender2',$key,$visdom_id,3,"lender2");
	       //$OEobj->detail('own.any.real.estate3',$key,$visdom_id,1,"own_any_real_estate3");
	       //$OEobj->detail('lender3',$key,$visdom_id,3,"lender3");



		echo "sssiiinn(8+1)>>>>".$_POST['W2TxtSIN'];
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
	//	print_r($response_app_id);					
		$search_id = $response_app_id[0];		
		echo "######>>>>>".$search_id;		
		if ($search_id){
			//$OEobj->create('applicant.mortgage', $arrayValAppMor);
			$OEobj->applicant_detail('applicant.property',$search_id,1,"own_any_real_estate");
			$OEobj->applicant_other_assets('crm.asset',$search_id,1,"crm_asset");						

		}


	}



	if(($_REQUEST['pageid']==10) || ((($_REQUEST['pageid']==7) || ($_REQUEST['pageid']==8) ) && ($_POST['W3Goal']==1))){
		echo "txt page id 9+1.";
		echo "ashi10>>>".$_REQUEST['pageid'];
/*
		$arrayVal9 = array(
			      'liabilities_owed'=>new xmlrpcval($_POST['W9LiabilitiesOwed'],"string"),
		);	
		$OEobj->write('visdom.form', $_POST['txtcrmid'], $arrayVal9);
		
		///Search value for One2Many (liabilities_detail) and remove them from the object
		$response = $OEobj->search('liabilities.detail',$key);	
		/// Unlink the values from (liabilities_detail) object
		$id_val = array();
		for($j=0;$j<sizeof($response);$j++){
			$id_val[$j] = new xmlrpcval($response[$j], "int");
		}
		
		$OEobj->unlink('liabilities.detail',$id_val);
		///Insert values in One2Many (liabilities_detail) relation 
*/		

		echo "sssiiinn>>>>".$_POST['W2TxtSIN'];
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
		//print_r($response_app_id);					
		$search_id = $response_app_id[0];		
		echo "$$$$$$$$>>>>>".$search_id;		
		
		$obj1 = 'applicant.liabilities';
		$key_mortage = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 	
				new xmlrpcval($search_id,"int")),"array"), // value it may be int or string 
				);
		$response1 = $OEobj->search($obj1,$key_mortage);
		$id_val = array();
		for($j=0;$j<sizeof($response1);$j++){
			$id_val1[$j] = new xmlrpcval($response1[$j], "int");
		}
		$OEobj->unlink($obj1,$id_val1);		
		
		
		
		for($i=1;$i<=5;$i++){
			$arrayCreatData = array();	

			if($_POST['W9TxtComp'.$i]!=''){
				//$W9LiabilitiesOwed = $OEobj->check_condition($_POST['W9LiabilitiesOwed']);
				echo "ashhhhhhhhhhhhhhhhhhhhh";
				$a = $_POST['W9TxtAmtPaid'.$i];
				$b = floatval($a);
				$arrayCreatData = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),
		    				'type'=>new xmlrpcval($_POST['W9DrpLoanType'.$i], "string"),
		    			 	'business'=>new xmlrpcval($_POST['W9TxtComp'.$i], "string"),
		    				'credit_balance'=>new xmlrpcval($_POST['W9TxtAmtPaid'.$i], "double"),
						//'credit_limit'=>new xmlrpcval($W9LiabilitiesOwed,"string"),
						'monthly_payment'=>new xmlrpcval($_POST['W9TxtPaymentMonth'.$i],"string"),
		    				
				);				
			}
			
			
			if (!empty($arrayCreatData)){
				$OEobj->create('applicant.liabilities',$arrayCreatData);	
			}

		}

	}

	// vijay
	// page 4 pre-approved       property value & downpayment - opportunity general tab    property value & down payment coming from.
	if(($_REQUEST['pageid']==4) && ($_POST['W3Goal']==1))
	{	
		echo "W4DrpNewProvince55preeeee".$_POST['W4DrpNewProvince55pre'];		
		echo "inside  page 4 pre-approved vijayyyyyyyyyyyyyyy".$_POST['W6TxtPreAPrice'];
		echo "opportunity id".$_POST['txtcrmid'];
		$W6TxtPreAPrice = str_replace(',', '', $_POST['W6TxtPreAPrice']);
		$W6TxtPreADownPayment = str_replace(',', '', $_POST['W6TxtPreADownPayment']);
		$arrayVal6_opp = array(		
				'property_value'=>new xmlrpcval($W6TxtPreAPrice,"double"),				
				'down_payment_amount'=>new xmlrpcval($W6TxtPreADownPayment,"double"),
				'desired_mortgage_amount'=>new xmlrpcval($W6TxtPreAPrice - $W6TxtPreADownPayment,"double"),
				'considered_cites'=>new xmlrpcval($_POST['W4TxtNewCity55pre'],"string"),
				'city'=>new xmlrpcval($_POST['W4TxtNewCity55pre'],"string"),				
				'province'=>new xmlrpcval($_POST['W4DrpNewProvince55pre'],"string"),
				
		);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $arrayVal6_opp);
		
	}	
	
/* page 6  Refinance - Opportunity general tab   current balance & desired mortgage amount.
   When "Refiannce / Renew" is selected on Pge 25%, the question "Do you want additional funds over and above your current mortage amount?
	If so,how much are you wanting" the "Additional Money" field presently maps nowhere. 
	This value needs to be added to "Current Balance" field and the Summed amount needs to be saved into the "Desired Mortgage Amount" field.
	In the event this amount is left blank and the "maximum amount" checkbox is unchecked, the "Current Balance" needs to be saved into the Desired Mortgage Amount field in the General Tab of the Opporutnity Record.

*/
		if(($_REQUEST['pageid']==6) && ($_POST['W3Goal']==3))
	{		
		echo "inside  page 6 refinance ".$_POST['W6TxtRPlusDownPaymentRTwo']; 
		echo "maximum amount...................".$_POST['W6TxtRPlusDownPayment'];		
		$additional_funds = 0;
		if($_POST['W6TxtRPlusDownPayment'])
		{
			$additional_funds = $_POST['W6TxtRPlusDownPayment'];
		}
		else if($_POST['W6TxtRPlusDownPaymentRTwo'])
		{
			$additional_funds = $_POST['W6TxtRPlusDownPaymentRTwo'];
		}
		else
		{
			echo "inside additional fund";
		}			

		
		$maxcheckbox = false;
		if($_POST['W6TxtRPlusMaxAmountRTwo'])
		{
			$maxcheckbox = true;
		}
		else if($_POST['W6TxtRPlusMaxAmount'])
		{
			$maxcheckbox = true;
		}
		else
		{
			echo "inside maxcheckbox";
		}
		$current_balance = 0 ;
		if($_POST['W6TxtRPlusBalance'])
		{
			$current_balance = $_POST['W6TxtRPlusBalance'];
		}
		else if($_POST['W6TxtRefnCBalance'])
		{
			$current_balance = $_POST['W6TxtRefnCBalance'];
		}
		else
		{
			echo "inside current balance";
		}
		
		if ($maxcheckbox){ // maximum checkbox checked			
			$refinance = array (
					'current_balance'=>new xmlrpcval($additional_funds,"double"),
					'desired_mortgage_amount'=>new xmlrpcval($current_balance + $additional_funds,"double"),
					'maximum_amount'=>new xmlrpcval(true,"boolean"),
			);
		}	
		else {		// maximum checkbox Un-checked		
			$refinance = array (
					'current_balance'=>new xmlrpcval(0,"double"),
					'desired_mortgage_amount'=>new xmlrpcval($additional_funds,"double"),
					'maximum_amount'=>new xmlrpcval(false,"boolean"),
			);
		}
		//print_r($refinance);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $refinance);
	}	
	
/* "How Much Monthly Rental Income do you receive .... " 
	question is not mapping to the "Monthly Rental Income" field of OpenERP in the Opportunity Record Property Tab.

*/	
	
	if((($_REQUEST['pageid']==7) && ($_POST['W3Goal']==3)) || (($_REQUEST['pageid']==5) && ($_POST['W3Goal']==1)) || (($_REQUEST['pageid']==7) && ($_POST['W3Goal']==2)))
	{	echo "inside page 77777   --------------------------------------------------------- 33333";
		echo "monthly rental income...".$_POST['W6TxtConsMonthlyRental'];
		$W6TxtConsMonthlyRental = str_replace(',', '', $_POST['W6TxtConsMonthlyRental']);
		echo "monthly rental income...".$_POST['W6TxtConsMonthlyRental'];
				
		$rental_array = array (
			'monthly_rental_income'=>new xmlrpcval($W6TxtConsMonthlyRental,"double")
		);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $rental_array);
		
	}
	
	
	//to change the state to complete app
	
	//if(($_REQUEST['pageid']==7) && ($_POST['W3Goal']==1) || ($_REQUEST['pageid']==9) && ($_POST['W3Goal']==2))
	if((($_REQUEST['pageid']==7) && ($_POST['W3Goal']==1)) || ($_REQUEST['pageid']==9))
	{	echo "inside page 99999  ================================ 33333";
		
		//echo "monthly rental income...".$_POST['W6TxtConsMonthlyRental'];
		//$W6TxtConsMonthlyRental = str_replace(',', '', $_POST['W6TxtConsMonthlyRental']);
		//echo "monthly rental income...".$_POST['W6TxtConsMonthlyRental'];
		
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('name' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval('Completed App',"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('crm.case.stage',$key_applicant);
		echo "response111111111111111111111111111111111111";
		$stage_id=$response_app_id[0];
		echo "stage---------------------------idddddddddddddd",$stage_id;
		print_r($response_app_id); 
		
				
		$state_array = array (
			'stage_id'=>new xmlrpcval($stage_id,"int")
		);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $state_array);
		
	}
	
	
	//  ##################################  Income 2 ################################################
/*
	Do you have any other employed, self-employed incomes?
*/
	if((($_REQUEST['pageid']==6) && ($_POST['W3Goal']==1)) || (($_REQUEST['pageid']==8) && ($_POST['W3Goal']==2))){
		    echo"inside the income 22222222222222222222222222222func    ".$_POST['W7OtherIncome']; 
		    echo"inside the income  ".$_POST['W7SelfOtherIncome'];  
		    echo"inside the income  ".$_POST['W7IncomeEarnedIncomeTwo'];            
		if ((($_POST['W7SelfOtherIncome'] == 1) || ($_POST['W7OtherIncome'] == 1)) && ($_POST['W7IncomeEarnedIncomeTwo'] == 1)){
			
			echo "inside Do you have any other employed, self-employed incomes?";
			echo $_POST['W7IncomeEarnedIncomeTwo'];
		$W72TxtPaidPeriod = str_replace(',', '', $_POST['W72TxtPaidPeriod']);
		//$W72TxtHourlyWage = str_replace(',', '', $_POST['W72TxtHourlyWage']);	
		//$W72TxtWeeklyWorkingHour = str_replace(',', '', $_POST['W72TxtWeeklyWorkingHour']);	
		$W72AverageOverTime = str_replace(',', '', $_POST['W72AverageOverTime']);	
			

		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
	//	print_r($response_app_id);					
		$search_id = $response_app_id[0];		
		echo "applicant ids----------------------------".$search_id;
				
		//$monthlychildsupport = $OEobj->check_condition($_POST['W7TxtMonthlySupport']);
		
		##Co-Applicant#
		if ($_POST['W2SecondApp'] == 1){
			$key_sec_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSINSec'],"string")),"array"), // value it may be int or string 
				);
		$response_sec_app_id = $OEobj->search('applicant.record',$key_sec_applicant);	
		$search_sec_id = $response_sec_app_id[0];		
		}
		
		 // How is your PRIMARY income earned?		
		if ($_POST['W7IncomeEarnedIncomeTwo'] == 1){
			echo "How is this income earned? ".$_POST['W7IncomeEarnedIncomeTwo'];
			
			
			// How is this income earned?
			if ($_POST['W72HowPaid'] == 2){
				$annual_income = $_POST['W72TxtHourlyWage'] * $_POST['W72TxtWeeklyWorkingHour'] * 52;
				echo "annual_income>>>>>>>>>>>>",$annual_income;
				if($_POST['W72OverTimePaid'] == 1)
				{
					$over_time = $W72AverageOverTime * 12 ;
					$annual_income = $annual_income + $over_time ;
				}
				echo "annual income including overtime".$annual_income;
			}
			
			if ($_POST['W72HowPaid'] == 1){
				if($_POST['W72FrequentlyPaid1'] == 1){					
					echo "W7TxtPaidPeriod>>>".$W72TxtPaidPeriod;
					$annual_income = $W72TxtPaidPeriod * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
				if($_POST['W72FrequentlyPaid2'] == 2){
					$annual_income = $W72TxtPaidPeriod * 26;
				}
				if($_POST['W72FrequentlyPaid3'] == 3){
					$annual_income = $W72TxtPaidPeriod * 24;
				}
				if($_POST['W72FrequentlyPaid4'] == 4){
					$annual_income = $W72TxtPaidPeriod * 12;
					echo "annual_income>>>>>>>>>>>>444444444444444444444444",$annual_income;
				}
			}
			
			
			if ($search_id){
			//	$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				echo "appplicanttttttttt.....";
				echo "incomeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee.....".$annual_income;
				$OEobj->applicant_income2_employer('income.employer',$search_id,1,"income_employer",$annual_income);

			}
			
			
			
		}
	}
}
// Where is the down payment coming from? (Please select all that apply.)	
/*	if(($_REQUEST['pageid']==4) && ($_POST['W3Goal']==1)){
				echo "inside income down paymentttttttttttttttttttttttttttttt".$_POST['bank_amount1'];
				 $array_downpayment = array();
				$array_downpayment = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),
		    				'type'=>new xmlrpcval($_POST['W9DrpLoanType'.$i], "string"),
		    			 	'business'=>new xmlrpcval($_POST['W9TxtComp'.$i], "string"),
		    				'credit_balance'=>new xmlrpcval($_POST['W9TxtAmtPaid'.$i], "double"),
						//'credit_limit'=>new xmlrpcval($W9LiabilitiesOwed,"string"),
						'monthly_payment'=>new xmlrpcval($_POST['W9TxtPaymentMonth'.$i],"string"),
		    				
				);
				if(!empty($array_downpayment))
				{
					$OEobj->create('applicant.liabilities',$arrayCreatData);
				}
				
	}
*/	
// ####################  income 3  #############################		
// Do you have any other employed, self-employed or retirement incomes?		
	if((($_REQUEST['pageid']==6) && ($_POST['W3Goal']==1)) || (($_REQUEST['pageid']==8) && ($_POST['W3Goal']==2))){
		
		echo"inside the income 3333333333333333333333func    ".$_POST['W72OtherIncome']; 
		    echo"inside the income  iiiiiiiiii".$_POST['W72SelfOtherIncome'];  
		    echo"inside the income  !!!!!!!!!!!".$_POST['W7IncomeEarnedIncomeThree'];   
		    echo"other keyyyyyyyyyyyyyyyyyy".$_POST['W7SelfIncomeEarnedIncomeThree'];   
		
		if (($_POST['W72SelfOtherIncome'] == 1) || ($_POST['W72OtherIncome'] == 1)){
			if(($_POST['W7IncomeEarnedIncomeThree'] == 1) || ($_POST['W7SelfIncomeEarnedIncomeThree'] == 1)){
				echo "inside income 3";
			$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
	//	print_r($response_app_id);					
		$search_id = $response_app_id[0];		
		echo "applicant ids".$search_id;	
			//$W73TxtHourlyWage = str_replace(',', '', $_POST['W73TxtHourlyWage']);	
			//$W73TxtWeeklyWorkingHour = str_replace(',', '', $_POST['W73TxtWeeklyWorkingHour']);	
			$W73AverageOverTime = str_replace(',', '', $_POST['W73AverageOverTime']);	
        					
			//How is this income earned?					
		//if ($_POST['W7IncomeEarnedIncomeThree'] == 1){
			echo "How is this income earned? ".$_POST['W7IncomeEarnedIncomeThree'];
			echo "howwwwwwwwwwwwwwwwwwww".$_POST['W73HowPaid'];
			// How is this income earned?
			if ($_POST['W73HowPaid'] == 2){
				$annual_income = $_POST['W73TxtHourlyWage'] * $_POST['W73TxtWeeklyWorkingHour'] * 52;
				echo "annual_income>>>>>>>>>>>>",$annual_income;
				if($_POST['W73OverTimePaid'] == 1)
				{
					$over_time = $W73AverageOverTime * 12 ;
					$annual_income = $annual_income + $over_time ;
				}
				echo "annual income including overtime".$annual_income;
			}
		
			/*
			if ($_POST['W72HowPaid1'] == 1){
				if($_POST['W72FrequentlyPaid1'] == 1){					
					echo "W7TxtPaidPeriod>>>".$W72TxtPaidPeriod;
					$annual_income = $W72TxtPaidPeriod * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
				if($_POST['W72FrequentlyPaid2'] == 2){
					$annual_income = $W72TxtPaidPeriod * 26;
				}
				if($_POST['W72FrequentlyPaid3'] == 3){
					$annual_income = $W72TxtPaidPeriod * 24;
				}
				if($_POST['W72FrequentlyPaid4'] == 4){
					$annual_income = $W72TxtPaidPeriod * 12;
				}
			} */
			
			
			if ($search_id){
			//	$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				echo "appplicanttttttttt.....";
				$OEobj->applicant_income3_employer('income.employer',$search_id,1,"income_employer",$annual_income);

			}
			
			
			
		//}
				
	}
}
}
	
	

//	How many advances are required?    approved/build a property/builder progress funding
	if(!empty($_POST['W2DrpADbuilder']))
	{			
			$draws_required = array (
				'draws_required'=>new xmlrpcval($_POST['W2DrpADbuilder'],"double")
			);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $draws_required);
	}

	// "WHen do you require funds for construction"
	 if(!empty($_POST['W3Goal2Funds']))
	 {
		$construction_fund = array (
				'building_funds'=>new xmlrpcval($_POST['W3Goal2Funds'],"string")
			);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $construction_fund);
	 }
	 
	 
		// page 6 approved     condition of financing date - opportunity general tab   condition of financing date.
/*	
	if(($_REQUEST['pageid']==6) && ($_POST['W3Goal']==2))
	{
		echo "inside  page 6 approved ".$_POST['W6TxtConsFinancingDate'];
		echo "opportunity id".$_POST['txtcrmid'];
		$W6TxtPImpRenovationAmount = str_replace(',', '', $_POST['W6TxtPImpRenovationAmount']);		
		echo "amountttttttt".$W6TxtPImpRenovationAmount;
		echo "purchase price".$_POST['W6TxtPImpPrice'];
		echo "down payment".$_POST['W6TxtPImpDPayment'];
		echo "---".$_POST['W6TxtPImpPrice']-$_POST['W6TxtPImpDPayment'];
		$arrayVal6_opp = array(		
				'condition_of_financing_date'=>new xmlrpcval($_POST['W6TxtConsFinancingDate'],"string"),
				'expected_closing_date'=>new xmlrpcval($_POST['W6TxtConsPossession'],"string"), 
				'renovation_value'=>new xmlrpcval($W6TxtPImpRenovationAmount,"double"),
				'desired_mortgage_amount'=>new xmlrpcval($_POST['W6TxtPImpPrice']-$_POST['W6TxtPImpDPayment'],"double"),
				
		);
		print_r($arrayVal6_opp);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $arrayVal6_opp);
		
	}	
*/		
?>
