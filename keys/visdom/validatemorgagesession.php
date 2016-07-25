<link href='<?php echo  JURI::base() .'/templates/protostar/css/template.css' ?>' rel='stylesheet' type='text/css' />
<?php
defined('_JEXEC') or die;
define('JOOMLAPATH_BASE', realpath(dirname(__FILE__)));
require_once ( JOOMLAPATH_BASE .'/includes/defines.php' );
require_once ( JOOMLAPATH_BASE .'/includes/framework.php' );
require_once ( JOOMLAPATH_BASE .'/libraries/joomla/factory.php' );
//$joomlaconfig = JFactory::getConfig();
$mainframe = JFactory::getApplication('site');
$mainframe->initialise();
JHtml::_('behavior.keepalive');
$jinput = JFactory::getApplication()->input;
$input = new JInput();
$post = $input->getArray($_REQUEST);
$username = "";
$password = "";
if($post['option'] == "com_mortgageapplication" && $post['view'] == "mortgageapplication" && isset($post['crm_lead']) && isset($post['action']) && isset($post['resid']))
{
	$leadid = $post['crm_lead'];
	$session = JFactory::getSession();
	if(isset($post['username']))
	{
		if(isset($post['password']))
		{
			$username = $post['username'];
            $password = $post['password'];
			$db = JFactory::getDbo();
			$query = $db->getQuery(true);
			  
			$query->select($db->quoteName(array('name', 'username', 'password', 'status')));
			$query->from($db->quoteName('#__mortgageapplication_mortgageapplicationedit'));
			$query->where($db->quoteName('id') . ' = '. $db->quote((int) $leadid));   
			try
			{
				$db->setQuery($query);
			}
			catch (RuntimeException $e)
			{
				JError::raiseError(500, $e->getMessage());
			}
			$dbdetails = $db->loadAssoc();
			if(isset($dbdetails['username']) && isset($dbdetails['password']))
			{
				if($username == $dbdetails['username'])
				{
					if($password == $dbdetails['password'])
					{
						$session->set('morgageapplicationlogin', "active");
					}
					else
					{
						echo '<div class="alert alert-error" id="system-message">'."Warning: Password does not match please provide a correct password".'</div>';
					}
					
				}
				else
				{
					echo '<div class="alert alert-error" id="system-message">'."Warning: The email or password you entered is incorrect. Capitalization is important.".'</div>';
				}
			}
		}
		else{
			echo '<div class="alert alert-error" id="system-message">'."Warning: Please provide a password".'</div>';
		}
		
	}
	
	
	
	if($session->get('morgageapplicationlogin') != "active")
	{
		?>
		<div id="wrap">
		<div id="mortgage-login" class="content panel">
		<div class="mortgage-login">
		<form name="key" method="POST" class="form-horizontal">
			<div class="login-description">
				<img src="<?php echo  JURI::base() .'/templates/protostar/images/logo2.png' ?>" width="280px">
				<h2><?php echo JText::_( 'Mortgage Application' );?></h2>
				<!--legend><?php //echo JText::_( 'Login' );?></legend-->
			</div>
			<fieldset class="well well-mortgage">
				<div class="control-group control-group-mortgage">
					<div class="control-label control-label-mortgage">
						<label id="username" for="username" class="hasTooltip required" title="<strong>Userame</strong><br />Enter your username"><?php //echo JText::_( 'Username' );?><span class="star">&#160;*</span></label>
					</div>
					<div class="controls controls-mortgage">
						<input type="text" name="username" id="username" size="30" required aria-required="true" class="mortgage-text" type="text" placeholder="Username" />
					</div>
				</div>
				<div class="control-group control-group-mortgage">
					<div class="control-label control-label-mortgage">
						<label id="password" for="password" class="hasTooltip required" title="<strong>Password</strong><br />Enter your password"><?php //echo JText::_( 'Password' );?><span class="star">&#160;*</span></label>
					</div>
					<div class="controls controls-mortgage">
						<input type="password" name="password" id="password" size="30" required aria-required="true" class="mortgage-text" type="text" placeholder="Password"/>
					</div>
				</div>
		
			</fieldset>
			<div class="form-actions form-actions-mortgage">
				<button type="submit" class="btn btn-primary validate btn-mortgage">Login</button>
			</div>
		</form>
		</div>
		</div>
		</div>
		<?php 
		exit;
	}
	
	
}

else{
	echo '<div class="alert alert-error" id="system-message">'."Warning: Please provide all parameters".'</div>';
	exit;
}

//~ exit;
//~ $username = 
//~ $session = JFactory::getSession();
//~ echo $session->get('morgageapplicationlogin');
//$session = JFactory::getSession();
//$session->set('morgageapplicationlogin', "active");

?>