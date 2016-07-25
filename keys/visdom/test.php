<?php

//echo "<pre>";

//print_r($_POST);
$newname ="";
$target ="";
if($_FILES['filename']['name']){
	
	$info = pathinfo($_FILES['filename']['name']);
	 $ext = $info['extension']; // get the extension of the file
	 $newname = $_POST["crm_lead"]."new".$ext; 

	 $target = $newname;
	 move_uploaded_file( $_FILES['filename']['tmp_name'], $target);

	}
 
 if($target!= ""){
	 $filename = $target;
	 }
	 else{
		 
		 $filename = $_POST["parent_doc"];
	 }
	 //echo  $filename;
 //exit;
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
			
			$crm_lead = $_POST["crm_lead"];

require_once('fpdf/fpdf.php');
require_once('fpdi/fpdi.php');

	$end_directory =  './';
	$new_path = preg_replace('/[\/]+/', '/', $end_directory.'/'.substr($filename, 0, strrpos($filename, '/')));
	
	if (!is_dir($new_path))
	{
		// Will make directories under end directory that don't exist
		// Provided that end directory exists and has the right permissions
		mkdir($new_path, 0777, true);
	}
	
	
	//echo $filename;exit;
	$pdf = new FPDI();
	try {
		$pagecount = $pdf->setSourceFile($filename);
	}catch (Exception $e) {
			echo 'Caught exception: ',  $e->getMessage(), "\n";
	}
	
	$applicant_ids = $_POST["applicant_id"];
	$applicant_ids = explode(',',$applicant_ids);
	//print_r($applicant_ids);exit;
foreach( $_POST as $key => $value ) {
     //$$key = $value;
     
     if($value!= "" && $key!="crm_lead" && $key!="parent_doc"&& $key!="submittext" && $key!=""){
		 
		//echo  $key." and Value is: ".$value;
		//echo "<br/>";
		//Start:split pdf
	//echo $value;
	$page_numbers = explode(',',$value);
	//print_r($page_numbers);
	$page_count = count($page_numbers);
	$new_pdf = new FPDI();
	for ($i = 1; $i <= $page_count; $i++) {
			
			$new_pdf->AddPage();
			$new_pdf->setSourceFile($filename);
			
			//$new_pdf->useTemplate($new_pdf->importPage($i));
			$new_pdf->useTemplate($new_pdf->importPage($i), -10, 20, 210);
	}
		
		
		
		try {
			$new_pdf->SetFont('Arial','',8);
			$new_pdf->SetTextColor(0,0,0);
			$new_pdf->SetXY(90, 160);
			$childfile = str_ireplace("textincome","",$key);
			$new_filename = $end_directory.str_replace('.pdf', '', $filename).'_'.$childfile.".pdf";
			
			$new_pdf->Output($new_filename, "F");
			$file_content = file_get_contents($new_filename);
			//die($file_content);exit;
			$file_content = base64_encode($file_content);
			//echo "Page ".$i." split into ".$new_filename."<br />\n";
			$new_filename = str_ireplace('./','',$new_filename);
			
			$last_num = substr($key, -1); 
			
			$applicant_id = $applicant_ids[$last_num];
			$document_line =array(
									'opportunity_id'=>new xmlrpcval($crm_lead,"int"),
									'document_name'=>new xmlrpcval($new_filename,"string"),
									'document_data'=>new xmlrpcval($file_content,"string"),
									'applicant_id'=>new xmlrpcval($applicant_id,"int"),
									'created_by'=>new xmlrpcval($userid,"int"),
									
					);
					$arrayvalues =array(
						'document_ids'=>new xmlrpcval($document_line,"array")
					);
					
					//echo "<pre>";print_r($arrayvalues);exit;
					unlink($new_filename);
					
					$client = new xmlrpc_client($base_url);
					$msg = new xmlrpcmsg('execute');
					$msg->addParam(new xmlrpcval($dbname, "string"));  //* database name */
					$msg->addParam(new xmlrpcval($userid, "int")); /* userid */
					$msg->addParam(new xmlrpcval($password, "string"));/** password */
					
					$msg->addParam(new xmlrpcval("app.documents", "string"));
					$msg->addParam(new xmlrpcval("create", "string"));
					
					$msg->addParam(new xmlrpcval($document_line, "struct"));
					
					$res = $client->send($msg);	 
					if($res->faultCode()){
						throw new Exception('Error: '.$res->faultString());
						echo $res->faultString();
					}
					
				
		} catch (Exception $e) {
			echo 'Caught exception: ',  $e->getMessage(), "\n";
		}
	}
//End:split pdf
	}
	unlink($filename);
	echo "Files has been uploaded.";
//}
?>
<html>
	<head>
			<title>Visdom Document Handling</title>
			<link href="css/style.css" rel="stylesheet" type="text/css" />
	</head>
</html>
