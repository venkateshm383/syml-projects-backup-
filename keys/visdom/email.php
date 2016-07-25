<?php
	//die('nbmnbm');
	define( '_JEXEC', 1 );
	define('JPATH_BASE', realpath(dirname(__FILE__)));
	define( 'DS', '/' );
    
	require_once ( JPATH_BASE .DS.'includes'.DS.'defines.php' );
	require_once ( JPATH_BASE .DS.'includes'.DS.'framework.php' );
	require_once ( JPATH_BASE .DS.'libraries'.DS.'joomla'.DS.'factory.php' );
	$mainframe =JFactory::getApplication('site');
    
	$config   = new JConfig();
	//print_r($config);exit;
	//$mailer=JFactory::getMailer();
    $db= JFactory::getDbo();
	//print_r($db);exit;
	$session = JFactory::getSession();
	//print_r($session);exit;
	//$session->get('contactus');//exit;
	if($session->get('contactus')=="active"){
		//die('hgui');    

		$mails= new mails();

		$mails->usermails($config);	
		$mails->adminmails($config,$db);
		//$session->clear('contactus');
		//echo $session->get('contactus');exit;
		//$mails->redirect();
	}
	else{
		echo "Restricted access";
	}
	$session->clear('contactus');
	
	

class mails
{
	
	function usermails($config)
	{
		//echo "test";
		    //die('nbmnbnm');
            $to=$_REQUEST['email'];
            $username=$_REQUEST['name'];
            if($to){
		    $fromEmail  = $config->mailfrom;
			$fromName  = $config->fromname;
			//echo $fromEmail."".$fromName;exit;
			$fromName  = $config->fromname;
			$subject   = "Re:Contact Us";
			
			$headers = 'From: '. $fromName . ' <' . $fromEmail . '>';
			
			$body = "<p>"."Dear, ".$username."</p>"."<p>"."Thank you for contacting us.We will get back to you soon."."</p>";
				
		
			$return = JFactory::getMailer()->sendMail($fromEmail, $headers, $to, $subject, $body,1);
			
			if ($return !== true) {
			  return new JException(JText::_('COM_JSECURE_MAIL_FAILED'), 500);
		    }
		    else{
			   echo "Thank you for contacting us.";
			}
		}
		
		}
	function redirect(){
	   header("REFRESH: 4; url='./index.php/new-contact-form'");
	}
		
	function adminmails($config,$db)
	{
			$username=$_REQUEST['name'];
            $useremail= $_REQUEST['email'];
            $message= $_REQUEST['message'];
            $brokername="Not specified";
            if(isset($_REQUEST['broker'])){
				$brokername=$_REQUEST['broker'];
           }
		   $emailids = array();
		   $query = $db->getQuery(true);//
		   $table=$db->quoteName('g21yj_categories');
		   //echo $table."nbmn";exit;
		   $query->clear();
		   $query->select($db->quoteName('id'));
			$query->from($db->quoteName('g21yj_categories'));
			$query->where($db->quoteName('extension')." = ".$db->quote('com_contact')." and ".
			              $db->quoteName('alias')." = ".$db->quote('contact-us'));
			//echo $query;exit;
			$db->setQuery($query);
			//$db->execute();
			$result=$db->query();
		   $cat_id=$db->loadResult();
		   //echo $cat_id;exit;
		   $query = $db->getQuery(true);//
		   $table=$db->quoteName('g21yj_contact_details');
		   //echo $table."nbmn";exit;
		   $query->clear();
		   $query->select($db->quoteName('email_to'));
		   $query->from($db->quoteName('g21yj_contact_details'));
		   $query->where($db->quoteName('catid')." = ".$db->quote($cat_id));
			//echo $query;exit;
			$db->setQuery($query);
			//$db->execute();
			$result=$db->query();
		   $result=$db->loadColumn();
		   //print_r($result);exit;
		   foreach($result as $key=>$value){
		   $emailids[$key]= $result[$key];
	   }
	   
	   foreach($emailids as $emailid){
		    $to=$emailid;
		    $fromEmail  = $config->mailfrom;
			$fromName  = $config->fromname;
			$subject   = "Re:Contact Us";
			
			$headers = 'From: '. $fromName . ' <' . $fromEmail . '>';
			
			$body = "<p>"."User Name: ".$username."</p>"."<p>"."Email Id: ".$useremail."</p>"."<p>"."Message: ".$message."</p>"."<p>"."Broker Name: ".$brokername."</p>";
				
		
			$return = JFactory::getMailer()->sendMail($fromEmail, $headers, $to, $subject, $body,1);
			
			if ($return !== true) {
			  return new JException(JText::_('COM_JSECURE_MAIL_FAILED'), 500);
		    }
		   
		   }
	   
	}
	
	}


?>
