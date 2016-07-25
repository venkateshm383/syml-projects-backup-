<?php
class socialnetworks{
	function getsites(){//die('b,mn,');
		//define( '_JEXEC', 1 );
		//define('JPATH_BASE', dirname(dirname(__FILE__) ));  // define JPATH_BASE on the external file
		//define( 'DS', '/' );
        	//require_once ( JPATH_BASE .DS.'includes'.DS.'defines.php' );
		//require_once ( JPATH_BASE .DS.'includes'.DS.'framework.php' );
		//require_once ( JPATH_BASE .DS.'libraries'.DS.'joomla'.DS.'factory.php' );
		$mainframe =JFactory::getApplication('site');
        $db= JFactory::getDbo();//print_r($db);exit;
        
        $query = $db->getQuery(true);//
		$table=$db->quoteName('g21yj_social_networks_');
		   
		$query->clear();
		$query->select($db->quoteName(array('state','facebook', 'twitter', 'google_plus', 'dribble','tutorial9','rss')));
		$query->from($db->quoteName('g21yj_social_networks_'));
			
		$db->setQuery($query);
		$social_configs = $db->loadObjectList(); 
		if($social_configs){
		foreach($social_configs as $social_config){
			if($social_config->state == 1){
				$facebook= $social_config->facebook;
				$twitter= $social_config->twitter;
				$google_plus= $social_config->google_plus;
				$dribble= $social_config->dribble;
				$tutorial9= $social_config->tutorial9;
				$rss= $social_config->rss;
			   }
				
			
			}
			return array(
			'facebook'=>$facebook,'twitter'=>$twitter,'google_plus'=>$google_plus,'dribble'=>$dribble,'tutorial9'=>$tutorial9,'rss'=>$rss
			);
		}
		}
}
    
?>
