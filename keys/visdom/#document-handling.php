<?php

//header('Content-type: application/pdf');
//defined('_JEXEC') or die('Restricted access');
	include_once('xmlrpc.inc');
	
	define( '_JEXEC', 1 );
	define('JPATH_BASE', realpath(dirname(__FILE__)));
	define( 'DS', '/' );
    
	require_once ( JPATH_BASE .DS.'includes'.DS.'defines.php' );
	require_once ( JPATH_BASE .DS.'includes'.DS.'framework.php' );
	require_once ( JPATH_BASE .DS.'libraries'.DS.'joomla'.DS.'factory.php' );
	$mainframe =JFactory::getApplication('site');
    
	$config   = new JConfig();
	
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
			
			//echo $dbname."".$userid."".$password;
			//echo $base_url;
			$crm_lead = 3433;
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
				$arrayValread = array(
			new xmlrpcval('deal_ids','string'),
			
		);
		
		
		
		//$crm_lead = $_GET['crm_lead'];
		$clientIncomeDocListWeb = "";
		$clientDownpayDocListWeb = "";
		$clientMiscDocListWeb ="";
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
				$clientIncomeDocListWeb ="";
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
						if($deal_line_details['marketing_field']->scalarval() == 'clientIncomeDocListWeb'){
									$clientIncomeDocListWeb = $deal_line_details['name']->scalarval();
							}
						if($deal_line_details['marketing_field']->scalarval()=='clientPropertyDocListWeb'){
									$clientPropertyDocListWeb = $deal_line_details['name']->scalarval();
							}
						//echo $clientIncomeDocListWeb;
						if($deal_line_details['marketing_field']->scalarval()=='clientDownpayDocListWeb'){
									$clientDownpayDocListWeb = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval()=='clientMiscDocListWeb'){
									$clientMiscDocListWeb = $deal_line_details['name']->scalarval();
						}
					}
?>

<!DOCTYPE html>
<html>
	<head>
			<title>Visdom Document Handling</title>
			<link href="css/style.css" rel="stylesheet" type="text/css" />
	</head>
