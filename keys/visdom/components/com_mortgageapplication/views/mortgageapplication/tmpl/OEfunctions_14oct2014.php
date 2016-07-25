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
//			    echo "msg>>>";
	//		    print_r($msg);
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
		echo "inside writeeeeeeeee";
		echo "arrrraaaay for appp reccccccc";
		print_r($arrayValAppRec);
		//echo "object".$obj;
		//echo "id".$id;
		//print_r($arrayVal);
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
				echo "write value:".$res->value()->Scalarval();
				return $res->value()->Scalarval();
		}
	}

        function read($obj, $ides, $arrayVal)
        {	     
		//	echo "read method called.........";
		//	echo "uid>>".$this->userid;
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
				//print_r($searchItems);
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
	{			echo "this********************>>>>>>>>>>>>>>>>>>>>";
				echo "function applicant_detail>>";
				if($str == "own_any_real_estate")
					{
						echo "'W8Txt1Address>>>".$_POST['W8Txt1Address'];
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
						
						
						### Remove Mortgages Data 
						/*$response_mor = $this->search('applicant.mortgage',$key);	
						$id_val_mor = array();
						for($j=0;$j<sizeof($response_mor);$j++){
							$id_val_mor[$j] = new xmlrpcval($response_mor[$j], "int");
						}
						$this->unlink('applicant.mortgage',$id_val_mor);*/
						### End
						
						
						###Remove Incomes
						$args_search=array(
								new xmlrpcval(array(
									new xmlrpcval("applicant_id","string"), 
									new xmlrpcval("=","string"),
									new xmlrpcval($applicant_id,"int")
								),
						"array")); 
						$args_search1=array(
								new xmlrpcval(array(
								new xmlrpcval("income","string"), 
								new xmlrpcval("=","string"),
								new xmlrpcval(4,"int")
							),
						"array")); 
      
						array_merge($args_search1,$args_search);
						$response_add = $this->search('income.employer',$args_search1);
						
						$id_val_add = array();
						for($j=0;$j<sizeof($response_add);$j++){
							$id_val_add[$j] = new xmlrpcval($response_add[$j], "int");
						}
						$this->unlink('income.employer',$id_val_add);
								
						###End
						
							
						if ($_POST['W8Txt1Address']!=''){
							$W8More1RentalPropertySelling = false;
						    $arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    			'name'=>new xmlrpcval($_POST['W8Txt1Address'], "string"),
					    			'value'=>new xmlrpcval($_POST['W8Txt1EstimatedValue'], "double"),
					    			'owed'=>new xmlrpcval($_POST['W8Txt1MortAmount_1'], "int"),
					    			'annual_tax'=>new xmlrpcval(($_POST['W8Txt1PropTaxes'] * 12), "int"),
									'mo_condo_fees'=>new xmlrpcval($_POST['W8Txt1Fees'], "int"),
									'selling'=>new xmlrpcval(false,"boolean"),
								);
							if (!empty($arrayCreatData)){
								echo "obj--------------".$obj;
								
								$pro_id = $this->create($obj,$arrayCreatData);
								echo "pro_id=====".$pro_id;
								
								if ($_POST['W8More1RentalPropertySelling'] == 1){
									$W8More1RentalPropertySelling = true;
									$pro_sell_array = array(
										'selling'=>new xmlrpcval($W8More1RentalPropertySelling,"string"),
									);
									$this->write($obj, $pro_id, $pro_sell_array);
								}
								
								$pro_id_array = array(
									'property_id'=>new xmlrpcval($pro_id,"string"),
								);	
								$this->write($obj, $pro_id, $pro_id_array);
							echo "mortgage amounttttttttttttttttt".$_POST['W8Txt1MortAmount_1'];
							for($j=1;$j<4;$j++){
								$arrayCreatData = array();	
								if($_POST['W8Txt1HoldMortgage_'.$j]!=''){
									if ($j==1){
										$rent=$_POST['W8Txt1MonthlyRent'];
									}
									if ($j==2){
										$rent=$_POST['W8Txt2MonthlyRent'];
									}
									if ($j==3){
										$rent=$_POST['W8Txt3MonthlyRent'];
									}
									
									$arrayCreatData = array(
										'applicant_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval($_POST['W8Txt1HoldMortgage_'.$j], "string"),
										'monthly_payment'=>new xmlrpcval($_POST['W8Txt1Month_'.$j], "string"),
										'interest_rate'=>new xmlrpcval($_POST['W8Txt1IntRate_'.$j], "string"),
										'property_id'=>new xmlrpcval($pro_id, "string"),
										'renewal'=>new xmlrpcval($_POST['W8Txt1RDate_'.$j], "string"),
										'selling'=>new xmlrpcval($W8More1RentalPropertySelling,"string"),
										'balance'=>new xmlrpcval($_POST['W8Txt1MortAmount_'.$j], "int"),
										'monthly_rent'=>new xmlrpcval($rent, "int")										
									);
									if (!empty($arrayCreatData)){
										print_r($arrayCreatData);																				
										$this->create('applicant.mortgage',$arrayCreatData);	
									}
								}			
							}
							
							
							if ($_POST['W8Txt1MonthlyRent'] != ''){
								$arrayRentalIncome = array();
								$arrayRentalIncome = array(
										'applicant_id'=>new xmlrpcval($applicant_id, "int"),
										'income_type'=>new xmlrpcval('9', "string"),
										'business'=>new xmlrpcval($_POST['W8Txt1Address'], "string"),
										'supplementary'=>new xmlrpcval(true, "boolean"),
										'income'=>new xmlrpcval(4, "int"),
										'annual_income'=>new xmlrpcval($_POST['W8Txt1MonthlyRent'] * 12, "string"),
										'property_id'=>new xmlrpcval($pro_id, "string"),
									);
									if (!empty($arrayRentalIncome)){
										$this->create('income.employer',$arrayRentalIncome);	
									}	
							}
								
							}
						}
						
						if ($_POST['W8Txt2Address']!=''){
							$W8More2RentalPropertySelling = false;
							$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    			'name'=>new xmlrpcval($_POST['W8Txt2Address'], "string"),
					    			'value'=>new xmlrpcval($_POST['W8Txt1EstimatedValue1'], "double"),
					    			'owed'=>new xmlrpcval($_POST['W8Txt1MortAmount_4'], "int"),
					    			'annual_tax'=>new xmlrpcval(($_POST['W8Txt2PropTaxes'] * 12), "int"),
									'mo_condo_fees'=>new xmlrpcval($_POST['W8Txt2Fees'], "int"),
								);
							if (!empty($arrayCreatData)){
								$pro_id = $this->create($obj,$arrayCreatData);
								echo "pro_id=====".$pro_id;
								
								if ($_POST['W8More2RentalPropertySelling'] == 1){
									$W8More2RentalPropertySelling = true;
									$pro_sell_array = array(
										'selling'=>new xmlrpcval($W8More2RentalPropertySelling,"string"),
									);
									$this->write($obj, $pro_id, $pro_sell_array);
								}
								
								$pro_id_array = array(
									'property_id'=>new xmlrpcval($pro_id,"string"),
								);	
								$this->write($obj, $pro_id, $pro_id_array);	
							for($j=4;$j<7;$j++){
								$arrayCreatData = array();	
								if($_POST['W8Txt1HoldMortgage_'.$j]!=''){
									$arrayCreatData = array(
										'applicant_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval($_POST['W8Txt1HoldMortgage_'.$j], "string"),
										'monthly_payment'=>new xmlrpcval($_POST['W8Txt1Month_'.$j], "string"),
										'interest_rate'=>new xmlrpcval($_POST['W8Txt1IntRate_'.$j], "string"),
										'property_id'=>new xmlrpcval($pro_id, "string"),
										'renewal'=>new xmlrpcval($_POST['W8Txt1RDate_'.$j], "string"),
										'selling'=>new xmlrpcval($W8More2RentalPropertySelling,"string"),
									);
									if (!empty($arrayCreatData)){
										print_r($arrayCreatData);										
										$this->create('applicant.mortgage',$arrayCreatData);	
									}
								}			
							}
							
							
							if ($_POST['W8Txt2MonthlyRent'] != ''){

								$arrayRentalIncome = array();
								$arrayRentalIncome = array(
										'applicant_id'=>new xmlrpcval($applicant_id, "int"),
										'income_type'=>new xmlrpcval('9', "string"),
										'business'=>new xmlrpcval($_POST['W8Txt2Address'], "string"),
										'supplementary'=>new xmlrpcval(true, "boolean"),
										'address_property'=>new xmlrpcval(1, "int"),
										'annual_income'=>new xmlrpcval($_POST['W8Txt2MonthlyRent'] * 12, "string"),
										
									);
									if (!empty($arrayRentalIncome)){
										$this->create('income.employer',$arrayRentalIncome);	
									}	
							}															
								
								
								
							}
						}
						
						if ($_POST['W8Txt3Address']!=''){
							$W8More3RentalPropertySelling = false;
							$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    			'name'=>new xmlrpcval($_POST['W8Txt3Address'], "string"),
					    			'value'=>new xmlrpcval($_POST['W8Txt1EstimatedValue2'], "double"),
					    			'owed'=>new xmlrpcval($_POST['W8Txt1MortAmount_7'], "int"),
					    			'annual_tax'=>new xmlrpcval(($_POST['W8Txt3PropTaxes'] * 12), "int"),
									'mo_condo_fees'=>new xmlrpcval($_POST['W8Txt3Fees'], "int"),
								);
							if (!empty($arrayCreatData)){
								$pro_id = $this->create($obj,$arrayCreatData);
								echo "pro_id=====".$pro_id;
								if ($_POST['W8More3RentalPropertySelling'] == 1){
									$W8More3RentalPropertySelling = true;
									$pro_sell_array = array(
										'selling'=>new xmlrpcval($W8More3RentalPropertySelling,"string"),
									);
									$this->write($obj, $pro_id, $pro_sell_array);
								}
								
								
								$pro_id_array = array(
									'property_id'=>new xmlrpcval($pro_id,"string"),
								);	
								$this->write($obj, $pro_id, $pro_id_array);	
							for($j=7;$j<10;$j++){
								$arrayCreatData = array();	
								if($_POST['W8Txt1HoldMortgage_'.$j]!=''){
									$arrayCreatData = array(
										'applicant_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval($_POST['W8Txt1HoldMortgage_'.$j], "string"),
										'monthly_payment'=>new xmlrpcval($_POST['W8Txt1Month_'.$j], "string"),
										'interest_rate'=>new xmlrpcval($_POST['W8Txt1IntRate_'.$j], "string"),
										'property_id'=>new xmlrpcval($pro_id, "string"),
										'renewal'=>new xmlrpcval($_POST['W8Txt1RDate_'.$j], "string"),
										'selling'=>new xmlrpcval($W8More3RentalPropertySelling,"string"),
									);
									if (!empty($arrayCreatData)){
										print_r($arrayCreatData);										
										$this->create('applicant.mortgage',$arrayCreatData);	
									}
								}			
							}
							
							if ($_POST['W8Txt3MonthlyRent'] != ''){

								$arrayRentalIncome = array();
								$arrayRentalIncome = array(
										'applicant_id'=>new xmlrpcval($applicant_id, "int"),
										'income_type'=>new xmlrpcval('9', "string"),
										'business'=>new xmlrpcval($_POST['W8Txt3Address'], "string"),
										'supplementary'=>new xmlrpcval(true, "boolean"),
										'address_property'=>new xmlrpcval(1, "int"),
										'annual_income'=>new xmlrpcval($_POST['W8Txt3MonthlyRent'] * 12, "string"),
									);
									if (!empty($arrayRentalIncome)){
										$this->create('income.employer',$arrayRentalIncome);	
									}	
							}															
								
								
								
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
	
		echo "inside applicant other assetttttttttt";				
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
						echo "Vehicle===";
						for($j=1;$j<6;$j++){
							$arrayCreatData = array();	
							echo "tt====".$_POST['W8Txt1Model'];
							echo "mmmm===".$_POST['W8Txt'.$j.'Model'];
							if($_POST['W8Txt'.$j.'Model']!=''){
									echo "j===".$j;
									$arrayCreatData = array(
										'opportunity_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval($_POST['W8Txt'.$j.'Model'], "string"),
										'value'=>new xmlrpcval($_POST['W8Txt'.$j.'Value'], "string"),
										'asset_type'=>new xmlrpcval("Vehicle", "string"),
									);
									"test>>>";
									print_r($arrayCreatData);
									if (!empty($arrayCreatData)){
										echo "model===";
										print_r($arrayCreatData);
										
										$this->create($obj,$arrayCreatData);	
									}
							}			
						}
						
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
									print_r($arrayCreatData);
									//exit;
									if (!empty($arrayCreatData)){
										echo "RRSP===";
										print_r($arrayCreatData);										
										$this->create($obj,$arrayCreatData);	
									}
							}			
						}						

						//non- RRSP,s**********
						echo "non- RRSP,s==".$_POST['W8Txt1NonCompany'];
						echo "res===>non- RRSP,s==".$_REQUEST['W8Txt1NonCompany'];
						for($j=1;$j<6;$j++){
							$arrayCreatData = array();	
							if($_POST['W8Txt'.$j.'NonCompany']!=''){
									$arrayCreatData = array(
										'opportunity_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval($_REQUEST['W8Txt'.$j.'NonCompany'], "string"),
										'value'=>new xmlrpcval($_POST['W8Txt'.$j.'NonValue'], "string"),
										'asset_type'=>new xmlrpcval("Non-RRSPs", "string"),
									);
									
									if (!empty($arrayCreatData)){
										echo "non- RRSP,s===";
										print_r($arrayCreatData);										
										$this->create($obj,$arrayCreatData);	
									}
							}			
						}
								
						// How much have you insured the contents of your properties for? 						
							if(!empty($_POST['W8Txtcontents']))
							{
								$arrayCreatData = array();
								$W8Txtcontents = str_replace(',', '', $_POST['W8Txtcontents']);								
								$arrayCreatData = array(
										'opportunity_id'=>new xmlrpcval($applicant_id, "int"),
										'name'=>new xmlrpcval('insured properties', "string"),
										'value'=>new xmlrpcval($W8Txtcontents, "string"),
										'asset_type'=>new xmlrpcval("other", "string"),
									);
									
									if (!empty($arrayCreatData)){
										$this->create($obj,$arrayCreatData);
									}
							}
							
						
				}
	}




    function applicant_income_employer($obj,$applicant_id,$len,$str,$annual_income)
	{
				echo "income.employer>>";
				
				if($str == "income_employer")
					{
							###Remove Incomes
						$args_search=array(
								new xmlrpcval(array(
									new xmlrpcval("applicant_id","string"), 
									new xmlrpcval("=","string"),
									new xmlrpcval($applicant_id,"int")
								),
						"array")); 
						 
						
						$args_search1=array(
								new xmlrpcval(array(
								new xmlrpcval("income","string"), 
								new xmlrpcval("=","string"),
								new xmlrpcval(1,"int")
							),
						"array")); 
						
						echo "1111111111111111111111111111111111";
						print_r($args_search);
						echo "2222222222222222222222222222222222";
						print_r($args_search1);
						echo "3333333333333333333333333333333333";
						print_r($args_search2);
      
						//array_merge($args_search1,$args_search2,$args_search);
						$merge=array_merge($args_search1,$args_search);
						$response_add = $this->search('income.employer',$merge);
						
						$id_val_add = array();
						for($j=0;$j<sizeof($response_add);$j++){
							$id_val_add[$j] = new xmlrpcval($response_add[$j], "int");
						}
						echo "id======================val---------------------------".$id_val_add;
						$this->unlink('income.employer',$id_val_add);
						
						
						
						$args_search3=array(
								new xmlrpcval(array(
									new xmlrpcval("applicant_id","string"), 
									new xmlrpcval("=","string"),
									new xmlrpcval($applicant_id,"int")
								),
						"array")); 
						
						
						$args_search4=array(
								new xmlrpcval(array(
								new xmlrpcval("income","string"), 
								new xmlrpcval("=","string"),
								new xmlrpcval(7,"int")
							),
						"array"));
						
						
						$merge2=array_merge($args_search4,$args_search3);
						$response_add2 = $this->search('income.employer',$merge2);
						
						$id_val_add2 = array();
						for($j=0;$j<sizeof($response_add2);$j++){
							$id_val_add2[$j] = new xmlrpcval($response_add2[$j], "int");
						}
						echo "id======================val---------------------------".$id_val_add2;
						$this->unlink('income.employer',$id_val_add2);
						
						
						
						
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
						echo "W7TxtPreNumberof>>>".$_POST['W7TxtPreNumberofMonths'];	
						$month = 0;
						if(!empty($_POST['W7TxtPreNumberofMonths']))
						{
							$month = $_POST['W7TxtPreNumberofMonths'];
						}						
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W7IndustryType']];
							echo "industry>>>".$industry;
						}
						echo "annaul ....=".$annual_income;
						$arrayCreatData = array(
										'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
					    				'business'=>new xmlrpcval($_POST['W7TxtCompName'], "string"),
					    				'position'=>new xmlrpcval($_POST['W7TxtJobTitle'], "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
										'annual_income'=>new xmlrpcval($annual_income, "string"),
										'historical'=>new xmlrpcval(false,"boolean"),
										'income_type'=>new xmlrpcval("1","string"),
										'income'=>new xmlrpcval(1,"int"),
										'month'=>new xmlrpcval($month,"string"),

					    	);
							//print_r($arrayCreatData);
						
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    
					     for($j=0;$j<10;$j++){
								$arrayCreatDataPrvEmp = array();
								if (($j == 0) && ($_POST['PreEmp'] != '')){
									$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['PreEmp'], "string"),
												'position'=>new xmlrpcval($_POST['PreJobTitle'], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['PreAnnuallyPaid'], "string"),
												'month'=>new xmlrpcval($_POST['PreNumberofMonths1'], "int"),
												'historical'=>new xmlrpcval(true,"boolean"),
												'income_type'=>new xmlrpcval("1","string"),
												'income'=>new xmlrpcval(1,"int"),
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
												'historical'=>new xmlrpcval(true,"boolean"),
												'income_type'=>new xmlrpcval("1","string"),
												'income'=>new xmlrpcval(1,"int"),
											); 
										
										}
										
								       }  
									}
								echo "income_employer>>>>>>>>>";
								//print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									echo "$arrayCreatDataPrvEmp^^^^^^^^^^^^>";
								//	print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
						  					  
						  
						######write the code  for Supplementary Income
					    $type_Interest = str_replace(',', '', $_POST['type_Interest']);  	
					    $type_Pension = str_replace(',', '', $_POST['type_Pension']);  	
					    $type_Rental = str_replace(',', '', $_POST['type_Rental']);  	
					    $type_ChildTaxCredit = str_replace(',', '', $_POST['type_ChildTaxCredit']);  	
					    $type_LivingAllowance = str_replace(',', '', $_POST['type_LivingAllowance']);  	
					    $type_CarAllowance = str_replace(',', '', $_POST['type_CarAllowance']);  	
					    $type_Investment = str_replace(',', '', $_POST['type_Investment']);  	
					    $type_other = str_replace(',', '', $_POST['type_other']);
					    
					    echo "type_Interest===".$type_Interest;
					    echo "type_Pension===".$type_Pension;
					    echo "type_Rental===".$type_Rental;
					    echo "type_ChildTaxCredit===".$type_ChildTaxCredit;
					    echo "type_LivingAllowance===".$type_LivingAllowance;
					    echo "type_CarAllowance===".$type_CarAllowance;
					    echo "type_Investment===".$type_Investment;
					    echo "type_other===".$type_other;
					    
					    
					    if ($type_Interest != '' ){
							$this->supplementary_income($obj,$applicant_id,'3',$type_Interest);				 
						}
						if ($type_Pension != '' ){
							$this->supplementary_income($obj,$applicant_id,'6',$type_Pension);				 
						}
						if ($type_Rental != '' ){
							$this->supplementary_income($obj,$applicant_id,'9',$type_Rental);				 
						}
						if ($type_ChildTaxCredit != '' ){
							$this->supplementary_income($obj,$applicant_id,'10',$type_ChildTaxCredit);				 
						}
						if ($type_LivingAllowance != '' ){
							$this->supplementary_income($obj,$applicant_id,'11',$type_LivingAllowance);				 
						}
						if ($type_CarAllowance != '' ){
							$this->supplementary_income($obj,$applicant_id,'12',$type_CarAllowance);				 
						}
						if ($type_Investment != '' ){
							$this->supplementary_income($obj,$applicant_id,'5',$type_Investment);				 
						}
						if ($type_other != '' ){
							$this->supplementary_income($obj,$applicant_id,'13',$type_other);				 
						}
						
				}
	}
	
	
	
	
	function supplementary_income($obj,$applicant_id,$income_type,$income_val){
						
		
		$supplementary_arr = array();
		$supplementary_arr = array(
							'applicant_id'=>new xmlrpcval($applicant_id, "int"),
							'income_type'=>new xmlrpcval($income_type,"string"),
							'annual_income'=>new xmlrpcval($income_val, "string"),
							'supplementary'=>new xmlrpcval(true,"boolean"),
							'income'=>new xmlrpcval("7","int"),
							);
		$this->create($obj,$supplementary_arr);		
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
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("4","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
							$this->unlink($obj,$id_val);
							
							//UNLINKING SUPPLEMENTARY INCOMES
							
							$key3 = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key4 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("7","int")),"array"), // value it may be int or string 
									);		
							
							$merge2=array_merge($key3,$key4);
							
							$response2 = $this->search($obj,$merge2);	
							$id_val2 = array();
							for($j=0;$j<sizeof($response2);$j++){
								$id_val2[$j] = new xmlrpcval($response2[$j], "int");
							}
							echo "id";
							print_r($id_val2);
							echo "id";
							$this->unlink($obj,$id_val2);
							
													
						
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
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsself'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("2","string"),
									'income'=>new xmlrpcval("4","string"),

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
												'historical'=>new xmlrpcval(true,"boolean"),
												'income_type'=>new xmlrpcval("1","string"),
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
												'historical'=>new xmlrpcval(true,"boolean"),
												'income_type'=>new xmlrpcval("1","string"),
											); 
										
										}
										
								       }  
									}
								echo "self_income_employer>>>>>>>>>";
							//	print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									echo "$arrayCreatDataPrvEmp^^^^^^^^^^^^>";
							//		print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
						  
						######write the code  for Supplementary Income
					    $type_Interest = str_replace(',', '', $_POST['type_InterestSelf']);  	
					    $type_Pension = str_replace(',', '', $_POST['type_PensionSelf']);  	
					    $type_Rental = str_replace(',', '', $_POST['type_RentalSelf']);  	
					    $type_ChildTaxCredit = str_replace(',', '', $_POST['type_ChildTaxCreditSelf']);  	
					    $type_LivingAllowance = str_replace(',', '', $_POST['type_LivingAllowanceSelf']);  	
					    $type_CarAllowance = str_replace(',', '', $_POST['type_CarAllowanceSelf']);  	
					    $type_Investment = str_replace(',', '', $_POST['type_InvestmentSelf']);  	
					    $type_other = str_replace(',', '', $_POST['type_otherSelf']);
					    
					    
					    if ($type_Interest != '' ){
							$this->supplementary_income($obj,$applicant_id,'3',$type_Interest);				 
						}
						if ($type_Pension != '' ){
							$this->supplementary_income($obj,$applicant_id,'6',$type_Pension);				 
						}
						if ($type_Rental != '' ){
							$this->supplementary_income($obj,$applicant_id,'9',$type_Rental);				 
						}
						if ($type_ChildTaxCredit != '' ){
							$this->supplementary_income($obj,$applicant_id,'10',$type_ChildTaxCredit);				 
						}
						if ($type_LivingAllowance != '' ){
							$this->supplementary_income($obj,$applicant_id,'11',$type_LivingAllowance);				 
						}
						if ($type_CarAllowance != '' ){
							$this->supplementary_income($obj,$applicant_id,'12',$type_CarAllowance);				 
						}
						if ($type_Investment != '' ){
							$this->supplementary_income($obj,$applicant_id,'5',$type_Investment);				 
						}
						if ($type_other != '' ){
							$this->supplementary_income($obj,$applicant_id,'13',$type_other);				 
						}						  
						
				}
	}
	
	//444444444444444444444444444444
