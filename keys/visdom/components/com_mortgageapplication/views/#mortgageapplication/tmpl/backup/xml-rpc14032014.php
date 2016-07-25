<?php
    ini_set('max_input_vars', 9999);

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
	
//    $OEobj  =  new OpenERP('http://127.0.0.1:8069/xmlrpc/','symldec','admin','syml');   
   $dbobj  =  new OpenERP('http://107.23.27.61:8069/xmlrpc/','symlsys','admin','syml'); 
	
	$visdom_id = $_POST['txtcrmid'];
	echo " crm_id>>>>>".$visdom_id;
	echo "pp_id>>>".$_REQUEST['pageid'];
	echo "your_ip_address>>>>>".$_SERVER['REMOTE_ADDR'];
	
	
		

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
		
		echo "page idxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>>>>>>".$_REQUEST['pageid'];
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
		    'sin'=>new xmlrpcval(W2TxtSIN, "string"),
		    'move_date'=>new xmlrpcval($_POST['W2TxtMove'],"string"),	
		    'lander_id'=>new xmlrpcval($_POST['txtcrmid'],"int"),	
		    
		    //Relationship Status
		    //2nd Applicant: (All Fields....)		
		    );		
		echo "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk";
		print_r($arrayVal1);
		$OEobj->write('res.partner', $_POST['txtresid'], $arrayVal1);
		echo "pageeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
		$app_rec_id = $OEobj->applicant_record($_POST['txtcrmid'],$W2DrpRelationship,1);
		if ($_POST['W2SecondApp'] == 1){
			if($_POST['W2DrpRelationshipSec']!='')	{
				$W2DrpRelationship = $_POST['W2DrpRelationshipSec'];
			}
			else{
				$W2DrpRelationship = 'Other';
			}			
			$app_sec_rec_id = $OEobj->applicant_record($_POST['txtcrmid'],$W2DrpRelationship,2);
		}
		
		$arrayVal1_opp = array(
			'type'=>new xmlrpcval("opportunity","string"),
		);
		$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal1_opp);

	        
	}