<body>
	<div class="body">
		<div class="doc-content">
			<div class="top-content">
				<div class="top-content-span1">
					<label>File Location</label><input type="file" />
				</div>
				<div class="top-content-span2">
					<label>Opportunity</label><input type="text" /><button>Load</button>
				</div>
			</div>
			<div class="bottom-content">
				<div class="bottom-content-span1">					
					<div class="doc-pdf">
						<!--embed name="plugin" src="result_patient.1.pdf" type="application/pdf">
						</embed-->
					</div>
				</div>
				<div class="bottom-content-span2">
					<?php if($applicant_name || $applicant_last_name): ?>
					<div class="content">
					<div class="blank"></div>
					<?php $length = count($applicant_name); //echo $length;?>
					<?php for($i =0; $i<$length;$i++):?>
					<div class="applicant"><?php echo $applicant_name[$i]." ".$applicant_last_name[$i]?></div>
					<!-- class="applicant">Applicant 2 Name page</div-->
					<?php endfor; ?>
					
					</div>
					<?php endif; ?>
					<?php if($clientIncomeDocListWeb):?>
					<div class="content">
						
					<label class="title">Incomes:</label>	
					<?php	$clientIncomeDocListsWeb = preg_split('/[\n\r]+/',$clientIncomeDocListWeb);//$clientIncomeDocListWeb = explode('-',$clientIncomeDocListWeb	)?>	
					<?php //print_r($clientIncomeDocListWeb)?>	
					<?php foreach($clientIncomeDocListsWeb as $clientIncomeDocListWeb):?>
					<?php	if($clientIncomeDocListWeb):?>
					<div class="field">
					
					<label><?php echo $clientIncomeDocListWeb = str_ireplace('-', '',$clientIncomeDocListWeb) ?></label>
					<?php $applicant_count = count($applicant_name)?>
					<?php for($i=0;$i<$applicant_count;$i++):?>
					<div class="applicant"><input id ="income<?php echo str_ireplace(' ','',$clientIncomeDocListWeb)."_".$i?>" type="text"></div>
					<!--div class="applicant"><input type="text"></div-->
					<?php endfor;?>
					</div>
					
					<?php endif;?>
					<?php endforeach;?>
					<!--div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div-->
					</div>
					<?php endif;?>
					<?php if($clientPropertyDocListWeb):?>
					<div class="content">
						
					<label class="title">Property:</label>	
					<?php	$clientPropertyDocsListWeb = preg_split('/[\n\r]+/',$clientPropertyDocListWeb);//$clientIncomeDocListWeb = explode('-',$clientIncomeDocListWeb	)?>	
					<?php //print_r($clientIncomeDocListWeb)?>	
					<?php foreach($clientPropertyDocsListWeb as $clientPropertyDocListWeb):?>
					<?php	if($clientPropertyDocListWeb):?>				
					<div class="field">
					<label><?php echo $clientPropertyDocListWeb = str_ireplace('-', '',$clientPropertyDocListWeb) ?></label>
					<?php for($i=0;$i<$applicant_count;$i++):?>
					<div class="applicant"><input id ="property<?php echo str_ireplace(' ','',$clientPropertyDocListWeb)."_".$i?>" type="text"></div>
					<!--div class="applicant"><input type="text"></div-->
					<?php endfor;?>
					</div>
					<?php endif;?>
					<?php endforeach;?>
					<!--div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					</div>
					<div class="content">
					<label class="title">Down Payment:</label>					
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div-->
					</div>
					<?php endif;?>
					<?php if($clientDownpayDocListWeb):?>
					<div class="content">
						
					<label class="title">Down Payment:</label>	
					<?php	$clientDownpayDocsListWeb = preg_split('/[\n\r]+/',$clientDownpayDocListWeb);//$clientIncomeDocListWeb = explode('-',$clientIncomeDocListWeb	)?>	
					<?php //print_r($clientIncomeDocListWeb)?>	
					<?php foreach($clientDownpayDocsListWeb as $clientDownpayDocListWeb):?>
					<?php	if($clientDownpayDocListWeb):?>				
					<div class="field">
					<label><?php echo $clientDownpayDocListWeb = str_ireplace('-', '',$clientDownpayDocListWeb) ?></label>
					<?php for($i=0;$i<$applicant_count;$i++):?>
					<div class="applicant"><input id ="downpay<?php echo str_ireplace(' ','',$clientDownpayDocListWeb)."_".$i?>" type="text"></div>
					<!--div class="applicant"><input type="text"></div-->
					<?php endfor;?>
					</div>
					<?php endif;?>
					<?php endforeach;?>
					<!--div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					</div>
					<div class="content">
					<label class="title">Down Payment:</label>					
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div-->
					</div>
					<?php endif;?>
					<?php if($clientMiscDocListWeb):?>
					<div class="content">
					<label class="title">Miscellaneous:</label>	
					<?php	$clientMiscDocsListWeb = preg_split('/[\n\r]+/',$clientMiscDocListWeb);//$clientIncomeDocListWeb = explode('-',$clientIncomeDocListWeb	)?>	
					<?php //print_r($clientIncomeDocListWeb)?>	
					<?php foreach($clientMiscDocsListWeb as $clientMiscDocListWeb):?>
					<?php	if($clientMiscDocListWeb):?>						
					<div class="field">
					<label><?php echo $clientMiscDocListWeb = str_ireplace('-', '',$clientMiscDocListWeb)?></label>
					<?php for($i=0;$i<$applicant_count;$i++):?>
					<div class="applicant"><input id ="misc<?php echo str_ireplace(' ','',$clientMiscDocListWeb)."_".$i?>" type="text"></div>
					<!--div class="applicant"><input type="text"></div-->
					<?php endfor;?>
					</div>
					<?php endif;?>
					<?php endforeach;?>
					<!--div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div>
					<div class="field">
					<label>Insert Text from Opportunity</label>
					<div class="applicant"><input type="text"></div>
					<div class="applicant"><input type="text"></div>
					</div-->
					</div>
					<?php endif;?>
					<div class="content">
					<button>Attach to Opportunity</button>
					<button>Close</button>
					</div>					
				</div>
			</div>
		</div>
	</div>
</body>
</html>