function self_applicant_income2_employer($obj,$applicant_id,$len,$str)
	{
				echo "income.employer22222222222222222222222222222222222222222222222222>>";
				if($str == "self_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("5","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W72SelfIndustryType'], $self_array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W72SelfIndustryType']];
							echo "industry>>>".$industry;
						}
						
						$W72SelfBusClass = '';
						if ($_POST['W72SelfBusClass'] == 1){
							$W72SelfBusClass = "Corporation";
						}
						if ($_POST['W72SelfBusClass'] == 2){
							$W72SelfBusClass = "Sole Proprietorship";
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W72SelfIndustryType'], "string"),
					    				'business'=>new xmlrpcval($_POST['W72TxtSelfCompName'], "string"),
					    				'position'=>new xmlrpcval($W72SelfBusClass, "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($_POST['W72TxtSelfAvgIncome'], "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsselfr1'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("2","string"),
									'income'=>new xmlrpcval("5","string"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    
					     
						  
						
						
				}
	}
	
	
	
	function self_applicant_income3_employer($obj,$applicant_id,$len,$str)
	{
				echo "income.employer333333333333333333333333333333333333333>>";
				if($str == "self_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("6","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W73SelfIndustryType'], $self_array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W73SelfIndustryType']];
							echo "industry>>>".$industry;
						}
						
						$W73SelfBusClass = '';
						if ($_POST['W73SelfBusClass'] == 1){
							$W72SelfBusClass = "Corporation";
						}
						if ($_POST['W73SelfBusClass'] == 2){
							$W73SelfBusClass = "Sole Proprietorship";
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W73SelfIndustryType'], "string"),
					    				'business'=>new xmlrpcval($_POST['W73TxtSelfCompName'], "string"),
					    				'position'=>new xmlrpcval($W72SelfBusClass, "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($_POST['W73TxtSelfAvgIncome'], "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsr33'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("2","string"),
									'income'=>new xmlrpcval("6","string"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    
					     
						  
						
						
				}
	}
	
	
	
	
	function co_self_applicant_income2_employer($obj,$applicant_id,$len,$str)
	{
				echo "co_self_income.employer222222222222222222222222222>>";
				if($str == "co_self_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("5","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W72SelfIndustryTypeSecApp'], $self_array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W72SelfIndustryTypeSecApp']];
							echo "industry>>>".$industry;
						}
						
						$W7SelfBusClass = '';
						if ($_POST['W72SelfBusClassSecApp'] == 1){
							$W7SelfBusClass = "Corporation";
						}
						if ($_POST['W72SelfBusClassSecApp'] == 2){
							$W7SelfBusClass = "Sole Proprietorship";
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W72SelfIndustryTypeSecApp'], "string"),
					    				'business'=>new xmlrpcval($_POST['W72TxtSelfCompNameSecApp'], "string"),
					    				'position'=>new xmlrpcval($W7SelfBusClass, "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($_POST['W72TxtSelfAvgIncomeSecApp'], "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsSecAppr12'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("2","string"),
									'income'=>new xmlrpcval("5","string"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
								
						
				}
	}
	
	
	
	
	function co_self_applicant_income3_employer($obj,$applicant_id,$len,$str)
	{
				echo "co_self_income.employer33333333333333333333333333333333333333>>";
				if($str == "co_self_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("6","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W73SelfIndustryTypeSecApp'], $self_array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W73SelfIndustryTypeSecApp']];
							echo "industry>>>".$industry;
						}
						
						$W7SelfBusClass = '';
						if ($_POST['W73SelfBusClassSecApp'] == 1){
							$W7SelfBusClass = "Corporation";
						}
						if ($_POST['W73SelfBusClassSecApp'] == 2){
							$W7SelfBusClass = "Sole Proprietorship";
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W73SelfIndustryTypeSecApp'], "string"),
					    				'business'=>new xmlrpcval($_POST['W73TxtSelfCompNameSecApp'], "string"),
					    				'position'=>new xmlrpcval($W7SelfBusClass, "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($_POST['W73TxtSelfAvgIncomeSecApp'], "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsSecAppr13'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("2","string"),
									'income'=>new xmlrpcval("2","string"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
								
						
				}
	}
	
	//123444444444444444444444444444444444444444




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
													'historical'=>new xmlrpcval(true,"boolean"),
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
												'historical'=>new xmlrpcval(true,"boolean"),
											); 
										
										}
										
								       }  
									}
								echo "self_income_employer>>>>>>>>>";
								print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									echo "$arrayCreatDataPrvEmp^^^^^^^^^^^^>";
								//	print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
					    	
						
				
						
				}
	}	
	


    function co_applicant_income_employer($obj,$applicant_id,$len,$str,$annual_income)
	{
				if($str == "co_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("1","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W7IndustryTypeSecApp'], $array_industry);
						$industry = '';
						echo "co_app_industry_type>>>".$industry_type;
						echo "W7TxtPreNumberofMonths>>>".$_POST['W7TxtPreNumberofMonths'];
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W7IndustryTypeSecApp']];
							echo "industry>>>".$industry;
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W7IndustryTypeSecApp'], "string"),
					    				'business'=>new xmlrpcval($_POST['W7TxtCompNameSecApp'], "string"),
					    				'position'=>new xmlrpcval($_POST['W7TxtJobTitleSecApp'], "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($annual_income, "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsSecApp1'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("1","string"),
									'income'=>new xmlrpcval("1","string"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    
					     for($j=0;$j<10;$j++){
								$arrayCreatDataPrvEmp = array();
								if (($j == 0) && ($_POST['W7SecAppTxtPreEmp'] != '')){
									if ($_POST['W7SecAppTxtPreEmp'] != 'Previous employer'){
											$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['W7SecAppTxtPreEmp'], "string"),
												'position'=>new xmlrpcval($_POST['W7SecAppTxtPreJobTitle'], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['W7SecAppTxtPreAnnuallyPaid'], "string"),
												'month'=>new xmlrpcval($_POST['W7SecAppTxtPreNumberofMonths'], "int"),
												'historical'=>new xmlrpcval(true,"boolean"),
												'income_type'=>new xmlrpcval("1","string"),
										); 
									}
								}
								else{
									if ($_POST['W7SecAppTxtPreEmp'.$j] != '' ){
										
										if ($_POST['W7SecAppTxtPreEmp'.$j] != 'Previous employer'){
											$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['W7SecAppTxtPreEmp'.$j], "string"),
												'position'=>new xmlrpcval($_POST['W7SecAppTxtPreJobTitle'.$j], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['W7SecAppTxtPreAnnuallyPaid'.$j], "string"),
												'month'=>new xmlrpcval($_POST['W7SecAppTxtPreNumberofMonths'.$j], "int"),
												'historical'=>new xmlrpcval(true,"boolean"),
												'income_type'=>new xmlrpcval("1","string"),
											); 
										
										}
										
								       }  
									}
								echo "co_income_employer>>>>>>>>>";
								print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									echo "$arrayCreatDataPrvEmp^^^^^^^^^^^^>";
									print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
						
				}
	}
	//12333333333333333333333333333333
	
	function co_applicant_income2_employer($obj,$applicant_id,$len,$str,$annual_income)
	{
				echo"co_appincome22222222222222222222222222";
				if($str == "co_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("2","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W72IndustryTypeSecApp'], $array_industry);
						$industry = '';
						echo "co_app_industry_type>>>".$industry_type;
						echo "W7TxtPreNumberofMonths>>>".$_POST['W72TxtPreNumberofMonths'];
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W72IndustryTypeSecApp']];
							echo "industry>>>".$industry;
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W72IndustryTypeSecApp'], "string"),
					    				'business'=>new xmlrpcval($_POST['W72TxtCompNameSecApp'], "string"),
					    				'position'=>new xmlrpcval($_POST['W72TxtJobTitleSecApp'], "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($annual_income, "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsSecAppr2'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("1","string"),
									'income'=>new xmlrpcval("2","string"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    
			
								
				}
						
	}


function co_applicant_income3_employer($obj,$applicant_id,$len,$str,$annual_income)
	{
				if($str == "co_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("3","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W73IndustryTypeSecApp'], $array_industry);
						$industry = '';
						echo "co_app_industry_type>>>".$industry_type;
						echo "W7TxtPreNumberofMonths>>>".$_POST['W73TxtPreNumberofMonths'];
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W73IndustryTypeSecApp']];
							echo "industry>>>".$industry;
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W73IndustryTypeSecApp'], "string"),
					    				'business'=>new xmlrpcval($_POST['W73TxtCompNameSecApp'], "string"),
					    				'position'=>new xmlrpcval($_POST['W73TxtJobTitleSecApp'], "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($annual_income, "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsSecAppr3'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("1","string"),
									'income'=>new xmlrpcval("3","string"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    

								
				}
						
	}




	//1233333333333333333333333
	
	
    function co_self_applicant_income_employer($obj,$applicant_id,$len,$str)
	{
				echo "co_self_income.employer11111111111111111111111111>>";
				if($str == "co_self_income_employer")
					{
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("4","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W7SelfIndustryTypeSecApp'], $self_array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W7SelfIndustryTypeSecApp']];
							echo "industry>>>".$industry;
						}
						
						$W7SelfBusClass = '';
						if ($_POST['W7SelfBusClassSecApp'] == 1){
							$W7SelfBusClass = "Corporation";
						}
						if ($_POST['W7SelfBusClassSecApp'] == 2){
							$W7SelfBusClass = "Sole Proprietorship";
						}
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W7SelfIndustryTypeSecApp'], "string"),
					    				'business'=>new xmlrpcval($_POST['W7TxtSelfCompNameSecApp'], "string"),
					    				'position'=>new xmlrpcval($W7SelfBusClass, "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
									'annual_income'=>new xmlrpcval($_POST['W7TxtSelfAvgIncomeSecApp'], "string"),
									'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsSelfSecAppr1'], "int"),
									'historical'=>new xmlrpcval(false,"boolean"),
									'income_type'=>new xmlrpcval("2","string"),
									'income'=>new xmlrpcval("4","string"),


					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					    
					     for($j=1;$j<11;$j++){
								$arrayCreatDataPrvEmp = array();
								
								
								if (($j == 1) && ($_POST['W7SecAppSelfTxtPreEmp'] != '')){
									$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['W7SecAppSelfTxtPreEmp'], "string"),
												'position'=>new xmlrpcval($_POST['W7SecAppSelfTxtPreJobTitle'], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['W7SecAppSelfTxtPreAnnuallyPaid'], "string"),
												'month'=>new xmlrpcval($_POST['W7SecAppSelfTxtPreNumberofMonths'], "int"),
												'historical'=>new xmlrpcval(true,"boolean"),
												'income_type'=>new xmlrpcval("1","string"),
									   ); 
									}
								else{
									if ($_POST['W7SecAppSelfTxtPreEmp'.$j] != '' ){
										
										if ($_POST['W7SecAppSelfTxtPreEmp'.$j] != 'Previous employer'){
											$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['W7SecAppSelfTxtPreEmp'.$j], "string"),
												'position'=>new xmlrpcval($_POST['W7SecAppSelfTxtPreJobTitle'.$j], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['W7SecAppSelfTxtPreAnnuallyPaid'.$j], "string"),
												'month'=>new xmlrpcval($_POST['W7SecAppSelfTxtPreNumberofMonths'.$j], "int"),
												'historical'=>new xmlrpcval(true,"boolean"),
												'income_type'=>new xmlrpcval("1","string"),
											); 
										
										}
										
								       }  
									}
								
								print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									echo "$arrayCreatDataPrvEmp^^^^^^^^^^^^>";
									print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
					    	
						
				
						
				}
	}
	
	
	
    function co_both_applicant_income_employer($obj,$applicant_id,$len,$str)
	{
				if($str == "co_both_income_employer")
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
								
								if (($j == 1) && ($_POST['W7BothSecAppTxtPreEmp'] != '')){
									if ($_POST['W7BothSecAppTxtPreEmp'] != 'Previous employer'){
										$arrayCreatDataPrvEmp = array(
													'applicant_id'=>new xmlrpcval($applicant_id, "int"),
													//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
													'business'=>new xmlrpcval($_POST['W7BothSecAppTxtPreEmp'], "string"),
													'position'=>new xmlrpcval($_POST['W7BothSecAppTxtPreJobTitle'], "string"),
													//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
													'annual_income'=>new xmlrpcval($_POST['W7BothSecAppTxtPreAnnuallyPaid'], "string"),
													'month'=>new xmlrpcval($_POST['W7BothSecAppTxtPreNumberofMonths'], "int"),
													'historical'=>new xmlrpcval(true,"boolean"),
											); 
										}
									}
								else{
									if ($_POST['W7BothSecAppTxtPreEmp'.$j] != '' ){
										
										if ($_POST['W7BothSecAppTxtPreEmp'.$j] != 'Previous employer'){
											$arrayCreatDataPrvEmp = array(
												'applicant_id'=>new xmlrpcval($applicant_id, "int"),
												//'name'=>new xmlrpcval($_POST['W7IndustryType'], "string"),
												'business'=>new xmlrpcval($_POST['W7BothSecAppTxtPreEmp'.$j], "string"),
												'position'=>new xmlrpcval($_POST['W7BothSecAppTxtPreJobTitle'.$j], "string"),
												//'industry'=>new xmlrpcval($_POST['W7TxtIndustryTypeDesc'], "string"),
												'annual_income'=>new xmlrpcval($_POST['W7BothSecAppTxtPreAnnuallyPaid'.$j], "string"),
												'month'=>new xmlrpcval($_POST['W7BothSecAppTxtPreNumberofMonths'.$j], "int"),
												'historical'=>new xmlrpcval(true,"boolean"),
											); 
										
										}
										
								       }  
									}
								print_r($arrayCreatDataPrvEmp);	
								if (!empty($arrayCreatDataPrvEmp)){
									print_r($arrayCreatDataPrvEmp);
									$this->create($obj,$arrayCreatDataPrvEmp);	
								}	
								
						  }
					    	
						
				
						
				}
	}			

	

    function applicant_address($obj,$search_id,$len){
	    ###### Applicant Address...........#####
	    $key_applicant_add = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			                
				new xmlrpcval('=',"string"), // here we pass condition 
				new xmlrpcval($search_id,"int")),"array"), // value it may be int or string 
				);
		$response = $this->search($obj,$key_applicant_add);	
		$id_val = array();
		for($j=0;$j<sizeof($response);$j++){
			$id_val[$j] = new xmlrpcval($response[$j], "int");
		}
		$this->unlink($obj,$id_val);			
				
	    $arrayApplicantAddRec = array();
	    $arrayApplicantAddRec2 = array();
	    $arrayApplicantAddRec3 = array();
	    $arrayApplicantAddRec4 = array();
	    $arrayApplicantAddRec5 = array();
	    
	    
	    if ($len == 1){
				$arrayApplicantAddRec = array(
					'applicant_id'=>new xmlrpcval($search_id, "int"),	
					'name'=>new xmlrpcval($_POST['W2TxtStreet'], "string"),	
					'city'=>new xmlrpcval($_POST['W2TxtCity'], "string"),
					'province'=>new xmlrpcval($_POST['W2DrpProvince'], "string"),		
					'postal_code'=>new xmlrpcval($_POST['W2TxtCode'], "string"),		
					'date'=>new xmlrpcval($_POST['W2TxtMove'], "string"),					
					
				);
				
				echo "address11111111===".$_POST['W2TxtMove'];
				echo "search====".$search_id;
				print_r($arrayApplicantAddRec);
				
				$app_add_rec_id = $this->create($obj, $arrayApplicantAddRec);
				
				if ($_POST['W2TxtCityN3Y'] != ''){
					$arrayApplicantAddRec2 = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),	
						'name'=>new xmlrpcval($_POST['W2TxtStreetN3Y'], "string"),	
						'city'=>new xmlrpcval($_POST['W2TxtCityN3Y'], "string"),
						'province'=>new xmlrpcval($_POST['W2DrpProvinceN3Y'], "string"),		
						'postal_code'=>new xmlrpcval($_POST['W2TxtCodeN3Y'], "string"),
						//'date'=>new xmlrpcval($_POST['W2TxtMoveN3Y'], "string"),	
					
					);
					$app_add_rec_id2 = $this->create($obj, $arrayApplicantAddRec2);
				}
				
				if ($_POST['W2TxtCityN3Y1'] != ''){
					$arrayApplicantAddRec3 = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),	
						'name'=>new xmlrpcval($_POST['W2TxtStreetN3Y1'], "string"),	
						'city'=>new xmlrpcval($_POST['W2TxtCityN3Y1'], "string"),
						'province'=>new xmlrpcval($_POST['W2DrpProvinceN3Y1'], "string"),		
						'postal_code'=>new xmlrpcval($_POST['W2TxtCodeN3Y1'], "string"),
						//'date'=>new xmlrpcval($_POST['W2TxtMoveN3Y2'], "string"),	
					
					);
					$app_add_rec_id3 = $this->create($obj, $arrayApplicantAddRec3);
				}
				
				if ($_POST['W2TxtCityN3Y2'] != ''){
					$arrayApplicantAddRec4 = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),	
						'name'=>new xmlrpcval($_POST['W2TxtStreetN3Y2'], "string"),	
						'city'=>new xmlrpcval($_POST['W2TxtCityN3Y2'], "string"),
						'province'=>new xmlrpcval($_POST['W2DrpProvinceN3Y2'], "string"),		
						'postal_code'=>new xmlrpcval($_POST['W2TxtCodeN3Y2'], "string"),
						//'date'=>new xmlrpcval($_POST['W2TxtMoveN3Y3'], "string"),	
					
					);
					$app_add_rec_id4 = $this->create($obj, $arrayApplicantAddRec4);
				}
				
				if ($_POST['W2TxtCityN3Y3'] != ''){
					$arrayApplicantAddRec5 = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),	
						'name'=>new xmlrpcval($_POST['W2TxtStreetN3Y3'], "string"),	
						'city'=>new xmlrpcval($_POST['W2TxtCityN3Y3'], "string"),
						'province'=>new xmlrpcval($_POST['W2DrpProvinceN3Y3'], "string"),		
						'postal_code'=>new xmlrpcval($_POST['W2TxtCodeN3Y3'], "string"),
						//'date'=>new xmlrpcval($_POST['W2TxtMoveN3Y3'], "string"),	
					
					);
					$app_add_rec_id5 = $this->create($obj, $arrayApplicantAddRec5);
				}							
			}
		if ($len == 2){
				$arrayApplicantAddRec = array(
					'applicant_id'=>new xmlrpcval($search_id, "int"),	
					'name'=>new xmlrpcval($_POST['W2TxtStreetSec'], "string"),	
					'city'=>new xmlrpcval($_POST['W2TxtCitySec'], "string"),
					'province'=>new xmlrpcval($_POST['W2DrpProvinceSec'], "string"),		
					'postal_code'=>new xmlrpcval($_POST['W2TxtCodeSec'], "string"),
					'date'=>new xmlrpcval($_POST['W2TxtMoveSecapp'], "string"),			
				);
				$app_add_rec_id = $this->create($obj, $arrayApplicantAddRec);	
				
				if ($_POST['W2TxtCityN3YSecapp'] != ''){
					$arrayApplicantAddRec2 = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),	
						'name'=>new xmlrpcval($_POST['W2TxtStreetN3YSecapp'], "string"),	
						'city'=>new xmlrpcval($_POST['W2TxtCityN3YSecapp'], "string"),
						'province'=>new xmlrpcval($_POST['W2DrpProvinceN3YSecapp'], "string"),		
						'postal_code'=>new xmlrpcval($_POST['W2TxtCodeN3YSecapp'], "string"),
						//'date'=>new xmlrpcval($_POST['W2TxtMoveN3YSecapp'], "string"),	
					);
					$app_add_rec_id2 = $this->create($obj, $arrayApplicantAddRec2);
				}
				
				if ($_POST['W2TxtCityN3Y1Secapp'] != ''){
					$arrayApplicantAddRec3 = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),	
						'name'=>new xmlrpcval($_POST['W2TxtStreetN3Y1Secapp'], "string"),	
						'city'=>new xmlrpcval($_POST['W2TxtCityN3Y1Secapp'], "string"),
						'province'=>new xmlrpcval($_POST['W2DrpProvinceN3Y1Secapp'], "string"),		
						'postal_code'=>new xmlrpcval($_POST['W2TxtCodeN3Y1Secapp'], "string"),
						//'date'=>new xmlrpcval($_POST['W2TxtMoveN3Y2Secapp'], "string"),	
					
					);
					$app_add_rec_id3 = $this->create($obj, $arrayApplicantAddRec3);
				}
				
				if ($_POST['W2TxtCityN3Y2Secapp'] != ''){
					$arrayApplicantAddRec4 = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),	
						'name'=>new xmlrpcval($_POST['W2TxtStreetN3Y2Secapp'], "string"),	
						'city'=>new xmlrpcval($_POST['W2TxtCityN3Y2Secapp'], "string"),
						'province'=>new xmlrpcval($_POST['W2DrpProvinceN3Y2Secapp'], "string"),		
						'postal_code'=>new xmlrpcval($_POST['W2TxtCodeN3Y2Secapp'], "string"),
						//'date'=>new xmlrpcval($_POST['W2TxtMoveN3Y3Secapp'], "string"),	
					
					);
					$app_add_rec_id4 = $this->create($obj, $arrayApplicantAddRec4);
				}	
				
				if ($_POST['W2TxtCityN3Y2Secapp'] != ''){
					$arrayApplicantAddRec5 = array(
						'applicant_id'=>new xmlrpcval($search_id, "int"),	
						'name'=>new xmlrpcval($_POST['W2TxtStreetN3Y3Secapp'], "string"),	
						'city'=>new xmlrpcval($_POST['W2TxtCityN3Y3Secapp'], "string"),
						'province'=>new xmlrpcval($_POST['W2DrpProvinceN3Y3Secapp'], "string"),		
						'postal_code'=>new xmlrpcval($_POST['W2TxtCodeN3Y3Secapp'], "string"),
						//'date'=>new xmlrpcval($_POST['W2TxtMoveN3Y3Secapp'], "string"),	
					
					);
					$app_add_rec_id5 = $this->create($obj, $arrayApplicantAddRec5);
				}							
		}		

	}


	
    function new_applicant_record($crmid,$W2DrpRelationship,$len){
		echo "ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg".$_POST['W2textcanada'];
		$arrayApplicantRec = array();
		if ($len==1){
			$W2TxtCell = preg_replace('- ( )', '', $_POST['W2TxtCell']);
			echo "W2TxtCell====".$W2TxtCell;
			echo "end";			 
			$arrayApplicantRec = array(
				'applicant_name'=>new xmlrpcval($_POST['W2TxtFirstNsme'], "string"),
				//'middle_name'=>new xmlrpcval($_POST['W2TxtMiddleName'], "string"),
				'applicant_last_name'=>new xmlrpcval($_POST['W2TxtLastName'], "string"),
				'email_personal'=>new xmlrpcval($_POST['W2TxtEmail'], "string"),
				//'email_work'=>new xmlrpcval($_POST['W2TxtEmail'], "string"),
				'cell'=>new xmlrpcval($_POST['W2TxtCell'], "string"), 
				'home'=>new xmlrpcval($_POST['W2TxtHome'], "string"),
				'work'=>new xmlrpcval($_POST['W2TxtWork'], "string"),
				'best_number'=>new xmlrpcval($_POST['W2DrpBestSec'], "string"),
			
				'dob'=>new xmlrpcval($_POST['W2TxtDOB'], "string"),
				'sin'=>new xmlrpcval($_POST['W2TxtSIN'], "string"),
				'relationship_status'=>new xmlrpcval($W2DrpRelationship, "string"),
				'opp_id'=>new xmlrpcval($crmid, "int"),
						
			);
			
			if($_POST['W2textcanada'])
			{
				$arrayApplicantRec['immigration_date'] = new xmlrpcval($_POST['W2textcanada'], "string");
			}
		    $key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
						new xmlrpcval('=',"string"), // here we pass condition 
						new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);			
		}
		
		
		if ($len==2){
			$arrayApplicantRec = array(
				'applicant_name'=>new xmlrpcval($_POST['W2TxtFirstNsmeSec'], "string"),
				//'middle_name'=>new xmlrpcval($_POST['W2TxtMiddleNameSec'], "string"),
				'applicant_last_name'=>new xmlrpcval($_POST['W2TxtLastNameSec'], "string"),
				'email_personal'=>new xmlrpcval($_POST['W2TxtEmailSec'], "string"),
				//'email_work'=>new xmlrpcval($_POST['W2TxtEmailSec'], "string"),
				'cell'=>new xmlrpcval($_POST['W2TxtCellSec'], "string"), 
				'home'=>new xmlrpcval($_POST['W2TxtHomeSec'], "string"),
				'work'=>new xmlrpcval($_POST['W2TxtWorkSec'], "string"),
				'best_number'=>new xmlrpcval($_POST['W2DrpBestSec'], "string"),
				
				'dob'=>new xmlrpcval($_POST['W2TxtDOBSec'], "string"),
				'sin'=>new xmlrpcval($_POST['W2TxtSINSec'], "string"),
				'relationship_status'=>new xmlrpcval($W2DrpRelationship, "string"),
				'opp_id'=>new xmlrpcval($crmid, "int"),				
				//2nd Applicant: (All Fields....)		
		    );
			
			if($_POST['W2textcanadaSec'])
			{
				$arrayApplicantRec['immigration_date'] = new xmlrpcval($_POST['W2textcanadaSec'], "string");
			}
		    $key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
						new xmlrpcval('=',"string"), // here we pass condition 
						new xmlrpcval($_POST['W2TxtSINSec'],"string")),"array"), // value it may be int or string 
				);			
		}
		
		$search_id = '';
	    print_r($arrayApplicantRec); 	
	    $arrayOppApplicantRel= array();
		    if (!empty($arrayApplicantRec)){
				$response_app_id = $this->search('applicant.record',$key_applicant);
				$search_id = $response_app_id[0];		
				echo "search_id>>>>>".$search_id;
				if($search_id){
					$app_rec_id = $search_id;
					$this->write('applicant.record',$search_id, $arrayApplicantRec);
				}
				else{
					print_r($arrayApplicantRec);
					$app_rec_id = $this->create('applicant.record', $arrayApplicantRec);
				}
				
			}
		## applicant address
		$this->applicant_address('applicant.address',$app_rec_id,$len);	    	
	
	}




    function applicant_record($crmid,$W2DrpRelationship,$len){
		$arrayApplicantRec = array();
		if ($len==1){
		$arrayApplicantRec = array(
		    'applicant_name'=>new xmlrpcval($_POST['W2TxtFirstNsme'], "string"),
		    //'middle_name'=>new xmlrpcval($_POST['W2TxtMiddleName'], "string"),
		    'applicant_last_name'=>new xmlrpcval($_POST['W2TxtLastName'], "string"),
		    'email_personal'=>new xmlrpcval($_POST['W2TxtEmail'], "string"),
		    'email_work'=>new xmlrpcval($_POST['W2TxtEmail'], "string"),
		    'cell'=>new xmlrpcval($_POST['W2TxtCell'], "string"), 
		    'home'=>new xmlrpcval($_POST['W2TxtHome'], "string"),
		    'work'=>new xmlrpcval($_POST['W2TxtWork'], "string"),
		    'best_number'=>new xmlrpcval($_POST['W2DrpBestSec'], "string"),
		    //Best
		    //'zip'=>new xmlrpcval($_POST['W2TxtCode'], "string"),
		    //'street'=>new xmlrpcval($_POST['W2TxtStreet'], "string"),	
		    //'street2'=>new xmlrpcval($_POST['W2TxtCity'], "string"),
		    //Province
		    'dob'=>new xmlrpcval($_POST['W2TxtDOB'], "string"),
		    'sin'=>new xmlrpcval($_POST['W2TxtSIN'], "string"),
		    'relationship_status'=>new xmlrpcval($W2DrpRelationship, "string"),
		    'applicant_id'=>new xmlrpcval($crmid, "string"),
		    //'signature_ip'=>new xmlrpcval($_SERVER['REMOTE_ADDR'], "string"),
		    //'consent_dateTime'=>new xmlrpcval(date('Y-m-d'), "string"),			
		    //'lander_id'=>new xmlrpcval($_POST['txtcrmid'],"int"),	
		    //Relationship Status
		    //2nd Applicant: (All Fields....)		
		    );
		    $key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
						new xmlrpcval('=',"string"), // here we pass condition 
						new xmlrpcval($_POST['W2TxtSIN'],"string")),"array"), // value it may be int or string 
				);			
		}
		
		
		if ($len==2){
			$arrayApplicantRec = array(
				'applicant_name'=>new xmlrpcval($_POST['W2TxtFirstNsmeSec'], "string"),
				//'middle_name'=>new xmlrpcval($_POST['W2TxtMiddleNameSec'], "string"),
				'applicant_last_name'=>new xmlrpcval($_POST['W2TxtLastNameSec'], "string"),
				'email_personal'=>new xmlrpcval($_POST['W2TxtEmailSec'], "string"),
				'email_work'=>new xmlrpcval($_POST['W2TxtEmailSec'], "string"),
				'cell'=>new xmlrpcval($_POST['W2TxtCellSec'], "string"), 
				'home'=>new xmlrpcval($_POST['W2TxtHomeSec'], "string"),
				'work'=>new xmlrpcval($_POST['W2TxtWorkSec'], "string"),
				'best_number'=>new xmlrpcval($_POST['W2DrpBestSec'], "string"),
				//Best
				//'zip'=>new xmlrpcval($_POST['W2TxtCode'], "string"),
				//'street'=>new xmlrpcval($_POST['W2TxtStreet'], "string"),	
				//'street2'=>new xmlrpcval($_POST['W2TxtCity'], "string"),
				//Province
				'dob'=>new xmlrpcval($_POST['W2TxtDOBSec'], "string"),
				'sin'=>new xmlrpcval($_POST['W2TxtSINSec'], "string"),
				'relationship_status'=>new xmlrpcval($W2DrpRelationship, "string"),
				'applicant_id'=>new xmlrpcval($crmid, "string"),
				//'signature_ip'=>new xmlrpcval($_SERVER['REMOTE_ADDR'], "string"),
				//'consent_dateTime'=>new xmlrpcval(date('Y-m-d'), "string"),		
				//'lander_id'=>new xmlrpcval($_POST['txtcrmid'],"int"),	
				//Relationship Status
				//2nd Applicant: (All Fields....)		
		    );
		    $key_applicant = array(new xmlrpcval(array(new xmlrpcval('sin' , "string"), // here we pass col name 			
						new xmlrpcval('=',"string"), // here we pass condition 
						new xmlrpcval($_POST['W2TxtSINSec'],"string")),"array"), // value it may be int or string 
				);			
		}		
		
		$search_id = '';
	    print_r($arrayApplicantRec); 
		    if (!empty($arrayApplicantRec)){
				$response_app_id = $this->search('applicant.record',$key_applicant);
				$search_id = $response_app_id[0];		
				echo "search_id>>>>>".$search_id;
				if($search_id){
					$app_rec_id = $search_id;
					$this->write('applicant.record',$search_id, $arrayApplicantRec);
				}
				else{
					print_r($arrayApplicantRec);
					$app_rec_id = $this->create('applicant.record', $arrayApplicantRec);
					
					/*$arrayApplicantRecid = array(
						'applicant_opportunity'=>new xmlrpcval($app_rec_id, "int"),
					);
					echo "app_rec_id".$app_rec_id;
					$this->write('applicant.record',$app_rec_id, $arrayApplicantRecid);	
					*/
				}
				$opp_id = array(
					'opportunity_id'=>new xmlrpcval($app_rec_id,"int"),
				);
				$this->write('crm.lead',$crmid, $opp_id);  
			}
			
			$arrayApplicantRecid = array(
				'applicant_opportunity'=>new xmlrpcval($app_rec_id, "int"),
			);
			$this->write('applicant.record',$app_rec_id, $arrayApplicantRecid);	

		## applicant address
		$this->applicant_address('applicant.address',$app_rec_id,$len);
	
    }



