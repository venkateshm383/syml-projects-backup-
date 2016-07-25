<?php
	include('xmlrpc.inc');
	
  $user = 'admin';
   $password = 'syml';
   $dbname = 'symlsys';
   $server_url = 'http://107.23.27.61:8069/xmlrpc/';

	/*
   if(isset($_COOKIE["user_id"]) == true)  {
       if($_COOKIE["user_id"]>0) {
       //return $_COOKIE["user_id"];
       echo $_COOKIE["user_id"];
       }
   }*/

   $sock = new xmlrpc_client($server_url.'common');
   $msg = new xmlrpcmsg('login');
   $msg->addParam(new xmlrpcval($dbname, "string"));
   $msg->addParam(new xmlrpcval($user, "string"));
   $msg->addParam(new xmlrpcval($password, "string"));
   $resp =  $sock->send($msg);
   //print_r($resp);
  
   $val = $resp->value();
   $userid = $val->scalarval();
   
   
   
	$arrayValread = array(
			new xmlrpcval('deal_ids','string'),
			
		);
		
		$client = new xmlrpc_client("http://107.23.27.61:8069/xmlrpc/object");
			$msg = new xmlrpcmsg('execute');
			$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
			$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
			$msg->addParam(new xmlrpcval($password, "string"));/** password */
			$msg->addParam(new xmlrpcval("crm.lead", "string"));
			$msg->addParam(new xmlrpcval("read", "string"));
			$msg->addParam(new xmlrpcval(3433, "int"));
			$msg->addParam(new xmlrpcval($arrayValread, "array"));
			$res = $client->send($msg);
			//print_r($res);
			if ($res->faultCode()){
			 throw new Exception( 'Error: '.$res->faultString());
			}
			 
				$deal_ids = $res->value()->scalarval();
				echo "<pre>";
				//print_r($deal_ids);
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
				foreach($deal_lines_ids as $deal_lines_id){
					//echo $deal_lines_id." ";
					$arrayValread = array(
						new xmlrpcval('name','string'),new xmlrpcval('marketing_field','string')
					);	
					$client = new xmlrpc_client("http://107.23.27.61:8069/xmlrpc/object");
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
						if($deal_line_details['marketing_field']->scalarval() == 'OriginalDesired'){
								$OriginalDesired = $deal_line_details['name']->scalarval();
						}
						if($deal_line_details['marketing_field']->scalarval() == 'OriginalDetails'){
								$OriginalDetails = $deal_line_details['name']->scalarval();
						}
				}
?>
<html>
	<head>
		
		
	</head>
<body>
<div id="original_app">
<?php echo $OriginalDesired ?>
Details: 
<?php echo $OriginalDetails ?>
</div>
</body>
</html>