//last page
	if($_REQUEST['pageid']==2){
		echo "txt page id 2.";
		echo "date>>>".date('Y-m-d');
		$arrayVal2 = array(
			'signature'=>new xmlrpcval($_POST['W0TxtAppSign'],"string"),
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

		$arrayVal4_opp = array(
				'what_is_your_lending_goal'=>new xmlrpcval($_POST['W3Goal'],"string"),
				'preapproved_looking_fora'=>new xmlrpcval($_POST['W3Goal1Look'],"string"),		
				'approved_looking_fora'=>new xmlrpcval($_POST['W3Goal2Look'],"string"),
				'building_funds'=>new xmlrpcval($_POST['W3Goal2Funds'],"string"),	
				'refinance_looking_fora'=>new xmlrpcval($_POST['W3Goal3Look'],"string"),
				'looking_to_approved'=>new xmlrpcval($_POST['W3Goal2Building'],"string"),
				'looking_to_refinance'=>new xmlrpcval($_POST['W3Goal3Renewing'],"string"),
		);
		$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal4_opp);

	}



	if($_REQUEST['pageid']==4){
		echo "txt page id 4.";
		echo "ashi4>>>".$_REQUEST['pageid'];
		echo "paaaaag id crm>>>".$_POST['txtcrmid'];	
		$arrayVal4_opp = array(
				'property_style'=>new xmlrpcval($_POST['W4Prop'],"string"),
				'property_type'=>new xmlrpcval($_POST['W4BType'],"string"),
				'plan'=>new xmlrpcval($_POST['W4TxtPlan'],"string"),	
				'lot'=>new xmlrpcval($_POST['W4TxtLot'],"string"),  	
				'block'=>new xmlrpcval($_POST['W4TxtBlock'],"string"),
				'mls'=>new xmlrpcval($_POST['W4TxtMLS'],"string"),
				
		);
		$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal4_opp);
		
		if ($_POST['W4Address'] == 1){
			echo "W4TxtCity>>>>>>>>",$_POST['W4TxtCity'];
			$arrayVal4_pro_add = array(
				'address'=>new xmlrpcval($_POST['W4TxtStreet'],"string"),
				'city'=>new xmlrpcval($_POST['W4TxtCity'],"string"),
				'province'=>new xmlrpcval($_POST['W4TxtProvince'],"string"),
				'postal_code'=>new xmlrpcval($_POST['W4TxtCode'],"string"),
			);
			$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal4_pro_add);
		}
		else{
			$arrayVal4_pro_add = array(
				'address'=>new xmlrpcval($_POST['W4TxtNewStreet'],"string"),
				'city'=>new xmlrpcval($_POST['W4TxtNewCity'],"string"),
				'province'=>new xmlrpcval($_POST['W4DrpNewProvince'],"string"),
				'postal_code'=>new xmlrpcval($_POST['W4TxtNewCode'],"string"),
			);
			$OEobj->write('crm.lead',$_POST['txtcrmid'], $arrayVal4_pro_add);
		}


	}

	if($_REQUEST['pageid']==5){
		echo "txt page id 5.";
		echo "ashi5>>>".$_REQUEST['pageid'];

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
		);	
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $arrayVal5_opp);

	}

	if(($_REQUEST['pageid']==6) || ((($_REQUEST['pageid']==4) || ($_REQUEST['pageid']==5) ) && ($_POST['W3Goal']==1))){
		echo "txt page id 6.";
		echo "ashi6>>>".$_REQUEST['pageid'];
		echo "living_property>>>".$_POST['W6ConsWhoLiving'];


		//write fields in opp form
		echo "expected closing dt>>>>>>".$_POST['W6TxtPImpFinancingDate'];
		echo "condition_of_financing_date dt>>>>>>".$_POST['W6TxtPImpPossession'];	
	
		$W6TxtPImpPrice = str_replace(',', '', $_POST['W6TxtPImpPrice']);
		$W6TxtPImpDPayment = str_replace(',', '', $_POST['W6TxtPImpDPayment']);
		$W6TxtPImpRenovationAmount = str_replace(',', '', $_POST['W6TxtPImpRenovationAmount']);
		
		$arrayVal6_opp = array(
		
				'property_value'=>new xmlrpcval($W6TxtPImpPrice,"double"),
				'down_payment_amount'=>new xmlrpcval($W6TxtPImpDPayment,"double"),
				'renovation_value'=>new xmlrpcval($W6TxtPImpRenovationAmount,"double"),
				//'condition_of_financing_date'=>new xmlrpcval($_POST['W6TxtPImpFinancingDate'],"dateTime.iso8601"),
				//'expected_closing_date'=>new xmlrpcval($_POST['W6TxtPImpPossession'],"dateTime.iso8601"),
		
		
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
				'down_payment_coming_from'=>new xmlrpcval($_POST['W6ConsDPaymentFrom'],"string"),
				
				
				
				
		);
		$OEobj->write('crm.lead', $_POST['txtcrmid'], $arrayVal6_opp);
		
		


		echo "sinvalue>>>>".$_POST['W2TxtSIN'];
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
		print_r($response_app_id);					
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
		if ($search_id){
			$OEobj->create('applicant.mortgage', $arrayValAppMor);
		}



	}
	
	if ($_REQUEST['pageid']==7){
		echo "inside page 7777777777777777777777777777777777777777";
		$arrayVal7_opp = array(		
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
		);
		echo "xxxxxxxx";
		print_r($arrayVal7_opp);
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
		print_r($response_app_id);					
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
		
		
		
		echo "income====".$_POST['W7IncomeEarned'];
		if ($_POST['W7IncomeEarned'] == 1){
			
			echo "W7HowPaid>>>".$_POST['W7HowPaid'];
			if ($_POST['W7HowPaid'] == 2){
				$annual_income = $_POST['W7TxtHourlyWage'] * $_POST['W7TxtWeeklyWorkingHour'] * 52;
				echo "annual_income>>>>>>>>>>>>",$annual_income;
			}
			
			if ($_POST['W7HowPaid'] == 1){
				if($_POST['W7FrequentlyPaid'] == 1){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 52;
				}
				if($_POST['W7FrequentlyPaid'] == 2){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 26;
				}
				if($_POST['W7FrequentlyPaid'] == 3){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 24;
				}
				if($_POST['W7FrequentlyPaid'] == 4){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 12;
				}
			}
			
			if ($_POST['W7HowPaid'] == 5){
				$annual_income = ($_POST['W7TxtHourlyWage'] * $_POST['W7TxtWeeklyWorkingHour'] * 52) + $_POST['W7TxtAnnualBonus'];
			}
			
			if ($_POST['W7HowPaid'] == 6){
				if($_POST['W7FrequentlyPaid'] == 1){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 52;
				}
				if($_POST['W7FrequentlyPaid'] == 2){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 26;
				}
				if($_POST['W7FrequentlyPaid'] == 3){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 24;
				}
				if($_POST['W7FrequentlyPaid'] == 4){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 12;
				}
			}
			
			if ($_POST['W7HowPaid'] == 3){
				$annual_income = $_POST['W7TxtAvgIncome'];
			}
			
			if ($_POST['W7HowPaid'] == 4){
				if($_POST['W7FrequentlyPaid'] == 1){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 52;
				}
				if($_POST['W7FrequentlyPaid'] == 2){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 26;
				}
				if($_POST['W7FrequentlyPaid'] == 3){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 24;
				}
				if($_POST['W7FrequentlyPaid'] == 4){
					$annual_income = $_POST['W7TxtPaidPeriod'] * 12;
				}
			}
			
			echo "annual_income>>>>>>>>>=".$annual_income;
			
			
			
			$W7TxtMonthlySupport = str_replace(',', '', $_POST['W7TxtMonthlySupport']);
			$total_other_income = $OEobj->check_condition($_POST['W7OtherIncome']);
			$arrayValAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7TxtMonthlySupport,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						'total_self_employed'=>new xmlrpcval($_POST['W72TxtSelfAvgIncome'],"string"),
						'total_other_income'=>new xmlrpcval($total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
			
			if ($search_id){
				$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				$OEobj->applicant_income_employer('income.employer',$search_id,1,"income_employer",$annual_income);
			}
			
			echo "cooooooooooo====".$_POST['W2SecondApp'];
			######Co-Applicant####
			if ($_POST['W2SecondApp'] == 1){
				echo "W7HowPaidSecApp>>>".$_POST['W7HowPaidSecApp'];
				if ($_POST['W7HowPaidSecApp'] == 2){
					$annual_income = $_POST['W7TxtHourlyWageSecApp'] * $_POST['W7TxtWeeklyWorkingHourSecApp'] * 52;
					echo "annual_income>>>>>>>>>>>>",$annual_income;
				}
			
				if ($_POST['W7HowPaidSecApp'] == 1){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
					$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 12;
					}
				}
			
				if ($_POST['W7HowPaidSecApp'] == 5){
					$annual_income = ($_POST['W7TxtHourlyWageSecApp'] * $_POST['W7TxtWeeklyWorkingHourSecApp'] * 52) + $_POST['W7TxtAnnualBonusSecApp'];
				}
				
				if ($_POST['W7HowPaidSecApp'] == 6){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 12;
					}
				}
			
				if ($_POST['W7HowPaidSecApp'] == 3){
					$annual_income = $_POST['W7TxtAvgIncomeSecApp'];
				}
			
				if ($_POST['W7HowPaidSecApp'] == 4){
					if($_POST['W7FrequentlyPaidSecApp'] == 1){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 52;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 2){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 26;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 3){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 24;
					}
					if($_POST['W7FrequentlyPaidSecApp'] == 4){
						$annual_income = $_POST['W7TxtPaidPeriodSecApp'] * 12;
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
			    echo "search_sec_id========>".$search_sec_id;
				if ($search_sec_id){
					$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec );
					$OEobj->co_applicant_income_employer('income.employer',$search_sec_id,1,"co_income_employer",$annual_income);
				}
			}
			#######
	    
	    }

	    
	    
	    
		if ($_POST['W7IncomeEarned'] == 2){
			//$annual_income = $_POST['W7TxtSelfAvgIncome'];
			$W7SelfTxtMonthlySupport = str_replace(',', '', $_POST['W7SelfTxtMonthlySupport']);
			$self_total_other_income = $OEobj->check_condition($_POST['W7SelfOtherIncome']);
			$arrayValAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7SelfTxtMonthlySupport,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncome'],"string"),
						'total_other_income'=>new xmlrpcval($self_total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
			
			if ($search_id){
				$OEobj->write('applicant.record', $search_id, $arrayValAppRec);
				$OEobj->self_applicant_income_employer('income.employer',$search_id,1,"self_income_employer");
			}
			
			######Co-Applicant####
			if ($_POST['W2SecondApp'] == 1){
			
				
				$W7SelfTxtMonthlySupportSecApp = str_replace(',', '', $_POST['W7SelfTxtMonthlySupportSecApp']);
				$self_total_other_income = $OEobj->check_condition($_POST['W7SelfOtherIncomeSecApp']);
				$arrayValSecAppRec = array(
						'monthlychildsupport'=>new xmlrpcval($W7SelfTxtMonthlySupportSecApp,"double"),
						//'total_employed_income'=>new xmlrpcval($_POST['W7TxtPreAnnuallyPaid'],"string"),
						'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncomeSecApp'],"string"),
						'total_other_income'=>new xmlrpcval($self_total_other_income,"string"),
						//'total_income'=>new xmlrpcval($_POST['W72Sourcesofincome_Amount'],"double"),
				);
			
				if ($search_sec_id){
					$OEobj->write('applicant.record', $search_sec_id, $arrayValSecAppRec);
					$OEobj->co_self_applicant_income_employer('income.employer',$search_sec_id,1,"co_self_income_employer",$annual_income);
				}
			}
			#######
		
	    }	
	    



	    
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
						'total_self_employed'=>new xmlrpcval($_POST['W7TxtSelfAvgIncomeSecApp'],"string"),
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
	       $OEobj->detail('vehicles.detail',$key,$visdom_id,5,"vehicles_detail");	
	       $OEobj->detail('rrsp.detail',$key,$visdom_id,5,"rrsp_detail");
	       $OEobj->detail('non.rrsp.detail',$key,$visdom_id,5,"non_rrsp_detail");
	       //$OEobj->detail('own.any.real.estate',$key,$visdom_id,1,"own_any_real_estate");
	       $OEobj->detail('lender',$key,$visdom_id,3,"lender");
	       $OEobj->detail('own.any.real.estate2',$key,$visdom_id,1,"own_any_real_estate2");
	       $OEobj->detail('lender2',$key,$visdom_id,3,"lender2");
	       $OEobj->detail('own.any.real.estate3',$key,$visdom_id,1,"own_any_real_estate3");
	       $OEobj->detail('lender3',$key,$visdom_id,3,"lender3");



		echo "sssiiinn(8+1)>>>>".$_POST['W2TxtSIN'];
		$key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);
		$response_app_id = $OEobj->search('applicant.record',$key_applicant);
		print_r($response_app_id);					
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
		print_r($response_app_id);					
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
						'monthly_payment'=>new xmlrpcval('W9TxtPaymentMonth'.$i,"string"),
		    				
				);				
			}
			
			
			if (!empty($arrayCreatData)){
				$OEobj->create('applicant.liabilities',$arrayCreatData);	
			}

		}

	}

?>
