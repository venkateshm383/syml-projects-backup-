<?php
/*------------------------------------------------------------------------
# mortgageapplication.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');

// Added for Joomla 3.0
if(!defined('DS')){
	define('DS',DIRECTORY_SEPARATOR);
};

// Set the component css/js
$document = JFactory::getDocument();
$document->addStyleSheet('components/com_mortgageapplication/assets/css/mortgageapplication.css');

// Require helper file
JLoader::register('MortgageapplicationHelper', dirname(__FILE__) . DS . 'helpers' . DS . 'mortgageapplication.php');

// import joomla controller library
jimport('joomla.application.component.controller');

// Get an instance of the controller prefixed by Mortgageapplication
$controller = JControllerLegacy::getInstance('Mortgageapplication');

// Perform the request task
$controller->execute(JRequest::getCmd('task'));

// Redirect if set by the controller
$controller->redirect();
?>