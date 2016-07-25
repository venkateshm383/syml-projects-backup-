<?php
	//error_reporting(-1);
	/*
	define( '_JEXEC', 1 );
	define('JPATH_BASE', realpath(dirname(dirname(__FILE__))));
	define( 'DS', '/' );
    //echo JPATH_BASE .DS.'libraries'.DS.'joomla'.DS.'factory.php';exit;
	require_once ( JPATH_BASE .DS.'includes'.DS.'defines.php' );
	require_once ( JPATH_BASE .DS.'includes'.DS.'framework.php' );
	require_once ( JPATH_BASE .DS.'libraries'.DS.'joomla'.DS.'factory.php' );
	$mainframe =JFactory::getApplication('site');
   
	$config   = new JConfig();
	
    $db= JFactory::getDbo();

	//print_r($db);exit;
	*/
    include('xmlrpc.inc');
    //$openerp = new openerp();
	//$rates = array();
	//$rates = $openerp->calculate_rate($db);
	//print_r($rates);
	class openerp{
		
		function getProductId($db){
			
			$query = $db->getQuery(true);//
		    $table=$db->quoteName('g21yj_openerp_config');
		   
		    $query->clear();
		    $query->select($db->quoteName(array('state','database_name', 'user_id', 'password', 'url','email','send_mails_to_user','text_to_send')));
			$query->from($db->quoteName('g21yj_openerp_config'));
			
			$db->setQuery($query);
			
			$configs = $db->loadObjectList();
			
			foreach($configs as $config){
				if($config->state == 1){
					$client = new xmlrpc_client($config->url);
					$db=$config->database_name;
					$user_id = $config->user_id;
					$password=$config->password;
				}
			}
			
			$attribute = 'id';
			$operator  = '>';
			$keys = 0;
			$keys_type = 'int';
		
			$key = array(
				new xmlrpcval(array(new xmlrpcval($attribute , "string"),
				new xmlrpcval($operator,"string"),
				new xmlrpcval($keys, $keys_type)),"array"),
			);

			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($db, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($user_id, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("product.product", "string"));
			$msg->addParam(new xmlrpcval("search", "string"));
			$msg->addParam(new xmlrpcval($key, "array"));

			$resp = $client->send($msg);
			if ($resp->faultCode())
        
				echo 'Error: '.$resp->faultString();
				
			
			else
				//echo "<pre>";print_r($resp);exit;
				//if($resp->value()){
					$val = $resp->value();
					$ids = $val->scalarval();
					foreach($ids as $keys=>$values){
						$arr[$keys] = $values->scalarval();
				
						
					}
					return $arr;
				//}
				//else{
					//echo 'Error: '.$resp->faultString();
				//}
			
			
			
		}
		function calculate_rate($db){
			$prod_ids=$this->getProductId($db);
			
			
			$query = $db->getQuery(true);//
		    $table=$db->quoteName('g21yj_openerp_config');
		  
		    $query->clear();
		    $query->select($db->quoteName(array('state','database_name', 'user_id', 'password', 'url','email','send_mails_to_user','text_to_send')));
			$query->from($db->quoteName('g21yj_openerp_config'));
			
			$db->setQuery($query);
			
			$configs = $db->loadObjectList();
			
			foreach($configs as $config){
				if($config->state == 1){
					$client = new xmlrpc_client($config->url);
					$db=$config->database_name;
					$user_id = $config->user_id;
					$password=$config->password;
				}
			}
			
			
			foreach($prod_ids as $key=>$val){
				$ids[$key]=new xmlrpcval($prod_ids[$key], 'int');
				}
				
			
			
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($db, "string"));   //database 
			$msg->addParam(new xmlrpcval($user_id, "int"));		// user id	
			$msg->addParam(new xmlrpcval($password, "string"));	// password
			$msg->addParam(new xmlrpcval("product.product", "string")); 
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval($ids, "array"));
    

			$resp = $client->send($msg);
			
			if ($resp->faultCode())
        
				echo 'Error: '.$resp->faultString();

			else
				//1-LOC 2-Variable 3-Fixed
				$val = array();
				$arr = array();
				$product_id = array();
				
				$product_type = array();
				$product_id = array();
				$var_interest_rate = array();
				$loc_interest_rate = array();
				$fix_interest_rate = array();
				$val = $resp->value()->scalarval();
				
				foreach($val as $keys=>$values){
					$arr = $values->scalarval();
					$products[$keys]['id']= $arr['id']->scalarval();
					$products[$keys]['interest_rate']= $arr['interest_rate']->scalarval();
					$products[$keys]['posted_rate']= $arr['posted_rate']->scalarval();
					$products[$keys]['mortgage_type']= $arr['mortgage_type']->scalarval();
					$products[$keys]['term']= $arr['term']->scalarval();
					$products[$keys]['open_closed']= $arr['open_closed']->scalarval();
				}
				$var_interest_one_open = array();
				$var_interest_one_closed = array();
				
				$var_posted_one_open = array();
				$var_posted_one_closed = array();
				foreach($products as $keys=>$values){
					
					if($products[$keys]['mortgage_type']==2){
						if($products[$keys]['term']==2 && $products[$keys]['open_closed']=='closed'){
							$var_interest_one_open[$keys] = $products[$keys]['interest_rate'];
							$var_posted_one_open[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==2 && $products[$keys]['open_closed']=='open'){
							$var_interest_one_closed[$keys]=$products[$keys]['interest_rate'];
							$var_posted_one_closed[$keys]=$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==4){
							$var_interest_three[$keys] = $products[$keys]['interest_rate'];
							$var_posted_three[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==6){
							$var_interest_five[$keys] = $products[$keys]['interest_rate'];
							$var_posted_five[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==1){
							$var_interest_sixmon[$keys] = $products[$keys]['interest_rate'];
							$var_posted_sixmon[$keys] =$products[$keys]['posted_rate'];
						}
					}
					
					
				}
				foreach($products as $keys=>$values){
					
					if($products[$keys]['mortgage_type']==1){
						
						if($products[$keys]['term']==6){
							$loc_interest_five[$keys] = $products[$keys]['interest_rate'];
							$loc_posted_five[$keys] =$products[$keys]['posted_rate'];
						}
					}
				}
				
				foreach($products as $keys=>$values){
					
					if($products[$keys]['mortgage_type']==3){
						
						if($products[$keys]['term']==1){
							$fix_interest_sixmon[$keys] = $products[$keys]['interest_rate'];
							$fix_posted_sixmon[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==2){
							$fix_interest_one[$keys] = $products[$keys]['interest_rate'];
							$fix_posted_one[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==3){
							$fix_interest_two[$keys] = $products[$keys]['interest_rate'];
							$fix_posted_two[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==4){
							$fix_interest_three[$keys] = $products[$keys]['interest_rate'];
							$fix_posted_three[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==5){
							$fix_interest_four[$keys] = $products[$keys]['interest_rate'];
							$fix_posted_four[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==6){
							$fix_interest_five[$keys] = $products[$keys]['interest_rate'];
							$fix_posted_five[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==8){
							$fix_interest_seven[$keys] = $products[$keys]['interest_rate'];
							$fix_posted_seven[$keys] =$products[$keys]['posted_rate'];
						}
						if($products[$keys]['term']==11){
							$fix_interest_ten[$keys] = $products[$keys]['interest_rate'];
							$fix_posted_ten[$keys] =$products[$keys]['posted_rate'];
						}
					}
				}
				/*Calculate minimum rates */
				$min_var_interest_one_open ="";
				$min_var_interest_one_closed ="";
				$min_var_interest_three ="";
				$min_var_interest_five ="";
				$min_var_interest_sixmon ="";
				$min_loc_interest_five ="";
				$min_fix_interest_sixmon ="";
				$min_fix_interest_one ="";
				$min_fix_interest_two ="";
				$min_fix_interest_three ="";
				$min_fix_interest_four ="";
				$min_fix_interest_seven ="";
				$max_fix_posted_ten ="";
				$min_fix_interest_five ="";
				if(!empty($var_interest_one_open)){
					$min_var_interest_one_open= min($var_interest_one_open);
				}
				if(!empty($var_interest_one_closed)){
					$min_var_interest_one_closed= min($var_interest_one_closed);	
				}
				if(!empty($var_interest_three)){
					$min_var_interest_three= min($var_interest_three);
				}
				if(!empty($var_interest_five)){
					$min_var_interest_five= min($var_interest_five);
				}
				if(!empty($var_interest_sixmon)){
					$min_var_interest_sixmon= min($var_interest_sixmon);
				}
				if(!empty($loc_interest_five)){
					$min_loc_interest_five= min($loc_interest_five);
				}
				if(!empty($fix_interest_sixmon)){
					$min_fix_interest_sixmon= min($fix_interest_sixmon);
				}
				if(!empty($fix_interest_one)){
					$min_fix_interest_one= min($fix_interest_one);
				}
				if(!empty($fix_interest_two)){
					$min_fix_interest_two= min($fix_interest_two);
				}
				if(!empty($fix_interest_three)){
					$min_fix_interest_three= min($fix_interest_three);
				}
				if(!empty($fix_interest_four)){
					$min_fix_interest_four= min($fix_interest_four);
				}
				if(!empty($fix_interest_five)){
					$min_fix_interest_five= min($fix_interest_five);
				}
				if(!empty($fix_interest_seven)){
					$min_fix_interest_seven= min($fix_interest_seven);
				}
				if(!empty($fix_interest_ten)){
					$min_fix_interest_ten= min($fix_interest_ten);
				}
				
				/*Calculate maximum rates */
				$max_var_posted_one_open  ="";
				$max_var_posted_one_closed ="";
				$max_var_posted_three ="";
				$max_var_posted_five ="";
				$max_var_posted_sixmon ="";
				$max_loc_posted_five ="";
				$max_fix_posted_sixmon ="";
				$max_fix_posted_one ="";
				$max_fix_posted_two ="";
				$max_fix_posted_three ="";
				$max_fix_posted_four ="";
				$max_fix_posted_seven ="";
				$max_fix_posted_ten ="";
				$max_fix_posted_five ="";
				if(!empty($var_posted_one_open)){
					$max_var_posted_one_open=max($var_posted_one_open);
				}
				if(!empty($var_posted_one_closed)){	
				$max_var_posted_one_closed=max($var_posted_one_closed);
				}
				if(!empty($var_posted_three)){
				$max_var_posted_three=max($var_posted_three);
				}
				if(!empty($var_posted_five)){
				$max_var_posted_five=max($var_posted_five);
				}
				if(!empty($var_posted_sixmon)){
				$max_var_posted_sixmon=max($var_posted_sixmon);
				}
				if(!empty($loc_posted_five)){
				$max_loc_posted_five= max($loc_posted_five);
				}
				if(!empty($fix_posted_sixmon)){
				$max_fix_posted_sixmon=max($fix_posted_sixmon);
				}
				if(!empty($fix_posted_one)){
				$max_fix_posted_one=max($fix_posted_one);
				}
				if(!empty($fix_posted_two)){
				$max_fix_posted_two=max($fix_posted_two);
				}
				if(!empty($fix_posted_three)){
				$max_fix_posted_three=max($fix_posted_three);
				}
				if(!empty($fix_posted_four)){
				$max_fix_posted_four=max($fix_posted_four);
				}
				if(!empty($fix_posted_five)){
				$max_fix_posted_five=max($fix_posted_five);
				}
				if(!empty($fix_posted_seven)){
				$max_fix_posted_seven=max($fix_posted_seven);
				}
				if(!empty($fix_posted_ten)){
				$max_fix_posted_ten=max($fix_posted_ten);
				}
				
				$rates=array(
					'min_var_interest_one_open'=>$min_var_interest_one_open,
					
					'min_var_interest_one_closed'=>$min_var_interest_one_closed,	
					
					'min_var_interest_three'=>$min_var_interest_three,
					
					'min_var_interest_five'=>$min_var_interest_five,
					
					'min_var_interest_sixmon'=>$min_var_interest_sixmon,
					
					'min_loc_interest_five'=>$min_loc_interest_five,
					
					'min_fix_interest_sixmon'=>$min_fix_interest_sixmon,
					
					'min_fix_interest_one'=>$min_fix_interest_one,
					
					'min_fix_interest_two'=>$min_fix_interest_two,
					
					'min_fix_interest_three'=>$min_fix_interest_three,
					
					'min_fix_interest_four'=>$min_fix_interest_four,
					
					'min_fix_interest_five'=>$min_fix_interest_five,
					
					'min_fix_interest_seven'=>$min_fix_interest_seven,
					
					'min_fix_interest_ten'=>$min_fix_interest_ten,
					
					'max_var_posted_one_open'=> $max_var_posted_one_open,
					
					'max_var_posted_one_closed'=>$max_var_posted_one_closed,
					
					'max_var_posted_three'=>$max_var_posted_three,
					
					'max_var_posted_five'=>$max_var_posted_five,
					
					'max_var_posted_sixmon'=>$max_var_posted_sixmon,
					
					'max_loc_posted_five'=>$max_loc_posted_five,
					
					'max_fix_posted_sixmon'=>$max_fix_posted_sixmon,
					
					'max_fix_posted_one'=>$max_fix_posted_one,
					
					'max_fix_posted_two'=>$max_fix_posted_two,
					
					'max_fix_posted_three'=>$max_fix_posted_three,
					
					'max_fix_posted_four'=>$max_fix_posted_four,
					
					'max_fix_posted_five'=>$max_fix_posted_five,
					
					'max_fix_posted_seven'=>$max_fix_posted_seven,
					
					'max_fix_posted_ten'=>$max_fix_posted_ten
							
				);
				return $rates;
		}
		
	}

    ?>

