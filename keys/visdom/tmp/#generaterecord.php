<?php
	define( '_JEXEC', 1 );
	define('JPATH_BASE', '/opt/bitnami/apache2/htdocs/visdom');
	define( 'DS', '/' );

	require_once ( JPATH_BASE .DS.'includes'.DS.'defines.php' );
	require_once ( JPATH_BASE .DS.'includes'.DS.'framework.php' );
	require_once ( JPATH_BASE .DS.'libraries'.DS.'joomla'.DS.'factory.php' );
	$mainframe =JFactory::getApplication('site');

	//~ $config   = new JConfig();
	//~ $mailer=JFactory::getMailer();
    //~ $db= JFactory::getDbo();
	//~ print_r($db);exit;
$jinput = JFactory::getApplication()->input;
$input = new JInput();
$post = $input->getArray($_REQUEST);
if(isset($post['view']) && $post['view'] == "mortgageapplication" && isset($post['id']) && isset($post['username']) && isset($post['password']))
{
	   $name = '""';
	   $leadid = $post['id'];
	   if(isset($post['name']))
	   $name = '"'.$post['name'].'"';
		$username = '"'.$post['username'].'"';
		$password = '"'.$post['password'].'"';
		$db = JFactory::getDbo();
		$query = $db->getQuery(true);
		$columns = array('id', 'name', 'username', 'password' , 'createddate' , 'updateddate' , 'status');
		$values = array($leadid, $name, $username, $password , 'now()' ,'now()',1);
		$query->insert($db->quoteName('#__mortgageapplication_mortgageapplicationedit'))
				->columns($db->quoteName($columns))
				->values(implode(',', $values));
				//~ echo $query;
				//~ exit;
				try
			{
				$db->setQuery($query);
				$db->query();
			}
			catch (RuntimeException $e)
			{
				JError::raiseError(500, $e->getMessage());
			}
			echo "inserted";

}

else{
	echo '<div class="alert alert-error" id="system-message">'."Warning: Please provide all parameters".'</div>';
	exit;
}

