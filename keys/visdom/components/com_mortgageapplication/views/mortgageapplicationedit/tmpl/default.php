<?php
	$baseurl = JURI::base();
/*------------------------------------------------------------------------
# default.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');


include('xmlrpc.inc');
	
   //$user = 'admin';
   //$password = 'syml';
   //$dbname = 'symlsys';
   //$server_url = 'http://107.23.27.61:8069/xmlrpc/';

	/*
   if(isset($_COOKIE["user_id"]) == true)  {
       if($_COOKIE["user_id"]>0) {
       //return $_COOKIE["user_id"];
       echo $_COOKIE["user_id"];
       }
   }*/

	$db= JFactory::getDbo();
	
	$query = $db->getQuery(true);//
		    $table=$db->quoteName('g21yj_openerp_config');
		   
		    $query->clear();
		    $query->select($db->quoteName(array('state','database_name', 'user_id', 'password', 'url','email','send_mails_to_user','text_to_send')));
			$query->from($db->quoteName('g21yj_openerp_config'));
			
			$db->setQuery($query);
			
			$configs = $db->loadObjectList();
			$client ="";
			$dbname ="";
			$userid ="";
			$password ="";
			foreach($configs as $config){
				if($config->state == 1){
					$base_url = $config->url;
					
					$dbname =$config->database_name;
					$userid = $config->user_id;
					$password = $config->password;
				}
			}
		$new_base_url = str_ireplace('xmlrpc/object','',$base_url);
		$new_password = "%$10".$password;
		$arrayValread = array(
			new xmlrpcval('login','string')
		);
		//echo $userid;
		$client = new xmlrpc_client($base_url);
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("res.users", "string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($userid, "int"));
			$msg->addParam(new xmlrpcval($arrayValread, "array"));
			$res = $client->send($msg);
			//print_r($res);
			if ($res->faultCode()){
			 echo  'Error: '.$res->faultString();
			}
				
				$user_details = $res->value()->scalarval();
				$user_name = $user_details[login]->me['string'];
		

	/*
   $sock = new xmlrpc_client($server_url.'common');
   $msg = new xmlrpcmsg('login');
   $msg->addParam(new xmlrpcval($dbname, "string"));
   $msg->addParam(new xmlrpcval($user, "string"));
   $msg->addParam(new xmlrpcval($password, "string"));
   $resp =  $sock->send($msg);
   //print_r($resp);
  
   $val = $resp->value();
   $userid = $val->scalarval();
   */
   //echo $client;echo $userid;echo $password;exit;
    $crm_lead = $_GET['crm_lead'];
   $lending_goal = 0;
   $arrayValread = array(
			new xmlrpcval('what_is_your_lending_goal','string')
   );
			$client = new xmlrpc_client($base_url);
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("crm.lead", "string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($crm_lead, "int"));
			$msg->addParam(new xmlrpcval($arrayValread, "array"));
			$res = $client->send($msg);
			//print_r($res);
			if ($res->faultCode()){
			 echo  'Error: '.$res->faultString();
			}
			 
				$lending_goal_arr = $res->value()->scalarval();
				//echo "<pre>";print_r($lending_goal_arr); exit;
				$lending_goal = $lending_goal_arr[what_is_your_lending_goal]->me['string'];
				
			//start: Applicant name search
			$arrayValread = array(
				new xmlrpcval('app_rec_ids','string'),
		
			);
				
			$client = new xmlrpc_client($base_url);
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("crm.lead", "string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($crm_lead, "int"));
			$msg->addParam(new xmlrpcval($arrayValread, "array"));
			$res = $client->send($msg);
			//print_r($res);
			if ($res->faultCode()){
			 throw new Exception( 'Error: '.$res->faultString());
			}
			 
				$app_rec_ids = $res->value()->scalarval();
				//print_r($app_rec_ids);exit;
				foreach($app_rec_ids as $keys=>$values){
					$arr[$keys] = $values->scalarval();
					
				}
				
				$app_rec_ids=$arr['app_rec_ids'];
				$app_recs_ids=array();
				$applicant_name=array();
				$applicant_last_name=array();
				foreach($app_rec_ids as $app_rec_id){
					$app_recs_ids[]= $app_rec_id->me['int'];
					
				}
				foreach($app_recs_ids as $app_recs_id){
						$arrayValread = array(
							new xmlrpcval('applicant_last_name','string'),
							new xmlrpcval('applicant_name','string'),
						);
						
					$client = new xmlrpc_client($base_url);
					$msg = new xmlrpcmsg('execute');
					$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
					$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
					$msg->addParam(new xmlrpcval($password, "string"));/** password */
					$msg->addParam(new xmlrpcval("applicant.record", "string"));
					$msg->addParam(new xmlrpcval("read", "string"));
					$msg->addParam(new xmlrpcval($app_recs_id, "int"));
					$msg->addParam(new xmlrpcval($arrayValread, "array"));
					$res = $client->send($msg);
					if ($res->faultCode()){
					 echo 'Error: '.$res->faultString();
					}
						$applicant_line_details = $res->value()->scalarval();
						$applicant_name[] =$applicant_line_details['applicant_name']->scalarval();
						$applicant_last_name[] =$applicant_line_details['applicant_last_name']->scalarval();
				}
				
				//End: Applicant name search
				//Start: Search address and city
					if($lending_goal == 2){
						$arrayValread = array(
							new xmlrpcval('address','string'),
							new xmlrpcval('city','string'),
						);
						$client = new xmlrpc_client($base_url);
						$msg = new xmlrpcmsg('execute');
						$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
						$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
						$msg->addParam(new xmlrpcval($password, "string"));/** password */
						$msg->addParam(new xmlrpcval("crm.lead", "string"));
						$msg->addParam(new xmlrpcval("read", "string"));
						$msg->addParam(new xmlrpcval($crm_lead, "int"));
						$msg->addParam(new xmlrpcval($arrayValread, "array"));
						$res = $client->send($msg);
						if ($res->faultCode()){
						 echo 'Error: '.$res->faultString();
						}
						
						$address_arr = $res->value()->scalarval();
						//echo "<pre>";print_r($address_arr); exit;
						$address = $address_arr['address']->me['string'];
						$city = $address_arr['city']->me['string'];	
						//echo $address; echo $city;exit;	
					}	
				//End: Search address and city
	$arrayValread = array(
			new xmlrpcval('deal_ids','string'),
			
		);
		
		
		
		//$crm_lead = $_GET['crm_lead'];
		$client = new xmlrpc_client($base_url);
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("crm.lead", "string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($crm_lead, "int"));
			$msg->addParam(new xmlrpcval($arrayValread, "array"));
			$res = $client->send($msg);
			//print_r($res);
			if ($res->faultCode()){
			 throw new Exception( 'Error: '.$res->faultString());
			}
			 
				$deal_ids = $res->value()->scalarval();
				
				//print_r($deal_ids);exit;
				//echo "<pre>";
				foreach($deal_ids as $keys=>$values){
					$arr[$keys] = $values->scalarval();
					
				}
				
				$deal_line_ids=$arr['deal_ids'];
				$deal_lines_ids=array();
				foreach($deal_line_ids as $deal_line_id){
					$deal_lines_ids[]= $deal_line_id->me['int'];
					
					
				}
				//print_r($deal_lines_ids);
				//die('bmnbmb');
				$OriginalDesired ="";
				$OriginalDetails ="";
				$RecommendDetails = "";
				$notes ="";
				$OriginalDetailhtml ="";
				$RecommendDetailhtml ="";
				$MaxMortgageTable_html ="";
				$Goals_html ="";
				$HelpingAchieve_html ="";
				$RiskBias_html ="";
				$CompareDetails_html ="";
				$VarVsFixedAnalysis_html ="";
				$NumberOfRecommendations_html ="";
				$VariableRecommendations_html ="";
				$FixedRecommendations_html ="";
				$LOCRecommendations_html ="";
				foreach($deal_lines_ids as $deal_lines_id){
					//echo $deal_lines_id." ";
					$arrayValread = array(
						new xmlrpcval('name','string'),new xmlrpcval('marketing_field','string')
					);	
					$client = new xmlrpc_client($base_url);
					$msg = new xmlrpcmsg('execute');
					$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
					$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
					$msg->addParam(new xmlrpcval($password, "string"));/** password */
					$msg->addParam(new xmlrpcval("deal", "string"));
					$msg->addParam(new xmlrpcval("read", "string"));
					$msg->addParam(new xmlrpcval($deal_lines_id, "int"));
					$msg->addParam(new xmlrpcval($arrayValread, "array"));
					$res = $client->send($msg);
					if ($res->faultCode()){
					 echo 'Error: '.$res->faultString();
					}
						$deal_line_details = $res->value()->scalarval();
						
						if($lending_goal == 1){
							
							if($deal_line_details['marketing_field']->scalarval() == 'MaxMortgageIntro'){
									$MaxMortgageIntro = $deal_line_details['name']->scalarval();
							}
							
							if($deal_line_details['marketing_field']->scalarval() == 'MaxMortgageTable'){
									$MaxMortgageTable_html .="<table>";
									$MaxMortgageTable_html .='<tr class="heading"><td></td><td>Debt<br> Reduction</td><td>Down<br> Payment</td><td>Mortgage<br> Amount</td><td>Insurance<br> (See notes)</td><td>Maximum<br> Purchase</td></tr>';
									$MaxMortgageTable = $deal_line_details['name']->scalarval();
									$MaxMortgageTable_lines = preg_split('/[\n\r]+/',$MaxMortgageTable);
									foreach($MaxMortgageTable_lines as $MaxMortgageTable_line){
										$MaxMortgageTable_html .="<tr>";
										
										$MaxMortgageTable_line = explode(')',$MaxMortgageTable_line);
										if($MaxMortgageTable_line[0]!=""){
											$MaxMortgageTable_html .= "<td>".$MaxMortgageTable_line[0].")</td>";
										}
										$MaxMortgageTable_line_cols = preg_split('/\s+/', $MaxMortgageTable_line[1]);
										
										//print_r($MaxMortgageTable_line_cols);exit;
										foreach($MaxMortgageTable_line_cols as $MaxMortgageTable_line_col){
											if($MaxMortgageTable_line_col !=""){
											//echo $MaxMortgageTable_line_col;
												$MaxMortgageTable_html .= "<td>".$MaxMortgageTable_line_col."</td>";
											}
										}
										$MaxMortgageTable_html .="</tr>";	
										
									}
									
									$MaxMortgageTable_html .="</table>";
									
									
							}
							
						}
						
						
						if($deal_line_details['marketing_field']->scalarval() == 'OriginalDesired'){
								$OriginalDesired = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'OriginalDetails'){
								$OriginalDetails = $deal_line_details['name']->scalarval();
								//print_r(preg_split('/[\n\r]+/', $OriginalDetails));
								if(strpos($OriginalDetails,':') == true){
									$OriginalDetails = preg_split('/[\n\r]+/', $OriginalDetails);
									$product_id = 0;
									foreach($OriginalDetails as $OriginalDetail){
										$OriginalDetailval = explode(':', $OriginalDetail);
										//print_r( $OriginalDetailval);
										if($OriginalDetailval[0]!="" || $OriginalDetailval[1]!=""){
										if(strpos($OriginalDetailval[0],'Product ID') >= 0){
														
														//$OriginalDetailhtml .= '<div><label class="subject" style="display:none;">'.$OriginalDetailval[0].': </label><label class="value">'.trim($OriginalDetailval[1]).'</label></div>';
														//$OriginalDetailhtml .='<table><tr id="'.$OriginalDetailval[1].'"><td><label class="subject">Initial Here:</label><input class="product_selection" type="text"></td></tr></table>';
														$product_id = $OriginalDetailval[1];
														
														
											}
										}
										
									}
									foreach($OriginalDetails as $OriginalDetail){
										$OriginalDetailval = explode(':', $OriginalDetail);
										//print_r($OriginalDetailval);
										if($OriginalDetailval[0]!="" || $OriginalDetailval[1]!=""){
											if(stripos($OriginalDetailval[0],'Product ID') === false){
												if (strpos($OriginalDetailval[1],'Initial Here') == false  ) {
													$OriginalDetailhtml .= '<div><label class="subject">'.$OriginalDetailval[0].': </label><label class="value">'.$OriginalDetailval[1].'</label></div>';
												}
												
												else{
													$OriginalDetailval = explode(' ', $OriginalDetail);
													//print_r($OriginalDetailval);
													//var_dump($OriginalDetail);
													
													$OriginalDetailval[1] = str_ireplace('Initial',' ',$OriginalDetailval[1]);
													//$OriginalDetailval[1] = str_ireplace('here',' ',$OriginalDetailval[1]);
													
													//$OriginalDetailhtml .='<div>'.$OriginalDetailhtml[0].''.$OriginalDetailhtml[1].'</div>';
													$OriginalDetailhtml .='<div class="col1"><label class="subject">'.$OriginalDetailval[0].' </label><label class="value">'.$OriginalDetailval[1].'</label></div><div class="col2"><table style="width: 60%;"><tr id="'.$product_id.'"><td><label class="subject">Initial Here:</label><input class="product_selection" type="text" style="width: 50px;"></td></tr></table></div>';
													
												}
											
											}	
										}
									}
							}
							else{
								$OriginalDetailhtml .= '<div>'.$OriginalDetails.'</div>';
							}
						}
						if($deal_line_details['marketing_field']->scalarval() == 'RecommendDetails'){
								$RecommendDetails = $deal_line_details['name']->scalarval();
								$RecommendDetails = preg_split('/[\n\r]+/', $RecommendDetails);
								
								$product_id = 0;
									foreach($RecommendDetails as $RecommendDetail){
										$RecommendDetailval = explode(':', $RecommendDetail);
										//print_r( $OriginalDetailval);
										//print_r($RecommendDetailval);
										if($RecommendDetailval[0]!="" || $RecommendDetailval[1]!=""){
										if(strpos($RecommendDetailval[0],'Product ID') >= 0){
														
														//$OriginalDetailhtml .= '<div><label class="subject" style="display:none;">'.$OriginalDetailval[0].': </label><label class="value">'.trim($OriginalDetailval[1]).'</label></div>';
														//$OriginalDetailhtml .='<table><tr id="'.$OriginalDetailval[1].'"><td><label class="subject">Initial Here:</label><input class="product_selection" type="text"></td></tr></table>';
														$product_id = $RecommendDetailval[1];
														
														
											}
										}
										
									}
								
								foreach($RecommendDetails as $RecommendDetail){
									$RecommendDetailval = explode(':', $RecommendDetail);
									//print_r($OriginalDetailval);
									if($RecommendDetailval[0]!="" || $RecommendDetailval[1]!=""){
										if(stripos($RecommendDetailval[0],'Product ID') === false){
											if (strpos($RecommendDetailval[1],'Initial Here') == false) {
											
												$RecommendDetailhtml .= '<div><label class="subject">'.$RecommendDetailval[0].': </label><label class="value">'.trim($RecommendDetailval[1]).'</label></div>';
											}
											else{
												//$RecommendDetaillastline = explode(' ', $RecommendDetailval[1]);
												//var_dump($RecommendDetaillastline);
												$RecommendDetailval[1] = str_ireplace('Initial',' ',$RecommendDetailval[1]);
												$RecommendDetailval[1] = str_ireplace('here',' ',$RecommendDetailval[1]);
												
												$RecommendDetailhtml .='<div class="col1"><label class="subject">'.$RecommendDetailval[0].': </label><label class="value">'.trim($RecommendDetailval[1]).'</label></div><div class="col2"><table style="width: 60%;"><tr id="'.$product_id.'"><td><label class="subject">Initial Here:</label><input class="product_selection" type="text" style="width: 50px;"></td></tr></table></div>';
											}
										}
										
									}
								}
								//$RecommendDetailhtml .= '<input type="text"></input>';
						}
						if($deal_line_details['marketing_field']->scalarval() == 'VariableRecommendations'){
								$VariableRecommendations = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'Notes'){
								$notes = $deal_line_details['name']->scalarval();
								$notes = explode(': ', $notes);
								$notes_first_line = $notes[0];
								//var_dump($notes[1]);
								$notes_lines = preg_split('/[\n\r]+/', $notes[1]);
								foreach($notes_lines as $notes_line){
									if($notes_line){
										$notes_lines_html .= "<li>".$notes_line."</li>";
									}
								}
						}
						if($deal_line_details['marketing_field']->scalarval() == 'Goals'){
								$Goals_html = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'HelpingAchieve'){
								$HelpingAchieve_html = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'RiskBias'){
								$RiskBias_html = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'CompareDetails'){
								$CompareDetails_html = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'VarVsFixedAnalysis'){
								$VarVsFixedAnalysis_html = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'NumberOfRecommendations'){
								$NumberOfRecommendations_html = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'VariableRecommendations'){
								$VariableRecommendations_html = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'FixedRecommendations'){
								$FixedRecommendations_html = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'LOCRecommendations'){
								$LOCRecommendations_html = $deal_line_details['name']->scalarval();
						}
				}
				
				$arrayValread = array(
			new xmlrpcval('recommendations_ids','string'),
			
		);
		
		$client = new xmlrpc_client($base_url);
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("crm.lead", "string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($crm_lead, "int"));
			$msg->addParam(new xmlrpcval($arrayValread, "array"));
			$res = $client->send($msg);
			//print_r($res);
			if ($res->faultCode()){
			 throw new Exception( 'Error: '.$res->faultString());
			}
			 
				$recommendations_ids = $res->value()->scalarval();
				//echo "<pre>";
				//print_r($deal_ids);
				//echo "<pre>";
				foreach($recommendations_ids as $keys=>$values){
					$arr[$keys] = $values->scalarval();
					
				}
				
				$recommendations_line_ids=$arr['recommendations_ids'];
				$recommendation_lines_ids=array();
				foreach($recommendations_line_ids as $recommendation_line_id){
					$recommendation_lines_ids[]= $recommendation_line_id->me['int'];
					
					
				}
				//print_r($recommendation_lines_ids);
				//die('bmnbmb');
				$variable_options_html ="";
				$fixed_options_html ="";
				$loc_options_html ="";
				foreach($recommendation_lines_ids as $recommendation_lines_id){
					//echo $deal_lines_id." ";
					$arrayValread = array(
						new xmlrpcval("lender",'string'),new xmlrpcval("product",'string'),new xmlrpcval("mortgage_type",'string'),new xmlrpcval("term",'string'),new xmlrpcval("maximum_amortization",'string'),new xmlrpcval("mortgage_amount",'string'),new xmlrpcval("mortgage_payment",'string'),new xmlrpcval("cashback",'string'),new xmlrpcval("name",'string')
					);	
					$client = new xmlrpc_client($base_url);
					$msg = new xmlrpcmsg('execute');
					$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
					$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
					$msg->addParam(new xmlrpcval($password, "string"));/** password */
					$msg->addParam(new xmlrpcval("opp.recommendations", "string"));
					$msg->addParam(new xmlrpcval("read", "string"));
					$msg->addParam(new xmlrpcval($recommendation_lines_id, "int"));
					$msg->addParam(new xmlrpcval($arrayValread, "array"));
					$res = $client->send($msg);
					if ($res->faultCode()){
					 echo 'Error: '.$res->faultString();
					}
						$recommendation_line_details = $res->value()->scalarval();
						
						if($recommendation_line_details['mortgage_type']->scalarval() == 2){
							$product_details = array();
							$product_details = $recommendation_line_details['product']->scalarval();
							$prod = $product_details[1]->me['string'];
							$prod_id = $product_details[0]->me['int'];
							$lender_details = array();
							$lender_details = $recommendation_line_details['lender']->scalarval();
							$lender = $lender_details[1]->me['string'];
							switch($recommendation_line_details['term']->scalarval()){
								Case 1:
									$term = "6 Months";
									break;
								Case 2: 
									$term = "1 Year";
									break;
								Case 3:
									$term = "2 Years";
									break;
								Case 4:
									$term = "3 Years";
									break;
								Case 5:
									$term = "4 Years";
									break;
								Case 6:
									$term = "5 Years";
									break;
								Case 7:
									$term = "6 Years";
									break;
								Case 8:
									$term = "7 Years";
									break;
								Case 9:
									$term = "8 Years";
									break;
								Case 10:
									$term = "9 Years";
									break;
								Case 11:
									$term = "10 Years";
									break;
								Case 12:
									$term = "Other";
									break;
							}
							//echo $recommendation_line_details['product']->scalarval()[1]->me['string'];
							$variable_options_html .="<tr id=".$prod_id."><td>".$lender."</td><td>".$prod."</td><td>".$term."</td><td>".$recommendation_line_details['maximum_amortization']->scalarval()."</td><td>".$recommendation_line_details['mortgage_payment']->scalarval()."</td><td>".number_format((float)$recommendation_line_details['name']->scalarval(),2,'.','')."%</td><td><input type='text' class='product_selection'></input></td></tr>";
						}
						if($recommendation_line_details['mortgage_type']->scalarval() == 3){
							$product_details = array();
							$product_details = $recommendation_line_details['product']->scalarval();
							
							$prod = $product_details[1]->me['string'];
							$prod_id = $product_details[0]->me['int'];
							$lender_details = array();
							$lender_details = $recommendation_line_details['lender']->scalarval();
							$lender = $lender_details[1]->me['string'];
							//echo $recommendation_line_details['product']->scalarval()[1]->me['string'];
							switch($recommendation_line_details['term']->scalarval()){
								Case 1:
									$term = "6 Months";
									break;
								Case 2: 
									$term = "1 Year";
									break;
								Case 3:
									$term = "2 Years";
									break;
								Case 4:
									$term = "3 Years";
									break;
								Case 5:
									$term = "4 Years";
									break;
								Case 6:
									$term = "5 Years";
									break;
								Case 7:
									$term = "6 Years";
									break;
								Case 8:
									$term = "7 Years";
									break;
								Case 9:
									$term = "8 Years";
									break;
								Case 10:
									$term = "9 Years";
									break;
								Case 11:
									$term = "10 Years";
									break;
								Case 12:
									$term = "Other";
									break;
							}
							$fixed_options_html .="<tr id=".$prod_id."><td>".$lender."</td><td>".$prod."</td><td>".$term."</td><td>".$recommendation_line_details['maximum_amortization']->scalarval()."</td><td>".$recommendation_line_details['mortgage_payment']->scalarval()."</td><td>".number_format((float)$recommendation_line_details['name']->scalarval(),2,'.','')."%</td><td><input type='text' class='product_selection'></input></td></tr>";
						}
						if($recommendation_line_details['mortgage_type']->scalarval() == 1){
							$product_details = array();
							$product_details = $recommendation_line_details['product']->scalarval();
							$prod = $product_details[1]->me['string'];
							$prod_id = $product_details[0]->me['int'];
							$lender_details = array();
							$lender_details = $recommendation_line_details['lender']->scalarval();
							$lender = $lender_details[1]->me['string'];
							//echo $recommendation_line_details['product']->scalarval()[1]->me['string'];
							switch($recommendation_line_details['term']->scalarval()){
								Case 1:
									$term = "6 Months";
									break;
								Case 2: 
									$term = "1 Year";
									break;
								Case 3:
									$term = "2 Years";
									break;
								Case 4:
									$term = "3 Years";
									break;
								Case 5:
									$term = "4 Years";
									break;
								Case 6:
									$term = "5 Years";
									break;
								Case 7:
									$term = "6 Years";
									break;
								Case 8:
									$term = "7 Years";
									break;
								Case 9:
									$term = "8 Years";
									break;
								Case 10:
									$term = "9 Years";
									break;
								Case 11:
									$term = "10 Years";
									break;
								Case 12:
									$term = "Other";
									break;
							}
							$loc_options_html .="<tr id=".$prod_id."><td>".$lender."</td><td>".$prod."</td><td>".$term."</td><td>".$recommendation_line_details['maximum_amortization']->scalarval()."</td><td>".$recommendation_line_details['mortgage_payment']->scalarval()."</td><td>".number_format((float)$recommendation_line_details['name']->scalarval(),2,'.','')."%</td><td><input type='text' class='product_selection'></input></td></tr>";
						}
						
				}
				
?>
<title>Mortgage Proposal</title>
<link rel="stylesheet" type="text/css" href="templates/protostar/css/wizard2.css" />
<div id="wrap1">
<div id="mortgage-form" class="">
<div id="container" style="margin-top:2px">
	<div id="header">
		<img src="templates/protostar/images/wizard/vission_11.png" align="left" width="300"  style="padding-left:20px;">
		<h1><span>M</span>ortgage <span>P</span>roposal</h1>
		<h2>
			<?php if($applicant_name[0]!="" || $applicant_last_name[0]!=""): ?>
			<?php echo $applicant_name[0]." ".$applicant_last_name[0]?><br>
			<?php endif;?>
			<?php if($applicant_name[1]!="" || $applicant_last_name[1]!=""):?>
			<span>and</span><br>
			<?php echo $applicant_name[1]." ".$applicant_last_name[1]?><br>	
			<?php endif;?>
		</h2>
		<?php if($lending_goal == 2):?>
		<h3>
			<?php echo $address?><br>
			<?php echo $city?>
		</h3>
		<?php else:?>
		<h3>
			321 Summit Dr.<br>
			Calgary, AB
		</h3>
		<?php endif;?>
	</div>
	<div class="content">
		<div class="content1">
			<h3>Goals</h3>
			<!--p>Congratulations on your goal to own the property at 321 Summit Drive, Calgary, AB. In your application, you indicated you wanted to purchase this property with a down payment of $70,000 and financing of $375,000. As we have explored mortgage options for you, we have also considered goals of cost savings, ease, payout flexibility and financial saving. In short we have sought to discover a mortgage which serves you rather than you serving a Mortgage.</p-->
			<p><?php echo $Goals_html ?></p>
			<input type="hidden" id="bl" value ="<?php echo $new_base_url ?>"></input>
			<h3>Original Application</h3>
			<p><?php echo $OriginalDesired ?></p>
			<div class="square_box">
			<!--div><label class="subject">Mortgage Amount:</label><label class="value">$750,000</label></div>
			<div><label class="subject">Insurance Amount:</label><label class="value">$0</label></div>
			<div><label class="subject">Mortgage Type:</label><label class="value">Fixed</label></div>
			<div><label class="subject">Mortgage Term:</label><label class="value">5 Year</label></div>
			<div><label class="subject">Payment Amount:</label><label class="value">$3,584</label></div>
			<div><label class="subject">Annual Interest Rate:</label><label class="value">3.09%To Select</label></div-->
			<?php echo $OriginalDetailhtml ?>
			<!--div class="col1"><label class="subject">Lender:</label><label class="value">Merix</label></div>
			<div class="col2"><label class="subject">Initial Here:</label><input type="text"></input></div-->
			</div>
			<input type="hidden" id="cl" value ="<?php echo $crm_lead ?>"></input>
			
			<h3>Helping you achieve your goals</h3>
			<!--p>When considering a significant financial decision such as a mortgage, wouldn’t you like to know you are getting the best option for which you can qualify? Achieving this can be a challenge because there are thousands of different Mortgage Products available in Canada. Understanding the options and those which you may qualify for is a daunting and time consuming task.</p>
			<p>To help you achieve your goals, we have virtually underwritten your application across <b>2,343</b> Mortgage Products. Of those options, the property you are financing could potentially qualify for <b>1,763</b> different possibilities. We have filtered these options for you (based upon cost effectiveness, interest rate, flexibility and other criteria) to the best 15 Mortgages presently offered by lenders which fit your goals. And finally, we have also provided a single recommendation for your consideration along with the reasons for our recommendation.</p-->
			<p><?php echo $HelpingAchieve_html ?></p>
			<h3>Our Recommendation</h3>
			<p style="display:none;">We would recommend you consider the following option:</p>
			<div class="square_box">
			<!--div><label class="subject">Mortgage Amount:</label><label class="value">$750,000</label></div>
			<div><label class="subject">Insurance Amount:</label><label class="value">$0</label></div>
			<div><label class="subject">Mortgage Type:</label><label class="value">Variable</label></div>
			<div><label class="subject">Mortgage Term:</label><label class="value">5 Year</label></div>
			<div><label class="subject">Payment Amount:</label><label class="value">$3,510</label></div>
			<div><label class="subject">Annual Interest Rate:</label><label class="value">2.5%To Select</label></div-->
			<?php echo $RecommendDetailhtml ?>
			<!--div class="col1"><label class="subject">Lender:</label><label class="value">Scotia</label></div>
			<div class="col2"><label class="subject">Initial Here:</label--><!--/div-->
			</div>
			<input type="hidden" id="dn" value ="<?php echo $dbname ?>"></input>
			<p>For many people, the selection of a mortgage often involves an effort to understand the balance between reducing cost and reducing risk. Most of us like to save as much money as possible, though we also do not want our costs to rise in the future in case interest rates go up. Additionally, if interest rates fall, we don’t want to be locked in at a higher rate. A third consideration in mortgage decisions involves payout penalties if you sell your property or refinance it before the term is complete. In our recommendation, we have sought to understand your personal “balance” between reducing risk and reducing cost.</p>
			<!--p>Based upon the future lifestyle questions you answered in your application, your “bias” towards reducing future risk (the possibility of rising interest rates and/or payout penalties) in your mortgage is low. This creates an opportunity for you to reduce your current mortgage costs with a variable Mortgage. We recommended the above mortgage solution because it most closely aligns with your personal “balance” between reducing risk and reducing cost.</p-->
			<p><?php echo $RiskBias_html ?></p>
			<p>The following characteristics of this mortgage may help you consider this recommendation:<br/> 
			<p><?php echo $CompareDetails_html ?></p>
			<p><?php echo $VarVsFixedAnalysis_html ?></p>
			<p><?php echo $NumberOfRecommendations_html ?></p>
			<!--b>Cost Comparison:</b> This recommendation <b>is $74 / month less</b> expensive than a 5 year fixed mortgage.<br>
			<!--b>Payout Risk Comparison:</b> This recommendation has a maximum payout penalty of 3 months of interest (currently <b>$9,827</b>) as opposed to a Fixed Mortgage which could have a payout penalty of as much as <b>$23,543</b>.<br>
			<b>Interest Rate Trend:</b> The current interest rate trend is level. In the past 20 years, there have been 2 periods of rising interest rates (17 months & 30 months). The average increase in rates during these periods (prior to rates falling again) was 1.68%. Unless economic activity picks up significantly, it is unlikely rates will rise significantly. Interest rates would need to rise by <b>0.93%</b> over the next <b>2 years</b> before a 5 Year Fixed mortgage would save you any money.</p>
			<p>While we recommend the above mortgage because it best suits our understanding of your needs, we also recognize our understanding of your financing goals may be imperfect. Accordingly, we have also provided core details for an additional <b>15</b> mortgages. You will find these listed in the tables below.</p-->
			</p>
			<input type="hidden" id="un" value ="<?php echo $user_name ?>"></input>
			<?php if($lending_goal == 1):?>
			<h3>Maximum Purchase</h3>
			<p><?php echo $MaxMortgageIntro?></p>
			<!--p>When qualifying for a mortgage with fluctuating interest rates or a shorter term, the Government of Canada requires that lenders use a higher interest in their qualification formulas than the actual rate of the product. This is to ensure that home buyers have some 'financial margin' if interest rates happen to rise over the next 5 years. Condo fees also present an additional consideration because lenders consider these as fixed monthly cost which is not required if you purchase a house. The following section outlines the various maximum purchase amounts you qualify for depending on the type and term of mortgage you select. This pre-approval is based on the lenders and insurers acceptance of all supporting documents and property.</p-->
			<div>
			<div class="CSSTableGenerator" >
					<!--table >
						<tr class="heading">
							<td>
								
							</td>
							<td >
								Debt<br> Reduction
							</td>
							<td>
								Down<br> Payment
							</td>
							<td>
								Mortgage<br> Amount
							</td>
							<td >
								Insurance<br> (See notes)
							</td>
							<td>
								Maximum<br> Purchase
							</td>						
						</tr>
						<tr>
							<td >
								Merix
							</td>
							<td>
								Merix - ARM - 5YR - Open
							</td>
							<td>
								5 Year
							</td>
							<td >
								25
							</td>
							<td>
								3510.97
							</td>
							<td>
								2.50%
							</td>						
						</tr>
						<tr>
							<td >
								Merix
							</td>
							<td>
								Merix - ARM - 5YR - Open
							</td>
							<td>
								5 Year
							</td>
							<td >
								25
							</td>
							<td>
								3510.97
							</td>
							<td>
								2.50%
							</td>						
						</tr>
						<tr>
							<td >
								Merix
							</td>
							<td>
								Merix - ARM - 5YR - Open
							</td>
							<td>
								5 Year
							</td>
							<td >
								25
							</td>
							<td>
								3510.97
							</td>
							<td>
								2.50%
							</td>
						</tr>                    
					</table-->
					<?php echo $MaxMortgageTable_html;?>
				</div>
				
				</div>
			<p>Until you have an offer in place, there is no need to decide upon the exact mortgage type and term you desire.  Once you have found the property you want and placed an offer upon it, we will review all the mortgages in Canada at that time on your behalf and find the best ones for which your property can qualify.  However, there is value in completing a pre-approval with a selected lender.  In the event interest rates fall between now and when you purchase your property, the future mortgage on your property will use the current lower rates.  Alternatively, in the event rates fall, your future mortgage will have the new lower rates.</p>
			<?php endif;?>
			<?php if($VariableRecommendations_html):?>
			<h3>Variable Options</h3>
			<p><?php echo $VariableRecommendations_html ?></p>
			<?php endif;?>
			<!--p>A variable mortgage has an interest rate that will fluctuate with changes in the Bank of Canada Prime rate. This means payments on a variable mortgage will also fluctuate. If interest rates fall, so will the payments on a variable mortgage. Conversely, if interest rates rise, so will your payments. With that said though, the rates and payment on a variable mortgage are often lower than the rates on a fixed mortgage so as long as rates remain reasonably stable, one can often save money by taking a variable mortgage rather than a fixed mortgage. <p></p></p-->
			<div>
			<?php if($variable_options_html): ?>
			<div class="CSSTableGenerator" >
					<table >
						<tr class="heading">
							<td>
								Lender
							</td>
							<td >
								Product Name
							</td>
							<td>
								Term
							</td>
							<td>
								Amortization
							</td>
							<td >
								Monthly<br> Payment
							</td>
							<td>
								Interest<br> Rate
							</td>
							<td>
								Initial For<br> Approval
							</td>
						</tr>
						 <?php echo $variable_options_html ?>    
					</table>
				</div>
				<?php endif;?>
				</div>
				<input type="hidden" id="dw" value ="<?php echo $new_password ?>"></input>
				<?php if($FixedRecommendations_html):?>
			<h3>Fixed Options</h3>
			<p><?php echo $FixedRecommendations_html ?></p>
			<?php endif;?>
			<!--p>With a fixed term mortgage, the rate and payment will stay the same for the entire term of the mortgage. This type of mortgage is suitable if interest rates are rising or if you need to be entirely certain that your payments are fixed (i.e. of you would find an increase in payment to really difficult financially). The benefit of certainty in interest rate and payment comes with the disadvantage where rates are often higher than variable mortgages and there is also an increased risk of higher payout penalties in the event you either refinance your mortgage or sell your property prior to the end of the term. The below table represents the best 12 options* from the 2581 fixed rate mortgages we examined to determine the fit for your goals.</p-->
			<div>
			<?php if($fixed_options_html): ?>
			<div class="CSSTableGenerator" >
					<table >
						<tr class="heading">
							<td>
								Lender
							</td>
							<td >
								Product Name
							</td>
							<td>
								Term
							</td>
							<td>
								Amortization
							</td>
							<td >
								Monthly<br> Payment
							</td>
							<td>
								Interest<br> Rate
							</td>
							<td>
								Initial For<br> Approval
							</td>
						</tr>
						<?php echo $fixed_options_html ?>
					</table>
				</div>
				<?php endif;?>
			</div>
			<?php if($LOCRecommendations_html): ?>
			<h3>LOC Options</h3>
			<p><?php echo $LOCRecommendations_html ?></p>
			<!--p>A Line of Credit secured against your property, can have some advantages.  These include, lower payments, no payout penalties and the ability to access unused equity at any time.  However, there are also a number of disadvantages.  LOCs tend to require a large down payment or portion of existing equity, thus they are often only suitable for refinancing properties with a fairly large amount of existing equity.  Many people only pay the interest costs, in which case, the principle of the debt is never reduced and the total interest paid (in dollars) over the lifetime of the debt can be more than in a mortgage.  In the event interest rates rise, so does the payment.  If your mortgage payment represent a significant portion of your total debt costs, this can be a stressful event.</p-->
			<?php endif; ?>
			<div>
			<?php if($loc_options_html): ?>
			<div class="CSSTableGenerator" >
					<table >
						<tr class="heading">
							<td>
								Lender
							</td>
							<td >
								Product Name
							</td>
							<td>
								Term
							</td>
							<td>
								Amortization
							</td>
							<td >
								Monthly<br> Payment
							</td>
							<td>
								Interest<br> Rate
							</td>
							<td>
								Initial For<br> Approval
							</td>
						</tr>
						<?php echo $loc_options_html ?>
					</table>
				</div>
				<?php endif;?>
			</div>
			<?php if($notes_first_line || $notes_lines_html):?>
			<h3>Notes</h3>
			<!--p>* The “Best” mortgages options available are determined based upon the following factors:
			<ul>
			<li>Interest Rate</li>
			<li>Monthly Cost</li>
			<li>Payment flexibility</li>
			<li>Payout Penalty Cost</li>
			<li>Ease of dealing with Lender</li>
			<li>Speed (Responsiveness) Lender</li>
			</ul>
			</p-->
			<!--p>Opportunity.DealNotes.MarketingField(“Notes”))</p-->
			<p><?php echo $notes_first_line ?></p>
			<ul><?php echo $notes_lines_html ?></ul>
			<?php endif; ?>
			<!--p>Disclosure around past not same as future, simply an analysis of historical interest rate events. (Guy to provide final text to cover off liability. )</p-->
			<div class="button"><button id ="close">Close</button><a id="submit" ><button>Submit</button></a></div>
			
		</div>
		<div class="content2" style=" display:none;padding: 10% 100px 26%;">
			<h2 style="color:#0070C0;line-height: 50px;">Thank You</h2>
			<p>Thank you for reviewing your proposal and selecting a Mortgage Product.</p>
			<p>We have sent you an email confirming the product you have chosen and will shortly be sending your application to the appropriate lender.  </p>
			<p>In the event there is any need for clarification, we will be in touch with you via email.</p>
			<div class="button"><button id ="close_thank">Close</button></div>
		</div>
	</div>
</div>
</div>
</div>

<script>
	
	var session_id = "";
	var uniq_id_counter = 0;
	function rpc_jsonp(url, payload) {
		var data = {
		session_id: session_id,
		id: payload.id
		};

		var ajax = {
		type: "POST",
		dataType: 'jsonp',
		jsonp: 'jsonp',
		cache: false,
		data: data,
		url: url
		};

		var payload_str = JSON.stringify(payload);
		var payload_url = jQuery.param({r: payload_str});
		if (payload_url.length < 2000) {
		}
		console.log(ajax);
		ajax.data.r = payload_str;
		console.log(ajax);
		return jQuery.ajax(ajax);
		}
		function json(url, params) {
		var deferred = jQuery.Deferred();
		uniq_id_counter += 1;
		var payload = {
		'jsonrpc': '2.0',
		'method': 'call',
		'params': params,
		'id': ("r" + uniq_id_counter)
	};

	rpc_jsonp(url, payload).then(function (data, textStatus, jqXHR) {
			if (data.error) {
			deferred.reject(data.error);
			}
			deferred.resolve(data.result, textStatus, jqXHR);
			});
			return deferred;
	}

	
	jQuery("body").prepend('<div id="overlay" class="ui-widget-overlay" style="z-index: 1001; display: none;"></div>');
	jQuery("body").prepend("<div id='PleaseWait' style='display: none;'><img style='width:100px;' src='../visdom/images/loading.gif'/></div>");
	jQuery( "a#submit" ).click(function( event ) {
		
	  //jQuery("div.content1").hide();
	  //jQuery("div#header h2, div#header h3").hide();
	  //jQuery("div.content2").show();
	  
	  
		jQuery.each(jQuery('.product_selection'), function() {
			if(jQuery(this).val()){
				var row_id = jQuery(this).parent().parent();
				var prod ;
				prod = jQuery(row_id).attr('id');
				call_prod(prod);
				
				var pass = true;
				if(pass == false){
				return false;
				}
				jQuery("#overlay, #PleaseWait").show();

				return true;
			}			
		});
		
	});
	jQuery( "#close" ).click(function( event ) {
		//window.location=window.location.origin +"/visdom";
		window.location=window.location.origin ;
	});
	jQuery( "#close_thank" ).click(function( event ) {
		window.location=window.location.origin ;
	});
	function call_prod(prod){
			var db = jQuery('#dn').val();
            var login= jQuery('#un').val();
            var password = jQuery('#dw').val();
            var len = password.length;
            password = password.substring(4,len);
            var base_url = jQuery('#bl').val();
            
			"use strict";

				var deferred = jQuery.Deferred();
				
				
				//alert(base_url);
				json(base_url+'web/session/authenticate', 
				 {
					'base_location': base_url,
					'db': db,
					'login': login,
					'password':password ,
					
				}).done(function (data) {
					
						var cl_id= jQuery('#cl').val();
						var uid = data.uid;
						var session_id = data.session_id;
						json(base_url+'web/dataset/call_kw', 
							//{"model":"crm.lead","method":"write","args":[[parseInt(cl_id)],{"selected_product":parseInt(prod)}],"kwargs":{"context":{"lang":"en_US","tz":"Canada/Atlantic","uid":parseInt(uid),"stage_type":"opportunity","default_type":"opportunity","default_user_id":parseInt(uid)}},"session_id":session_id,"context":{"lang":"en_US","tz":"Canada/Atlantic","uid":parseInt(uid)}}
							{"model":"crm.lead","method":"write","args":[[parseInt(cl_id)],{"selected_product":parseInt(prod)}],"kwargs":{"context":{"lang":"en_US","tz":"Canada/Atlantic","uid":parseInt(uid),"stage_type":"opportunity","default_type":"opportunity","default_user_id":parseInt(uid)}},"session_id":session_id,"context":{"lang":"en_US","tz":"Canada/Atlantic","uid":parseInt(uid)}}
						).then(function (data) {
							jQuery("div.content1").hide();
						    jQuery("div#header h2, div#header h3").hide();
						    jQuery("div.content2").show();
							jQuery("#overlay").remove();
							jQuery("#PleaseWait").remove();
						});
						
					
					
					//alert(data.uid + " "+data.session_id)
					
					deferred.resolve();
				});

				return deferred;
	}
	
	
</script>
<style>
div#overlay {
background-color: rgba(0,0,0,0.5);
width: 100%;
position: fixed;
height: 100%;
top: 0;
}
div#PleaseWait{
z-index: 9999;
position: fixed;
vertical-align: middle;
width: 100%;
height: 100%;
text-align: center;
display: table;
top: 45%;
}
</style>
<!--script>
$(document).ready(function(){
	jQuery("body").prepend('<div id="overlay" class="ui-widget-overlay" style="z-index: 1001; display: none;"></div>');
	jQuery("body").prepend("<div id='PleaseWait' style='display: none;'><img src='../images/loading.gif'/></div>");
	
	jQuery('a#submit').submit(function() {
		var pass = true;
		
		if(pass == false){
			return false;
		}
		jQuery("#overlay, #PleaseWait").show();

		return true;
	});
})
</script-->