// vijay

	function applicant_income2_employer($obj,$applicant_id,$len,$str,$annual_income)
	{
				echo "inside applicant_income2_employer %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%---------------";
				
				if($str == "income_employer")
				{		
							$terd1 = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$terd2 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("2","int")),"array"), // value it may be int or string 
									);	
									
									
									
							$merge=array_merge($terd2,$terd1);
							//$response_add2 = $this->search('income.employer',$merge2);	
							
							$response = $this->search($obj,$merge);	
							echo "response------------------!!!!!!!!!!!!!".$response;
							print_r($response);
							
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id-------------------123--------------------";
							
							print_r($id_val);
							
							echo "id";
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
						$industry_type = array_key_exists($_POST['W72IndustryType'], $array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						echo "W7TxtPreNumberofMonths>>>".$_POST['W7TxtPreNumberofMonths'];
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W72IndustryType']];
							echo "industry>>>".$industry;
						}
						echo "annaul ....=".$annual_income;
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W72IndustryType'], "string"),
					    				'business'=>new xmlrpcval($_POST['W72TxtCompName'], "string"),
					    				'position'=>new xmlrpcval($_POST['W72TxtJobTitle'], "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
										'annual_income'=>new xmlrpcval($annual_income, "string"),
										'historical'=>new xmlrpcval(false,"boolean"),
										'income_type'=>new xmlrpcval("1","string"),
										'income'=>new xmlrpcval("2","int"),
										'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsr1'], "int"),

					    	);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
						}	
					}


	}
	
	function applicant_income3_employer($obj,$applicant_id,$len,$str,$annual_income)
	{
				echo "inside applicant_income3_employer %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%";
				
				if($str == "income_employer")
				{		
							$key = array(new xmlrpcval(array(new xmlrpcval('applicant_id' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval($applicant_id,"int")),"array"), // value it may be int or string 
									);
							
							$key1 = array(new xmlrpcval(array(new xmlrpcval('income' , "string"), // here we pass col name 			
										new xmlrpcval('=',"string"), // here we pass condition 
										new xmlrpcval("3","int")),"array"), // value it may be int or string 
									);		
							
							$merge=array_merge($key,$key1);
							
							$response = $this->search($obj,$merge);	
							$id_val = array();
							for($j=0;$j<sizeof($response);$j++){
								$id_val[$j] = new xmlrpcval($response[$j], "int");
							}
							echo "id";
							print_r($id_val);
							echo "id";
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
						$industry_type = array_key_exists($_POST['W73IndustryType'], $array_industry);
						$industry = '';
						echo "industry_type>>>".$industry_type;
						echo "W7TxtPreNumberofMonths>>>".$_POST['W7TxtPreNumberofMonths'];
						if ($industry_type == "1"){
							$industry = $array_industry[$_POST['W73IndustryType']];
							echo "industry>>>".$industry;
						}
						echo "annaul ....)))))))))))))))))))))))".$annual_income;
						$arrayCreatData = array(
									'applicant_id'=>new xmlrpcval($applicant_id, "int"),
					    				'name'=>new xmlrpcval($_POST['W73IndustryType'], "string"),
					    				'business'=>new xmlrpcval($_POST['W73TxtCompName'], "string"),
					    				'position'=>new xmlrpcval($_POST['W73TxtJobTitle'], "string"),
					    				'industry'=>new xmlrpcval($industry, "string"),
										'annual_income'=>new xmlrpcval($annual_income, "string"),
										'historical'=>new xmlrpcval(false,"boolean"),
										'income_type'=>new xmlrpcval("1","string"),
										'income'=>new xmlrpcval("3","int"),
										'month'=>new xmlrpcval($_POST['W7TxtPreNumberofMonthsr2'], "int"),

					    	);
						//print_r($arrayCreatData);
					    if (!empty($arrayCreatData)){
								$this->create($obj,$arrayCreatData);	
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
