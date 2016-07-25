 <?php
//    include("lib/xmlrpc.inc");
    
	
class OpenERP
    {   
	public $serverUrl,$username,$password,$db,$userid;   
	public function __construct($url, $database, $username, $password)
	{   
		$this->serverUrl = $url;
		$this->db = $database;
		$this->username = $username;
		$this->password = $password;
		$client = new xmlrpc_client($this->serverUrl.'common');
	 		    $msg = new xmlrpcmsg('login');
			    $msg->addParam(new xmlrpcval($this->db, "string"));
			    $msg->addParam(new xmlrpcval($username, "string"));
			    $msg->addParam(new xmlrpcval($this->password, "string"));
			    $res = $client->send($msg);
			    if(!$res->faultCode())
				$this->userid=$res->value()->scalarval();

	}
			
        function create($obj,$arrayVal)
        {
			  
		if(!empty($this->userid)){		
		    $client = new xmlrpc_client($this->serverUrl.'object');
		    $msg = new xmlrpcmsg('execute');
		    $msg->addParam(new xmlrpcval($this->db, "string"));
		    $msg->addParam(new xmlrpcval($this->userid, "int"));
		    $msg->addParam(new xmlrpcval($this->password, "string"));
		    $msg->addParam(new xmlrpcval($obj, "string")); 
		    $msg->addParam(new xmlrpcval("create", "string"));
		    $msg->addParam(new xmlrpcval($arrayVal, "struct"));
		    $resp = $client->send($msg);
		    //echo "msg>>>>?=".$resp;
		    if ($resp->faultCode())
			  return 'Error: '.$resp->faultString();
		     else if(isset($resp->val->me['int']))
			return $idss = $resp->val->me['int'];	
		     else
			return 'no id found !';
		}	
        }
		
    
        function write($obj,$id,$arrayVal)
        {	    
	
		if(!empty($this->userid)){		
		    $client = new xmlrpc_client($this->serverUrl.'object');
		    $msg = new xmlrpcmsg('execute');
		    $msg->addParam(new xmlrpcval($this->db, "string"));  
		    $msg->addParam(new xmlrpcval($this->userid, "int"));	
		    $msg->addParam(new xmlrpcval($this->password, "string"));	
		    $msg->addParam(new xmlrpcval($obj, "string"));
		    $msg->addParam(new xmlrpcval("write", "string"));
		    $msg->addParam(new xmlrpcval($id, "int"));
		    $msg->addParam(new xmlrpcval($arrayVal, "struct"));
		    $res = $client->send($msg);	 
		     if($res->faultCode())
			 return 'Error: '.$res->faultString();
    		     else
			return $res->value()->Scalarval();
		}
	}

        function read($obj, $ides, $arrayVal)
        {	     
			echo "read method called.........";
		if(!empty($this->userid)){		
			$client = new xmlrpc_client($this->serverUrl.'object');
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($this->db, "string"));
			$msg->addParam(new xmlrpcval($this->userid, "int"));
			$msg->addParam(new xmlrpcval($this->password, "string"));
			$msg->addParam(new xmlrpcval($obj,"string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($ides, "array"));
			$msg->addParam(new xmlrpcval($arrayVal, "array"));
			$res = $client->send($msg);
			if ($res->faultCode())
			 return 'Error: '.$res->faultString();
			else
			{   $s =  $res->value()->scalarval();					
			    return $val = $res->value()->scalarval();
	
		        }
		}
	}
		
        function search($obj,$search_key)
        {	 
		if(!empty($this->userid)){		
		       $client = new xmlrpc_client($this->serverUrl.'object');
			$msg = new xmlrpcmsg('execute');
		    	$msg->addParam(new xmlrpcval($this->db, "string"));  
		    	$msg->addParam(new xmlrpcval($this->userid, "int"));	
		    	$msg->addParam(new xmlrpcval($this->password, "string"));	
			$msg->addParam(new xmlrpcval($obj, "string")); 
			$msg->addParam(new xmlrpcval("search", "string"));
			$msg->addParam(new xmlrpcval($search_key, "array"));
			$response_ids = $client->send($msg); 	
			if ($response_ids->faultCode())
			 return 'Error: '.$response_ids->faultString();
			else
			{
				$ids=$response_ids->value()->scalarval();
				$searchItems=array();
				foreach($ids as $id)
				{
				  $searchItems[]=$id->me['int'];
				}
				print_r($searchItems);
				return $searchItems; 
			}
		}
    }

     function unlink($obj,$arrayVal)
	{	

		if(!empty($this->userid)){		
		        $client = new xmlrpc_client($this->serverUrl.'object');
		        $msg = new xmlrpcmsg('execute');
		    	$msg->addParam(new xmlrpcval($this->db, "string"));  
		    	$msg->addParam(new xmlrpcval($this->userid, "int"));		
		    	$msg->addParam(new xmlrpcval($this->password, "string"));	
			$msg->addParam(new xmlrpcval($obj, "string"));
			$msg->addParam(new xmlrpcval("unlink", "string"));
			$msg->addParam(new xmlrpcval($arrayVal, "array")); 
			$unlink_ids = $client->send($msg);
			if($unlink_ids->faultCode())
				return 'Error: '.$unlink_ids->faultString();				
			else				
				print_r($unlink_ids); 
		}	
	}


    function previous_employment($obj,$key,$visdom_id,$call)
	{
		$response = $this->search($obj,$key);	
		$id_val = array();
		for($j=0;$j<sizeof($response);$j++){
			$id_val[$j] = new xmlrpcval($response[$j], "int");
		}
		$this->unlink($obj,$id_val);
		if ($call == 'call1')
			$w7 = 'W7';
		elseif ($call == 'call2') 
			$w7 = 'W72';  
		elseif ($call == 'call3') 
			$w7 = 'W73';  
		elseif ($call == 'self_call1') 
			$w7 = 'W7Self';
		elseif ($call == 'self_call2') 
			$w7 = 'W72Self';
		elseif ($call == 'self_call3') 
			$w7 = 'W73Self';  

		for($i=1;$i<=10;$i++){
			$arrayCreatData = array();	
				if (($i == 1) && ($_POST[$w7.'TxtPreEmp'] !='')){
					$arrayCreatData = array(
								'visdom_id'=>new xmlrpcval($visdom_id, "int"),
				    				'previous_employment'=>new xmlrpcval($_POST[$w7.'TxtPreEmp'], "string"),
				    				'job_title'=>new xmlrpcval($_POST[$w7.'TxtPreJobTitle'], "string"),
				    				'annual'=>new xmlrpcval($_POST[$w7.'TxtPreAnnuallyPaid'], "string"),
				    				'of_months'=>new xmlrpcval($_POST[$w7.'TxtPreNumberofMonths'], "string"),

					);				
				}
				elseif($_POST[$w7.'TxtPreEmp'.$i]!=''){
					$arrayCreatData = array(
								'visdom_id'=>new xmlrpcval($visdom_id, "int"),
				    				'previous_employment'=>new xmlrpcval($_POST[$w7.'TxtPreEmp'.$i], "string"),
				    			 	'job_title'=>new xmlrpcval($_POST[$w7.'TxtPreJobTitle'.$i], "string"),
				    				'annual'=>new xmlrpcval($_POST[$w7.'TxtPreAnnuallyPaid'.$i], "string"),
				    				'of_months'=>new xmlrpcval($_POST[$w7.'TxtPreNumberofMonths'.$i], "string"),
					);				
				}

			if (!empty($arrayCreatData)){
				$this->create($obj,$arrayCreatData);	
			}

		}
	}



    function detail($obj,$key,$visdom_id,$len,$str)
	{
		$response = $this->search($obj,$key);	
		$id_val = array();
		for($j=0;$j<sizeof($response);$j++){
			$id_val[$j] = new xmlrpcval($response[$j], "int");
		}
		$this->unlink($obj,$id_val);

		for($i=1;$i<=$len;$i++){
			$arrayCreatData = array();
				if($str == "vehicles_detail")
					{
						if ($_POST['W8Txt'.$i.'Model']!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'model'=>new xmlrpcval($_POST['W8Txt'.$i.'Model'], "string"),
					    				'value'=>new xmlrpcval($_POST['W8Txt'.$i.'Value'], "string"),
					    				'year'=>new xmlrpcval($_POST['W8Txt'.$i.'Year'], "string"),
					    		);				
						}
					}

				elseif($str == "rrsp_detail")
					{
						if ($_POST['W8Txt'.$i.'Company']!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'company'=>new xmlrpcval($_POST['W8Txt'.$i.'Company'], "string"),
					    				'value'=>new xmlrpcval($_POST['W8Txt'.$i.'RRSPsValue'], "string"),
					    		);				
						}
					}
				elseif($str == "non_rrsp_detail")
					{
						if ($_POST['W8Txt'.$i.'NonCompany']!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'company'=>new xmlrpcval($_POST['W8Txt'.$i.'NonCompany'], "string"),
					    				'value'=>new xmlrpcval($_POST['W8Txt'.$i.'NonValue'], "string"),
									'type'=>new xmlrpcval($_POST['W8Drp'.$i.'NonType'], "string"),
					    		);				
						}
					}
				elseif($str == "own_any_real_estate")
					{
						if ($_POST['W8Txt1Address']!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'address'=>new xmlrpcval($_POST['W8Txt1Address'], "string"),
					    				'city'=>new xmlrpcval($_POST['W8Txt1City'], "string"),
									'province'=>new xmlrpcval($_POST['W8Txt1Province'], "string"),
									'postal_code'=>new xmlrpcval($_POST['W8Txt1Code'], "string"),
					    		);				
						}
					}
				elseif($str == "lender")
					{
						if ($_POST['W8Txt1HoldMortgage_'.$i]!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'holding_mortgage'=>new xmlrpcval($_POST['W8Txt1HoldMortgage_'.$i], "string"),
					    				'amount'=>new xmlrpcval($_POST['W8Txt1MortAmount_'.$i], "string"),
									'pay_month'=>new xmlrpcval($_POST['W8Txt1Month_'.$i], "string"),
									'rate'=>new xmlrpcval($_POST['W8Txt1IntRate_'.$i], "string"),
									'renewal_date'=>new xmlrpcval($_POST['W8Txt1RDate_'.$i], "string"),	
					    		);				
						}
					}
				elseif($str == "own_any_real_estate2")
					{
						if ($_POST['W8Txt2Address']!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'address'=>new xmlrpcval($_POST['W8Txt2Address'], "string"),
					    				'city'=>new xmlrpcval($_POST['W8Txt2City'], "string"),
									'province'=>new xmlrpcval($_POST['W8Txt2Province'], "string"),
									'postal_code'=>new xmlrpcval($_POST['W8Txt2Code'], "string"),
					    		);				
						}
					}
				elseif($str == "lender2")
					{
						$k = $i+3;
						if ($_POST['W8Txt1HoldMortgage_'.$k]!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'holding_mortgage'=>new xmlrpcval($_POST['W8Txt1HoldMortgage_'.$k], "string"),
					    				'amount'=>new xmlrpcval($_POST['W8Txt1MortAmount_'.$k], "string"),
									'pay_month'=>new xmlrpcval($_POST['W8Txt1Month_'.$k], "string"),
									'rate'=>new xmlrpcval($_POST['W8Txt1IntRate_'.$k], "string"),
									'renewal_date'=>new xmlrpcval($_POST['W8Txt1RDate_'.$k], "string"),	
					    		);				
						}
					}
				elseif($str == "own_any_real_estate3")
					{
						if ($_POST['W8Txt3Address']!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'address'=>new xmlrpcval($_POST['W8Txt3Address'], "string"),
					    				'city'=>new xmlrpcval($_POST['W8Txt3City'], "string"),
									'province'=>new xmlrpcval($_POST['W8Txt3Province'], "string"),
									'postal_code'=>new xmlrpcval($_POST['W8Txt3Code'], "string"),
					    		);				
						}
					}
				elseif($str == "lender3")
					{	
						$k = $i+6;
						if ($_POST['W8Txt1HoldMortgage_'.$k]!=''){
							$arrayCreatData = array(
									'visdom_id'=>new xmlrpcval($visdom_id, "int"),
					    				'holding_mortgage'=>new xmlrpcval($_POST['W8Txt1HoldMortgage_'.$k], "string"),
					    				'amount'=>new xmlrpcval($_POST['W8Txt1MortAmount_'.$k], "string"),
									'pay_month'=>new xmlrpcval($_POST['W8Txt1Month_'.$k], "string"),
									'rate'=>new xmlrpcval($_POST['W8Txt1IntRate_'.$k], "string"),
									'renewal_date'=>new xmlrpcval($_POST['W8Txt1RDate_'.$k], "string"),	
					    		);				
						}
					}
			if (!empty($arrayCreatData)){
				$this->create($obj,$arrayCreatData);	
			}

		}
	}
	

    function applicant_detail($obj,$applicant_id,$len,$str)
	{
				echo "function applicant_detail>>";
				if($str == "own_any_real_estate")
					{
						echo "own_any_real_estate>>";
						echo "'W8Txt1Address>>>".$_POST['W8Txt1Address'];
						if ($_POST['W8Txt1Address']!=''){
							
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							$response = $this->search($obj,$key);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							
							$this->unlink($obj,$id_val);
							
							
							$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W8Txt1Address'], "string"),
					    				//'city'=>new xmlrpcval($_POST['W8Txt1City'], "string"),
									//'province'=>new xmlrpcval($_POST['W8Txt1Province'], "string"),
									//'postal_code'=>new xmlrpcval($_POST['W8Txt1Code'], "string"),
					    				'value'=>new xmlrpcval($_POST['W8Txt1EstimatedValue'], "string"),
					    				'owed'=>new xmlrpcval($_POST['W8Txt1MortAmount_1'], "string"),
					    				'annual_tax'=>new xmlrpcval($_POST['W8Txt1PropTaxes'], "string"),
									'mo_condo_fees'=>new xmlrpcval($_POST['W8Txt1Fees'], "string"),
									

					    		);
							if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
							}
				
						}
				}
	}

    function check_condition($val){
	if($val==1){
 		return 'Yes';	
	}
	else{
		return 'No';
	}	
    }


    function applicant_other_assets($obj,$applicant_id,$len,$str)
	{
		echo "W8TxtMoneyAccount==".$_POST['W8TxtMoneyAccount'];
		$arrayValMoney = array(
			      'money'=>new xmlrpcval($_POST['W8TxtMoneyAccount'],"string"),
		);	
		$this->write('applicant.record', $applicant_id, $arrayValMoney);
				if($str == "crm_asset")
					{
						echo "crm_asset>>";
							
						//$W8Vehicles = $this->check_condition($_POST['W8Vehicles']);
						//$W8RRSPs = $this->check_condition($_POST['W8RRSPs']);
						//$W8NonRSP = $this->check_condition($_POST['W8NonRSP']);
						
						/// Search and Unlink the values 
						$key = array(new xmlrpcval(array(new xmlrpcval('opportunity_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
						$response = $this->search($obj,$key);	
						$id_val = array();
						for($j=0;$j<sizeof($response);$j++){
							$id_val[$j] = new xmlrpcval($response[$j], "int");
						}
						
						$this->unlink($obj,$id_val);
 
 						//Vehicle**********
						echo "applicant id===".$applicant_id;
						for($j=1;$j<6;$j++){
							$arrayCreatData = array();
							echo "modelllll>>>>>".$_POST['W8Txt'.$j.'Model'];
							echo "mmm>>>>>".$_POST['W8Txt1Model'];
							echo "mmm2>>>>>".$_POST['W8Txt2Model'];
							echo "sin==".$_POST['W2TxtSIN'];	
							if($_POST['W8Txt'.$j.'Model']!=''){
									$arrayCreatData = array(
										'opportunity_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval($_POST['W8Txt'.$j.'Model'], "string"),
										'value'=>new xmlrpcval($_POST['W8Txt'.$j.'Value'], "string"),
										'asset_type'=>new xmlrpcval("Vehicle", "string"),
									);
									if (!empty($arrayCreatData)){
										$this->create($obj,$arrayCreatData);	
									}
							}			
						}
                                                echo "Vehicle**********";
                                                print_r($arrayCreatData);

						
						//RRSPs**********
						
						for($j=1;$j<6;$j++){
							$arrayCreatData = array();	
							if($_POST['W8Txt'.$j.'Company']!=''){
									$arrayCreatData = array(
										'opportunity_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval($_POST['W8Txt'.$j.'Company'], "string"),
										'value'=>new xmlrpcval($_POST['W8Txt'.$j.'RRSPsValue'], "string"),
										'asset_type'=>new xmlrpcval("RRSPs", "string"),
									);
									if (!empty($arrayCreatData)){
										$this->create($obj,$arrayCreatData);	
									}
							}			
						}						

                                                echo "RRSPs**********";
                                                print_r($arrayCreatData);						
                                                //non- RRSP,s**********
						
						for($j=1;$j<6;$j++){
							$arrayCreatData = array();	
							if($_POST['W8Txt'.$j.'NonCompany']!=''){
									$arrayCreatData = array(
										'opportunity_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval($_POST['W8Txt'.$j.'NonCompany'], "string"),
										'value'=>new xmlrpcval($_POST['W8Txt'.$j.'NonValue'], "string"),
										'asset_type'=>new xmlrpcval("Non-RRSPs", "string"),
									);
									if (!empty($arrayCreatData)){
										$this->create($obj,$arrayCreatData);	
									}
							}			
						}
                                                echo "non- RRSP,s**********";
                                                print_r($arrayCreatData);							
						
				}
	}




    function applicant_income_employer($obj,$applicant_id,$len,$str)
	{
				echo "income.employer>>";
				if($str == "income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							$response = $this->search($obj,$key);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							
							$this->unlink($obj,$id_val);						
						
						$array_industry = array(
									"1" => "Banking / Finance",
									"2" => "Government",
									"3" => "Education",
									"4" => "Health",
									"5" => "Manufacturing",
									"6" => "Services",
									"7" => "Resources / Transportation",
									"8" => "Other",
						);
						$industry_type = array_key_exists($_POST['W7IndustryType'], $array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						echo "W7TxtPreNumberofMonths>>>".$_POST['W7TxtPreNumberofMonths'];
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W7IndustryType']];
							echo "industry>>>".$industry;
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
					    				'business'=>new xmlrpcval($_POST['W7TxtCompName'], "string"),
					    				'position'=>new xmlrpcval($_POST['W7TxtJobTitle'], "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($_POST['W7TxtAvgIncome'], "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonths'], "int"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    
					     for($j=0;$j<10;$j++){
								$arrayCreatDataPrvEmp = array();
								echo "applicant_id==".$applicant_id;
								echo "PreEmp==".$_POST['PreEmp'];
								echo "PreJobTitle==".$_POST['PreJobTitle'];
								echo "PreAnnuallyPaid==".$_POST['PreAnnuallyPaid'];
								echo "PreNumberofMonths==".$_POST['PreNumberofMonths'];
								
								if (($j == 0) && ($_POST['PreEmp'] != '')){
									$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['PreEmp'], "string"),
												'position'=>new xmlrpcval($_POST['PreJobTitle'], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['PreAnnuallyPaid'], "string"),
												'month'=>new xmlrpcval($_POST['PreNumberofMonths'], "int"),
									   ); 
									}
								else{
									if ($_POST['PreEmp'.$j] != '' ){
										
										if ($_POST['PreEmp'.$j] != 'Previous employer'){
											$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['PreEmp'.$j], "string"),
												'position'=>new xmlrpcval($_POST['PreJobTitle'.$j], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['PreAnnuallyPaid'.$j], "string"),
												'month'=>new xmlrpcval($_POST['PreNumberofMonths'.$j], "int"),
											); 
										
										}
										
								       }  
									}
								echo "income_employer>>>>>>>>>";
								print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									echo "$arrayCreatDataPrvEmp^^^^^^^^^^^^>";
									print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
					    	
						
				
						
				}
	}
	
	
	
	
	
	



    function self_applicant_income_employer($obj,$applicant_id,$len,$str)
	{
				echo "income.employer>>";
				if($str == "self_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							$response = $this->search($obj,$key);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							
							$this->unlink($obj,$id_val);						
						
						$self_array_industry = array(
									"1" => "Banking / Finance",
									"2" => "Government",
									"3" => "Education",
									"4" => "Health",
									"5" => "Manufacturing",
									"6" => "Services",
									"7" => "Resources / Transportation",
									"8" => "Other",
						);
						$industry_type = array_key_exists($_POST['W7SelfIndustryType'], $self_array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W7SelfIndustryType']];
							echo "industry>>>".$industry;
						}
						
						$W7SelfBusClass = '';
						if ($_POST['W7SelfBusClass'] == 1){
							$W7SelfBusClass = "Corporation";
						}
						if ($_POST['W7SelfBusClass'] == 2){
							$W7SelfBusClass = "Sole Proprietorship";
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W7SelfIndustryType'], "string"),
					    				'business'=>new xmlrpcval($_POST['W7TxtSelfCompName'], "string"),
					    				'position'=>new xmlrpcval($W7SelfBusClass, "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($_POST['W7TxtSelfAvgIncome'], "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonths'], "int"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    
					     for($j=1;$j<11;$j++){
								$arrayCreatDataPrvEmp = array();
								
								
								if (($j == 1) && ($_POST['W7SelfTxtPreEmp'] != '')){
									$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['W7SelfTxtPreEmp'], "string"),
												'position'=>new xmlrpcval($_POST['W7SelfTxtPreJobTitle'], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['W7SelfTxtPreAnnuallyPaid'], "string"),
												'month'=>new xmlrpcval($_POST['W7SelfTxtPreNumberofMonths'], "int"),
									   ); 
									}
								else{
									if ($_POST['W7SelfTxtPreEmp'.$j] != '' ){
										
										if ($_POST['W7SelfTxtPreEmp'.$j] != 'Previous employer'){
											$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['W7SelfTxtPreEmp'.$j], "string"),
												'position'=>new xmlrpcval($_POST['W7SelfTxtPreJobTitle'.$j], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['W7SelfTxtPreAnnuallyPaid'.$j], "string"),
												'month'=>new xmlrpcval($_POST['W7SelfTxtPreNumberofMonths'.$j], "int"),
											); 
										
										}
										
								       }  
									}
								echo "self_income_employer>>>>>>>>>";
								print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									echo "$arrayCreatDataPrvEmp^^^^^^^^^^^^>";
									print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
					    	
						
				
						
				}
	}
	
	





    function both_applicant_income_employer($obj,$applicant_id,$len,$str)
	{
				echo "income.employer>>";
				if($str == "both_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							$response = $this->search($obj,$key);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							
							$this->unlink($obj,$id_val);						
						
						/*
						$self_array_industry = array(
									"1" => "Banking / Finance",
									"2" => "Government",
									"3" => "Education",
									"4" => "Health",
									"5" => "Manufacturing",
									"6" => "Services",
									"7" => "Resources / Transportation",
									"8" => "Other",
						);
						$industry_type = array_key_exists($_POST['W7SelfIndustryType'], $self_array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W7SelfIndustryType']];
							echo "industry>>>".$industry;
						}
						
						$W7SelfBusClass = '';
						if ($_POST['W7SelfBusClass'] == 1){
							$W7SelfBusClass = "Corporation";
						}
						if ($_POST['W7SelfBusClass'] == 2){
							$W7SelfBusClass = "Sole Proprietorship";
						}
						
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W7SelfIndustryType'], "string"),
					    				'business'=>new xmlrpcval($_POST['W7TxtSelfCompName'], "string"),
					    				'position'=>new xmlrpcval($W7SelfBusClass, "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($_POST['W7TxtSelfAvgIncome'], "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonths'], "int"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    */
					     for($j=1;$j<11;$j++){
								$arrayCreatDataPrvEmp = array();
								
								
								if (($j == 1) && ($_POST['BothPreEmp'] != '')){
									if ($_POST['BothPreEmp'] != 'Previous employer'){
										$arrayCreatDataPrvEmp = array(
													'applicant_id'=>new xmlrpcval($applicant_id, "int"),
													//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
													'business'=>new xmlrpcval($_POST['BothPreEmp'], "string"),
													'position'=>new xmlrpcval($_POST['BothPreJobTitle'], "string"),
													//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
													'annual_income'=>new xmlrpcval($_POST['BothPreAnnuallyPaid'], "string"),
													'month'=>new xmlrpcval($_POST['BothPreNumberofMonths'], "int"),
											); 
										}
									}
								else{
									if ($_POST['BothPreEmp'.$j] != '' ){
										
										if ($_POST['BothPreEmp'.$j] != 'Previous employer'){
											$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['BothPreEmp'.$j], "string"),
												'position'=>new xmlrpcval($_POST['BothPreJobTitle'.$j], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['BothPreAnnuallyPaid'.$j], "string"),
												'month'=>new xmlrpcval($_POST['BothPreNumberofMonths'.$j], "int"),
											); 
										
										}
										
								       }  
									}
								echo "self_income_employer>>>>>>>>>";
								print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									echo "$arrayCreatDataPrvEmp^^^^^^^^^^^^>";
									print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
					    	
						
				
						
				}
	}	
	
	

	
	
	
	



}



  /*$arrayVal = array(
	    'name'=>new xmlrpcval('aaaa', "string") ,
	    'email'=>new xmlrpcval('ashish@domain.com' , "string"),
	    'title'=>new xmlrpcval('3' , "int"),
	    'phone'=>new xmlrpcval('22222' , "string"),
	    'middle_name'=>new xmlrpcval('middle_name',"string"),	
	    );


$ids=array(new xmlrpcval(6,'int')); 
array_push($ids,new xmlrpcval(5,'int')); 
$arrayValread = array(
		new xmlrpcval('id','string'),new xmlrpcval('name','string')
	);	
 
$search_key = array(new xmlrpcval(array(new xmlrpcval('name' , 'string'), // here we pass col name 			
			new xmlrpcval('ilike','string'), // here we pass condition 
			new xmlrpcval('cantor','string')),"array"), // value it may be int or string 
	     	  );

$OEobj = new OpenERP('http://127.0.0.1:8069/xmlrpc/','symldec','admin','syml');
$OEobj->create('res.partner', $arrayVal);
$OEobj->write('res.partner', 5, $arrayVal);
$response = $OEobj->read('res.partner',$ids,$arrayValread);
print_r($response);
exit; 
$field = $response[0]->scalarval();
print_r($field); 
$OEobj->search('res.partner',$search_key);
$OEobj->unlink('res.partner',$ids);

*/

?>
